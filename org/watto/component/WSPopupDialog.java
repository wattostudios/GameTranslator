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
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.File;

/**
**********************************************************************************************
This class is not to be called directly - it is only for use with WSPopup. This is the actual
dialog that displays an error message.
**********************************************************************************************
**/
public class WSPopupDialog extends JDialog implements WSClickableInterface, WSKeyableInterface {

  /** the popup that this dialog belongs to **/
  WSPopup popup;

/**
**********************************************************************************************
Creates the dialog for the parent <i>popup</i>
@param popup the popup owner/creator
**********************************************************************************************
**/
  public WSPopupDialog(WSPopup popup) {
    super();
    this.popup = popup;
    setModal(true);
    getContentPane().setLayout(new BorderLayout());
    }


/**
**********************************************************************************************
Redirects button click events to the <i>popup</i>
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, MouseEvent e) {
    if (c instanceof JButton){
      popup.onClick(c,e);
      }
    return true;
    }


/**
**********************************************************************************************
Redirects key press events to the <i>popup</i>
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onKeyPress(JComponent c, KeyEvent e) {
    popup.onKeyPress(c,e);
    return true;
    }


/**
**********************************************************************************************
Constructs the interface of the dialog
@param type the type of the dialog
@param code the Language code of the message
@param hidable whether the dialog can be disabled from appearing or not
**********************************************************************************************
**/
  public void constructInterface(String type, String code, boolean hidable){
    try {

      getContentPane().removeAll();

      String settingCode = "Popup_" + code + "_Show";

      setTitle(Language.get("PopupDialog_" + type + "_Title"));


      // Setting up the panel for the buttons
      int numButtons = 1;
      if (type.equals(WSPopup.TYPE_CONFIRM)){
        numButtons++;
        }

      JPanel buttonsPanel = new JPanel(new GridLayout(1,numButtons,5,5));
      buttonsPanel.setOpaque(false);


      // Constructing the buttons
      WSButton buttonWithFocus = null;

      if (type.equals(WSPopup.TYPE_MESSAGE)){
        buttonWithFocus = constructButton(WSPopup.BUTTON_OK);
        buttonsPanel.add(buttonWithFocus);
        }
      else if (type.equals(WSPopup.TYPE_ERROR)){
        buttonWithFocus = constructButton(WSPopup.BUTTON_OK);
        buttonsPanel.add(buttonWithFocus);
        }
      else if (type.equals(WSPopup.TYPE_CONFIRM)){
        buttonWithFocus = constructButton(WSPopup.BUTTON_YES);
        buttonsPanel.add(buttonWithFocus);

        buttonsPanel.add(constructButton(WSPopup.BUTTON_NO));
        }

      popup.setButtonWithFocus(buttonWithFocus);


      // Setting up the panel for the message
      JPanel messagePanel = new JPanel(new BorderLayout(5,5));
      messagePanel.setOpaque(false);


      // Constructing the message
      WSLabel messageLabel = new WSLabel(XMLReader.readString("<WSLabel code=\"" + code + "\" wrap=\"true\" />"));
      messageLabel.setOpaque(false);
      messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
      messagePanel.add(messageLabel,BorderLayout.CENTER);


      // Setting up the panel for the checkbox
      JPanel checkboxPanel = new JPanel(new BorderLayout(5,5));
      checkboxPanel.setOpaque(false);


      // Constructing the checkbox
      if (hidable){
        JCheckBox hidableCheckbox = new JCheckBox(Language.get("PopupDialog_HidableCheckBox_Message"));
        hidableCheckbox.setOpaque(false);
        hidableCheckbox.setHorizontalAlignment(SwingConstants.CENTER);
        hidableCheckbox.setSelected(Settings.getBoolean(settingCode));
        checkboxPanel.add(hidableCheckbox,BorderLayout.CENTER);
        popup.setHidableCheckbox(hidableCheckbox);
        }


      // Constructing the center panel
      JPanel centerPanel = new JPanel(new BorderLayout(5,5));
      centerPanel.setOpaque(false);

      centerPanel.add(checkboxPanel,BorderLayout.SOUTH);
      centerPanel.add(messagePanel,BorderLayout.CENTER);

      WSPanel overallPanel = new WSPanel(XMLReader.readString("<WSPanel showBorder=\"true\" />"));

      try {
        JLabel iconLabel = new JLabel(new ImageIcon(WSHelper.getResource("images/WSLabel/" + type + ".png")));
        overallPanel.add(iconLabel,BorderLayout.WEST);
        }
      catch (Throwable t){
        // image doesn't exist
        }


      // Constructing the dialog
      overallPanel.add(centerPanel,BorderLayout.CENTER);
      overallPanel.add(buttonsPanel,BorderLayout.SOUTH);

      getContentPane().add(overallPanel,BorderLayout.CENTER);


      pack();
      setSize(400,getHeight()+10);
      setLocationRelativeTo(null);
      buttonWithFocus.requestFocus();
      setVisible(true);

      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Builds a button specifically for the dialog
@param code the Language code of the button
@return the button
**********************************************************************************************
**/
  public WSButton constructButton(String code){
    WSButton button = new WSButton(XMLReader.readString("<WSButton code=\"" + code + "\" />"));
    button.addKeyListener(new WSKeyableListener(this));
    return button;
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