package Controller.Event;

import java.net.InetAddress;
import java.util.UUID;

import Model.Entity.Entity;

/**
 * Subklasse von Event, welche bei der Erzeugung eines neuen Objekts aufgerufen wird
 */
public class NewEntityPlayerEvent extends NewEntityEvent
{
	private static final long serialVersionUID = 3982714051673959950L;
	
	private InetAddress address;

	public NewEntityPlayerEvent(InetAddress address, float x, float y, float scale, String path, int animationSteps, int[] animationLengths,	String[] animationNames, float[] animationSpeeds, UUID id, Class<? extends Entity> classObject) 
	{
		super(x, y, scale, path, animationSteps, animationLengths, animationNames, animationSpeeds, id, classObject);
		this.address = address;
	}
	
	public NewEntityPlayerEvent(InetAddress destination, InetAddress address, float x, float y, float scale, String path, int animationSteps, int[] animationLengths,	String[] animationNames, float[] animationSpeeds, UUID id, Class<? extends Entity> classObject) 
	{
		super(destination, x, y, scale, path, animationSteps, animationLengths, animationNames, animationSpeeds, id, classObject);
		this.address = address;
	}
	
	public InetAddress getPlayerAddress()
	{
		return address;
	}
}
