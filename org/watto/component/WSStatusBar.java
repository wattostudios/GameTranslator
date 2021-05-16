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

import org.watto.plaf.AquanauticTheme;
import org.watto.plaf.AquanauticStatusBarUI;
import org.watto.xml.XMLNode;

import javax.swing.plaf.LabelUI;

/**
**********************************************************************************************
A StatusBar
**********************************************************************************************
**/
public class WSStatusBar extends WSLabel {


  /** the previous status message **/
  String oldStatus = " ";


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSStatusBar(){
    super();
    setOpaque(true);
    setBackground(AquanauticTheme.COLOR_LIGHT);
    }



/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
@param caller the object that contains this component, created this component, or more formally,
              the object that receives events from this component.
**********************************************************************************************
**/
  public WSStatusBar(XMLNode node){
    super(node);
    }


/**
**********************************************************************************************
Changes the message back to the previous message
**********************************************************************************************
**/
  public void revertText() {
    if (oldStatus.length() == 0){
      oldStatus = " ";
      }

    super.setText(oldStatus);
    super.setText_Super(oldStatus);
    }


/**
**********************************************************************************************
Sets the message shown in the statusbar
@param message the new message
**********************************************************************************************
**/
  public void setText(String message) {
    if (message == null || message.length() == 0){
      return;
      }
    oldStatus = super.getText();

    super.setText(message); // sets the fixed text because WSLabel.setText() sets it fixed!
    super.setText_Super(message);
    }


/**
**********************************************************************************************
Overwritten to force use of AquanauticBorderPanelUI
@param ui not used
**********************************************************************************************
**/
  public void setUI(LabelUI ui){
    super.setUI(AquanauticStatusBarUI.createUI(this));
    }


  }