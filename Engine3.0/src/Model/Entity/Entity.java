package Model.Entity;

import java.util.UUID;

import org.lwjgl.util.vector.Vector2f;

import Controller.Event.AnimationMirroredEvent;
import Controller.Event.MovementAlterEvent;
import Controller.Event.NewEntityEvent;
import Controller.Event.NewEntityPlayerEvent;
import Controller.Event.SetCoordEvent;
import Main.Game;
import Main.Time;
import Model.Model;
import static org.lwjgl.opengl.GL11.*;

/**
 * Klasse, welche sich um Objekte k�mmert
 */
public abstract class Entity
{
	private UUID uuid;
	
	private float width, height, scale;
	
	private Vector2f position, speed, newSpeed;
	
	private String path;
	
	private int animationSteps;
	private int[] animationLengths;
	private String[] animationNames;
	private float[] animationSpeeds;
	
	private float posVertices[][];
	
	private Animation animation;
	
	public Entity()
	{
		
	}
	
	/**
	 * Konstruktor, initialisiert das Objekt, darf in der nicht abstrakten Klasse keine Parameter haben
	 * @param x
	 * @param y
	 * @param scale
	 * @param path
	 * @param animationSteps
	 * @param animationLengths
	 * @param animationNames
	 * @param animationSpeeds
	 */
	public Entity(float x, float y, float scale, String path, int animationSteps, int[] animationLengths, String[] animationNames, float[] animationSpeeds)
	{
		uuid = UUID.randomUUID();
		position = new Vector2f();
		position.x = x;
		position.y = y;
		this.scale = scale;
		this.path = path;
		
		posVertices = new float[4][2];
		
		speed = new Vector2f();
		newSpeed = new Vector2f();
		
		animation = new Animation(path, animationSteps, animationLengths, animationNames, animationSpeeds);
		
		this.width = animation.getWidth();
		this.height = animation.getHeight();
		
		this.animationLengths = animationLengths;
		this.animationNames = animationNames;
		this.animationSpeeds = animationSpeeds; 
		this.animationSteps = animationSteps;
		
		updateVertices();
		
		if (!Model.instance().containsUUID(uuid))
		{
			Model.instance().addEntity(this);
		}
		
		if (!Game.instance().isStart())
		{
			start();
		}
		
		if (this instanceof EntityPlayer) new NewEntityPlayerEvent(Game.instance().getIp(), x, y, scale, path, animationSteps, animationLengths, animationNames, animationSpeeds, uuid, this.getClass());
		else new NewEntityEvent(x, y, scale, path, animationSteps, animationLengths, animationNames, animationSpeeds, uuid, this.getClass());
	}
	
	public void start()
	{
		animation.start();
	}
	
	/**
	 * Zweiter "Konstruktor", der nur f�r den Server ist
	 * @param x
	 * @param y
	 * @param scale
	 * @param path
	 * @param animationSteps
	 * @param animationLengths
	 * @param animationNames
	 * @param animationSpeeds
	 * @param id
	 */
	public void init(float x, float y, float scale, String path, int animationSteps, int[] animationLengths, String[] animationNames, float[] animationSpeeds, UUID id)
	{
		this.uuid = id;
		position = new Vector2f();
		position.x = x;
		position.y = y;
		this.scale = scale;
		this.path = path;
		
		posVertices = new float[4][2];
		
		speed = new Vector2f();
		newSpeed = new Vector2f();
		
		animation = new Animation(path, animationSteps, animationLengths, animationNames, animationSpeeds);
		
		this.width = animation.getWidth();
		this.height = animation.getHeight();
		
		this.animationLengths = animationLengths;
		this.animationNames = animationNames;
		this.animationSpeeds = animationSpeeds; 
		this.animationSteps = animationSteps;
		
		updateVertices();
		
		if (!Model.instance().containsUUID(uuid))
		{
			Model.instance().addEntity(this);
		}
		
		if (!Game.instance().isStart())
		{
			start();
		}
	}

	public void update(boolean onUpdate)
	{
		if (onUpdate) this.onUpdate();
		animation.update();
		
		if (!speed.equals(newSpeed))
		{
			System.out.println("Old: " + speed.x + " " + speed.y + " New: " + newSpeed.x + " " + newSpeed.y);
			speed.x = newSpeed.x;
			speed.y = newSpeed.y;
			new MovementAlterEvent(speed.x, speed.y, this.getUUID());
		}
		
		position.x += speed.x * Time.deltaTime();
		position.y += speed.y * Time.deltaTime();
		updateVertices();
	}
	
	private void updateVertices()
	{
		float a = width *  scale / 2;
		float b = height * scale / 2;
		posVertices[0][0] = position.x - a;
		posVertices[0][1] = position.y + b;
		
		posVertices[1][0] = position.x - a;
		posVertices[1][1] = position.y - b;
		
		posVertices[2][0] = position.x + a;
		posVertices[2][1] = position.y - b;
		
		posVertices[3][0] = position.x + a;
		posVertices[3][1] = position.y + b;
	}
	
	public void render()
	{
		float[][] texCoords = animation.getTextureCoords();
		
		glEnable(GL_TEXTURE_2D);
		
		glPushMatrix();
		glBindTexture(GL_TEXTURE_2D, animation.getImage());
		
		glBegin(GL_QUADS);
		{
			glTexCoord2f(texCoords[0][0], texCoords[0][1]);
			glVertex3f(posVertices[0][0], posVertices[0][1], -0.25f);
			
			glTexCoord2f(texCoords[1][0], texCoords[1][1]);
		    glVertex3f(posVertices[1][0], posVertices[1][1], -0.25f);
		    
			glTexCoord2f(texCoords[2][0], texCoords[2][1]);
		    glVertex3f(posVertices[2][0], posVertices[2][1], -0.25f);
		    
			glTexCoord2f(texCoords[3][0], texCoords[3][1]);
		    glVertex3f(posVertices[3][0], posVertices[3][1], -0.25f);
		}
		glEnd();
		
		glPopMatrix();
		glBindTexture(GL_TEXTURE_2D, 0);
		
		glDisable(GL_TEXTURE_2D);
	}
	
	public abstract void onUpdate();
	
	//++++++++++++++++ Getter & Setter ++++++++++++++++++++++//
	
	public UUID getUUID(){ return uuid; }
	public float getX(){ return position.x; }
	public float getY(){ return position.y; }
	public float getScale(){ return scale; }
	public String getPath(){ return path; }
	public int getAnimationSteps(){ return animationSteps; }
	public int[] getAnimationLengths(){ return animationLengths; }
	public String[] getAnimationNames(){ return animationNames; }
	public float[] getAnimationSpeeds(){ return animationSpeeds; }
		
	public void setXAndY(float x, float y)
	{
		new SetCoordEvent(x, y, this.uuid);
	}
	
	public void applySetCoordEvent(SetCoordEvent event)
	{
		this.position.x = event.getX();
		this.position.y = event.getY();
		this.uuid = event.getUUID();
	}
	
	public void setMirrored(boolean mirrored)
	{
		new AnimationMirroredEvent(mirrored, this.uuid);
	}
	
	public void applyAnmationMirroredEvent(AnimationMirroredEvent event)
	{
		animation.setMirrored(event.isMirrored());
	}
	
	public void moveX(float x)
	{
		newSpeed.x = x;
	}
	
	public void moveY(float y)
	{
		newSpeed.y = y;
	}
	
	public void applyMovementAlterEvent(MovementAlterEvent event)
	{
		this.speed = new Vector2f(event.getX(), event.getY());
		this.newSpeed = new Vector2f(event.getX(), event.getY());
	}
}
