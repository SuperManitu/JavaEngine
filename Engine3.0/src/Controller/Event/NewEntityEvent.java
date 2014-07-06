package Controller.Event;

import java.net.InetAddress;
import java.util.UUID;

import Model.Entity.Entity;

/**
 * Subklasse von Event, welche bei der Erzeugung eines neuen Objekts aufgerufen wird
 */
public class NewEntityEvent extends Event
{
	private static final long serialVersionUID = -4281736565277187300L;

	private float x, y, scale;
	private String path;
	private int animationSteps;
	private int[] animationLengths;
	private float[] animationSpeeds;
	private String[] animationNames;
	private UUID id;
	private Class<? extends Entity> classObject;
	
	/**
	 * Konstruktor von NewEntityEvent, ben�tigt alle Informationen �ber das Objekt
	 * @param x x-Koordinate
	 * @param y y-Koordinate
	 * @param scale Gr��enfaktor
	 * @param path Pfad der Textur
	 * @param animationSteps Anzahl an Einzelanimationen in der Textur
	 * @param animationLengths Array mit den einzelnen L�ngen der verschiedenen Animationen
	 * @param animationNames Array mit den Namen der Animationen
	 * @param animationSpeeds Array mit den Bildwechselgeschwindigkeiten der Aniamtionen. 1 bedeutet einmal pro Sekunde.
	 * @param id UUID des Objekts
	 * @param classObject Class<? extends Entity> ermittelbar �ber this.getClass()
	 */
	public NewEntityEvent(float x, float y, float scale, String path, int animationSteps, int[] animationLengths, String[] animationNames, float[] animationSpeeds, UUID id, Class<? extends Entity> classObject) 
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.path = path;
		this.animationSteps = animationSteps;
		this.animationLengths = animationLengths;
		this.animationNames = animationNames;
		this.animationSpeeds = animationSpeeds;
		this.classObject = classObject;
	}
	
	public NewEntityEvent(InetAddress destination, float x, float y, float scale, String path, int animationSteps, int[] animationLengths, String[] animationNames, float[] animationSpeeds, UUID id, Class<? extends Entity> classObject) 
	{
		super(destination);
		this.id = id;
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.path = path;
		this.animationSteps = animationSteps;
		this.animationLengths = animationLengths;
		this.animationNames = animationNames;
		this.animationSpeeds = animationSpeeds;
		this.classObject = classObject;
	}
	
	/**
	 * Gibt die UUID des Objekts zur�ck
	 * @return UUID
	 */
	public UUID getUUID()
	{
		return id;
	}

	/**
	 * Gibt die Namen der Animationen zur�ck
	 * @return String[]
	 */
	public String[] getAnimationNames() 
	{
		return animationNames;
	}

	/**
	 * Gibt die Bildwechselgeschwindigkeiten der Animationen zur�ck
	 * @return float[]
	 */
	public float[] getAnimationSpeeds()
	{
		return animationSpeeds;
	}

	/**
	 * Gibt die Anzahl an Einzelbildern in der Textur zur�ck
	 * @return int
	 */
	public int getAnimationSteps()
	{
		return animationSteps;
	}

	/**
	 * Gibt die x-Koordinate des Objekts zur�ck
	 * @return float
	 */
	public float getX()
	{
		return x;
	}
	
	/**
	 * Gibt die y-Koordinate des Objekts zur�ck
	 * @return float
	 */
	public float getY()
	{
		return y;
	}
	
	/**
	 * Gibt den Gr��enfaktor des Objekts zur�ck
	 * @return float
	 */
	public float getScale()
	{
		return scale;
	}

	/**
	 * Gibt den Pfad der Textur zur�ck
	 * @return String
	 */
	public String getPath() 
	{
		return path;
	}

	/**
	 * Gibt einen Array mit den L�ngen der Animationen zur�ck
	 * @return int[]
	 */
	public int[] getAnimationLengths()
	{
		return animationLengths;
	}
	
	/**
	 * Gibt das Klassenobjekt des Objekts zur�ck
	 * @return Class<? extends Entity>
	 */
	public Class<? extends Entity> getClassObject()
	{
		return classObject;
	}
}
