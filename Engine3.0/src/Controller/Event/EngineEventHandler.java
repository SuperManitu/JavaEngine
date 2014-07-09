package Controller.Event;

import java.util.Hashtable;
import java.util.UUID;

import Main.Game;
import Model.Model;
import Model.Entity.Entity;
import Model.Entity.EntityPlayer;
import Model.Network.Network;

/**
 * Eventhandler, welcher die engineinternen Methoden aufruft
 */
public class EngineEventHandler
{
	/**
	 * Funktion, welche durch die Überklasse aufgerufen wird um Events zu verwalten
	 */
	public void onEvent(Event event) 
	{
		System.out.println(event.getClass().getName() + ": " + event.getAddress());
		
		if (event instanceof NewEntityEvent)
		{
			if (Game.instance().getIp() == null || event.getAddress().equals(Game.instance().getIp())) return;
			
			NewEntityEvent e = (NewEntityEvent) event;

			Entity entity = null;
			try 
			{
				entity = e.getClassObject().newInstance();
				
				if (e instanceof NewEntityPlayerEvent)
				{
					((EntityPlayer)entity).setAddress(((NewEntityPlayerEvent)e).getPlayerAddress());
				}
			}
			catch (InstantiationException e1) {e1.printStackTrace();}
			catch (IllegalAccessException e1) {e1.printStackTrace();}

			entity.init(e.getX(), e.getY(), e.getScale(), e.getPath(), e.getAnimationSteps(), e.getAnimationLengths(), e.getAnimationNames(), e.getAnimationSpeeds(), e.getUUID());

			if (! Model.instance().containsUUID(entity.getUUID()))
			{
				Model.instance().addEntity(entity);
			}
		}
		else if (event instanceof MovementAlterEvent)
		{
			MovementAlterEvent e = (MovementAlterEvent) event;

			if (Model.instance().containsUUID(e.getUUID()))
			{
				Model.instance().getEntity(e.getUUID()).applyMovementAlterEvent(e);
			}
		}
		else if (event instanceof PlayerLoginEvent)
		{
			if (!event.getAddress().equals(Game.instance().getIp())) // LoginEvent von außen
			{
				Network.instance().addPlayer(event.getAddress()); // Spieler hinzufügen
				
				for (Entity e : Model.instance().getEntities().values()) // Entities an destination (den neuen Spieler) senden
				{
					new NewEntityEvent(event.getAddress(), e.getX(), e.getY(), e.getScale(), e.getPath(), e.getAnimationSteps(), e.getAnimationLengths(), e.getAnimationNames(), e.getAnimationSpeeds(), e.getUUID(), e.getClass());
				}
			}
			else // eigenes LoginEvent
			{
				for (Entity e : Model.instance().getEntities().values())
				{
					if (e instanceof EntityPlayer)
					{
						EntityPlayer p = (EntityPlayer) e;
						new NewEntityPlayerEvent(p.getAddress(), e.getX(), e.getY(), e.getScale(), e.getPath(), e.getAnimationSteps(), e.getAnimationLengths(), e.getAnimationNames(), e.getAnimationSpeeds(), e.getUUID(), e.getClass());
					}
				}
			}
		}
		else if (event instanceof PlayerLogoutEvent)
		{
			PlayerLogoutEvent e = (PlayerLogoutEvent) event;
			Network.instance().removePlayer(e.getAddress());
			
			Hashtable<UUID, Entity> entitiesCopied = Model.instance().getEntitiesCopied();
			
			for (Entity entity : entitiesCopied.values()) // Entity des Spielers entfernen
			{
				if (entity instanceof EntityPlayer && ((EntityPlayer)entity).getAddress().equals(e.getAddress()))
				{
					Model.instance().removeEntity(entity.getUUID());
				}
			}
		}
		else if (event instanceof NewEventHandlerEvent)
		{
			NewEventHandlerEvent e = (NewEventHandlerEvent) event;
			Model.instance().addEventHandler(e.getEventHandler());
		}
		else if (event instanceof AnimationMirroredEvent)
		{
			AnimationMirroredEvent e = (AnimationMirroredEvent) event;
			Model.instance().getEntity(e.getUUID()).applyAnmationMirroredEvent(e);
		}
	}
}
