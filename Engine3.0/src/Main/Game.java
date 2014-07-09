package Main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.Vector;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import Controller.Controller;
import Controller.Event.EngineEventHandler;
import Controller.Event.Event;
import Controller.Event.PlayerAliveEvent;
import Model.Model;
import Model.Entity.Entity;
import Model.Network.Network;
import View.View;
import static org.lwjgl.opengl.GL11.*;

/**
 * Hauptklasse der Engine, die Hauptklasse des Spiels muss aus dieser abgeleitet werden
 */
public abstract class Game implements Runnable
{
	private static Game instance;
	
	private boolean running;
	
	private Model model;
	private Network network;
	private Controller controller;
	private View view;
	private Time time;
	private EngineEventHandler eventHandler;
	
	private int width, height;
	private boolean fullscreen;
	private String title;
	
	private boolean start;
		
	private InetAddress ip;
	
	private long counter = 0;
	
	/**
	 * Konstruktor von Game, entweder für lokalen LAN-Modus oder als Server
	 * @param port Netzwerkport für den Server
	 * @param width Breite des Fensters
	 * @param height Höhe des Festers
	 * @param fullscreen true bei Vollbild, Auflösung dann gleiche wie Desktop
	 * @param title Titel des Fensters
	 * @param worldPath Pfad der Textur der Welt
	 */
	public Game(int width, int height, boolean fullscreen, String title, String worldPath)
	{
		running = true;
		instance = this;
		
		this.start = true;

		this.width = width;
		this.height = height;
		this.fullscreen = fullscreen;
		this.title = title;
		
		time = new Time();
		controller = new Controller();
		model = new Model(worldPath);
		eventHandler = new EngineEventHandler();
	}
	
	protected void startGame()
	{
		Thread t1 = new Thread(this);
		t1.start();
	}
	
	@Override
	public void run()
	{
		initLWJGL(width, height, title, fullscreen);
		
		view = new View(width, height, fullscreen, title);
		
		controller.init();
		
		for (Entity e : Model.instance().getEntities().values())
		{
			e.start();
		}
		Model.instance().start();
		
		this.start = false;
		
		while (running)
		{
			Vector<Event> events = Controller.instance().getEvents();
			for (Event e : events)
			{
				eventHandler.onEvent(e);
			}
			time.update();
			model.update(events);
			if (network != null) network.update(events);
			controller.update();
			view.update();
			this.onUpdate();
			
			view.render();
			
			this.sendAlive();
			
			try
			{
				Thread.sleep(10);
			} catch (InterruptedException e1) {e1.printStackTrace();}
			
			if (Display.isCloseRequested()) running = false;
		}
		controller.destroy();
		Display.destroy();
	}

	protected void startServer(String host, int port)
	{
		ip = getExternalIP();
		
		if (host != null) network = new Network(host, port);
		else network = new Network(port);
	}
	
	private void initLWJGL(int width, int height, String title, boolean fullscreen)
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			if (fullscreen)
			{
				Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
			}
			Display.setInitialBackground(150, 150, 150);
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			Display.destroy();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(-1, 1, -1, 1, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_ALPHA_TEST);
		
		glEnable(GL_BLEND);
		glAlphaFunc(GL_GREATER, 0.1f);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
	}

	public void sendAlive()
	{
		if (counter > 2000)
		{
			counter = 0;
			new PlayerAliveEvent();
		}
	}

	/**
	 * Wird in jedem Frame aufgerufen, kann frei verwendet werden
	 */
	public abstract void onUpdate();
	
	//++++++++++++++++ Getter & Setter ++++++++++++++++++++++//
	
	private InetAddress getExternalIP()
	{
		InetAddress address = null;
		String[] urls = 
			{
				"http://checkip.amazonaws.com/",
				"http://icanhazip.com/",
				"http://curlmyip.com/",
				"http://www.trackip.net/ip"
			};
		int counter = 0;
		do
		{
			try
			{
			URL whatismyip = new URL(urls[counter]);
			BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			
			String ip = in.readLine();
			
			System.out.println("Address: " + ip);
			address = InetAddress.getByName(ip);
			}
			catch (IOException e) {e.printStackTrace();}
			counter++;
		}
		while (address == null && counter < urls.length);
		
		return address;
	}
	
	/**
	 * Gibt eine Instanz der Klasse Game zurï¿½ck
	 * @return Instanz von Game
	 */
	public static Game instance(){ return instance; }
	
	/**
	 * Gibt den aktuellen Spielstatus zurï¿½ck
	 * @return true, wenn Spiel noch aktiv
	 */
	public boolean isRunning(){ return running; }
	
	/**
	 * Gibt die Lokale IP-Adresse zurï¿½ck
	 * @return InetAddress
	 */
	public InetAddress getIp(){ return ip; }
	
	public void setRunning(boolean run)
	{
		this.running = run;
	}
	
	public boolean isStart()
	{
		return start;
	}
}