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

import org.watto.ArrayTools;

/**
**********************************************************************************************
Maintains a list of all plugins of a given type
**********************************************************************************************
**/
public class WSPluginGroup {

  /** The type of plugins in this group **/
  String type = "";

  /** the plugins **/
  WSPlugin[] plugins = new WSPlugin[0];
  /** the number of plugins **/
  int numPlugins = 0;
  /** the current plugin **/
  int currentPlugin = 0;


/**
**********************************************************************************************
Constructor
@param type the type of plugins in this group
**********************************************************************************************
**/
  public WSPluginGroup(String type){
    this.type = type;
    }


/**
**********************************************************************************************
Adds the <i>plugin</i> into the array
@param plugin the plugin to add
**********************************************************************************************
**/
  public void addPlugin(WSPlugin plugin){
    if (numPlugins >= plugins.length){
      plugins = ArrayTools.resize(plugins,numPlugins+25);
      }

    if (!plugin.isEnabled()){
      return; // does not allow disabled plugins
      }

    plugins[numPlugins] = plugin;
    numPlugins++;
    }


/**
**********************************************************************************************
Gets the current plugin
@return the current plugin
**********************************************************************************************
**/
  public WSPlugin getCurrentPlugin(){
    if (numPlugins == 0){
      return null;
      }

    if (currentPlugin > numPlugins){
      currentPlugin = 0;
      }

    return plugins[currentPlugin];
    }


/**
**********************************************************************************************
Gets the plugin with the matching <i>code</i>
@param code the plugin to get
@return the matching plugin, or the current plugin if the matching plugin could not be found
**********************************************************************************************
**/
  public WSPlugin plugin(int pluginNumber){
    return getPlugin(pluginNumber);
    }


/**
**********************************************************************************************
Gets the plugin with the matching <i>code</i>
@param code the plugin to get
@return the matching plugin, or the current plugin if the matching plugin could not be found
**********************************************************************************************
**/
  public WSPlugin getPlugin(int pluginNumber){
    return getPlugin(pluginNumber,true);
    }


/**
**********************************************************************************************
Gets the plugin with the matching <i>code</i>
@param code the plugin to get
@param setCurrent whether to change the current plugin to the matching one
@return the matching plugin, or the current plugin if the matching plugin could not be found
**********************************************************************************************
**/
  public WSPlugin plugin(int pluginNumber, boolean setCurrent){
    return getPlugin(pluginNumber,setCurrent);
    }


/**
**********************************************************************************************
Gets the plugin with the matching <i>code</i>
@param code the plugin to get
@param setCurrent whether to change the current plugin to the matching one
@return the matching plugin, or the current plugin if the matching plugin could not be found
**********************************************************************************************
**/
  public WSPlugin getPlugin(int pluginNumber, boolean setCurrent){
    if (pluginNumber >= 0 && pluginNumber < numPlugins){
      if (setCurrent){
        if (plugins[currentPlugin] instanceof WSPanelPlugin){
          ((WSPanelPlugin)plugins[currentPlugin]).onCloseRequest();
          }
        currentPlugin = pluginNumber;
        }

      if (plugins[currentPlugin] instanceof WSPanelPlugin){
        ((WSPanelPlugin)plugins[currentPlugin]).onOpenRequest();
        }

      return plugins[pluginNumber];
      }
    return getCurrentPlugin();
    }


/**
**********************************************************************************************
Gets the plugin with the matching <i>code</i>
@param code the plugin to get
@return the matching plugin, or the current plugin if the matching plugin could not be found
**********************************************************************************************
**/
  public WSPlugin plugin(String code){
    return getPlugin(code);
    }


/**
**********************************************************************************************
Gets the plugin with the matching <i>code</i>
@param code the plugin to get
@return the matching plugin, or the current plugin if the matching plugin could not be found
**********************************************************************************************
**/
  public WSPlugin getPlugin(String code){
    return getPlugin(code,true);
    }


/**
**********************************************************************************************
Gets the plugin with the matching <i>code</i>
@param code the plugin to get
@param setCurrent whether to change the current plugin to the matching one
@return the matching plugin, or the current plugin if the matching plugin could not be found
**********************************************************************************************
**/
  public WSPlugin plugin(String code, boolean setCurrent){
    return getPlugin(code,setCurrent);
    }


/**
**********************************************************************************************
Gets the plugin with the matching <i>code</i>
@param code the plugin to get
@param setCurrent whether to change the current plugin to the matching one
@return the matching plugin, or the current plugin if the matching plugin could not be found
**********************************************************************************************
**/
  public WSPlugin getPlugin(String code, boolean setCurrent){
    for (int i=0;i<numPlugins;i++){
      //System.out.println(plugins[i].getCode());
      if (plugins[i].getCode().equals(code)){
        return getPlugin(i,setCurrent);
        }
      }
    return getCurrentPlugin();
    }


/**
**********************************************************************************************
Gets all the plugins
@return the plugins
**********************************************************************************************
**/
  public WSPlugin[] getPlugins(){
    if (numPlugins < plugins.length){
      plugins = ArrayTools.resize(plugins,numPlugins);
      }
    return plugins;
    }


/**
**********************************************************************************************
Gets the type of the plugins in this group
@return the type
**********************************************************************************************
**/
  public String getType(){
    return type;
    }


/**
**********************************************************************************************
Is there a plugin with the given <i>code</i>?
@param code the plugin to look for
@return -1 is no plugin was found, otherwise returns the plugin number
**********************************************************************************************
**/
  public int hasPlugin(String code){
    for (int i=0;i<numPlugins;i++){
      if (plugins[i].getCode().equals(code)){
        return i;
        }
      }
    return -1;
    }


  }