////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                        PROGRAM NAME                                        //
//                                 Description Of The Program                                 //
//                                    http://www.watto.org                                    //
//                                                                                            //
//                           Copyright (C) 2008-2008  WATTO Studios                           //
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

/***************************************** TODO LIST *****************************************\

////////////////////////////////////////////////////////////////////////////////////////////////
//                                  VERSION RELEASE FEATURES                                  //
////////////////////////////////////////////////////////////////////////////////////////////////

// TESTING

// BUGS

// REQUIRED

// PREFERRED

// OPTIONAL

// OTHER

// THINGS TO REMEMBER
- All plugins need to call super(code/name)!!!
- ALL EXTENDED JCOMPONENTS NEED TO IMPLEMENT THE FOLLOWING...
  - constructor() needs to be in full (not super());
  - buildObject();
  - buildXML();
  - registerEvents();


////////////////////////////////////////////////////////////////////////////////////////////////
//                              RELEASE AND PACKAGING INFORMATION                             //
////////////////////////////////////////////////////////////////////////////////////////////////

- Make sure to add any new files into the install maker script! (NOT NEW CLASSES! Just others)
  - Scripts
  - Help / help images
  - Languages
- Remember to package up any changed language files too!

*** *** ***
- Include all classpath items in the release zip (org* for all, com* and net* for Full Version only)
*** *** ***

// Things to remove from Basic Version...
- FullVersionVerifier

\*********************************************************************************************/

import org.watto.*;
import org.watto.component.*;
import org.watto.event.*;
import org.watto.manipulator.FileExtensionFilter;
import org.watto.xml.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
**********************************************************************************************
The Game Extractor program. This class contains the main interface, loads major components
such as the <code>PluginManager</code>s, and handles toolbar/menubar events.
**********************************************************************************************
**/
public class MyProgram extends WSProgram implements WSClickableInterface {


