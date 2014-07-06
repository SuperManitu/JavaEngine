package Controller.Event;

import java.util.UUID;

public class SetCoordEvent extends Event
{
	private static final long serialVersionUID = -936361075187922254L;
	
	private float x, y;
	private UUID uuid;

	public SetCoordEvent(float x, float y, UUID id) 
	{
		this.uuid = id;
		this.x = x;
		this.y = y;
	}
	
	public float getX(){ return x; }
	
	public float getY(){ return y; }
	
	public UUID getUUID(){ return uuid; }
}
