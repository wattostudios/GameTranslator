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

import org.watto.component.WSPlugin;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class RatedPlugin implements Comparable {

  WSPlugin plugin;
  int rating = 0;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public RatedPlugin(WSPlugin plugin, int rating){
    this.plugin = plugin;
    this.rating = rating;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getRating(){
    return rating;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Integer getRatingInteger(){
    return new Integer(rating);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSPlugin getPlugin(){
    return plugin;
    }



/**
**********************************************************************************************
  NOTE - the "0-" is needed to sort them in REVERSE order (ie 100 > 1 instead of 1 > 100)
**********************************************************************************************
**/
  public int compareTo(Object other) {
    return 0 - (getRatingInteger().compareTo(((RatedPlugin)other).getRatingInteger()));
    //return toString().compareTo(other.toString());
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String toString(){
    return plugin.toString();
    }

}