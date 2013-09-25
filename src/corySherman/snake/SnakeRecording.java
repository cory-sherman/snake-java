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

public interface SnakeRecording
{
	public int size();
	public double[] get(int i);
	public void addSegment();
	public void add(double[] loc);
	public void clear();
	public void setMaxSize(int i);
}