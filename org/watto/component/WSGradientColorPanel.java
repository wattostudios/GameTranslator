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
import org.watto.plaf.AquanauticGradientColorPanelUI;
import org.watto.xml.XMLNode;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.plaf.PanelUI;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSGradientColorPanel extends WSPanel {

  Color color = Color.RED;
  Color selectedColor = Color.RED;

  Dimension dotPos = null;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSGradientColorPanel(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSGradientColorPanel(XMLNode node){
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
    }


/**
**********************************************************************************************
Builds an XMLNode that describes this object
@return an XML node with the details of this object
**********************************************************************************************
**/
  public XMLNode buildXML(){
    return super.buildXML();
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
  public Color getColorAtPoint(int x, int y){

   if (x > 255){
     x = 255;
     }
   if (y > 255){
     y = 255;
     }

   if (x < 0){
     x = 0;
     }
   if (y < 0){
     y = 0;
     }

    x = 255 - x;
    y = 255 - y;

    double red   = color.getRed();
    double green = color.getGreen();
    double blue  = color.getBlue();

    double redBlack   = 255 - ((double)y)/255*red;
    double greenBlack = 255 - ((double)y)/255*green;
    double blueBlack  = 255 - ((double)y)/255*blue;

    int redBlackWhite   = (int)(255 - ((255-((double)x))/255*redBlack));
    int greenBlackWhite = (int)(255 - ((255-((double)x))/255*greenBlack));
    int blueBlackWhite  = (int)(255 - ((255-((double)x))/255*blueBlack));

    return new Color(redBlackWhite,greenBlackWhite,blueBlackWhite);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Dimension getDotPos(){
    return dotPos;

    /*
    if (dotPos != null){
      return dotPos;
      }
    int selRed = selectedColor.getRed();
    int selGreen = selectedColor.getGreen();
    int selBlue = selectedColor.getBlue();

    double red   = color.getRed();
    double green = color.getGreen();
    double blue  = color.getBlue();

    for (int d=0;d<256;d++){
      double redBlack   = 255 - ((double)d)/255*red;
      double greenBlack = 255 - ((double)d)/255*green;
      double blueBlack  = 255 - ((double)d)/255*blue;

      for (int l=0;l<256;l++){
        int redBlackWhite   = (int)(255 - ((255-((double)l))/255*redBlack));
        int greenBlackWhite = (int)(255 - ((255-((double)l))/255*greenBlack));
        int blueBlackWhite  = (int)(255 - ((255-((double)l))/255*blueBlack));

        if (redBlackWhite == selRed && greenBlackWhite == selGreen && blueBlackWhite == selBlue){
          return new Dimension(255 - l, 255 - d);
          }
        }
      }

    return new Dimension(255,0);
    */
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Color getSelectedColor(){
    return selectedColor;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void onClick(int x, int y){
    dotPos = new Dimension(x,y);
    setSelectedColor(getColorAtPoint(x,y));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColor(Color color){
    this.color = color;
    dotPos = null;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColor(int colorRGB){
    setColor(new Color(colorRGB));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColor(int red, int green, int blue){
    setColor(new Color(red,green,blue));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSelectedColor(Color selectedColor){
    this.selectedColor = selectedColor;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSelectedColor(int colorRGB){
    selectedColor = new Color(colorRGB);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSelectedColor(int red, int green, int blue){
    selectedColor = new Color(red,green,blue);
    }


/**
**********************************************************************************************
Overwritten to force use of AquanauticColorPanelUI
@param ui not used
**********************************************************************************************
**/
  public void setUI(PanelUI ui){
    super.setUI(AquanauticGradientColorPanelUI.createUI(this));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Dimension getPreferredSize(){
    return new Dimension(256,256);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Dimension getMinimumSize(){
    return new Dimension(256,256);
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public Dimension getMaximumSize(){
    return new Dimension(256,256);
    }



  }