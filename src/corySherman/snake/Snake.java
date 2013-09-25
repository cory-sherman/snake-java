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
import java.awt.Point;
import javax.swing.event.*;
import java.awt.Color;

import java.awt.geom.Point2D;

import java.util.ArrayList;


class Snake
{
    protected final double FORWARD_DISTANCE = .1;

    protected double myFuelIncrement;
    protected double myFuelDecrement;

    protected SnakeLevel myLevel;
    protected SnakePanel myPanel;
    protected Color myColor;
    protected double myDirection;
    protected int myNumber;
    protected double[] myLocation;
    protected int mySize;
    //protected Timer myTimer;

    protected double myBoost;
    protected double myBrakes;

    protected int myMaxBoost;
    protected int myMaxBrakes;

    protected boolean myPower;

    protected int myScore;

    protected ArrayList<SnakeSegment> mySegments;

    public SnakeRecording myRecording;

    public Snake(SnakePanel panel, SnakeLevel level, int player, Color c)
    {
        this(panel, level, player, c, -1, -1);
    }

    public Snake(SnakePanel panel, SnakeLevel level, int player, Color c, int boost, int brakes)
    {

        myRecording = new LocalRecording();
        myPower = false;
        setBoost(boost);
        setMaxBoost(boost);
        setBrakes(brakes);
        setMaxBrakes(brakes);
        myFuelDecrement = .1;
        myFuelIncrement = .01;
        setSize(0);
        myScore = 0;
        myNumber = player;
        myPanel = panel;
        myLevel = level;
        myColor = c;
        mySegments = new ArrayList<SnakeSegment>();
        /*
        myTimer = new Timer(10, new ActionListener ()
        {
            public void actionPerformed(ActionEvent e)
            {
                rotate();
                move();
            }
        });
        */

        respawn();
    }

    public static void removeDuplicates(ArrayList<int[]> source)
    {
        ArrayList<Point> point = new ArrayList<Point>();
        for(int[] square : source)
            point.add(new Point(square[1], square[0]));
        int i = 0;
        while(i < point.size())
        {
            if(point.lastIndexOf(point.get(i)) != i)
            {
                point.remove(i);
                source.remove(i);
            }
            else
                i++;
        }
    }

    public void power()
    {
        myPower = true;
        myBoost = myMaxBoost + 60 + (myMaxBoost == -1 ? 1 : 0);
        myBrakes = myMaxBrakes + 60  + (myMaxBrakes == -1 ? 1 : 0);
    }

    public void dePower()
    {
        myPower = false;
    }

    public boolean isPowered()
    {
        return myPower;
    }

    public void setSize(int size)
    {
        mySize = size;
        myRecording.setMaxSize(10 * mySize);
    }

    public double getFuelIncrement()
    {
        return myFuelIncrement;
    }

    public double getFuelDecrement()
    {
        return myFuelDecrement;
    }

    public SnakePanel getPanel()
    {
        return myPanel;
    }

    public double getBoost()
    {
        return myBoost;
    }

    public void setBoost(int boost)
    {
        myBoost = boost;
    }

    public int getMaxBoost()
    {
        return myMaxBoost;
    }

    public void setMaxBoost(int boost)
    {
        myMaxBoost = boost;
        if(myMaxBoost < myBoost)
            myBoost = myMaxBoost;
    }

    public double getBrakes()
    {
        return myBrakes;
    }

    public void setBrakes(int brakes)
    {
        myBrakes = brakes;
    }

    public int getMaxBrakes()
    {
        return myMaxBrakes;
    }

    public void setMaxBrakes(int brakes)
    {
        myMaxBrakes = brakes;
        if(myMaxBrakes < myBrakes)
            myBrakes = myMaxBrakes;
    }

    public void setFuelIncrement(double increment)
    {
        myFuelIncrement = increment;
    }

    public void setFuelDecrement(double decrement)
    {
        myFuelDecrement = decrement;
    }

    public void respawn()
    {
        if(isPowered())
            return;
        mySegments.clear();
        setSize(0);
        myLocation = new double[] {myLevel.getPlayerSpawn(myNumber)[0], myLevel.getPlayerSpawn(myNumber)[1]};
        myRecording.clear();
        addSegment();
        myRecording.addSegment();
        mySegments.get(0).setColor(myColor);
    }

    /*

    public void start()
    {
        myTimer.start();
    }

    public void stop()
    {
        myTimer.stop();
    }
    */

    public int getLength()
    {
        return mySize;
    }

    public int getNum()
    {
        return myNumber;
    }

    public SnakeLevel getLevel()
    {
        return myLevel;
    }

    public void addSegment()
    {
        myScore += mySize;
        mySegments.add(new SnakeSegment(this, 10 * mySize));
        setSize(mySize + 1);
        myPanel.change();
    }

    public int getScore()
    {
        return myScore;
    }

    public ArrayList<SnakeSegment> getSegments()
    {
        return mySegments;
    }

