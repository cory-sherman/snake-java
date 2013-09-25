/*
The MIT License (MIT)

Copyright (c) 2013 Cory Sherman <cory@coryscorner.com>

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

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