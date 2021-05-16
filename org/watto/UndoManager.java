////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                            WATTO STUDIOS JAVA PROGRAM TEMPLATE                             //
//                  Template Classes and Helper Utilities for Java Programs                   //
//                                    http://www.watto.org                                    //
//                                                                                            //
//                           Copyright (C) 2004-2008  WATTO Studios                           //
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

package org.watto;

import org.watto.component.*;

/**
**********************************************************************************************
This class allows for the control and management of undo/redo-related tasks. In escence,
another java program can use this class to include undo/redo support in their program without
the need for setting up the classes themselves.
<br><br>
While this class will manage and control the undo/redo tasks, the programmer must still create
their own UndoTask-extended classes for performing the undo operations, as each program will
have their own unique tasks that can be undone/redone and their own unique way of doing them.
**********************************************************************************************
**/
public class UndoManager {

  /** The tasks that can be undone/redone **/
  static UndoTask[] tasks;

  /** The number of tasks in the array **/
  static int numTasks = 0;

  /** The task that would be undone if the user called undo(). When redoing, the task to be redone is the
      currentTask+1. In other words, think of it as a pointer between the undo and redo task, where the
      pointer on the lower side (the actual value) is the undo, and the pointer on the upper side (+1)
      is the redo. **/
  static int currentTask = -1;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public UndoManager() {
    clear();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void add(UndoTask task){

    // remove any redoable tasks
    numTasks = currentTask+1;

    if (numTasks < tasks.length){
      // add the task to the end
      tasks[numTasks] = task;
      numTasks++;
      }
    else {
      // remove the last task, and then add the task to the end
      System.arraycopy(tasks,1,tasks,0,numTasks-1);
      tasks[numTasks-1] = task;
      }

    // set the current task to the newest task
    currentTask = numTasks-1;

    rebuildUndo();
    rebuildRedo(); // because the redo list is cleared
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static boolean canRedo(){
    if (currentTask+1 >= numTasks){
      return false;
      }
    else {
      return true;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static boolean canUndo(){
    if (currentTask < 0){
      return false;
      }
    else {
      return true;
      }
    }


/**
**********************************************************************************************
Clears the <i>tasks</i> and resets all global variables
**********************************************************************************************
**/
  public static void clear(){
    int numNewTasks = Settings.getInt("NumberOfUndoTasks");
    if (numNewTasks <= 0){
      numNewTasks = 20;
      }
    tasks = new UndoTask[numNewTasks];

    numTasks = 0;
    currentTask = -1;

    rebuildUndo();
    rebuildRedo();
    }


/**
**********************************************************************************************
Gets the tasks that are redoable from the current position. Task <i>0</i> is the <b>first</b>
task to be redone, task <i>n</i> is the <b>last</b> task to be redone.
**********************************************************************************************
**/
  public static UndoTask[] getRedoableTasks(){
    //if (currentTask == numTasks-1){
    //  return new UndoTask[0];
    //  }

    int numToCopy = numTasks - (currentTask+1);

    UndoTask[] undoTasks = new UndoTask[numToCopy];
    System.arraycopy(tasks,currentTask+1,undoTasks,0,numToCopy);
    return undoTasks;
    }


/**
**********************************************************************************************
Gets the tasks that are undoable from the current position, in reverse order. Task <i>0</i> is
the <b>last</b> task to be undone, task <i>n</i> is the <b>first</b> task to be redone.
**********************************************************************************************
**/
  public static UndoTask[] getUndoableTasks(){
    //if (currentTask == numTasks-1){
    //  return tasks;
    //  }

    int numToCopy = currentTask+1;

    UndoTask[] undoTasks = new UndoTask[numToCopy];
    System.arraycopy(tasks,0,undoTasks,0,numToCopy);

    // reverse the order of the undoable tasks
    int startPointer = 0;
    int endPointer = undoTasks.length - 1;

    while (startPointer < endPointer){
      UndoTask startTask = undoTasks[startPointer];
      undoTasks[startPointer] = undoTasks[endPointer];
      undoTasks[endPointer] = startTask;

      startPointer++;
      endPointer--;
      }

    return undoTasks;

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static int numRedo(){
    return numTasks - (currentTask+1);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static int numUndo(){
    return currentTask+1;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void rebuildRedo(){
    Object redoMenu = WSRepository.get("RedoMenu");
    if (redoMenu != null){
      ((WSRedoMenu)redoMenu).rebuild();
      }

    Object redoComboButton = WSRepository.get("RedoComboButton");
    if (redoComboButton != null){
      ((WSRedoComboButton)redoComboButton).rebuild();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void rebuildUndo(){
    Object undoMenu = WSRepository.get("UndoMenu");
    if (undoMenu != null){
      ((WSUndoMenu)undoMenu).rebuild();
      }

    Object undoComboButton = WSRepository.get("UndoComboButton");
    if (undoComboButton != null){
      ((WSUndoComboButton)undoComboButton).rebuild();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void redo(){
    if (currentTask+1 >= numTasks){
      return;
      }

    UndoTask task = tasks[currentTask+1];
    task.setDirection(UndoTask.DIRECTION_REDO);
    //new Thread(task).start();
    task.redo();
    currentTask++;

    rebuildUndo();
    rebuildRedo();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void redo(int numToRedo){
    if (currentTask+1 >= numTasks){
      return;
      }

    // setting the number of undos if there are too many given
    if (currentTask+1 + numToRedo > numTasks){
      numToRedo = numTasks - (currentTask+1);
      }

    // undoing each task
    for (int i=0;i<numToRedo;i++){
      redo();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void redo(UndoTask task){
    int numToRedo = 0;
    for (int i=currentTask+1;i<numTasks;i++){
      numToRedo++;
      if (tasks[i] == task){
        break;
        }
      }

    redo(numToRedo);
    }


/**
**********************************************************************************************
Resizes an array of type UndoTask
**********************************************************************************************
**/
  public static UndoTask[] resize(UndoTask[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    UndoTask[] dest = new UndoTask[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void undo(){
    if (currentTask < 0){
      return;
      }

    UndoTask task = tasks[currentTask];
    task.setDirection(UndoTask.DIRECTION_UNDO);
    //new Thread(task).start();
    task.undo();
    currentTask--;

    rebuildUndo();
    rebuildRedo();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void undo(int numToUndo){
    if (currentTask < 0){
      return;
      }

    // setting the number of undos if there are too many given
    if (numToUndo > currentTask+1){
      numToUndo = currentTask;
      }

    // undoing each task
    for (int i=0;i<numToUndo;i++){
      undo();
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void undo(UndoTask task){
    int numToUndo = 0;
    for (int i=currentTask;i>=0;i--){
      numToUndo++;
      if (tasks[i] == task){
        break;
        }
      }

    undo(numToUndo);
    }


  }

