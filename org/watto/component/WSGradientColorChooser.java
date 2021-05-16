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
import org.watto.event.*;
import org.watto.xml.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JComponent;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSGradientColorChooser extends WSPanel implements WSClickableInterface,
                                                               WSKeyableInterface {


  // the components generated from the XML, that we need to reference frequently
  WSGradientColorPanel colorPanel;
  WSGradientColorSlider colorSlider;
  WSSlider colorSliderBar;

  WSTextField colorRed;
  WSTextField colorGreen;
  WSTextField colorBlue;
  WSTextField colorHex;

  WSColorPanel colorPreview;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSGradientColorChooser(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSGradientColorChooser(XMLNode node){
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

    setOpaque(true);

    setLayout(new BorderLayout());

    // Build an XMLNode tree containing all the elements on the screen
    XMLNode srcNode = XMLReader.read(new File(Settings.getString("WSGradientColorChooserXML")));

    // Build the components from the XMLNode tree
    Component component = WSHelper.buildComponent(srcNode);
    add(component,BorderLayout.CENTER);

    // setting up this object in the repository
    setCode(((WSComponent)component).getCode());
    //WSRepository.add(this);

    loadDefaults();


    String tag;

    int red = 0;
    int green = 0;
    int blue = 0;

    tag = node.getAttribute("red");
    if (tag != null){
      red = WSHelper.parseInt(tag);
      }

    tag = node.getAttribute("green");
    if (tag != null){
      green = WSHelper.parseInt(tag);
      }

    tag = node.getAttribute("blue");
    if (tag != null){
      blue = WSHelper.parseInt(tag);
      }

    if (red == -1){
      red = 0;
      }
    if (green == -1){
      green = 0;
      }
    if (blue == -1){
      blue = 0;
      }

    setColor(red,green,blue);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Color getColor(){
    return colorPanel.getSelectedColor();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadDefaults(){
    // referencing the components
    colorPanel = (WSGradientColorPanel)WSRepository.get("GradientColorPanel");
    colorSlider = (WSGradientColorSlider)WSRepository.get("GradientColorSlider");
    colorSliderBar = (WSSlider)WSRepository.get("GradientColorSliderBar");
    colorSliderBar.setMinimum(0);
    colorSliderBar.setMaximum(255);

    colorRed = (WSTextField)WSRepository.get("GradientColorChooserRedValue");
    colorGreen = (WSTextField)WSRepository.get("GradientColorChooserGreenValue");
    colorBlue = (WSTextField)WSRepository.get("GradientColorChooserBlueValue");
    colorHex = (WSTextField)WSRepository.get("GradientColorChooserHexValue");

    colorPreview = (WSColorPanel)WSRepository.get("GradientColorChooserSelectedColor");
    }


/**
**********************************************************************************************
The event that is triggered from a WSSelectableListener when an item is deselected
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, MouseEvent e){
    if (c == colorSliderBar){
      setColorFromSlider();
      }
    else if (c == colorPanel){
      setColorFromPanel(e.getX(),e.getY());
      }
    else if (c == colorSlider){
      colorSliderBar.setValue(255 - e.getY());
      setColorFromSlider();
      }
    else {
      return false;
      }
    return true;
    }


/**
**********************************************************************************************
The event that is triggered from a WSSelectableListener when an item is deselected
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onKeyPress(JComponent c, KeyEvent e){
    if (c == colorSliderBar){
      setColorFromSlider();
      }
    else {
      return false;
      }
    return true;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColor(Color color){
    setColor(color,true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColor(Color color, boolean fireChangeEvent){
    colorSlider.setColor(color);
    colorPanel.setSelectedColor(color);
    colorPanel.setColor(colorSlider.getColor());
    colorSliderBar.setValue(255 - colorSlider.getDotPos());
    colorPanel.repaint();

    updateReportingFields(color,fireChangeEvent);
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
  public void setColorFromPanel(int x, int y){
    colorPanel.onClick(x,y);
    colorPanel.repaint();

    updateReportingFields(colorPanel.getSelectedColor());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColorFromSlider(){
    colorSlider.setDotPos(255 - colorSliderBar.getValue());

    Color color = colorSlider.getColor();
    colorPanel.setSelectedColor(color);
    colorPanel.setColor(color);

    updateReportingFields(color);

    repaint();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void updateReportingFields(Color color){
    updateReportingFields(color,true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void updateReportingFields(Color color, boolean fireChangeEvent){
    int red = color.getRed();
    int green = color.getGreen();
    int blue = color.getBlue();

    colorRed.setText(""+red);
    colorGreen.setText(""+green);
    colorBlue.setText(""+blue);

    String hex = "#";

    String hexValue = Integer.toHexString(red);
    if (hexValue.length() == 1){
      hexValue = "0" + hexValue;
      }
    hex += hexValue;

    hexValue = Integer.toHexString(green);
    if (hexValue.length() == 1){
      hexValue = "0" + hexValue;
      }
    hex += hexValue;

    hexValue = Integer.toHexString(blue);
    if (hexValue.length() == 1){
      hexValue = "0" + hexValue;
      }
    hex += hexValue;

    colorHex.setText(hex.toUpperCase());

    colorPreview.setColor(red,green,blue);
    colorPreview.repaint();

    Settings.set("WSGradientColorChooser_Color_Selected",color.getRGB());

    if (fireChangeEvent){
      WSHelper.fireEvent(WSEvent.COLOR_CHANGED,this);
      }
    }


  }