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
import org.watto.xml.*;

import javax.swing.JComponent;
import javax.swing.KeyStroke;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSRedoMenu extends WSMenu {


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSRedoMenu(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSRedoMenu(XMLNode node){
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
    WSHelper.buildObject(node,this);

    if (node.getAttribute("code") == null){
      setCode("RedoMenu");
      }

    //WSRepository.add(this);
    setIcons();

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
    UndoTask[] tasks = UndoManager.getRedoableTasks();

    if (tasks.length <= 0){
      // add a disabled item saying that there are no undo tasks
      WSRedoMenuItem item = new WSRedoMenuItem(XMLReader.readString("<WSRedoMenuItem enabled=\"false\" code=\"Empty\" />"));
      add(item);
      return;
      }

    for (int i=0;i<tasks.length;i++){
      // add an undo task to the menu
      UndoTask task = tasks[i];
      WSRedoMenuItem item = new WSRedoMenuItem(XMLReader.readString("<WSRedoMenuItem code=\""+task.toString()+"\" />"),task);
      if (i == 0){
        // Adds CTRL-Y as an accelerator for redoing the last task
        item.setAccelerator(KeyStroke.getKeyStroke("control Y"));
        }
      add(item);
      }
    }


/**
**********************************************************************************************
Rebuilds the list of recent files from the Settings
**********************************************************************************************
**/
  public void rebuild(){
    removeAll();
    addMenuItems();
    }




  }