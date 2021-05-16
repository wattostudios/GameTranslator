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

/**
**********************************************************************************************
Defines a class that can be loaded and used dynamically by a WSProgram.
**********************************************************************************************
**/
public interface WSPlugin extends Comparable {


/**
**********************************************************************************************
Compares this plugin to another plugin, by name
@param otherPlugin the plugin that this is being compared against
@return an integer describing the sort order
**********************************************************************************************
**/
  public int compareTo(Object otherPlugin);


/**
**********************************************************************************************
Gets the Language <i>code</i> for this plugin
@return the Language code
**********************************************************************************************
**/
  public String getCode();


/**
**********************************************************************************************
Gets the description of the plugin
@return the description
**********************************************************************************************
**/
  public String getDescription();


/**
**********************************************************************************************
Gets the name of the plugin
@return the name
**********************************************************************************************
**/
  public String getName();


/**
**********************************************************************************************
Is this plugin enabled?
@return true if the plugin is enabled, false if disabled
**********************************************************************************************
**/
  public boolean isEnabled();


/**
**********************************************************************************************
Sets the code for this plugins' Language text
@param code the code for the Language
**********************************************************************************************
**/
  public void setCode(String code);


/**
**********************************************************************************************
Sets the description of the plugin
@param description the description
**********************************************************************************************
**/
  public void setDescription(String description);


/**
**********************************************************************************************
Sets whether this plugin is enabled
@param enabled the enabled status
**********************************************************************************************
**/
  public void setEnabled(boolean enabled);


/**
**********************************************************************************************
Sets the name of the plugin
@param name the name
**********************************************************************************************
**/
  public void setName(String name);


/**
**********************************************************************************************
Gets the name of the plugin
@return the name
**********************************************************************************************
**/
  public String toString();


  }

