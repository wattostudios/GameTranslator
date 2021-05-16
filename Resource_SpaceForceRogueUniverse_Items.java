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

public class Resource_SpaceForceRogueUniverse_Items extends Resource {

  String description = "";
  int patch = 0;
  int group = 0;
  int unit = 0;
  double price = 0;
  double weight = 0;
  double space = 0;
  int power = 0;
  int data1 = -1;
  int data2 = -1;

/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_SpaceForceRogueUniverse_Items(){
    super();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_SpaceForceRogueUniverse_Items(String original, String description, int patch, int group, int unit, double price, double weight, double space, int power, int data1, int data2){
    super(original);
    this.description = description;
    this.patch = patch;
    this.group = group;
    this.unit = unit;
    this.price = price;
    this.weight = weight;
    this.space = space;
    this.power = power;
    this.data1 = data1;
    this.data2 = data2;
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
  public int getPatch(){
    return patch;
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
  public int getUnit(){
    return unit;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public double getPrice(){
    return price;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public double getWeight(){
    return weight;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public double getSpace(){
    return space;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getPower(){
    return power;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getData1(){
    return data1;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getData2(){
    return data2;
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
  public void setPatch(int patch){
    this.patch = patch;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setGroup(int group){
    this.group = group;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setUnit(int unit){
    this.unit = unit;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setPrice(double price){
    this.price = price;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setWeight(double weight){
    this.weight = weight;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setSpace(double space){
    this.space = space;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setPower(int power){
    this.power = power;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setData1(int data1){
    this.data1 = data1;
    }

/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setData2(int data2){
    this.data2 = data2;
    }


  }