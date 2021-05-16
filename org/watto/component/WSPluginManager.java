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

import java.net.*;
import java.util.zip.*;
import java.io.*;
import java.util.Hashtable;

/**
**********************************************************************************************
A class that scans for *.class files in directories and zip files, and passes each of them to
all the registered PluginManagers to see if they want to load the file. Basically just a class
that gives all *.class files to all PluginManagers so that there is only 1 pass needed over any
directory or zip file, rather than a pass for each PluginManager individually.
<br><br>
If a PluginManager loads the plugin, all remaining PluginManagers are skipped, as a plugin can
only be associated with a single PluginManager. Therefore, the order of trying the PluginManagers
is sorted from most popular to lease popular, so that skipping can occur as frequently as
possible, decreasing the processing time.
**********************************************************************************************
**/
public class WSPluginManager {

  /** The plugin groups **/
  static WSPluginGroup[] groups = new WSPluginGroup[0];

  /** The filename prefix that indicates a compatable plugin **/
  static String[] prefixes = new String[0];

  /** A description of this plugin **/
  static String description = "The base plugin that loads all other plugins.";


/**
**********************************************************************************************
Constructor
**********************************************************************************************
**/
  public WSPluginManager(){
    loadPlugins();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static boolean addPlugin(String name, ClassLoader cl){
    for (int i=0;i<prefixes.length;i++){
      if (name.indexOf(prefixes[i]) == 0){
        try {
          addPlugin((WSPlugin)cl.loadClass(name).newInstance(), i);
          return true;
          }
        catch (Throwable t){
          ErrorLogger.log(t);
          }
        }
      }
    return false;
    }


/**
**********************************************************************************************
Adds the <i>plugin</i> to the group
@param plugin the plugin to add
@param groupNumber the number of the group to add the plugin to
**********************************************************************************************
**/
  public static void addPlugin(WSPlugin plugin, int groupNumber){
    WSPluginGroup group = groups[groupNumber];
    group.addPlugin(plugin);
    }


/**
**********************************************************************************************
Adds the <i>prefix</i> to the list of available prefixes
@param prefix the prefix to add
@param type the group type of these plugins
**********************************************************************************************
**/
  public static void addPrefix(String prefix, String type){
    int numPrefixes = prefixes.length;

    prefixes = ArrayTools.resize(prefixes,numPrefixes+1);
    prefixes[numPrefixes] = prefix;

    WSPluginGroup[] tempG = groups;
    groups = new WSPluginGroup[numPrefixes+1];
    System.arraycopy(tempG,0,groups,0,numPrefixes);

    groups[numPrefixes] = new WSPluginGroup(type);
    }


/**
**********************************************************************************************
Gets the plugin group of the given <i>type</i>
@param type the type of plugin group too get
@return the plugin group, or null if no plugin group exists
**********************************************************************************************
**/
  public static WSPluginGroup getGroup(String type){
    for (int i=0;i<groups.length;i++){
      if (groups[i].getType().equals(type)){
        return groups[i];
        }
      }
    return null;
    }


/**
**********************************************************************************************
Gets the plugin group of the given <i>type</i>
@param type the type of plugin group too get
@return the plugin group, or null if no plugin group exists
**********************************************************************************************
**/
  public static WSPluginGroup group(String type){
    return getGroup(type);
    }


/**
**********************************************************************************************
Loads the PluginManagers that are used to load the remaining plugins
**********************************************************************************************
**/
  public static void loadPlugins(){
    try {
      File pluginPaths = new File(Settings.getString("PluginPathsFile"));

      if (!pluginPaths.exists()){
        return;
        }

      XMLNode root = XMLReader.read(pluginPaths);//.getChild(0);


      // load the prefixes
      XMLNode prefixesNode = root.getChild("prefixes");
      int numPrefixes = prefixesNode.getChildCount();

      for (int i=0;i<numPrefixes;i++){
        XMLNode prefixNode = prefixesNode.getChild(i);

        String prefixType = prefixNode.getAttribute("type");

        String prefixName = prefixNode.getContent();
        addPrefix(prefixName,prefixType);
        }


      // load the plugins
      XMLNode locationsNode = root.getChild("locations");
      int numLocations = locationsNode.getChildCount();

      for (int i=0;i<numLocations;i++){
        XMLNode locationNode = locationsNode.getChild(i);

        String locationName = locationNode.getContent();
        File location = new File(new File(locationName).getAbsolutePath());

        String locationType = locationNode.getAttribute("type");
        if (locationType.equals("zip")){
          loadFromZip(location);
          }
        else {
          loadFromDirectory(location);
          }
        }

      }
    catch (Throwable t){
      ErrorLogger.log(t);
      }
    }


/**
**********************************************************************************************
Scans all the files in the <i>directory</i> and passes each *.class file to the PluginManagers
**********************************************************************************************
**/
  public static void loadFromDirectory(File directory) {
    try {

      if (!directory.exists()){
        return;
        }

      File[] files = directory.listFiles();

      URL classURL = new URL("file:" + directory.getAbsolutePath() + "/");
      URLClassLoader cl = URLClassLoader.newInstance(new URL[] { classURL });

      for (int i=0;i<files.length;i++){

        String name = files[i].getName();
        if (name.length() <= 6 || name.indexOf(".class") < 0){
          continue;
          }
        name = name.substring(0,name.length() - 6);

        try {
          if (addPlugin(name,cl)){
            continue;
            }
          }
        catch (Throwable e){
          // Skips bad panels
          }
        }

      }
    catch (Throwable t){
      ErrorLogger.log(t);
      }
    }


/**
**********************************************************************************************
Scans all the files in the <i>zip</i> archive, and passes each *.class file to the PluginManagers
**********************************************************************************************
**/
  public static void loadFromZip(File zip) {
    try {

      if (! zip.exists()){
        return;
        }

      ZipFile zipFile = new ZipFile(zip);
      java.util.Enumeration files = zipFile.entries();

      ClassLoader cl = ClassLoader.getSystemClassLoader();

      while (files.hasMoreElements()){

        String name = ((ZipEntry)files.nextElement()).getName();
        if (name.length() <= 6 || name.indexOf(".class") < 0){
          continue;
          }

        name = name.substring(0,name.length() - 6);

        try {
          if (addPlugin(name,cl)){
            continue;
            }
          }
        catch (Throwable e){
          // Skips bad panels
          }
        }

      zipFile.close();

      }
    catch (Throwable t){
      ErrorLogger.log(t);
      }
    }


  }