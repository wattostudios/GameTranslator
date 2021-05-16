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

package org.watto.manipulator.wrapper;

import org.watto.manipulator.*;
import org.watto.ErrorLogger;

import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


/** XOR FILE BUFFER
**********************************************************************************************
Sits between a FileManipulator and a FileBuffer, and converts each byte by XORing them with
a given value.
<br><br>
The easiest way to implement this class, is to create a FileManipulator for a file, then add
this class in the middle, like this...
<br><br>
<pre>
FileManipulator fm = new FileManipulator(file,"r");
fm.setBuffer(new XORFileBuffer(fm.getBuffer(),xorValue));
</pre>
<br><br>
The benefit of having this class in the middle is that it is transparent - you still use the
File Manipulator like you normally do, without needing any further conversions to take place.
**********************************************************************************************
**/
public class XORFileBuffer implements ManipulatorBuffer{

  FileBuffer buffer;
  int xorValue;


/** XOR FILE BUFFER
**********************************************************************************************

**********************************************************************************************
**/
  public XORFileBuffer(FileBuffer buffer, int xorValue) {
    this.buffer = buffer;
    this.xorValue = xorValue;
    }


/** CHECK FILL
**********************************************************************************************
Checks to see whether the buffer can read <i>length</i> bytes - if not then the bufferLevel
is set to 0 and the filePointer moved forward alittle.
@param length the length of data that is needed in the buffer
**********************************************************************************************
**/
  public void checkFill(int length) {
    buffer.checkFill(length);
    }


/** CHECK WRITE
**********************************************************************************************
If the buffer can't fit the required length of data, the data in the buffer is written to disk
and the buffer cleared ready for writing
@param length the length of space needed in the buffer
**********************************************************************************************
**/
  public void checkWrite(int length) {
    buffer.checkWrite(length);
    }


/** CLOSE
**********************************************************************************************
Closes the file, and forceWrite() the data if <i>writable</i> is true
**********************************************************************************************
**/
  public void close() {
    buffer.close();
    }


/** COPY FROM BUFFER
**********************************************************************************************
Copies <i>length</i> bytes of data from the <i>source</i> into the current position in this buffer
@param length the number of bytes to copy
@param source the source to copy from
**********************************************************************************************
**/
  public void copyFromBuffer(ManipulatorBuffer source, long length) {
    buffer.copyFromBuffer(source,length);
    }


/** FILL
**********************************************************************************************
Flushes the buffer and refills it
**********************************************************************************************
**/
  public void fill() {
    buffer.fill();
    }


/** FLUSH
**********************************************************************************************
Empties the buffer, discarding all data within it.
**********************************************************************************************
**/
  public void flush() {
    buffer.flush();
    }


/** FORCE WRITE
**********************************************************************************************
Forces the data in the buffer to be written out to the file, and flushes the buffer.
**********************************************************************************************
**/
  public void forceWrite() {
    buffer.forceWrite();
    }


/** GET BUFFER
**********************************************************************************************
  Copies the desired length of data from the buffer. This does not move the pointers
  @param length the length to duplicate from the buffer
  @return the data from the buffer
**********************************************************************************************
**/
  public byte[] getBuffer(int length) throws IOException{
    byte[] bytes = buffer.getBuffer(length);
    for (int i=0;i<bytes.length;i++){
      bytes[i] ^= xorValue;
      }
    return bytes;
    }


/** GET BUFFER LEVEL
**********************************************************************************************
Gets the pointer position for the buffer
@return the pointer position
**********************************************************************************************
**/
  public int getBufferLevel() {
    return buffer.getBufferLevel();
    }


/** GET BUFFER SIZE
**********************************************************************************************
Gets the size of the buffer
@return the size of the buffer
**********************************************************************************************
**/
  public int getBufferSize() {
    return buffer.getBufferSize();
    }


/** GET POINTER
**********************************************************************************************
Gets the current position in this file, from which the data will be read or written from
@return the current position in the file
**********************************************************************************************
**/
  public long getPointer() {
    return buffer.getPointer();
    }


/** GET PATH
**********************************************************************************************
Gets the file path that this buffer is pointing to
@return the file path
**********************************************************************************************
**/
  public File getPath() {
    return buffer.getPath();
    }


/** LENGTH
**********************************************************************************************
Gets the length of the file
@return the length of the file
**********************************************************************************************
**/
  public long length() {
    return buffer.length();
    }


/** MAKE DIRECTORY
**********************************************************************************************
Makes the directory structure for the <i>path</i>
@param path the path to create
**********************************************************************************************
**/
  public void makeDirectory(File path) {
    buffer.makeDirectory(path);
    }


/** MAKE DIRECTORY
**********************************************************************************************
Makes the directory structure for the <i>path</i>
@param path the path to create
@param isDirectoy is this file a directory?
**********************************************************************************************
**/
  public void makeDirectory(File path, boolean isDirectory) {
    buffer.makeDirectory(path,isDirectory);
    }


/** MAKE VALID FILENAME
**********************************************************************************************
Changes the <i>path</i> into a <i>File</i> that has a valid name. Performs the following:
<ul>
<li>Removes any invalid characters in the filename by calling <i>validateFilename(File)</i></li>
<li>If the file exists, a number is appended to the end of the filename until it is unique</li>
<li>If the directory structure doesn't exist, the directories are make by calling <i>makeDirectory(File)</i></li>
</ul>
@param path the path to make valid
@return the valid path
**********************************************************************************************
**/
  public File makeValidFilename(File path){
    return buffer.makeValidFilename(path);
    }


/** READ
**********************************************************************************************
Reads a single byte from the buffer
@return the byte
**********************************************************************************************
**/
  public int read() {
    int value = buffer.read();
    if (value == -1){
      return value;
      }
    return value ^ xorValue;
    }


/** READ
**********************************************************************************************
Reads an array of bytes from the buffer
@param readData the array into which the data is read
@return the amount of data that was read in to the array
**********************************************************************************************
**/
  public int read(byte[] readData) {
    int length = buffer.read(readData);
    for (int i=0;i<length;i++){
      //System.out.println("Before: " + readData[i]);
      readData[i] ^= xorValue;
      //System.out.println("After:  " + readData[i]);
      }
    return length;
    }


/** READ
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
    int length = buffer.read(readData,offset,readSize);
    for (int i=0;i<length;i++){
      readData[offset+i] ^= xorValue;
      }
    return length;
    }


/** READ LINE
**********************************************************************************************
Reads a line of text from the buffer. A line ends with the \n newLine or \r return characters,
or a \r\n combination
@return the line of text
**********************************************************************************************
**/
public String readLine(){
  try {

    String line = "";

    byte n = 10;
    byte r = 13;

    int bufferSize = buffer.getBufferSize();
    int bufferLevel = buffer.getBufferLevel();

    boolean foundLine = false;

    while (!foundLine){

      for (int i=bufferLevel;i<bufferSize;i++){
        if (buffer.getValueAtBufferPos(i) == n){
          // new line character '\n'
          byte[] lineBytes = new byte[i-bufferLevel];
          read(lineBytes);

          bufferLevel++; // skip the '\n'
          line += new String(lineBytes);

          return line;
          }
        else if (buffer.getValueAtBufferPos(i) == r){
          // carriage return character '\r'
          byte[] lineBytes = new byte[i-bufferLevel];
          read(lineBytes);

          bufferLevel++; // skip the '\r'
          line += new String(lineBytes);

          // check for the \r\n combination
          checkFill(1);
          if (buffer.getValueAtBufferPos(bufferLevel) == n){
            bufferLevel++;
            }

          return line;
          }
        }

      int copyLength = 0;
      boolean endOfFile = false;
      if (buffer.getRAF().getFilePointer() >= buffer.getRAF().length()){
        // the end of the file has been reached
        copyLength = (int)remainingLength();
        endOfFile = true;
        }
      else {
        copyLength = bufferSize-bufferLevel;
        }

      // copy the checked bytes into the line, re-fill the buffer, and continue checking.
      byte[] lineBytes = new byte[copyLength];
      read(lineBytes);

      line += new String(lineBytes);

      if (endOfFile){
        // the end of the file, so just return the string
        return line;
        }

      checkFill(bufferSize);

      }

    // should never get here!?
    return line;
    }
  catch (Throwable t){
    logError(t);
    return "";
    }
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

    String line = new String(new byte[0],"UTF-16LE");

    byte n = 10;
    byte r = 13;

    int bufferSize = buffer.getBufferSize();
    int bufferLevel = buffer.getBufferLevel();

    boolean foundLine = false;

    while (!foundLine){

      for (int i=bufferLevel;i<bufferSize-1;i++){
        if (buffer.getValueAtBufferPos(i) == n && buffer.getValueAtBufferPos(i+1) == 0){
          // new line character '\n'
          byte[] lineBytes = new byte[i-bufferLevel];
          read(lineBytes);

          bufferLevel+=2; // skip the '\n'
          line += new String(lineBytes,"UTF-16LE");

          return line;
          }
        else if (buffer.getValueAtBufferPos(i) == r && buffer.getValueAtBufferPos(i+1) == 0){
          // carriage return character '\r'
          byte[] lineBytes = new byte[i-bufferLevel];
          read(lineBytes);

          bufferLevel+=2; // skip the '\r'
          line += new String(lineBytes,"UTF-16LE");

          // check for the \r\n combination
          checkFill(2);
          if (buffer.getValueAtBufferPos(bufferLevel) == n && buffer.getValueAtBufferPos(bufferLevel+1) == 0){
            bufferLevel+=2;
            }

          return line;
          }
        }

      int copyLength = 0;
      boolean endOfFile = false;
      if (buffer.getRAF().getFilePointer() >= buffer.getRAF().length()){
        // the end of the file has been reached
        copyLength = (int)remainingLength();
        endOfFile = true;
        }
      else {
        copyLength = bufferSize-bufferLevel;
        }


      if (copyLength < 0){
        copyLength = 0;
        }

      // copy the checked bytes into the line, re-fill the buffer, and continue checking.
      byte[] lineBytes = new byte[copyLength];
      read(lineBytes);

      line += new String(lineBytes,"UTF-16LE");

      if (endOfFile){
        // the end of the file, so just return the string
        return line;
        }

      checkFill(bufferSize);

      }

    // should never get here!?
    return line;
    }
  catch (Throwable t){
    logError(t);
    return "";
    }
  }


