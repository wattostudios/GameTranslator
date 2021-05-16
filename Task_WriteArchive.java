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
import org.watto.component.WSPopup;
import org.watto.component.WSRepository;
import org.watto.component.WSProgressDialog;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Task_WriteArchive implements UndoTask {

  /** The direction to perform in the thread **/
  int direction = 1;

  File path;
  ArchivePlugin plugin;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Task_WriteArchive(File path, ArchivePlugin plugin){
    this.path = path;
    this.plugin = plugin;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void redo(){
    if (!TaskManager.canDoTask()){
      return;
      }


    WSFileListPanelHolder fileListPanelHolder = (WSFileListPanelHolder)WSRepository.get("FileListPanelHolder");
    fileListPanelHolder.stopInlineEditing();


    // Progress dialog
    WSProgressDialog progress = WSProgressDialog.getInstance();
    progress.show(2,0,Language.get("Progress_WritingArchive")); // 2 progress bars
    progress.setIndeterminate(true,0); // first 1 is indeterminate
    progress.setMaximum(Archive.getNumFiles(),1); // second one shows how many files are done


    TaskManager.startTask();


    Archive.setOldReadPlugin(Archive.getReadPlugin());

    plugin.write(Archive.getResources(),path);
    Settings.addRecentFile(path);

    Archive.setReadPlugin(plugin);

    ((WSDirectoryListHolder)WSRepository.get("SidePanel_DirectoryList_DirectoryListHolder")).reload();
    //((FileListPanel)((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).getCurrentPanel()).reload();
    fileListPanelHolder.reload();


    TaskManager.stopTask();


    ChangeMonitor.set(false);
    WSPopup.showMessage("WriteArchive_ArchiveSaved",true);

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
    if (!TaskManager.canDoTask()){
      return;
      }
    }


  }

