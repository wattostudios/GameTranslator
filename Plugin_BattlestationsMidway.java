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
public class Plugin_BattlestationsMidway extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_BattlestationsMidway() {

    super("BattlestationsMidway","BattlestationsMidway");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(true);

    setGames("Battlestations Midway");
    setExtensions("lan");
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
      char unicodeTerminator = (char)2560;
      int realNumFiles = 0;
      while (fm.getFilePointer() < arcSize){
        // X - Type String (ASCII)
        // 1 - Type String Terminator (32)
        String type = fm.readTerminatedString((byte)32);

        // X - Code String (ASCII)
        // 1 - Code String Terminator (32)
        String code = fm.readTerminatedString((byte)32);

        // X - Text String (Unicode)
        // 2 - Text String Terminator (0,10) = (2560)
        String original = new String(new byte[0],"UTF-16LE");
        char nextChar = fm.readChar();
        while (nextChar != unicodeTerminator && fm.getFilePointer() < arcSize){
          original += nextChar;
          nextChar = fm.readChar();
          }

        //original,type,code
        resources[realNumFiles] = new Resource_BattlestationsMidway(original,type,code);
        WSProgressDialog.setValue(realNumFiles);
        realNumFiles++;

        // 2 - end of line (0,13)
        fm.skip(2);
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


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        String type = "";
        String code = "";

        if (text instanceof Resource_BattlestationsMidway){
          Resource_BattlestationsMidway resource = (Resource_BattlestationsMidway)text;
          type = resource.getType();
          code = resource.getCode();
          }

        // X - Type String (ASCII)
        // 1 - Type String Terminator (32)
        fm.writeString(type);
        fm.writeByte((byte)32);

        // X - Code String (ASCII)
        // 1 - Code String Terminator (32)
        fm.writeString(code);
        fm.writeByte((byte)32);

        // X - Text String (Unicode)
        // 2 - Text String Terminator (0,10) = (2560)
        fm.writeUnicodeString(translated);
        fm.writeByte((byte)0);
        fm.writeByte((byte)10);
        fm.writeByte((byte)0);
        fm.writeByte((byte)13);

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
  return new Resource_BattlestationsMidway();
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
    columns[numColumns] = new WSTableColumn("Type",'Y',String.class,true,true);
    columns[numColumns+1] = new WSTableColumn("Code",'C',String.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_BattlestationsMidway){
      Resource_BattlestationsMidway resource = (Resource_BattlestationsMidway)text;

      if (code == 'Y'){
        return resource.getType();
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
    if (text instanceof Resource_BattlestationsMidway){
      Resource_BattlestationsMidway resource = (Resource_BattlestationsMidway)text;

      if (code == 'Y'){
        resource.setType(value.toString());
        return;
        }
      else if (code == 'C'){
        resource.setCode(value.toString());
        return;
        }
      }

    super.setColumnValue(text,code,value);
    }


  }
