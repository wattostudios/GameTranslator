////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                            WATTO STUDIOS JAVA PROGRAM TEMPLATE                             //
//                  Template Classes and Helper Utilities for Java Programs                   //
//                                    http://www.watto.org                                    //
//                                                                                            //
//                           Copyright (C) 2004-2008  WATTO Studios                           //
//                                                                                            //
// This program is free software; you can redistribute it and/or modify it under the terms of //
// the GNU General Public License published by the Free Software Foundation; either version 2 //
// of the License, or (at your option) any later versions. This program is distributed in the //
// hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranties //
// of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License //
// at http://www.gnu.org for more details. For updates and information about this program, go //
// to the WATTO Studios website at http://www.watto.org or email watto@watto.org . Thanks! :) //
//                                                                                            //
////////////////////////////////////////////////////////////////////////////////////////////////

package org.watto;

import java.util.TimerTask;
import java.awt.Component;


/**
**********************************************************************************************
A thread that repaints a component every <i>interval</i> milliseconds
**********************************************************************************************
**/
public class RepaintThread extends Thread{

  Component panel;
  boolean running = true;

  /** the time between repaints **/
  int interval = 1000;


/**
**********************************************************************************************
Constructor to build the thread
@param panel the panel to repaint
@param interval the time to wait between repaints
**********************************************************************************************
**/
  public RepaintThread(Component panel, int interval){
    this.panel = panel;
    this.interval = interval;
    }


/**
**********************************************************************************************
Repaints the panel until <code>stopThread()</code> is called. You must call start() instead in
order to run this in a separate thread - <b>do not call run()!</b> See the Thread API for
more information.
**********************************************************************************************
**/
  public void run(){
    while (running){
      panel.repaint();
      try {
        Thread.sleep(interval);
        }
      catch (Throwable t){
        running = false;
        }
      }
    }


/**
**********************************************************************************************
Stops the thread.
**********************************************************************************************
**/
  public synchronized void stopThread(){
    running = false;
    }


  }

