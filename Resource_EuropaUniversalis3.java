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

public class Resource_EuropaUniversalis3 extends Resource {

  String code = "";
  String french = "";
  String german = "";
  String polish = "";
  String spanish = "";
  String portuguese = "";
  String blank = "";
  String x = "";


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_EuropaUniversalis3(){
    super();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource_EuropaUniversalis3(String original, String code, String french, String german, String polish, String spanish, String portuguese, String blank, String x){    super(original);
    this.code = code;
    this.french = french;
    this.german = german;
    this.polish = polish;
    this.spanish = spanish;
    this.portuguese = portuguese;
    this.blank = blank;
    this.x = x;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getCode(){
    return code;
    }

  public String getFrench(){
    return french;
    }

  public String getGerman(){
    return german;
    }

  public String getPolish(){
    return polish;
    }

  public String getSpanish(){
    return spanish;
    }

  public String getPortuguese(){
    return portuguese;
    }

  public String getBlank(){
    return blank;
    }

  public String getX(){
    return x;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setCode(String code){
    this.code = code;
    }

  public void setFrench(String french){
    this.french = french;
    }

  public void setGerman(String german){
    this.german = german;
    }

  public void setPolish(String polish){
    this.polish = polish;
    }

  public void setSpanish(String spanish){
    this.spanish = spanish;
    }

  public void setPortuguese(String portuguese){
    this.portuguese = portuguese;
    }

  public void setBlank(String blank){
    this.blank = blank;
    }

  public void setX(String x){
    this.x = x;
    }


  }