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

import javax.swing.Icon;

public class Resource implements Comparable {

  String original = "";
  String translated = "";

  // only used for the icons - not anywhere else
  boolean renamed = false;


/**
**********************************************************************************************

**********************************************************************************************
**/
  Resource(){
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource(String original){
    this.original = original;
    this.translated = original;
    }




/////
//
// METHODS
//
/////


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object clone(){
    Resource newRes = new Resource(original);

    newRes.setTranslated(translated);
    newRes.setRenamed(renamed);

    return newRes;
    }


/**
**********************************************************************************************
Copies all the values from <i>resource</i> into this resource (ie does a replace without
affecting pointers)
**********************************************************************************************
**/
  public void copyFrom(Resource resource){
    this.original = resource.getOriginal();
    this.translated = resource.getTranslated();
    this.renamed = resource.isRenamed();
    }


/**
**********************************************************************************************
Not Used???
**********************************************************************************************
**/
  public int compareTo(Object otherResource){
    return translated.compareTo(((Resource)otherResource).getTranslated());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Icon getIcon(){
    return Archive.getIcon(translated);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Icon getAddedIcon(){
    return Archive.getAddedIcon(isAdded());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Icon getRenamedIcon(){
    return Archive.getRenamedIcon(isRenamed());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getName(){
    return translated;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getNameLength(){
    return translated.length();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isAdded(){
    if (renamed){
      return false;
      }

    if (original.equals("")){
      return true;
      }
    return false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean isRenamed(){
    return (! (original.equals(translated)));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void rename(String name){
    this.translated = name;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setName(String name){
    this.translated = name;
    }


/**
**********************************************************************************************
Only sets the value of the boolean renamed, which is used for the icons.
**********************************************************************************************
**/
  public void setRenamed(boolean renamed){
    this.renamed = renamed;
    }





/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getOriginal(){
    return original;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getTranslated(){
    return translated;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getTranslatedLength(){
    return translated.length();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setOriginal(String original){
    this.original = original;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setTranslated(String translated){
    this.translated = translated;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String toString(){
    return getTranslated();
    }


  }