/** RELATIVE SEEK
**********************************************************************************************
Performs a relative seek through the buffer. The result is the same as the normal <i>seek(offset)</i>,
in that the file pointer is moved a certain distance, however this method is much quicker than
<i>seek(offset)</i> if the position <i>offset</i> is in the current buffer.
<br>
What happens is the <i>offset</i> is checked to see if that position is in the current buffer - if
it is then the file pointer is incremented without needing to read anything from the hard drive.
If the buffer does not cover the <i>offset</i>, the <i>seek(offset)</i> method is called normally.
This is in contrast to the normal <i>seek(offset)</i> method that always reads from the hard drive
regardless of where the offset is.
<br>
This method is significantly faster than <i>seek(offset)</i> in applications where many seeks of
small distance are required - however it will be very slightly slower for applications where
seeking is over lengths greater than the size of the buffer - therefore it is important that the
buffer size is large enough to make relative seeking feasable (ie larger than the distance
between oldOffset and newOffset).
@param offset the offset to seek to
**********************************************************************************************
**/
  public void relativeSeek(long offset) {
    buffer.relativeSeek(offset);
    }


/** REMAINING LENGTH
**********************************************************************************************
Gets the number of bytes left to read in the file
@return the number of remaining bytes
**********************************************************************************************
**/
  public long remainingLength() {
    return buffer.remainingLength();
    }


