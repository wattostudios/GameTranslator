////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                            WATTO STUDIOS JAVA COMPONENT TOOLKIT                            //
//                    A Collection Of Components To Build Java Applications                   //
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

package org.watto.component;

import org.watto.UndoManager;
import org.watto.UndoTask;
import org.watto.event.WSClickableInterface;
import org.watto.xml.*;

import javax.swing.JComponent;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSUndoComboButton extends WSComboButton implements WSClickableInterface {


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSUndoComboButton(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSUndoComboButton(XMLNode node){
    super();
    buildObject(node);
    registerEvents();
    }



///////////////
//
// Configurable
//
///////////////

/**
**********************************************************************************************
Build this object from the <i>node</i>
@param node the XML node that indicates how to build this object
**********************************************************************************************
**/
  public void buildObject(XMLNode node){
    String code = node.getAttribute("code");
    if (code == null){
      code = "UndoComboButton";
      }

    node.addChild(XMLReader.readString("<WSButton code=\"" + code + "\" opaque=\"false\" showText=\"false\" repository=\"false\" />"));

    super.buildObject(node);

    //if (node.getAttribute("code") == null){
      setCode(code);
    //  }

    addMenuItems();
    }


/**
**********************************************************************************************
Builds an XMLNode that describes this object
@return an XML node with the details of this object
**********************************************************************************************
**/
  public XMLNode buildXML(){
    XMLNode node = WSHelper.buildXML(this);
    return node;
    }



///////////////
//
// Class-Specific Methods
//
///////////////


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void addMenuItems(){
    popupMenu = new WSPopupMenu(new XMLNode(""));

    UndoTask[] tasks = UndoManager.getUndoableTasks();

    for (int i=0;i<tasks.length;i++){
      // add an undo task to the menu
      UndoTask task = tasks[i];
      WSUndoMenuItem item = new WSUndoMenuItem(XMLReader.readString("<WSUndoMenuItem code=\""+task.toString()+"\" />"),task);
      popupMenu.add(item);
      }

    if (tasks.length <= 0){
      // add a disabled item saying that there are no undo tasks
      WSUndoMenuItem item = new WSUndoMenuItem(XMLReader.readString("<WSUndoMenuItem enabled=\"false\" code=\"Empty\" />"));
      popupMenu.add(item);
      }

    }


/**
**********************************************************************************************
The event that is triggered from a WSButtonableListener when a button click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, java.awt.event.MouseEvent e){
    if (c instanceof WSButton){
      String code = ((WSComponent)c).getCode();

      if (code.equals("UndoComboButton")){
        UndoManager.undo();
        return true;
        }
      }

    return super.onClick(c,e);
    }


/**
**********************************************************************************************
Rebuilds the list of recent files from the Settings
**********************************************************************************************
**/
  public void rebuild(){
    //removeAll();
    addMenuItems();
    }




  }