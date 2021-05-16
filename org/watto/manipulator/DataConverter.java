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

import java.nio.*;

/**
**********************************************************************************************
Converts data between different formats.
**********************************************************************************************
**/

public class DataConverter{

  /** Table of hex values **/
  protected final static char[] hexTable = new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

  /** Little-endian ordering **/
  public static int LITTLE_ENDIAN = 0;
  /** Big-endian ordering **/
  public static int BIG_ENDIAN = 1;

  /** The format used by generic methods **/
  public static int format = LITTLE_ENDIAN;


/**
**********************************************************************************************
Default constructor. All methods of this class are static, so you don't really need to create
an object of this class unless you really want to. The ordering format is set to LITTLE-ENDIAN
**********************************************************************************************
**/
  public DataConverter(){
    this(LITTLE_ENDIAN);
    }


/**
**********************************************************************************************
Default constructor with specified <i>format</i>. All methods of this class are static, so
you don't really need to create an object of this class unless you really want to.
@param format the ordering format
**********************************************************************************************
**/
  public DataConverter(int format){
    setFormat(format);
    }


  //////////
  //
  // WORKING METHODS
  //
  //////////


  /**
  **********************************************************************************************
  Gets the format used with the generic methods
  @return the ordering format
  **********************************************************************************************
  **/
  public static int getFormat(){
    return format;
    }

  /**
  **********************************************************************************************
  Sets the format used with the generic methods
  @param formatIn the ordering format
  **********************************************************************************************
  **/
  public static void setFormat(int formatIn){
    format = formatIn;
    }


  //////////
  //
  // PRIMITIVE CONVERSIONS
  //
  //////////

  /////
  // PRIMITIVE --> BOOLEAN
  /////

