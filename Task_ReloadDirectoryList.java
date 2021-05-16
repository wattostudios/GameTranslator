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
import org.watto.component.WSList;
import org.watto.component.WSProgressDialog;
import org.watto.component.WSRepository;

import java.io.File;
import java.io.FileFilter;
import java.util.Hashtable;
import javax.swing.DefaultListModel;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Task_ReloadDirectoryList implements UndoTask {

  /** The direction to perform in the thread **/
  int direction = 1;

  /** the directory to change to **/
  File directory;
  /** the filter for the files **/
  FileFilter filter;
  /** the list to display the files **/
  WSList list;

  boolean rememberSelections = true;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Task_ReloadDirectoryList(File directory, FileFilter filter, WSList list, boolean rememberSelections){
    this.directory = directory;
    this.filter = filter;
    this.list = list;
    this.rememberSelections = rememberSelections;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void redo(){
    // Remembering selections takes up more memory, and takes longer to do.
    // So we only want to remember selections if we really have to.
    if (rememberSelections){
      reloadWithSelections();
      }
    else {
      reloadWithoutSelections();
      }
    }



/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reloadWithSelections(){
    if (list == null){
      return;
      }

    // build a hashtable of the selected items
    Object[] selectedFiles = list.getSelectedValues();
    Hashtable<String,String> selected = new Hashtable<String,String>();
    for (int i=0;i<selectedFiles.length;i++){
      selected.put(((File)selectedFiles[i]).getAbsolutePath(),"!");
      }

    File[] files = directory.listFiles(filter);

    if (files == null){
      return;
      }

    DefaultListModel model = new DefaultListModel();

    int numAdded = 0;
    int numSelected = 0;
    int[] selectedIndexes = new int[files.length];

    for (int i=0;i<files.length;i++){
      if (files[i].isDirectory()){
        model.addElement(files[i]);
        if (selected.get(files[i].getAbsolutePath()) != null){
          selectedIndexes[numSelected] = numAdded;
          numSelected++;
          }
        numAdded++;
        }
      }

    for (int i=0;i<files.length;i++){
      if (! files[i].isDirectory()){
        model.addElement(files[i]);
        if (selected.get(files[i].getAbsolutePath()) != null){
          selectedIndexes[numSelected] = numAdded;
          numSelected++;
          }
        numAdded++;
        }
      }


    bypassListReloadBug();
    list.clearSelection();
    list.setModel(model);

    if (numSelected > 0){
      int[] selectedIndices = new int[numSelected];
      System.arraycopy(selectedIndexes,0,selectedIndices,0,numSelected);
      list.setSelectedIndices(selectedIndices);
      }

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reloadWithoutSelections(){
    if (list == null){
      return;
      }

    File[] files = directory.listFiles(filter);

    if (files == null){
      return;
      }

    DefaultListModel model = new DefaultListModel();

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

    bypassListReloadBug();
    list.clearSelection();
    list.setModel(model);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void bypassListReloadBug(){
    // if we don't set the cell height, a dump occurs in BasicListUI:1345
    int cellHeight = org.watto.plaf.AquanauticTheme.TEXT_HEIGHT+8;
    if (cellHeight < 17){ // 17 = 16 (icon height) + 1
      cellHeight = 17;
      }
    list.setFixedCellHeight(cellHeight);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void run(){
    if (direction == -1){
      undo();
      }
    else if (direction == 1){
      redo();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setDirection(int direction){
    this.direction = direction;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String toString(){
    Class cl = getClass();
    String name = cl.getName();
    Package pack = cl.getPackage();

    if (pack != null){
      name = name.substring(pack.getName().length()+1);
      }

    return Language.get(name);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void undo(){
    }


  }

