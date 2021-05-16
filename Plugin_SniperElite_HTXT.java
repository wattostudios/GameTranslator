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
public class Plugin_SniperElite_HTXT extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_SniperElite_HTXT() {

    super("SniperElite_HTXT","SniperElite_HTXT");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(true);

    setGames("Sniper Elite");
    setExtensions("asr");
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

      // Header
      if (fm.readString(12).equals("Asura   HTXT")){
        rating += 50;
        }

      long arcSize = fm.getLength();

      // Archive Size
      if (fm.readIntL()+24 == arcSize){
        rating += 5;
        }

      // Version
      if (fm.readIntL() == 0){
        rating += 5;
        }

      fm.skip(4);

      // Number Of Text Strings
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

      // 8 - Header 1 (Asura   )
      // 4 - Header 2 (HTXT)
      // 4 - Archive Length [+24]
      // 8 - Version (0)
      fm.skip(24);

      // 4 - Number Of Text Strings
      int numFiles = fm.readIntL();
      check.numFiles(numFiles);

      Resource[] resources = new Resource[numFiles];

      WSProgressDialog.setMaximum(numFiles);

      // Loop through directory
      for(int i=0;i<numFiles;i++){
        // 4 - Unknown
        int hash = fm.readIntL();

        // 4 - String Length [*2 for unicode] (including null terminators)
        long length = fm.readIntL() - 1;
        check.length(length,arcSize);

        // X - Text String (unicode text)
        String original = fm.readUnicodeString((int)length);

        // 2 - null Filename Terminator
        fm.skip(2);

        //path,id,name,offset,length,decompLength,exporter
        resources[i] = new Resource_SniperElite_HTXT(original,hash);

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


      int archiveSize = 20 + (numFiles*10);
      for (int i=0;i<numFiles;i++){
        archiveSize += resources[i].getTranslatedLength();
        }



      // 8 - Header 1 (Asura   )
      // 4 - Header 2 (HTXT)
      fm.writeString("Asura   HTXT");

      // 4 - Archive Length [+24]
      fm.writeIntL((int)archiveSize);

      // 8 - Version (0)
      fm.writeLongL(0);

      // 4 - Number Of Text Strings
      fm.writeIntL(numFiles);


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        int hash = 0;

        if (text instanceof Resource_SniperElite_HTXT){
          Resource_SniperElite_HTXT resource = (Resource_SniperElite_HTXT)text;
          hash = resource.getHash();
          }

        // 4 - Hash
        fm.writeIntL(hash);

        // 4 - String Length [*2 for unicode] (including null terminators)
        fm.writeIntL(translated.length()+1);

        // X - Text String (unicode text)
        // 2 - null Filename Terminator
        fm.writeUnicodeString(translated);
        fm.writeShortL((short)0);

        WSProgressDialog.setValue(i);
        }


      // 16 - null
      for (int i=0;i<16;i++){
        fm.writeByte(0);
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
  return new Resource_SniperElite_HTXT();
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
    columns[numColumns] = new WSTableColumn("Hash",'H',Integer.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_SniperElite_HTXT){
      Resource_SniperElite_HTXT resource = (Resource_SniperElite_HTXT)text;

      if (code == 'H'){
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
    if (text instanceof Resource_SniperElite_HTXT){
      Resource_SniperElite_HTXT resource = (Resource_SniperElite_HTXT)text;

      if (code == 'H'){
        resource.setHash(((Integer)value).intValue());
        return;
        }
      }

    super.setColumnValue(text,code,value);
    }


  }
