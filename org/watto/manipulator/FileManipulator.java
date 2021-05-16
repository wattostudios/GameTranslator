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

/*

/// VERSION HISTORY ///

Version 3.0
[I] Analysed and re-coded all classes and methods for better speed and structure. Main improvements:
   [ ] ManipulatorBuffer
      [+] New buffer interface that allows FileManipulator to process other objects like Strings.
      [+] New buffers: ByteBuffer and StringBuffer,for reading/writing as the name suggests
   [ ] FileBuffer
      [A] Changed internal Read/Write checking to use a boolean instead of a String
      [A] All constructors now merge into 1 instead of being duplicated
      [A] If the ErrorLogger class exists, it is used for recording the errors
      [A] Added a readUnicodeLine() method
      [+] When writing a file, the filename is now checked for invalid characters
      [-] Removed all methods that aren't used by FileManipulator (such as RandomAccessFile methods)
      [-] Removed some unused try-catch statements to improve speed in methods
      [ ] The following methods have been significantly re-written and improved:
         [A] makeDirectory()
            [ ] Utilising recurrance
            [ ] Uses File methods instead of manual String manipulations
         [A] readLine()
            [ ] Less checking during the loop
            [ ] Immediate return on finding a line
            [ ] Uses other FileBuffer methods rather than directly talking to RandomAccessFile
  [ ] DataConverter
     [+] All formats can now be converted into all other formats
     [+] Added generic methods that read based on the format given in the constructor
     [+] Created separate methods for returning singles and arrays
     [+] Added methods for converting Strings to/from Unicode/ASCII
  [ ] FileManipulator
     [-] Removed all generic read and write methods
     [A] Removed repetition and simplfied all methods by passing to existing methods
     [-] Removed all methods that aren't used, or are duplicated (such as RandomAccessFile methods)
     [+] Added write() methods for writing data alternatively to the writeXXX() methods
  [ ] FileManipulatorInputStream
     [B] Calling read() will now return an unsigned byte value.

Version 2.67
[+] Added readTerminatedString to read a string until it reaches a byte of a given value

Version 2.66
[B] Fixed a bug where reading a byte[] larger than the buffer size, then doing a skip/seek, would
    not advance the file pointer.

Version 2.65
[+] Added a readUnicodeLine() method

Version 2.6
[+] Implemented and tested the relativeSeek() method
[+] Added methods for removing invalid characters from a filename
[+] Added copyFile() to the FileManipulator which directs to the copyFile() method in FileBuffer
[+] Added hex2byteL() to the DataConverter

Version 2.5
[+] Moved the converters into a separate class called DataConverter
[+] Added methods to search for specific bytes or Strings within a file
[+] Added alternative method names for C-style reading and writing
   [ ] QWORD
   [ ] SHORTINT
[+] Added unsigned methods for all reading formats, and unsigned converters to DataConverter
[B] Fixed a bug where readLine() would duplicate text if the buffer was reloaded
[B] Fixed a bug where readUnicodeString() would not return the correct values
[B] Fixed a bug where readLongInt and readQword methods would use Ints instead of Longs
[A] Adjusted the read(byte[],int,int) method so that it can read more than the buffer size
[A] Added method comments to the FileBuffer, and those remaining in the FileManipulator class
[A] The FileBuffer won't refill after a seek() if it is already at the right position

Version 2.0
[+] Added the file buffer for more efficient file reading and writing
[+] Implements the DataInput and DataOutput interfaces
[+] Created streaming interfaces for full Java compatability
[+] Added alternative method names for C-style reading and writing
   [ ] WORD
   [ ] DWORD
   [ ] LONGINT

Version 1.0
[I] First release
[+] Read and write support for most data types
[+] Converters for most data types

*/

import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.FileDescriptor;
import java.nio.channels.FileChannel;


