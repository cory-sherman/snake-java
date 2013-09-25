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
import java.awt.Color;
import java.awt.geom.Point2D;

import java.util.*;


class Apple
{
	protected int[] myLocation;
	
	public Apple(int[] location)
	{
		myLocation = location;
	}
	
	public int getX()
	{
		return myLocation[1];
	}
	
	public int getY()
	{
		return myLocation[0];
	}
	
	public int[] getSquare()
	{
		return myLocation;
	}
	
	public boolean equals(Object other)
	{
		return other instanceof Apple ? ((Apple)other).getX() == getX() && ((Apple)other).getY() == getY()  : false;
	}
}
