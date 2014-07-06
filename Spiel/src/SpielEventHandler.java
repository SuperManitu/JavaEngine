import Controller.Event.Event;
import Controller.Event.EventHandler;

public class SpielEventHandler extends EventHandler
{
	private static final long serialVersionUID = 1L;

	@Override
	public void onEvent(Event event) 
	{
		if(event instanceof PlayerDeathEvent)
		{
			
		}
	}
}