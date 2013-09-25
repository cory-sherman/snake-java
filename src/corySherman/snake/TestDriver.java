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
