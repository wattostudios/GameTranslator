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

import org.watto.*;
import org.watto.event.*;
import org.watto.component.*;
import org.watto.xml.*;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.*;
import javax.swing.border.*;
import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.datatransfer.Transferable;

public class DirectoryList_DirectoryList extends DirectoryListPanel implements WSClickableInterface,
                                                                               WSKeyableInterface,
                                                                               WSTransferableInterface,
                                                                               WSRightClickableInterface {

  DirectoryListDrivesComboBox drives;
  WSList list;
  WSButton upButton;

  /** for knowing if the directory is just reloaded, or changed to a different one **/
  File currentDirectory = null;

  FileFilter filter = null;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public DirectoryList_DirectoryList() {
    super("List");
    constructInterface(new File(Settings.get("CurrentDirectory")));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public DirectoryListPanel getNew() {
    return new DirectoryList_DirectoryList();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean onClick(JComponent c, java.awt.event.MouseEvent e){

    if (c == upButton){
      changeToParent();
      return true;
      }
    else if (c instanceof WSMenuItem) {
      String code = ((WSMenuItem)c).getCode();

      if (code.equals("DirectoryList_RightClick_Refresh")){
        changeDirectory(currentDirectory);
        }
      else if (code.equals("DirectoryList_RightClick_ParentDirectory")){
        changeToParent();
        }
      else if (code.equals("DirectoryList_RightClick_OpenDirectory")){
        File directory = (File)list.getSelectedValue();
        changeDirectory(directory);
        }
      else {
        File file = (File)list.getSelectedValue();
        SidePanel_DirectoryList dirList = (SidePanel_DirectoryList)WSRepository.get("SidePanel_DirectoryList");

        if (code.equals("DirectoryList_RightClick_ReadArchive_Normal")){
          dirList.changeControls("ReadPanel",false);
          dirList.readArchive(file);
          }
        else if (code.equals("DirectoryList_RightClick_ReadArchive_OpenWith")){
          dirList.changeControls("ReadPanel",false);
          dirList.readArchive(file);
          }
        else {
          return false;
          }
        }
      return true;
      }

    return false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean onKeyPress(JComponent c, KeyEvent e){
    if (c == list){

      // check for tthe BACKSPACE or ENTER keys
      int keyType = e.getKeyCode();
      if (keyType == KeyEvent.VK_BACK_SPACE){
        // go to the parent directory
        changeToParent();
        return true;
        }
      else if (keyType == KeyEvent.VK_ENTER){
        // enter a directory, or open the file
        File selectedFile = (File)list.getSelectedValue();
        if (selectedFile == null){
          return false;
          }
        else if (selectedFile.isDirectory()){
          loadDirectory(selectedFile);
          }
        else {
          // trigger a double-click (which is passed to the SidePanel_DirectoryList for processing normally)
          ((WSPanel)getParent()).processEvent(new MouseEvent(list,MouseEvent.MOUSE_CLICKED,0,0,0,0,2,false,MouseEvent.BUTTON1));
          }
        return true;
        }

      // move to the next file starting with this letter

      char keyCode = e.getKeyChar();
      if (keyCode == KeyEvent.CHAR_UNDEFINED){
        return false; // not a letter or number
        }
      keyCode = (""+keyCode).toLowerCase().charAt(0);
      char keyCodeCaps = (""+keyCode).toUpperCase().charAt(0);

      ListModel model = list.getModel();

      int numFiles = model.getSize();
      int selectedIndex = list.getSelectedIndex() + 1;

      if (selectedIndex >= numFiles){
        selectedIndex = 0;
        }

      // search the bottom half of the list
      for (int i=selectedIndex;i<numFiles;i++){
        String filename = ((File)model.getElementAt(i)).getName();
        char currentChar = filename.charAt(0);
        if (currentChar == keyCode || currentChar == keyCodeCaps){
          list.setSelectedIndex(i);
          list.ensureIndexIsVisible(i);
          return false; // still want to pass the event through anyway
          }
        }

      if (selectedIndex == 0){
        // we started searching from the start of the list, so we don't want to re-search
        return false;
        }

      //  search the top half of the list, if not found in the bottom half.
      for (int i=0;i<=selectedIndex;i++){
        String filename = ((File)model.getElementAt(i)).getName();
        char currentChar = filename.charAt(0);
        if (currentChar == keyCode || currentChar == keyCodeCaps){
          list.setSelectedIndex(i);
          list.ensureIndexIsVisible(i);
          return false; // still want to pass the event through anyway
          }
        }

      }

    return false;

    }


/**
**********************************************************************************************
The event that is triggered from a WSRightClickableListener when a right click occurs
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onRightClick(JComponent c, java.awt.event.MouseEvent e){
    if (c == list){
      Point p = e.getPoint();

      list.setSelectedIndex(list.locationToIndex(p));
      File selectedFile = (File)list.getSelectedValue();
      if (selectedFile == null){
        return false;
        }

      WSPopupMenu menu = new WSPopupMenu(XMLReader.readString("<WSPopupMenu></WSPopupMenu>"));

      menu.add(new WSMenuItem(XMLReader.readString("<WSMenuItem code=\"DirectoryList_RightClick_OpenDirectory\" />")));
      menu.add(new WSMenuItem(XMLReader.readString("<WSMenuItem code=\"DirectoryList_RightClick_ParentDirectory\" />")));
      menu.add(new WSMenuItem(XMLReader.readString("<WSMenuItem code=\"DirectoryList_RightClick_Refresh\" />")));

      menu.show(list,(int)p.getX() - 10,(int)p.getY() - 10);

      return true;
      }

    return false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void changeToParent() {

    File current = (File)drives.getSelectedItem();
    File parent = current.getParentFile();
    if (parent != null){
      Settings.set("CurrentDirectory",parent.getAbsolutePath());
      changeDirectory(parent);
      }

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void constructInterface(){
    constructInterface(new File(new File("").getAbsolutePath()));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void constructInterface(File directory) {
    removeAll();

    drives = new DirectoryListDrivesComboBox();
    drives.setBorder(new EmptyBorder(0,0,0,0));
    drives.addActionListener(new DirectoryListDirectoryListDriveChangeListener(this));

    upButton = new WSButton(XMLReader.readString("<WSButton code=\"ParentDirectory\" showText=\"false\" />"));

    list = new WSList(XMLReader.readString("<WSList code=\"DirectoryList\" />"));
    list.setLayoutOrientation(WSList.VERTICAL_WRAP);
    list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    list.addMouseListener(new DirectoryListDirectoryListDirectoryDoubleClickListener(this));
    list.addMouseListener(new DirectoryListDirectoryListFileDoubleClickListener(this));
    list.addMouseListener(new DirectoryListDirectoryListFileSingleClickListener(this));
    list.setCellRenderer(new DirectoryListDirectoryListCellRenderer());

    list.setDragEnabled(true);
    list.addMouseMotionListener(new WSTransferableListener(this));


    JScrollPane listScroll = new JScrollPane(list);
    listScroll.setViewportBorder(new EmptyBorder(0,0,0,0));

    setLayout(new BorderLayout(1,1));
    add(listScroll,BorderLayout.CENTER);

    JPanel drivePanel = new JPanel(new BorderLayout(1,1));
    drivePanel.setBorder(new EmptyBorder(0,0,0,0));
    drivePanel.add(drives,BorderLayout.CENTER);
    drivePanel.add(upButton,BorderLayout.EAST);

    add(drivePanel,BorderLayout.NORTH);


    if (directory.exists()){
      changeDirectory(directory);
      }
    else {
      changeDirectory(new File(new File("").getAbsolutePath()));
      }


    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public File getCurrentDirectory(){
    return (File)drives.getSelectedItem();
    }


/**
**********************************************************************************************
  Makes a fresh root and calls reloadTree()
**********************************************************************************************
**/
  public void loadDirectory(File directory) {
    if (currentDirectory != null && currentDirectory.getAbsolutePath().equals(directory.getAbsolutePath())){
      loadDirectory(directory,true);
      }
    else {
      loadDirectory(directory,false);
      }
    }


/**
**********************************************************************************************
  Makes a fresh root and calls reloadTree()
**********************************************************************************************
**/
  public void loadDirectory(File directory, boolean rememberSelections) {
    if (! directory.exists() || ! directory.isDirectory()){
      return;
      }

    // if the directory hasn't changed, we want to re-select all the items that are selected,
    // after the refresh has happened
    /*
    File[] selectedFiles = new File[0];
    if (getCurrentDirectory().equals(directory)){
      selectedFiles = getAllSelectedFiles();
      }
    */

    currentDirectory = directory;

    /*
    if (rememberSelections){
      System.out.println("REMEMBER");
      }
    else {
      System.out.println("FORGET");
      }
    try {
      throw new Exception();
      }
    catch (Throwable t){
      t.printStackTrace();
      }
    */

    // sets the statics in the renderer so that it paints the list quickly
    new DirectoryListDirectoryListCellRenderer();
    new DirectoryListDrivesComboBoxCellRenderer();

    Task_ReloadDirectoryList task = new Task_ReloadDirectoryList(directory,filter,list,rememberSelections);
    task.setDirection(UndoTask.DIRECTION_REDO);
    new Thread(task).start();

    /*
    DefaultListModel model = new DefaultListModel();


    File[] files = directory.listFiles(filter);

    if (files == null){
      return;
      }

    for (int i=0;i<files.length;i++){
      if (files[i].isDirectory()){
        model.addElement(files[i]);
        }
      }

    for (int i=0;i<files.length;i++){
      if (! files[i].isDirectory()){
        model.addElement(files[i]);
        }
      }


    reloadList(model);


    setSelectedFiles(selectedFiles);
    */

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSelectedFiles(File[] files) {
    if (files.length <= 0){
      return;
      }

    File currentSelection = files[0];

    DefaultListModel model = (DefaultListModel)list.getModel();
    int numInModel = model.getSize();

    if (files.length > 1){
      // select all other files first

      // first, stop the current file from being selected
      int numSelected = files.length-1;
      files[0] = files[numSelected];

      // now look for all other files
      for (int i=0;i<numInModel;i++){
        for (int j=0;j<numSelected;j++){
          if (model.getElementAt(i).equals(files[j])){
            // found a file that needs to be selected
            list.addSelectionInterval(i,i);

            // now reshuffle the files[] array so that it is smaller to read through (quicker)
            numSelected--;
            files[j] = files[numSelected];

            // finish this loop
            j = numSelected;
            }
          }
        }
      }

    // now select the current file
    for (int i=0;i<numInModel;i++){
      if (model.getElementAt(i).equals(currentSelection)){
        // found the currently selected file
        list.addSelectionInterval(i,i);
        }
      }

    }


/**
**********************************************************************************************
  Makes a fresh root and calls reloadTree()
**********************************************************************************************
**/
  public void changeDirectory(File directory) {
    if (! directory.exists() || ! directory.isDirectory()){
      return;
      }

    Settings.set("CurrentDirectory",directory.getAbsolutePath());

    drives.loadDirectory(directory);

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public File getSelectedFile() {
    Object selectedFile = list.getSelectedValue();
    if (selectedFile == null){
      return new File(Settings.get("CurrentDirectory"));
      }
    else {
      return (File)selectedFile;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public File[] getAllSelectedFiles() {
    Object[] files = list.getSelectedValues();
    File[] selections = new File[files.length];

    for (int i=0;i<files.length;i++){
      selections[i] = (File)files[i];
      }

    return selections;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reload() {
    rebuildList((File)drives.getSelectedItem());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reloadList(DefaultListModel model) {
    list.setModel(model);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void scrollToSelected() {
    int height = list.getHeight();
    int cellHeight = list.getFixedCellHeight();
    list.setVisibleRowCount(height/cellHeight);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setMatchFilter(FileFilter filter) {
    if (filter != this.filter){
      this.filter = filter;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void rebuildList(File directory) {
    loadDirectory(directory);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setMultipleSelection(boolean multi) {
    if (multi){
      list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      }
    else {
      list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      }
    }


/**
**********************************************************************************************
Checks the current directory when it is being repainted incase any files are added/removed
**********************************************************************************************
**/
  public void checkFilesExist() {
    File currentDirectory = (File)drives.getSelectedItem();

    if (!currentDirectory.exists()){
      // if the dir was removed, change to the program directory
      changeDirectory(new File(new File("").getAbsolutePath()));
      return;
      }

    ListModel listModel = list.getModel();

    // check for same number of files
    if (currentDirectory.listFiles(filter).length != listModel.getSize()){
      changeDirectory(currentDirectory);
      return;
      }

    // check for deleted files (occurs when a file was deleted and replaced by another, so
    // there is still the same number of files in the dir)
    for (int i=0;i<listModel.getSize();i++){
      if (!(((File)listModel.getElementAt(i)).exists())){
        changeDirectory(currentDirectory);
        return;
        }
      }

    }


/**
**********************************************************************************************
Creates a TransferHandler for this component, allowing it to be dragged.
@param c the component that will be dragged
@param e the dragging event
@return the TransferHandler for this component
**********************************************************************************************
**/
  public TransferHandler onDrag(JComponent c, MouseEvent e){
    return null;
    }


/**
**********************************************************************************************
Drops the transferable object from the component
@param t the transferred data
@return true if the event was handled
**********************************************************************************************
**/
  public boolean onDrop(Transferable t){
    return true;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void requestFocus(){
    list.requestFocus();
    }


  }