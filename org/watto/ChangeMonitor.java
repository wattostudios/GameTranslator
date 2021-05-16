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

import java.io.*;

/**
**********************************************************************************************
A static class that provides a way to monitor the changes made by a program. For example, if
your program is a text editor, any typing in the document will make the text file "changed".
When you close the program, you want to check whether changes have been made, and if so, ask
the user whether they want to save or not. This class provides simple and convenient methods
to perform these tasks, including the display of a popup dialog for "do you want to save?"
**********************************************************************************************
**/
public class ChangeMonitor{

  /** whether a modification has occurred or not **/
  static boolean modified = false;

/**
**********************************************************************************************
Constructor
**********************************************************************************************
**/
  public ChangeMonitor() {
    }


/**
**********************************************************************************************
Has a modification occurred?
@return true if a modification has occurred, false if no modifications have taken place
**********************************************************************************************
**/
  public static boolean check() {
    return modified;
    }


/**
**********************************************************************************************
Displays the "Save Changes" popup
@return true if the user clicked the "yes save" button, false if the user chose the "no don't
        save" button
**********************************************************************************************
**/
  public static boolean popup() {
    String ok = WSPopup.showConfirm("SaveChanges");
    if (ok.equals(WSPopup.BUTTON_YES)){
      return true;
      }
    else {
      return false;
      }
    }


/**
**********************************************************************************************
Sets whether a modification has taken place or not
@param newModified the new modification value.
**********************************************************************************************
**/
  public static void set(boolean newModified) {
    modified = newModified;
    }


  }