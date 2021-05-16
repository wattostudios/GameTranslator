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
public class Plugin_AutoAssault extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_AutoAssault() {

    super("AutoAssault","AutoAssault");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Auto Assault");
    setExtensions("pak");
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

      // 4 - numFiles
      if (check.numFiles(fm.readIntL())){
        rating += 5;
        }

      fm.skip(4);

      int arcSize = (int)fm.length();

      // 4 - first file offset
      if (check.offset(fm.readIntL(),arcSize)){
        rating += 5;
        }

      // 4 - first file length
      if (check.length(fm.readIntL(),arcSize)){
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


      // 4 - Number Of Text Strings
      int numFiles = fm.readIntL();
      check.numFiles(numFiles);

      int relOffset = 4 + (numFiles*20);


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      // read the resources
      int[] hashes  = new int[numFiles];
      int[] ids     = new int[numFiles];
      int[] offsets = new int[numFiles];
      int[] lengths = new int[numFiles];

      for (int i=0;i<numFiles;i++){
        // 4 - Hash
        int hash = fm.readIntL();

        // 4 - Offset
        int offset = fm.readIntL() + relOffset;
        check.offset(offset,arcSize);

        // 4 - Length
        int length = fm.readIntL();
        check.length(length,arcSize);

        // 4 - ID? (can be -1)
        int id = fm.readIntL();

        // 4 - Unknown
        fm.skip(4);

        hashes[i]  = hash;
        ids[i]     = id;
        offsets[i] = offset;
        lengths[i] = length;
        WSProgressDialog.setValue(i);
        }


      for (int i=0;i<numFiles;i++){
        fm.seek(offsets[i]);

        // X - Text
        String original = fm.readString(lengths[i]);

        //original,id
        resources[i] = new Resource_AutoAssault(original,ids[i],hashes[i]);
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



      // 4 - Number Of Text Strings
      fm.writeIntL(numFiles);


      WSProgressDialog.setMessage(Language.get("Progress_WritingDirectory"));

      // write the offsets
      int offset = 0;
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        int length = text.getTranslatedLength();
        int id = -1;
        int hash = 0;

        if (text instanceof Resource_AutoAssault){
          Resource_AutoAssault resource = (Resource_AutoAssault)text;
          id = resource.getID();
          hash = resource.getHash();
          }


        // 4 - Hash
        fm.writeIntL(hash);

        // 4 - Offset
        fm.writeIntL(offset);

        // 4 - Length
        fm.writeIntL(length);

        // 4 - ID? (can be -1)
        fm.writeIntL(id);

        // 4 - Unknown
        fm.writeIntL(1);


        offset += length;
        WSProgressDialog.setValue(i);
        }



      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        String translated = resources[i].getTranslated();
        // X - String Data
        fm.writeString(translated);
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
  return new Resource_AutoAssault();
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
    columns[numColumns]   = new WSTableColumn("ID",  'I',Integer.class,true,true);
    columns[numColumns+1] = new WSTableColumn("Hash",'H',Integer.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_AutoAssault){
      Resource_AutoAssault resource = (Resource_AutoAssault)text;

      if (code == 'I'){
        return new Integer(resource.getID());
        }
      else if (code == 'H'){
        return new Integer(resource.getHash());
        }
      }

    return super.getColumnValue(text,code);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColumnValue(Resource text, char code, Object value) {
    if (text instanceof Resource_AutoAssault){
      Resource_AutoAssault resource = (Resource_AutoAssault)text;

      if (code == 'I'){
        resource.setID(((Integer)value).intValue());
        return;
        }
      else if (code == 'H'){
        resource.setHash(((Integer)value).intValue());
        return;
        }

      }

    super.setColumnValue(text,code,value);
    }


  }
