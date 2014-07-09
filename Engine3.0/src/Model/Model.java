package Model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import Controller.Event.Event;
import Controller.Event.EventHandler;
import Main.Game;
import Model.Entity.Entity;
import Model.Entity.EntityPlayer;
import Model.Entity.World;

/**
 * Klasse, welche fï¿½r das Verwalten von Objekten zustï¿½ndig ist
 */
public class Model
{
	private List<EventHandler> eventHandlers;
	private static Model instance;
	private Hashtable<UUID, Entity> entities;
	private World world;
	
	/**
	 * Konstruktor fï¿½r den lokalen LAN-Modus oder als Server
	 * @param profile Das GameProfile des Spielers
	 * @param port Netzwerkport fï¿½r den Server/Client
	 * @param worldPath Pfad der Textur der Welt
	 */
	public Model(String worldPath)
	{
		instance = this;
		entities = new Hashtable<UUID, Entity>();
		
		eventHandlers = new ArrayList<EventHandler>();
		world = new World(worldPath);
	}
	
	public void start()
	{
		world.start();
	}
	
	/**
	 * Aktualisiert alle Objekte und die Welt
	 * @param events EventBus
	 */
	public void update(Vector<Event> events)
	{	
		for (Event e : events)
		{
			for (EventHandler handler : eventHandlers)
			{
				handler.onEvent(e);
			}
		}
		
		for (Entity e : entities.values())
		{
			if (e instanceof EntityPlayer && (Game.instance().getIp() != null && !((EntityPlayer)e).getAddress().equals(Game.instance().getIp()))) continue;
			e.update();
		}
		
		world.update();
	}

	/**
	 * Fügt einen EventHandler zu der Liste hinzu
	 * @param handler EventHandler
	 */
	public void addEventHandler(EventHandler handler)
	{
		eventHandlers.add(handler);
	}
	
	/**
	 * Fügt ein Objekt zu der Liste hinzu
	 * @param e Objekt
	 */
	public void addEntity(Entity e)
	{
		entities.put(e.getUUID(), e);
	}
	
	/**
	 * Entfernt ein Objekt aus der Liste
	 * @param uuid ID des Objekts
	 */
	public void removeEntity(UUID uuid)
	{
		entities.remove(uuid);
	}
	
	//++++++++++++++++ Getter & Setter ++++++++++++++++++++++//
	
	/**
	 * Gint eine Instanz der Klasse Model zurï¿½ck
	 * @return Instanz von Model
	 */
	public static Model instance(){ return instance; }
	
	/**
	 * Gibt das Objekt zurï¿½ck, welches die ï¿½bergebene ID hat
	 * @param uuid ID des Objekts
	 * @return Entity
	 */
	public Entity getEntity(UUID uuid)
	{
		return entities.get(uuid);
	}
	
	/**
	 * Schaut, ob die ID schon in der Liste der Objekte vorhanden ist
	 * @param uuid ID des Objekts
	 * @return true, wenn Objekt bereits vorhanden
	 */
	public boolean containsUUID(UUID uuid)
	{
		return entities.containsKey(uuid);
	}
	
	/**
	 * Gibt eine Hashtable mit allen Entities zurï¿½ck
	 * @return Hashtable<UUID, Entity>
	 */
	public Hashtable<UUID, Entity> getEntities()
	{
		return entities;
	}
	
	@SuppressWarnings("unchecked")
	public Hashtable<UUID, Entity> getEntitiesCopied()
	{
		return (Hashtable<UUID, Entity>) entities.clone();
	}
}