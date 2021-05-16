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
public class Plugin_FarWest extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_FarWest() {

    super("FarWest","FarWest");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Far West");
    setExtensions("dat"); // MUST BE LOWER CASE
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

      // 11 - language header
      if (fm.readString(11).equals("Language = ")){
        rating += 50;
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

      // first line (Language = 0; "English";)
      String line = fm.readLine();

      int quotePos1 = line.indexOf("\"");
      int quotePos2 = line.lastIndexOf("\"");

      String langName = line.substring(quotePos1+1,quotePos2);

      int equalPos = line.indexOf("=");
      int semiPos = line.indexOf(";");

      String langID = line.substring(equalPos+2,semiPos);



      Resource_Property[] properties = new Resource_Property[2];
      properties[0] = new Resource_Property(langName,"Language Name");
      properties[1] = new Resource_Property(langID,"Language ID");
      setProperties(properties);


      int numFiles = Archive.getMaxFiles();

      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);



      // read the resources
      int realNumFiles = 0;
      while (fm.getFilePointer() < arcSize){
        // line (id; "language"; "Value";)
        line = fm.readLine();

        if (line.length() <= 0){
          // blank line
          continue;
          }
        else if (line.charAt(0) == '/'){
          // comment
          continue;
          }

        int semiPos1 = line.indexOf(";");
            quotePos1 = line.indexOf("\"");
            quotePos2 = line.indexOf("\"",quotePos1+1);
        int quotePos3 = line.indexOf("\"",quotePos2+1);
        int quotePos4 = line.indexOf("\"",quotePos3+1);

        if (quotePos1 <= 0 || quotePos2 <= 0 || quotePos3 <= 0 || quotePos4 <= 0 || semiPos1 <= 0){
          // bad line
          continue;
          }

        int id = Integer.parseInt(line.substring(0,semiPos1));
        String original = line.substring(quotePos3+1,quotePos4);

        //original,code
        resources[realNumFiles] = new Resource_FarWest(original,id);
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


      WSProgressDialog.setMessage(Language.get("Progress_WritingDirectory"));



      String langName = "English";
      String langID = "0";

      Resource_Property[] properties = getProperties();
      for (int i=0;i<properties.length;i++){
        String code = properties[i].getProperty();
        String translated = properties[i].getTranslated();

        if (code.equals("Language Name")){
          langName = translated;
          }
        else if (code.equals("Language ID")){
          langID = translated;
          }
        }



      // First Line (Language = 0; "English";)
      fm.writeString("Language = " + langID + "; \"" + langName + "\";");
      fm.writeByte(13);
      fm.writeByte(10);



      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        int id = 0;

        if (text instanceof Resource_FarWest){
          Resource_FarWest resource = (Resource_FarWest)text;
          id = resource.getID();
          }

        // Line (id; "language"; "Value";)
        fm.writeString(id + "; \"" + langName + "\"; \"" + translated + "\";");
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
  return new Resource_FarWest();
  }


/**
**********************************************************************************************
Sets up the default properties
**********************************************************************************************
**/
public void setDefaultProperties(boolean force){
  if (force || properties.length == 0){
    properties = new Resource_Property[2];
    properties[0] = new Resource_Property("Language Name");
    properties[1] = new Resource_Property("Language ID");
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
    if (text instanceof Resource_FarWest){
      Resource_FarWest resource = (Resource_FarWest)text;

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
      if (text instanceof Resource_FarWest){
        Resource_FarWest resource = (Resource_FarWest)text;

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
