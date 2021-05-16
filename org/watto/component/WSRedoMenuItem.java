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

import org.watto.UndoTask;
import org.watto.xml.XMLNode;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSRedoMenuItem extends WSMenuItem {

  UndoTask task = null;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSRedoMenuItem(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSRedoMenuItem(XMLNode node){
    super();
    buildObject(node);
    registerEvents();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSRedoMenuItem(XMLNode node, UndoTask task){
    this(node);
    setTask(task);
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
    //WSRepository.add(this);
    setOpaque(false);
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




///////////////
//
// Default Implementations
//
///////////////


/**
**********************************************************************************************

**********************************************************************************************
**/
  public UndoTask getTask(){
    return task;
    }


/**
**********************************************************************************************
Gets the text of this component. If the component has a language-dependent text, it will be
returned, otherwise it will return "".
@return the text of this component
**********************************************************************************************
**/
  public String getText(){
    return code;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setTask(UndoTask task){
    this.task = task;
    setCode(task.toString());
    }





  }