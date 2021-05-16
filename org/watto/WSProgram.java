////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                            WATTO STUDIOS JAVA PROGRAM TEMPLATE                             //
//                  Template Classes and Helper Utilities for Java Programs                   //
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

package org.watto;

import org.watto.component.*;
import org.watto.event.*;
import org.watto.plaf.*;
import org.watto.xml.*;

import javax.swing.*;
import java.io.*;
import java.awt.*;

/**
--- TO DO ---

- Implement hasFocus() in the AquanauticXXXUI classes, when it is painting a WSComponent
- Document all methods/classes and generate API docs
- Distribute on SourceForge

// WSCOMPONENT CHANGES
- WSList, WSTable, WSComboBox, WSComboButton should all have the same methods for adding/getting/etc.
  - eg. one has getSelectedItem() and another has getSelectedObject();
- Allow setting of min/max/preferred sizes individually, not just setSize()
- In the header of each component, add the allowed XML attributes.
- WSComboBox
  - Sorting
  - Custom renderer/model to optionally use Language
    - LanguageComboBoxModel vs DefaultComboBoxModel
  - Attribute to store items in the XML or not (yes, is numChildren when loading XML > 0)
  - Generic load data methods (addItems(), etc).
- WSList
  - Custom renderer/model to optionally use Language
    - LanguageComboBoxModel vs DefaultComboBoxModel
  - Generic load data methods (addItems(), etc).
  - fireSelectionValueChanged() and fireValueChanged() need to point to processEvent();
- WSTable
  - Custom renderer/model to optionally use Language
    - LanguageComboBoxModel vs DefaultComboBoxModel
  - Load/save data from XML
  - Generic load data methods (addItems(), etc).
- WSEditableComboBox
  - Uses JTextField for the main thingy so the user can choose OR enter their own value
- WSToolBar
  - Orientation
- WSPopupMenu
  - Saving XML saves all menuitems?
- WSTabbedPane
  - Make class
- WSColorChooser
  - Make class
- WSFileChooser
  - Make class
- WSTableColumn
  - Build and save in XML
- WSMonthChooser
  - Set default dates, etc.
  - Highlight selected day
- WSNumberField
  - Same as WSTextField, but with an InputVerifier
- WSTree
  - fireSelectionValueChanged() and fireValueChanged() need to point to processEvent();
- WSFontChooser
  - TextChangeListener for the textfields (such as when pressing Enter or Tab)
  - Implement the other formatting options like underlines and so forth.

**/

/**
**********************************************************************************************
                                         WSProgram
                                   External version: 3.0
                                   Internal version: 3.2

                      See the Readme file for information on WSProgram

Designed to be extended by your own main program class, it sets up and initialises components
such as the ErrorLogger, Settings, and Language when the constructor is called (ie super() in
your main class constructor).
**********************************************************************************************
**/
public abstract class WSProgram extends JFrame implements WSHoverableInterface,
                                                          WSClosableInterface{

  public WSSplashDialog splash;


/**
**********************************************************************************************
Calls buildProgram(this);
**********************************************************************************************
**/
  public WSProgram(){
    buildProgram(this);
    }


/**
**********************************************************************************************
Generates the interface from the XML file, and sets up the basic program utilities, such as:
<ul>
<li>ErrorLogger</li>
<li>Settings</li>
<li>Language</li>
<li>ThemeLoader</li>
<li>WSPopup</li>
<li>UndoManager</li>
<li>WSPluginManager</li>
<li>WSRepository</li>
</ul>
**********************************************************************************************
**/
  public void buildProgram(Object obj){
    //
    // NOTE:
    // The prefix of /*//REMOVE//*/ on some lines exists only for testing during the
    // development of the org.watto.component components. These prefixes can be removed.
    //

    new ErrorLogger();
    new Settings();
    new Language();

    WSHelper.setResourcePath(obj);
    //setResourcePath(obj); // sets the path for the images

    new ThemeLoader();
    new WSPopup(); // init the popup error dialogs
    new UndoManager(); // setup the number of under tasks

    // We have all the information for the Splash Screen,
    // so now we can load and display it
    splash = WSSplashDialog.getInstance();
    if (Settings.getBoolean("ShowSplashScreen")){
      splash.setVisible(true);
      }

    // load the plugins
    splash.setMessage("Plugins");
    new WSPluginManager();


    // construct the interface
    splash.setMessage("ConstructInterface");
    constructInterface();


    // Close the splash screen. If you use the splash screen in your main
    // program constructor, you should set this setting to false so that
    // you can continue using the same splash screen that was created here.
    if (Settings.getBoolean("WSProgramClosesSplashScreen")){
      splash.dispose();
      }
    }


/**
**********************************************************************************************
Builds the interface of the program. Can be overwritten if you want to do additional things
when the interface is being constructed, or if you dont want to load the interface from an
XML file.
**********************************************************************************************
**/
  public void constructInterface(){
    File interfaceFile = new File(Settings.getString("InterfaceFile"));
    if (! interfaceFile.exists()){
      interfaceFile = new File(Settings.getString("DefaultInterfaceFile"));
      }

    Container frame = getContentPane();
    frame.removeAll();
    frame.setLayout(new BorderLayout(0,0));
    frame.add(WSHelper.buildComponent(XMLReader.read(interfaceFile)),BorderLayout.CENTER);

    addWindowListener(new WSClosableWindowListener(this));
    }


/**
**********************************************************************************************
Saves the interface back to an XML file
**********************************************************************************************
**/
  public void saveInterface(){
    File interfaceFile = new File(Settings.getString("InterfaceFile"));

    WSComponent rootComponent = (WSComponent)getContentPane().getComponent(0);
    XMLNode node = rootComponent.buildXML();

    if (interfaceFile.exists()){
      interfaceFile.delete();
      }
    XMLWriter.write(interfaceFile,node);
    }


/**
**********************************************************************************************
Send the <i>error</i> to the ErrorLogger for recording
@param error the error
**********************************************************************************************
**/
  public static void logError(Throwable error){
    ErrorLogger.log(error);
    }


  }