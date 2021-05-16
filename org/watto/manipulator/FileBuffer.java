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

import org.watto.ErrorLogger;

import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


/** FILE BUFFER
**********************************************************************************************
A buffer that sits between the file system of the computer, and the FileManipulator, improving
the reading and writing speed of the system.
**********************************************************************************************
**/
public class FileBuffer implements ManipulatorBuffer{

  /** The virtual pointer location in the file **/
  long filePointer = 0;

  /** The interface to the file **/
  RandomAccessFile raf;

  /** The size of the buffer **/
  int bufferSize = 2048;
  /** The buffer **/
  byte[] buffer = new byte[bufferSize];
  /** The read position in the buffer **/
  int bufferLevel = 0;

  /** The file being manipulated **/
  File path;

  /** Is writing enabled for this file? **/
  boolean writable = false;


/** FILE BUFFER
**********************************************************************************************
Initialization of the buffer.
@param path the file that is being buffered
@param mode determines whether the buffer will read, or read/write
@param bufferSize the size of the buffer
**********************************************************************************************
**/
  public FileBuffer(File path, String mode, int bufferSize) {
    try {

      if (mode.equals("rw")){
        writable = true;
        path = makeValidFilename(path);
        }
      else {
        writable = false;
        }

      this.path = path;

      raf = new RandomAccessFile(path,mode);
      buffer = new byte[bufferSize];

      if (!writable){
        fill();
        }

      // For trace logging of file pointers
      //ErrorLogger.log("OPENED", path.getAbsolutePath());

      }
    catch (Throwable t){
      logError(t);
      }
    }


/** FILE BUFFER
**********************************************************************************************
Initialization of the buffer.
@param path the file that is being buffered
@param mode determines whether the buffer will read, or read/write
@param bufferSize the size of the buffer
**********************************************************************************************
**/
  public FileBuffer(String path, String mode, int bufferSize) {
    this(new File(path),mode,bufferSize);
    }


/** FILE BUFFER
**********************************************************************************************
Initialization of the buffer, with the default 2048 buffer size
@param path the file that is being buffered
@param mode determines whether the buffer will read, or read/write
**********************************************************************************************
**/
  public FileBuffer(String path, String mode) {
    this(new File(path),mode,2048);
    }


/** FILE BUFFER
**********************************************************************************************
Initialization of the buffer, with the default 2048 buffer size
@param path the file that is being buffered
@param mode determines whether the buffer will read, or read/write
**********************************************************************************************
**/
  public FileBuffer(File path, String mode) {
    this(path,mode,2048);
    }


/** CHECK FILL
**********************************************************************************************
Checks to see whether the buffer can read <i>length</i> bytes - if not then the bufferLevel
is set to 0 and the filePointer moved forward alittle.
@param length the length of data that is needed in the buffer
**********************************************************************************************
**/
  public void checkFill(int length) {
    try {

      if (bufferLevel + length >= bufferSize){
        filePointer += bufferLevel;
        seek(filePointer);
        }

      }
    catch (Throwable t){
      logError(t);
      }
    }


/** CHECK WRITE
**********************************************************************************************
If the buffer can't fit the required length of data, the data in the buffer is written to disk
and the buffer cleared ready for writing
@param length the length of space needed in the buffer
**********************************************************************************************
**/
  public void checkWrite(int length) {
    try {

      if (bufferLevel + length >= bufferSize){
        forceWrite();
        }

      }
    catch (Throwable t){
      logError(t);
      }
    }


/** CLOSE
**********************************************************************************************
Closes the file, and forceWrite() the data if <i>writable</i> is true
**********************************************************************************************
**/
  public void close() {
    try {

      if (writable){
        forceWrite();
        }

      raf.close();

      // For trace logging of file pointers
      //ErrorLogger.log("CLOSED", path.getAbsolutePath());

      }
    catch (Throwable t){
      logError(t);
      }
    }


/** COPY FROM BUFFER
**********************************************************************************************
Copies <i>length</i> bytes of data from the <i>source</i> into the current position in this buffer
@param length the number of bytes to copy
@param source the source to copy from
**********************************************************************************************
**/
  public void copyFromBuffer(ManipulatorBuffer source, long length) {
    try {

      if (bufferLevel != 0){
        forceWrite();
        }

      // find the smallest of the lengths of the buffers
      int smallBufferSize = source.getBufferSize();
      if (smallBufferSize > bufferSize){
        smallBufferSize = bufferSize;
        }

      // copy the buffer in lengths of smallBufferSize
      byte[] smallBuffer = new byte[smallBufferSize];
      while (length > smallBufferSize){
        source.read(smallBuffer);
        write(smallBuffer);
        length -= smallBufferSize;
        }

      if (length != 0){
        // copy the remaining data, which is smaller than the smallBufferLength
        source.read(smallBuffer,0,(int)length);
        write(smallBuffer,0,(int)length);
        }

      }
    catch (Throwable t){
      logError(t);
      }
    }


/** FILL
**********************************************************************************************
Flushes the buffer and refills it
**********************************************************************************************
**/
  public void fill() {
    try {

      flush();
      raf.read(buffer);

      }
    catch (Throwable t){
      logError(t);
      }
    }


/** FLUSH
**********************************************************************************************
Empties the buffer, discarding all data within it.
**********************************************************************************************
**/
  public void flush() {
    buffer = new byte[bufferSize];
    bufferLevel = 0;
    }


/** FORCE WRITE
**********************************************************************************************
Forces the data in the buffer to be written out to the file, and flushes the buffer.
**********************************************************************************************
**/
  public void forceWrite() {
    try {

      if (writable && bufferLevel > 0){
        raf.write(buffer,0,bufferLevel);
        }

      filePointer += bufferLevel;
      flush();

      }
    catch (Throwable t){
      logError(t);
      }
    }


/** GET BUFFER
**********************************************************************************************
  Copies the desired length of data from the buffer. This does not move the pointers
  @param length the length to duplicate from the buffer
  @return the data from the buffer
**********************************************************************************************
**/
  public byte[] getBuffer(int length) throws IOException{
    try {

      if (length > bufferSize){
        return buffer;
        }
      else {
        byte[] smallBuffer = new byte[length];
        System.arraycopy(buffer,0,smallBuffer,0,length);
        return smallBuffer;
        }

      }
    catch (Exception e){
      logError(e);
      throw new IOException("Bad buffer read");
      }
    }


/** GET BUFFER LEVEL
**********************************************************************************************
Gets the pointer position for the buffer
@return the pointer position
**********************************************************************************************
**/
  public int getBufferLevel() {
    return bufferLevel;
    }


/** GET BUFFER SIZE
**********************************************************************************************
Gets the size of the buffer
@return the size of the buffer
**********************************************************************************************
**/
  public int getBufferSize() {
    return bufferSize;
    }


/** GET POINTER
**********************************************************************************************
Gets the current position in this file, from which the data will be read or written from
@return the current position in the file
**********************************************************************************************
**/
  public long getPointer() {
    try {

      if (writable){
        return raf.getFilePointer() + bufferLevel;
        }
      else {
        return filePointer + bufferLevel;
        }

      }
    catch (Throwable t){
      logError(t);
      return -1;
      }
    }


/** GET PATH
**********************************************************************************************
Gets the file path that this buffer is pointing to
@return the file path
**********************************************************************************************
**/
  public File getPath() {
    return path;
    }


/** GET RAF
**********************************************************************************************
Gets the underlysing RandomAccessFile that talks to the file system
@return the RandomAccessFile
**********************************************************************************************
**/
  public RandomAccessFile getRAF() {
    return raf;
    }


/** GET VALUE AT BUFFER POS
**********************************************************************************************
  Returns a value in the current buffer, without moving any pointers.
  @param pos the position in the buffer to read
  @return the byte at the buffer pos
**********************************************************************************************
**/
  public int getValueAtBufferPos(int pos) throws IOException{
    try {
      return buffer[pos];
      }
    catch (Exception e){
      logError(e);
      throw new IOException("No value at buffer position " + pos);
      }
    }


/** LENGTH
**********************************************************************************************
Gets the length of the file
@return the length of the file
**********************************************************************************************
**/
  public long length() {
    try {

      if (writable){
        return raf.length() + bufferLevel;
        }
      else {
        return raf.length();
        }

      }
    catch (Throwable t){
      logError(t);
      return -1;
      }
    }


/** MAKE DIRECTORY
**********************************************************************************************
Makes the directory structure for the <i>path</i>
@param path the path to create
**********************************************************************************************
**/
  public static void makeDirectory(File path) {
    makeDirectory(path,false);
    }


/** MAKE DIRECTORY
**********************************************************************************************
Makes the directory structure for the <i>path</i>
@param path the path to create
@param isDirectoy is this file a directory?
**********************************************************************************************
**/
  public static void makeDirectory(File path, boolean isDirectory) {
    try {

      if (path == null){
        return;
        }

      File parent = path.getParentFile();
      if (!parent.exists()){
        makeDirectory(parent,true);
        }

      if (isDirectory){
        path.mkdir();
        }

      }
    catch (Throwable t){
      logError(t);
      }
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
  public static File makeValidFilename(File path){
    path = validateFilename(path);

    if (path.exists()){
      String directory = path.getAbsolutePath();
      String extension = "";

      int dotPos = directory.lastIndexOf(".");
      if (dotPos > -1){
        extension = directory.substring(dotPos);
        directory = directory.substring(0,dotPos) + "-";
        }

      int i=1;
      path = new File(directory + i + extension);
      while (path.exists()){
        i++;
        path = new File(directory + i + extension);
        }
      }

    makeDirectory(new File(path.getAbsolutePath()));
    return path;
    }


/** READ
**********************************************************************************************
Reads a single byte from the buffer
@return the byte
**********************************************************************************************
**/
  public int read() {
    try {

      checkFill(1);

      int readData = buffer[bufferLevel];
      bufferLevel++;

      return readData;

      }
    catch (Throwable t){
      logError(t);
      return -1;
      }
    }


/** READ
**********************************************************************************************
Reads an array of bytes from the buffer
@param readData the array into which the data is read
@return the amount of data that was read in to the array
**********************************************************************************************
**/
  public int read(byte[] readData) {
    return read(readData,0,readData.length);
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
    try {

      checkFill(readSize);

      int readLevel = offset;

      if (readSize <= bufferSize){
        System.arraycopy(buffer,bufferLevel,readData,readLevel,readSize);
        bufferLevel += readSize;
        }
      else {
        while (readSize > 0){
          int sizeToRead = readSize;
          if (readSize > bufferSize){
            sizeToRead = bufferSize;
            }
          System.arraycopy(buffer,bufferLevel,readData,readLevel,sizeToRead);
          bufferLevel += sizeToRead;

          readSize -= sizeToRead;
          checkFill(sizeToRead);
          readLevel += sizeToRead;
          }

        }

      return readSize;

      }
    catch (Throwable t){
      logError(t);
      return -1;
      }
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

    boolean foundLine = false;

    while (!foundLine){

      for (int i=bufferLevel;i<bufferSize;i++){
        if (buffer[i] == n){
          // new line character '\n'
          byte[] lineBytes = new byte[i-bufferLevel];
          read(lineBytes);

          bufferLevel++; // skip the '\n'
          line += new String(lineBytes);

          return line;
          }
        else if (buffer[i] == r){
          // carriage return character '\r'
          byte[] lineBytes = new byte[i-bufferLevel];
          read(lineBytes);

          bufferLevel++; // skip the '\r'
          line += new String(lineBytes);

          // check for the \r\n combination
          checkFill(1);
          if (buffer[bufferLevel] == n){
            bufferLevel++;
            }

          return line;
          }
        }

      int copyLength = 0;
      boolean endOfFile = false;
      if (raf.getFilePointer() >= raf.length()){
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

    boolean foundLine = false;

    while (!foundLine){

      for (int i=bufferLevel;i<bufferSize-1;i++){
        if (buffer[i] == n && buffer[i+1] == 0){
          // new line character '\n'
          byte[] lineBytes = new byte[i-bufferLevel];
          read(lineBytes);

          bufferLevel+=2; // skip the '\n'
          line += new String(lineBytes,"UTF-16LE");

          return line;
          }
        else if (buffer[i] == r && buffer[i+1] == 0){
          // carriage return character '\r'
          byte[] lineBytes = new byte[i-bufferLevel];
          read(lineBytes);

          bufferLevel+=2; // skip the '\r'
          line += new String(lineBytes,"UTF-16LE");

          // check for the \r\n combination
          checkFill(2);
          if (buffer[bufferLevel] == n && buffer[bufferLevel+1] == 0){
            bufferLevel+=2;
            }

          return line;
          }
        }

      int copyLength = 0;
      boolean endOfFile = false;
      if (raf.getFilePointer() >= raf.length()){
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
    try {

      if (writable){
        forceWrite();
        }

      long newPosition = offset - filePointer;

      if (newPosition >= 0 && newPosition < bufferSize){
        bufferLevel = (int)newPosition;
        }
      else {
        seek(offset);
        }

      }
    catch (Throwable t){
      logError(t);
      }
    }


/** REMAINING LENGTH
**********************************************************************************************
Gets the number of bytes left to read in the file
@return the number of remaining bytes
**********************************************************************************************
**/
  public long remainingLength() {
    return length() - getPointer();
    }


/** SEEK
**********************************************************************************************
Moves the pointer to the specified position in the file
@param offset the position to move to
**********************************************************************************************
**/
  public void seek(long offset) {
    try {

      if (offset == getPointer()){
        // already at the right offset, so we don't need to do anything
        return;
        }
      //else if (offset == filePointer){
      //  // the buffer starts at this offset, so just reset the bufferLevel
      //  bufferLevel = 0;
      //  return;
      //  }

      if (writable){
        forceWrite();
        raf.seek(offset);
        filePointer = offset;
        flush();
        }
      else {
        raf.seek(offset);
        filePointer = offset;
        fill();
        }

      }
    catch (Throwable t){
      logError(t);
      }
    }


/** SKIP
**********************************************************************************************
Skips over a specified number of bytes
@param length the number of bytes to skip
@return the number of skipped bytes
**********************************************************************************************
**/
  public int skip(int length) {
    try {

      if (writable){
        // for reading and writing...
        forceWrite();
        raf.skipBytes(length);
        flush();

        filePointer += length;

        return length;
        }
      else {
        // else for read only...
        int bufferPos = length + bufferLevel;
        if (bufferPos >= bufferSize){

          // In very very rare cases, this would cause the seek() to fail. This is because in seek() it
          // doesn't actually do a seek if the filePointer is already at the correct spot. This is in
          // seek() at the line...
          //
          // if (offset == getPointer()){
          //
          // Because this line would evaluate to true, the seek wouldn't actually move, even though it
          // is at the wrong spot. The new line corrects the problem, and filePointer still gets updated
          // in the seek method regardless.
          //
          // This is a very rare bug - it only occurred once in several years!

          //filePointer += bufferPos;
          //seek(filePointer);
          seek(filePointer+bufferPos);
          }
        else {
          bufferLevel += length;
          }

        return length;
        }

      }
    catch (Throwable t){
      logError(t);
      return 0;
      }
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
    try {

      if (writable){
        forceWrite();
        }

      bufferSize = length;
      flush();

      }
    catch (Throwable t){
      logError(t);
      }
    }


/** SET LENGTH
**********************************************************************************************
Sets the length of the file
@param length the new length of the file
**********************************************************************************************
**/
  public void setLength(long length) {
    try {

      raf.setLength(length);

      }
    catch (Throwable t){
      logError(t);
      }
    }


/** VALIDATE FILENAME
**********************************************************************************************
Removes any characters that are invalid for use in a filename
@param file the file to check for invalid characters
@return the file without invalid characters
**********************************************************************************************
**/
  public static File validateFilename(File file){
    if (file.exists()){
      return file;
      }


    String path = "";
    File parent = new File(file.getAbsolutePath());

    boolean everChanged = false;
    while (parent != null && ! parent.exists()){
      String name = parent.getName();
      byte[] bytes = name.getBytes();
      int numBytes = bytes.length;

      boolean changed = false;
      for (int i=0;i<numBytes;i++){
        byte curByte = bytes[i];

        if ((curByte >= 32 || curByte < 0) && curByte != 34 && curByte != 42 && curByte != 47 && curByte != 58 && curByte != 60 && curByte != 62 && curByte != 63 && curByte != 92 && curByte != 124){
          //the character is valid
          }
        else {
          bytes[i] = (byte)95;
          changed = true;
          everChanged = true;
          }
        }

      if (changed){
        name = new String(bytes);
        }

      if (path.length() > 0){
        path = name + File.separator + path;
        }
      else {
        path = name + path;
        }

      parent = parent.getParentFile();
      }


    if (!everChanged){
      // path was valid
      return file;
      }
    else if (parent == null){
      return new File(path);
      }
    else {
      path = parent.getAbsolutePath() + File.separator + path;
      return new File(path);
      }

    }


/** WRITE
**********************************************************************************************
Writes a byte of data to the buffer
@param writeData the byte of data to write
**********************************************************************************************
**/
  public void write(int writeData) {
    checkWrite(1);
    buffer[bufferLevel] = (byte)writeData;
    bufferLevel++;
    }


/** WRITE
**********************************************************************************************
Writes an array of data to the buffer
@param writeData the data to write
**********************************************************************************************
**/
  public void write(byte[] writeData) {
    write(writeData,0,writeData.length);
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
    try {

      checkWrite(writeSize);

      if (writeSize >= bufferSize){
        // just write straight to the disk
        raf.write(writeData,offset,writeSize);
        filePointer += writeSize;
        }
      else {
        // buffer it
        System.arraycopy(writeData,offset,buffer,bufferLevel,writeSize);
        bufferLevel += writeSize;
        }

      }
    catch (Throwable t){
      logError(t);
      }
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

