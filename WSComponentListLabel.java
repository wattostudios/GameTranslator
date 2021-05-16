////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                      GAME TRANSLATOR                                       //
//                            Game Language Translation Assistant                             //
//                                 http://www.watto.org/trans                                 //
//                                                                                            //
//                           Copyright (C) 2006-2009  WATTO Studios                           //
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

import org.watto.component.*;
import org.watto.event.WSEventHandler;
import org.watto.plaf.AquanauticTheme;
import org.watto.xml.*;

import java.awt.BorderLayout;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.plaf.PanelUI;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSComponentListLabel extends WSPanel {

  String code = "";
  boolean selected = false;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSComponentListLabel(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSComponentListLabel(XMLNode node){
    super();
    node.setAttribute("code",""); // so codes aren't overwritten in the WSRepository - this is basically a temp object
    buildObject(node);
    registerEvents();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSComponentListLabel(String code){
    this(new XMLNode(""));
    this.code = code;
    buildLabel();
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
    super.buildObject(node);
    }


/**
**********************************************************************************************
Builds an XMLNode that describes this object
@return an XML node with the details of this object
**********************************************************************************************
**/
  public XMLNode buildXML(){
    XMLNode node = super.buildXML();
    return node;
    }


/**
**********************************************************************************************
Registers the events that this component generates
**********************************************************************************************
**/
  public void registerEvents(){
    enableEvents(
                 AWTEvent.COMPONENT_EVENT_MASK |
                 AWTEvent.CONTAINER_EVENT_MASK |
                 AWTEvent.MOUSE_EVENT_MASK |
                 AWTEvent.MOUSE_MOTION_EVENT_MASK |
                 AWTEvent.HIERARCHY_EVENT_MASK |
                 AWTEvent.HIERARCHY_BOUNDS_EVENT_MASK |
                 AWTEvent.INPUT_METHOD_EVENT_MASK |
                 WSComponent.WS_EVENT_MASK
                 );
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
  public void buildLabel(){

    // label
    WSLabel label = new WSLabel(XMLReader.readString("<WSLabel opaque=\"false\" code=\"ComponentList_" + code + "\" />"));
    label.setForeground(AquanauticTheme.COLOR_TEXT);
    add(label,BorderLayout.CENTER);

    // image
    WSButton button = new WSButton(XMLReader.readString("<WSButton repository=\"false\" code=\"" + code + "\" showText=\"false\" />"));
    add(button,BorderLayout.WEST);
    button.setCode(code);

    }


/**
**********************************************************************************************
Processes the given event
@param event the event that was triggered
**********************************************************************************************
**/
  public void processEvent(AWTEvent event){
    super.processEvent(event); // handles any normal listeners
    WSEventHandler.processEvent(this,event); // passes events to the caller
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSelected(boolean selected){
    this.selected = selected;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isSelected(){
    return selected;
    }


/**
**********************************************************************************************
Overwritten to force use of AquanauticBorderLabelUI
@param ui not used
**********************************************************************************************
**/
  public void setUI(PanelUI ui){
    super.setUI(AquanauticWSComponentListLabelUI.createUI(this));
    }



  }