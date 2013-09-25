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

import java.awt.*;

import java.util.*;

class GamePanel extends JPanel
{
	protected LevelChooser myLevelChooser;
	protected LevelLoader myLevelLoader;
	protected SnakeLevel myLevel;
	
	protected final String INSTRUCTIONS = "Instructions:\nObjective: Move your snake around to collect apples before the other player can.\n\n" +
		"Points: When you collect an apple, you get points based on your current length. The first apple you collect gives you one point, " +
		" and the points you get per apple grow along with your snake. White apples are power-ups, which make you invincible (go through walls) and give you unlimited boost/brake for a period of time.\n\n" +
		"Death: When the head of your snake comes in contact with another snake, your own snake, or a wall, you die. " +
		"Luckily, your death shall be quick and painless, and a new snake will be born instantly. The new snake will be only be one segment, so " +
		"apples earn you only one point.\n\n" +
		"Controls:     Exit: Escape Key,     Pause: 'P' Key,     addSegment (for testing, but fun): Spacebar.\n" +
		"Player 1 (Blue):     Turn Left: Left Arrow,     Turn Right: Right Arrow,     Boost: Up Arrow,     Slow: Down Arrow.\n" +
		"Player 2 (Red):     Turn Left: 'A' Key,     Turn Right: 'D' Key,     Boost: 'W' Key,     Slow: 'S' Key.\n\n" +
		"Boost/Brakes: You have a limited (but constantly replenishing) supply of boost and brakes available. " +
		"When your boost/brakes are being used but running low, your snake will flicker to warn you that you are about to run out and return to normal speed." +
		"When your power-up is about to run out, your snake's outline will flicker.";
	
	public GamePanel()
	{
		this(new SnakeLevel[0]);
	}
	
	public GamePanel(SnakeLevel[] levels)
	{
		myLevel = null;
		myLevelChooser = new LevelChooser(this, levels);
		myLevelLoader = new LevelLoader(this, myLevelChooser);
		showInstructions();
	}
	
	public void showInstructions()
	{
		removeAll();
		setLayout(new BorderLayout());
		JTextArea text = new JTextArea(INSTRUCTIONS);
		text.setEditable(false);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		JButton okay = new JButton("Okay");
		okay.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				chooseLevel();
			}
		});
		
		add(text, BorderLayout.CENTER);
		add(okay, BorderLayout.PAGE_END);
		validate();
		repaint();
	}
	
	public void loadLevel()
	{
		removeAll();
		setLayout(new BorderLayout());
		add(myLevelLoader, BorderLayout.CENTER);
		validate();
		repaint();
	}
	
	public void chooseLevel()
	{
		removeAll();
		setLayout(new BorderLayout());
		add(myLevelChooser, BorderLayout.CENTER);
		//myLevelChooser.setVisible(true);
		validate();
		repaint();
	}
	
	public void play()
	{
		removeAll();
		final SnakePanel sp = new SnakePanel(myLevel);
		/*
		for(Snake s : sp.getSnakes())
		{
			s.setMaxBoost(-1);
			s.setMaxBrakes(-1);
		}
		*/
		ScorePanel score = new ScorePanel(sp);
		
		sp.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if(sp.isOver())
				{
					sp.stop();
					chooseLevel();
				}
			}
		});
		
		setLayout(new BorderLayout());
		add(score, BorderLayout.PAGE_START);
		add(sp, BorderLayout.CENTER);
		sp.requestFocusInWindow();
		validate();
		repaint();
	}
	
	public void setLevel(SnakeLevel level)
	{
		myLevel = level;
	}
	
	private void printComp()
	{
		for(Component c : getComponents())
		{
			System.out.println("" + c.getClass());
		}
		System.out.println();
	}
		
}

class LevelChooser extends JPanel
{
	private SnakeLevel[] myLevels;
	private GamePanel myGame;
	
	private final JList myList;
	
	public LevelChooser(GamePanel game, SnakeLevel[] levels)
	{
		myGame = game;
		myLevels = levels;
		myList = new JList(myLevels);
		myList.setLayoutOrientation(JList.VERTICAL);
		myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//list.setVisibleRowCount(-1);
		
		JScrollPane scroll = new JScrollPane(myList);
		scroll.setAlignmentX(LEFT_ALIGNMENT);
		setLayout(new BorderLayout());
		add(scroll, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		JButton loadButton = new JButton("Load");
		JButton newButton = new JButton("New");
		buttonPanel.add(loadButton);
		buttonPanel.add(newButton);
		add(buttonPanel, BorderLayout.PAGE_END);
		
		
		loadButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				myGame.setLevel(myLevels[myList.getSelectedIndex()]);
				myGame.play();
			}
		});
		
		newButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				myGame.loadLevel();
			}
		});
	}
	
	public void addLevel(SnakeLevel level)
	{
		SnakeLevel[] newList = new SnakeLevel[myLevels.length + 1];
		System.arraycopy(myLevels, 0, newList, 0, myLevels.length);
		newList[newList.length - 1] = level;
		myLevels = newList;
		myList.setListData(myLevels);
	}
}

class LevelLoader extends JPanel
{
	protected LevelChooser myLevelChooser;
	protected GamePanel myGame;
	
	public LevelLoader(GamePanel gp, LevelChooser lc)
	{
		myGame = gp;
		myLevelChooser = lc;
		
		final JTextField name = new JTextField("Level Name");
		final JTextArea text = new JTextArea("Paste Level Here");
		JPanel buttons = new JPanel();
		JButton load = new JButton("Load");
		load.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SnakeLevel level = new SnakeLevel(text.getText());
				level.setName(name.getText());
				myLevelChooser.addLevel(level);
				myGame.chooseLevel();
			}
		});
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				myGame.chooseLevel();
			}
		});
		
		text.setSelectionEnd(text.getText().length());
		
		buttons.setLayout(new FlowLayout());
		buttons.add(load);
		buttons.add(cancel);
		
		
		setLayout(new BorderLayout());
		add(text, BorderLayout.CENTER);
		add(name, BorderLayout.PAGE_START);
		add(buttons, BorderLayout.PAGE_END);
	}
}