package Model.Network;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import Controller.Event.Event;
import Controller.Event.PlayerLoginEvent;
import Controller.Controller;
import Main.Game;

public class Receiver implements Runnable
{
	private DatagramSocket socket;
	private Server server;
	private InetAddress local;
	
	public Receiver(DatagramSocket socket, Server server)
	{
		this.socket = socket;
		this.server = server;
		local = Game.instance().getIp();
	}
	
	@Override
	public void run()
	{
		while(Game.instance().isRunning())
		{
			byte[] buffer = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

			Event event = null;

			try
			{
				socket.receive(packet);
				
				if (packet.getLength() < 10) 
				{
					continue;
				}
				
				ByteArrayInputStream s = new ByteArrayInputStream(buffer);
				ObjectInputStream ois = new ObjectInputStream(s);
				event = (Event) ois.readObject();
				ois.close();
				s.close();
			}
			catch (SocketTimeoutException e){continue;}
			catch (IOException e) {e.printStackTrace();} 
			catch (ClassNotFoundException e) {e.printStackTrace();}
			
			if(!Network.instance().isServer())
			{
				event.shouldBeSentOverServer(false);
			}
			
			if (!event.getAddress().equals(local))
			{
				Controller.instance().addEvent(event);
				InetAddress ad = packet.getAddress();

				if (event instanceof PlayerLoginEvent && server.isServer() && !server.contains(ad))
				{
					PlayerLoginEvent e = (PlayerLoginEvent) event;
					server.addClient(ad);
					System.out.println("Added client: " + e.getAddress());
				}
				else
				{
					server.resetCounter(ad);
				}
			}
		}
	}
}