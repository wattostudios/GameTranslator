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

public class Resource_ArmageddonEmpires_BasicTileData extends Resource {

  String description = "";
  String tilePic = "";
  String baseID = "";
  String movementCost = "";


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_ArmageddonEmpires_BasicTileData(){
    super();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_ArmageddonEmpires_BasicTileData(String original, String description, String tilePic, String baseID, String movementCost){
    super(original);
    this.description = description;
    this.tilePic = tilePic;
    this.baseID = baseID;
    this.movementCost = movementCost;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getBaseID(){
    return baseID;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getDescription(){
    return description;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getMovementCost(){
    return movementCost;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getTilePic(){
    return tilePic;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setBaseID(String baseID){
    this.baseID = baseID;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setDescription(String description){
    this.description = description;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setMovementCost(String movementCost){
    this.movementCost = movementCost;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setTilePic(String tilePic){
    this.tilePic = tilePic;
    }


  }