  public static boolean toBoolean(boolean in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(boolean[] in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(byte in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(byte[] in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(short in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(short[] in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(int in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(int[] in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(long in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(long[] in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(float in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(float[] in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(double in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(double[] in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(char in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(char[] in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(String in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }

  public static boolean toBoolean(Hex in){
    if (format == BIG_ENDIAN){
      return toBooleanB(in);
      }
    return toBooleanL(in);
    }


  /////
  // PRIMITIVE --> BOOLEAN (LITTLE-ENDIAN)
  /////

  public static boolean toBooleanL(boolean in){
    return in;
    }

  public static boolean toBooleanL(boolean[] in){
    return in[0];
    }

  public static boolean toBooleanL(byte in){
    if (in == 0){
      return false;
      }
    return true;
    }

  public static boolean toBooleanL(byte[] in){
    if (in[0] == 0){
      return false;
      }
    return true;
    }

  public static boolean toBooleanL(short in){
    if (in == 0){
      return false;
      }
    return true;
    }

  public static boolean toBooleanL(short[] in){
    if (in[0] == 0){
      return false;
      }
    return true;
    }

  public static boolean toBooleanL(int in){
    if (in == 0){
      return false;
      }
    return true;
    }

  public static boolean toBooleanL(int[] in){
    if (in[0] == 0){
      return false;
      }
    return true;
    }

  public static boolean toBooleanL(long in){
    if (in == 0){
      return false;
      }
    return true;
    }

  public static boolean toBooleanL(long[] in){
    if (in[0] == 0){
      return false;
      }
    return true;
    }

  public static boolean toBooleanL(float in){
    if (in == 0){
      return false;
      }
    return true;
    }

  public static boolean toBooleanL(float[] in){
    if (in[0] == 0){
      return false;
      }
    return true;
    }

  public static boolean toBooleanL(double in){
    if (in == 0){
      return false;
      }
    return true;
    }

  public static boolean toBooleanL(double[] in){
    if (in[0] == 0){
      return false;
      }
    return true;
    }

  public static boolean toBooleanL(char in){
    if (in == '0' || in == 'f' || in == 'F'){
      return false;
      }
    return true;
    }

  public static boolean toBooleanL(char[] in){
    if (in[0] == '0' || in[0] == 'f' || in[0] == 'F'){
      return false;
      }
    return true;
    }

  public static boolean toBooleanL(String in){
    if (in.equalsIgnoreCase("true") || in.equalsIgnoreCase("t") || in.equals("1")){
      return true;
      }
    else if (in.equalsIgnoreCase("false") || in.equalsIgnoreCase("f") || in.equals("0")){
      return false;
      }
    else {
      return toBooleanL(toByteArrayL(in));
      }
    }

  public static boolean toBooleanL(Hex in){
    return toBooleanL(toByteArrayL(in));
    }


  /////
  // PRIMITIVE --> BOOLEAN (BIG-ENDIAN)
  /////

  public static boolean toBooleanB(boolean in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(boolean[] in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(byte in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(byte[] in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(short in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(short[] in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(int in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(int[] in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(long in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(long[] in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(float in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(float[] in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(double in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(double[] in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(char in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(char[] in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(String in){
    return toBooleanL(changeFormat(in));
    }

  public static boolean toBooleanB(Hex in){
    return toBooleanL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> BOOLEAN ARRAY
  /////

  public static boolean[] toBooleanArray(boolean in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(boolean[] in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(byte in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(byte[] in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(short in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(short[] in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(int in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(int[] in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(long in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(long[] in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(float in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(float[] in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(double in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(double[] in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(char in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(char[] in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(String in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }

  public static boolean[] toBooleanArray(Hex in){
    if (format == BIG_ENDIAN){
      return toBooleanArrayB(in);
      }
    return toBooleanArrayL(in);
    }


  /////
  // PRIMITIVE --> BOOLEAN ARRAY (LITTLE-ENDIAN)
  /////

  public static boolean[] toBooleanArrayL(boolean in){
    return new boolean[]{in};
    }

  public static boolean[] toBooleanArrayL(boolean[] in){
    return in;
    }

  public static boolean[] toBooleanArrayL(byte in){
    boolean[] bits = new boolean[8];

    if ((in & 128) == 128){ bits[0] = true; } else { bits[0] = false; }
    if ((in & 64) == 64)  { bits[1] = true; } else { bits[1] = false; }
    if ((in & 32) == 32)  { bits[2] = true; } else { bits[2] = false; }
    if ((in & 16) == 16)  { bits[3] = true; } else { bits[3] = false; }
    if ((in & 8) == 8)    { bits[4] = true; } else { bits[4] = false; }
    if ((in & 4) == 4)    { bits[5] = true; } else { bits[5] = false; }
    if ((in & 2) == 2)    { bits[6] = true; } else { bits[6] = false; }
    if ((in & 1) == 1)    { bits[7] = true; } else { bits[7] = false; }

    return bits;
    }

  public static boolean[] toBooleanArrayL(byte[] in){
    boolean[] bits = new boolean[in.length*8];
    for (int i=0,j=0;i<in.length;i++,j+=8){
      boolean[] byteBits = toBooleanArrayL(in[i]);
      System.arraycopy(byteBits,0,bits,j,8);
      }
    return bits;
    }

  public static boolean[] toBooleanArrayL(short in){
    return toBooleanArrayL(toByteArrayL(in));
    }

  public static boolean[] toBooleanArrayL(short[] in){
    return toBooleanArrayL(toByteArrayL(in));
    }

  public static boolean[] toBooleanArrayL(int in){
    return toBooleanArrayL(toByteArrayL(in));
    }

  public static boolean[] toBooleanArrayL(int[] in){
    return toBooleanArrayL(toByteArrayL(in));
    }

  public static boolean[] toBooleanArrayL(long in){
    return toBooleanArrayL(toByteArrayL(in));
    }

  public static boolean[] toBooleanArrayL(long[] in){
    return toBooleanArrayL(toByteArrayL(in));
    }

  public static boolean[] toBooleanArrayL(float in){
    return toBooleanArrayL(toByteArrayL(in));
    }

  public static boolean[] toBooleanArrayL(float[] in){
    return toBooleanArrayL(toByteArrayL(in));
    }

  public static boolean[] toBooleanArrayL(double in){
    return toBooleanArrayL(toByteArrayL(in));
    }

  public static boolean[] toBooleanArrayL(double[] in){
    return toBooleanArrayL(toByteArrayL(in));
    }

  public static boolean[] toBooleanArrayL(char in){
    return toBooleanArrayL(toByteArrayL(in));
    }

  public static boolean[] toBooleanArrayL(char[] in){
    return toBooleanArrayL(toByteArrayL(in));
    }

  public static boolean[] toBooleanArrayL(String in){
    return toBooleanArrayL(toByteArrayL(in));
    }

  public static boolean[] toBooleanArrayL(Hex in){
    return toBooleanArrayL(toByteArrayL(in));
    }


  /////
  // PRIMITIVE --> BOOLEAN ARRAY (BIG-ENDIAN)
  /////

  public static boolean[] toBooleanArrayB(boolean in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(boolean[] in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(byte in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(byte[] in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(short in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(short[] in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(int in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(int[] in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(long in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(long[] in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(float in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(float[] in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(double in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(double[] in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(char in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(char[] in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(String in){
    return toBooleanArrayL(changeFormat(in));
    }

  public static boolean[] toBooleanArrayB(Hex in){
    return toBooleanArrayL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> BYTE
  /////

  public static byte toByte(boolean in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(boolean[] in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(byte in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(byte[] in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(short in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(short[] in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(int in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(int[] in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(long in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(long[] in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(float in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(float[] in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(double in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(double[] in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(char in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(char[] in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(String in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }

  public static byte toByte(Hex in){
    if (format == BIG_ENDIAN){
      return toByteB(in);
      }
    return toByteL(in);
    }


  /////
  // PRIMITIVE --> BYTE (LITTLE-ENDIAN)
  /////

  public static byte toByteL(boolean in){
    if (in){
      return (byte)1;
      }
    return (byte)0;
    }

  public static byte toByteL(boolean[] in){
    return toByteArrayL(in)[0];
    }

  public static byte toByteL(byte in){
    return in;
    }

  public static byte toByteL(byte[] in){
    return in[0];
    }

  public static byte toByteL(short in){
    return (byte)in;
    }

  public static byte toByteL(short[] in){
    return (byte)in[0];
    }

  public static byte toByteL(int in){
    return (byte)in;
    }

  public static byte toByteL(int[] in){
    return (byte)in[0];
    }

  public static byte toByteL(long in){
    return (byte)in;
    }

  public static byte toByteL(long[] in){
    return (byte)in[0];
    }

  public static byte toByteL(float in){
    return (byte)in;
    }

  public static byte toByteL(float[] in){
    return (byte)in[0];
    }

  public static byte toByteL(double in){
    return (byte)in;
    }

  public static byte toByteL(double[] in){
    return (byte)in[0];
    }

  public static byte toByteL(char in){
    return (byte)in;
    }

  public static byte toByteL(char[] in){
    return (byte)in[0];
    }

  public static byte toByteL(String in){
    return toByteArrayL(in)[0];
    }

  public static byte toByteL(Hex in){
    return toByteArrayL(in)[0];
    }


  /////
  // PRIMITIVE --> BYTE (BIG-ENDIAN)
  /////

  public static byte toByteB(boolean in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(boolean[] in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(byte in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(byte[] in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(short in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(short[] in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(int in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(int[] in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(long in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(long[] in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(float in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(float[] in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(double in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(double[] in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(char in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(char[] in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(String in){
    return toByteL(changeFormat(in));
    }

  public static byte toByteB(Hex in){
    return toByteL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> BYTE ARRAY
  /////

  public static byte[] toByteArray(boolean in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(boolean[] in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(byte in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(byte[] in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(short in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(short[] in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(int in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(int[] in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(long in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(long[] in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(float in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(float[] in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(double in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(double[] in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(char in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(char[] in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(String in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }

  public static byte[] toByteArray(Hex in){
    if (format == BIG_ENDIAN){
      return toByteArrayB(in);
      }
    return toByteArrayL(in);
    }


  /////
  // PRIMITIVE --> BYTE ARRAY (LITTLE-ENDIAN)
  /////

  public static byte[] toByteArrayL(boolean in){
    if (in){
      return new byte[]{(byte)1};
      }
    return new byte[]{(byte)0};
    }

  public static byte[] toByteArrayL(boolean[] in){
    int numBytes = in.length/8;
    if (in.length%8 != 0){
      numBytes++;
      }

    byte[] bytes = new byte[numBytes];

    for (int b=in.length-1,p=0;b>=0;b-=8,p++){
      int value = 0;

      for (int i=b,j=0;i>b-8 && i>=0;i--,j++){
        if (in[i]){
          value += Math.pow(2,j);
          }
        }

      bytes[p] = (byte)value;

      }

    return bytes;
    }

  public static byte[] toByteArrayL(byte in){
    return new byte[]{in};
    }

  public static byte[] toByteArrayL(byte[] in){
    return in;
    }

  public static byte[] toByteArrayL(short in){
    byte[] bArray = new byte[2];
    java.nio.ByteBuffer bBuffer = java.nio.ByteBuffer.wrap(bArray);
    bBuffer.order(ByteOrder.LITTLE_ENDIAN);
    ShortBuffer lBuffer = bBuffer.asShortBuffer();
    lBuffer.put(0, in);
    return bArray;
    }

  public static byte[] toByteArrayL(short[] in){
    byte[] out = new byte[in.length*2];

    for (int i=0,j=0;i<in.length;i++,j+=2){
      byte[] shortBytes = toByteArrayL(in[i]);
      System.arraycopy(shortBytes,0,out,j,2);
      }

    return out;
    }

  public static byte[] toByteArrayL(int in){
    byte[] bArray = new byte[4];
    java.nio.ByteBuffer bBuffer = java.nio.ByteBuffer.wrap(bArray);
    bBuffer.order(ByteOrder.LITTLE_ENDIAN);
    IntBuffer lBuffer = bBuffer.asIntBuffer();
    lBuffer.put(0, in);
    return bArray;
    }

  public static byte[] toByteArrayL(int[] in){
    byte[] out = new byte[in.length*4];

    for (int i=0,j=0;i<in.length;i++,j+=4){
      byte[] intBytes = toByteArrayL(in[i]);
      System.arraycopy(intBytes,0,out,j,4);
      }

    return out;
    }

  public static byte[] toByteArrayL(long in){
    byte[] bArray = new byte[8];
    java.nio.ByteBuffer bBuffer = java.nio.ByteBuffer.wrap(bArray);
    bBuffer.order(ByteOrder.LITTLE_ENDIAN);
    LongBuffer lBuffer = bBuffer.asLongBuffer();
    lBuffer.put(0, in);
    return bArray;
    }

  public static byte[] toByteArrayL(long[] in){
    byte[] out = new byte[in.length*8];

    for (int i=0,j=0;i<in.length;i++,j+=8){
      byte[] longBytes = toByteArrayL(in[i]);
      System.arraycopy(longBytes,0,out,j,8);
      }

    return out;
    }

  public static byte[] toByteArrayL(float in){
    byte[] bArray = new byte[4];
    java.nio.ByteBuffer bBuffer = java.nio.ByteBuffer.wrap(bArray);
    bBuffer.order(ByteOrder.LITTLE_ENDIAN);
    FloatBuffer lBuffer = bBuffer.asFloatBuffer();
    lBuffer.put(0, in);
    return bArray;
    }

  public static byte[] toByteArrayL(float[] in){
    byte[] out = new byte[in.length*4];

    for (int i=0,j=0;i<in.length;i++,j+=4){
      byte[] floatBytes = toByteArrayL(in[i]);
      System.arraycopy(floatBytes,0,out,j,4);
      }

    return out;
    }

  public static byte[] toByteArrayL(double in){
    byte[] bArray = new byte[8];
    java.nio.ByteBuffer bBuffer = java.nio.ByteBuffer.wrap(bArray);
    bBuffer.order(ByteOrder.LITTLE_ENDIAN);
    DoubleBuffer lBuffer = bBuffer.asDoubleBuffer();
    lBuffer.put(0, in);
    return bArray;
    }

  public static byte[] toByteArrayL(double[] in){
    byte[] out = new byte[in.length*8];

    for (int i=0,j=0;i<in.length;i++,j+=8){
      byte[] doubleBytes = toByteArrayL(in[i]);
      System.arraycopy(doubleBytes,0,out,j,8);
      }

    return out;
    }

  public static byte[] toByteArrayL(char in){
    byte[] bArray = new byte[2];
    java.nio.ByteBuffer bBuffer = java.nio.ByteBuffer.wrap(bArray);
    bBuffer.order(ByteOrder.LITTLE_ENDIAN);
    CharBuffer lBuffer = bBuffer.asCharBuffer();
    lBuffer.put(0, in);
    return bArray;
    }

  public static byte[] toByteArrayL(char[] in){
    byte[] out = new byte[in.length*2];

    for (int i=0,j=0;i<in.length;i++,j+=2){
      byte[] charBytes = toByteArrayL(in[i]);
      System.arraycopy(charBytes,0,out,j,2);
      }

    return out;
    }

  public static byte[] toByteArrayL(String in){
    return in.getBytes();
    }

  public static byte[] toByteArrayL(Hex in){
    byte[] bArray = new byte[in.length()/2];

    String text = in.toString();

    int bPos = 0;
    for (int i=0;i<text.length();i+=2){
      byte b = 0;

      char high = text.charAt(i);
      char low = text.charAt(i+1);

      for (int h=0;h<hexTable.length;h++){
        if (hexTable[h] == high){
          b += (h*16);
          }
        if (hexTable[h] == low){
          b += (h);
          }
        }

      bArray[bPos] = b;
      bPos++;
      }

    return bArray;
    }


  /////
  // PRIMITIVE --> BYTE ARRAY (BIG-ENDIAN)
  /////

  public static byte[] toByteArrayB(boolean in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(boolean[] in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(byte in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(byte[] in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(short in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(short[] in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(int in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(int[] in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(long in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(long[] in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(float in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(float[] in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(double in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(double[] in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(char in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(char[] in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(String in){
    return toByteArrayL(changeFormat(in));
    }

  public static byte[] toByteArrayB(Hex in){
    return toByteArrayL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> SHORT
  /////

  public static short toShort(boolean in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(boolean[] in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(byte in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(byte[] in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(short in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(short[] in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(int in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(int[] in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(long in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(long[] in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(float in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(float[] in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(double in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(double[] in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(char in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(char[] in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(String in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }

  public static short toShort(Hex in){
    if (format == BIG_ENDIAN){
      return toShortB(in);
      }
    return toShortL(in);
    }


  /////
  // PRIMITIVE --> SHORT (LITTLE-ENDIAN)
  /////

  public static short toShortL(boolean in){
    if (in){
      return (short)1;
      }
    return (short)0;
    }

  public static short toShortL(boolean[] in){
    return toShortL(toByteArrayL(in));
    }

  public static short toShortL(byte in){
    if (in < 0){
      return (short)(256 + in);
      }
    return (short)in;
    }

  public static short toShortL(byte[] in){
    java.nio.ByteBuffer bBuffer = java.nio.ByteBuffer.wrap(in);
    bBuffer.order(ByteOrder.LITTLE_ENDIAN);
    ShortBuffer lBuffer = bBuffer.asShortBuffer();
    return lBuffer.get();
    }

  public static short toShortL(short in){
    return in;
    }

  public static short toShortL(short[] in){
    return in[0];
    }

  public static short toShortL(int in){
    return toShortL(toByteArrayL(in));
    }

  public static short toShortL(int[] in){
    return toShortL(toByteArrayL(in));
    }

  public static short toShortL(long in){
    return toShortL(toByteArrayL(in));
    }

  public static short toShortL(long[] in){
    return toShortL(toByteArrayL(in));
    }

  public static short toShortL(float in){
    return toShortL(toByteArrayL(in));
    }

  public static short toShortL(float[] in){
    return toShortL(toByteArrayL(in));
    }

  public static short toShortL(double in){
    return toShortL(toByteArrayL(in));
    }

  public static short toShortL(double[] in){
    return toShortL(toByteArrayL(in));
    }

  public static short toShortL(char in){
    return toShortL(toByteArrayL(in));
    }

  public static short toShortL(char[] in){
    return toShortL(toByteArrayL(in));
    }

  public static short toShortL(String in){
    return toShortL(toByteArrayL(in));
    }

  public static short toShortL(Hex in){
    return toShortL(toByteArrayL(in));
    }


  /////
  // PRIMITIVE --> SHORT (BIG-ENDIAN)
  /////

  public static short toShortB(boolean in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(boolean[] in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(byte in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(byte[] in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(short in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(short[] in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(int in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(int[] in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(long in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(long[] in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(float in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(float[] in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(double in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(double[] in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(char in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(char[] in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(String in){
    return toShortL(changeFormat(in));
    }

  public static short toShortB(Hex in){
    return toShortL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> SHORT ARRAY
  /////

  public static short[] toShortArray(boolean in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(boolean[] in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(byte in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(byte[] in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(short in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(short[] in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(int in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(int[] in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(long in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(long[] in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(float in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(float[] in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(double in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(double[] in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(char in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(char[] in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(String in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }

  public static short[] toShortArray(Hex in){
    if (format == BIG_ENDIAN){
      return toShortArrayB(in);
      }
    return toShortArrayL(in);
    }


  /////
  // PRIMITIVE --> SHORT ARRAY (LITTLE-ENDIAN)
  /////

  public static short[] toShortArrayL(boolean in){
    if (in){
      return new short[]{(short)1};
      }
    return new short[]{(short)0};
    }

  public static short[] toShortArrayL(boolean[] in){
    return toShortArrayL(toByteArrayL(in));
    }

  public static short[] toShortArrayL(byte in){
    return new short[]{toShortL(in)};
    }

  public static short[] toShortArrayL(byte[] in){
    int numShort = in.length/2;

    if (in.length%2 == 1){
      // uneven length
      short[] out = new short[numShort+1];

      for (int i=0,j=0;i<numShort;i++,j+=2){
        out[i] = toShortL(new byte[]{in[j],in[j+1]});
        }

      out[numShort] = toShortL(new byte[]{in[in.length-1],0});

      return out;
      }
    else {
      //even length
      short[] out = new short[numShort];

      for (int i=0,j=0;i<numShort;i++,j+=2){
        out[i] = toShortL(new byte[]{in[j],in[j+1]});
        }

      return out;
      }

    }

  public static short[] toShortArrayL(short in){
    return new short[]{in};
    }

  public static short[] toShortArrayL(short[] in){
    return in;
    }

  public static short[] toShortArrayL(int in){
    return toShortArrayL(toByteArrayL(in));
    }

  public static short[] toShortArrayL(int[] in){
    return toShortArrayL(toByteArrayL(in));
    }

  public static short[] toShortArrayL(long in){
    return toShortArrayL(toByteArrayL(in));
    }

  public static short[] toShortArrayL(long[] in){
    return toShortArrayL(toByteArrayL(in));
    }

  public static short[] toShortArrayL(float in){
    return toShortArrayL(toByteArrayL(in));
    }

  public static short[] toShortArrayL(float[] in){
    return toShortArrayL(toByteArrayL(in));
    }

  public static short[] toShortArrayL(double in){
    return toShortArrayL(toByteArrayL(in));
    }

  public static short[] toShortArrayL(double[] in){
    return toShortArrayL(toByteArrayL(in));
    }

  public static short[] toShortArrayL(char in){
    return toShortArrayL(toByteArrayL(in));
    }

  public static short[] toShortArrayL(char[] in){
    return toShortArrayL(toByteArrayL(in));
    }

  public static short[] toShortArrayL(String in){
    return toShortArrayL(toByteArrayL(in));
    }

  public static short[] toShortArrayL(Hex in){
    return toShortArrayL(toByteArrayL(in));
    }


  /////
  // PRIMITIVE --> SHORT ARRAY (BIG-ENDIAN)
  /////

  public static short[] toShortArrayB(boolean in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(boolean[] in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(byte in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(byte[] in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(short in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(short[] in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(int in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(int[] in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(long in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(long[] in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(float in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(float[] in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(double in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(double[] in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(char in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(char[] in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(String in){
    return toShortArrayL(changeFormat(in));
    }

  public static short[] toShortArrayB(Hex in){
    return toShortArrayL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> INT
  /////

  public static int toInt(boolean in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(boolean[] in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(byte in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(byte[] in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(short in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(short[] in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(int in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(int[] in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(long in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(long[] in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(float in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(float[] in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(double in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(double[] in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(char in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(char[] in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(String in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }

  public static int toInt(Hex in){
    if (format == BIG_ENDIAN){
      return toIntB(in);
      }
    return toIntL(in);
    }


  /////
  // PRIMITIVE --> INT (LITTLE-ENDIAN)
  /////

  public static int toIntL(boolean in){
    if (in){
      return 1;
      }
    return 0;
    }

  public static int toIntL(boolean[] in){
    return toIntL(toByteArrayL(in));
    }

  public static int toIntL(byte in){
    if (in < 0){
      return (int)(256 + in);
      }
    return (int)in;
    }

  public static int toIntL(byte[] in){
    java.nio.ByteBuffer bBuffer = java.nio.ByteBuffer.wrap(in);
    bBuffer.order(ByteOrder.LITTLE_ENDIAN);
    IntBuffer lBuffer = bBuffer.asIntBuffer();
    return lBuffer.get();
    }

  public static int toIntL(short in){
    return toIntL(toByteArrayL(in));
    }

  public static int toIntL(short[] in){
    return toIntL(toByteArrayL(in));
    }

  public static int toIntL(int in){
    return in;
    }

  public static int toIntL(int[] in){
    return toIntL(toByteArrayL(in));
    }

  public static int toIntL(long in){
    return toIntL(toByteArrayL(in));
    }

  public static int toIntL(long[] in){
    return toIntL(toByteArrayL(in));
    }

  public static int toIntL(float in){
    return toIntL(toByteArrayL(in));
    }

  public static int toIntL(float[] in){
    return toIntL(toByteArrayL(in));
    }

  public static int toIntL(double in){
    return toIntL(toByteArrayL(in));
    }

  public static int toIntL(double[] in){
    return toIntL(toByteArrayL(in));
    }

  public static int toIntL(char in){
    return toIntL(toByteArrayL(in));
    }

  public static int toIntL(char[] in){
    return toIntL(toByteArrayL(in));
    }

  public static int toIntL(String in){
    return toIntL(toByteArrayL(in));
    }

  public static int toIntL(Hex in){
    return toIntL(toByteArrayL(in));
    }


  /////
  // PRIMITIVE --> INT (BIG-ENDIAN)
  /////

  public static int toIntB(boolean in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(boolean[] in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(byte in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(byte[] in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(short in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(short[] in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(int in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(int[] in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(long in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(long[] in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(float in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(float[] in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(double in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(double[] in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(char in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(char[] in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(String in){
    return toIntL(changeFormat(in));
    }

  public static int toIntB(Hex in){
    return toIntL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> INT ARRAY
  /////

  public static int[] toIntArray(boolean in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(boolean[] in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(byte in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(byte[] in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(short in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(short[] in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(int in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(int[] in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(long in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(long[] in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(float in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(float[] in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(double in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(double[] in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(char in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(char[] in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(String in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }

  public static int[] toIntArray(Hex in){
    if (format == BIG_ENDIAN){
      return toIntArrayB(in);
      }
    return toIntArrayL(in);
    }


  /////
  // PRIMITIVE --> INT ARRAY (LITTLE-ENDIAN)
  /////

  public static int[] toIntArrayL(boolean in){
    if (in){
      return new int[]{1};
      }
    return new int[]{0};
    }

  public static int[] toIntArrayL(boolean[] in){
    return toIntArrayL(toByteArrayL(in));
    }

  public static int[] toIntArrayL(byte in){
    return new int[]{toIntL(in)};
    }

  public static int[] toIntArrayL(byte[] in){
    int numInt = in.length/4;

    int[] out;

    int remainder = in.length%4;
    if (remainder != 0){
      // uneven length
      out = new int[numInt+1];
      }
    else {
      //even length
      out = new int[numInt];
      }


    for (int i=0,j=0;i<numInt;i++,j+=4){
      out[i] = toIntL(new byte[]{in[j],in[j+1],in[j+2],in[j+3]});
      }

    if (remainder == 1){
      out[numInt] = toIntL(new byte[]{in[in.length-1],0,0,0});
      }
    else if (remainder == 2){
      out[numInt] = toIntL(new byte[]{in[in.length-2],in[in.length-1],0,0});
      }
    else if (remainder == 3){
      out[numInt] = toIntL(new byte[]{in[in.length-3],in[in.length-2],in[in.length-1],0});
      }

    return out;
    }

  public static int[] toIntArrayL(short in){
    return toIntArrayL(toByteArrayL(in));
    }

  public static int[] toIntArrayL(short[] in){
    return toIntArrayL(toByteArrayL(in));
    }

  public static int[] toIntArrayL(int in){
    return new int[]{in};
    }

  public static int[] toIntArrayL(int[] in){
    return in;
    }

  public static int[] toIntArrayL(long in){
    return toIntArrayL(toByteArrayL(in));
    }

  public static int[] toIntArrayL(long[] in){
    return toIntArrayL(toByteArrayL(in));
    }

  public static int[] toIntArrayL(float in){
    return toIntArrayL(toByteArrayL(in));
    }

  public static int[] toIntArrayL(float[] in){
    return toIntArrayL(toByteArrayL(in));
    }

  public static int[] toIntArrayL(double in){
    return toIntArrayL(toByteArrayL(in));
    }

  public static int[] toIntArrayL(double[] in){
    return toIntArrayL(toByteArrayL(in));
    }

  public static int[] toIntArrayL(char in){
    return toIntArrayL(toByteArrayL(in));
    }

  public static int[] toIntArrayL(char[] in){
    return toIntArrayL(toByteArrayL(in));
    }

  public static int[] toIntArrayL(String in){
    return toIntArrayL(toByteArrayL(in));
    }

  public static int[] toIntArrayL(Hex in){
    return toIntArrayL(toByteArrayL(in));
    }


  /////
  // PRIMITIVE --> INT ARRAY (BIG-ENDIAN)
  /////

  public static int[] toIntArrayB(boolean in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(boolean[] in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(byte in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(byte[] in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(short in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(short[] in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(int in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(int[] in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(long in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(long[] in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(float in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(float[] in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(double in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(double[] in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(char in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(char[] in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(String in){
    return toIntArrayL(changeFormat(in));
    }

  public static int[] toIntArrayB(Hex in){
    return toIntArrayL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> LONG
  /////

  public static long toLong(boolean in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(boolean[] in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(byte in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(byte[] in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(short in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(short[] in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(int in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(int[] in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(long in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(long[] in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(float in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(float[] in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(double in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(double[] in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(char in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(char[] in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(String in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }

  public static long toLong(Hex in){
    if (format == BIG_ENDIAN){
      return toLongB(in);
      }
    return toLongL(in);
    }


  /////
  // PRIMITIVE --> LONG (LITTLE-ENDIAN)
  /////

  public static long toLongL(boolean in){
    if (in){
      return 1;
      }
    return 0;
    }

  public static long toLongL(boolean[] in){
    return toLongL(toByteArrayL(in));
    }

  public static long toLongL(byte in){
    if (in < 0){
      return (long)(256 + in);
      }
    return (long)in;
    }

  public static long toLongL(byte[] in){
    java.nio.ByteBuffer bBuffer = java.nio.ByteBuffer.wrap(in);
    bBuffer.order(ByteOrder.LITTLE_ENDIAN);
    LongBuffer lBuffer = bBuffer.asLongBuffer();
    return lBuffer.get();
    }

  public static long toLongL(short in){
    return (long)in;
    }

  public static long toLongL(short[] in){
    return toLongL(toByteArrayL(in));
    }

  public static long toLongL(int in){
    return (long)in;
    }

  public static long toLongL(int[] in){
    return toLongL(toByteArrayL(in));
    }

  public static long toLongL(long in){
    return in;
    }

  public static long toLongL(long[] in){
    return toLongL(toByteArrayL(in));
    }

  public static long toLongL(float in){
    return (long)in;
    }

  public static long toLongL(float[] in){
    return toLongL(toByteArrayL(in));
    }

  public static long toLongL(double in){
    return (long)in;
    }

  public static long toLongL(double[] in){
    return toLongL(toByteArrayL(in));
    }

  public static long toLongL(char in){
    return toLongL(toByteArrayL(in));
    }

  public static long toLongL(char[] in){
    return toLongL(toByteArrayL(in));
    }

  public static long toLongL(String in){
    return toLongL(toByteArrayL(in));
    }

  public static long toLongL(Hex in){
    return toLongL(toByteArrayL(in));
    }


  /////
  // PRIMITIVE --> LONG (BIG-ENDIAN)
  /////

  public static long toLongB(boolean in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(boolean[] in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(byte in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(byte[] in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(short in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(short[] in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(int in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(int[] in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(long in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(long[] in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(float in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(float[] in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(double in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(double[] in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(char in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(char[] in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(String in){
    return toLongL(changeFormat(in));
    }

  public static long toLongB(Hex in){
    return toLongL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> LONG ARRAY
  /////

  public static long[] toLongArray(boolean in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(boolean[] in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(byte in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(byte[] in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(short in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(short[] in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(int in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(int[] in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(long in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(long[] in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(float in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(float[] in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(double in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(double[] in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(char in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(char[] in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(String in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }

  public static long[] toLongArray(Hex in){
    if (format == BIG_ENDIAN){
      return toLongArrayB(in);
      }
    return toLongArrayL(in);
    }


  /////
  // PRIMITIVE --> LONG ARRAY (LITTLE-ENDIAN)
  /////

  public static long[] toLongArrayL(boolean in){
    if (in){
      return new long[]{(long)1};
      }
    return new long[]{(long)0};
    }

  public static long[] toLongArrayL(boolean[] in){
    return toLongArrayL(toByteArrayL(in));
    }

  public static long[] toLongArrayL(byte in){
    return new long[]{toLongL(in)};
    }

  public static long[] toLongArrayL(byte[] in){
    int numLong = in.length/8;

    long[] out;

    int remainder = in.length%8;
    if (remainder != 0){
      // uneven length
      out = new long[numLong+1];
      }
    else {
      //even length
      out = new long[numLong];
      }


    for (int i=0,j=0;i<numLong;i++,j+=8){
      out[i] = toLongL(new byte[]{in[j],in[j+1],in[j+2],in[j+3],in[j+4],in[j+5],in[j+6],in[j+7]});
      }

    if (remainder == 1){
      out[numLong] = toLongL(new byte[]{in[in.length-1],0,0,0,0,0,0,0});
      }
    else if (remainder == 2){
      out[numLong] = toLongL(new byte[]{in[in.length-2],in[in.length-1],0,0,0,0,0,0});
      }
    else if (remainder == 3){
      out[numLong] = toLongL(new byte[]{in[in.length-3],in[in.length-2],in[in.length-1],0,0,0,0,0});
      }
    else if (remainder == 4){
      out[numLong] = toLongL(new byte[]{in[in.length-4],in[in.length-3],in[in.length-2],in[in.length-1],0,0,0,0});
      }
    else if (remainder == 5){
      out[numLong] = toLongL(new byte[]{in[in.length-5],in[in.length-4],in[in.length-3],in[in.length-2],in[in.length-1],0,0,0});
      }
    else if (remainder == 6){
      out[numLong] = toLongL(new byte[]{in[in.length-6],in[in.length-5],in[in.length-4],in[in.length-3],in[in.length-2],in[in.length-1],0,0});
      }
    else if (remainder == 7){
      out[numLong] = toLongL(new byte[]{in[in.length-7],in[in.length-6],in[in.length-5],in[in.length-4],in[in.length-3],in[in.length-2],in[in.length-1],0});
      }

    return out;
    }

  public static long[] toLongArrayL(short in){
    return toLongArrayL(toByteArrayL(in));
    }

  public static long[] toLongArrayL(short[] in){
    return toLongArrayL(toByteArrayL(in));
    }

  public static long[] toLongArrayL(int in){
    return toLongArrayL(toByteArrayL(in));
    }

  public static long[] toLongArrayL(int[] in){
    return toLongArrayL(toByteArrayL(in));
    }

  public static long[] toLongArrayL(long in){
    return toLongArrayL(toByteArrayL(in));
    }

  public static long[] toLongArrayL(long[] in){
    return in;
    }

  public static long[] toLongArrayL(float in){
    return toLongArrayL(toByteArrayL(in));
    }

  public static long[] toLongArrayL(float[] in){
    return toLongArrayL(toByteArrayL(in));
    }

  public static long[] toLongArrayL(double in){
    return toLongArrayL(toByteArrayL(in));
    }

  public static long[] toLongArrayL(double[] in){
    return toLongArrayL(toByteArrayL(in));
    }

  public static long[] toLongArrayL(char in){
    return toLongArrayL(toByteArrayL(in));
    }

  public static long[] toLongArrayL(char[] in){
    return toLongArrayL(toByteArrayL(in));
    }

  public static long[] toLongArrayL(String in){
    return toLongArrayL(toByteArrayL(in));
    }

  public static long[] toLongArrayL(Hex in){
    return toLongArrayL(toByteArrayL(in));
    }


  /////
  // PRIMITIVE --> LONG ARRAY (BIG-ENDIAN)
  /////

  public static long[] toLongArrayB(boolean in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(boolean[] in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(byte in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(byte[] in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(short in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(short[] in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(int in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(int[] in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(long in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(long[] in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(float in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(float[] in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(double in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(double[] in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(char in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(char[] in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(String in){
    return toLongArrayL(changeFormat(in));
    }

  public static long[] toLongArrayB(Hex in){
    return toLongArrayL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> FLOAT
  /////

  public static float toFloat(boolean in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(boolean[] in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(byte in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(byte[] in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(short in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(short[] in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(int in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(int[] in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(long in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(long[] in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(float in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(float[] in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(double in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(double[] in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(char in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(char[] in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(String in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }

  public static float toFloat(Hex in){
    if (format == BIG_ENDIAN){
      return toFloatB(in);
      }
    return toFloatL(in);
    }


  /////
  // PRIMITIVE --> FLOAT (LITTLE-ENDIAN)
  /////

  public static float toFloatL(boolean in){
    if (in){
      return 1;
      }
    return 0;
    }

  public static float toFloatL(boolean[] in){
    return toFloatL(toByteArrayL(in));
    }

  public static float toFloatL(byte in){
    if (in < 0){
      return (float)(256 + in);
      }
    return (float)in;
    }

  public static float toFloatL(byte[] in){
    java.nio.ByteBuffer bBuffer = java.nio.ByteBuffer.wrap(in);
    bBuffer.order(ByteOrder.LITTLE_ENDIAN);
    FloatBuffer lBuffer = bBuffer.asFloatBuffer();
    return lBuffer.get();
    }

  public static float toFloatL(short in){
    return toFloatL(toByteArrayL(in));
    }

  public static float toFloatL(short[] in){
    return toFloatL(toByteArrayL(in));
    }

  public static float toFloatL(int in){
    return toFloatL(toByteArrayL(in));
    }

  public static float toFloatL(int[] in){
    return toFloatL(toByteArrayL(in));
    }

  public static float toFloatL(long in){
    return toFloatL(toByteArrayL(in));
    }

  public static float toFloatL(long[] in){
    return toFloatL(toByteArrayL(in));
    }

  public static float toFloatL(float in){
    return (float)in;
    }

  public static float toFloatL(float[] in){
    return toFloatL(toByteArrayL(in));
    }

  public static float toFloatL(double in){
    return toFloatL(toByteArrayL(in));
    }

  public static float toFloatL(double[] in){
    return toFloatL(toByteArrayL(in));
    }

  public static float toFloatL(char in){
    return toFloatL(toByteArrayL(in));
    }

  public static float toFloatL(char[] in){
    return toFloatL(toByteArrayL(in));
    }

  public static float toFloatL(String in){
    return toFloatL(toByteArrayL(in));
    }

  public static float toFloatL(Hex in){
    return toFloatL(toByteArrayL(in));
    }


  /////
  // PRIMITIVE --> FLOAT (BIG-ENDIAN)
  /////

  public static float toFloatB(boolean in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(boolean[] in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(byte in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(byte[] in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(short in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(short[] in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(int in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(int[] in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(long in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(long[] in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(float in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(float[] in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(double in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(double[] in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(char in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(char[] in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(String in){
    return toFloatL(changeFormat(in));
    }

  public static float toFloatB(Hex in){
    return toFloatL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> FLOAT ARRAY
  /////

  public static float[] toFloatArray(boolean in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(boolean[] in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(byte in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(byte[] in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(short in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(short[] in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(int in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(int[] in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(long in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(long[] in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(float in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(float[] in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(double in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(double[] in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(char in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(char[] in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(String in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }

  public static float[] toFloatArray(Hex in){
    if (format == BIG_ENDIAN){
      return toFloatArrayB(in);
      }
    return toFloatArrayL(in);
    }


  /////
  // PRIMITIVE --> FLOAT ARRAY (LITTLE-ENDIAN)
  /////

  public static float[] toFloatArrayL(boolean in){
    if (in){
      return new float[]{(float)1};
      }
    return new float[]{(float)0};
    }

  public static float[] toFloatArrayL(boolean[] in){
    return toFloatArrayL(toByteArrayL(in));
    }

  public static float[] toFloatArrayL(byte in){
    return new float[]{toFloatL(in)};
    }

  public static float[] toFloatArrayL(byte[] in){
    int numFloat = in.length/4;

    float[] out;

    int remainder = in.length%4;
    if (remainder != 0){
      // uneven length
      out = new float[numFloat+1];
      }
    else {
      //even length
      out = new float[numFloat];
      }


    for (int i=0,j=0;i<numFloat;i++,j+=4){
      out[i] = toFloatL(new byte[]{in[j],in[j+1],in[j+2],in[j+3]});
      }

    if (remainder == 1){
      out[numFloat] = toFloatL(new byte[]{in[in.length-1],0,0,0});
      }
    else if (remainder == 2){
      out[numFloat] = toFloatL(new byte[]{in[in.length-2],in[in.length-1],0,0});
      }
    else if (remainder == 3){
      out[numFloat] = toFloatL(new byte[]{in[in.length-3],in[in.length-2],in[in.length-1],0});
      }

    return out;
    }

  public static float[] toFloatArrayL(short in){
    return toFloatArrayL(toByteArrayL(in));
    }

  public static float[] toFloatArrayL(short[] in){
    return toFloatArrayL(toByteArrayL(in));
    }

  public static float[] toFloatArrayL(int in){
    return toFloatArrayL(toByteArrayL(in));
    }

  public static float[] toFloatArrayL(int[] in){
    return toFloatArrayL(toByteArrayL(in));
    }

  public static float[] toFloatArrayL(long in){
    return toFloatArrayL(toByteArrayL(in));
    }

  public static float[] toFloatArrayL(long[] in){
    return toFloatArrayL(toByteArrayL(in));
    }

  public static float[] toFloatArrayL(float in){
    return new float[]{in};
    }

  public static float[] toFloatArrayL(float[] in){
    return in;
    }

  public static float[] toFloatArrayL(double in){
    return toFloatArrayL(toByteArrayL(in));
    }

  public static float[] toFloatArrayL(double[] in){
    return toFloatArrayL(toByteArrayL(in));
    }

  public static float[] toFloatArrayL(char in){
    return toFloatArrayL(toByteArrayL(in));
    }

  public static float[] toFloatArrayL(char[] in){
    return toFloatArrayL(toByteArrayL(in));
    }

  public static float[] toFloatArrayL(String in){
    return toFloatArrayL(toByteArrayL(in));
    }

  public static float[] toFloatArrayL(Hex in){
    return toFloatArrayL(toByteArrayL(in));
    }


  /////
  // PRIMITIVE --> FLOAT ARRAY (BIG-ENDIAN)
  /////

  public static float[] toFloatArrayB(boolean in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(boolean[] in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(byte in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(byte[] in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(short in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(short[] in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(int in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(int[] in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(long in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(long[] in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(float in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(float[] in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(double in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(double[] in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(char in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(char[] in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(String in){
    return toFloatArrayL(changeFormat(in));
    }

  public static float[] toFloatArrayB(Hex in){
    return toFloatArrayL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> DOUBLE
  /////

  public static double toDouble(boolean in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(boolean[] in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(byte in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(byte[] in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(short in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(short[] in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(int in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(int[] in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(long in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(long[] in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(float in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(float[] in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(double in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(double[] in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(char in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(char[] in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(String in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }

  public static double toDouble(Hex in){
    if (format == BIG_ENDIAN){
      return toDoubleB(in);
      }
    return toDoubleL(in);
    }


  /////
  // PRIMITIVE --> DOUBLE (LITTLE-ENDIAN)
  /////

  public static double toDoubleL(boolean in){
    if (in){
      return 1;
      }
    return 0;
    }

  public static double toDoubleL(boolean[] in){
    return toDoubleL(toByteArrayL(in));
    }

  public static double toDoubleL(byte in){
    if (in < 0){
      return (double)(256 + in);
      }
    return (double)in;
    }

  public static double toDoubleL(byte[] in){
    java.nio.ByteBuffer bBuffer = java.nio.ByteBuffer.wrap(in);
    bBuffer.order(ByteOrder.LITTLE_ENDIAN);
    DoubleBuffer lBuffer = bBuffer.asDoubleBuffer();
    return lBuffer.get();
    }

  public static double toDoubleL(short in){
    return toDoubleL(toByteArrayL(in));
    }

  public static double toDoubleL(short[] in){
    return toDoubleL(toByteArrayL(in));
    }

  public static double toDoubleL(int in){
    return toDoubleL(toByteArrayL(in));
    }

  public static double toDoubleL(int[] in){
    return toDoubleL(toByteArrayL(in));
    }

  public static double toDoubleL(long in){
    return toDoubleL(toByteArrayL(in));
    }

  public static double toDoubleL(long[] in){
    return toDoubleL(toByteArrayL(in));
    }

  public static double toDoubleL(float in){
    return toDoubleL(toByteArrayL(in));
    }

  public static double toDoubleL(float[] in){
    return toDoubleL(toByteArrayL(in));
    }

  public static double toDoubleL(double in){
    return (double)in;
    }

  public static double toDoubleL(double[] in){
    return toDoubleL(toByteArrayL(in));
    }

  public static double toDoubleL(char in){
    return toDoubleL(toByteArrayL(in));
    }

  public static double toDoubleL(char[] in){
    return toDoubleL(toByteArrayL(in));
    }

  public static double toDoubleL(String in){
    return toDoubleL(toByteArrayL(in));
    }

  public static double toDoubleL(Hex in){
    return toDoubleL(toByteArrayL(in));
    }


  /////
  // PRIMITIVE --> DOUBLE (BIG-ENDIAN)
  /////

  public static double toDoubleB(boolean in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(boolean[] in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(byte in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(byte[] in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(short in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(short[] in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(int in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(int[] in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(long in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(long[] in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(float in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(float[] in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(double in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(double[] in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(char in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(char[] in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(String in){
    return toDoubleL(changeFormat(in));
    }

  public static double toDoubleB(Hex in){
    return toDoubleL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> DOUBLE ARRAY
  /////

  public static double[] toDoubleArray(boolean in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(boolean[] in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(byte in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(byte[] in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(short in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(short[] in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(int in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(int[] in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(long in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(long[] in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(float in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(float[] in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(double in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(double[] in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(char in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(char[] in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(String in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }

  public static double[] toDoubleArray(Hex in){
    if (format == BIG_ENDIAN){
      return toDoubleArrayB(in);
      }
    return toDoubleArrayL(in);
    }


  /////
  // PRIMITIVE --> DOUBLE ARRAY (LITTLE-ENDIAN)
  /////

  public static double[] toDoubleArrayL(boolean in){
    if (in){
      return new double[]{(double)1};
      }
    return new double[]{(double)0};
    }

  public static double[] toDoubleArrayL(boolean[] in){
    return toDoubleArrayL(toByteArrayL(in));
    }

  public static double[] toDoubleArrayL(byte in){
    return new double[]{toDoubleL(in)};
    }

  public static double[] toDoubleArrayL(byte[] in){
    int numDouble = in.length/8;

    double[] out;

    int remainder = in.length%8;
    if (remainder != 0){
      // uneven length
      out = new double[numDouble+1];
      }
    else {
      //even length
      out = new double[numDouble];
      }


    for (int i=0,j=0;i<numDouble;i++,j+=8){
      out[i] = toDoubleL(new byte[]{in[j],in[j+1],in[j+2],in[j+3],in[j+4],in[j+5],in[j+6],in[j+7]});
      }

    if (remainder == 1){
      out[numDouble] = toDoubleL(new byte[]{in[in.length-1],0,0,0,0,0,0,0});
      }
    else if (remainder == 2){
      out[numDouble] = toDoubleL(new byte[]{in[in.length-2],in[in.length-1],0,0,0,0,0,0});
      }
    else if (remainder == 3){
      out[numDouble] = toDoubleL(new byte[]{in[in.length-3],in[in.length-2],in[in.length-1],0,0,0,0,0});
      }
    else if (remainder == 4){
      out[numDouble] = toDoubleL(new byte[]{in[in.length-4],in[in.length-3],in[in.length-2],in[in.length-1],0,0,0,0});
      }
    else if (remainder == 5){
      out[numDouble] = toDoubleL(new byte[]{in[in.length-5],in[in.length-4],in[in.length-3],in[in.length-2],in[in.length-1],0,0,0});
      }
    else if (remainder == 6){
      out[numDouble] = toDoubleL(new byte[]{in[in.length-6],in[in.length-5],in[in.length-4],in[in.length-3],in[in.length-2],in[in.length-1],0,0});
      }
    else if (remainder == 7){
      out[numDouble] = toDoubleL(new byte[]{in[in.length-7],in[in.length-6],in[in.length-5],in[in.length-4],in[in.length-3],in[in.length-2],in[in.length-1],0});
      }

    return out;
    }

  public static double[] toDoubleArrayL(short in){
    return toDoubleArrayL(toByteArrayL(in));
    }

  public static double[] toDoubleArrayL(short[] in){
    return toDoubleArrayL(toByteArrayL(in));
    }

  public static double[] toDoubleArrayL(int in){
    return toDoubleArrayL(toByteArrayL(in));
    }

  public static double[] toDoubleArrayL(int[] in){
    return toDoubleArrayL(toByteArrayL(in));
    }

  public static double[] toDoubleArrayL(long in){
    return toDoubleArrayL(toByteArrayL(in));
    }

  public static double[] toDoubleArrayL(long[] in){
    return toDoubleArrayL(toByteArrayL(in));
    }

  public static double[] toDoubleArrayL(float in){
    return toDoubleArrayL(toByteArrayL(in));
    }

  public static double[] toDoubleArrayL(float[] in){
    return toDoubleArrayL(toByteArrayL(in));
    }

  public static double[] toDoubleArrayL(double in){
    return new double[]{in};
    }

  public static double[] toDoubleArrayL(double[] in){
    return in;
    }

  public static double[] toDoubleArrayL(char in){
    return toDoubleArrayL(toByteArrayL(in));
    }

  public static double[] toDoubleArrayL(char[] in){
    return toDoubleArrayL(toByteArrayL(in));
    }

  public static double[] toDoubleArrayL(String in){
    return toDoubleArrayL(toByteArrayL(in));
    }

  public static double[] toDoubleArrayL(Hex in){
    return toDoubleArrayL(toByteArrayL(in));
    }


  /////
  // PRIMITIVE --> DOUBLE ARRAY (BIG-ENDIAN)
  /////

  public static double[] toDoubleArrayB(boolean in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(boolean[] in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(byte in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(byte[] in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(short in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(short[] in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(int in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(int[] in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(long in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(long[] in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(float in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(float[] in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(double in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(double[] in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(char in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(char[] in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(String in){
    return toDoubleArrayL(changeFormat(in));
    }

  public static double[] toDoubleArrayB(Hex in){
    return toDoubleArrayL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> CHAR
  /////

  public static char toChar(boolean in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(boolean[] in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(byte in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(byte[] in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(short in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(short[] in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(int in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(int[] in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(long in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(long[] in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(float in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(float[] in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(double in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(double[] in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(char in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(char[] in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(String in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }

  public static char toChar(Hex in){
    if (format == BIG_ENDIAN){
      return toCharB(in);
      }
    return toCharL(in);
    }


  /////
  // PRIMITIVE --> CHAR (LITTLE-ENDIAN)
  /////

  public static char toCharL(boolean in){
    if (in){
      return (char)1;
      }
    return (char)0;
    }

  public static char toCharL(boolean[] in){
    return toCharL(toByteArrayL(in));
    }

  public static char toCharL(byte in){
    return (char)in;
    }

  public static char toCharL(byte[] in){
    java.nio.ByteBuffer bBuffer = java.nio.ByteBuffer.wrap(in);
    bBuffer.order(ByteOrder.LITTLE_ENDIAN);
    CharBuffer lBuffer = bBuffer.asCharBuffer();
    return lBuffer.get();
    }

  public static char toCharL(short in){
    return toCharL(toByteArrayL(in));
    }

  public static char toCharL(short[] in){
    return toCharL(toByteArrayL(in));
    }

  public static char toCharL(int in){
    return toCharL(toByteArrayL(in));
    }

  public static char toCharL(int[] in){
    return toCharL(toByteArrayL(in));
    }

  public static char toCharL(long in){
    return toCharL(toByteArrayL(in));
    }

  public static char toCharL(long[] in){
    return toCharL(toByteArrayL(in));
    }

  public static char toCharL(float in){
    return toCharL(toByteArrayL(in));
    }

  public static char toCharL(float[] in){
    return toCharL(toByteArrayL(in));
    }

  public static char toCharL(double in){
    return toCharL(toByteArrayL(in));
    }

  public static char toCharL(double[] in){
    return toCharL(toByteArrayL(in));
    }

  public static char toCharL(char in){
    return in;
    }

  public static char toCharL(char[] in){
    return in[0];
    }

  public static char toCharL(String in){
    return toCharL(toByteL(in));
    }

  public static char toCharL(Hex in){
    return toCharL(toByteL(in));
    }


  /////
  // PRIMITIVE --> CHAR (BIG-ENDIAN)
  /////

  public static char toCharB(boolean in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(boolean[] in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(byte in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(byte[] in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(short in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(short[] in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(int in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(int[] in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(long in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(long[] in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(float in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(float[] in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(double in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(double[] in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(char in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(char[] in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(String in){
    return toCharL(changeFormat(in));
    }

  public static char toCharB(Hex in){
    return toCharL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> CHAR ARRAY
  /////

  public static char[] toCharArray(boolean in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(boolean[] in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(byte in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(byte[] in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(short in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(short[] in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(int in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(int[] in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(long in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(long[] in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(float in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(float[] in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(double in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(double[] in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(char in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(char[] in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(String in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }

  public static char[] toCharArray(Hex in){
    if (format == BIG_ENDIAN){
      return toCharArrayB(in);
      }
    return toCharArrayL(in);
    }


  /////
  // PRIMITIVE --> CHAR ARRAY (LITTLE-ENDIAN)
  /////

  public static char[] toCharArrayL(boolean in){
    if (in){
      return new char[]{(char)1};
      }
    return new char[]{(char)0};
    }

  public static char[] toCharArrayL(boolean[] in){
    return toCharArrayL(toByteArrayL(in));
    }

  public static char[] toCharArrayL(byte in){
    return new char[]{(char)in};
    }

  public static char[] toCharArrayL(byte[] in){
    int numChar = in.length/2;

    if (in.length%2 == 1){
      // uneven length
      char[] out = new char[numChar+1];

      for (int i=0,j=0;i<numChar;i++,j+=2){
        out[i] = toCharL(new byte[]{in[j],in[j+1]});
        }

      out[numChar] = toCharL(new byte[]{in[in.length-1],0});

      return out;
      }
    else {
      //even length
      char[] out = new char[numChar];

      for (int i=0,j=0;i<numChar;i++,j+=2){
        out[i] = toCharL(new byte[]{in[j],in[j+1]});
        }

      return out;
      }

    }

  public static char[] toCharArrayL(short in){
    return toCharArrayL(toByteArrayL(in));
    }

  public static char[] toCharArrayL(short[] in){
    return toCharArrayL(toByteArrayL(in));
    }

  public static char[] toCharArrayL(int in){
    return toCharArrayL(toByteArrayL(in));
    }

  public static char[] toCharArrayL(int[] in){
    return toCharArrayL(toByteArrayL(in));
    }

  public static char[] toCharArrayL(long in){
    return toCharArrayL(toByteArrayL(in));
    }

  public static char[] toCharArrayL(long[] in){
    return toCharArrayL(toByteArrayL(in));
    }

  public static char[] toCharArrayL(float in){
    return toCharArrayL(toByteArrayL(in));
    }

  public static char[] toCharArrayL(float[] in){
    return toCharArrayL(toByteArrayL(in));
    }

  public static char[] toCharArrayL(double in){
    return toCharArrayL(toByteArrayL(in));
    }

  public static char[] toCharArrayL(double[] in){
    return toCharArrayL(toByteArrayL(in));
    }

  public static char[] toCharArrayL(char in){
    return new char[]{in};
    }

  public static char[] toCharArrayL(char[] in){
    return in;
    }

  public static char[] toCharArrayL(String in){
    //return toCharArrayL(toByteArrayL(in));
    char[] chars = new char[in.length()];
    in.getChars(0,chars.length,chars,0);
    return chars;
    }

  public static char[] toCharArrayL(Hex in){
    return toCharArrayL(toByteArrayL(in));
    }


  /////
  // PRIMITIVE --> CHAR ARRAY (BIG-ENDIAN)
  /////

  public static char[] toCharArrayB(boolean in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(boolean[] in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(byte in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(byte[] in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(short in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(short[] in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(int in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(int[] in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(long in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(long[] in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(float in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(float[] in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(double in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(double[] in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(char in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(char[] in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(String in){
    return toCharArrayL(changeFormat(in));
    }

  public static char[] toCharArrayB(Hex in){
    return toCharArrayL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> STRING
  /////

  public static String toString(boolean in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(boolean[] in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(byte in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(byte[] in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(short in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(short[] in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(int in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(int[] in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(long in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(long[] in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(float in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(float[] in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(double in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(double[] in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(char in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(char[] in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(String in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }

  public static String toString(Hex in){
    if (format == BIG_ENDIAN){
      return toStringB(in);
      }
    return toStringL(in);
    }


  /////
  // PRIMITIVE --> STRING (LITTLE-ENDIAN)
  /////

  public static String toStringL(boolean in){
    if (in){
      return toStringL(new byte[]{1});
      }
    return toStringL(new byte[]{0});
    }

  public static String toStringL(boolean[] in){
    return toStringL(toByteArrayL(in));
    }

  public static String toStringL(byte in){
    return toStringL(new byte[]{in});
    }

  public static String toStringL(byte[] in){
    return new String(in);
    }

  public static String toStringL(short in){
    return toStringL(toByteArrayL(in));
    }

  public static String toStringL(short[] in){
    return toStringL(toByteArrayL(in));
    }

  public static String toStringL(int in){
    return toStringL(toByteArrayL(in));
    }

  public static String toStringL(int[] in){
    return toStringL(toByteArrayL(in));
    }

  public static String toStringL(long in){
    return toStringL(toByteArrayL(in));
    }

  public static String toStringL(long[] in){
    return toStringL(toByteArrayL(in));
    }

  public static String toStringL(float in){
    return toStringL(toByteArrayL(in));
    }

  public static String toStringL(float[] in){
    return toStringL(toByteArrayL(in));
    }

  public static String toStringL(double in){
    return toStringL(toByteArrayL(in));
    }

  public static String toStringL(double[] in){
    return toStringL(toByteArrayL(in));
    }

  public static String toStringL(char in){
    return toStringL(toByteArrayL(in));
    }

  public static String toStringL(char[] in){
    return toStringL(toByteArrayL(in));
    }

  public static String toStringL(String in){
    return in;
    }

  public static String toStringL(Hex in){
    return toStringL(toByteArrayL(in));
    }


  /////
  // PRIMITIVE --> STRING (BIG-ENDIAN)
  /////

  public static String toStringB(boolean in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(boolean[] in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(byte in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(byte[] in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(short in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(short[] in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(int in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(int[] in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(long in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(long[] in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(float in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(float[] in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(double in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(double[] in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(char in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(char[] in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(String in){
    return toStringL(changeFormat(in));
    }

  public static String toStringB(Hex in){
    return toStringL(changeFormat(in));
    }


  /////
  // PRIMITIVE --> HEX
  /////

  public static Hex toHex(boolean in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(boolean[] in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(byte in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(byte[] in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(short in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(short[] in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(int in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(int[] in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(long in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(long[] in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(float in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(float[] in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(double in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(double[] in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(char in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(char[] in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(String in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }

  public static Hex toHex(Hex in){
    if (format == BIG_ENDIAN){
      return toHexB(in);
      }
    return toHexL(in);
    }


  /////
  // PRIMITIVE --> HEX (LITTLE-ENDIAN)
  /////

  public static Hex toHexL(boolean in){
    if (in){
      return new Hex("01");
      }
    return new Hex("00");
    }

  public static Hex toHexL(boolean[] in){
    return toHexL(toByteArrayL(in));
    }

  public static Hex toHexL(byte in){
    int b = in;
    if (in < 0){
      b = 256 + in;
      }
    int upperVal = b/16;
    int lowerVal = b%16;
    return new Hex(hexTable[upperVal] + "" + hexTable[lowerVal]);
    }

  public static Hex toHexL(byte[] in){
    String hex = "";
    for (int i=0;i<in.length;i++){
      hex += toHexL(in[i]).toString();
      }
    return new Hex(hex);
    }

  public static Hex toHexL(short in){
    return toHexL(toByteArrayL(in));
    }

  public static Hex toHexL(short[] in){
    return toHexL(toByteArrayL(in));
    }

  public static Hex toHexL(int in){
    return toHexL(toByteArrayL(in));
    }

  public static Hex toHexL(int[] in){
    return toHexL(toByteArrayL(in));
    }

  public static Hex toHexL(long in){
    return toHexL(toByteArrayL(in));
    }

  public static Hex toHexL(long[] in){
    return toHexL(toByteArrayL(in));
    }

  public static Hex toHexL(float in){
    return toHexL(toByteArrayL(in));
    }

  public static Hex toHexL(float[] in){
    return toHexL(toByteArrayL(in));
    }

  public static Hex toHexL(double in){
    return toHexL(toByteArrayL(in));
    }

  public static Hex toHexL(double[] in){
    return toHexL(toByteArrayL(in));
    }

  public static Hex toHexL(char in){
    return toHexL(toByteArrayL(in));
    }

  public static Hex toHexL(char[] in){
    return toHexL(toByteArrayL(in));
    }

  public static Hex toHexL(String in){
    return toHexL(toByteArrayL(in));
    }

  public static Hex toHexL(Hex in){
    return in;
    }


  /////
  // PRIMITIVE --> HEX (BIG-ENDIAN)
  /////

  public static Hex toHexB(boolean in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(boolean[] in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(byte in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(byte[] in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(short in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(short[] in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(int in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(int[] in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(long in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(long[] in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(float in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(float[] in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(double in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(double[] in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(char in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(char[] in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(String in){
    return toHexL(changeFormat(in));
    }

  public static Hex toHexB(Hex in){
    return toHexL(changeFormat(in));
    }


  //////////
  //
  // FORMATTING
  //
  //////////

  /////
  // CHANGE FORMAT (LITTLE-ENDIAN <-> BIG-ENDIAN)
  /////

  public static boolean changeFormat(boolean in){
    return in;
    }

  public static boolean[] changeFormat(boolean[] in){
    int size = in.length;

    boolean[] out = new boolean[size];
    for (int i=0,j=size-1;i<size;i++,j--){
      out[i] = in[j];
      }
    return out;
    }

  public static byte changeFormat(byte in){
    boolean[] bits = toBooleanArrayL(in);
    bits = changeFormat(bits);
    return toByteL(bits);
    }

  public static byte[] changeFormat(byte[] in){
    int size = in.length;

    byte[] out = new byte[size];
    for (int i=0,j=size-1;i<size;i++,j--){
      out[i] = in[j];
      }
    return out;
    }

  public static short changeFormat(short in){
    byte[] bytes = toByteArrayL(in);
    bytes = changeFormat(bytes);
    return toShortL(bytes);
    }

  public static short[] changeFormat(short[] in){
    int size = in.length;

    short[] out = new short[size];
    for (int i=0,j=size-1;i<size;i++,j--){
      out[i] = in[j];
      }
    return out;
    }

  public static int changeFormat(int in){
    byte[] bytes = toByteArrayL(in);
    bytes = changeFormat(bytes);
    return toIntL(bytes);
    }

  public static int[] changeFormat(int[] in){
    int size = in.length;

    int[] out = new int[size];
    for (int i=0,j=size-1;i<size;i++,j--){
      out[i] = in[j];
      }
    return out;
    }

  public static long changeFormat(long in){
    byte[] bytes = toByteArrayL(in);
    bytes = changeFormat(bytes);
    return toLongL(bytes);
    }

  public static long[] changeFormat(long[] in){
    int size = in.length;

    long[] out = new long[size];
    for (int i=0,j=size-1;i<size;i++,j--){
      out[i] = in[j];
      }
    return out;
    }

  public static float changeFormat(float in){
    byte[] bytes = toByteArrayL(in);
    bytes = changeFormat(bytes);
    return toFloatL(bytes);
    }

  public static float[] changeFormat(float[] in){
    int size = in.length;

    float[] out = new float[size];
    for (int i=0,j=size-1;i<size;i++,j--){
      out[i] = in[j];
      }
    return out;
    }

  public static double changeFormat(double in){
    byte[] bytes = toByteArrayL(in);
    bytes = changeFormat(bytes);
    return toDoubleL(bytes);
    }

  public static double[] changeFormat(double[] in){
    int size = in.length;

    double[] out = new double[size];
    for (int i=0,j=size-1;i<size;i++,j--){
      out[i] = in[j];
      }
    return out;
    }

  public static char changeFormat(char in){
    byte[] bytes = toByteArrayL(in);
    bytes = changeFormat(bytes);
    return toCharL(bytes);
    }

  public static char[] changeFormat(char[] in){
    int size = in.length;

    char[] out = new char[size];
    for (int i=0,j=size-1;i<size;i++,j--){
      out[i] = in[j];
      }
    return out;
    }

  public static String changeFormat(String in){
    char[] chars = in.toCharArray();
    chars = changeFormat(chars);
    return new String(chars);
    }

  public static Hex changeFormat(Hex in){
    char[] chars = in.toCharArray();
    int size = chars.length;

    char[] out = new char[size];

    for (int i=0,j=size-2;i<size;i+=2,j-=2){
      out[i] = chars[j];
      out[i+1] = chars[j+1];
      }

    return new Hex(new String(out));
    }


  /////
  // UNSIGN
  /////

  public static boolean unsign(boolean in){
    return in;
    }

  public static boolean[] unsign(boolean[] in){
    return in;
    }

  public static short unsign(byte in){
    if (in < 0){
      return (short)(256 + (short)in);
      }
    return (short)in;
    }

  public static short[] unsign(byte[] in){
    short[] out = new short[in.length];
    for (int i=0;i<in.length;i++){
      out[i] = unsign(in[i]);
      }
    return out;
    }

  public static int unsign(short in){
    if (in < 0){
      return (int)(65536 + (int)in);
      }
    return (int)in;
    }

  public static int[] unsign(short[] in){
    int[] out = new int[in.length];
    for (int i=0;i<in.length;i++){
      out[i] = unsign(in[i]);
      }
    return out;
    }

  public static long unsign(int in){
    if (in < 0){
      return (long)(4294967296L + (long)in);
      }
    return (long)in;
    }

  public static long[] unsign(int[] in){
    long[] out = new long[in.length];
    for (int i=0;i<in.length;i++){
      out[i] = unsign(in[i]);
      }
    return out;
    }

  public static long unsign(long in){
    return in;
    }

  public static long[] unsign(long[] in){
    return in;
    }

  public static double unsign(float in){
    if (in < 0){
      return (double)(2147483648.0D + (double)in);
      }
    return (double)in;
    }

  public static double[] unsign(float[] in){
    double[] out = new double[in.length];
    for (int i=0;i<in.length;i++){
      out[i] = unsign(in[i]);
      }
    return out;
    }

  public static double unsign(double in){
    return in;
    }

  public static double[] unsign(double[] in){
    return in;
    }

  public static char unsign(char in){
    return in;
    }

  public static char[] unsign(char[] in){
    return in;
    }

  public static String unsign(String in){
    return in;
    }

  public static Hex unsign(Hex in){
    return in;
    }


  //////////
  //
  // UNIQUE
  //
  //////////

  /////
  // UNICODE <-> ASCII
  /////

  public static String toUnicode(String in){
    try {
      byte[] bytes = in.getBytes();
      return new String(bytes,"UTF-16LE");
      }
    catch (Throwable t){
      return in;
      }
    }

  public static String toUnicode(byte[] in){
    try {
      return new String(in,"UTF-16LE");
      }
    catch (Throwable t){
      return new String(in);
      }
    }

  public static String toAscii(String in){
    char[] chars = in.toCharArray();
    byte[] bytes = new byte[in.length()];

    for (int i=0;i<chars.length;i++){
      bytes[i] = (byte)chars[i];
      }

    return new String(bytes);
    }


  }