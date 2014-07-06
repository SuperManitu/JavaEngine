package Controller.Input;

import java.util.Vector;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

public class Input 
{
	private static Input instance;
	
	private Vector<InputHandler> inputHandlers;
	
	public Input()
	{
		instance = this;
		inputHandlers = new Vector<InputHandler>();
	}
	
	public void init()
	{
		try
		{
			Keyboard.create();
		} catch (LWJGLException e) { e.printStackTrace(); }
	}
	
	public void update()
	{
		while (Keyboard.next())
		{
			for (InputHandler handler : inputHandlers)
			{
				if (Keyboard.getEventKeyState())
				{
					handler.onKeyPressed(Key.fromInt(Keyboard.getEventKey()));
				}
				else
				{
					handler.onKeyReleased(Key.fromInt(Keyboard.getEventKey()));
				}
			}
		}
	}
	
	public void destroy()
	{
		Keyboard.destroy();
	}
	
	public static Input instance()
	{
		return instance;
	}
	
	public void addInputHandler(InputHandler handler)
	{
		inputHandlers.add(handler);
	}
}
