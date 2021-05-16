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
import org.watto.manipulator.FileManipulator;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Plugin_XXX extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_XXX() {

    super("XXX","XXX");

    //           read write replace
    setProperties(true,false,false);
    setAllowsUnicode(false);

    setGames("");
    setExtensions(""); // MUST BE LOWER CASE
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

      if (fm.getFile().getName().equals("strings.inf")){
        rating += 25;
        }

      // 4 - Unknown (8)
      if (fm.readIntL() == 8){
        rating += 5;
        }

      // 4 - numFiles
      if (check.numFiles(fm.readIntL())){
        rating += 5;
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

      FileManipulator fm = new FileManipulator(path,"r");
      long arcSize = fm.getLength();

      // 8 - Unknown (8)
      // 2 - null
      fm.skip(10);

      // 34 - Filename (null)
      filename = fm.readNullString(34);

      // 4 - Number Of Text Strings
      int numFiles = fm.readIntL();
      check.numFiles(numFiles);



      Resource_Property[] properties = new Resource_Property[5];
      properties[0] = new Resource_Property(langName,"Language Name");
      properties[1] = new Resource_Property(langVersion,"Version");
      properties[2] = new Resource_Property(langAuthorName,"Author Name");
      properties[3] = new Resource_Property(langAuthorEmail,"Email Address");
      properties[4] = new Resource_Property(langAuthorWebsite,"Website");
      setProperties(properties);



      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);



      // read the resources
      for (int i=0;i<numFiles;i++){
        // 2 - ID
        int id = fm.readShortL();

        // X - Text
        // 1 - null Text Terminator
        String original = fm.readNullString();

        //original,code
        resources[i] = new Resource_XXX(original,code);
        WSProgressDialog.setValue(i);
        }



      // read the resources
      int realNumFiles = 0;
      while (fm.getFilePointer() < arcSize){
        String line = fm.readLine();

        if (line.length() <= 0){
          // blank line
          continue;
          }
        else if (line.charAt(0) == '#'){
          // comment
          continue;
          }

        int equalPos = line.indexOf('=');
        if (equalPos <= 0){
          // bad line
          continue;
          }

        String code = line.substring(0,equalPos);
        String original = line.substring(equalPos+1);

        //original,code
        resources[realNumFiles] = new Resource_XXX(original,code);
        WSProgressDialog.setValue(realNumFiles);
        realNumFiles++;
        }



      fm.close();

      resources = resizeResources(resources,realNumFiles);

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

      FileManipulator fm = new FileManipulator(path,"rw");
      int numFiles = resources.length;

      WSProgressDialog.setMaximum(numFiles);


      // 8 - Unknown (8)
      fm.writeLongL(8);

      // 2 - null
      fm.writeShortL((short)0);

      // 34 - Filename (null)
      if (filename.equals("")){
        filename = path.getName();
        }
      fm.writeNullString(filename,34);

      // 4 - Number Of Text Strings
      fm.writeIntL(numFiles);


      WSProgressDialog.setMessage(Language.get("Progress_WritingDirectory"));

      // write the offsets
      int offset = 48 + numFiles*4;
      for (int i=0;i<numFiles;i++){
        // 4 - String Offset
        fm.writeIntL(offset);

        offset += resources[i].getTranslatedLength() + 3; // +1 for the null terminator, +2 for the ID
        }



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



      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        int id = 0;

        if (text instanceof Resource_XXX){
          Resource_XXX resource = (Resource_XXX)text;
          id = resource.getID();
          }

        // 2 - String ID
        fm.writeShortL((short)id);

        // X - String Data
        fm.writeString(translated);

        // 1 - null String Data Terminator
        fm.writeByte(0);

        WSProgressDialog.setValue(i);
        }

      fm.close();

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
  return new Resource_XXX();
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
    columns[numColumns] = new WSTableColumn("ID",'I',Integer.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_XXX){
      Resource_XXX resource = (Resource_XXX)text;

      if (code == 'I'){
        return new Integer(resource.getID());
        }
      else if (code == 'C'){
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
    try {
      if (text instanceof Resource_XXX){
        Resource_XXX resource = (Resource_XXX)text;

        if (code == 'I'){
          resource.setID(((Integer)value).intValue());
          return;
          }
        else if (code == 'C'){
          resource.setCode((String)value);
          return;
          }
        }
      }
    catch (Throwable t){
      }

    super.setColumnValue(text,code,value);
    }


  }
