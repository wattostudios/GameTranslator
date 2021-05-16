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
public class Task_ReadArchive implements UndoTask {

  /** The direction to perform in the thread **/
  int direction = 1;

  File path;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Task_ReadArchive(File path){
    this.path = path;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void redo(){
    if (!TaskManager.canDoTask()){
      return;
      }

    if (path == null || !path.exists()){
      WSPopup.showError("ReadArchive_FilenameMissing",true);
      TaskManager.stopTask();
      return;
      }

    if (path.isDirectory()){
      WSPopup.showError("ReadArchive_FileNotAnArchive",true);
      TaskManager.stopTask();
      return;
      }


    // ask to save the modified archive
    if (GameTranslator.getInstance().promptToSave()){
      return;
      }
    ChangeMonitor.set(false);


    // Progress dialog
    WSProgressDialog progress = WSProgressDialog.getInstance();
    progress.show(1,0,Language.get("Progress_ReadingArchive"));


    TaskManager.startTask();


      RatedPlugin[] plugins;

      ArchivePlugin selectedPlugin;
      // auto-detect a plugin for this archive
      plugins = PluginFinder.findPlugins(path,ArchivePlugin.class);
      if (plugins == null || plugins.length == 0){
        WSPopup.showError("ReadArchive_NoPluginsFound",true);
        TaskManager.stopTask();
        return;
        }

      java.util.Arrays.sort(plugins);

      //Archive.makeNewArchive();
      //if (ArchiveModificationMonitor.isModified()){
      //  // The user is saving, so do not continue with this method
      //  return;
      //  }

      // try to open the archive using each plugin and openArchive(File,Plugin)
      boolean archiveOpened = false;

      //String oldCurrentArchive = Settings.getString("CurrentArchive");
      Settings.set("CurrentArchive",path.getAbsolutePath());

      for (int i=0;i<plugins.length;i++){
        //System.out.println(plugins[i].getRating());

        // true, so it knows it is started within a current task
        Task_ReadArchiveWithPlugin task = new Task_ReadArchiveWithPlugin(path,plugins[i].getPlugin(),true);
        task.redo();
        archiveOpened = task.getResult();

        if (archiveOpened){
          i = plugins.length;
          Settings.addRecentFile(path);
          }
        else {
          }

        }

      ((FileListPanel)((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).getCurrentPanel()).reload();

      if (!archiveOpened){

        // Also shows this message if the scanner does not exist!
        WSPopup.showError("ReadArchive_NoPluginsFound",true);
        TaskManager.stopTask();
        return;
        //GameTranslator.getInstance().setPluginAllowedEvent();
        }
      else {
        //GameTranslator.getInstance().setPluginAllowedEvent();
        }


      //SidePanel_DirectoryList panel = (SidePanel_DirectoryList)WSRepository.get("SidePanel_DirectoryList");
      //panel.onOpenRequest();

      WSSidePanelHolder sidePanelHolder = (WSSidePanelHolder)WSRepository.get("SidePanelHolder");
      if (! sidePanelHolder.getCurrentPanelCode().equals("SidePanel_DirectoryList")){
        sidePanelHolder.reloadPanel();
        }


    TaskManager.stopTask();


    WSPopup.showMessage("ReadArchive_ArchiveOpened",true);

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