/**
**********************************************************************************************
The main interface to a file, it handles all reading, writing, and data conversion. Provides
many convenience methods for quick and thorough data manipulation of almost any standard format
including all primitives, primitive arrays, Strings, and unicode values. Backed by a read/write
buffer for improved performance, and conforms to the Input/OutputStream interfaces when used
with the appropriate class. Designed to be an all-inclusive "replacement" for RandomAccessFile
and many other generic Input/OutputStreams.
**********************************************************************************************
**/

public class FileManipulator{// implements DataInput, DataOutput{

  public final static int defaultBufferSize = 2048;

  /** the buffer that reads/writes to the underlying file **/
  ManipulatorBuffer buf;


//////////
//
// CONSTRUCTORS
//
//////////


/**
**********************************************************************************************
  Constructor that uses the supplied <i>buffer</i> for reading and writing.
  @param buffer the buffer
**********************************************************************************************
**/
  public FileManipulator(){
    }


/**
**********************************************************************************************
  Constructor that uses the supplied <i>buffer</i> for reading and writing.
  @param buffer the buffer
**********************************************************************************************
**/
  public FileManipulator(ManipulatorBuffer buffer){
    buf = buffer;
    }


/**
**********************************************************************************************
Constructor that creates a file stream to read from, & optionally to write to.
@param file the file object
@param mode the access mode. Either "r" or "rw"
**********************************************************************************************
**/
  public FileManipulator(File file, String mode){
    buf = new FileBuffer(file,mode,defaultBufferSize);
    }


/**
**********************************************************************************************
Constructor that creates a file stream to read from, & optionally to write to.
@param file the file object
@param mode the access mode. Either "r" or "rw"
@param bufferSize the size of the file buffer
**********************************************************************************************
**/
  public FileManipulator(File file, String mode, int bufferSize){
    buf = new FileBuffer(file,mode,bufferSize);
    }



//////////
//
// WORKING METHODS
//
//////////

/**
**********************************************************************************************
Sets the size of the file buffer
@param size the new size of the buffer
**********************************************************************************************
**/
  public void setBufferSize(int size){
    buf.setBufferSize(size);
    }

/**
**********************************************************************************************
Gets the ordering format to use when running generic methods
@return the ordering format. Either <i>DataConverter.LITTLE_ENDIAN</i> or <i>DataConverter.BIG_ENDIAN</i>
@see DataConverter.getFormat();
**********************************************************************************************
**/
  public int getFormat(){
    return DataConverter.getFormat();
    }

/**
**********************************************************************************************
Sets the encoding format to use when running generic methods
@param format the ordering format. Either <i>DataConverter.LITTLE_ENDIAN</i> or <i>DataConverter.BIG_ENDIAN</i>
@see DataConverter.setFormat(int);
**********************************************************************************************
**/
  public void setFormat(int format){
    DataConverter.setFormat(format);
    }

/**
**********************************************************************************************
Gets the buffer used to read/write to the file
@return the file buffer
**********************************************************************************************
**/
  public ManipulatorBuffer getBuffer(){
    return buf;
    }

/**
**********************************************************************************************
Sets the buffer used to read/write to the file
@param buf the buffer
**********************************************************************************************
**/
  public void setBuffer(ManipulatorBuffer buf){
    this.buf = buf;
    }

/**
**********************************************************************************************
Seeks to the specified position, relatively from the current position.
@param seekPos the position to seek to in the file
**********************************************************************************************
**/
  public void relativeSeek(long seekPos){
    buf.relativeSeek(seekPos);
    }

/**
**********************************************************************************************
Flushes the data out of the file buffer
**********************************************************************************************
**/
  public void flush(){
    buf.flush();
    }

/**
**********************************************************************************************
Forces any data in the buffer to be written out to the file (in mode "rw" only)
**********************************************************************************************
**/
  public void forceWrite(){
    buf.forceWrite();
    }

/**
**********************************************************************************************
Closes the file buffer, preventing further reading or writing.
**********************************************************************************************
**/
  public void close(){
    buf.close();
    }

/**
**********************************************************************************************
Gets the path that is being manipulated
@return the path to the file
**********************************************************************************************
**/
  public String getPath(){
    return getFile().getAbsolutePath();
    }

/**
**********************************************************************************************
Gets the file that is being manipulated
@return the file
**********************************************************************************************
**/
  public File getFile(){
    return buf.getPath();
    }

/**
**********************************************************************************************
Returns the current offset in the file.
@return the offset from the start of the file to the position where the next read/write will occur
**********************************************************************************************
**/
  public long getFilePointer(){
    return buf.getPointer();
    }

/**
**********************************************************************************************
Returns the length of the file.
@return the length of the file.
**********************************************************************************************
**/
  public long length(){
    return buf.length();
    }

/**
**********************************************************************************************
Returns the number of bytes from the offset to the end of the file
@return the remaining length of the file.
**********************************************************************************************
**/
  public long remainingLength(){
    return buf.remainingLength();
    }

/**
**********************************************************************************************
Reads a byte of data from the file.
@return the next byte of data, or -1 if the end of the file has been reached.
**********************************************************************************************
**/
  public int read(){
    return buf.read();
    }

/**
**********************************************************************************************
Reads up to <i>buf.length</i> bytes of data from the file into an array of bytes.
@param buffer the buffer into which the data is read
@return the total number of bytes read in to the <i>buffer</i>.
**********************************************************************************************
**/
  public int read(byte[] buffer){
    return buf.read(buffer);
    }

/**
**********************************************************************************************
Reads up to <i>length</i> bytes of data from the file into an array of bytes
@param buffer the buffer into which the data is read
@param offset the offset in the <i>buffer</i> to start storing the data.
@param length the maximum number of bytes read
@return the total number of bytes read into the <i>buffer</i>.
**********************************************************************************************
**/
  public int read(byte[] buffer, int offset, int length){
    return buf.read(buffer,offset,length);
    }

/**
**********************************************************************************************
Reads a line of text from the file
@return the line of text.
**********************************************************************************************
**/
  public String readLine(){
    return buf.readLine();
    }

/**
**********************************************************************************************
Writes a line of text to the file
@param text the line of text
**********************************************************************************************
**/
  public void writeLine(String text){
    buf.write(DataConverter.toByteArray(text + '\n'));
    }

/**
**********************************************************************************************
Reads a line of unicode text from the file
@return the unicode line
**********************************************************************************************
**/
  public String readUnicodeLine(){
    return buf.readUnicodeLine();
    }

/**
**********************************************************************************************
Writes a line of unicode text to the file
@param text the line of unicode text
**********************************************************************************************
**/
  public void writeUnicodeLine(String text){
    buf.write(DataConverter.toByteArray(DataConverter.toCharArray(text + (char)10)));
    }

/**
**********************************************************************************************
Sets the offset in the file from which the next byte will be read/written.
@param position the offset position
**********************************************************************************************
**/
  public void seek(long position){
    buf.seek(position);
    }

/**
**********************************************************************************************
Sets the length of the file.
@param newLength The desired length of the file
**********************************************************************************************
**/
  public void setLength(long newLength){
    buf.setLength(newLength);
    }

/**
**********************************************************************************************
Attempts to skip over numToSkip bytes of input discarding the skipped bytes.
@param numToSkip the number of bytes to be skipped
@return the actual number of bytes skipped
**********************************************************************************************
**/
  public int skip(int numToSkip){
    return buf.skip(numToSkip);
    }

/**
**********************************************************************************************
Attempts to skip over numToSkip bytes of input discarding the skipped bytes.
@param numToSkip the number of bytes to be skipped
@return the actual number of bytes skipped
**********************************************************************************************
**/
  public long skip(long numToSkip){
    long offset = getOffset() + numToSkip;
    buf.seek(offset);
    return numToSkip;
    }


/**
**********************************************************************************************
Writes the bytes in the <i>buffer</i> to the file, at the current offset.
@param buffer the data
**********************************************************************************************
**/
  public void write(byte[] buffer){
    buf.write(buffer);
    }

/**
**********************************************************************************************
Writes <i>length</i> bytes from the <i>offset</i> position in the <i>buffer</i> to the current
offset in the file
@param buffer the data
@param offset the start offset in the <i>buffer</i>.
@param length the number of bytes to write
**********************************************************************************************
**/
  public void write(byte[] buffer, int offset, int length){
    buf.write(buffer,offset,length);
    }

/**
**********************************************************************************************
Gets the current offset in the file
@return the offset
**********************************************************************************************
**/
  public long getOffset(){
    return getFilePointer();
    }

/**
**********************************************************************************************
Gets the length of the file
@return the length of the file
**********************************************************************************************
**/
  public long getLength(){
    return length();
    }

/**
**********************************************************************************************
Returns the number of bytes from the offset to the end of the file
@return the remaining length of the file.
**********************************************************************************************
**/
  public long getRemainingLength(){
    return remainingLength();
    }

/**
**********************************************************************************************
Skips forward a number of bytes
@param numBytes the number of bytes to skip
**********************************************************************************************
**/
  public void skipForward(long numBytes){
    seek(getOffset() + numBytes);
    }

/**
**********************************************************************************************
Skips backwards a number of bytes
@param numBytes the number of bytes to skip
**********************************************************************************************
**/
  public void skipBackward(long numBytes){
    seek(getOffset() + numBytes);
    }

/**
**********************************************************************************************
Copies a <i>source</i> file into the current file
@param source the file to copy
**********************************************************************************************
**/
  public void copyFile(File source){
    copyFile(new FileManipulator(source,"r"));
    }

/**
**********************************************************************************************
Copies a <i>source</i> file into the current file
@param source the file to copy
**********************************************************************************************
**/
  public void copyFile(FileManipulator source){
    copyFile(source,source.length());
    }


/**
**********************************************************************************************
Copies a <i>length</i> of a <i>source</i> file into the current file
@param source the file to copy
@param length the length of data to copy
**********************************************************************************************
**/
  public void copyFile(FileManipulator source, long length){
    buf.copyFromBuffer(source.getBuffer(),length);
    }


//////////
//
// READ METHODS
//
//////////

/////
// BOOLEAN
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean[] readBits(){
    return DataConverter.toBooleanArray(readByte());
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean[] readBitsL(){
    return DataConverter.toBooleanArrayL(readByte());
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean[] readBitsB(){
    return DataConverter.toBooleanArrayB(readByte());
    }


/////
// BYTE
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public byte readByte(){
    return (byte)read();
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public byte[] readBytes(int numBytes){
    return read(numBytes);
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public byte[] read(int numBytes){
    byte[] bytes = new byte[numBytes];
    read(bytes);
    return bytes;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public short readByteU(){
    return DataConverter.unsign(readByte());
    }


/////
// SHORT
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public short readShort(){
    return DataConverter.toShort(read(2));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public int readShortU(){
    return DataConverter.unsign(DataConverter.toShort(read(2)));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public short readShortL(){
    return DataConverter.toShortL(read(2));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public short readShortB(){
    return DataConverter.toShortB(read(2));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int readShortLU(){
    return DataConverter.unsign(DataConverter.toShortL(read(2)));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public int readShortBU(){
    return DataConverter.unsign(DataConverter.toShortB(read(2)));
    }


/////
// INTEGER
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public int readInt(){
    return DataConverter.toInt(read(4));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public long readIntU(){
    return DataConverter.unsign(DataConverter.toInt(read(4)));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public int readIntL(){
    return DataConverter.toIntL(read(4));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public int readIntB(){
    return DataConverter.toIntB(read(4));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public long readIntLU(){
    return DataConverter.unsign(DataConverter.toIntL(read(4)));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public long readIntBU(){
    return DataConverter.unsign(DataConverter.toIntB(read(4)));
    }


/////
// LONG
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public long readLong(){
    return DataConverter.toLong(read(8));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public long readLongU(){
    return readLong();
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public long readLongL(){
    return DataConverter.toLongL(read(8));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public long readLongB(){
    return DataConverter.toLongB(read(8));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public long readLongLU(){
    return readLongL();
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public long readLongBU(){
    return readLongB();
    }


/////
// FLOAT
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public float readFloat(){
    return DataConverter.toFloat(read(4));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public double readFloatU(){
    return DataConverter.unsign(DataConverter.toFloat(read(4)));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public float readFloatL(){
    return DataConverter.toFloatL(read(4));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public float readFloatB(){
    return DataConverter.toFloatB(read(4));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public double readFloatLU(){
    return DataConverter.unsign(DataConverter.toFloatL(read(4)));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public double readFloatBU(){
    return DataConverter.unsign(DataConverter.toFloatB(read(4)));
    }


/////
// DOUBLE
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public double readDouble(){
    return DataConverter.toDouble(read(8));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public double readDoubleU(){
    return readDouble();
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public double readDoubleL(){
    return DataConverter.toDoubleL(read(8));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public double readDoubleB(){
    return DataConverter.toDoubleB(read(8));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public double readDoubleLU(){
    return readDoubleL();
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public double readDoubleBU(){
    return readDoubleB();
    }


/////
// CHAR
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public char readChar(){
    return DataConverter.toChar(read(2));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public char readCharL(){
    return DataConverter.toCharL(read(2));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public char readCharB(){
    return DataConverter.toCharB(read(2));
    }


/////
// STRING
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public String readString(int numLetters){
    return DataConverter.toStringL(read(numLetters));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public String readUnicodeString(int numLetters){
    return DataConverter.toUnicode(read(numLetters*2));
    }


/////
// HEX
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public Hex readHex(int numBytes){
    return DataConverter.toHex(read(numBytes));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public Hex readHexB(int numBytes){
    return DataConverter.toHexB(read(numBytes));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public Hex readHexL(int numBytes){
    return DataConverter.toHexL(read(numBytes));
    }


//////////
//
// WRITE METHODS
//
//////////

/////
// BOOLEAN
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeBits(boolean[] bits){
    write(DataConverter.toByteArray(bits));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeBitsL(boolean[] bits){
    write(DataConverter.toByteArrayL(bits));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeBitsB(boolean[] bits){
    write(DataConverter.toByteArrayB(bits));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void write(boolean[] bits){
    write(DataConverter.toByteArray(bits));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeL(boolean[] bits){
    write(DataConverter.toByteArrayL(bits));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeB(boolean[] bits){
    write(DataConverter.toByteArrayB(bits));
    }


/////
// BYTE
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeByte(byte num){
    write(num);
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeBytes(byte[] num){
    write(num);
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void write(byte num){
    buf.write(num);
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeL(byte num){
    write(num);
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeB(byte num){
    write(num);
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeByte(int num){
    write((byte)num);
    }


/////
// SHORT
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeShort(short num){
    write(DataConverter.toByteArray(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeShortL(short num){
    write(DataConverter.toByteArrayL(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeShortB(short num){
    write(DataConverter.toByteArrayB(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void write(short num){
    write(DataConverter.toByteArray(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeL(short num){
    write(DataConverter.toByteArrayL(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeB(short num){
    write(DataConverter.toByteArrayB(num));
    }


/////
// INTEGER
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeInt(int num){
    write(DataConverter.toByteArray(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeIntL(int num){
    write(DataConverter.toByteArrayL(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeIntB(int num){
    write(DataConverter.toByteArrayB(num));
    }

/**
**********************************************************************************************
NOTE: THIS IS DIFFERENT TO EXPECTED!!!
**********************************************************************************************
**/
  public void write(int num){
    writeByte(num);
    //write(DataConverter.toByteArray(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeL(int num){
    write(DataConverter.toByteArrayL(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeB(int num){
    write(DataConverter.toByteArrayB(num));
    }


/////
// LONG
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeLong(long num){
    write(DataConverter.toByteArray(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeLongL(long num){
    write(DataConverter.toByteArrayL(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeLongB(long num){
    write(DataConverter.toByteArrayB(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void write(long num){
    write(DataConverter.toByteArray(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeL(long num){
    write(DataConverter.toByteArrayL(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeB(long num){
    write(DataConverter.toByteArrayB(num));
    }


/////
// FLOAT
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeFloat(float num){
    write(DataConverter.toByteArray(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeFloatL(float num){
    write(DataConverter.toByteArrayL(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeFloatB(float num){
    write(DataConverter.toByteArrayB(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void write(float num){
    write(DataConverter.toByteArray(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeL(float num){
    write(DataConverter.toByteArrayL(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeB(float num){
    write(DataConverter.toByteArrayB(num));
    }


/////
// DOUBLE
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeDouble(double num){
    write(DataConverter.toByteArray(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeDoubleL(double num){
    write(DataConverter.toByteArrayL(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeDoubleB(double num){
    write(DataConverter.toByteArrayB(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void write(double num){
    write(DataConverter.toByteArray(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeL(double num){
    write(DataConverter.toByteArrayL(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeB(double num){
    write(DataConverter.toByteArrayB(num));
    }


/////
// CHAR
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeChar(char num){
    write(DataConverter.toByteArray(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeCharL(char num){
    write(DataConverter.toByteArrayL(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeCharB(char num){
    write(DataConverter.toByteArrayB(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void write(char num){
    write(DataConverter.toByteArray(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeL(char num){
    write(DataConverter.toByteArrayL(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeB(char num){
    write(DataConverter.toByteArrayB(num));
    }


/////
// STRING
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeString(String text){
    write(DataConverter.toByteArray(text));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void write(String text){
    write(DataConverter.toByteArray(text));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeUnicodeString(String text){
    write(DataConverter.toByteArray(DataConverter.toCharArray(text)));
    }


/////
// HEX
/////

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeHex(Hex num){
    write(DataConverter.toByteArray(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeHexL(Hex num){
    write(DataConverter.toByteArrayL(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeHexB(Hex num){
    write(DataConverter.toByteArrayB(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void write(Hex num){
    write(DataConverter.toByteArray(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeL(Hex num){
    write(DataConverter.toByteArrayL(num));
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void writeB(Hex num){
    write(DataConverter.toByteArrayB(num));
    }


//////////
//
// SEARCHING
//
//////////

/**
**********************************************************************************************
Searches and moves the file pointer to the next match
@return the offset to the match
**********************************************************************************************
**/
  public long find(byte byteNum){
    return find(new byte[]{byteNum});
    }

/**
**********************************************************************************************
Searches and moves the file pointer to the next match
@return the offset to the match
**********************************************************************************************
**/
  public long find(byte[] bytes){
    long arcSize = getLength();
    long pointer = getOffset();
    int numBytes = bytes.length;

    int matchPos = 0; // the position in the bytes array that is being matched
    while (pointer < arcSize){
      if (read() == bytes[matchPos]){
        matchPos++;
        if (matchPos >= numBytes){
          // found the full string
          pointer -= numBytes;
          pointer++;
          seek(pointer);
          return pointer;
          }
        }
      else {
        matchPos = 0;
        }
      pointer++;
      }

    return -1;
    }

/**
**********************************************************************************************
Searches and moves the file pointer to the next match
@return the offset to the match
**********************************************************************************************
**/
  public long find(String text){
    return find(DataConverter.toByteArray(text));
    }


/**
**********************************************************************************************
Moves forward 1 byte, and searches for the next match
@return the offset to the match
**********************************************************************************************
**/
  public long findNext(byte byteNum){
    skip(1);
    return find(byteNum);
    }

/**
**********************************************************************************************
Moves forward 1 byte, and searches for the next match
@return the offset to the match
**********************************************************************************************
**/
  public long findNext(byte[] bytes){
    skip(1);
    return find(bytes);
    }

/**
**********************************************************************************************
Moves forward 1 byte, and searches for the next match
@return the offset to the match
**********************************************************************************************
**/
  public long findNext(String text){
    skip(1);
    return find(text);
    }


//////////
//
// TERMINATED STRINGS
//
//////////

/**
**********************************************************************************************
Reads a String until the first <i>terminator</i> is found
@param terminator the byte to stop reading at
@return the String
**********************************************************************************************
**/
  public String readTerminatedString(byte terminator){
    String filename = "";
    byte filenameByte = readByte();
    while (filenameByte != terminator){
      filename += (char)filenameByte;
      filenameByte = readByte();
      }
    return filename;
    }

/**
**********************************************************************************************
Reads a String until the first <i>terminator</i> is found, or until <i>length</i> bytes have
been read. If the string is smaller than the <i>length</i>, the remaining bytes are skipped.
@param terminator the byte to stop reading at
@param length the length of the string
@return the String
**********************************************************************************************
**/
  public String readTerminatedString(byte terminator, int length){
    byte[] bytes = read(length);

    for (int i=0;i<length;i++){
      if (bytes[i] == terminator){
        byte[] nameBytes = new byte[i];
        System.arraycopy(bytes,0,nameBytes,0,i);
        return new String(nameBytes);
        }
      }

    return new String(bytes);
    }

/**
**********************************************************************************************
Reads a String until the first null (byte 0) is found
@return the String
**********************************************************************************************
**/
  public String readNullString(){
    return readTerminatedString((byte)0);
    }

/**
**********************************************************************************************
Reads a String until the first null (byte 0) is found, or until <i>length</i> bytes have been
read. If the string is smaller than the <i>length</i>, the remaining bytes are skipped.
@param length the length of the string
@return the String
**********************************************************************************************
**/
  public String readNullString(int length){
    return readTerminatedString((byte)0, length);
    }


/**
**********************************************************************************************
Reads a String until the first <i>terminator</i> is found
@param terminator the byte to stop reading at
@return the String
**********************************************************************************************
**/
  public String readTerminatedUnicodeString(char terminator){
    String filename = new String(new char[0]);
    char filenameChar = readChar();
    while (filenameChar != terminator){
      filename += filenameChar;
      filenameChar = readChar();
      }
    return filename;
    }

/**
**********************************************************************************************
Reads a String until the first <i>terminator</i> is found, or until <i>length</i> bytes have
been read. If the string is smaller than the <i>length</i>, the remaining bytes are skipped.
@param terminator the byte to stop reading at
@param length the length of the string
@return the String
**********************************************************************************************
**/
  public String readTerminatedUnicodeString(char terminator, int length){
    char[] chars = DataConverter.toCharArray(read(length*2));

    for (int i=0;i<length;i++){
      if (chars[i] == terminator){
        char[] nameChars = new char[i];
        System.arraycopy(chars,0,nameChars,0,i);
        return new String(nameChars);
        }
      }

    return new String(chars);
    }

/**
**********************************************************************************************
Reads a String until the first null (byte 0) is found
@return the String
**********************************************************************************************
**/
  public String readNullUnicodeString(){
    return readTerminatedUnicodeString((char)0);
    }

/**
**********************************************************************************************
Reads a String until the first null (byte 0) is found, or until <i>length</i> bytes have been
read. If the string is smaller than the <i>length</i>, the remaining bytes are skipped.
@param length the length of the string
@return the String
**********************************************************************************************
**/
  public String readNullUnicodeString(int length){
    return readTerminatedUnicodeString((char)0, length);
    }

/**
**********************************************************************************************
Writes a String followed by the terminator byte
@param text the String to write
@param terminator the byte that follows the text
**********************************************************************************************
**/
  public void writeTerminatedString(String text, byte terminator){
    write(text);
    write(terminator);
    }

/**
**********************************************************************************************
Writes a String followed by the terminator byte. If the String is longer than <i>length</i>,
it is shortened to <i>length</i>. If the String+1 is shorter than <i>length</i>, terminator bytes
fill the remaining length.
@param text the String to write
@param length the length of the String
@param terminator the byte that follows the text
**********************************************************************************************
**/
  public void writeTerminatedString(String text, byte terminator, int length){
    int textLength = text.length();
    if (textLength > length){
      text = text.substring(0,length);
      }
    write(text);

    int padding = length - textLength;
    while (padding > 0){
      write(terminator);
      padding--;
      }

    }

/**
**********************************************************************************************
Writes a String followed by a null (byte 0)
@param text the String to write
**********************************************************************************************
**/
  public void writeNullString(String text){
    writeTerminatedString(text,(byte)0);
    }

/**
**********************************************************************************************
Writes a String followed by a null (byte 0). If the String is longer than <i>length</i>, it is
shortened to <i>length</i>. If the String+1 is shorter than <i>length</i>, null bytes fill the
remaining length.
@param text the String to write
@param length the length of the String
**********************************************************************************************
**/
  public void writeNullString(String text, int length){
    writeTerminatedString(text, (byte)0, length);
    }


/**
**********************************************************************************************
Reads a String, and reverses the order of the characters
@return the String
**********************************************************************************************
**/
  public String readReverseString(int length){
    String text = "";
    for (int i=0;i<length;i++){
      text = (char)readByte() + text;
      }
    return text;
    }


/**
**********************************************************************************************
Reads a String, and reverses the order of the characters
@return the String
**********************************************************************************************
**/
  public String readReverseNullString(){
    String text = "";
    String orig = readNullString();
    for (int i=0;i<orig.length();i++){
      text = orig.charAt(i) + text;
      }
    return text;
    }


/**
**********************************************************************************************
Reads a String, and reverses the order of the characters
@return the String
**********************************************************************************************
**/
  public String readReverseNullString(int length){
    String orig = readNullString(length);
    String text = "";
    for (int i=0;i<orig.length();i++){
      text = orig.charAt(i) + text;
      }
    return text;
    }


/**
**********************************************************************************************
Writes a String in the reverse order, followed by nulls to the given <i>length</i>
@param orig the String
@param length the maximum length of the string
**********************************************************************************************
**/
  public void writeReverseString(String orig){
    String text = "";
    for (int i=0;i<orig.length();i++){
      text = orig.charAt(i) + text;
      }
    writeString(text);
    }


/**
**********************************************************************************************
Writes a String in the reverse order, followed by nulls to the given <i>length</i>
@param orig the String
@param length the maximum length of the string
**********************************************************************************************
**/
  public void writeReverseNullString(String orig, int length){
    String text = "";
    for (int i=0;i<orig.length();i++){
      text = orig.charAt(i) + text;
      }
    writeNullString(text);
    }


//////////
//
// FILES
//
//////////


/**
**********************************************************************************************
Gets the extension of the file used for reading/writing
@return the extension of the file
**********************************************************************************************
**/
  public String getExtension(){
    return getExtension(buf.getPath());
    }

/**
**********************************************************************************************
Gets the extension of the <i>file</i>
@param file the file
@return the extension of the file
**********************************************************************************************
**/
  public static String getExtension(File file){
    return getExtension(file.getName());
    }

/**
**********************************************************************************************
Gets the extension of the <i>filename</i>
@param filename the name of the file
@return the extension of the file
**********************************************************************************************
**/
  public static String getExtension(String filename){
    int dotPos = filename.lastIndexOf(".");
    if (dotPos > 0){
      // Look for the directory slashes.
      // An extension dot must appear after all the directory slashes,
      // otherwise the dot is part of a directory name

      int slashPos = filename.lastIndexOf("/");
      if (slashPos > dotPos){
        return "";
        }

      slashPos = filename.lastIndexOf("\\");
      if (slashPos > dotPos){
        return "";
        }

      return filename.substring(dotPos + 1).toLowerCase();
      }
    else {
      return "";
      }
    }



}