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

import java.util.*;

class ScorePanel extends JPanel
{
	protected SnakePanel mySnakePanel;
	
	protected ArrayList<JLabel> myLabels;
	
	public ScorePanel(SnakePanel sp)
	{
		mySnakePanel = sp;
		myLabels = new ArrayList<JLabel>();
		countLabels();
		mySnakePanel.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				repaint();
			}
		});
	}
	
	protected void countLabels()
	{
		if(mySnakePanel.getSnakes().length != myLabels.size())
		{
			myLabels.clear();
			for(Snake snake : mySnakePanel.getSnakes())
			{
				JLabel label = new JLabel("" + snake); //"Player " + snake.getNum() + ": length = " + snake.getLength() + ", score = " + snake.getScore());
				label.setForeground(snake.getColor());
				myLabels.add(label);
			}
			
			removeAll();
			for(JLabel label : myLabels)
				add(label);
		}
	}
	
	protected void updateText()
	{
		countLabels();
		for(int i = 0; i < myLabels.size(); i++)
			myLabels.get(i).setText("" + mySnakePanel.getSnakes()[i]);
				
				/*"Player " + mySnakePanel.getSnakes()[i].getNum() +
				 ": length = " + mySnakePanel.getSnakes()[i].getLength() +
				 ", score = " + mySnakePanel.getSnakes()[i].getScore());*/
	}
	
	public void paintComponent(Graphics g)
	{
		updateText();
		super.paintComponent(g);
	}
	
}