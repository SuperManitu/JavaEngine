package Controller.Event;

import java.util.UUID;

/**
 * Subklasse von Event, wird aufgerufen wenn sich ein Objekt bewegt
 */
public class MovementAlterEvent extends Event
{
	private static final long serialVersionUID = 3033893330446070461L;
	
	private float x, y;
	private UUID id;

	/**
	 * Konstruktor von MovementEvent, beinhaltet x- und y-Koordinate, sowie die ID des Objekts
	 * @param x x-Koordinate
	 * @param y y-Koordinate
	 * @param id UUID des Objekts
	 */
	public MovementAlterEvent(float x, float y, UUID id)
	{	
		this.x = x; 
		this.y = y;
		this.id = id;
	}
	
	/**
	 * Gibt die aktuelle x-Position des Objekts zur�ck
	 * @return x-Koordinate
	 */
	public float getX() { return x; }
	/**
	 * Gibt die aktuelle y-Position des Objekts zur�ck
	 * @return y-Koordinate
	 */
	public float getY() { return y; }
	/**
	 * Gibt die UUID des Objekts zur�ck
	 * @return UUID
	 */
	public UUID getUUID() { return id; }
}
