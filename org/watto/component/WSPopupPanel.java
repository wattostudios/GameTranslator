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

import java.io.File;
import java.net.URL;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
**********************************************************************************************
A Popup that displays a panel, and returns the code of the clicked button
**********************************************************************************************
**/
public class WSPopupPanel extends JComponent implements WSClickableInterface,
                                                        WSKeyableInterface {


  /** so only 1 popup will ever be displayed to the user at a time! **/
  static WSPopupPanelDialog instance = null;

  /** The value of the button that was pressed **/
  static String pressedEvent = null;



/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  public WSPopupPanel(){
    super();
    if (instance == null){
      instance = new WSPopupPanelDialog(this);
      }
    }


/**
**********************************************************************************************
Gets the singleton this of the popup
@return the singleton <i>instance</i>
**********************************************************************************************
**/
  public static WSPopupPanelDialog getInstance() {
    return instance;
    }


/**
**********************************************************************************************
Shows a popup of the given <i>typeIn</i>
@param typeIn the type of popup
@param codeIn the Language code of the message
@param hidableIn whether the popup can be disabled from appearing or not
@return the button that was pressed
**********************************************************************************************
**/
  public static String show(JPanel panel, String code){
    if (instance == null){
      // builds an instance
      new WSPopupPanel();
      }
    instance.constructInterface(panel,code);
    return pressedEvent;
    }


/**
**********************************************************************************************
Shows a popup of the given <i>typeIn</i>
@param typeIn the type of popup
@param codeIn the Language code of the message
@param hidableIn whether the popup can be disabled from appearing or not
@return the button that was pressed
**********************************************************************************************
**/
  public static String show(JPanel panel, String code, JComponent focusComponent){
    if (instance == null){
      // builds an instance
      new WSPopupPanel();
      }
    instance.constructInterface(panel,code,focusComponent);
    return pressedEvent;
    }


/**
**********************************************************************************************
Closes the popup when a button was pressed, and sets the <i>pressedEvent</i>.
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, MouseEvent e) {
    if (c instanceof JButton){
      pressedEvent = ((WSComponent)c).getCode();
      instance.dispose();
      }
    return true;
    }


/**
**********************************************************************************************
Presses the button when the <i>Enter</i> key is pressed
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onKeyPress(JComponent c, KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_ENTER){
      if (c instanceof WSButton){
        WSButton button = (WSButton)c;
        instance.onClick(button,new MouseEvent(button, MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, 1, false));
        }
      }

    return true;

    }



/**
**********************************************************************************************
Records the error/exception stack trace in the log file. If debug is enabled, it will also
write the error to the <i>System.out</i> command prompt
@param t the <i>Throwable</i> error/exception
**********************************************************************************************
**/
  public static void logError(Throwable t){
    ErrorLogger.log(t);
    }



  }