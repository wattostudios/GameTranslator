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

import org.watto.*;
import org.watto.component.*;

import java.util.*;

public class PluginListBuilder {

/**
**********************************************************************************************

**********************************************************************************************
**/
  public PluginListBuilder() {
    }



/**
**********************************************************************************************

**********************************************************************************************
**/
  public static PluginList[] convertPluginsToExtensionList(WSPlugin[] plugins) {
    if (plugins.length == 0){
      return new PluginList[0];
      }

    Hashtable pluginList = new Hashtable(plugins.length);

    for (int i=0;i<plugins.length;i++){
      ArchivePlugin arcPlugin = (ArchivePlugin)plugins[i];
      if (arcPlugin == null){
        continue;
        }
      String name = arcPlugin.getName();
      String[] exts = arcPlugin.getExtensions();

      for (int e=0;e<exts.length;e++){
        String desc = "*." + exts[e] + " (" + name + ")";
        pluginList.put(desc,new PluginList(desc,arcPlugin));
        }
      }


    Collection values = pluginList.values();
    PluginList[] list = (PluginList[])values.toArray(new PluginList[0]);

    if (Settings.getBoolean("SortPluginLists")){
      Arrays.sort(list);
      }

    return list;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static PluginList[] convertPluginsToGameList(WSPlugin[] plugins) {
    if (plugins.length == 0){
      return new PluginList[0];
      }

    Hashtable pluginList = new Hashtable(plugins.length);

    for (int i=0;i<plugins.length;i++){
      ArchivePlugin arcPlugin = (ArchivePlugin)plugins[i];

      if (arcPlugin == null){
        continue;
        }
      String[] games = arcPlugin.getGames();

      String ext = " (" + arcPlugin.getName() + ")";


      for (int g=0;g<games.length;g++){
        String desc = games[g] + ext;
        pluginList.put(desc,new PluginList(desc,arcPlugin));
        }
      }


    Collection values = pluginList.values();
    PluginList[] list = (PluginList[])values.toArray(new PluginList[0]);

    if (Settings.getBoolean("SortPluginLists")){
      Arrays.sort(list);
      }

    return list;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static PluginList[] convertPluginsToList(WSPlugin[] plugins) {
    if (plugins.length == 0){
      return new PluginList[0];
      }

    PluginList[] list = new PluginList[plugins.length];

    for (int i=0;i<plugins.length;i++){
      ArchivePlugin arcPlugin = (ArchivePlugin)plugins[i];
      if (arcPlugin == null){
        continue;
        }
      String name = arcPlugin.getName();
      list[i] = new PluginList(name,arcPlugin);
      }


    if (Settings.getBoolean("SortPluginLists")){
      Arrays.sort(list);
      }

    return list;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static PluginList[] getPluginList(WSPlugin[] plugins) {
    if (plugins.length == 0){
      return new PluginList[0];
      }

    PluginList[] list;
    String displayType = Settings.get("PluginListDisplayType");

    if (displayType.equals("Game")){
      list = convertPluginsToGameList(plugins);
      }
    else if (displayType.equals("Extension")){
      list = convertPluginsToExtensionList(plugins);
      }
    else {
      list = convertPluginsToList(plugins);
      }

    return list;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static PluginList[] getPluginList() {
    WSPlugin[] plugins = WSPluginManager.group("Archive").getPlugins();

    if (plugins == null || plugins.length == 0){
      plugins = new ArchivePlugin[0];
      }

    return getPluginList(plugins);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static PluginList[] getWritePluginList() {
    ArchivePlugin[] arcPlugins = getWritePlugins();
    return getPluginList(arcPlugins);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ArchivePlugin[] getWritePlugins() {
    WSPlugin[] plugins = WSPluginManager.group("Archive").getPlugins();

    if (plugins == null || plugins.length == 0){
      plugins = new ArchivePlugin[0];
      }

    ArchivePlugin[] writePlugins = new ArchivePlugin[plugins.length];
    int numWritable = 0;

    // If the plugin can do renames or replaces, add it into the writeArchive list.
    // But if the archive has been modified irreversably, don't allow it.
    // Irreversable is, for example, add or remove files in replace-only or rename-only archives
    ArchivePlugin readPlugin = Archive.getReadPlugin();
    if (readPlugin != null){

      // Also grabs all implicitReplacing plugins
      if (! readPlugin.canWrite() && (readPlugin.canReplace())){
        writePlugins[numWritable] = readPlugin;
        numWritable++;
        }
      }

    for (int i=0;i<plugins.length;i++){
      ArchivePlugin arcPlugin = (ArchivePlugin)plugins[i];
      if (arcPlugin.canWrite()){
        writePlugins[numWritable] = arcPlugin;
        numWritable++;
        }
      }

    if (numWritable == writePlugins.length){
      return writePlugins;
      }

    ArchivePlugin[] resizedWrite = new ArchivePlugin[numWritable];
    System.arraycopy(writePlugins,0,resizedWrite,0,numWritable);

    return resizedWrite;
    }


  }