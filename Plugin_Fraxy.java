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
public class Plugin_Fraxy extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_Fraxy() {

    super("Fraxy","Fraxy");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("FRAXY");
    setExtensions("flg"); // MUST BE LOWER CASE
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

      // 2 - comment
      if (fm.readString(2).equals("//")){
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


      int numFiles = Archive.getMaxFiles();


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      // read the resources
      int realNumFiles = 0;
      while (fm.getFilePointer() < arcSize){
        String line = fm.readLine();

        if (line.length() <= 0){
          // blank line
          continue;
          }
        else if (line.charAt(0) == '/'){
          // comment
          continue;
          }

        //if ((""+line.charAt(0)).equals("\"")){
        else if (line.charAt(0) == '\"'){
          // header line

          String[] blocks = line.split(",");

          String langName = blocks[1];
          langName = langName.substring(1,langName.length()-1);

          String langNumber = blocks[2];

          String langParts = blocks[3];
          langParts = langParts.substring(1,langParts.length()-1);

          Resource_Property[] properties = new Resource_Property[3];
          properties[0] = new Resource_Property(langName,"Language Name");
          properties[1] = new Resource_Property(langNumber,"Language Number");
          properties[2] = new Resource_Property(langParts,"Parts File");
          setProperties(properties);
          continue;
          }


        int commaPos = line.indexOf(',');
        if (commaPos <= 0){
          // bad line
          continue;
          }

        String code = line.substring(0,commaPos);

        String original = line.substring(commaPos+1);
        original = original.substring(1,original.length()-1);

        int id = Integer.parseInt(code);

        //original,code
        resources[realNumFiles] = new Resource_Fraxy(original,id);
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



      String langName = "English";
      String langNumber = "1";
      String langParts = "parts_eng";

      Resource_Property[] properties = getProperties();
      for (int i=0;i<properties.length;i++){
        String code = properties[i].getProperty();
        String translated = properties[i].getTranslated();

        if (code.equals("Language Name")){
          langName = translated;
          }
        else if (code.equals("Language Number")){
          langNumber = translated;
          }
        else if (code.equals("Parts File")){
          langParts = translated;
          }
        }



      // Header line
      fm.writeString("\"FRAXY_LANGUAGE\",\"" + langName + "\"," + langNumber + ",\"" + langParts + "\"");
      fm.writeByte(13);
      fm.writeByte(10);



      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        int id = 0;

        if (text instanceof Resource_Fraxy){
          Resource_Fraxy resource = (Resource_Fraxy)text;
          id = resource.getID();
          }

        // X - line
        fm.writeString(id + ",\"" + translated + "\"");

        // 2 - new line chars
        fm.writeByte(13);
        fm.writeByte(10);

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
  return new Resource_Fraxy();
  }


/**
**********************************************************************************************
Sets up the default properties
**********************************************************************************************
**/
public void setDefaultProperties(boolean force){
  if (force || properties.length == 0){
    properties = new Resource_Property[3];
    properties[0] = new Resource_Property("Language Name");
    properties[1] = new Resource_Property("Language Number");
    properties[2] = new Resource_Property("Parts File");
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
    if (text instanceof Resource_Fraxy){
      Resource_Fraxy resource = (Resource_Fraxy)text;

      if (code == 'I'){
        return new Integer(resource.getID());
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
      if (text instanceof Resource_Fraxy){
        Resource_Fraxy resource = (Resource_Fraxy)text;

        if (code == 'I'){
          resource.setID(((Integer)value).intValue());
          return;
          }
        }
      }
    catch (Throwable t){
      }

    super.setColumnValue(text,code,value);
    }


  }
