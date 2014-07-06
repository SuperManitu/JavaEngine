package Main;

public class Time 
{
	private static long delta, last, now;
	
	public Time()
	{
		last = System.nanoTime();
	}
	
	public void update()
	{
		now = System.nanoTime();
		delta = last - now;
		last = now;
	}
	
	public static float deltaTime()
	{
		return ((float)delta)/(float)1e9;
	}
}
