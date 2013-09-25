/**
 *Copyright(c) 2010 Cory Sherman (http://www.coryscorner.com)
 *This code may not be redistributed in any way without direct permission from Cory Sherman.
 *
 *@author Cory Sherman
 **/
 
package corySherman.snake;

import javax.swing.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import java.io.*;

import java.util.*;

public class SnakeApplet extends JApplet 
{
	static final SnakeLevel[] levelAr = getLevels();
	
	public void init()
	{
		GamePanel gp = new GamePanel(levelAr);
		setContentPane(gp);
		setVisible(true);
		gp.requestFocusInWindow();
	}
	
	public static SnakeLevel[] getLevels()
	{
		ArrayList<String[]> stringAr = new ArrayList<String[]>();
		
		stringAr.add(new String[] {"60 80 w80 ; w1 b78 w1 ; w1 b78 w1 ; w1 b78 w1 " + 
			"; w1 b78 w1 ; w1 b78 w1 ; w1 b78 w1 ; w1 b35 w" + 
			"8 b35 w1 ; w1 b13 w1 b21 w1 b6 w1 b21 w1 b13 w" + 
			"1 ; w1 b13 w1 b50 w1 b13 w1 ; w1 b13 w1 b50 w1" + 
			" b13 w1 ; w1 b13 w1 b50 w1 b13 w1 ; w1 b13 w1 " + 
			"b50 w1 b13 w1 ; w1 b13 w1 b50 w1 b13 w1 ; w1 b" + 
			"13 w1 b24 w2 b24 w1 b13 w1 ; w1 b13 w1 b24 w2 " + 
			"b24 w1 b13 w1 ; w1 b13 w2 b23 w2 b23 w2 b13 w1" + 
			" ; w1 b15 w15 b8 w2 b8 w15 b15 w1 ; w1 b78 w1 " + 
			"; w1 b78 w1 ; w1 b78 w1 ; w1 b33 w1 b10 w1 b33" + 
			" w1 ; w1 b33 w1 b10 w1 b33 w1 ; w1 b33 w1 b10 " + 
			"w1 b33 w1 ; w1 b33 w1 b10 w1 b33 w1 ; w1 b33 w" + 
			"1 b10 w1 b33 w1 ; w1 b78 w1 ; w1 b13 y1 b64 w1" + 
			" ; w1 b54 x1 b23 w1 ; w1 b78 w1 ; w1 b78 w1 ; " + 
			"w1 b78 w1 ; w1 b78 w1 ; w1 b78 w1 ; w1 b33 w1 " + 
			"b10 w1 b33 w1 ; w1 b33 w1 b10 w1 b33 w1 ; w1 b" + 
			"33 w1 b10 w1 b33 w1 ; w1 b33 w1 b10 w1 b33 w1 " + 
			"; w1 b33 w1 b10 w1 b33 w1 ; w1 b78 w1 ; w1 b78" + 
			" w1 ; w1 b78 w1 ; w1 b15 w15 b8 w2 b8 w15 b15 " + 
			"w1 ; w1 b13 w2 b23 w2 b23 w2 b13 w1 ; w1 b13 w" + 
			"1 b24 w2 b24 w1 b13 w1 ; w1 b13 w1 b24 w2 b24 " + 
			"w1 b13 w1 ; w1 b13 w1 b50 w1 b13 w1 ; w1 b13 w" + 
			"1 b50 w1 b13 w1 ; w1 b13 w1 b50 w1 b13 w1 ; w1" + 
			" b13 w1 b50 w1 b13 w1 ; w1 b13 w1 b50 w1 b13 w" + 
			"1 ; w1 b13 w1 b21 w1 b6 w1 b21 w1 b13 w1 ; w1 " + 
			"b35 w8 b35 w1 ; w1 b78 w1 ; w1 b78 w1 ; w1 b78" + 
			" w1 ; w1 b78 w1 ; w1 b78 w1 ; w1 b78 w1 ; w80 ;", 
			"Easy"});
		stringAr.add(new String[] {"30 60 b8 w2 b11 w3 b12 w3 b11 w2 b8 ; b60 ; b60" + 
			" ; b18 w1 b7 w1 b6 w1 b7 w1 b18 ; b3 w4 b4 w4 " + 
			"b3 w1 b7 w1 b6 w1 b7 w1 b3 w4 b4 w4 b3 ; b3 w1" + 
			" b14 w1 b7 w1 b6 w1 b7 w1 b14 w1 b3 ; b3 w1 b1" + 
			"4 w1 b7 w1 b6 w1 b7 w1 b14 w1 b3 ; b3 w1 b14 w" + 
			"1 b7 w1 b6 w1 b7 w1 b14 w1 b3 ; b18 w1 b7 w1 b" + 
			"6 w1 b7 w1 b18 ; b18 w1 b7 w1 b6 w1 b7 w1 b18 " + 
			"; w1 b6 w7 b32 w7 b6 w1 ; w1 b6 w1 b14 w1 b6 w" + 
			"2 b6 w1 b14 w1 b6 w1 ; w1 b6 w1 b14 w1 b6 w2 b" + 
			"6 w1 b14 w1 b6 w1 ; w1 b6 w1 b14 w1 b6 w2 b6 w" + 
			"1 b14 w1 b6 w1 ; w1 b6 w1 b5 w1 b3 x1 b4 w1 b6" + 
			" w2 b6 w1 b4 y1 b3 w1 b5 w1 b6 w1 ; w1 b6 w1 b" + 
			"5 w1 b8 w1 b6 w2 b6 w1 b8 w1 b5 w1 b6 w1 ; w1 " + 
			"b12 w1 b8 w1 b6 w2 b6 w1 b8 w1 b12 w1 ; w1 b12" + 
			" w1 b8 w1 b6 w2 b6 w1 b8 w1 b12 w1 ; w1 b12 w1" + 
			" b8 w1 b6 w2 b6 w1 b8 w1 b12 w1 ; w1 b6 w7 b32" + 
			" w7 b6 w1 ; b18 w1 b7 w1 b6 w1 b7 w1 b18 ; b18" + 
			" w1 b7 w1 b6 w1 b7 w1 b18 ; b3 w1 b14 w1 b7 w1" + 
			" b6 w1 b7 w1 b14 w1 b3 ; b3 w1 b14 w1 b7 w1 b6" + 
			" w1 b7 w1 b14 w1 b3 ; b3 w1 b14 w1 b7 w1 b6 w1" + 
			" b7 w1 b14 w1 b3 ; b3 w4 b4 w4 b3 w1 b7 w1 b6 " + 
			"w1 b7 w1 b3 w4 b4 w4 b3 ; b18 w1 b7 w1 b6 w1 b" + 
			"7 w1 b18 ; b60 ; b60 ; b8 w2 b11 w3 b12 w3 b11" + 
			" w2 b8 ;", "Challenging"});
		
		SnakeLevel[] levelAr = new SnakeLevel[stringAr.size()];
		int c = 0;
		for(String[] str : stringAr)
		{
			SnakeLevel level = new SnakeLevel(str[0]);
			level.setName(str[1]);
			levelAr[c++] = level;
		}
		
		return levelAr;
	}
}