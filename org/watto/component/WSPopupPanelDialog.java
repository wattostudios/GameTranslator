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
This class is not to be called directly - it is only for use with WSPopupPanel. This is the actual
dialog that displays an error message.
**********************************************************************************************
**/
public class WSPopupPanelDialog extends JDialog implements WSClickableInterface, WSKeyableInterface {

  /** the popup that this dialog belongs to **/
  WSPopupPanel popup;

/**
**********************************************************************************************
Creates the dialog for the parent <i>popup</i>
@param popup the popup owner/creator
**********************************************************************************************
**/
  public WSPopupPanelDialog(WSPopupPanel popup) {
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
@param panel the panel to display on the dialog
@param code the Language code of the message
**********************************************************************************************
**/
  public void constructInterface(JPanel panel, String code){
    constructInterface(panel,code,null);
    }


/**
**********************************************************************************************
Constructs the interface of the dialog
@param panel the panel to display on the dialog
@param code the Language code of the message
**********************************************************************************************
**/
  public void constructInterface(JPanel panel, String code, JComponent focusComponent){
    try {

      getContentPane().removeAll();

      setTitle(Language.get("PopupDialog_" + code + "_Title"));

      getContentPane().add(panel,BorderLayout.CENTER);


      pack();
      setSize(400,getHeight()+10);
      setLocationRelativeTo(null);

      if (focusComponent != null){
        focusComponent.requestFocus();
        }

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