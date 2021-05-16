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
import java.io.File;
import javax.swing.*;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSSmallColorChooser extends WSPanel implements WSClickableInterface,
                                                            WSHoverableInterface{

  WSColorInfoPanel infoPanel; // on the popup
  WSColorInfoPanel infoPanelMain; // on the main panel

  WSButton arrowButton;
  WSPopupMenu popupMenu;

  WSGradientColorChooser colorChooser;
  WSPopupMenu popup;

  boolean popupOpen = false;



/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSSmallColorChooser(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSSmallColorChooser(XMLNode node){
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

    // Build an XMLNode tree containing all the elements on the screen
    XMLNode srcNode = XMLReader.read(new File(Settings.getString("WSSmallColorChooserXML")));

    // Build the components from the XMLNode tree
    Component component = WSHelper.buildComponent(srcNode);
    popupMenu = new WSPopupMenu(XMLReader.readString("<WSPopupMenu />"));
    popupMenu.add(component);
    //add(component,BorderLayout.CENTER);

    infoPanel = (WSColorInfoPanel)WSRepository.get("ColorCurrentColor");
    arrowButton = new WSButton(XMLReader.readString("<WSButton code=\"WSComboButtonArrow\" opaque=\"false\" />"));

    //WSColorInfoPanel infoPanelMain = new WSColorInfoPanel(this);
    //infoPanelMain.setColor(infoPanel.getColor());

    infoPanelMain = new WSColorInfoPanel(XMLReader.readString("<WSColorInfoPanel />"));
    infoPanelMain.setOpaque(false);

    WSPanel bp = new WSPanel(XMLReader.readString("<WSPanel showBorder=\"true\" />"));
    bp.setOpaque(false);
    bp.add(infoPanelMain,BorderLayout.CENTER);

    add(bp,BorderLayout.CENTER);
    add(arrowButton,BorderLayout.EAST);

    //infoPanel.setOpaque(false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void closePopup(){
    popupOpen = false;
    popupMenu.setVisible(false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Color getColor(){
    return infoPanelMain.getColor();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Dimension getMaximumSize(){
    return new Dimension((int)(infoPanel.getPreferredSize().getWidth() + arrowButton.getPreferredSize().getWidth()),(int)infoPanel.getPreferredSize().getHeight());
    }


/**
**********************************************************************************************
The event that is triggered from a WSClickableListener when a click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, java.awt.event.MouseEvent e){
    if (c instanceof JButton){
      if (c == arrowButton){
        // the arrow button was clicked
        if (popupOpen){
          closePopup();
          }
        else {
          openPopup();
          }
        }
      else if (c instanceof WSButton){
        String code = ((WSButton)c).getCode();

        if (code.equals("ColorChooserClosePopup")){
          //closing the popup happens automatically, we just want to trigger the setting of the new color
          if (colorChooser != null){
            popup.setVisible(false);
            setColor(colorChooser.getColor());
            closePopup();
            }
          }
        else if (code.equals("ColorMoreColors")){
          //// using the normal color chooser
          //Color color = JColorChooser.showDialog(this,Language.get("ColorChooser",false),infoPanel.getColor());

          // Using WSGradientColorChooser
          colorChooser = new WSGradientColorChooser(new XMLNode());
          colorChooser.setColor(getColor());

          WSPanel panel = new WSPanel(new XMLNode());
          panel.add(colorChooser,BorderLayout.CENTER);
          panel.add(new WSButton(XMLReader.readString("<WSButton code=\"ColorChooserClosePopup\" />")),BorderLayout.SOUTH);

          popup = new WSPopupMenu(new XMLNode());
          popup.add(panel);
          popup.setOpaque(true);

          popup.show(this,0,0);
          }
        }
      }
    else if (c instanceof WSColorPanel){
      try {
        // set the color
        WSColorPanel panel = (WSColorPanel) c;
        Color color = panel.getColor();

        setColor(color);
        closePopup();
        }
      catch (Throwable t){
        ErrorLogger.log(t);
        return false;
        }
      }
    else if (c == infoPanelMain){
      if (popupOpen){
        closePopup();
        }
      else {
        openPopup();
        }
      }

    return true;
    }


/**
**********************************************************************************************
The event that is triggered from a WSHoverableListener when a component is hovered over
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onHover(JComponent c, java.awt.event.MouseEvent e){
    if (c instanceof WSColorPanel){
      try {
        // set the color
        WSColorPanel panel = (WSColorPanel) c;
        Color color = panel.getColor();

        infoPanel.setColor(color);
        }
      catch (Throwable t){
        ErrorLogger.log(t);
        return false;
        }
      }

    return true;
    }


/**
**********************************************************************************************
The event that is triggered from a WSHoverableListener when a component is no longer hovered
over (ie loses its hover)
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onHoverOut(JComponent c, java.awt.event.MouseEvent e){

    if (! (c instanceof WSColorPanel)){
      try {
        // Change the color back to the current color when the user leaves something other
        // than a color cell. The reason: each color cell has a few pixels of padding between
        // them - we dont want to flicker back and forth to the current color when simply
        // moving around the color grid - we only want to change the color back to the
        // current color when it moves away from the color cells (thus, out of the color grid)
        infoPanel.setColor(infoPanelMain.getColor());
        }
      catch (Throwable t){
        ErrorLogger.log(t);
        return false;
        }
      }

    return true;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void openPopup(){
    popupOpen = true;
    popupMenu.show(this,0,arrowButton.getHeight());
    }


/**
**********************************************************************************************
The event that is triggered from a WSClickableListener when a click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public void setColor(Color color){
    if (color == null){
      return;
      }

    int red = color.getRed();
    int green = color.getGreen();
    int blue = color.getBlue();

    Settings.set("WSColorChooser_ColorRed_Selected",red);
    Settings.set("WSColorChooser_ColorGreen_Selected",green);
    Settings.set("WSColorChooser_ColorBlue_Selected",blue);

    infoPanel.setColor(red,green,blue);
    infoPanelMain.setColor(red,green,blue);

    WSHelper.fireEvent(WSEvent.COLOR_CHANGED,this);
    }



  }