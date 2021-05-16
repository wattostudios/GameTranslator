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

package org.watto.event;

import javax.swing.*;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.HyperlinkEvent;


/**
**********************************************************************************************
Listens for click events and passes them to the WSLinkableInterface
**********************************************************************************************
**/
public class WSLinkableListener implements HyperlinkListener{

  /** The interface that handles the event **/
  WSLinkableInterface program;


/**
**********************************************************************************************
Constructor that registers the event-handling <i>program</i>
@param program the interface that handles the event
**********************************************************************************************
**/
  public WSLinkableListener(WSLinkableInterface program){
    this.program = program;
    }


/**
**********************************************************************************************
Calls <code>WSLinkableInterface.onClick(c,e)</code> when the component is clicked
@param e the event that was triggered
**********************************************************************************************
**/
  public void hyperlinkUpdate(HyperlinkEvent e){
    program.onHyperlink((JComponent)e.getSource(),e);
    }


  }