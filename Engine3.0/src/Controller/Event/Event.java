package Controller.Event;

import java.io.Serializable;
import java.net.InetAddress;

import Controller.Controller;
import Main.Game;

/**
 * Klasse, aus der alle Events abgeleitet werden m�ssen.
 */
public abstract class Event implements Serializable
{
	private static final long serialVersionUID = 3708320630105359732L;
	private InetAddress address, destination;
	private boolean sendOverNetwork;
	
	/**
	 * Konstruktor von Event, initialisiert die ID und f�gt sich selbst dem EventBus hinzu
	 * @param name Name des Events
	 */
	public Event()
	{
		this.sendOverNetwork = true;
		
		address = Game.instance().getIp();
		this.destination = null;
		
		Controller.instance().addEvent(this);
	}
	
	public Event(boolean b) 
	{
		this.sendOverNetwork = b;
		this.destination = null;
		address = Game.instance().getIp();

		Controller.instance().addEvent(this);
	}
	
	public Event(InetAddress destination)
	{
		this.sendOverNetwork = true;
		this.destination = destination;
		address = Game.instance().getIp();

		Controller.instance().addEvent(this);
	}
	
	//++++++++++++++++ Getter & Setter ++++++++++++++++++++++//
	
	/**
	 * Gibt die IP-Adresse des Computers zur�ck, welcher das Event erzeugt hat
	 * @return InetAddress IP-Adresse
	 */
	public InetAddress getAddress()
	{
		return address;
	}
	
	public void shouldBeSentOverServer(boolean sentOverServer)
	{
		this.sendOverNetwork = sentOverServer;
	}
	
	public boolean isSentOverNetwork()
	{
		return this.sendOverNetwork;
	}
	
	public InetAddress getDestination()
	{
		return destination;
	}
}