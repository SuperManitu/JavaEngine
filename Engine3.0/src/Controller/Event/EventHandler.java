package Controller.Event;

import java.io.Serializable;

/**
 * Klasse, aus der alle EventHandler abgeleitet werden müssen
 */
public abstract class EventHandler implements Serializable
{
	private static final long serialVersionUID = 7855372193333506488L;

	/**
	 * Konstruktor von EventHandler, fügt sich selbst in die Liste der Event-Handler ein
	 */
	public EventHandler()
	{
		new NewEventHandlerEvent(this, false);
	}
	
	/**
	 * Methode, in der die Befehle, welche beim Auftreten eines bestimmten Events getan werden sollen, stehen  
	 * @param event Event, welches bearbeitet werden soll.
	 */
	public abstract void onEvent(Event event);
}
