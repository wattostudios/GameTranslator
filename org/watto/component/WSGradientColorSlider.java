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
import org.watto.plaf.AquanauticGradientColorSliderUI;
import org.watto.xml.XMLNode;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.plaf.PanelUI;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSGradientColorSlider extends WSPanel {

  Color color = new Color(90,100,110);

  boolean showDot = false;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSGradientColorSlider(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSGradientColorSlider(XMLNode node){
    super();
    buildObject(node);
    registerEvents();
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

    String tag = node.getAttribute("showDot");
    if (tag != null){
      setShowDot(WSHelper.parseBoolean(tag));
      }
    }


/**
**********************************************************************************************
Builds an XMLNode that describes this object
@return an XML node with the details of this object
**********************************************************************************************
**/
  public XMLNode buildXML(){
    XMLNode node = super.buildXML();

    if (showDot){
      node.addAttribute("showDot","true");
      }

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

**********************************************************************************************
**/
  public int getDotPos(){

    double increment42 = ((double)255/42);
    double increment43 = ((double)255/43);

    int selRed   = color.getRed();
    int selGreen = color.getGreen();
    int selBlue  = color.getBlue();


    // determine where the current color is located
    int dotPos = 0;
    if (selRed >= selGreen && selRed >= selBlue){
      if (selGreen >= selBlue){
        // 1 = Red, 2 = Green      Yellow --> Red
        dotPos = 212 + (int)((255-selGreen)/increment43);
        }
      else {
        // 1 = Red, 2 = Blue       Red --> Purple
        dotPos = 0 + (int)(selBlue/increment42);
        }
      }
    else if (selGreen >= selRed && selGreen >= selBlue){
      if (selRed >= selBlue){
        // 1 = Green, 2 = Red      Green --> Yellow
        dotPos = 170 + (int)(selRed/increment42);
        }
      else {
        // 1 = Green, 2 = Blue     Cyan --> Green
        dotPos = 127 + (int)((255-selBlue)/increment43);
        }
      }
    else if (selBlue >= selRed && selBlue >= selGreen){
      if (selRed >= selGreen){
        // 1 = Blue, 2 = Red       Purple --> Blue
        dotPos = 42 + (int)((255-selRed)/increment43);
        }
      else {
        // 1 = Blue, 2 = Green     Blue --> Cyan
        dotPos = 85 + (int)(selGreen/increment42);
        }
      }

    return dotPos;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean getShowDot(){
    return showDot;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColor(Color color){
    this.color = color;
    }


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

**********************************************************************************************
**/
  public void setDotPos(int dotPos){

    double increment42 = ((double)255/42);
    double increment43 = ((double)255/43);

    int selRed = 0;
    int selGreen = 0;
    int selBlue = 0;

    // determine where the current color is located
    if (dotPos > 212){
      // 1 = Red, 2 = Green      Yellow --> Red
      dotPos -= 212;
      selGreen = 255 - (int)(dotPos*increment43);
      selRed = 255;// - selGreen;
      }
    else if (dotPos > 170){
      // 1 = Green, 2 = Red      Green --> Yellow
      dotPos -= 170;
      selRed = (int)(dotPos*increment42);
      selGreen = 255;// - selRed;
      }
    else if (dotPos > 127){
      // 1 = Green, 2 = Blue     Cyan --> Green
      dotPos -= 127;
      selBlue = 255 - (int)(dotPos*increment43);
      selGreen = 255;// - selBlue;
      }
    else if (dotPos > 85){
      // 1 = Blue, 2 = Green     Blue --> Cyan
      dotPos -= 85;
      selGreen = (int)(dotPos*increment42);
      selBlue = 255;// - selGreen;
      }
    else if (dotPos > 42){
      // 1 = Blue, 2 = Red       Purple --> Blue
      dotPos -= 42;
      selRed = 255 - (int)(dotPos*increment43);
      selBlue = 255;// - selRed;
      }
    else if (dotPos > 0){
      // 1 = Red, 2 = Blue       Red --> Purple
      dotPos -= 0;
      selBlue = (int)(dotPos*increment42);
      selRed = 255;// - selBlue;
      }

    if (selRed == 0 && selGreen == 0 && selBlue == 0){
      selRed = 255;
      }

    setColor(selRed,selGreen,selBlue);

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setShowDot(boolean showDot){
    this.showDot = showDot;
    }


/**
**********************************************************************************************
Overwritten to force use of AquanauticColorPanelUI
@param ui not used
**********************************************************************************************
**/
  public void setUI(PanelUI ui){
    super.setUI(AquanauticGradientColorSliderUI.createUI(this));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Dimension getPreferredSize(){
    return getMinimumSize();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Dimension getMinimumSize(){
    if (showDot){
      return new Dimension(25,256);
      }
    else {
      return new Dimension(20,256);
      }
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public Dimension getMaximumSize(){
    return getMinimumSize();
    }



  }