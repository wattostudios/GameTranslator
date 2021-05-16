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
public class Task_AddFiles implements UndoTask {

  /** The direction to perform in the thread **/
  int direction = 1;

  int numFiles;
  /** The resources that hold the files that were added **/
  Resource[] resources;
  /** the number of files before these new files were added to the archive **/
  int originalNumFiles = -1;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Task_AddFiles(int numFiles){
    this.numFiles = numFiles;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void redo(){
    if (!TaskManager.canDoTask()){
      return;
      }

    if (numFiles <= 0){
      WSPopup.showError("AddFiles_NegativeNumberOfFiles",true);
      return;
      }


    // Progress dialog
    WSProgressDialog progress = WSProgressDialog.getInstance();
    progress.show(1,0,Language.get("Progress_AddingFiles"));
    progress.setIndeterminate(true);


    TaskManager.startTask();


      ((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).stopInlineEditing();

      originalNumFiles = Archive.getNumFiles();

      resources = Archive.addResources(numFiles);

      ((FileListPanel)((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).getCurrentPanel()).reload();


    TaskManager.stopTask();


    ChangeMonitor.set(true);
    WSPopup.showMessage("AddFiles_FilesAdded",true);

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

    return Language.get(name).replace("&number&",""+numFiles);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void undo(){
    if (!TaskManager.canDoTask()){
      return;
      }

    if (originalNumFiles == -1){
      return;
      }


    // Progress dialog
    WSProgressDialog progress = WSProgressDialog.getInstance();
    progress.show(1,0,Language.get("Progress_AddingFiles_Undo"));
    progress.setIndeterminate(true);


    TaskManager.startTask();


      ((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).stopInlineEditing();

      //Archive.setNumFiles(originalNumFiles); // this doesn't work if the list is sorted (removes the wrong files)
      Archive.removeResources(resources);


    TaskManager.stopTask();


    ((FileListPanel)((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).getCurrentPanel()).reload();

    WSPopup.showMessage("AddFiles_FilesRemoved",true);
    }


  }

