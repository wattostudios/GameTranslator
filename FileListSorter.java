////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                      GAME TRANSLATOR                                       //
//                            Game Language Translation Assistant                             //
//                                 http://www.watto.org/trans                                 //
//                                                                                            //
//                           Copyright (C) 2006-2009  WATTO Studios                           //
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

import org.watto.component.WSTableColumn;

/**
**********************************************************************************************
THIS IS ENHANCED SUCH THAT, IF THERE ARE 2 EQUAL VALUES IN A COLUMN, IT WILL SORT BY THE FILEPATH
**********************************************************************************************
**/

public class FileListSorter {

  static char sortColumnCode = ' ';
  static ArchivePlugin readPlugin = null;
  static boolean ascending = true;

  static char filePathCode = 'O';


/**
**********************************************************************************************

**********************************************************************************************
**/
  public FileListSorter() {
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static char getSortColumnCode() {
    return sortColumnCode;
    }



/**
**********************************************************************************************

**********************************************************************************************
**/
  public static Resource[] sort(Resource[] resources, WSTableColumn column, boolean toggleAscending) {

    readPlugin = Archive.getReadPlugin();

    // determine whether to sort ascending or descending
    if (toggleAscending && sortColumnCode == column.getCharCode()){
      ascending = ! ascending;
      }
    else {
      sortColumnCode = column.getCharCode();
      ascending = true;
      }


    Class sortType = column.getType();

    if (sortType == String.class){
      sortStrings((Resource[])resources.clone(),resources,0,resources.length);
      }
    else if (sortType == Integer.class){
      sortIntegers((Resource[])resources.clone(),resources,0,resources.length);
      }
    else if (sortType == Long.class){
      sortLongs((Resource[])resources.clone(),resources,0,resources.length);
      }
    else if (sortType == Boolean.class){
      sortBooleans((Resource[])resources.clone(),resources,0,resources.length);
      }

    return resources;

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static Resource[] sort(Resource[] resources, WSTableColumn column) {
    return sort(resources,column,true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static Resource[] sort(WSTableColumn column, boolean toggleAscending) {

    Resource[] resources = Archive.getResources();
    return sort(resources,column,toggleAscending);

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static Resource[] sort(WSTableColumn column) {

    return sort(column,true);

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void sortStrings(Resource[] from, Resource[] to, int low, int high) {
    if (high - low < 2) {
      return;
      }
    int middle = (low + high)/2;

    sortStrings(to, from, low, middle);
    sortStrings(to, from, middle, high);

    int p = low;
    int q = middle;

    if (high-low >= 4 && compareStrings(from[middle-1], from[middle]) <= 0) {
      for (int i=low;i<high;i++) {
        to[i] = from[i];
        }
      return;
      }

    for (int i=low;i<high;i++) {
      if (q >= high || (p < middle && compareStrings(from[p], from[q]) <= 0)) {
        to[i] = from[p++];
        }
      else {
        to[i] = from[q++];
        }
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void sortIntegers(Resource[] from, Resource[] to, int low, int high) {
    if (high - low < 2) {
      return;
      }
    int middle = (low + high)/2;

    sortIntegers(to, from, low, middle);
    sortIntegers(to, from, middle, high);

    int p = low;
    int q = middle;

    if (high-low >= 4 && compareIntegers(from[middle-1], from[middle]) <= 0) {
      for (int i=low;i<high;i++) {
        to[i] = from[i];
        }
      return;
      }

    for (int i=low;i<high;i++) {
      if (q >= high || (p < middle && compareIntegers(from[p], from[q]) <= 0)) {
        to[i] = from[p++];
        }
      else {
        to[i] = from[q++];
        }
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void sortLongs(Resource[] from, Resource[] to, int low, int high) {
    if (high - low < 2) {
      return;
      }
    int middle = (low + high)/2;

    sortLongs(to, from, low, middle);
    sortLongs(to, from, middle, high);

    int p = low;
    int q = middle;

    if (high-low >= 4 && compareLongs(from[middle-1], from[middle]) <= 0) {
      for (int i=low;i<high;i++) {
        to[i] = from[i];
        }
      return;
      }

    for (int i=low;i<high;i++) {
      if (q >= high || (p < middle && compareLongs(from[p], from[q]) <= 0)) {
        to[i] = from[p++];
        }
      else {
        to[i] = from[q++];
        }
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void sortBooleans(Resource[] from, Resource[] to, int low, int high) {
    if (high - low < 2) {
      return;
      }
    int middle = (low + high)/2;

    sortBooleans(to, from, low, middle);
    sortBooleans(to, from, middle, high);

    int p = low;
    int q = middle;

    if (high-low >= 4 && compareBooleans(from[middle-1], from[middle]) <= 0) {
      for (int i=low;i<high;i++) {
        to[i] = from[i];
        }
      return;
      }

    for (int i=low;i<high;i++) {
      if (q >= high || (p < middle && compareBooleans(from[p], from[q]) <= 0)) {
        to[i] = from[p++];
        }
      else {
        to[i] = from[q++];
        }
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static int compareStrings(Resource row1, Resource row2) {
    String s1 = ((String)readPlugin.getColumnValue(row1,sortColumnCode)).toLowerCase();
    String s2 = ((String)readPlugin.getColumnValue(row2,sortColumnCode)).toLowerCase();

    int result = s1.compareTo(s2);

    if (result < 0) {
      if (ascending){
        return -1;
        }
      return 1;
      }
    else if (result == 0) {
      return compareFilePaths(row1,row2);
      }
    else {
      if (ascending){
        return 1;
        }
      return -1;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static int compareIntegers(Resource row1, Resource row2) {
    int i1 = ((Integer)readPlugin.getColumnValue(row1,sortColumnCode)).intValue();
    int i2 = ((Integer)readPlugin.getColumnValue(row2,sortColumnCode)).intValue();

    if (i1 < i2) {
      if (ascending){
        return -1;
        }
      return 1;
      }
    else if (i1 == i2) {
      return compareFilePaths(row1,row2);
      }
    else {
      if (ascending){
        return 1;
        }
      return -1;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static int compareLongs(Resource row1, Resource row2) {
    long i1 = ((Long)readPlugin.getColumnValue(row1,sortColumnCode)).longValue();
    long i2 = ((Long)readPlugin.getColumnValue(row2,sortColumnCode)).longValue();

    if (i1 < i2) {
      if (ascending){
        return -1;
        }
      return 1;
      }
    else if (i1 == i2) {
      return compareFilePaths(row1,row2);
      }
    else {
      if (ascending){
        return 1;
        }
      return -1;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static int compareBooleans(Resource row1, Resource row2) {
    boolean b1 = ((Boolean)readPlugin.getColumnValue(row1,sortColumnCode)).booleanValue();
    boolean b2 = ((Boolean)readPlugin.getColumnValue(row2,sortColumnCode)).booleanValue();

    if ((b2 != b1) && b2) {
      if (ascending){
        return -1;
        }
      return 1;
      }
    else if (b1 == b2) {
      return compareFilePaths(row1,row2);
      }
    else {
      if (ascending){
        return 1;
        }
      return -1;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static int compareFilePaths(Resource row1, Resource row2) {
    String s1 = ((String)readPlugin.getColumnValue(row1,filePathCode)).toLowerCase();
    String s2 = ((String)readPlugin.getColumnValue(row2,filePathCode)).toLowerCase();

    int result = s1.compareTo(s2);

    if (result < 0) {
      if (ascending){
        return -1;
        }
      return 1;
      }
    else {
      if (ascending){
        return 1;
        }
      return -1;
      }
    }


  }
