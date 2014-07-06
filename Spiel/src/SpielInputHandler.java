import Controller.Input.InputHandler;
import Controller.Input.Key;


public class SpielInputHandler extends InputHandler
{
	private Mario mario;
	
	public SpielInputHandler(Mario mario)
	{
		this.mario = mario;
	}

	@Override
	public void onKeyPressed(Key key)
	{
		mario.onKeyPressed(key);
	}

	@Override
	public void onKeyReleased(Key key)
	{
		mario.onKeyReleased(key);
	}

}
