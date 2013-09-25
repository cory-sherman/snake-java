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

class TestDriver 
{
	public static void main(String[] args)
	{
		
		File dir = new File("./");
		ArrayList<SnakeLevel> levels = new ArrayList<SnakeLevel>();
		for(String s : dir.list())
		{
			if(s.toLowerCase().endsWith(".snk"))
			{
				SnakeLevel level = new SnakeLevel(new File(s));
				level.setName(s.substring(0, s.lastIndexOf(".")));
				levels.add(level);
			}
		}
		
		SnakeLevel[] levelAr = new SnakeLevel[levels.size()];
		int c = 0;
		for(SnakeLevel l : levels)
			levelAr[c++] = l;
		
		JFrame frame = new JFrame();
		GamePanel gp = new GamePanel(levelAr);
		//frame.add(gp);
		frame.setContentPane(gp);
		//SnakePanel sp = new SnakePanel(new SnakeLevel(new File("c:/level.txt")));
		//frame.add(sp);
		frame.setBounds(100, 100, 600, 600);
		//frame.pack();
		frame.setVisible(true);
		gp.requestFocusInWindow();
	}
}
