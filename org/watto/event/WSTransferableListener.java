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

import javax.swing.JComponent;
import javax.swing.TransferHandler;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;

/**
**********************************************************************************************
Listens for transfer events and passes them to the WSTransferableInterface

COMPONENTS THAT SUPPORT DRAGGING...
component.setDragEnabled(true);
component.addMouseMotionListener(new WSTransferableListener(this));

COMPONENTS THAT SUPPORT DROPPING...
component.setTransferHandler(new WSTransferableListener(this));
**********************************************************************************************
**/
public class WSTransferableListener extends TransferHandler implements MouseMotionListener {

  /** The interface that handles the event **/
  WSTransferableInterface program;


/**
**********************************************************************************************
Constructor that registers the event-handling <i>program</i>
@param program the interface that handles the event
**********************************************************************************************
**/
  public WSTransferableListener(WSTransferableInterface program){
    this.program = program;
    }


/**
**********************************************************************************************
Determines whether the component can accept the type of transferable object being moved.
@param c the component being dragged from
@param flavors the type of data being dropped
@return always true - the <code>program.onDrop(c,t)</code> should determine whether the drop is
        allowed or not.
**********************************************************************************************
**/
  public boolean canImport(JComponent c, DataFlavor[] flavors) {
    return true;
    }


/**
**********************************************************************************************
Calls <code>WSTransferableInterface.onDrop(c,t)</code> when a drop occurs
@param c the component to drop on
@param transObjects the objects being dropped
@return true to indicate the drop was successful, even if the drop failed.
**********************************************************************************************
**/
  public boolean importData(JComponent c, Transferable transObjects){
    program.onDrop(transObjects);
    return true;
    }


/**
**********************************************************************************************
Unused
**********************************************************************************************
**/
  public void mouseMoved(MouseEvent e){
    }


/**
**********************************************************************************************
If the <i>program</i> exists, calls <code>WSDragableInterface.onDrag(c,e)</code> when the drag
occurs, otherwise calls <code>TransferHandler.exportAsDrag(c,e,TransferHandler.COPY)</code>
@param e the event that was triggered
**********************************************************************************************
**/
  public void mouseDragged(MouseEvent e){
    JComponent c = (JComponent)e.getSource();
    TransferHandler handler = c.getTransferHandler();

    if (handler == null){
      if (program == null){
        return;
        }
      handler = program.onDrag(c,e);
      }

    handler.exportAsDrag(c, e, TransferHandler.COPY);
    }


  }