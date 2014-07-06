package Controller.Event;

/**
 * Sub-Klasse von Event, wir immer nach zwei Sekunden erzeugt, um eine Abmeldung beim Server zu Verhindern
 */
public class PlayerAliveEvent extends Event
{
	private static final long serialVersionUID = 4032611023334256366L;

	/**
	 * Konstruktor von PlayerAliveEvent, setzt nur den Namen
	 */
	public PlayerAliveEvent()
	{
		
	}
}
