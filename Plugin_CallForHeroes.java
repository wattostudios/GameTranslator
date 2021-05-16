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
public class Plugin_CallForHeroes extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_CallForHeroes() {

    super("CallForHeroes","CallForHeroes");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Call For Heroes: Pompolic Wars");
    setExtensions("lng");
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


      if (fm.readString(10).equals("Language=\"")){
        rating += 25;
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



      // First Line - Language="English"
      String langName = fm.readLine();
      langName = langName.substring(10,langName.length()-1);




      Resource_Property[] properties = new Resource_Property[1];
      properties[0] = new Resource_Property(langName,"Language Name");
      setProperties(properties);



      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      // read the resources
      int realNumFiles = 0;
      String group = "";
      while (fm.getFilePointer() < arcSize){
        String line = fm.readLine();
        if (line == null || line.length() <= 0){
          // blank line
          continue;
          }

        if (line.equals("END_GROUP")){
          group = "";
          }

        int startPos = line.indexOf("\"");
        if (startPos <= 0){
          // bad line
          continue;
          }
        int endPos = line.lastIndexOf("\"");
        if (endPos == startPos){
          // bad line
          continue;
          }

        if (line.indexOf("BEGIN_GROUP") == 0){
          group = line.substring(startPos+1,endPos);
          continue;
          }

        String code = line.substring(0,startPos-1);
        line = line.substring(startPos+1,endPos);


        resources[realNumFiles] = new Resource_CallForHeroes(line,code,group);
        WSProgressDialog.setValue(realNumFiles);

        realNumFiles++;
        }


      resources = resizeResources(resources,realNumFiles);


      fm.close();

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


      Resource_Property[] properties = getProperties();
      for (int i=0;i<properties.length;i++){
        String code = properties[i].getProperty();
        String translated = properties[i].getTranslated();

        if (code.equals("Language Name")){
          // Language="English"

          fm.writeString("Language=\"" + translated + "\"");
          fm.writeByte(13);
          fm.writeByte(10);

          // blank line
          fm.writeByte(13);
          fm.writeByte(10);
          }
        }


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      String currentGroup = "";
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        String code = "";
        String group = "";

        if (text instanceof Resource_CallForHeroes){
          Resource_CallForHeroes resource = (Resource_CallForHeroes)text;
          code = resource.getCode();
          group = resource.getGroup();
          }

        if (!group.equals(currentGroup)){
          if (! group.equals("")){
            // END_GROUP
            fm.writeString("END_GROUP");
            fm.writeByte(13);
            fm.writeByte(10);

            // blank line
            fm.writeByte(13);
            fm.writeByte(10);
            }

          // BEGIN_GROUP="General"
          fm.writeString("BEGIN_GROUP=\"" + group + "\"");
          fm.writeByte(13);
          fm.writeByte(10);

          currentGroup = group;
          }

        // Code="Value"
        fm.writeString(code + "=\"" + translated + "\"");
        fm.writeByte(13);
        fm.writeByte(10);

        WSProgressDialog.setValue(i);
        }


      if (! currentGroup.equals("")){
        // END_GROUP
        fm.writeString("END_GROUP");
        fm.writeByte(13);
        fm.writeByte(10);
        }

      // blank line
      fm.writeByte(13);
      fm.writeByte(10);

      // END_LANGUAGE
      fm.writeString("END_LANGUAGE");


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
  return new Resource_CallForHeroes();
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
    WSTableColumn[] columns = new WSTableColumn[numColumns+2];
    System.arraycopy(baseColumns,0,columns,0,numColumns);

    // add the additional columns...

    //code,languageCode,class,editable,sortable
    columns[numColumns] = new WSTableColumn("Code",'C',String.class,true,true);
    columns[numColumns+1] = new WSTableColumn("Group",'G',String.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_CallForHeroes){
      Resource_CallForHeroes resource = (Resource_CallForHeroes)text;

      if (code == 'C'){
        return resource.getCode();
        }
      else if (code == 'G'){
        return resource.getGroup();
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
      if (text instanceof Resource_CallForHeroes){
        Resource_CallForHeroes resource = (Resource_CallForHeroes)text;

        if (code == 'C'){
          resource.setCode((String)value);
          return;
          }
        else if (code == 'G'){
          resource.setGroup((String)value);
          return;
          }
        }
      }
    catch (Throwable t){
      }

    super.setColumnValue(text,code,value);
    }


  }
