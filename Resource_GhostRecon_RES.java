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

public class Resource_GhostRecon_RES extends Resource implements Comparable {

  int group = 0;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_GhostRecon_RES(){
    super();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_GhostRecon_RES(String original, int group){
    super(original);
    this.group = group;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int compareTo(Object otherResource){
    if (otherResource instanceof Resource_GhostRecon_RES){
      return new Integer(group).compareTo(new Integer(((Resource_GhostRecon_RES)otherResource).getGroup()));
      }
    return 0;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getGroup(){
    return group;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setGroup(int group){
    this.group = group;
    }


  }