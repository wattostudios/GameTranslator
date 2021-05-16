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
public class Plugin_AttackOnPearlHarbor_Language extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_AttackOnPearlHarbor_Language() {

    super("AttackOnPearlHarbor_Language","AttackOnPearlHarbor_Language");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(true);

    setGames("Attack On Pearl Harbor");
    setExtensions(".ini");
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

      if (fm.getFile().getName().equalsIgnoreCase("language.ini")){
        rating += 25;
        }
      else {
        rating = 0;
        }

      if (fm.readByteU() == 255 && fm.readByteU() == 254){
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


      // 2 - Unicode Header
      fm.skip(2);


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);

      int realNumFiles = 0;

      // read the resources
      while (fm.getFilePointer() < arcSize){
        // Format...
        // * textID Value *
        String line = fm.readUnicodeLine();

        if (line.length() <= 2){
          // blank line
          continue;
          }
        else if (line.charAt(0) != '*'){
          // not a translation
          continue;
          }

        int secondStarPos = line.indexOf(" *");
        if (secondStarPos <= 0){
          // bad line - no closing * character
          continue;
          }

        line = line.substring(2,secondStarPos);

        int spacePos = line.indexOf(" ");
        if (spacePos <= 0){
          // bad line - no space between ID and translation
          continue;
          }

        int id = 0;
        try {
          id = Integer.parseInt(line.substring(0,spacePos));
          }
        catch (Throwable t){
          // non-numeric ID number
          continue;
          }

        line = line.substring(spacePos+1);


        //original,id
        resources[realNumFiles] = new Resource_AttackOnPearlHarbor_Language(line,id);
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



      // 2 - Unicode Header
      fm.writeByte(255);
      fm.writeByte(254);


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        int id = 0;

        if (text instanceof Resource_AttackOnPearlHarbor_Language){
          Resource_AttackOnPearlHarbor_Language resource = (Resource_AttackOnPearlHarbor_Language)text;
          id = resource.getID();
          }

        // Format...
        // * textID Value *

        fm.writeUnicodeString("* " + id + " " + translated + " *");

        // new line char
        fm.writeByte(13);
        fm.writeByte(0);
        fm.writeByte(10);
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
  return new Resource_AttackOnPearlHarbor_Language();
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
    if (text instanceof Resource_AttackOnPearlHarbor_Language){
      Resource_AttackOnPearlHarbor_Language resource = (Resource_AttackOnPearlHarbor_Language)text;

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
      if (text instanceof Resource_AttackOnPearlHarbor_Language){
        Resource_AttackOnPearlHarbor_Language resource = (Resource_AttackOnPearlHarbor_Language)text;

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
