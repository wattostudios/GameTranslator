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
import org.watto.event.WSClickableInterface;
import org.watto.xml.XMLNode;
import org.watto.xml.XMLReader;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.JComponent;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSComboButton extends WSPanel implements WSClickableInterface {


  WSButton mainButton;
  WSButton arrowButton;

  WSPopupMenu popupMenu;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSComboButton(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSComboButton(XMLNode node){
    super(node);
    }



///////////////
//
// Class-Specific Methods
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

    if (node.getAttribute("opaque") == null){
      setOpaque(false);
      }

    try {
      mainButton = new WSButton(node.getChild("WSButton"));
      }
    catch (Throwable t){
      ErrorLogger.log(t);
      }

    arrowButton = new WSButton(XMLReader.readString("<WSButton code=\"ComboArrowButton\" />"));

    add(mainButton,BorderLayout.CENTER);
    add(arrowButton,BorderLayout.EAST);

    try {
      popupMenu = new WSPopupMenu(node.getChild("WSPopupMenu"));
      }
    catch (Throwable t){
      popupMenu = new WSPopupMenu(new XMLNode());
      //ErrorLogger.log(t);
      }
    }


/**
**********************************************************************************************
Builds an XMLNode that describes this object
@return an XML node with the details of this object
**********************************************************************************************
**/
  public XMLNode buildXML(){
    XMLNode node = WSHelper.buildXML(this);

    node.addChild(mainButton.buildXML());
    node.addChild(popupMenu.buildXML());

    return node;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getMainCode(){
    return mainButton.getCode();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getMainText(){
    return mainButton.getText();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getMainToolTipText(){
    return mainButton.getToolTipText();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Dimension getMaximumSize(){
    return new Dimension((int)(mainButton.getPreferredSize().getWidth() + arrowButton.getPreferredSize().getWidth()),(int)mainButton.getPreferredSize().getHeight());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean onClick(JComponent c, MouseEvent e){
    if (c == arrowButton){
      // the arrow button was clicked
      if (popupMenu.isVisible()){
        popupMenu.setVisible(false);
        }
      else {
        popupMenu.show(this,0,mainButton.getHeight());
        }
      return true;
      }
    return false;
    }


  }