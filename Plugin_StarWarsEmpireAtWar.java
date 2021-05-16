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
public class Plugin_StarWarsEmpireAtWar extends ArchivePlugin {

  String filename = "";

/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_StarWarsEmpireAtWar() {

    super("StarWarsEmpireAtWar","StarWarsEmpireAtWar");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(true);

    setGames("Star Wars: Empire At War");
    setExtensions("dat");
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

      // 4 - Number Of Texts
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

      // 4 - Number Of Text Strings
      int numFiles = fm.readIntL();
      check.numFiles(numFiles);

      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      int[] ids = new int[numFiles];
      int[] lengths = new int[numFiles];
      int[] attLengths = new int[numFiles];
      for (int i=0;i<numFiles;i++){
        // 4 - Attribute Type Tag (11426=CENTER, 28487=HEADER, etc.)
        ids[i] = fm.readIntL();

        // 4 - Text String Length [*2 for unicode]
        int length = fm.readIntL();
        check.length(length,arcSize);
        lengths[i] = length;

        // 4 - Attribute String Length (6)
        int attLength = fm.readIntL();
        check.length(attLength,arcSize);
        attLengths[i] = attLength;
        }



      String[] originals = new String[numFiles];
      for (int i=0;i<numFiles;i++){
        // X - Text String (unicode)
        originals[i] = fm.readUnicodeString(lengths[i]);
        }



      // read the strings
      for (int i=0;i<numFiles;i++){
        // X - Attribute String (ASCII) (CENTER, HEADER, etc.)
        String attribute = fm.readString(attLengths[i]);

        int id = ids[i];
        String original = originals[i];

        //original,id
        resources[i] = new Resource_StarWarsEmpireAtWar(original,id,attribute);
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


      // 4 - Number Of Text Strings
      fm.writeIntL(numFiles);


      WSProgressDialog.setMessage(Language.get("Progress_WritingDirectory"));

      // write the lengths
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        int id = 0;
        String translated = text.getTranslated();
        String attribute = "";

        if (text instanceof Resource_StarWarsEmpireAtWar){
          Resource_StarWarsEmpireAtWar resource = (Resource_StarWarsEmpireAtWar)text;
          id = resource.getID();
          attribute = resource.getAttribute();
          }

        // 4 - Attribute Type Tag (11426=CENTER, 28487=HEADER, etc.)
        fm.writeIntL(id);

        // 4 - Text String Length [*2 for unicode]
        fm.writeIntL(translated.length());

        // 4 - Attribute String Length (6)
        fm.writeIntL(attribute.length());
        }


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();

        // 2.0 - MAKE SURE THE writeUnicodeString() METHOD DOES THE FOLLOWING! ...
        // char[] chars = lines[i].toCharArray();
        // for (int j=0;j<chars.length;j++){
        //   fmOut.writeChar(chars[j]);
        //   }

        // X - String Data (UNICODE)
        fm.writeUnicodeString(translated);

        WSProgressDialog.setValue(i);
        }


      // Write the attributes
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String attribute = "";

        if (text instanceof Resource_StarWarsEmpireAtWar){
          Resource_StarWarsEmpireAtWar resource = (Resource_StarWarsEmpireAtWar)text;
          attribute = resource.getAttribute();
          }

        // X - Attribute string (ASCII)
        fm.writeString(attribute);
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
  return new Resource_StarWarsEmpireAtWar();
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
    columns[numColumns]   = new WSTableColumn("ID"       ,'I',Integer.class,true,true);
    columns[numColumns+1] = new WSTableColumn("Attribute",'A',String.class ,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_StarWarsEmpireAtWar){
      Resource_StarWarsEmpireAtWar resource = (Resource_StarWarsEmpireAtWar)text;

      if (code == 'I'){
        return new Integer(resource.getID());
        }
      else if (code == 'A'){
        return resource.getAttribute();
        }
      }

    return super.getColumnValue(text,code);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColumnValue(Resource text, char code, Object value) {
    if (text instanceof Resource_StarWarsEmpireAtWar){
      Resource_StarWarsEmpireAtWar resource = (Resource_StarWarsEmpireAtWar)text;

      if (code == 'I'){
        resource.setID(((Integer)value).intValue());
        return;
        }
      else if (code == 'A'){
        resource.setAttribute((String)value);
        return;
        }
      }

    super.setColumnValue(text,code,value);
    }


  }
