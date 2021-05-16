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

public class Resource_ArcadeRaceCrash extends Resource {

  float size1 = 1.0f;
  float size2 = 1.0f;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_ArcadeRaceCrash(){
    super();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_ArcadeRaceCrash(String original, float size1, float size2){
    super(original);
    this.size1 = size1;
    this.size2 = size2;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public float getSize1(){
    return size1;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public float getSize2(){
    return size2;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSize1(float size1){
    this.size1 = size1;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSize2(float size2){
    this.size2 = size2;
    }


  }