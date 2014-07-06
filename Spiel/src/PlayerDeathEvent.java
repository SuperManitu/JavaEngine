import java.util.UUID;

import Controller.Event.Event;

public class PlayerDeathEvent extends Event
{
	private static final long serialVersionUID = 6800935324115891362L;

	private UUID playerID;
	
	public PlayerDeathEvent() 
	{
		
	}
	
	public UUID getUUID()
	{
		return playerID;
	}

}
