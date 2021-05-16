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
public class Plugin_ArmageddonEmpires_TileDefBonus extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_ArmageddonEmpires_TileDefBonus() {

    super("ArmageddonEmpires_TileDefBonus","ArmageddonEmpires_TileDefBonus");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Armageddon Empires");
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

      if (fm.getFile().getName().equals("TileDefBonus.txt")){
        rating += 25;
        }

      // first line
      if (fm.readString(13).equals("NumEntries: [")){
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


      // First line - numFiles
      String firstLine = fm.readLine();
      int numFiles = Integer.parseInt(firstLine.substring(13,firstLine.length()-1));


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      // read the resources
      for (int i=0;i<numFiles;i++){

        // ["Mountains", 2]
        String line = fm.readLine();
        if (line == null || line.length() <= 0 || line.equals("EOF")){
          continue;
          }

        int commaPos = line.indexOf(",");
        String original = line.substring(2,commaPos-1);
        int id = Integer.parseInt(line.substring(commaPos+2,line.length()-1));


        //original,id
        resources[i] = new Resource_ArmageddonEmpires_TileDefBonus(original,id);
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


      // first line - NumEntries: [4]
      fm.writeString("NumEntries: [" + numFiles + "]");
      fm.writeByte(13);
      fm.writeByte(10);


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        int id = 0;

        if (text instanceof Resource_ArmageddonEmpires_TileDefBonus){
          Resource_ArmageddonEmpires_TileDefBonus resource = (Resource_ArmageddonEmpires_TileDefBonus)text;
          id = resource.getID();
          }

        // ["Mountains", 2]
        fm.writeString("[\"" + translated + "\", " + id + "]");
        fm.writeByte(13);
        fm.writeByte(10);

        WSProgressDialog.setValue(i);
        }


      // EOF
      fm.writeString("EOF");

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
  return new Resource_ArmageddonEmpires_TileDefBonus();
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
    columns[numColumns]   = new WSTableColumn("ID",'I',Integer.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_ArmageddonEmpires_TileDefBonus){
      Resource_ArmageddonEmpires_TileDefBonus resource = (Resource_ArmageddonEmpires_TileDefBonus)text;

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
      if (text instanceof Resource_ArmageddonEmpires_TileDefBonus){
        Resource_ArmageddonEmpires_TileDefBonus resource = (Resource_ArmageddonEmpires_TileDefBonus)text;

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
