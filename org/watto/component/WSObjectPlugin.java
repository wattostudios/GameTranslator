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

import org.watto.ErrorLogger;
import org.watto.Language;

/**
**********************************************************************************************
Defines a class that can be loaded and used dynamically by a WSProgram.
**********************************************************************************************
**/
public class WSObjectPlugin implements WSPlugin {

  /** The code for the language and settings **/
  String code = "";

  /** The name of this plugin **/
  String name = "WS Plugin";
  /** A description of this plugin **/
  String description = "A plugin.";

  /** Is this plugin enabled? **/
  boolean enabled = true;

  /** the plugin type **/
  String type = "Generic";


/**
**********************************************************************************************
Compares this plugin to another plugin, by name
@param otherPlugin the plugin that this is being compared against
@return an integer describing the sort order
**********************************************************************************************
**/
  public int compareTo(Object otherPlugin){
    return name.compareTo(((WSPlugin)otherPlugin).getName());
    }


/**
**********************************************************************************************
Gets the Language <i>code</i> for this component
@return the Language code
**********************************************************************************************
**/
  public String getCode(){
    return code;
    }


/**
**********************************************************************************************
Gets the description of the plugin
@return the description
**********************************************************************************************
**/
  public String getDescription(){
    String descriptionCode = "WSObjectPlugin_" + code + "_Description";
    if (Language.has(descriptionCode)){
      return Language.get(descriptionCode);
      }
    return description;
    }


/**
**********************************************************************************************
Gets the name of the plugin
@return the name
**********************************************************************************************
**/
  public String getName(){
    String nameCode = "WSObjectPlugin_" + code + "_Name";
    if (Language.has(nameCode,false)){
      return Language.get(nameCode);
      }
    return name;
    }


/**
**********************************************************************************************
Gets the type of the plugin
@return the type
**********************************************************************************************
**/
  public String getType(){
    return type;
    }


/**
**********************************************************************************************
Is this plugin enabled?
@return tru if the plugin is enabled, false if disabled
**********************************************************************************************
**/
  public boolean isEnabled(){
    return enabled;
    }


/**
**********************************************************************************************
Sets the code for this components Language text
@param code the code for the Language
**********************************************************************************************
**/
  public void setCode(String code){
    this.code = code;
    }


/**
**********************************************************************************************
Sets the description of the plugin
@param description the description
**********************************************************************************************
**/
  public void setDescription(String description){
    this.description = description;
    }


/**
**********************************************************************************************
Sets whether this plugin is enabled
@param enabled the enabled status
**********************************************************************************************
**/
  public void setEnabled(boolean enabled){
    this.enabled = enabled;
    }


/**
**********************************************************************************************
Sets the name of the plugin
@param name the name
**********************************************************************************************
**/
  public void setName(String name){
    this.name = name;
    }


/**
**********************************************************************************************
Gets the name of the plugin
@return the name
**********************************************************************************************
**/
  public String toString(){
    return getName();
    }


/**
**********************************************************************************************
Sends an error to the ErrorLogger for recording
@param t the error that occurred
**********************************************************************************************
**/
  public static void logError(Throwable t){
    ErrorLogger.log(t);
    }


  }

