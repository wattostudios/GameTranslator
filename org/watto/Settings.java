////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                            WATTO STUDIOS JAVA PROGRAM TEMPLATE                             //
//                  Template Classes and Helper Utilities for Java Programs                   //
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

package org.watto;

import org.watto.xml.*;
import org.watto.component.*;

import java.io.*;
import java.util.*;

/**
**********************************************************************************************
A static class that is used to store and manage the program settings. This class can be used
in other programs without alteration. This class provides all static methods for retrieving
and storing settings for the program, thus allowing all classes in the JVM to access the same
settings, and when one setting changes then all classes will know the change.
<br><br>
This class also provides a mechanism for loading default values, so that program updates will
not fail due to missing settings in an old setting file. In essence, the settings file is
updated whenever this class is loaded, adding in any missing values.
**********************************************************************************************
**/
public class Settings {

  /** the settings and their codes **/
  static Hashtable settings = null;

  /** the settings file **/
  static XMLNode settingFile;


/**
**********************************************************************************************
Constructor that loads the Default and Saved settings from their default locations
**********************************************************************************************
**/
  public Settings() {
    File defaultsFile = new File("settings" + File.separator + "defaults" + File.separator + "settings.wsd");
    File settingsFile = new File("settings" + File.separator + "settings.wsd");
    loadSettings(defaultsFile,settingsFile);
    }


/**
**********************************************************************************************
Constructor that loads the Default and Saved settings from the given files
@param defaultsFile the file of default settings
@param settingsFile the file of saved settings
**********************************************************************************************
**/
  public Settings(File defaultsFile, File settingsFile) {
    loadSettings(defaultsFile,settingsFile);
    }


/**
**********************************************************************************************
Loads the settings from the given Default and Saved settings files
@param defaultsFile the file of default settings
@param settingsFile the file of saved settings
**********************************************************************************************
**/
  public static void loadSettings(File defaultsFile, File settingsFile) {
    try {

      XMLNode defaultsTree = XMLReader.read(defaultsFile);
      loadSettings(defaultsTree);

      settingFile = defaultsTree;

      if (! settingsFile.exists()){
        saveSettings();
        }

      XMLNode settingsTree = XMLReader.read(settingsFile);
      loadSettings(settingsTree);

      set("SettingsFile-Defaults",defaultsFile.getAbsolutePath());
      set("SettingsFile",settingsFile.getAbsolutePath());

      settingFile = settingsTree;

      ErrorLogger.setDebug(getBoolean("DebugMode"));

      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Loads the settings from the <i>tree</i> of values
@param tree the tree containing the settings
**********************************************************************************************
**/
  public static void loadSettings(XMLNode tree){
    try {

      XMLNode settingNode = tree.getChild("settings");

      int numSettings = settingNode.getChildCount();
      if (settings == null){
        settings = new Hashtable(numSettings);
        }

      for (int i=0;i<numSettings;i++){
        XMLNode text = settingNode.getChild(i);

        String settingCode = text.getAttribute("code");
        String settingString = text.getAttribute("value");

        String changable = text.getAttribute("changable");
        if (changable != null && changable.equals("false")){
          // not changable - make it a TemporarySetting
          TemporarySettings.set(settingCode,settingString);
          }
        else {
          // normal - make it a Setting
          settings.put(settingCode,settingString);
          }

        }

      }
    catch (Exception e){
      logError(e);
      }
    }


/**
**********************************************************************************************
Saves the current settings to the Saved settings file
**********************************************************************************************
**/
  public static void saveSettings() {
    try {

      File path = new File(getString("SettingsFile"));

      if (path.exists()){
        path.delete();
        }


      // build an XML tree of the settings
      XMLNode settingsTree = settingFile.getChild("settings");

      settingsTree.removeAllChildren();


      Enumeration keys = settings.keys();
      Enumeration values = settings.elements();

      while (keys.hasMoreElements() && values.hasMoreElements()){
        String key = (String) keys.nextElement();
        String value = (String) values.nextElement();

        XMLNode setting = new XMLNode("setting");
        setting.addAttribute("code",key);
        setting.addAttribute("value",value);
        settingsTree.addChild(setting);
        }

      XMLWriter.write(path,settingFile);

      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Changes all the settings back to their defaults
**********************************************************************************************
**/
  public static void revertToDefaults() {
    try {

      File defaultsFile = new File(get("SettingsFile-Defaults"));
      XMLNode defaultsTree = XMLReader.read(defaultsFile);
      loadSettings(defaultsTree);

      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Adds the <i>path</i> as a recent file.
@param path the recent file to add.
**********************************************************************************************
**/
  public static void addRecentFile(File path) {
    try {

      String filePath = path.getAbsolutePath();

      int numRecentFiles = getInt("NumberOfRecentFiles");

      String previousRecentFile = filePath;


      for (int i=1;i<=numRecentFiles;i++){ // start at 1 because the 1st recent file is #1, not #0
        String recentFileCode = "RecentFile"+i;
        String existingRecentFile = getString(recentFileCode);

        if (previousRecentFile == null){
          break;
          }
        set(recentFileCode,previousRecentFile);

        if (filePath.equals(existingRecentFile)){
          break;
          }

        previousRecentFile = existingRecentFile;
        }

      ((WSRecentFileMenu)WSRepository.get("RecentFileMenu")).rebuild();

      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Gets the String setting with the given <i>setting</i> code
@param settings the setting code
@return a String of the setting value
**********************************************************************************************
**/
  public static String get(String setting) {
    return getString(setting);
    }


/**
**********************************************************************************************
Gets the String setting with the given <i>setting</i> code
@param settings the setting code
@param showErrorAlert whether to log an error when a setting does not exist
@return a String of the setting value
**********************************************************************************************
**/
  public static String get(String setting, boolean showErrorAlert) {
    return getString(setting,showErrorAlert);
    }


/**
**********************************************************************************************
Gets the boolean setting with the given <i>setting</i> code
@param settings the setting code
@return a boolean of the setting value
**********************************************************************************************
**/
  public static boolean getBoolean(String setting) {
    return getBoolean(setting,true);
    }


/**
**********************************************************************************************
Gets the boolean setting with the given <i>setting</i> code
@param settings the setting code
@param showErrorAlert whether to log an error when a setting does not exist
@return a boolean of the setting value
**********************************************************************************************
**/
  public static boolean getBoolean(String setting, boolean showErrorAlert) {
    try {
      String result = (String)settings.get(setting);
      if (result == null){
        result = (String)TemporarySettings.get(setting); // maybe it is a temporary setting?
        }

      if (result.equals("true")){
        return true;
        }
      else {
        return false;
        }
      }
    catch (Throwable t){
      // If it is missing - create it and make it true
      //set(setting,true);
      if (showErrorAlert){
        logMissingSetting(setting);
        }
      return true;
      }
    }


/**
**********************************************************************************************
Gets the double setting with the given <i>setting</i> code
@param settings the setting code
@return a double of the setting value
**********************************************************************************************
**/
  public static double getDouble(String setting) {
    return getDouble(setting,true);
    }


/**
**********************************************************************************************
Gets the double setting with the given <i>setting</i> code
@param settings the setting code
@param showErrorAlert whether to log an error when a setting does not exist
@return a double of the setting value
**********************************************************************************************
**/
  public static double getDouble(String setting, boolean showErrorAlert) {
    try {
      String result = (String)settings.get(setting);
      if (result == null){
        result = (String)TemporarySettings.get(setting); // maybe it is a temporary setting?
        }

      return Double.parseDouble(result);
      }
    catch (Throwable t){
      if (showErrorAlert){
        logMissingSetting(setting);
        }
      return -1;
      }
    }


/**
**********************************************************************************************
Gets the integer setting with the given <i>setting</i> code
@param settings the setting code
@return an int of the setting value
**********************************************************************************************
**/
  public static int getInt(String setting) {
    return getInt(setting,true);
    }


/**
**********************************************************************************************
Gets the integer setting with the given <i>setting</i> code
@param settings the setting code
@param showErrorAlert whether to log an error when a setting does not exist
@return an int of the setting value
**********************************************************************************************
**/
  public static int getInt(String setting, boolean showErrorAlert) {
    try {
      String result = (String)settings.get(setting);
      if (result == null){
        result = (String)TemporarySettings.get(setting); // maybe it is a temporary setting?
        }

      return Integer.parseInt(result);
      }
    catch (Throwable t){
      if (showErrorAlert){
        logMissingSetting(setting);
        }
      return -1;
      }
    }


/**
**********************************************************************************************
Gets the integer setting with the given <i>setting</i> code
@param settings the setting code
@return an int of the setting value
**********************************************************************************************
**/
  public static long getLong(String setting) {
    return getLong(setting,true);
    }


/**
**********************************************************************************************
Gets the integer setting with the given <i>setting</i> code
@param settings the setting code
@param showErrorAlert whether to log an error when a setting does not exist
@return an int of the setting value
**********************************************************************************************
**/
  public static long getLong(String setting, boolean showErrorAlert) {
    try {
      String result = (String)settings.get(setting);
      if (result == null){
        result = (String)TemporarySettings.get(setting); // maybe it is a temporary setting?
        }

      return Long.parseLong(result);
      }
    catch (Throwable t){
      if (showErrorAlert){
        logMissingSetting(setting);
        }
      return -1;
      }
    }


/**
**********************************************************************************************
Gets the String setting with the given <i>setting</i> code
@param settings the setting code
@return a String of the setting value
**********************************************************************************************
**/
  public static String getString(String setting) {
    return getString(setting,true);
    }


/**
**********************************************************************************************
Gets the String setting with the given <i>setting</i> code
@param settings the setting code
@param showErrorAlert whether to log an error when a setting does not exist
@return a String of the setting value
**********************************************************************************************
**/
  public static String getString(String setting, boolean showErrorAlert) {
    try {
      String result = (String)settings.get(setting);
      if (result == null){
        result = (String)TemporarySettings.get(setting); // maybe it is a temporary setting?
        }
      return result;
      }
    catch (Throwable t){
      if (showErrorAlert){
        logMissingSetting(setting);
        }
      return "";
      }
    }


/**
**********************************************************************************************
Sets the setting with code <i>key</i> to the <i>value</i>
@param key the setting code
@param value the value of the setting
**********************************************************************************************
**/
  public static void set(String key, String value) {
    try {
      if (value != null){
        settings.put(key,value);
        }
      }
    catch (Throwable t){
      logError(t);
      }
    }


/**
**********************************************************************************************
Sets the setting with code <i>key</i> to the <i>value</i>
@param key the setting code
@param value the value of the setting
**********************************************************************************************
**/
  public static void set(String key, int value) {
    set(key,""+value);
    }


/**
**********************************************************************************************
Sets the setting with code <i>key</i> to the <i>value</i>
@param key the setting code
@param value the value of the setting
**********************************************************************************************
**/
  public static void set(String key, double value) {
    set(key,""+value);
    }


/**
**********************************************************************************************
Sets the setting with code <i>key</i> to the <i>value</i>
@param key the setting code
@param value the value of the setting
**********************************************************************************************
**/
  public static void set(String key, boolean value) {
    set(key,""+value);
    }



/**
**********************************************************************************************
Records the error/exception stack trace in the log file. If debug is enabled, it will also
write the error to the <i>System.out</i> command prompt
@param t the <i>Throwable</i> error/exception
**********************************************************************************************
**/
  public static void logError(Throwable t){
    ErrorLogger.log(t);
    }


/**
**********************************************************************************************
If a <i>setting</i> is missing and setting "DebugMode" is turned on, the name of the missing
setting will be output to the command prompt. If setting "ExtendedSettingsDebug" is also
enabled, a complete stack trace will also be output, telling where the setting was trying to
be used.
@param setting the setting code that was missing
**********************************************************************************************
**/
  public static void logMissingSetting(String setting) {
    if (Settings.getBoolean("DebugMode",false)){
      System.out.println("Missing Setting: " + setting);
      if (Settings.getBoolean("ExtendedSettingsDebug")){
        try {
          throw new Exception("Missing Setting");
          }
        catch (Throwable t){
          ErrorLogger.log(t);
          }
        }
      }
    }


  }

