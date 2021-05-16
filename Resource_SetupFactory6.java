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

public class Resource_SetupFactory6 extends Resource {

  String code = "";
  String group = "";


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_SetupFactory6(){
    super();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_SetupFactory6(String original, String code, String group){
    super(original);
    this.code = code;
    this.group = group;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int compareTo(Object otherResource){
    if (otherResource instanceof Resource_SetupFactory6){
      return group.compareTo(((Resource_SetupFactory6)otherResource).getGroup());
      }
    return 0;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getCode(){
    return code;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getGroup(){
    return group;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setCode(String code){
    this.code = code;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setGroup(String group){
    this.group = group;
    }


  }