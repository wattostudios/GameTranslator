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
A Popup
**********************************************************************************************
**/
public class WSPopup extends JComponent implements WSClickableInterface,
                                                   WSKeyableInterface {


  /** The code for the language and settings **/
  static String code = null;


  /** so only 1 popup will ever be displayed to the user at a time! **/
  static WSPopupDialog instance = null;

  /** Message popup **/
  public static String TYPE_MESSAGE = "Message";
  /** Error popup **/
  public static String TYPE_ERROR = "Error";
  /** Confirmation popup **/
  public static String TYPE_CONFIRM = "Confirm";

  /** The OK Button **/
  public static String BUTTON_OK = "OK";
  /** The Yes Button **/
  public static String BUTTON_YES = "Yes";
  /** The No Button **/
  public static String BUTTON_NO = "No";


  /** Whether the panel can be disabled from appearing **/
  static boolean hidable = false;
  /** The type of the popup **/
  static String type = TYPE_MESSAGE;

  /** The value of the button that was pressed **/
  static String pressedEvent = null;


  /** The button with focus **/
  static WSButton buttonWithFocus = null;
  /** The checkbox that asks to hide the popup **/
  static JCheckBox hidableCheckbox = null;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  public WSPopup(){
    super();
    if (instance == null){
      instance = new WSPopupDialog(this);
      }
    }


/**
**********************************************************************************************
Gets the singleton this of the popup
@return the singleton <i>instance</i>
**********************************************************************************************
**/
  public static WSPopupDialog getInstance() {
    return instance;
    }


/**
**********************************************************************************************
Shows a message popup
@param codeIn the Language code of the message
@return the button that was pressed
**********************************************************************************************
**/
  public static String show(String codeIn) {
    return show(TYPE_MESSAGE,codeIn,false);
    }


/**
**********************************************************************************************
Shows a message popup
@param codeIn the Language code of the message
@param hidableIn whether the popup can be disabled from appearing or not
@return the button that was pressed
**********************************************************************************************
**/
  public static String show(String codeIn, boolean hidableIn){
    return show(TYPE_MESSAGE,codeIn,hidableIn);
    }


/**
**********************************************************************************************
Shows a message popup
@param codeIn the Language code of the message
@return the button that was pressed
**********************************************************************************************
**/
  public static String showMessage(String codeIn) {
    return show(TYPE_MESSAGE,codeIn,false);
    }


/**
**********************************************************************************************
Shows a message popup
@param codeIn the Language code of the message
@param hidableIn whether the popup can be disabled from appearing or not
@return the button that was pressed
**********************************************************************************************
**/
  public static String showMessage(String codeIn, boolean hidableIn){
    return show(TYPE_MESSAGE,codeIn,hidableIn);
    }


/**
**********************************************************************************************
Shows an error popup
@param codeIn the Language code of the message
@return the button that was pressed
**********************************************************************************************
**/
  public static String showError(String codeIn) {
    return show(TYPE_ERROR,codeIn,false);
    }


/**
**********************************************************************************************
Shows an error popup
@param codeIn the Language code of the message
@param hidableIn whether the popup can be disabled from appearing or not
@return the button that was pressed
**********************************************************************************************
**/
  public static String showError(String codeIn, boolean hidableIn){
    return show(TYPE_ERROR,codeIn,hidableIn);
    }


/**
**********************************************************************************************
Shows a confirmation popup
@param codeIn the Language code of the message
@return the button that was pressed
**********************************************************************************************
**/
  public static String showConfirm(String codeIn) {
    return show(TYPE_CONFIRM,codeIn,false);
    }


/**
**********************************************************************************************
Shows a confirmation popup
@param codeIn the Language code of the message
@param hidableIn whether the popup can be disabled from appearing or not
@return the button that was pressed
**********************************************************************************************
**/
  public static String showConfirm(String codeIn, boolean hidableIn){
    return show(TYPE_CONFIRM,codeIn,hidableIn);
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
  public static String show(String typeIn, String codeIn, boolean hidableIn){
    code = codeIn;
    hidable = hidableIn;
    type = typeIn;
    pressedEvent = null;
    hidableCheckbox = null;


    String settingCode = "Popup_" + code + "_Show";

    if (hidable && !Settings.getBoolean(settingCode)) {
      return BUTTON_OK;
      }


    instance.constructInterface(type,code,hidable);


    if (hidable){
      Settings.set(settingCode,hidableCheckbox.isSelected());
      }

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
Sets the button that has focus
@param button the button to gain focus
**********************************************************************************************
**/
  public static void setButtonWithFocus(WSButton button){
    buttonWithFocus = button;
    }


/**
**********************************************************************************************
Sets the hidable checkbox
@param checkbox the hidable checkbox
**********************************************************************************************
**/
  public static void setHidableCheckbox(JCheckBox checkbox){
    hidableCheckbox = checkbox;
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