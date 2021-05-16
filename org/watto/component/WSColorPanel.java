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
import org.watto.plaf.AquanauticColorPanelUI;
import org.watto.xml.XMLNode;

import java.awt.Color;
import javax.swing.plaf.PanelUI;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSColorPanel extends WSPanel {

  Color color;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSColorPanel(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSColorPanel(XMLNode node){
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

    String tag;

    int red = 0;
    int green = 0;
    int blue = 0;

    try {
      tag = node.getAttribute("red");
      if (tag != null){
        red = Integer.parseInt(tag);
        }

      tag = node.getAttribute("green");
      if (tag != null){
        green = Integer.parseInt(tag);
        }

      tag = node.getAttribute("blue");
      if (tag != null){
        blue = Integer.parseInt(tag);
        }
      }
    catch (Throwable t){
      ErrorLogger.log(t);
      }

    setColor(red,green,blue);
    setOpaque(false);
    }


/**
**********************************************************************************************
Builds an XMLNode that describes this object
@return an XML node with the details of this object
**********************************************************************************************
**/
  public XMLNode buildXML(){
    XMLNode node = super.buildXML();

    node.setAttribute("red",  ""+color.getRed());
    node.setAttribute("green",""+color.getGreen());
    node.setAttribute("blue", ""+color.getBlue());

    return node;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Color getColor(){
    return color;
    }


/**
**********************************************************************************************
Forces it to be a square
**********************************************************************************************
**/
  //public int getHeight(){
  //  return getWidth();
  //  }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColor(int colorRGB){
    color = new Color(colorRGB);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColor(int red, int green, int blue){
    color = new Color(red,green,blue);
    }


/**
**********************************************************************************************
Overwritten to force use of AquanauticColorPanelUI
@param ui not used
**********************************************************************************************
**/
  public void setUI(PanelUI ui){
    super.setUI(AquanauticColorPanelUI.createUI(this));
    }



  }