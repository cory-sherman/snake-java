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