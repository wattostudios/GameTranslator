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

package org.watto;

import org.watto.component.*;

/**
**********************************************************************************************
A static class that performs common operations on standard array types. Operations include
resizing and shuffling arrays. The methods are all static so you can use them without constructing
the class, and make it much quicker and easier to perform these operations. Very each to add
support for other object types, simply by copying the appropriate method and doing a replace-all
on the old object name with the new one.
**********************************************************************************************
**/
public class ArrayTools {


/**
**********************************************************************************************
Constructor if you want to use it, but is isn't necessary.
**********************************************************************************************
**/
  public ArrayTools() {
    }



//////////
//
// RESIZE
//
//////////



/**
**********************************************************************************************
Resizes an array of type <i>char</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static char[] resize(char[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    char[] dest = new char[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>byte</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static byte[] resize(byte[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    byte[] dest = new byte[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>short</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static short[] resize(short[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    short[] dest = new short[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>int</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static int[] resize(int[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    int[] dest = new int[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>long</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static long[] resize(long[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    long[] dest = new long[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>float</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static float[] resize(float[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    float[] dest = new float[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>double</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static double[] resize(double[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    double[] dest = new double[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>boolean</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static boolean[] resize(boolean[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    boolean[] dest = new boolean[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>String</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static String[] resize(String[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    String[] dest = new String[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>Object</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static Object[] resize(Object[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    Object[] dest = new Object[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>Character</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static Character[] resize(Character[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    Character[] dest = new Character[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>Byte</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static Byte[] resize(Byte[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    Byte[] dest = new Byte[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>Boolean</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static Boolean[] resize(Boolean[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    Boolean[] dest = new Boolean[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>Short</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static Short[] resize(Short[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    Short[] dest = new Short[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>Integer</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static Integer[] resize(Integer[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    Integer[] dest = new Integer[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>Long</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static Long[] resize(Long[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    Long[] dest = new Long[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>Float</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static Float[] resize(Float[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    Float[] dest = new Float[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>Double</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static Double[] resize(Double[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    Double[] dest = new Double[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }


/**
**********************************************************************************************
Resizes an array of type <i>WSPluginBase</i>
@param source the source array
@param newSize the size of the output array
@return the resized output array
**********************************************************************************************
**/
  public static WSPlugin[] resize(WSPlugin[] source, int newSize){
    int copySize = source.length;
    if (newSize < copySize){
      copySize = newSize;
      }

    WSPlugin[] dest = new WSPlugin[newSize];
    System.arraycopy(source,0,dest,0,copySize);

    return dest;
    }



//////////
//
// SHUFFLE
//
//////////



/**
**********************************************************************************************
Shuffles an array of type <i>char</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static char[] shuffle(char[] source){
    int numItems = source.length;
    char[] shuffled = new char[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>byte</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static byte[] shuffle(byte[] source){
    int numItems = source.length;
    byte[] shuffled = new byte[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>short</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static short[] shuffle(short[] source){
    int numItems = source.length;
    short[] shuffled = new short[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>int</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static int[] shuffle(int[] source){
    int numItems = source.length;
    int[] shuffled = new int[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>long</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static long[] shuffle(long[] source){
    int numItems = source.length;
    long[] shuffled = new long[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>float</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static float[] shuffle(float[] source){
    int numItems = source.length;
    float[] shuffled = new float[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>double</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static double[] shuffle(double[] source){
    int numItems = source.length;
    double[] shuffled = new double[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>boolean</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static boolean[] shuffle(boolean[] source){
    int numItems = source.length;
    boolean[] shuffled = new boolean[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>String</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static String[] shuffle(String[] source){
    int numItems = source.length;
    String[] shuffled = new String[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>Object</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static Object[] shuffle(Object[] source){
    int numItems = source.length;
    Object[] shuffled = new Object[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>Character</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static Character[] shuffle(Character[] source){
    int numItems = source.length;
    Character[] shuffled = new Character[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>Byte</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static Byte[] shuffle(Byte[] source){
    int numItems = source.length;
    Byte[] shuffled = new Byte[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>Boolean</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static Boolean[] shuffle(Boolean[] source){
    int numItems = source.length;
    Boolean[] shuffled = new Boolean[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>Short</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static Short[] shuffle(Short[] source){
    int numItems = source.length;
    Short[] shuffled = new Short[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>Integer</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static Integer[] shuffle(Integer[] source){
    int numItems = source.length;
    Integer[] shuffled = new Integer[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>Long</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static Long[] shuffle(Long[] source){
    int numItems = source.length;
    Long[] shuffled = new Long[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>Float</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static Float[] shuffle(Float[] source){
    int numItems = source.length;
    Float[] shuffled = new Float[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


/**
**********************************************************************************************
Shuffles an array of type <i>Double</i>
@param source the source array
@return the shuffled output array
**********************************************************************************************
**/
  public static Double[] shuffle(Double[] source){
    int numItems = source.length;
    Double[] shuffled = new Double[numItems];

    int numToShuffle = numItems;
    for (int i=0;i<numItems;i++){
      int random = (int)(Math.random() * numToShuffle);
      shuffled[i] = source[random];
      numToShuffle--;
      source[random] = source[numToShuffle]; // move after -- so that we get the last item
      }

    return shuffled;
    }


  }

