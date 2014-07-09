package Controller.Event;
import java.util.UUID;

/**
 * Subklasse von Event, wird aufgerufen wenn auf ein Objekt eine Kraft wirkt
 */

public class ForceEvent extends Event 
{
	
	private static final long serialVersionUID = -7427448439961548605L;
	private float vectorX, vectorY;
	private UUID id;
	
	/**
	 * Konstruktor von ForceEvent, beinhaltet x- und y-Vektoren, sowie die ID des Objekts
	 * @param x x-Vektor
	 * @param y y-Vektor
	 * @param id UUID des Objekts
	 */
	
	public ForceEvent(float x, float y, UUID id)
	{
		 vectorX = x;
		 vectorY = y;
		 this.id = id;
	}

	/**
	 * Gibt den aktuellen x-Vektor des Objekts aus
	 * @return x-Vektor
	 */
	public float getVectorX()
	{
		return vectorX;
	}
	
	/**
	 * Gibt den aktuellen y-Vektor des Objekts aus
	 * @return y-Vektor
	 */
	
	public float getVectorY()
	{
		return vectorY;
	}
	
	/**
	 * Gibt die ID des Objektes aus
	 * @return ID
	 * */
	
	public UUID getID()
	{
		return id;
	}
}
