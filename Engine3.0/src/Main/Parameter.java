package Main;

/**
 * Klasse zum Speichern der Übergabeparameter von Game
 */
public class Parameter 
{
	public int width, height;
	public String title, worldPath;
	public boolean fullscreen;
	
	/**
	 * Konstruktor, hat alle Parameter genau wie Game
	 * @see Game
	 */
	public Parameter(int width, int height, boolean fullscreen, String title, String worldPath)
	{
		this.width = width; 
		this.height = height;
		this.title = title; 
		this.fullscreen = fullscreen;
		this.worldPath = worldPath;
	}
}