  /** A singleton holder for the MyProgram program, so other classes can directly access
      the same instance **/
  static MyProgram instance = new MyProgram();


/**
**********************************************************************************************
Not to be used - use "MyProgram.getInstance()" instead of "new MyProgram()"
**********************************************************************************************
**/
  public MyProgram(){

    setIconImage(new ImageIcon(getClass().getResource("images/WSFrame/icon.png")).getImage());

    // Construct the default archive
    splash.setMessage("LoadingSomething");

    // Setting up any defaults
    //new Archive();
    //FileTypeDetector.loadGenericDescriptions();

    ((WSStatusBar)WSRepository.get("StatusBar")).setText(Language.get("Welcome"));
    setTitle(Language.get("ProgramName") + " " + Settings.get("Version"));

    // close the splash screen
    splash.dispose();


    pack();
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    setVisible(true);

    }


/**
**********************************************************************************************
Returns the singleton instance of the program. This allows other classes to all address the
same instance of the interface, rather than separate instances.
@return the singleton <i>instance</i> of MyProgram
**********************************************************************************************
**/
  public static MyProgram getInstance(){
    return instance;
    }


/**
**********************************************************************************************
Builds the interface of the program. Can be overwritten if you want to do additional things
when the interface is being constructed, or if you dont want to load the interface from an
XML file.
**********************************************************************************************
**/
  public void constructInterface(){
    super.constructInterface();

    // do anything else here that you need
    }


/**
**********************************************************************************************
Deletes all the temporary files from the <i>directory</i>.
@param directory the directory that contains the temporary files.
**********************************************************************************************
**/
  public void deleteTempFiles(File directory) {
    try {

      File[] tempFiles = directory.listFiles();

      if (tempFiles == null){
        return;
        }

      for (int i=0;i<tempFiles.length;i++){
        if (tempFiles[i].isDirectory()){
          deleteTempFiles(tempFiles[i]);
          }
        tempFiles[i].delete();
        }

      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static boolean isFullVersion(){
    try {
      new FullVersionVerifier();
      return true;
      }
    catch (Throwable t){
      return false;
      }
    }


/**
**********************************************************************************************
The event that is triggered from a WSButtonableListener when a button click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, java.awt.event.MouseEvent e){
    if (! (c instanceof WSComponent)){
      return false;
      }

    String code = ((WSComponent)c).getCode();

    System.out.println("MyProgram: " + code);

    if (c instanceof WSRecentFileMenuItem){
      // opening a recent file - the 'code' is the filename to open
      File recentFile = new File(code);
      if (recentFile.exists()){
        Task_ReadArchive task = new Task_ReadArchive(recentFile);
        task.setDirection(UndoTask.DIRECTION_REDO);
        new Thread(task).start();
        }
      }
    else if (c instanceof WSUndoMenuItem){
      // undo a task
      UndoTask task = ((WSUndoMenuItem)c).getTask();
      UndoManager.undo(task);
      //WSPopup.showMessage("UndoTask",true); // handled by the actual task
      }
    else if (c instanceof WSRedoMenuItem){
      // redo a task
      UndoTask task = ((WSRedoMenuItem)c).getTask();
      UndoManager.redo(task);
      //WSPopup.showMessage("RedoTask",true); // handled by the actual task
      }
    else if (c instanceof WSMenuItem || c instanceof WSButton){
      if (code.equals("CloseProgram")){
        onClose();
        }
      else if (code.equals("Help")){
        setSidePanel("Help");
        }
      else if (code.equals("About")){
        setSidePanel("About");
        }
      else {
        return false;
        }
      return true;
      }

    return false;
    }


/**
**********************************************************************************************
The event that is triggered from a WSClosableListener when a component is closed
**********************************************************************************************
**/
  public boolean onClose(){

    // ask to save the modified archive
    if (MyProgram.getInstance().promptToSave()){
      return false;
      }
    ChangeMonitor.set(false);


    deleteTempFiles(new File("temp"));


    // Save settings files
    Settings.saveSettings();

    // Saves the interface to XML, in case there were changes made by the program, such as
    // the adding/removal of buttons, or repositioning of elements
    saveInterface();


    ErrorLogger.closeLog();

    System.exit(0);

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
    ((WSStatusBar)WSRepository.get("StatusBar")).setText(((JComponent)c).getToolTipText());
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
    ((WSStatusBar)WSRepository.get("StatusBar")).revertText();
    return true;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean promptToSave(){
    if (ChangeMonitor.check()){
      if (isFullVersion()){
        if (ChangeMonitor.popup()){
          // save changes
          setSidePanel("DirectoryList");
          ((SidePanel_DirectoryList)sidePanelHolder.getCurrentPanel()).changeControls("WritePanel",true);
          return true;
          }
        }
      }
    return false;
    }


/**
**********************************************************************************************
Does a soft reload, after options changes
**********************************************************************************************
**/
  public void reload(){
    fileListPanelHolder.reload();
    sidePanelHolder.reload();

    ((WSStatusBar)WSRepository.get("StatusBar")).setText(Language.get("Welcome"));

    validate();
    repaint();
    }


/**
**********************************************************************************************
Does a hard reload (rebuilds the entire interface after language/font/interface change)
**********************************************************************************************
**/
  public void rebuild(){
    WSPlugin[] plugins = WSPluginManager.group("DirectoryList").getPlugins();
    for (int i=0;i<plugins.length;i++){
      ((DirectoryListPanel)plugins[i]).constructInterface(new File(Settings.get("CurrentDirectory")));
      }

    plugins = WSPluginManager.group("Options").getPlugins();
    for (int i=0;i<plugins.length;i++){
      ((WSPanelPlugin)plugins[i]).buildObject(new XMLNode());
      }

    constructInterface();
    sidePanelHolder.rebuild();
    fileListPanelHolder.rebuild();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSidePanel(String name){
    Settings.set("AutoChangedToHexPreview","false");
    ((WSSidePanelHolder)WSRepository.get("SidePanelHolder")).loadPanel("SidePanel_" + name);
    WSPopup.showMessage("SidePanelChanged",true);
    }


/**
**********************************************************************************************
The main method that starts the program.
@param args the arguments passed in from the commandline.
**********************************************************************************************
**/
  public static void main(String[] args){
    MyProgram ge = MyProgram.getInstance();
    }

  }
