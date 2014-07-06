package View;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import Model.Model;
import Model.Entity.Entity;
import Model.Entity.World;

public class View
{
	public View(int width, int height, boolean fullscreen, String title)
	{
		
	}
	
	public void update()
	{
		Display.update();
	}
	
	public void render()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		
		World.instance().renderWorld();
		
		for(Entity e : Model.instance().getEntities().values())
		{
			e.render();
		}
	}
}
