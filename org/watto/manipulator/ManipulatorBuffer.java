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


/** FILE BUFFER
**********************************************************************************************
  A buffer that sits between the file system of the computer, and the FileManipulator, improving
  the reading and writing speed of the system.
**********************************************************************************************
**/
public interface ManipulatorBuffer {


/** CHECK FILL
**********************************************************************************************
  Checks to see whether the buffer can read <i>length</i> bytes - if not then the bufferLevel
  is set to 0 and the filePointer moved forward alittle.
  @param length the length of data that is needed in the buffer
**********************************************************************************************
**/
  public void checkFill(int length);


/** CHECK WRITE
**********************************************************************************************
  If the buffer can't fit the required length of data, the data in the buffer is written to disk
  and the buffer cleared ready for writing
  @param length the length of space needed in the buffer
**********************************************************************************************
**/
  public void checkWrite(int length);


/** CLOSE
**********************************************************************************************
  Closes the file, and forceWrite() the data if the mode is "rw"
**********************************************************************************************
**/
  public void close();


/** COPY FILE
**********************************************************************************************
  Copies the file from the <i>source</i> into the current position of this file
  @param length the buffer size
  @param source the source file to copy
**********************************************************************************************
**/
  public void copyFromBuffer(ManipulatorBuffer source, long length);


/** FILL
**********************************************************************************************
  Flushes the buffer and refills it
**********************************************************************************************
**/
  public void fill();


/** FLUSH
**********************************************************************************************
  Empties the buffer, discarding all data within it.
**********************************************************************************************
**/
  public void flush();


/** FORCE WRITE
**********************************************************************************************
  Forces the data in the buffer to be written out to the file, and flushes the buffer.
**********************************************************************************************
**/
  public void forceWrite();


/** GET BUFFER
**********************************************************************************************
  Copies the desired length of data from the buffer. This does not move the pointers
  @param length the length to duplicate from the buffer
  @return the data from the buffer
**********************************************************************************************
**/
  public byte[] getBuffer(int length) throws IOException;


/** GET BUFFER LEVEL
**********************************************************************************************
  Gets the pointer position for the buffer
  @return the pointer position
**********************************************************************************************
**/
  public int getBufferLevel();


/** GET BUFFER SIZE
**********************************************************************************************
  Gets the size of the buffer
  @return the size of the buffer
**********************************************************************************************
**/
  public int getBufferSize();


/** GET FILE POINTER
**********************************************************************************************
  Gets the current position in this file, from which the data will be read or written from
  @return the current position in the file
**********************************************************************************************
**/
  public long getPointer();


/** GET PATH
**********************************************************************************************
  Gets the file path that this buffer is pointing to
  @return the file path
**********************************************************************************************
**/
  public File getPath();


/** LENGTH
**********************************************************************************************
  Gets the length of the file
  @return the length of the file
**********************************************************************************************
**/
  public long length();


/** READ
**********************************************************************************************
  Reads a single byte from the buffer
  @return the byte
**********************************************************************************************
**/
  public int read();


/** READ
**********************************************************************************************
  Reads an array of bytes from the buffer
  @param readData the array into which the data is read
  @return the amount of data that was read in to the array
**********************************************************************************************
**/
  public int read(byte[] readData);


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
  public int read(byte[] readData, int offset, int readSize);


/** READ LINE
**********************************************************************************************
  Reads a line of text from the buffer. A line ends with the \n newLine or \r return characters,
  or a \r\n combination
  @return the line of text
**********************************************************************************************
**/
  public String readLine();


/** READ UNICODE LINE
**********************************************************************************************
  Reads a line of unicode text from the buffer. A line ends with the \n newLine or \r return
  characters, or a \r\n combination
  @return the line of text
**********************************************************************************************
**/
  public String readUnicodeLine();


/** RELATIVE SEEK
**********************************************************************************************
  Tested - should be OK. Used best where many small seeks occur back and forward (where each
  seek is smaller than the buffer size) rather than being used as a complete seek replacement.
**********************************************************************************************
**/
  public void relativeSeek(long offset);


/** REMAINING LENGTH
**********************************************************************************************
  Gets the number of bytes left to read in the file
  @return the number of remaining bytes
**********************************************************************************************
**/
  public long remainingLength();


/** SEEK
**********************************************************************************************
  Moves the pointer to the specified position in the file
  @param offset the position to move to
**********************************************************************************************
**/
  public void seek(long offset);


/** SKIP
**********************************************************************************************
  Skips over a specified number of bytes
  @param length the number of bytes to skip
  @return the number of skipped bytes
**********************************************************************************************
**/
  public int skip(int length);


/** SET BUFFER SIZE
**********************************************************************************************
  Sets the size of the buffer

  WARNING: If you are using this class to read from a file but your mode is set to "rw" then
  any information in the buffer will be written to the file when you run this method. Please
  ensure that you use the correct modes, change this value in the constructor rather than
  using this method, or run flush() before you call this method.

  @param length the new size of the buffer
**********************************************************************************************
**/
  public void setBufferSize(int length);


/** SET LENGTH
**********************************************************************************************
  Sets the length of the file
  @param length the new length of the file
**********************************************************************************************
**/
  public void setLength(long length);


/** WRITE
**********************************************************************************************
  Writes a byte of data to the buffer
  @param writeData the byte of data to write
**********************************************************************************************
**/
  public void write(int writeData);


/** WRITE
**********************************************************************************************
  Writes an array of data to the buffer
  @param writeData the data to write
**********************************************************************************************
**/
  public void write(byte[] writeData);


/** WRITE
**********************************************************************************************
  Writes <i>writeSize</i> bytes of data to the buffer starting from <i>offset</i> position in
  the <i>writeData</i> array
  @param writeData the data to write
  @param offset the offset to start from in the array
  @param writeSize the amount of data to write
**********************************************************************************************
**/
  public void write(byte[] writeData, int offset, int writeSize);


  }

