////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                      FILE MANIPULATOR                                      //
//                Generic, Multi-Purpose File Reading and Writing Java Classes                //
//                                    http://www.watto.org                                    //
//                                                                                            //
//                           Copyright (C) 2002-2008  WATTO Studios                           //
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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
**********************************************************************************************
A FileManipulatorOutputStream that, when close(), does not close the underlying FileManipulator
**********************************************************************************************
**/

public class FileManipulatorUnclosableOutputStream extends FileManipulatorOutputStream{


/**
**********************************************************************************************

**********************************************************************************************
**/
  public FileManipulatorUnclosableOutputStream(File path){
    super(path);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public FileManipulatorUnclosableOutputStream(FileManipulator fm){
    super(fm);
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void close() throws IOException{
    //fm.close();
    }


  }