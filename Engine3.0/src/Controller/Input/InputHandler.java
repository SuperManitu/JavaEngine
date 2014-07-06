package Controller.Input;

public abstract class InputHandler
{
	public InputHandler()
	{
		Input.instance().addInputHandler(this);
	}
	
	public abstract void onKeyPressed(Key key);
	
	public abstract void onKeyReleased(Key key);
}
