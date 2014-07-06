package Controller.Event;

import java.util.UUID;

public class AnimationMirroredEvent extends Event
{
	private static final long serialVersionUID = -8300149989561763250L;

	private boolean mirrored;
	private UUID id;
	
	public AnimationMirroredEvent(boolean mirrored, UUID id)
	{
		this.mirrored = mirrored;
		this.id = id;
	}
	
	public boolean isMirrored()
	{
		return mirrored;
	}
	
	public UUID getUUID()
	{
		return id;
	}
}
