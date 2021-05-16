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

package org.watto.manipulator;

import java.io.*;

/**
**********************************************************************************************
A file filter that can be set to show only files with a particular extension, or show all
files by using the wildcard as the extension.
**********************************************************************************************
**/
public class FileExtensionFilter implements FilenameFilter, FileFilter{

  /** the extension to allow **/
  String extension = "*";

/**
**********************************************************************************************
Constructor with an extension to display
@param extension the extension of the files that you want to allow
**********************************************************************************************
**/
  public FileExtensionFilter(String extension){
    this.extension = extension;
    }


/**
**********************************************************************************************
Whether the file with the given <i>dir</i> and <i>name</i> is allowed by this filter
@param dir the directory that contains the file
@param name the name of the file
@return true if the file is allowed, false if not
**********************************************************************************************
**/
  public boolean accept(File dir, String name){
    if (extension.equals("*")){
      return true;
      }

    int dotPos = name.lastIndexOf(".");
    if (dotPos < 0){
      return false;
      }

    String fileExt = name.substring(dotPos+1);
    if (fileExt.equals(extension)){
      return true;
      }

    return false;
    }


/**
**********************************************************************************************
Whether the <i>file</i> is allowed by this filter
@param file the file to check
@return true if the file is allowed, false if not
**********************************************************************************************
**/
  public boolean accept(File path){
    if (!path.isFile()){
      return true;
      }

    return accept(new File(path.getParent()),path.getName());
    }


  }