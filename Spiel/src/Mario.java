import Controller.Input.Key;
import Model.Entity.EntityPlayer;

public class Mario extends EntityPlayer
{
	private boolean left = false, right = false, up = false, down = false;
	
	private final float speed = 0.5f;
	
	public Mario()
	{
		
	}
	
	public Mario(boolean useless)
	{
		super(0.5F, 0.5F, 1.5f, "Images/Mario.png", 6, new int[]{4,1,1}, new String[]{"Walk", "Jump", "Stand"}, new float[]{0.1f, 100f, 100f});
	}

	@Override
	public void onUpdate()
	{
		if (left)
		{
			this.moveX(-speed);
		}
		if (right)
		{
			this.moveX(speed);
		}
		if (up)
		{
			this.moveY(speed);
		}
		if (down)
		{
			this.moveY(-speed);
		}
		else if (!left && !right && !down && !up)
		{
			this.moveX(0);
			this.moveY(0);
		}
	}
	
	public void onKeyPressed(Key key)
	{
		if (key == Key.W)
		{
			up = true;
		}
		else if (key == Key.S)
		{
			down = true;
		}
		
		if (key == Key.A)
		{
			this.setMirrored(false);
			left = true;
		}
		else if (key == Key.D)
		{
			this.setMirrored(true);
			right = true;
		}
	}
	
	public void onKeyReleased(Key key)
	{
		
		if (key == Key.W)
		{
			up = false;
		}
		if (key == Key.A)
		{
			left = false;
		}
		if (key == Key.S)
		{
			down = false;
		}
		if (key == Key.D)
		{
			right = false;
		}
	}
}