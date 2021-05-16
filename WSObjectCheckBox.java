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

import org.watto.component.WSCheckBox;
import org.watto.xml.XMLNode;

public class WSObjectCheckBox extends WSCheckBox {

  Object object;

/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSObjectCheckBox(Object object) {
    super();
    this.object = object;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSObjectCheckBox(XMLNode node, Object object) {
    super(node);
    this.object = object;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getObject() {
    return object;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setObject(Object object) {
    this.object = object;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getText() {
    if (object == null){
      return "";
      }
    return object.toString();
    }



  }