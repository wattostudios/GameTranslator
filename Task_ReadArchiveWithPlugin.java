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
import org.watto.component.*;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Task_ReadArchiveWithPlugin implements UndoTask {

  /** The direction to perform in the thread **/
  int direction = 1;

  File path;
  WSPlugin plugin;
  boolean result = false;

  // is this called from within an existing thread?
  // if so, don't call TaskManager or set up the WSProgressBar
  boolean withinThread = false;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Task_ReadArchiveWithPlugin(File path, WSPlugin plugin){
    this(path,plugin,false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Task_ReadArchiveWithPlugin(File path, WSPlugin plugin, boolean withinThread){
    this.path = path;
    this.plugin = plugin;
    this.withinThread = withinThread;

    result = false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public synchronized boolean getResult(){
    return result;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void redo(){
    try {
      result = false;

      if (path == null || !path.exists()){
        if (!withinThread){
          WSPopup.showError("ReadArchive_FilenameMissing",true);
          }
        result = false;
        return;
        }

      if (path.isDirectory()){
        if (!withinThread){
          WSPopup.showError("ReadArchive_FileNotAnArchive",true);
          }
        result = false;
        return;
        }

      if (plugin == null || ! (plugin instanceof ArchivePlugin)){
        result = false;
        return;
        }

      try {
        ArchivePlugin arcPlugin = (ArchivePlugin)plugin;

        if (!withinThread){
          // ask to save the modified archive
          if (GameTranslator.getInstance().promptToSave()){
            return;
            }
          ChangeMonitor.set(false);

          // Progress dialog
          WSProgressDialog progress = WSProgressDialog.getInstance();
          progress.show(1,0,Language.get("Progress_ReadingArchive"));

          TaskManager.startTask();
          }

        Resource[] resources = (arcPlugin).read(path);

        if (resources != null && resources.length > 0){
          //if (!ArchiveModificationMonitor.setModified(true)){
          //  return false;
          //  }
          Archive.makeNewArchive();

          Archive.setResources(resources);
          Archive.setReadPlugin(arcPlugin);
          Archive.setBasePath(path);
          Archive.setColumns(arcPlugin.getColumns());

          // now display the files that are in the archive - same as in Task_ReadArchive
          Settings.addRecentFile(path);
          ((FileListPanel)((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).getCurrentPanel()).reload();

          WSSidePanelHolder sidePanelHolder = (WSSidePanelHolder)WSRepository.get("SidePanelHolder");
          if (! sidePanelHolder.getCurrentPanelCode().equals("SidePanel_DirectoryList")){
            sidePanelHolder.reloadPanel();
            }
          else {
            ((SidePanel_DirectoryList)sidePanelHolder.getCurrentPanel()).onOpenRequest();
            }
          result = true;
          }
        else {
          result = false;
          }

        if (!withinThread){
          TaskManager.stopTask();
          }

        }
      catch (Throwable t){
        ErrorLogger.log(t);

        if (!withinThread){
          WSPopup.showError("ReadArchive_ReadWithPluginFailed",true);
          }

        result = false;
        return;
        }

      // check that, after opening, there is at least 1 file in the archive
      if (result == false || Archive.getNumFiles() <= 0){
        if (!withinThread){
          WSPopup.showError("ReadArchive_ReadWithPluginFailed",true);
          }
        result = false;
        return;
        }

      if (!withinThread){
        WSPopup.showMessage("ReadArchive_ArchiveOpened",true);
        }

      // clear out the undo/redo
      UndoManager.clear();

      result = true;

      }
    catch (Throwable t){
      ErrorLogger.log(t);
      result = false;
      }
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

