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

import org.watto.ErrorLogger;
import org.watto.Language;
import org.watto.event.*;
import org.watto.xml.XMLNode;

import java.awt.event.*;
import javax.swing.*;

/**
**********************************************************************************************
A PanelPlugin
**********************************************************************************************
**/
public abstract class WSPanelPlugin extends WSPanel implements WSPlugin,
                                                               WSDoubleClickableInterface,
                                                               WSClickableInterface,
                                                               WSKeyableInterface,
                                                               WSHoverableInterface {


  /** A description of this plugin **/
  String description = "A panel plugin.";


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  public WSPanelPlugin(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
@param caller the object that contains this component, created this component, or more formally,
              the object that receives events from this component.
**********************************************************************************************
**/
  public WSPanelPlugin(XMLNode node){
    super(node);
    }


/**
**********************************************************************************************
Gets the plugin description
**********************************************************************************************
**/
  public String getDescription(){
    String code = getCode();
    if (code == null){
      return description;
      }
    String langCode = getClass().getName() + "_" + code + "_Description";
    if (Language.has(langCode)){
      return Language.get(langCode);
      }
    return description;
    }


/**
**********************************************************************************************
Gets the plugin name
**********************************************************************************************
**/
  public String getText(){
    String name = WSHelper.getText(this);
    if (name.equals("")){
      name = getCode();
      }
    return name;
    }


/**
**********************************************************************************************
The event that is triggered from a WSClickableListener when a click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, MouseEvent e){
    return false;
    }


/**
**********************************************************************************************
The event that is triggered from a WSDoubleClickableListener when a double click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onDoubleClick(JComponent c, MouseEvent e){
    return false;
    }


/**
**********************************************************************************************
The event that is triggered from a WSHoverableListener when the mouse moves over an object
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onHover(JComponent c, MouseEvent e){
    //WSStatusBar.getInstance().setText(c.getToolTipText());
    return false;
    }


/**
**********************************************************************************************
The event that is triggered from a WSHoverableListener when the mouse moves out of an object
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onHoverOut(JComponent c, MouseEvent e){
    //GameExtractor.getInstance().revertText();
    return false;
    }


/**
**********************************************************************************************
The event that is triggered from a WSKeyableListener when a key press occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onKeyPress(JComponent c, KeyEvent e){
    return false;
    }


/**
**********************************************************************************************
Performs any functionality that needs to happen when the panel is to be opened. By default,
it just calls checkLoaded(), but can be overwritten to do anything else needed before the
panel is displayed, such as resetting or refreshing values.
**********************************************************************************************
**/
  public void onOpenRequest(){
    }


/**
**********************************************************************************************
Performs any functionality that needs to happen when the panel is to be closed. This method
does nothing by default, but can be overwritten to do anything else needed before the panel is
closed, such as garbage collecting and closing pointers to temporary objects.
**********************************************************************************************
**/
  public void onCloseRequest(){
    }



/**
**********************************************************************************************
Sets the description of the plugin
@param description the description
**********************************************************************************************
**/
  public void setDescription(String description){
    String langCode = getClass().getName() + "_" + getCode() + "_Description";
    Language.set(langCode,description);
    }


/**
**********************************************************************************************
Sends an error to the ErrorLogger for recording
@param t the error that occurred
**********************************************************************************************
**/
  public static void logError(Throwable t){
    ErrorLogger.log(t);
    }



  }