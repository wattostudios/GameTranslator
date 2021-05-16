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

import org.watto.ErrorLogger;
import org.watto.Language;
import org.watto.component.WSProgressDialog;

import java.awt.Cursor;

/**
**********************************************************************************************
Controls Tasks and the Progress bar display, such as making sure only 1 task can happen at a
time (ie Thread control)
**********************************************************************************************
**/
public class TaskManager {

  static boolean taskRunning = false;
  static WSProgressDialog progress = WSProgressDialog.getInstance();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public TaskManager() {
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static boolean canDoTask() {
    return (taskRunning == false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void setTaskRunning(boolean running) {
    try {
      if (taskRunning && running){
        throw new Exception("A task is already running!");
        }
      taskRunning = running;
      }
    catch (Throwable t){
      ErrorLogger.log(t);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void startTask() {
    setTaskRunning(true);
    progress.setVisible(true);

    Cursor cursor = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
    progress.setCursor(cursor);
    GameTranslator.getInstance().setCursor(cursor);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void stopTask() {
    setTaskRunning(false);
    progress.setVisible(false);

    Cursor cursor = Cursor.getDefaultCursor();
    progress.setCursor(cursor);
    GameTranslator.getInstance().setCursor(cursor);
    }



  }

