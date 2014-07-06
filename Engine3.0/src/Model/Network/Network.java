package Model.Network;

import java.net.BindException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import Controller.Event.Event;


public class Network 
{
	private Server server;
	private Receiver receiver;
	private DatagramSocket socket;
	private List<InetAddress> players;
	
	private static Network instance;
	
	public Network(int port)
	{
		instance = this;
		players = new ArrayList<InetAddress>();
		
		try
		{
			socket = new DatagramSocket(port);
			socket.setSoTimeout(20);
			server = new Server(socket);
		}
		catch (BindException e) { System.out.println("Another instance of the game is already running!"); System.exit(0); }
		catch (SocketException e) { e.printStackTrace(); }
		

		receiver = new Receiver(socket, server);
		
		Thread t = new Thread(receiver);
		t.start();
	}
	
	public Network(String host, int port)
	{
		instance = this;
		players = new ArrayList<InetAddress>();
		
		try
		{
			InetAddress address = InetAddress.getByName(host);
			socket = new DatagramSocket(port);
			socket.setSoTimeout(20);
			server = new Server(socket, address);
		}
		catch (UnknownHostException e) {e.printStackTrace();} 
		catch (SocketException e) {e.printStackTrace();}
		
		receiver = new Receiver(socket, server);
		
		Thread t = new Thread(receiver);
		t.start();
	}
	
	public void update(Vector<Event> events)
	{
		server.update(events);
	}
	
	/**
	 * Gibt eine Tabelle mit allen Spielern zur�ck
	 * @return Hashtable<InetAddress, UUID>
	 */
	public List<InetAddress> getPlayers()
	{
		return players;
	}

	/**
	 * F�gt einen Spieler zu der Liste hinzu
	 * @param id IP-Adresse des Spielers
	 * @param profile Profil des Spielers
	 */
	public void addPlayer(InetAddress address) 
	{
		players.add(address);
	}
	
	/**
	 * Entfernt einen Spieler aus der Liste
	 * @param address IP-Adresse des Spielers
	 */
	public void removePlayer(InetAddress address)
	{
		players.remove(address);
	}
	
	public boolean isServer()
	{
		return server.isServer();
	}
	
	public static Network instance(){ return instance; }
}
