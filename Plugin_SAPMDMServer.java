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
public class Plugin_SAPMDMServer extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_SAPMDMServer() {

    super("SAPMDMServer","SAP MDM Server");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("SAP MDM Server");
    setExtensions("lang");
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

      String line = fm.readLine();
      int tabPos = line.indexOf('\t');

      if (tabPos > 0){
        rating += 5;
        }

      Integer.parseInt(line.substring(0,tabPos));
      rating += 5;


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


      // 17 382 interface
      String header = fm.readLine();

      int tabPos = header.indexOf('\t');

      String packID = header.substring(0,tabPos);


      Resource_Property[] properties = new Resource_Property[1];
      properties[0] = new Resource_Property(packID,"Language Pack ID");
      setProperties(properties);



      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      // read the resources
      int realNumFiles = 0;
      while (fm.getFilePointer() < arcSize){
        // 17 eng SYNI_BUT_Cancel 0 "Cancel"
        String line = fm.readLine();
        if (line == null || line.length() <= 0){
          // blank line at end of file
          continue;
          }

        // Pack ID - skip the pack ID number
        tabPos = line.indexOf('\t');
        line = line.substring(0,tabPos+1);

        // Language Code
        tabPos = line.indexOf('\t');
        String languageCode = line.substring(0,tabPos);
        line = line.substring(0,tabPos+1);

        // Code
        tabPos = line.indexOf('\t');
        String code = line.substring(0,tabPos);
        line = line.substring(0,tabPos+1);

        // ID
        tabPos = line.indexOf('\t');
        int id = Integer.parseInt(line.substring(0,tabPos));
        line = line.substring(0,tabPos+1);

        // Original
        String original = line.substring(1,line.length()-2);

        //original,id
        resources[realNumFiles] = new Resource_SAPMDMServer(original,languageCode,code,id);
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


      // get the pack ID
      int packID = 0;
      Resource_Property[] properties = getProperties();
      for (int i=0;i<properties.length;i++){
        String code = properties[i].getProperty();
        String translated = properties[i].getTranslated();

        if (code.equals("Language Pack ID")){
          packID = Integer.parseInt(translated);
          }
        }


      // go through and determine the number of languages, and then the number of files
      int numLangCodes = 0;
      int currentID = 0;
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];
        if (text instanceof Resource_SAPMDMServer){
          if (((Resource_SAPMDMServer)text).getID() == currentID){
            numLangCodes++;
            }
          else {
            break;
            }
          }
        }

      int numTexts = numFiles / numLangCodes;



      // first line - 17  382 interface
      fm.writeString(packID + "\t" + numTexts + "\tinterfaces");
      fm.writeByte(13);
      fm.writeByte(10);



      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        int id = 0;
        String code = "";
        String languageCode = "";

        if (text instanceof Resource_SAPMDMServer){
          Resource_SAPMDMServer resource = (Resource_SAPMDMServer)text;
          id = resource.getID();
          code = resource.getCode();
          languageCode = resource.getLanguageCode();
          }

        // lines - 17 eng SYNI_BUT_Cancel 0 "Cancel"
        fm.writeString(packID + "\t" + languageCode + "\t" + code + "\t" + id + "\t\"" + translated + "\"");
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
  return new Resource_SAPMDMServer();
  }


/**
**********************************************************************************************
Sets up the default properties
**********************************************************************************************
**/
public void setDefaultProperties(boolean force){
  if (force || properties.length == 0){
    properties = new Resource_Property[1];
    properties[0] = new Resource_Property("Language Pack ID");
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
    WSTableColumn[] columns = new WSTableColumn[numColumns+3];
    System.arraycopy(baseColumns,0,columns,0,numColumns);

    // add the additional columns...

    //code,languageCode,class,editable,sortable
    columns[numColumns] = new WSTableColumn("Language Code",'L',String.class,true,true);
    columns[numColumns+1] = new WSTableColumn("Code",'C',String.class,true,true);
    columns[numColumns+2] = new WSTableColumn("ID",'I',Integer.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_SAPMDMServer){
      Resource_SAPMDMServer resource = (Resource_SAPMDMServer)text;

      if (code == 'I'){
        return new Integer(resource.getID());
        }
      else if (code == 'C'){
        return resource.getCode();
        }
      else if (code == 'L'){
        return resource.getLanguageCode();
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
      if (text instanceof Resource_SAPMDMServer){
        Resource_SAPMDMServer resource = (Resource_SAPMDMServer)text;

        if (code == 'I'){
          resource.setID(((Integer)value).intValue());
          return;
          }
        else if (code == 'C'){
          resource.setCode((String)value);
          return;
          }
        else if (code == 'L'){
          resource.setLanguageCode((String)value);
          return;
          }

        }
      }
    catch (Throwable t){
      }

    super.setColumnValue(text,code,value);
    }


  }
