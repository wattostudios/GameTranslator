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
public class Plugin_BrokenSword2 extends ArchivePlugin {

  String filename = "";

/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_BrokenSword2() {

    super("BrokenSword2","Broken Sword 2");

    //           read write rename
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Broken Sword 2");
    setExtensions("");
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

      // 8 - Unknown (8)
      if (fm.readLongL() == 8){
        rating += 5;
        }

      // 2 - null
      if (fm.readShortL() == 0){
        rating += 5;
        }

      fm.skip(33);

      // 1 - null
      if (fm.read() == 0){
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

      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);

      // skip over the offset information
      fm.skip(numFiles*4);

      // read the resources
      for (int i=0;i<numFiles;i++){
        // 2 - ID
        int id = fm.readShortL();

        // X - Text
        // 1 - null Text Terminator
        String original = fm.readNullString();

        //original,id
        resources[i] = new Resource_BrokenSword2(original,id);
        WSProgressDialog.setValue(i);
        }

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


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        int id = 0;

        if (text instanceof Resource_BrokenSword2){
          Resource_BrokenSword2 resource = (Resource_BrokenSword2)text;
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
  return new Resource_BrokenSword2();
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
    if (text instanceof Resource_BrokenSword2){
      Resource_BrokenSword2 resource = (Resource_BrokenSword2)text;

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
    if (text instanceof Resource_BrokenSword2){
      Resource_BrokenSword2 resource = (Resource_BrokenSword2)text;

      if (code == 'I'){
        resource.setID(((Integer)value).intValue());
        return;
        }
      }

    super.setColumnValue(text,code,value);
    }


  }