    /*

    public void setDelay(int delay)
    {
        myTimer.setDelay(delay);
    }

    public double getDelay()
    {
        return myTimer.getDelay();
    }

    */

    public Color getColor()
    {
        return myColor;
    }

    public double[] getLocation()
    {
        return myLocation;
    }

    public double getX()
    {
        return myLocation[1];
    }

    public double getY()
    {
        return myLocation[0];
    }

    public int[][] getSquaresOccupiedByHead()
    {
        return mySegments.get(0).getSquaresOccupied();
    }

    public int[][] getSquaresOccupied()
    {
        ArrayList<int[]> output = new ArrayList<int[]>();
        //for(int[] square : getSquaresOccupiedByHead())
        //    output.add(square);

        for(SnakeSegment segment : mySegments)
            for(int[] square : segment.getSquaresOccupied())
                    output.add(square);

        Snake.removeDuplicates(output);

        int[][] outputArray = new int[output.size()][2];
        int c = 0;
        for(int[] square : output)
            outputArray[c++] = square;
        return outputArray;
    }

    public double getDirection()
    {
        return myDirection;
    }

    public SnakeRecording getRecording()
    {
        return myRecording;
    }

    public void rotate()
    {
        String left;
        String right;

        switch(myNumber)
        {
            case 0: left = "Left"; right = "Right"; break;
            case 1: default: left = "A"; right = "D";
        }

        int add = 0;
        if(myPanel.getKeysDown().contains(left))
            add--;
        if(myPanel.getKeysDown().contains(right))
            add++;
        myDirection += 2 * add;
        if(myDirection > 360 || myDirection < 0)
            myDirection %= 360;
    }

    public void move()
    {
        rotate();

        String forward;
        String backward;

        switch(myNumber)
        {
            case 0: forward = "Up"; backward = "Down"; break;
            case 1: default: forward = "W"; backward = "S";
        }

        double forwardDistance = FORWARD_DISTANCE;

        if(!myPower)
        {
            if(myBoost < myMaxBoost && myMaxBoost != -1)
                myBoost += myFuelIncrement;

            if(myBrakes < myMaxBrakes && myMaxBrakes != -1)
                myBrakes += myFuelIncrement;
        }

        if(myPanel.getKeysDown().contains(forward))
            if(myBoost > 0 || Math.round(myBoost) == -1)
            {
                forwardDistance *= 2;
                if(!myPower)
                    if(myBoost > 0)
                        myBoost -= myFuelDecrement;
            }
        if(myPanel.getKeysDown().contains(backward))
            if(myBrakes > 0 || Math.round(myBrakes) == -1)
            {
                forwardDistance /= 2;
                if(!myPower)
                    if(myBrakes > 0)
                        myBrakes -= myFuelDecrement;
            }

        if(myPower)
        {
            myBoost -= myFuelDecrement;
            myBrakes -= myFuelDecrement;
            if(myBoost <= myMaxBoost)
                dePower();
        }

        myPanel.change();
        myLocation[0] -= Math.cos(Math.toRadians(myDirection)) * forwardDistance;
        myLocation[1] += Math.sin(Math.toRadians(myDirection)) * forwardDistance;

        if(myLocation[0] + .5 < 0)
            myLocation[0] = myLevel.getRows() - .5;
        if(myLocation[1] + .5 < 0)
            myLocation[1] = myLevel.getCols() - .5;
        if(myLocation[0] + .5 > myLevel.getRows())
            myLocation[0] = -.5;
        if(myLocation[1] + .5 > myLevel.getCols())
            myLocation[1] = -.5;

        myRecording.add(new double[] {myLocation[0], myLocation[1]});

        boolean respawn = false;
        int c = 0;

        hitSelfCheck:
        for(SnakeSegment s : mySegments)
        {
            s.move();
            if(c++ > 6)
                if(s.isTouching(mySegments.get(0)))
                {
                    respawn = true;
                }
        }
        if(respawn)
            respawn();

        hitCheck:
        for(Snake snake : myPanel.getSnakes())
        {
            if(snake != this)
            {
                //if head hits other head
                if(mySegments.get(0).isTouching(snake.getSegments().get(0)))
                {
                    snake.respawn();
                    respawn();
                    break hitCheck;
                }

                //if head hits other body
                for(SnakeSegment seg : snake.getSegments())
                {
                    if(seg.isTouching(mySegments.get(0)))
                    {
                        respawn();
                        break hitCheck;
                    }
                }
            }
        }

        myPanel.hitCheck(this);
    }

    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[Score: ");
        sb.append("" + myScore);
        sb.append("] [Boost: ");
        sb.append(myBoost == -1 ? "\u221e" : "" + (int)(myBoost + .5));
        sb.append("] [Brakes: ");
        sb.append(myBrakes == -1 ? "\u221e" : "" + (int)(myBrakes +.5));
        sb.append("]");
        return sb.toString();
    }

}
