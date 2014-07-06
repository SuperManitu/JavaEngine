package Model.Entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Klasse zum Verwalten von Animationen
 */
public class Animation
{
	private BufferedImage animationChain;
	
	private int image, animationStep, animationSteps;
	private float[] animationSpeeds;
	
	private boolean mirrored;
	
	private int[] animationLengths;
	private String[] animationNames;
	
	private long last = System.nanoTime(), counter;

	private int actualAnimation;
	
	/**
	 * Konstruktor von Anmimation, läd die Bilder
	 * @param path Pfad der Textur
	 * @param animationSteps Anzahl an Einzelbildern in der Textur
	 * @param animationLengths Array mit den Längen der Animationen
	 * @param animationNames Array mit den Namen der Animationen
	 * @param animationSpeeds Array mit den Bildwelchesgeschwindigkeiten der Animation
	 */
	public Animation(String path, int animationSteps, int[] animationLengths, String[] animationNames, float[] animationSpeeds)
	{
		this.animationSteps = animationSteps;
		this.animationSpeeds = animationSpeeds;
		this.animationStep = 0;
		this.counter = 0L;
		this.animationLengths = animationLengths;
		this.animationNames = animationNames;
		this.actualAnimation = 0;
		
		animationChain = loadImage(path);
		
		mirrored = false;
	}
	
	public void start()
	{
		image = loadLWJGLImage(animationChain);
	}
	
	/**
	 * Aktuelisiert das Bild nach Ablauf der Zeit
	 */
	public void update()
	{
		long aktuell = System.nanoTime();
		counter += aktuell - last;
		last = aktuell;
		
		if(counter >=  animationSpeeds[actualAnimation]*10e8)
		{
			counter = 0;
			
			int max = 0;
			for(int i = 0; i < actualAnimation+1; i++)
			{
				max += animationLengths[i];
			}
			
			if(animationStep < max-1)
			{
				animationStep++;
			}
			else
			{
				animationStep = 0;
				for(int i = 0; i < actualAnimation; i++)
				{
					animationStep += animationLengths[i];
				}
			}
		}
	}

	private int loadLWJGLImage(BufferedImage image)
	{
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4); //4 for RGBA, 3 for RGB

		for(int y = 0; y < image.getHeight(); y++)
		{
			for(int x = 0; x < image.getWidth(); x++)
			{
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));     // Red component
				buffer.put((byte) ((pixel >> 8) & 0xFF));      // Green component
				buffer.put((byte) (pixel & 0xFF));             // Blue component
				buffer.put((byte) ((pixel >> 24) & 0xFF));     // Alpha component. Only for RGBA
			}
		}

		buffer.flip(); // FOR THE LOVE OF GOD DO NOT FORGET THIS

		// You now have a ByteBuffer filled with the color data of each pixel.
		// Now just create a texture ID and bind it. Then you can load it using 
		// whatever OpenGL method you want, for example:
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		int textureID = GL11.glGenTextures(); //Generate texture ID
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID); //Bind texture ID

		//Setup wrap mode
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		//Setup texture scaling filtering
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

		//Send texel data to OpenGL
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		//Return the texture ID so we can bind it later again
		return textureID;
	}

	private BufferedImage loadImage(String path) 
	{
		BufferedImage ret = null;
		
		File f = new File(path);
		try 
		{
			ret = ImageIO.read(f);
		} 
		catch (IOException e) {e.printStackTrace();}
		
		return ret;
	}
	
	//++++++++++++++++ Getter & Setter ++++++++++++++++++++++//
	/**
	 * Spiegelt die Textur an der y-Achse
	 * @param mirrored true, wenn Bild mirrored werden soll
	 */
	public void setMirrored(boolean mirrored){this.mirrored = mirrored; }
	
	/**
	 * Gibt die Breite des Bildes zurück
	 * @return Breite
	 */
	public float getWidth(){ return ((float)(animationChain.getWidth()/animationSteps))/200; }
	/**
	 * Gibt die Höhe des Bildes zurück
	 * @return Höhe
	 */
	public float getHeight(){ return ((float)animationChain.getHeight())/200; }
	
	/**
	 * Gibt die Texture-ID des Bildes zurück
	 * @return int Texture-ID
	 */
	public int getImage(){ return image; }
	
	/**
	 * Setzt die aktuelle Animation, Name muss im AnimationNames-Array vorhanden sein
	 * @param animationName Name der Animation
	 */
	public void setActualAnimation(String animationName)
	{
		for(int i = 0; i < animationNames.length; i++)
		{
			if(animationNames[i].equals(animationName))
			{
				actualAnimation = i;
				break;
			}
		}
	}
	
	/**
	 * Gibt die aktuellen Texturkoordinaten für den Frame zurück
	 * @return float[4][2]
	 */
	public float[][] getTextureCoords()
	{
		float[][] ret = new float[4][2];
		
		float width = 1/((float) animationSteps);
		
		float anX = (float) animationStep;
		
		if(!mirrored)
		{
			ret[0][0] = width*anX;
			ret[0][1] = 0;

			ret[1][0] = width*anX;
			ret[1][1] = 1;

			ret[2][0] = width*anX + width;
			ret[2][1] = 1;

			ret[3][0] = width*anX + width;
			ret[3][1] = 0;
		}
		else
		{
			ret[0][0] = width*anX + width;
			ret[0][1] = 0;

			ret[1][0] = width*anX + width;
			ret[1][1] = 1;

			ret[2][0] = width*anX;
			ret[2][1] = 1;

			ret[3][0] = width*anX;
			ret[3][1] = 0;
		}
		
		return ret;
	}
}
