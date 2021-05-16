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
public class Plugin_SpaceStrike extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_SpaceStrike() {

    super("SpaceStrike","SpaceStrike");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(true);

    setGames("Space Strike");
    setExtensions("txt");
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

      if (fm.getFile().getName().equals("local.txt")){
        rating += 25;
        }

      // 2 - unicode header
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


      // 2 - Unicode Header
      fm.skip(2);

      int numFiles = Archive.getMaxFiles();



      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      // read the resources
      int realNumFiles = 0;
      while (fm.getFilePointer() < arcSize){
        String line = fm.readUnicodeLine();

        if (line.length() == 0){
          // blank line
          }
        else if (line.length() > 2 && line.substring(0,2).equals("//")){
          // comment
          }
        else {
          String[] parts = line.split("\t");

          String code = "";
          String comment = "";
          String original = "";

          if (parts.length >= 1){
            code = parts[0];
            }
          if (parts.length >= 2){
            original = parts[1];
            }
          if (parts.length >= 4){
            comment = parts[3];
            }

          //original,code,comment
          resources[realNumFiles] = new Resource_SpaceStrike(original,code,comment);
          WSProgressDialog.setValue(realNumFiles);
          realNumFiles++;
          }

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


      // 2 - Unicode Header
      fm.writeByte((byte)255);
      fm.writeByte((byte)254);


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        String original = text.getOriginal();
        String code = "";
        String comment = "";

        if (text instanceof Resource_SpaceStrike){
          Resource_SpaceStrike resource = (Resource_SpaceStrike)text;
          code = resource.getCode();
          comment = resource.getComment();
          }

        // line
        fm.writeUnicodeString(code + "\t" + original + "\t" + translated + "\t" + comment);

        // new line character
        fm.writeByte((byte)13);
        fm.writeByte((byte)0);
        fm.writeByte((byte)10);
        fm.writeByte((byte)0);

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
  return new Resource_SpaceStrike();
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
    columns[numColumns+1] = new WSTableColumn("Comment",'c',String.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_SpaceStrike){
      Resource_SpaceStrike resource = (Resource_SpaceStrike)text;

      if (code == 'C'){
        return resource.getCode();
        }
      else if (code == 'c'){
        return resource.getComment();
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
      if (text instanceof Resource_SpaceStrike){
        Resource_SpaceStrike resource = (Resource_SpaceStrike)text;

        if (code == 'C'){
          resource.setCode((String)value);
          return;
          }
        else if (code == 'c'){
          resource.setComment((String)value);
          return;
          }
        }
      }
    catch (Throwable t){
      }

    super.setColumnValue(text,code,value);
    }


  }
