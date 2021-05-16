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

import java.awt.event.MouseEvent;
import java.awt.datatransfer.Transferable;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
**********************************************************************************************
An interface that accepts button click events from a WSTransferableListener

COMPONENTS THAT SUPPORT DRAGGING...
component.setDragEnabled(true);
component.addMouseMotionListener(new WSTransferableListener(this));

COMPONENTS THAT SUPPORT DROPPING...
component.setTransferHandler(new WSTransferableListener(this));
**********************************************************************************************
**/
public interface WSTransferableInterface{


/**
**********************************************************************************************
Creates a TransferHandler for this component, allowing it to be dragged.
@param c the component that will be dragged
@param e the dragging event
@return the TransferHandler for this component
**********************************************************************************************
**/
  public TransferHandler onDrag(JComponent c, MouseEvent e);


/**
**********************************************************************************************
Drops the transferable object from the component
@param t the transferred data
@return true if the event was handled
**********************************************************************************************
**/
  public boolean onDrop(Transferable t);


  }