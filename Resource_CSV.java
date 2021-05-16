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

public class Resource_CSV extends Resource {

  String[] columns = new String[0];


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_CSV(){
    super();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_CSV(String[] columns){
    super(columns[0]);
    this.columns = columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getColumn(int column){
    return columns[column];
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String[] getColumns(){
    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getNumColumns(){
    return columns.length;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColumn(int column, String value){
    columns[column] = value;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColumns(String[] columns){
    this.columns = columns;
    }


  }