////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                      GAME TRANSLATOR                                       //
//                            Game Language Translation Assistant                             //
//                                 http://www.watto.org/trans                                 //
//                                                                                            //
//                           Copyright (C) 2006-2009  WATTO Studios                           //
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
- When editing a Integer such as an ID, the editor is right-aligned
- When saving to CSV or TabDelimited, the additional columns are cleared of their values
  - because the resources need to be converted over to Resource_CSV and the extra columns values stored in String[] array
  - Also happens when changing back from CSV to a different format
    - Maybe just have an Export/Import file list control panel on SidePanel_DirList? (uses export plugins from GE)

// REQUIRED
- Test PLUGIN_1848 and check...
  - Replace only, not write support
  - The text length does not include the null terminator - if it does, need to adjust write();
- Make the website
  - need to update the sf links (including buttons on left) to point to the correct project id!!!
  - update screenshots (installation)
  - Check all pages, links, and heading images
- Set up the website http://sourceforge.net/projects/gametranslator
- Edit the PAD file, and upload it to websites like SoftPedia

// PREFERRED

// OPTIONAL

// OTHER

// THINGS TO REMEMBER
- Source code headers should say GameTranslator
- In the language, change all... (including upper/lower case!)
  - "archive" to "language pack"
  - "file" to "text"
  - "Game Extractor" to "Game Translator"
  - "FileList" to "TextList"


////////////////////////////////////////////////////////////////////////////////////////////////
//                              RELEASE AND PACKAGING INFORMATION                             //
////////////////////////////////////////////////////////////////////////////////////////////////

- Edit the PAD file
- Make sure to add any new files into the install maker script! (NOT NEW CLASSES! Just others)
  - Help / help images
  - Languages
- Remember to package up any changed language files too!

