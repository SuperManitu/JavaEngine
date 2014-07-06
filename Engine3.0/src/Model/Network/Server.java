package Model.Network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Vector;

import Controller.Event.Event;
import Controller.Event.PlayerLoginEvent;
import Controller.Event.PlayerLogoutEvent;
import Main.Game;
import Main.Time;

public class Server 
{
	private int port, packageCounter;
	private boolean isServer;

	private DatagramSocket socket;
	private InetAddress myAdress;
	private InetAddress serverAddress;
	
	private long counter = 0;
	
	private Hashtable<InetAddress, Long> clients;

	public Server(DatagramSocket socket) throws SocketException
	{
		this.socket = socket;
		this.clients = new Hashtable<InetAddress, Long>();
		this.port = socket.getLocalPort();

		myAdress = Game.instance().getIp();

		this.serverAddress = serverAvailable();

		if (serverAddress != null)
		{
			this.isServer = false;
			try 
			{
				sendPacket(new PlayerLoginEvent());
			}
			catch (IOException e) {e.printStackTrace();}
		}
		else
		{
			this.isServer = true;
			this.serverAddress = this.myAdress;
		}
	}
	
	public Server(DatagramSocket socket, InetAddress host)
	{
		myAdress = Game.instance().getIp();
		
		this.socket = socket;
		this.clients = new Hashtable<InetAddress, Long>();
		this.isServer = false;
		this.serverAddress = host;
		this.port = socket.getLocalPort();
		try 
		{
			sendPacket(new PlayerLoginEvent());
		}
		catch (IOException e) {e.printStackTrace();}
	}

	public void update(Vector<Event> events)
	{
		if (isServer) sendBroadcastPacket();
		
		counter += Time.deltaTime();

		for (Event ev : events)
		{
			try
			{
				if (isServer) broadcastPacket(ev);
				else if (ev.getAddress().equals(myAdress) && ev.isSentOverNetwork())
				{
					sendPacket(ev);
				}
			} 
			catch (IOException e) {e.printStackTrace();}
		}
	}

	private void broadcastPacket(Event ev) throws IOException
	{	
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(stream);
		oos.writeObject(ev);
		oos.flush();
		stream.flush();
		
		byte[] buffer = stream.toByteArray();
		
		oos.close();
		stream.close();
		
		for (InetAddress ip : clients.keySet())
		{
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ip, port);
			socket.send(packet);
			clients.put(ip, clients.get(ip).longValue() + counter);
		}
		counter = 0;
		removeInactiveClients();
	}

	private void removeInactiveClients() 
	{
		@SuppressWarnings("unchecked")
		Hashtable<InetAddress, Long> cp = (Hashtable<InetAddress, Long>) clients.clone();
		for (Entry<InetAddress, Long> entry : cp.entrySet())
		{
			if (entry.getValue() > 5000)
			{
				new PlayerLogoutEvent();
				removeClient(entry.getKey());
				System.out.println("Removed Client: " + entry.getKey());
			}
		}
	}

	private void sendPacket(Event ev) throws IOException 
	{
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(stream);
		oos.writeObject(ev);
		oos.flush();
		stream.flush();

		byte[] buffer = stream.toByteArray();

		oos.close();
		stream.close();

		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, serverAddress, socket.getLocalPort());
		socket.send(packet);
	}

	private void sendBroadcastPacket()
	{
		if(packageCounter > 100)
		{
			packageCounter = 0;
			try
			{
				byte[] p = new byte[1];
				socket.setBroadcast(true);
				DatagramPacket packet = new DatagramPacket(p, p.length, InetAddress.getByName("255.255.255.255"), port);
				socket.send(packet);
			} catch (IOException e) {e.printStackTrace();}
		}
		packageCounter++;
	}

	private InetAddress serverAvailable() throws SocketException 
	{
		InetAddress address = null;
		try 
		{
			socket.setSoTimeout(250);
			byte[] buffer = new byte[1000];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			socket.setSoTimeout(20);
			address = packet.getAddress();
		} 
		catch (SocketTimeoutException e)
		{
			socket.setSoTimeout(20);
			return address;
		} catch (IOException e) {e.printStackTrace();}

		return address;
	}
	
	public void addClient(InetAddress address)
	{
		clients.put(address, 0L);
		Network.instance().addPlayer(address);
		new PlayerLoginEvent();
	}
	
	public void removeClient(InetAddress address)
	{
		clients.remove(address);
	}
	
	public boolean contains(InetAddress address)
	{
		return clients.containsKey(address);
	}
	
	public void resetCounter(InetAddress address)
	{
		clients.put(address, 0L);
	}
	
	public boolean isServer()
	{
		return isServer;
	}
}
