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

class PowerUp extends Apple
{
	public PowerUp(int[] location)
	{
		super(location);
	}
	
	public void power(Snake snake)
	{
		snake.power();
	}
}