/** SEEK
**********************************************************************************************
Moves the pointer to the specified position in the file
@param offset the position to move to
**********************************************************************************************
**/
  public void seek(long offset) {
    buffer.seek(offset);
    }


/** SKIP
**********************************************************************************************
Skips over a specified number of bytes
@param length the number of bytes to skip
@return the number of skipped bytes
**********************************************************************************************
**/
  public int skip(int length) {
    return buffer.skip(length);
    }


/** SET BUFFER SIZE
**********************************************************************************************
Sets the size of the buffer
<br>
WARNING: If you are using this class to read from a file but your mode is set to "rw" then
any information in the buffer will be written to the file when you run this method. Please
ensure that you use the correct modes, change this value in the constructor rather than
using this method, or run flush() before you call this method.
@param length the new size of the buffer
**********************************************************************************************
**/
  public void setBufferSize(int length) {
    buffer.setBufferSize(length);
    }


/** SET LENGTH
**********************************************************************************************
Sets the length of the file
@param length the new length of the file
**********************************************************************************************
**/
  public void setLength(long length) {
    buffer.setLength(length);
    }


/** VALIDATE FILENAME
**********************************************************************************************
Removes any characters that are invalid for use in a filename
@param file the file to check for invalid characters
@return the file without invalid characters
**********************************************************************************************
**/
  public File validateFilename(File file){
    return buffer.validateFilename(file);
    }


/** WRITE
**********************************************************************************************
Writes a byte of data to the buffer
@param writeData the byte of data to write
**********************************************************************************************
**/
  public void write(int writeData) {
    buffer.write(writeData^xorValue);
    }


/** WRITE
**********************************************************************************************
Writes an array of data to the buffer
@param writeData the data to write
**********************************************************************************************
**/
  public void write(byte[] writeData) {
    for (int i=0;i<writeData.length;i++){
      writeData[i] ^= xorValue;
      }
    buffer.write(writeData);
    }


/** WRITE
**********************************************************************************************
Writes <i>writeSize</i> bytes of data to the buffer starting from <i>offset</i> position in
the <i>writeData</i> array
@param writeData the data to write
@param offset the offset to start from in the array
@param writeSize the amount of data to write
**********************************************************************************************
**/
  public void write(byte[] writeData, int offset, int writeSize) {
    for (int i=0;i<writeSize;i++){
      writeData[offset+i] ^= xorValue;
      }
    buffer.write(writeData,offset,writeSize);
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

