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
public class Plugin_AnkhHeartOfOsiris extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_AnkhHeartOfOsiris() {

    super("AnkhHeartOfOsiris","AnkhHeartOfOsiris");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Ankh: Heart Of Osiris");
    setExtensions("bin");
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

      // 4 - Header Length(21)
      if (fm.readIntB() == 21){
        rating += 5;
        }

      // 21 - Header (PinaStringTable-0.1.0)
      if (fm.readString(21).equals("PinaStringTable-0.1.0")){
        rating += 50;
        }

      // 4 - Number Of Files
      if (check.numFiles(fm.readIntB())){
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

      // 4 - Header Length(21)
      // 21 - Header (PinaStringTable-0.1.0)
      fm.skip(25);

      // 4 - Number Of Files
      int numFiles = fm.readIntB();
      check.numFiles(numFiles);


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);

      // read the resources
      for (int i=0;i<numFiles;i++){
        // 4 - Code String Length
        int codeLength = fm.readIntB();
        check.length(codeLength,arcSize);

        // X - Code String
        String code = fm.readString(codeLength);

        // 4 - Value String Length
        int originalLength = fm.readIntB();
        check.length(originalLength,arcSize);

        // X - Value String
        String original = fm.readString(originalLength);

        //original,code
        resources[i] = new Resource_AnkhHeartOfOsiris(original,code);
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


      // 4 - Header Length(21)
      fm.writeIntB(21);

      // 21 - Header (PinaStringTable-0.1.0)
      fm.writeString("PinaStringTable-0.1.0");

      // 4 - Number Of Files
      fm.writeIntB(numFiles);


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        String code = "";

        if (text instanceof Resource_AnkhHeartOfOsiris){
          Resource_AnkhHeartOfOsiris resource = (Resource_AnkhHeartOfOsiris)text;
          code = resource.getCode();
          }

        // 4 - Code String Length
        fm.writeIntB(code.length());

        // X - Code String
        fm.writeString(code);

        // 4 - Value String Length
        fm.writeIntB(translated.length());

        // X - Value String
        fm.writeString(translated);

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
  return new Resource_AnkhHeartOfOsiris();
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
    columns[numColumns] = new WSTableColumn("Code",'C',String.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_AnkhHeartOfOsiris){
      Resource_AnkhHeartOfOsiris resource = (Resource_AnkhHeartOfOsiris)text;

      if (code == 'C'){
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
    if (text instanceof Resource_AnkhHeartOfOsiris){
      Resource_AnkhHeartOfOsiris resource = (Resource_AnkhHeartOfOsiris)text;

      if (code == 'C'){
        resource.setCode((String)value);
        return;
        }
      }

    super.setColumnValue(text,code,value);
    }


  }
