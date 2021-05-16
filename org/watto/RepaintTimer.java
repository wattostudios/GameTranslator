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
Repaints a component whenever this event is called. To be used with a java.util.Timer
**********************************************************************************************
**/
public class RepaintTimer extends TimerTask{

  Component panel;


/**
**********************************************************************************************
Constructor to build the timer
@param panel the panel to repaint
**********************************************************************************************
**/
  public RepaintTimer(Component panel){
    this.panel = panel;
    }


/**
**********************************************************************************************
Repaints the panel at every timer trigger.
**********************************************************************************************
**/
  public void run(){
    panel.repaint();
    }


  }

