////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                            WATTO STUDIOS JAVA COMPONENT TOOLKIT                            //
//                    A Collection Of Components To Build Java Applications                   //
//                                    http://www.watto.org                                    //
//                                                                                            //
//                           Copyright (C) 2004-2008  WATTO Studios                           //
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

package org.watto.component;

import org.watto.*;
import org.watto.event.*;
import org.watto.xml.*;

import java.util.Hashtable;
import java.util.Enumeration;

/**
**********************************************************************************************
The repository is a central point for accessing WSComponents. All WSComponents that are
built from XML are placed in this class, against their code name.
**********************************************************************************************
**/
public class WSRepository{

  static Hashtable components = new Hashtable(50);


/**
**********************************************************************************************

**********************************************************************************************
**/
  public WSRepository(){
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static String add(WSComponent component){
    String code = component.getCode();
    if (code == null || code.equals("")){
      code = "" + component.hashCode();
      component.setCode(code);
      }
    components.put(code,component);
    return code;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static WSComponent remove(String code){
    Object component = components.remove(code);
    if (component instanceof WSComponent){
      return (WSComponent)component;
      }
    else{
      return null;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static WSComponent get(String code){
    Object component = components.get(code);
    if (component instanceof WSComponent){
      return (WSComponent)component;
      }
    else{
      return null;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static boolean has(String code){
    return components.containsKey(code);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static WSComponent focus(String code){
    Object component = components.get(code);
    if (component instanceof WSComponent){
      WSComponent wsc = (WSComponent)component;
      wsc.setFocus(true);
      return wsc;
      }
    else{
      return null;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void printComponentList(){
    Enumeration keys = components.keys();
    Enumeration values = components.elements();

    while (keys.hasMoreElements() && values.hasMoreElements()){
      String key = (String) keys.nextElement();
      String value = values.nextElement().getClass().getName();

      System.out.println(key + " : " + value);
      }

    }





  }