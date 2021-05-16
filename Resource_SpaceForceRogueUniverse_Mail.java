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

public class Resource_SpaceForceRogueUniverse_Mail extends Resource {

  int id = 0;
  int type = 0;
  String from = "";
  String body = "";


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_SpaceForceRogueUniverse_Mail(){
    super();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_SpaceForceRogueUniverse_Mail(String original, int id, int type, String from, String body){
    super(original);
    this.id = id;
    this.type = type;
    this.from = from;
    this.body = body;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getBody(){
    return body;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getFrom(){
    return from;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getID(){
    return id;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getType(){
    return type;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setBody(String body){
    this.body = body;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setFrom(String from){
    this.from = from;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setID(int id){
    this.id = id;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setType(int type){
    this.type = type;
    }


  }