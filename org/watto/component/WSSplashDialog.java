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
import org.watto.plaf.*;
import org.watto.xml.*;

import javax.swing.*;
import java.awt.*;

/**
**********************************************************************************************
A dialog used as a splash screen, that displays an image and a changable message
THIS IS SPECIAL - DOESN'T USE WSCOMPONENTS BECAUSE IT WILL BE SHOWN BEFORE THEY ARE LOADED!!!
**********************************************************************************************
**/
public class WSSplashDialog extends JDialog{

  static WSSplashDialog instance = new WSSplashDialog();

  /** the message **/
  static JLabel message;
  /** the image **/
  static JLabel image;

  /** the main panel **/
  static JPanel panel = null;


/**
**********************************************************************************************
Constructor
**********************************************************************************************
**/
  public WSSplashDialog() {
    super();
    setModal(false);
    constructInterface();
    }


/**
**********************************************************************************************
Sets the instance
@return the instance
**********************************************************************************************
**/
  public static WSSplashDialog getInstance(){
    return instance;
    }


/**
**********************************************************************************************
Constructs the interface of the component
**********************************************************************************************
**/
  public void constructInterface(){
    if (panel == null){

      setResizable(false);
      setUndecorated(true);
      setTitle("Loading...");

      getContentPane().setLayout(new BorderLayout(0,0));

      try {
        image = new JLabel(new ImageIcon(WSHelper.getResource("images/WSSplashDialog/logo.gif")));
        }
      catch (Throwable t){
        // image does not exist
        image = new JLabel();
        }

      panel = new JPanel(new BorderLayout(0,0));
      panel.setOpaque(true);

      panel.add(image,BorderLayout.NORTH);

      message = new JLabel("Loading Languages And Initial Settings");
      message.setHorizontalAlignment(JLabel.CENTER);
      panel.add(message,BorderLayout.CENTER);

      getContentPane().add(panel,BorderLayout.CENTER);

      pack();

      setLocationRelativeTo(getParent());
      }

    }


/**
**********************************************************************************************
Sets the message
@param newMessage the message
**********************************************************************************************
**/
  public static void setMessage(String newMessage){
    message.setText(Language.get("WSSplashDialog_" + newMessage));
    }



  }