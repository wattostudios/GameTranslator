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
import java.io.InputStream;

/**
**********************************************************************************************
  An InputStream that is compatable with all Java classes and methods that use streams for file
  reading. This is basically an interface between the Java standards and the FileManipulator.
  See the InputStream API for details of each method.
**********************************************************************************************
**/

public class FileManipulatorInputStream extends InputStream{

  FileManipulator fm;
  int mark = 0;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public FileManipulatorInputStream(File path){
    this.fm = new FileManipulator(path,"r");
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public FileManipulatorInputStream(FileManipulator fm){
    this.fm = fm;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int available() throws IOException{
    return ((int)fm.length() - (int)fm.getFilePointer());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void close() throws IOException{
    //fm.close();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void mark(int readLimit){
    try {
      mark = (int)fm.getFilePointer();
      }
    catch (Exception e){
      mark = 0;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean markSupported(){
    return true;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int read() throws IOException{
    return fm.readByteU();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int read(byte[] byteArray) throws IOException{
    return fm.read(byteArray);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int read(byte[] byteArray, int offset, int length) throws IOException{
    return fm.read(byteArray,offset,length);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reset(){
    try {
      fm.seek(mark);
      }
    catch (Exception e){
      }

    mark = 0;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public long skip(long numToSkip) throws IOException{
    long currentPos = fm.getFilePointer();
    fm.skip((int)numToSkip);
    long newPos = fm.getFilePointer();
    return newPos - currentPos;
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