*** *** ***
- Include all classpath items in the release zip (org* for all, com* and net* for Full Version only)
*** *** ***

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
public class GameTranslator extends WSProgram implements WSClickableInterface,
                                                         WSResizableInterface,
                                                         WSWindowFocusableInterface {


  /** A singleton holder for the GameTranslator program, so other classes can directly access
      the same instance **/
  static GameTranslator instance = new GameTranslator();

  WSSidePanelHolder sidePanelHolder;
  WSFileListPanelHolder fileListPanelHolder;


/**
**********************************************************************************************
Not to be used - use "GameTranslator.getInstance()" instead of "new GameTranslator()"
**********************************************************************************************
**/
  public GameTranslator(){
    // DONT PUT THIS LINE HERE, CAUSE IT IS DONE AUTOMATICALLY BY super()
    // EVEN THOUGH super() ISNT CALLED, IT IS RUN BECAUSE THIS CONSTRUCTOR EXTENDS WSProgram
    // AND THUS MUST RUN super() BEFORE THIS CLASS CAN BE BUILT.
    // HAVING THIS LINE CAUSES THE PROCESSES TO BE RUN TWICE, ENDING UP WITH 2 OF
    // EACH PLUGIN, AND STUPID THINGS LIKE THAT.
    //buildProgram(this);


    // add the window focus listener, so it wil reload the dirpanel when focus has regained
    addWindowFocusListener(new WSWindowFocusableListener(this));

    setIconImage(new ImageIcon(getClass().getResource("images/WSFrame/icon.png")).getImage());

    // Construct the default archive
    splash.setMessage("Archive");

    // Construct the default archive
    new Archive(); // required, to set up the icons, etc.

    ((WSStatusBar)WSRepository.get("StatusBar")).setText(Language.get("Welcome"));

    // close the splash screen
    splash.dispose();


    pack();
    setExtendedState(JFrame.MAXIMIZED_BOTH);

    fileListPanelHolder.setMinimumSize(new Dimension(0,0));
    sidePanelHolder.setMinimumSize(new Dimension(0,0));

    WSSplitPane mainSplit = (WSSplitPane)WSRepository.get("MainSplit");
    mainSplit.setDividerSize(5);

    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    setVisible(true);

    }


/**
**********************************************************************************************
Returns the singleton instance of the program. This allows other classes to all address the
same instance of the interface, rather than separate instances.
@return the singleton <i>instance</i> of GameTranslator
**********************************************************************************************
**/
  public static GameTranslator getInstance(){
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

    sidePanelHolder = (WSSidePanelHolder)WSRepository.get("SidePanelHolder");
    sidePanelHolder.loadPanel(Settings.get("CurrentSidePanel"));

    fileListPanelHolder = (WSFileListPanelHolder)WSRepository.get("FileListPanelHolder");
    fileListPanelHolder.loadPanel(Settings.get("FileListView"));
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

**********************************************************************************************
**/
  public void makeNewArchive(){
    Task_NewArchive task = new Task_NewArchive();
    task.setDirection(UndoTask.DIRECTION_REDO);
    task.redo();
    WSPopup.showMessage("ReadArchive_MakeNewArchive",true);
    //new Thread(task).start();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void openSidePanelOnStartup(){
    WSPanel panel = sidePanelHolder.getCurrentPanel();
    if (panel instanceof WSPanelPlugin){
      ((WSPanelPlugin)panel).onOpenRequest();
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

    System.out.println("GT: " + code);

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
      if (code.equals("NewArchive")){
        makeNewArchive();
        }
      else if (code.equals("ReadArchive")){
        setSidePanel("DirectoryList");
        ((SidePanel_DirectoryList)sidePanelHolder.getCurrentPanel()).changeControls("ReadPanel",false);
        }
      else if (code.equals("WriteArchive")){
        setSidePanel("DirectoryList");
        ((SidePanel_DirectoryList)sidePanelHolder.getCurrentPanel()).changeControls("WritePanel",true);
        }
      else if (code.equals("ConvertArchive")){
        setSidePanel("DirectoryList");
        ((SidePanel_DirectoryList)sidePanelHolder.getCurrentPanel()).changeControls("ConvertPanel",true);
        }
      else if (code.equals("CloseProgram")){
        onClose();
        }
      else if (code.equals("AddResources")){
        setSidePanel("DirectoryList");
        ((SidePanel_DirectoryList)sidePanelHolder.getCurrentPanel()).changeControls("ModifyPanel",true);
        }
      else if (code.equals("RemoveResources")){
        setSidePanel("DirectoryList");
        ((SidePanel_DirectoryList)sidePanelHolder.getCurrentPanel()).changeControls("ModifyPanel",true);
        }
      else if (code.equals("Search")){
        setSidePanel("Search");
        }
      else if (code.equals("SelectResources_All")){
        fileListPanelHolder.selectAll();
        WSPopup.showMessage("SelectResources_All",true);
        }
      else if (code.equals("SelectResources_None")){
        fileListPanelHolder.selectNone();
        WSPopup.showMessage("SelectResources_None",true);
        }
      else if (code.equals("SelectResources_Inverse")){
        fileListPanelHolder.selectInverse();
        WSPopup.showMessage("SelectResources_Inverse",true);
        }
      else if (code.equals("Options")){
        setSidePanel("Options");
        }
      else if (code.equals("PluginList")){
        setSidePanel("PluginList");
        }
      else if (code.equals("Information")){
        setSidePanel("Information");
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
    if (GameTranslator.getInstance().promptToSave()){
      return false;
      }
    ChangeMonitor.set(false);



    deleteTempFiles(new File("temp"));


    // do onClose() on FileListPanel and SidePanel
    sidePanelHolder.onCloseRequest();
    fileListPanelHolder.onCloseRequest();

    // Remember the location of the main split divider
    WSSplitPane mainSplit = (WSSplitPane)WSRepository.get("MainSplit");
    double splitLocationOld = Settings.getDouble("DividerLocation");
    double splitLocationNew = (double)(mainSplit.getDividerLocation()) / (double)(mainSplit.getWidth());
    double diff = splitLocationOld - splitLocationNew;
    if (diff > 0.01 || diff < -0.01){
      // only set if the change is large.
      // this gets around the problem with the split slowly moving left over each load
      Settings.set("DividerLocation",splitLocationNew);
      }

    // Save settings files
    //XMLWriter.write(new File(Settings.getString("ToolbarFile")),toolbar.constructXMLNode());
    //XMLWriter.write(new File(Settings.getString("MenuBarFile")),menubar.constructXMLNode());
    Settings.saveSettings();

    //System.out.println(((WSComponent)getContentPane().getComponent(0)).getCode());
    //XMLWriter.write(new File("temp/test.wsd"),WSRepository.get("MainWindowFrame").buildXML());

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
    //statusbar.setText(((JComponent)c).getToolTipText());
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
    //statusbar.revertText();
    ((WSStatusBar)WSRepository.get("StatusBar")).revertText();
    return true;
    }


/**
**********************************************************************************************
The event that is triggered from a WSResizableListener when a component is resized
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onResize(JComponent c, java.awt.event.ComponentEvent e){
    if (c instanceof WSComponent){
      String code = ((WSComponent)c).getCode();
      if (code.equals("MainSplit")){
        // reposition the splitpane divider when the splitpane changes sizes
        double splitPos = Settings.getDouble("DividerLocation");
        if (splitPos < 0 || splitPos > 1){
          splitPos = 0.7;
          }

        //System.out.println("Before: " + splitPos);
        ((WSSplitPane)c).setDividerLocation(splitPos);
        //System.out.println("After: " + ((double)((WSSplitPane)c).getDividerLocation() / ((WSSplitPane)c).getWidth()));
        }
      }
    return true;
    }


/**
**********************************************************************************************
The event that is triggered from a WSWindowFocusableListener when a component gains focus
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onWindowFocus(java.awt.event.WindowEvent e){
    if (sidePanelHolder.getCurrentPanelCode().equals("SidePanel_DirectoryList")){
      // reload the directory list
      ((SidePanel_DirectoryList)sidePanelHolder.getCurrentPanel()).reloadDirectoryList();
      }
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
    //menubar.reload();
    //toolbar.reload();

    fileListPanelHolder.reload();

    //FileListPanelManager.reloadCurrentPanel();

    //SidePanelManager.reloadPanels();
    sidePanelHolder.reload();

    //repositionToolbar();
    //repositionSidePanel();

    ((WSStatusBar)WSRepository.get("StatusBar")).setText(Language.get("Welcome"));

    //SwingUtilities.updateComponentTreeUI(this);
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
    GameTranslator gt = GameTranslator.getInstance();
    gt.makeNewArchive();
    gt.openSidePanelOnStartup();

    if (args.length > 0){
      File fileToOpen = new File(args[0]);
      if (fileToOpen.exists()){
        ((SidePanel_DirectoryList)WSRepository.get("SidePanel_DirectoryList")).readArchive(fileToOpen);
        //sidePanelHolder.reloadPanel();
        }
      }
    }

  }
