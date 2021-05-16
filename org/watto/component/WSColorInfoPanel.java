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

import org.watto.*;
import org.watto.xml.*;

import java.awt.BorderLayout;
import java.awt.Color;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSColorInfoPanel extends WSPanel {

  WSColorPanel colorBox;
  WSLabel colorName;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSColorInfoPanel(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSColorInfoPanel(XMLNode node){
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

    // getting last selected color
    int red = Settings.getInt("WSColorChooser_ColorRed_Selected");
    int green = Settings.getInt("WSColorChooser_ColorGreen_Selected");
    int blue = Settings.getInt("WSColorChooser_ColorBlue_Selected");

    if (red == -1){
      red = 0;
      }
    if (green == -1){
      green = 0;
      }
    if (blue == -1){
      blue = 0;
      }

    // set the color
    colorName = new WSLabel(XMLReader.readString("<WSLabel code=\"ColorInfoPanelName\" />"));
    colorName.setOpaque(false);

    colorBox = new WSColorPanel(XMLReader.readString("<WSColorPanel width=\"15\" height=\"15\" />"));

    setColor(red,green,blue);

    // build the panel
    setLayout(new BorderLayout(4,4));

    add(colorName,BorderLayout.CENTER);
    add(colorBox,BorderLayout.WEST);

    setOpaque(false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Color getColor(){
    return colorBox.getColor();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColor(Color color){
    int red = color.getRed();
    int green = color.getGreen();
    int blue = color.getBlue();
    setColor(red,green,blue);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColor(int red, int green, int blue){
    // looking up the color name
    String colorCode = red + "," + green + "," + blue;
    String name = colorCode;

    if (Language.has("WSColorChooser_Color_" + colorCode,false)){
      name = Language.get("WSColorChooser_Color_" + colorCode);
      }

    colorName.setText(name);

    colorBox.setColor(red,green,blue);

    revalidate();
    repaint();
    }




  }