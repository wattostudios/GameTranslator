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
  An OutputStream that is compatable with all Java classes and methods that use streams for file
  writing. This is basically an interface between the Java standards and the FileManipulator.
  See the OutputStream API for details of each method.
**********************************************************************************************
**/

public class FileManipulatorOutputStream extends OutputStream{

  FileManipulator fm;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public FileManipulatorOutputStream(File path){
    this.fm = new FileManipulator(path,"rw");
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public FileManipulatorOutputStream(FileManipulator fm){
    this.fm = fm;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void close() throws IOException{
    fm.close();
    }


/**
**********************************************************************************************
DON'T ACTUALLY WANT TO FLUSH THE BUFFER - THE UNDERLYING FM CONTROLS THAT ANYWAY!!!
**********************************************************************************************
**/
  public void flush(){
    //fm.flush();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void write(byte[] byteArray) throws IOException{
    fm.write(byteArray);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void write(byte[] byteArray, int offset, int length) throws IOException{
    fm.write(byteArray,offset,length);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void write(int b) throws IOException{
    fm.write(b);
    }


// ADDITIONAL METHODS FOR CUSTOM USE ONLY


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void seek(long position) throws IOException{
    fm.seek(position);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public long getOffset(){
    return fm.getOffset();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public FileManipulator getFileManipulator(){
    return fm;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public long getLength(){
    return fm.getLength();
    }



  }