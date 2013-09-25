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

import java.util.ArrayList;

public class LocalRecording implements SnakeRecording
{
    protected ArrayList<double[]> myRecording;
    protected int myMaxSize;

    public LocalRecording()
    {
        myRecording = new ArrayList<double[]>();
        myMaxSize = 0;
    }

    public int size()
    {
        return myRecording.size();
    }

    public double[] get(int i)
    {
        return i >= size() ? myRecording.get(size() - 1) : myRecording.get(i);
    }

    public void add(double[] loc)
    {
        myRecording.add(0, loc);
        if(myRecording.size() > myMaxSize)
            myRecording.remove(size() - 1);
    }

    public void clear()
    {
        myRecording.clear();
        setMaxSize(10);
    }

    public void addSegment()
    {
        setMaxSize(getMaxSize() + 10);
    }

    public int getMaxSize()
    {
        return myMaxSize;
    }

    public void setMaxSize(int size)
    {
        myMaxSize = size;
    }
}