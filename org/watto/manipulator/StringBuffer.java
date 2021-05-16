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

import org.watto.*;

import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;


/**
**********************************************************************************************
A buffer that sits between a String and the FileManipulator, allowing you to use all the
FileManipulator methods on a String instead of a File.
**********************************************************************************************
**/
public class StringBuffer implements ManipulatorBuffer {

  /** The size of the buffer **/
  int bufferSize = 0;
  /** The buffer **/
  byte[] buffer = new byte[0];
  /** The read position in the buffer **/
  int bufferLevel = 0;


/**
**********************************************************************************************
Initialization of the buffer.
@param text the text to be read/written to
**********************************************************************************************
**/
  public StringBuffer(String text) {
    try {
      buffer = text.getBytes("UTF-8");
      bufferSize = buffer.length;
      }
    catch (Throwable t){
      logError(t);
      buffer = new byte[0];
      }
    }


/**
**********************************************************************************************
N/A - the buffer is always fully available
**********************************************************************************************
**/
  public void checkFill(int length) {
    }


/**
**********************************************************************************************
N/A - the buffer is always fully available
**********************************************************************************************
**/
  public void checkWrite(int length) {
    }


/**
**********************************************************************************************
N/A
**********************************************************************************************
**/
  public void close() {
    }


/**
**********************************************************************************************
Copies the file from the <i>source</i> into the current position of this file
@param length the buffer size
@param source the source file to copy
**********************************************************************************************
**/
  public void copyFromBuffer(ManipulatorBuffer source, long length) {
    try {
      write(source.getBuffer((int)length));
      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
N/A - the buffer is always fully available
**********************************************************************************************
**/
  public void fill() {
    }


/**
**********************************************************************************************
N/A - the buffer is always fully available
**********************************************************************************************
**/
  public void flush() {
    }


/**
**********************************************************************************************
N/A - the buffer is always fully available
**********************************************************************************************
**/
  public void forceWrite() {
    }


/**
**********************************************************************************************
Copies the desired length of data from the buffer. This does not move the bufferLevels
@param length the length to duplicate from the buffer
@return the data from the buffer
**********************************************************************************************
**/
  public byte[] getBuffer(int length) throws IOException{
    int readSize = length;
    if (bufferLevel+length >= bufferSize){
      readSize = bufferSize - bufferLevel;
      }

    byte[] bytes = new byte[readSize];
    System.arraycopy(buffer,bufferLevel,bytes,0,readSize);
    return bytes;
    }


/**
**********************************************************************************************
Gets the bufferLevel position for the buffer
@return the bufferLevel position
**********************************************************************************************
**/
  public int getBufferLevel() {
    return bufferLevel;
    }


/**
**********************************************************************************************
Gets the size of the buffer
@return the size of the buffer
**********************************************************************************************
**/
  public int getBufferSize() {
    return bufferSize;
    }


/**
**********************************************************************************************
Gets the current position in this String, from which the data will be read or written from
@return the current position in the file
**********************************************************************************************
**/
  public long getPointer() {
    return bufferLevel;
    }


/**
**********************************************************************************************
N/A
@return null
**********************************************************************************************
**/
  public File getPath() {
    return null;
    }


/**
**********************************************************************************************
Increases the size of the buffer, if needed (for writing)
**********************************************************************************************
**/
  public void increaseSize(int length) {
    if (bufferLevel+length >= bufferSize){
      buffer = ArrayTools.resize(buffer,bufferLevel+length);
      bufferSize = buffer.length;
      }
    }


/**
**********************************************************************************************
Gets the length of the file
@return the length of the file
**********************************************************************************************
**/
  public long length() {
    return bufferSize;
    }


/**
**********************************************************************************************
Reads a single byte from the buffer
@return the byte
**********************************************************************************************
**/
  public int read() {
    try {
      int readData = buffer[bufferLevel];
      bufferLevel++;
      return readData;
      }
    catch (Throwable t){
      //logError(t);
      return -1;
      }
    }


/**
**********************************************************************************************
Reads an array of bytes from the buffer
@param readData the array into which the data is read
@return the amount of data that was read in to the array
**********************************************************************************************
**/
  public int read(byte[] readData) {
    return read(readData,0,readData.length);
    }


/**
**********************************************************************************************
Reads <i>readSize</i> bytes from the buffer into the <i>offset</i> position of the
<i>readData</i> array
@param readData the array into which the data is read
@param offset the position in the array to start storing the data
@param readSize the amount of data to read
@return the amount of data that was read in to the array
**********************************************************************************************
**/
  public int read(byte[] readData, int offset, int readSize) {
    int remaining = (int)remainingLength();
    if (readSize > remaining){
      readSize = remaining;
      }

    int readLevel = offset;

    System.arraycopy(buffer,bufferLevel,readData,readLevel,readSize);
    bufferLevel += readSize;

    return readSize;
    }


/**
**********************************************************************************************
Reads a line of text from the buffer. A line ends with the \n newLine or \r return characters,
or a \r\n combination
@return the line of text
**********************************************************************************************
**/
  public String readLine() {

    byte n = 10;
    byte r = 13;

    for (int i=bufferLevel;i<bufferSize;i++){
      if (buffer[i] == n){
        // new line character '\n'
        byte[] lineBytes = new byte[i-bufferLevel];
        read(lineBytes);

        bufferLevel+=1; // skip the '\n'
        return new String(lineBytes);
        }
      else if (buffer[i] == r){
        // carriage return character '\r'
        byte[] lineBytes = new byte[i-bufferLevel];
        read(lineBytes);

        bufferLevel+=1; // skip the '\r'

        // check for the \r\n combination
        checkFill(1);
        if (buffer[bufferLevel] == n){
          bufferLevel+=1;
          }

        return new String(lineBytes);
        }
      }

    return "";
    }


/** READ UNICODE LINE
**********************************************************************************************
Reads a line of unicode text from the buffer. A line ends with the \n newLine or \r return
characters, or a \r\n combination. Unicode uses 2 bytes per character, including \n and \r.
@return the line of unicode text
**********************************************************************************************
**/
public String readUnicodeLine(){
  try {

    byte n = 10;
    byte r = 13;

    for (int i=bufferLevel;i<bufferSize-1;i++){
      if (buffer[i] == n && buffer[i+1] == 0){
        // new line character '\n'
        byte[] lineBytes = new byte[i-bufferLevel];
        read(lineBytes);

        bufferLevel+=2; // skip the '\n'
        return new String(lineBytes,"UTF-16");
        }
      else if (buffer[i] == r && buffer[i+1] == 0){
        // carriage return character '\r'
        byte[] lineBytes = new byte[i-bufferLevel];
        read(lineBytes);

        bufferLevel+=2; // skip the '\r'

        // check for the \r\n combination
        checkFill(2);
        if (buffer[bufferLevel] == n && buffer[bufferLevel+1] == 0){
          bufferLevel+=2;
          }

        return new String(lineBytes,"UTF-16");
        }
      }

    return "";

    }
  catch (Throwable t){
    logError(t);
    return "";
    }
  }


/**
**********************************************************************************************
N/A - same as regular seek
**********************************************************************************************
**/
  public void relativeSeek(long offset) {
    seek(offset);
    }


/**
**********************************************************************************************
Gets the number of bytes left to read in the file
@return the number of remaining bytes
**********************************************************************************************
**/
  public long remainingLength() {
    return bufferSize - bufferLevel;
    }


/**
**********************************************************************************************
Moves the bufferLevel to the specified position in the file
@param offset the position to move to
**********************************************************************************************
**/
  public void seek(long offset) {
    bufferLevel = (int)offset;
    }


/**
**********************************************************************************************
Skips over a specified number of bytes
@param length the number of bytes to skip
@return the number of skipped bytes
**********************************************************************************************
**/
  public int skip(int length) {
    bufferLevel += length;
    return length;
    }


/**
**********************************************************************************************
N/A - the buffer always encompasses the whole String
**********************************************************************************************
**/
  public void setBufferSize(int length) {
    }


/**
**********************************************************************************************
Sets the length of the file, by filling the extra data with blanks, or by shrinking the String
@param length the new length of the String
**********************************************************************************************
**/
  public void setLength(long length) {
    buffer = ArrayTools.resize(buffer,(int)length);
    if (bufferLevel >= length){
      bufferLevel = (int)length-1;
      }
    }


/**
**********************************************************************************************
Writes a byte of data to the buffer
@param writeData the byte of data to write
**********************************************************************************************
**/
  public void write(int writeData) {
    increaseSize(1);
    buffer[bufferLevel] = (byte)writeData;
    bufferLevel++;
    }


/**
**********************************************************************************************
Writes an array of data to the buffer
@param writeData the data to write
**********************************************************************************************
**/
  public void write(byte[] writeData) {
    int writeSize = writeData.length;
    increaseSize(writeSize);
    System.arraycopy(writeData,0,buffer,bufferLevel,writeSize);
    bufferLevel += writeSize;
    }


/**
**********************************************************************************************
Writes <i>writeSize</i> bytes of data to the buffer starting from <i>offset</i> position in
the <i>writeData</i> array
@param writeData the data to write
@param offset the offset to start from in the array
@param writeSize the amount of data to write
**********************************************************************************************
**/
  public void write(byte[] writeData, int offset, int writeSize) {
    increaseSize(writeSize);
    System.arraycopy(writeData,offset,buffer,bufferLevel,writeSize);
    bufferLevel += writeSize;
    }



/** LOG ERROR
**********************************************************************************************
Prints an error stack trace
@param t the error that occurred
**********************************************************************************************
**/
  public static void logError(Throwable t){
    try {
      ErrorLogger.log(t);
      }
    catch (Throwable t2){
      // The ErrorLogger doesn't exist, so just output to the System.out
      t.printStackTrace();
      }
    }


  }

