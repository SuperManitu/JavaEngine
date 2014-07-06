package Controller;

import java.util.Vector;

import Controller.Event.Event;
import Controller.Input.Input;

/**
 * Controller, kümmert sich um Events
 */
public class Controller
{
	private Vector<Event> eventBus;
	private static Controller instance;
	
	private Input input;
	
	/**
	 * Standardkonstuktor, initialisiert den EventBus und die statische Instanz
	 */
	public Controller()
	{
		instance = this;
		eventBus = new Vector<Event>();
		input = new Input();
	}
	
	/**
	 * Update-Methode des Controllers, bisher ungenutzt
	 */
	public void update()
	{
		input.update();
	}
	
	public void destroy()
	{
		input.destroy();
	}
	
	public void init()
	{
		input.init();
	}
	
	/**
	 * Methode zum hizufügen eines Events, wird von der Eventklasse automatisch aufgerufen
	 * @param event Event, welches hizugefügt werden soll
	 */
	public synchronized void addEvent(Event event)
	{
		eventBus.add(event);
	}
	
	//++++++++++++++++ Getter & Setter ++++++++++++++++++++++//
	
	/**
	 * Gibt eine Instanz der Klasse Controller zurück, um auf Methoden zugreifen zu können
	 * @return Instanz von Controller
	 */
	public static Controller instance(){ return instance; }
	
	/**
	 * Gibt den kompletten EventBus zurück, und leert diesen
	 * @return Ein Vector mit allen Events die seit dem letzten Aufruf aufgetreten sind
	 */
	public Vector<Event> getEvents()
	{
		@SuppressWarnings("unchecked")
		Vector<Event> ret = (Vector<Event>) eventBus.clone();
		eventBus.clear();
		return ret;
	}
}
