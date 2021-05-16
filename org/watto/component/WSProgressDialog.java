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

import javax.swing.*;
import java.awt.*;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class WSProgressDialog extends JDialog implements WSClickableInterface {

  /** the singleton instance of the dialog **/
  static WSProgressDialog instance = null;

  /** the progress bars **/
  static JProgressBar[] bars = new JProgressBar[1];
  /** the number of progress bars **/
  static int numBars = 1;

  /** the status message label **/
  static JLabel message = new JLabel();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSProgressDialog() {
    super();
    setModal(false);
    setUndecorated(true);
    constructInterface();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void constructInterface(){
    //instance.setDefaultLookAndFeelDecorated(false); // don't want to show the Windows-style border around the popup

    getContentPane().removeAll();
    getContentPane().setLayout(new BorderLayout(0,0));

    WSButton closeButton = new WSButton(XMLReader.readString("<WSButton code=\"CloseProgress\" />"));
    closeButton.addMouseListener(new WSClickableListener(this));

    WSPanel overallPanel = new WSPanel(XMLReader.readString("<WSPanel showBorder=\"true\" />"));

    WSPanel mainPanel = new WSPanel(XMLReader.readString("<WSPanel opaque=\"false\" />"));
    mainPanel.add(message,BorderLayout.CENTER);

    WSPanel barPanel = new WSPanel(XMLReader.readString("<WSPanel layout=\"GridLayout\" rows=\"" + numBars + "\" columns=\"1\" />"));
    barPanel.setOpaque(false);

    bars = new JProgressBar[numBars];

    for (int i=0;i<numBars;i++){
      JProgressBar bar = new JProgressBar();

      bar.setOpaque(false);
      bar.setPreferredSize(new Dimension(200,20));
      bar.setStringPainted(true);

      barPanel.add(bar);
      bars[i] = bar;
      }

    mainPanel.add(barPanel,BorderLayout.SOUTH);

    overallPanel.add(mainPanel,BorderLayout.CENTER);
    overallPanel.add(closeButton,BorderLayout.SOUTH);

    message.setHorizontalAlignment(JLabel.CENTER);

    getContentPane().add(overallPanel,BorderLayout.CENTER);

    pack();
    //setSize(200,100);
    //progress.setSize(new Dimension(200,20));
    setLocationRelativeTo(getParent());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static WSProgressDialog getInstance(){
    if (instance == null){
      instance = new WSProgressDialog();
      }
    return instance;
    }





/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setIndeterminate(boolean indeterminate){
    setIndeterminate(indeterminate,0);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setIndeterminate(boolean indeterminate, int barNumber){
    try {
      bars[barNumber].setIndeterminate(indeterminate);
      if (indeterminate){
        // if true, need to repaint the indeterminate progress
        //new RepaintThread_WhileVisible(this,50).start();
        new RepaintThread_WhileVisible(bars[barNumber],50).start();
        }
      }
    catch (Throwable t){
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void setMaximum(long newMaximum){
    setMaximum(newMaximum,0);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void setMaximum(long newMaximum, int barNumber){
    try {
      if (numBars > 1){
        // if the barNumber is indeterminate, looks for the next determinate bar and changes that instead
        for (;barNumber<numBars;barNumber++){
          if (! bars[barNumber].isIndeterminate()){
            bars[barNumber].setValue(0);
            bars[barNumber].setMaximum((int)newMaximum);
            return;
            }
          }
        }
      else {
        bars[barNumber].setValue(0);
        bars[barNumber].setMaximum((int)newMaximum);
        }
      }
    catch (Throwable t){
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void setMessage(String newMessage){
    message.setText(newMessage);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setNumberOfBars(int newNumBars){
    numBars = newNumBars;
    constructInterface();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void setValue(long newValue){
    setValue(newValue,0);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void setValue(long newValue, int barNumber){
    try {
      if (numBars > 1){
        // if the barNumber is indeterminate, looks for the next determinate bar and changes that instead
        for (;barNumber<numBars;barNumber++){
          if (! bars[barNumber].isIndeterminate()){
            bars[barNumber].setValue((int)newValue);
            return;
            }
          }
        }
      else {
        bars[barNumber].setValue((int)newValue);
        }
      }
    catch (Throwable t){
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setVisible(boolean visible){
    for (int i=0;i<numBars;i++){
      bars[i].setVisible(visible);
      }

    Dimension size = getSize();
    size.height += 10;
    size.width += 10;
    setSize(size);

    super.setVisible(visible);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void show(int numBars, int newMaximum, String newMessage){
    setNumberOfBars(numBars);
    show(newMaximum,newMessage);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void show(int newMaximum, String newMessage){
    setMaximum(newMaximum);
    setMessage(newMessage);
    setVisible(true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void show(String newMessage){
    show(0,newMessage);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void show(int newMaximum){
    show(newMaximum,Language.get("Progress_PleaseWait"));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean onClick(JComponent c, java.awt.event.MouseEvent e){
    if (c instanceof WSButton){
      if (((WSButton)c).getCode().equals("CloseProgress")){
        setVisible(false);
        return true;
        }
      }
    return false;
    }


  }