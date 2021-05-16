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
public class Plugin_XMenTheOfficialGame extends ArchivePlugin {

  String filename = "";

/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_XMenTheOfficialGame() {

    super("XMenTheOfficialGame","XMenTheOfficialGame");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("X-Men: The Official Game");
    setExtensions("zst");
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

      // 4 - Header (ZSTR)
      if (fm.readString(4).equals("ZSTR")){
        rating += 50;
        }

      // 4 - Version (1)
      if (fm.readIntL() == 1){
        rating += 5;
        }

      // 4 - Archive Length
      if (fm.readIntL() == fm.length()){
        rating += 5;
        }

      // 4 - Number Of Text Strings
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


      // 4 - Header (ZSTR)
      // 4 - Version (1)
      // 4 - Archive Length
      fm.skip(12);

      // 4 - Number Of Text Strings
      int numFiles = fm.readIntL();
      check.numFiles(numFiles);


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);

      // read the hashes
      int[] hashes = new int[numFiles];
      int[] ids = new int[numFiles];
      for (int i=0;i<numFiles;i++){
        // 4 - Hash?
        hashes[i] = fm.readIntL();

        // 4 - Text ID?
        ids[i] = fm.readIntL();
        }


      // read the offsets
      int[] offsets = new int[numFiles];
      for (int i=0;i<numFiles;i++){
        // 4 - Text Offset
        int offset = fm.readIntL();
        check.offset(offset,arcSize);
        offsets[i] = offset;
        }


      // X - null padding to a multiple of 16? bytes


      // read the resources
      for (int i=0;i<numFiles;i++){
        fm.seek(offsets[i]);

        // X - Text
        // 1 - null Text Terminator
        String original = fm.readNullString();

        //original,id
        resources[i] = new Resource_XMenTheOfficialGame(original,ids[i],hashes[i]);
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



      int textOffset = 16 + (numFiles*12);
      int paddingSize = 16 - (textOffset%16);
      if (paddingSize == 16){
        paddingSize = 0;
        }
      textOffset += paddingSize;

      int arcSize = textOffset;

      for (int i=0;i<numFiles;i++){
        arcSize += resources[i].getTranslatedLength() + 1;
        }




      // 4 - Header (ZSTR)
      fm.writeString("ZSTR");

      // 4 - Version (1)
      fm.writeInt(1);

      // 4 - Archive Length
      fm.writeInt(arcSize);

      // 4 - Number Of Text Strings
      fm.writeInt(numFiles);



      WSProgressDialog.setMessage(Language.get("Progress_WritingDirectory"));

      // write the hashes
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        int id = 0;
        int hash = 0;

        if (text instanceof Resource_XMenTheOfficialGame){
          Resource_XMenTheOfficialGame resource = (Resource_XMenTheOfficialGame)text;
          id = resource.getID();
          hash = resource.getHash();
          }

        // 4 - Hash?
        fm.writeIntL(hash);

        // 4 - Text ID?
        fm.writeIntL(id);
        }


      // write the offsets
      int offset = textOffset;
      for (int i=0;i<numFiles;i++){
        // 4 - String Offset
        fm.writeIntL(offset);

        offset += resources[i].getTranslatedLength() + 1; // +1 for the null terminator
        }


      // X - null padding to a multiple of 16? bytes
      for (int i=0;i<paddingSize;i++){
        fm.writeByte(0);
        }


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];
        String translated = text.getTranslated();

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
  return new Resource_XMenTheOfficialGame();
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
    columns[numColumns] = new WSTableColumn("ID"  ,'I',Integer.class,true,true);
    columns[numColumns+1] = new WSTableColumn("Hash",'H',Integer.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_XMenTheOfficialGame){
      Resource_XMenTheOfficialGame resource = (Resource_XMenTheOfficialGame)text;

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
    if (text instanceof Resource_XMenTheOfficialGame){
      Resource_XMenTheOfficialGame resource = (Resource_XMenTheOfficialGame)text;

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
