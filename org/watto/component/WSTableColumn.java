////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                            WATTO STUDIOS JAVA COMPONENT TOOLKIT                            //
//                    A Collection Of Components To Build Java Applications                   //
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

package org.watto.component;

import org.watto.*;
import org.watto.event.*;
import org.watto.xml.*;

import java.io.*;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class WSTableColumn implements Comparable {

  char charCode; // for quicker sorting
  String code = "";
  Class type = String.class;
  boolean editable = false;
  boolean sortable = true;
  int minWidth = 0;
  int maxWidth = -1;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSTableColumn(){
    super();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSTableColumn(String code, char charCode) {
    this(code,charCode,String.class,false,false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSTableColumn(String code, char charCode, Class type) {
    this(code,charCode,type,false,false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSTableColumn(String code, char charCode, Class type, boolean editable, boolean sortable) {
    this(code,charCode,type,editable,sortable,0,-1);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSTableColumn(String code, char charCode, Class type, boolean editable, boolean sortable, int minWidth, int maxWidth) {
    this.code = code;
    this.charCode = charCode;
    this.type = type;
    this.editable = editable;
    this.sortable = sortable;
    this.minWidth = minWidth;
    this.maxWidth = maxWidth;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int compareTo(Object otherResource){
    return getName().compareTo(((WSTableColumn)otherResource).getName());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public char getCharCode() {
    return charCode;
    }


/**
**********************************************************************************************
Gets the class name of the component
@param component the component
@return the class name
**********************************************************************************************
**/
  public String getClassName(){
    String name = getClass().getName();
    String pack = getClass().getPackage().getName();
    name = name.substring(pack.length()+1);
    return name;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getCode() {
    return code;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Class getType() {
    return type;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getMaxWidth() {
    return maxWidth;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getMinWidth() {
    return minWidth;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getName() {
    if (code == null){
      return "";
      }
    String langCode = getClassName() + "_" + code + "_Text";
    if (Language.has(langCode)){
      return Language.get(langCode);
      }
    return code;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getWidth() {
    String colCode = getClassName() + "_" + charCode + "_Width";
    int width = Settings.getInt(colCode,false);

    if (width < 0){
      width = 100;
      }

    if (width > maxWidth && maxWidth > 0){
      width = maxWidth;
      }
    if (width < minWidth){
      width = minWidth;
      }

    return width;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isEditable() {
    return editable;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isSortable() {
    return sortable;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setEditable(boolean editable) {
    this.editable = editable;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setMaxWidth(int maxWidth) {
    this.maxWidth = maxWidth;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setMinWidth(int minWidth) {
    this.minWidth = minWidth;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSortable(boolean sortable) {
    this.sortable = sortable;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setType(Class type) {
    this.type = type;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setWidth(int width) {
    if (width == 0){
      return;
      }

    if (width > maxWidth && maxWidth > -1){
      width = maxWidth;
      }
    if (width < minWidth){
      width = minWidth;
      }

    String colCode = getClassName() + "_" + charCode + "_Width";
    Settings.set(colCode,width);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String toString() {
    return getName();
    }


  }