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
import javax.swing.event.*;

import java.util.*;

import java.io.*;
class SnakeLevel
{
	protected String myName;
	protected int myRows;
	protected int myCols;
	
	protected int[][] myPlayerSpawn;
	
	protected char[][] myLevel;
	
	public static final char WALL_CHAR = 'w';
	public static final char BACKGROUND_CHAR = 'b';
	public static final char PLAYER_ONE_CHAR = 'x';
	public static final char PLAYER_TWO_CHAR = 'y';
	
	public static final int PLAYER_ONE = 0;
	public static final int PLAYER_TWO = 1;
	
	public SnakeLevel (File file)
	{
		myName = file.getName();
		setLevel(file);
	}
	
	public SnakeLevel (String level)
	{
		myName = "Unknown Level";
		setLevel(level);
	}
	
	public SnakeLevel (char[][] level)
	{
		myName = "Unknown Level";
		setLevel(level);
	}
	
	public void setName(String name)
	{
		myName = name;
	}
	
	public String getName()
	{
		return myName;
	}
	
	public ArrayList<int[]> getBackground()
	{
		ArrayList<int[]> output = new ArrayList<int[]>();
		
		for(int r = 0; r < myRows; r++)
			for(int c = 0; c < myCols; c++)
				if(myLevel[r][c] != WALL_CHAR)
					output.add(new int[] {r, c});
					
		return output;
	}
	
	public void setLevel(File file)
	{
		try
		{
			setLevel(new Scanner(file));
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File \"" + file.getName() + "\" is not found in the specified directory: \"" + file.getPath() + "\".");
		}
	}
	
	public void setLevel(String string)
	{
		setLevel(new Scanner(string));
	}
	
	public void setLevel(Scanner scanner)
	{
		char[][] newLevel = new char[scanner.nextInt()][scanner.nextInt()];
		
		int row = 0, col = 0;
		whileLoop:
		while(scanner.hasNext())
		{
			String s = scanner.next();
			char c = s.charAt(0);
			if(c == ';')
			{
				col = 0;
				row++;
				continue whileLoop;
			}
			
			int num = Integer.parseInt(s.substring(1));
			for(int i = 0; i < num; i++)
				newLevel[row][col++] = c;
		}
		
		setLevel(newLevel);
	}
	
	public void setLevel(char[][] level)
	{
		if(level.length != myRows || level[0].length != myCols)
			setSize(level.length, level[0].length);
		myLevel = level;
		
		myPlayerSpawn = new int[][] {null, null};
		findSpawn:
		for(int r = 0; r < myRows; r++)
			for(int c = 0; c < myCols; c++)
			{
				if(myLevel[r][c] == PLAYER_ONE_CHAR)
				{
					myPlayerSpawn[PLAYER_ONE] = new int[] {r, c};
					if(myPlayerSpawn[PLAYER_TWO] != null)
						break findSpawn;
				}
				else if(myLevel[r][c] == PLAYER_TWO_CHAR)
				{
					myPlayerSpawn[PLAYER_TWO] = new int[] {r, c};
					if(myPlayerSpawn[PLAYER_ONE] != null)
						break findSpawn;
				}
			}
	}
	
	public void setSize(int rows, int cols)
	{
		char[][] level = new char[rows][cols];
		
		for(int r = 0; r < rows; r++)
			for(int c = 0; c < cols; c++)
				if(r < myRows && c < myCols)
					level[r][c] = myLevel[r][c];
				else
					level[r][c] = BACKGROUND_CHAR;
		
		myRows = rows;
		myCols = cols;
		setLevel(level);
	}
	
	public int getRows()
	{
		return myRows;
	}
	
	public int getCols()
	{
		return myCols;
	}
	
	public char[][] getLevel()
	{
		return myLevel;
	}
	
	public char getElement(int row, int col)
	{
		if(row < 0 || row >= myRows || col < 0 || col > myCols)
			return ' ';
			
		return myLevel[row][col];
	}
	
	public void setElement(int row, int col, char c)
	{
		myLevel[row][col] = c;
	}
	
	public int[] getPlayerSpawn(int player)
	{
		return myPlayerSpawn[player];
	}
	
	public String getLevelString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(myRows + " " + myCols);
		for(int r = 0; r < myRows; r++)
		{
			sb.append(" ");
			int c = 0;
			while(c < myCols)
			{
				char next = myLevel[r][c];
				int num;
				for(num = 1; c + num < myCols ? myLevel[r][c + num] == next : false; num++) {}
				c += num;
				sb.append("" + next + num + " "); 
			}
			sb.append(";");
		}
		
		return sb.toString();
	}
	
	public String toString()
	{
		return myName;
	}
}
