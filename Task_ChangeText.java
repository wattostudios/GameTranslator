////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                       GAME EXTRACTOR                                       //
//                               Extensible Game Archive Editor                               //
//                                http://www.watto.org/extract                                //
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
public class Task_ChangeText implements UndoTask {

  /** The direction to perform in the thread **/
  int direction = 1;

  /** the resources to rename **/
  Resource resource;

  /** The original contents of the resources that were changed.
      These are clone()s so that they have a separate reference to the original resources **/
  Resource originalResource;

  String newValue = "";


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Task_ChangeText(Resource resource, String newValue){
    this.resource = resource;
    this.newValue = newValue;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void redo(){
    if (!TaskManager.canDoTask()){
      return;
      }

    if (resource == null){
      return;
      }


    originalResource = (Resource)resource.clone();
    resource.setTranslated(newValue);

    ((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).reload();


    ChangeMonitor.set(true);

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

    return Language.get(name).replace("&newValue&","\""  + newValue + "\"");
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void undo(){
    if (!TaskManager.canDoTask()){
      return;
      }

    if (originalResource == null){
      return;
      }



    ((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).stopInlineEditing();

    // copy the details from the clone into the actual resource
    resource.copyFrom(originalResource);


    ((WSFileListPanelHolder)WSRepository.get("FileListPanelHolder")).reload();

    }


  }

