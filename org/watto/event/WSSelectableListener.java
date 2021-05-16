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

import java.awt.event.*;
import javax.swing.JComponent;
import javax.swing.event.*;

/**
**********************************************************************************************
Listens for selection events and passes them to the WSSelectableInterface
**********************************************************************************************
**/
public class WSSelectableListener implements ActionListener,
                                             ListSelectionListener,
                                             ItemListener,
                                             TreeSelectionListener{

  /** The interface that handles the event **/
  WSSelectableInterface program;


/**
**********************************************************************************************
Constructor that registers the event-handling <i>program</i>
@param program the interface that handles the event
**********************************************************************************************
**/
  public WSSelectableListener(WSSelectableInterface program){
    this.program = program;
    }


/////
// ITEM LISTENER
/////

/**
**********************************************************************************************
Calls <code>WSSelectableInterface.onSelect(c,e)</code> when an item is selected, or calls
<code>WSSelectableInterface.onDeselect(c,e)</code> when an item is deselected. This is
specific for components with ItemListeners
@param e the event that was triggered
**********************************************************************************************
**/
  public void itemStateChanged(ItemEvent e){
    if (e.getStateChange() == ItemEvent.SELECTED){
      program.onSelect((JComponent)e.getSource(),e);
      }
    else {
      program.onDeselect((JComponent)e.getSource(),e);
      }
    }


/////
// LIST SELECTION LISTENER
/////

/**
**********************************************************************************************
Calls <code>WSSelectableInterface.onSelect(c,e)</code> when an item is selected. This is
specific for components with ListSelectionListeners
@param e the event that was triggered
**********************************************************************************************
**/
  public void valueChanged(ListSelectionEvent e){
    if (! e.getValueIsAdjusting()){
      program.onSelect((JComponent)e.getSource(),e);
      }
    }


/////
// TREE SELECTION LISTENER
/////

/**
**********************************************************************************************
Calls <code>WSSelectableInterface.onSelect(c,e)</code> when an item is selected, or calls
<code>WSSelectableInterface.onDeselect(c,e)</code> when an item is deselected. This is
specific for components with TreeSelectionListeners
@param e the event that was triggered
**********************************************************************************************
**/
  public void valueChanged(TreeSelectionEvent e){
    if (e.isAddedPath()){
      program.onSelect((JComponent)e.getSource(),e);
      }
    else {
      program.onDeselect((JComponent)e.getSource(),e);
      }
    }


/////
// ACTION LISTENER
/////

/**
**********************************************************************************************
Calls <code>WSSelectableInterface.onSelect(c,e)</code> when an item is selected. This is
specific for components with ActionListeners
@param e the event that was triggered
**********************************************************************************************
**/
  public void actionPerformed(ActionEvent e){
    program.onSelect((JComponent)e.getSource(),e);
    }


  }