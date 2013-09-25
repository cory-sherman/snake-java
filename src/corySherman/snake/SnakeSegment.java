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

import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.Color;
import java.awt.geom.Point2D;

import java.util.*;


class SnakeSegment
{
	protected Snake mySnake;
	protected int myCounter;
	protected double[] myLocation;
	protected Color myColor;
	
	
	public SnakeSegment(Snake snake, int counter)
	{
		myCounter = counter;
		mySnake = snake;
		setRandColor();
		myLocation = new double[] {-2, -2};
	}
	
	public boolean isTouching(SnakeSegment other)
	{
		return Point2D.Double.distance(getX(), getY(), other.getX(), other.getY()) < 1;
	}
	
	public int getCounter()
	{
		return myCounter;
	}
	
	public double getX()
	{
		return myLocation[1];
	}
	
	public double getY()
	{
		return myLocation[0];
	}
	
	public void setRandColor()
	{
		Color c = mySnake.getColor();
		
		float[] colors = c.getRGBColorComponents(null);
		for(int i = 0; i < 3; i++)
			colors[i] = (float)(colors[i] * Math.random());
			
		myColor = new Color(colors[0], colors[1], colors[2]);
	}
	
	public void setColor(Color c)
	{
		myColor = c;
	}
	
	public Color getColor()
	{
		ArrayList<Color> colors = new ArrayList<Color>();
		colors.add(mySnake.getColor());
		
		String forward;
		String backward;
		
		switch(mySnake.getNum())
		{
			case 0: forward = "Up"; backward = "Down"; break;
			case 1: default: forward = "W"; backward = "S";
		}
		
		if(mySnake.getBoost() < 5 && (mySnake.getBoost() > 1 * mySnake.getFuelDecrement()) && mySnake.getPanel().myKeysDown.contains(forward))
			colors.add(Color.WHITE);
		
		if(mySnake.getBrakes() < 5 && (mySnake.getBrakes() > 1 * mySnake.getFuelDecrement()) && mySnake.getPanel().myKeysDown.contains(backward))
			colors.add(Color.BLACK);
			
		if(colors.size() > 1)
			return colors.get((int)(Math.random() * colors.size()));
			
		return myColor;
	}
	
	public int[][] getSquaresOccupied()
	{
		int[][] output;
			
		//if circle covers a corner, all 4 possible squares are occupied
		Point2D.Double cornerPoint = new Point2D.Double((int)(getX() + 1), (int)(getY() + 1));
		Point2D.Double circleCenter = new Point2D.Double(getX() + .5, getY() + .5);
		if(cornerPoint.distance(circleCenter) <= .5)
		{
			output = new int[4][2];
			output[0] = new int[] {(int)getY(), (int)getX()};
			output[1] = new int[] {(int)getY(), (int)(getX() + 1)};
			output[2] = new int[] {(int)(getY() + 1), (int)getX()};
			output[3] = new int[] {(int)(getY() + 1), (int)(getX() + 1)};
		}
		//otherwise, only only the squares that lie on the top, bottom, left, and right points of the circle are occupied
		else
		{
			double[][] circleEdges = new double[4][2];
			circleEdges[0] = new double[] {getY() + .5, getX()};
			circleEdges[1] = new double[] {getY() + 1, getX() + .5};
			circleEdges[2] = new double[] {getY() + .5, getX() + 1};
			circleEdges[3] = new double[] {getY(), getX() + .5};
			
			ArrayList<int[]> squares = new ArrayList<int[]>();
			for(double[] edge : circleEdges)
			{
				int[] square = new int[] {(int)edge[0], (int)edge[1]};
				squares.add(square);
			}
			
			output = new int[squares.size()][2];
			int c = 0;
			for(int[] square : squares)
				output[c++] = square;
		}
		
		ArrayList<int[]> full = new ArrayList<int[]>();
		for(int[] square : output)
			full.add(square);
		
		//Snake.removeDuplicates(full);
		
		Iterator iter = full.iterator();
		while(iter.hasNext())
		{
			int[] loc = (int[])iter.next();
			if(loc[0] < 0 || loc[1] < 0)
				iter.remove();	
			else if(loc[0] >= mySnake.getLevel().getRows() || loc[1] >= mySnake.getLevel().getCols())
				iter.remove();
		}
		
		int[][] shortOutput = new int[full.size()][2];
		int c = 0;
		for(int[] square : full)
			shortOutput[c++] = square;
		
		return shortOutput;
	}
	
	public void move()
	{
		int counter = myCounter;
		if(counter >= mySnake.getRecording().size())
			counter = mySnake.getRecording().size() - 1;
			
		myLocation = mySnake.getRecording().get(counter);
	}
	
}
