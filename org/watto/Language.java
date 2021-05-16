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

import org.watto.component.*;
import org.watto.xml.*;

import java.io.*;
import java.util.*;

/**
**********************************************************************************************
Language is a static class that allows all classes in the JVM to access language-specific
strings through static methods. The language strings are loaded from an external file that
has the same name as the language to be used. The language can also be changed, which will
rebuild the list of strings.
**********************************************************************************************
**/
public class Language extends ResourceBundle{

  /** the translation codes and texts for the current language **/
  static Hashtable<String,String> messages = new Hashtable<String,String>();

  /** the current language **/
  static XMLNode language;

  /** all loaded languages **/
  static XMLNode[] languages;


/**
**********************************************************************************************
Constructor that loads the languages from the language directory in the Settings.
**********************************************************************************************
**/
  public Language(){
    loadLanguages(Settings.getString("CurrentLanguage"));
    }


/**
**********************************************************************************************
Loads all the language files in the languages directory, and changes to the current language
@param language the current language
**********************************************************************************************
**/
  public static void loadLanguages(String language){
    try {
      File langDir = new File(Settings.getString("LanguageDirectory"));

      File[] langFiles = langDir.listFiles();
      int numLanguages = langFiles.length;

      languages = new XMLNode[numLanguages];

      for (int i=0;i<numLanguages;i++){
        languages[i] = XMLReader.read(langFiles[i]);

        if (languages[i].getChild("name").getContent().equals(language)){
          changeLanguage(languages[i]);
          }

        }

      }
    catch (Exception e){
      logError(e);
      }
    }


/**
**********************************************************************************************
Changes the language to that with the given <i>langName</i>. If no language is found, the
current language is retained
@param langName the name of the language to change to.
**********************************************************************************************
**/

  public static void changeLanguage(String langName){
    try {
      for (int i=0;i<languages.length;i++){
        if (languages[i].getChild("name").getContent().equals(langName)){
          changeLanguage(languages[i]);
          return;
          }
        }
      }
    catch (Exception e){
      logError(e);
      }
    }


/**
**********************************************************************************************
Changes the language to that specified in the <i>tree</i>
@param tree the tree of language translations
**********************************************************************************************
**/
  public static void changeLanguage(XMLNode tree){
    try {

      XMLNode langNode = tree.getChild("texts");

      int numTexts = langNode.getChildCount();
      for (int i=0;i<numTexts;i++){
        XMLNode text = langNode.getChild(i);

        String langCode = text.getAttribute("code");
        String langString = text.getAttribute("value");

        String existingValue = messages.get(langCode);
        if (existingValue != null && existingValue.equals("")){
          }
        else {
          // Only put it in if the existing value does not exist, and is not blank.
          // This is important, as showText="false" on buttons sets the text to blank.
          messages.put(langCode,langString);
          }

        }

      language = tree;

      Settings.set("CurrentLanguage",language.getChild("name").getContent());

      }
    catch (Exception e){
      logError(e);
      }
    }


/**
**********************************************************************************************
Is there a language-dependent text for the <i>code</i>?
@param code the language code
@return true if a language text for the <i>code</i> exists, false if it doesn't exist
**********************************************************************************************
**/
  public static boolean has(String code){
    return has(code,true);
    }


/**
**********************************************************************************************
Is there a language-dependent text for the <i>code</i>?
@param code the language code
@return true if a language text for the <i>code</i> exists, false if it doesn't exist
**********************************************************************************************
**/
  public static boolean has(String code, boolean showErrorAlert){
    if (!messages.containsKey(code)){

      if (showErrorAlert && Settings.getBoolean("DebugMode")){
        System.out.println("Missing Language: " + code);
        if (Settings.getBoolean("ExtendedLanguageDebug")){
          try {
            throw new Exception("Missing Language");
            }
          catch (Throwable t){
            ErrorLogger.log(t);
            }
          }
        }

      return false;
      }
    else {
      return true;
      }
    }


/**
**********************************************************************************************
Unused
**********************************************************************************************
**/
  public Object[][] getContents(){
    return new Object[0][0];
    }


/**
**********************************************************************************************
Gets the number of loaded languages
@return the number of loaded languages
**********************************************************************************************
**/
  public static int getNumLanguages(){
    return languages.length;
    }


/**
**********************************************************************************************
Gets the trees for all the loaded languages
@return the trees
**********************************************************************************************
**/
  public static XMLNode[] getLanguages(){
    return languages;
    }


/**
**********************************************************************************************
Gets the names of all loaded languages
@return the language names
**********************************************************************************************
**/
  public static String[] getLanguageNames(){
    String[] names = new String[languages.length];

    try {
      for (int i=0;i<languages.length;i++){
        names[i] = languages[i].getChild("name").getContent();
        }
      }
    catch (Throwable t){
      ErrorLogger.log(t);
      }

    return names;
    }


/**
**********************************************************************************************
Gets all the codes for the loaded language texts
@return the codes
**********************************************************************************************
**/
  public Enumeration getKeys(){
    return messages.keys();
    }


/**
**********************************************************************************************
Gets the translation text for the given <i>key</i> code
@param key the code to get the translation for
@return the translation text
**********************************************************************************************
**/
  public static String get(String key){
    return get(key,true);
    }


/**
**********************************************************************************************
Gets the translation text for the given <i>key</i> code. If <i>showErrorAlert</i> is true, an
error popup will be shown if the <i>key</i> code cannot be found, thus indicating an old or
invalid language file. The error will only be shown once per application load, and only when
a text cannot be found.
@param code the code to get the translation for
@param showErrorAlert whether to show an error popup on failure or not
@return the translation text
**********************************************************************************************
**/
  public static String get(String code, boolean showErrorAlert){

    Object text = (messages.get(code));
    if (text == null){
      String errorCode = "ShowLanguagePackOutdatedError";
      if (Settings.getBoolean("DebugMode")){
        System.out.println("Missing Language: " + code);
        if (Settings.getBoolean("ExtendedLanguageDebug")){
          try {
            throw new Exception("Missing Language");
            }
          catch (Throwable t){
            ErrorLogger.log(t);
            }
          }
        }

      if (showErrorAlert && TemporarySettings.getBoolean(errorCode) && Settings.getBoolean(errorCode)){ // has Settings so that it can be globally turned off
        TemporarySettings.set(errorCode,false);
        WSPopup.showError(errorCode,false);
        }

      return "<" + code + ">";
      }
    else {
      return (String)text;
      }
    }


/**
**********************************************************************************************
Gets the translation text for the given <i>key</i> code
@param key the code to get the translation for
@return the translation text
**********************************************************************************************
**/
  public Object handleGetObject(String key){
    return ((String)messages.get(key)).replaceAll("\\\\n","\n");
    }


/**
**********************************************************************************************
Gets the name of the current language pack
@return the name of the current language
**********************************************************************************************
**/
  public static String getName(){
    try {
      if (language == null){
        return "";
        }
      return language.getChild("name").getContent();
      }
    catch (Exception e){
      logError(e);
      return "";
      }
    }


/**
**********************************************************************************************
Sets the value of a code
@param code the code to set
@param text the text value
**********************************************************************************************
**/
  public static void set(String code, String text){
    messages.put(code,text);
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


  }