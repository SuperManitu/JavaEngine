import Main.Game;

public class Spiel extends Game
{	
	public Spiel(String host)
	{
		super(800, 600, false, "Testgame", "Images/World.png");
		this.startServer(host, 25566);
		new SpielEventHandler();
		Mario mario = new Mario(true);
		new SpielInputHandler(mario);
		
		this.startGame(); // Letzer Teil des Konstruktors
	}

	@Override
	public void onUpdate() 
	{
		
	}
}