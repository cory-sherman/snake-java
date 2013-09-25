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

public class LocalRecording implements SnakeRecording
{
	protected ArrayList<double[]> myRecording;
	protected int myMaxSize;
	
	public LocalRecording()
	{
		myRecording = new ArrayList<double[]>();
		myMaxSize = 0;
	}
	
	public int size()
	{
		return myRecording.size();
	}
	
	public double[] get(int i)
	{
		return i >= size() ? myRecording.get(size() - 1) : myRecording.get(i);
	}
	
	public void add(double[] loc)
	{
		myRecording.add(0, loc);
		if(myRecording.size() > myMaxSize)
			myRecording.remove(size() - 1);
	}
	
	public void clear()
	{
		myRecording.clear();
		setMaxSize(10);
	}
	
	public void addSegment()
	{
		setMaxSize(getMaxSize() + 10);
	}
	
	public int getMaxSize()
	{
		return myMaxSize;
	}
	
	public void setMaxSize(int size)
	{
		myMaxSize = size;
	}
}