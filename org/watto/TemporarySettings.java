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

import java.io.*;
import java.util.*;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class TemporarySettings {

  static Hashtable settings = new Hashtable();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public TemporarySettings() {
    }


/**
**********************************************************************************************
Clears all the values
**********************************************************************************************
**/
  public static void revertToDefaults() {
    try {
      settings = new Hashtable();
      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static String get(String setting) {
    return getString(setting);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static boolean getBoolean(String setting) {
    try {
      String result = (String)settings.get(setting);
      if (result == null || result.equals("true")){
        return true;
        }
      else {
        return false;
        }
      }
    catch (Throwable t){
      return true;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static double getDouble(String setting) {
    try {
      return Double.parseDouble((String)settings.get(setting));
      }
    catch (Throwable t){
      return -1;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static int getInt(String setting) {
    try {
      return Integer.parseInt((String)settings.get(setting));
      }
    catch (Throwable t){
      return -1;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static long getLong(String setting) {
    try {
      return Long.parseLong((String)settings.get(setting));
      }
    catch (Throwable t){
      return -1;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static String getString(String setting) {
    try {
      return (String) settings.get(setting);
      }
    catch (Throwable t){
      return "";
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void set(String key, String value) {
    try {
      settings.put(key,value);
      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void set(String key, int value) {
    set(key,""+value);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void set(String key, double value) {
    set(key,""+value);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void set(String key, boolean value) {
    set(key,""+value);
    }



/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void logError(Throwable t){
    ErrorLogger.log(t);
    }


  }

