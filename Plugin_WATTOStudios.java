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

import org.watto.Language;
import org.watto.component.WSProgressDialog;
import org.watto.component.WSTableColumn;
import org.watto.manipulator.FileBuffer;
import org.watto.manipulator.FileManipulator;
import org.watto.xml.*;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Plugin_WATTOStudios extends ArchivePlugin {

  String langName = "";
  String langVersion = "1.0";
  String langAuthorName = "";
  String langAuthorEmail = "";
  String langAuthorWebsite = "";

/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_WATTOStudios() {

    super("WATTOStudios","WATTO Studios");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(true);

    setGames("Game Extractor",
             "Game Translator",
             "WATTO Studios Programs");
    setExtensions("xml");
    setPlatforms("PC");

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getMatchRating(FileManipulator fm) {
    try {

      int rating = 0;

      if (fm.getExtension().equals(extensions[0])){
        rating += 25;
        }

      // 13 - Heading
      if (fm.readString(14).equals("<languagePack>")){
        // ASCII
        rating += 50;
        }
      else {
        fm.seek(0);
        if (fm.readByteU() == 255 && fm.readByteU() == 254 && fm.readUnicodeString(14).equals("<languagePack>")){
          // UNICODE
          rating += 50;
          }
        else {
          rating = 0;
          }
        }

      return rating;

      }
    catch (Throwable t){
      return 0;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource[] read(File path) {
    try {

      // RESETTING THE GLOBAL VARIABLES
      setDefaultProperties(true);

      XMLNode tree = XMLReader.read(path);


      // get the basic attributes
      langName = tree.getChild("name").getContent();
      langVersion = tree.getChild("version").getContent();

      XMLNode authorNode = tree.getChild("author");

      langAuthorName = authorNode.getChild("name").getContent();
      langAuthorEmail = authorNode.getChild("email").getContent();
      langAuthorWebsite = authorNode.getChild("website").getContent();


      tree = tree.getChild("texts");

      int numFiles = tree.getChildCount();
      check.numFiles(numFiles);

      //Resource[] resources = new Resource[numFiles + 5];
      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);

      /*
      // add the language pack attributes
      resources[0] = new Resource_WATTOStudios(langName,"LanguagePack_Name");
      resources[1] = new Resource_WATTOStudios(langVersion,"LanguagePack_Version");
      resources[2] = new Resource_WATTOStudios(langAuthorName,"LanguagePack_Author_Name");
      resources[3] = new Resource_WATTOStudios(langAuthorEmail,"LanguagePack_Author_Email");
      resources[4] = new Resource_WATTOStudios(langAuthorWebsite,"LanguagePack_Author_Website");
      */


      // add the archive properties
      Resource_Property[] properties = new Resource_Property[5];
      properties[0] = new Resource_Property(langName,"Language Name");
      properties[1] = new Resource_Property(langVersion,"Version");
      properties[2] = new Resource_Property(langAuthorName,"Author Name");
      properties[3] = new Resource_Property(langAuthorEmail,"Email Address");
      properties[4] = new Resource_Property(langAuthorWebsite,"Website");
      setProperties(properties);


      // add the texts
      //for (int i=5,j=0;j<numFiles;i++,j++){
      //  XMLNode text = tree.getChild(j);
      for (int i=0;i<numFiles;i++){
        XMLNode text = tree.getChild(i);

        String code = text.getAttribute("code");
        String original = text.getAttribute("value");

        //original,code
        resources[i] = new Resource_WATTOStudios(original,code);
        WSProgressDialog.setValue(i);
        }


      return resources;

      }
    catch (Throwable t){
      logError(t);
      return null;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void write(Resource[] resources, File path) {
    try {


      path = FileBuffer.makeValidFilename(path);

      XMLNode root = new XMLNode("languagePack");

      XMLNode nameNode = new XMLNode("name");
      XMLNode versionNode = new XMLNode("version");
      XMLNode authorNode = new XMLNode("author");

      XMLNode tree = new XMLNode("texts");


      int numFiles = resources.length;
      WSProgressDialog.setMaximum(numFiles);

      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));


      // get the archive properties
      Resource_Property[] properties = getProperties();
      for (int i=0;i<properties.length;i++){
        String code = properties[i].getProperty();
        String translated = properties[i].getTranslated();

        if (code.equals("Language Name")){
          nameNode.setContent(translated);
          }
        else if (code.equals("Version")){
          versionNode.setContent(translated);
          }
        else if (code.equals("Author Name")){
          authorNode.addChild(new XMLNode("name",translated));
          }
        else if (code.equals("Email Address")){
          authorNode.addChild(new XMLNode("email",translated));
          }
        else if (code.equals("Website")){
          authorNode.addChild(new XMLNode("website",translated));
          }
        }



      // Write the strings
      //int numFound = 5;
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        String code = "";

        if (text instanceof Resource_WATTOStudios){
          Resource_WATTOStudios resource = (Resource_WATTOStudios)text;
          code = resource.getCode();
          }

        /*
        if (numFound != 0){
          if (code.equals("LanguagePack_Name")){
            nameNode.setContent(translated);
            numFound--;
            continue;
            }
          else if (code.equals("LanguagePack_Version")){
            versionNode.setContent(translated);
            numFound--;
            continue;
            }
          else if (code.equals("LanguagePack_Author_Name")){
            authorNode.addChild(new XMLNode("name",translated));
            numFound--;
            continue;
            }
          else if (code.equals("LanguagePack_Author_Email")){
            authorNode.addChild(new XMLNode("email",translated));
            numFound--;
            continue;
            }
          else if (code.equals("LanguagePack_Author_Website")){
            authorNode.addChild(new XMLNode("website",translated));
            numFound--;
            continue;
            }
          }
        */

        XMLNode textNode = new XMLNode("lang");
        textNode.addAttribute("code",code);
        textNode.addAttribute("value",translated);
        tree.addChild(textNode);

        WSProgressDialog.setValue(i);
        }



      root.addChild(nameNode);
      root.addChild(versionNode);
      root.addChild(authorNode);

      root.addChild(tree);


      // SAVE THE FILE HERE - IN UNICODE (true)!
      XMLWriter.write(path,root,true);


      }
    catch (Throwable t){
      logError(t);
      }
    }



/**
**********************************************************************************************
Gets a blank resource of this type, for use when adding resources
**********************************************************************************************
**/
public Resource getBlankResource(){
  return new Resource_WATTOStudios();
  }


/**
**********************************************************************************************
Sets up the default properties
**********************************************************************************************
**/
public void setDefaultProperties(boolean force){
  if (force || properties.length == 0){
    properties = new Resource_Property[5];
    properties[0] = new Resource_Property("Language Name");
    properties[1] = new Resource_Property("Version");
    properties[2] = new Resource_Property("Author Name");
    properties[3] = new Resource_Property("Email Address");
    properties[4] = new Resource_Property("Website");
    }
  }


/**
**********************************************************************************************
Gets all the columns
**********************************************************************************************
**/
  public WSTableColumn[] getColumns() {
    WSTableColumn[] baseColumns = super.getColumns();
    int numColumns = baseColumns.length;

    // copy the base columns into a new array
    WSTableColumn[] columns = new WSTableColumn[numColumns+1];
    System.arraycopy(baseColumns,0,columns,0,numColumns);

    // add the additional columns...

    //code,languageCode,class,editable,sortable
    columns[numColumns] = new WSTableColumn("Code",'c',String.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_WATTOStudios){
      Resource_WATTOStudios resource = (Resource_WATTOStudios)text;

      if (code == 'c'){
        return resource.getCode();
        }
      }

    return super.getColumnValue(text,code);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColumnValue(Resource text, char code, Object value) {
    if (text instanceof Resource_WATTOStudios){
      Resource_WATTOStudios resource = (Resource_WATTOStudios)text;

      if (code == 'c'){
        resource.setCode((String)value);
        return;
        }
      }

    super.setColumnValue(text,code,value);
    }


  }
