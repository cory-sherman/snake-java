/**
 *Copyright(c) 2010 Cory Sherman (http://www.coryscorner.com)
 *This code may not be redistributed in any way without direct permission from Cory Sherman.
 *
 *@author Cory Sherman
 **/
 
package corySherman.snake;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

class ServerSnake extends Thread
{
	protected final boolean DEBUG = true;
	
	protected static final int DEFAULT_PORT = 9000;
	
	protected int myPort;
	protected ServerSocket mySocket;
	protected ArrayList<SnakeOnline> myGames;
	
	public ServerSnake(int port)
	{
		myPort = port;
		myGames = new ArrayList<SnakeOnline>();
		try
		{
			mySocket = new ServerSocket(myPort);
		}
		catch(IOException e)
		{
			System.err.println(e.getMessage() + "\n" + e.getStackTrace());
		}
		
		if(DEBUG)
			System.out.println("SnakeServer listening on port " + myPort);
		
		this.start();
	}
	
	public static void main(String[] args)
	{
		if(args.length == 0)
			args = new String[] {"" + DEFAULT_PORT};
		
		for(String arg : args)
			(new ServerSnake(Integer.parseInt(arg))).run();
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				Socket client = mySocket.accept();
				SnakeOnline game = null;
				findGame:
				for(SnakeOnline g : myGames)
					if(g.getPlayersNeeded() > 0)
					{
						game = g;
						break findGame;
					}
				if(game == null)
				{
					game = new SnakeOnline();
					myGames.add(game);
				}
				
				System.out.println("game = " + game);
				
				game.addPlayer(client);
			}
		}
		catch(IOException e)
		{
			System.err.println(e.getMessage() + "\n" + e.getStackTrace());
		}
	}
}

class SnakeOnline extends Thread
{
	protected int myReqPlayers;
	protected ArrayList<Socket> myPlayers;
	
	public SnakeOnline()
	{
		myReqPlayers = 2;
		myPlayers = new ArrayList<Socket>();
		this.start();
	}
	
	public void run()
	{
		try
		{
			while(true)
			{
				int p = 0;
				for(Socket s : myPlayers)
				{
					p++;
					//DataInputStream in = new DataInputStream(s.getInputStream());
					//PrintStream out = new PrintStream(s.getOutputStream());
					
					//String line = null;
					String line = (new BufferedReader(new InputStreamReader(s.getInputStream()))).readLine();
					if(line == null)
						continue;
					System.out.println("Player " + p + " said \"" + line + "\"");
					//throw new IOException();
				}
			}
		}
		catch(IOException e)
		{
			System.err.println(e.getMessage() + "\n" + e.getStackTrace());
		}
	}
	
	public boolean addPlayer(Socket s)
	{
		if(myPlayers.size() < myReqPlayers)
		{
			myPlayers.add(s);
			System.out.println("new player connected!");
			return true;
		}
		return false;
	}
	
	public int getPlayersNeeded()
	{
		return myReqPlayers - myPlayers.size();
	}
}