/**
 *Copyright(c) 2010 Cory Sherman (http://www.coryscorner.com)
 *This code may not be redistributed in any way without direct permission from Cory Sherman.
 *
 *@author Cory Sherman
 **/
 
package corySherman.snake;

import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

import java.awt.*;

import java.util.ArrayList;

class SnakePanel extends JPanel
{
	
	protected boolean myOver;
	
	protected SnakeLevel myLevel;
	protected Snake[] mySnakes;
	
	protected Timer myTimer;
	
	protected ArrayList<ChangeListener> myChangeListeners;
	
	protected ArrayList<String> myKeysDown;
	protected ArrayList<Apple> myApples;
	
	public SnakePanel(SnakeLevel level)
	{
		myOver = false;
		myChangeListeners = new ArrayList<ChangeListener>();
		myApples = new ArrayList<Apple>();
		myLevel = level;
		mySnakes = new Snake[2];
		mySnakes[0] = new Snake(this, myLevel, SnakeLevel.PLAYER_ONE, Color.BLUE, 20, 20);
		mySnakes[1] = new Snake(this, myLevel, SnakeLevel.PLAYER_TWO, Color.RED, 20, 20);
		//mySnakes[0].setDelay(0);
		
		myKeysDown = new ArrayList<String>();
		
		addKeyListener(new KeyListener ()
		{
			public void keyPressed(KeyEvent e) 
			{
				if(e.getKeyText(e.getKeyCode()).equals("Escape"))
				{
					myOver = true;
					change();
				}
				else if(e.getKeyText(e.getKeyCode()).equals("P"))
				{
					pause();
				}
				else if(e.getKeyText(e.getKeyCode()).equals("Space"))
				{
					for(Snake s : mySnakes)
						s.addSegment();
				}
				else
					addKey(e.getKeyText(e.getKeyCode()));
			}
			
			public void keyReleased(KeyEvent e) 
			{
				removeKey(e.getKeyText(e.getKeyCode()));
			}
			
			public void keyTyped(KeyEvent e) {}		
		
		});
		
		addApple();
		addApple();
		addApple();
		
		myTimer = new Timer(10, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				for(Snake s : mySnakes)
				{
					s.rotate();
					s.move();
					repaint();
				}
			}
		});
		myTimer.start();
	}
	
	
	public void pause()
	{
		if(myTimer.isRunning())
			myTimer.stop();
		else
			myTimer.start();
	}
	
	public void stop()
	{
		myTimer.stop();
	}
	
	public boolean isOver()
	{
		return myOver;
	}
	
	public Snake[] getSnakes()
	{
		return mySnakes;
	}
	
	public void addChangeListener(ChangeListener cl)
	{
		if(!myChangeListeners.contains(cl))
			myChangeListeners.add(cl);
	}
	
	public void removeChangeListener(ChangeListener cl)
	{
		System.out.println("removed: " + myChangeListeners.remove(cl));
	}
	
	public ArrayList<int[]> getBlankSpots()
	{
		ArrayList<int[]> possibleLocs = myLevel.getBackground();
		ArrayList<Point> possiblePoints = new ArrayList<Point>();
		for(int[] square : possibleLocs)
			possiblePoints.add(new Point(square[1], square[0]));
		
		for(Snake s : mySnakes)
			for(int[] loc : s.getSquaresOccupied())
				possiblePoints.remove(new Point(loc[1], loc[0]));
		for(Apple a : myApples)
			possiblePoints.remove(new Point(a.getX(), a.getY()));
				
		possibleLocs.clear();
		for(Point p : possiblePoints)
			possibleLocs.add(new int[] {(int)p.getY(), (int)p.getX()});
			
		return possibleLocs;
	}
	
	public void addApple()
	{
		ArrayList<int[]> blanks = getBlankSpots();
		Apple a;
		if(Math.random() < .7)
			a = new Apple(blanks.get((int)(Math.random() * blanks.size())));
		else
			a = new PowerUp(blanks.get((int)(Math.random() * blanks.size())));
		myApples.add(a);	
	}
	
	public ArrayList<String> getKeysDown()
	{
		return myKeysDown;
	}
	
	public void printKeys()
	{
		System.out.println(getKeysDown());
	}
	
	protected void addKey(String key)
	{
		if(!myKeysDown.contains(key))
		{
			myKeysDown.add(key);
			//printKeys();
		}
	}
	
	protected void removeKey(String key)
	{
		myKeysDown.remove(key);
		//printKeys();
	}
	
	public void change()
	{
		for(ChangeListener cl : myChangeListeners)
			cl.stateChanged(new ChangeEvent(this));
	}
	
	public int getSquareSize()
	{
		Rectangle bounds = getBounds();
		
		int squareHeight = (int)bounds.getHeight() / myLevel.getRows();
		int squareWidth = (int)bounds.getWidth() / myLevel.getCols();
		
		return (squareHeight < squareWidth) ? squareHeight : squareWidth;
	}
	
	public void hitCheck(Snake snake)
	{
		for(int[] square : snake.getSquaresOccupiedByHead())
			if(myLevel.getElement(square[0], square[1]) == SnakeLevel.WALL_CHAR)
			{
				if(!snake.isPowered())
					snake.respawn();
			}
			else if(myApples.contains(new Apple(square)))
			{
				int i = myApples.indexOf(new Apple(square));
				Apple a = myApples.remove(i);
				if(a instanceof PowerUp)
					((PowerUp)a).power(snake);
				snake.addSegment();
				addApple();
			}
	}
	
	protected void paintComponent(Graphics g)
	{
		this.setBackground(Color.darkGray);
		super.paintComponent(g);
			
		int squareSize = getSquareSize();
		
		for(int r = 0; r < myLevel.getRows(); r++)
			for(int c = 0; c < myLevel.getCols(); c++)
			{
				if(myLevel.getElement(r, c) == SnakeLevel.WALL_CHAR)
					g.setColor(Color.BLACK);
				else
					g.setColor(Color.GRAY);
				g.fillRect(c * squareSize, r * squareSize, squareSize, squareSize);
			}
			
		
		//apples
		for(Apple a : myApples)
		{
			if(a instanceof PowerUp)
				g.setColor(Color.WHITE);
			else
				g.setColor(Color.GREEN);
			g.fillOval(a.getX() * squareSize, a.getY() * squareSize, squareSize, squareSize);
			g.setColor(Color.BLACK);
			g.drawOval(a.getX() * squareSize, a.getY() * squareSize, squareSize, squareSize);
		}
		
		//snakes
		for(Snake s: mySnakes)
		{
			g.setColor(s.getColor());
			g.fillOval((int)(s.getX() * squareSize), (int)(s.getY() * squareSize), squareSize, squareSize);
			g.setColor(Color.BLACK);
			g.drawOval((int)(s.getX() * squareSize), (int)(s.getY() * squareSize), squareSize, squareSize);
			
			
			for(int i = s.getSegments().size() - 1; i >= 0; i--)
			{
				SnakeSegment segment = s.getSegments().get(i);
				g.setColor(segment.getColor());
				g.fillOval((int)(segment.getX() * squareSize), (int)(segment.getY() * squareSize), squareSize, squareSize);
				Color c = Color.BLACK;
				if(s.isPowered())
					if(s.getBoost() - 15 < s.getMaxBoost())
						c = Math.random() < .5 ? Color.WHITE : Color.BLACK;
					else
						c = Color.WHITE;
									
				g.setColor(c);
				g.drawOval((int)(segment.getX() * squareSize), (int)(segment.getY() * squareSize), squareSize, squareSize);
			}
		}
	}
}