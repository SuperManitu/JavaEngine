package Model.Entity;

import java.net.InetAddress;

import Main.Game;

public abstract class EntityPlayer extends Entity
{
	private InetAddress address;
	
	public EntityPlayer()
	{
		
	}
	
	public EntityPlayer(float x, float y, float scale, String path, int animationSteps, int[] animationLengths, String[] animationNames, float[] animationSpeeds)
	{
		super(x, y, scale, path, animationSteps, animationLengths, animationNames, animationSpeeds);
		
		address = Game.instance().getIp();
	}
	
	public void setAddress(InetAddress address)
	{
		this.address = address;
	}
	
	public InetAddress getAddress()
	{
		return address;
	}
}
