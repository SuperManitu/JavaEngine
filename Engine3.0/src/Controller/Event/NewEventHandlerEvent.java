package Controller.Event;

public class NewEventHandlerEvent extends Event
{
	private static final long serialVersionUID = 1L;
	
	private EventHandler handler;

	public NewEventHandlerEvent(EventHandler handler) 
	{
		this.handler = handler;
	}
	
	public NewEventHandlerEvent(EventHandler eventHandler, boolean b) 
	{
		super(b);
		this.handler = eventHandler;
	}

	public EventHandler getEventHandler()
	{
		return handler;
	}
}
