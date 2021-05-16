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

import org.watto.component.*;
import org.watto.*;

import javax.swing.table.*;
import javax.swing.event.*;

public class FileListModel_Table implements FileListModel,TableModel {

Resource[] resources;
WSTableColumn[] columns;
ArchivePlugin readPlugin;

/**
**********************************************************************************************

**********************************************************************************************
**/
  public FileListModel_Table() {
    reload();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void addTableModelListener(TableModelListener tml) {
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Class getColumnClass(int column) {
    if (column >= columns.length){
      return String.class;
      }
    return columns[column].getType();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getColumnCount() {
    return columns.length;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getColumnName(int column) {
    return columns[column].getName();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource getResource(int row) {
    if (row < resources.length){
      return resources[row];
      }
    else {
      return null;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getRowCount() {
    return resources.length;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getValueAt(int row, int column) {
    if (row == -1 || column == -1){
      return null;
      }
    if (column >= columns.length){
      return "";
      }
    return readPlugin.getColumnValue(resources[row],columns[column].getCharCode());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isCellEditable(int row, int column) {
    if (columns[column].isEditable()){
      return true;
      }
    return false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reload() {
    resources = Archive.getResources();
    readPlugin = Archive.getReadPlugin();
    columns = readPlugin.getViewingColumns();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reload(Resource[] resources) {
    this.resources = resources;
    readPlugin = Archive.getReadPlugin();
    columns = readPlugin.getViewingColumns();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void removeTableModelListener(TableModelListener tml) {
    }


/**
**********************************************************************************************
copied kinda from Task_RenameFiles in GameExtractor
**********************************************************************************************
**/
  public void setValueAt(Object value, int row, int column) {
    char charCode = columns[column].getCharCode();

    Object oldValue = readPlugin.getColumnValue(resources[row],charCode);
    if (value.equals(oldValue)){
      // no change
      return;
      }

    if (charCode == 'T' && Settings.getBoolean("AddTextChangesIntoUndo")){
      // change and add into the undo

      Task_ChangeText task = new Task_ChangeText(resources[row],value.toString());
      task.setDirection(UndoTask.DIRECTION_REDO);
      task.redo();
      UndoManager.add(task);

      }
    else {
      // change without adding to the undo
      readPlugin.setColumnValue(resources[row],charCode,value);
      ChangeMonitor.set(true);

      // reload, to change the icon
      ((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).reload();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void sortResources(int column) {
    if (columns[column].isSortable()){
      resources = FileListSorter.sort(columns[column]);
      }
    }


  }