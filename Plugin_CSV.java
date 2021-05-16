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
import javax.swing.Icon;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Plugin_CSV extends ArchivePlugin {

  String[] columnHeaders;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_CSV() {

    super("CSV","CSV");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(true);

    setGames("");
    setExtensions("csv");
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
        rating += 24;
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

      // look for unicode header
      boolean unicode = false;
      if (fm.readByteU() == 255 && fm.readByteU() == 254){
        unicode = true;
        }
      else {
        unicode = false;
        fm.seek(0);
        }


      String headerLine;
      if (unicode){
        headerLine = fm.readUnicodeLine();
        }
      else {
        headerLine = fm.readLine();
        }

      columnHeaders = headerLine.split(",");


      int numFiles = Archive.getMaxFiles();
      int realNumFiles = 0;

      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      // read the resources
      while (fm.getFilePointer() < arcSize){
        String line;
        if (unicode){
          line = fm.readUnicodeLine();
          }
        else {
          line = fm.readLine();
          }
        if (line.length() <= 0){
          continue;
          }

        String[] columns = line.split(",");
        if (columns.length == 0){
          columns = new String[columnHeaders.length];
          for (int j=0;j<columns.length;j++){
            columns[j] = "";
            }
          }

        //columns
        resources[realNumFiles] = new Resource_CSV(columns);
        WSProgressDialog.setValue(realNumFiles);
        realNumFiles++;
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


      // write Unicode header
      fm.writeByte(255);
      fm.writeByte(254);


      WSTableColumn[] columns = Archive.getColumns();

      // ignore Icon columns
      int numColumns = 0;
      for (int i=0;i<columns.length;i++){
        if (!(columns[i].getType().equals(Icon.class)) && columns[i].getCharCode() != 'O'){
          numColumns++;
          }
        }

      WSTableColumn[] tempColumns = columns;
      columns = new WSTableColumn[numColumns];
      columnHeaders = new String[numColumns]; // have to set the column headers, in case of conversion from another format
      int numFoundColumns = 0;
      for (int i=0;i<tempColumns.length;i++){
        if (!(tempColumns[i].getType().equals(Icon.class)) && tempColumns[i].getCharCode() != 'O'){
          columns[numFoundColumns] = tempColumns[i];
          columnHeaders[numFoundColumns] = tempColumns[i].getName();
          numFoundColumns++;
          }
        }

      // write the table headings
      for (int i=0;i<numColumns;i++){
        //if (!(columns[i].getType().equals(Icon.class))){
          if (i > 0){
            fm.writeUnicodeString(",");
            }
          fm.writeUnicodeString(columns[i].getName());
        //  }
        }
      fm.writeUnicodeString("\n");



      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      ArchivePlugin oldPlugin = Archive.getOldReadPlugin();

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource resource = resources[i];

        for (int j=0;j<numColumns;j++){
          //if (!(columns[j].getType().equals(Icon.class))){
            if (j > 0){
              fm.writeUnicodeString(",");
              }

            Object object = oldPlugin.getColumnValue(resource,columns[j].getCharCode());
            if (object != null){
              fm.writeUnicodeString(object.toString());
              }
          //  }
          }

        fm.writeUnicodeString("\n");

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
  return new Resource_CSV();
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
    int numHeaders = columnHeaders.length;
    WSTableColumn[] columns = new WSTableColumn[numColumns + numHeaders-1];
    System.arraycopy(baseColumns,0,columns,0,numColumns);

    // add the additional columns...

    //code,languageCode,class,editable,sortable
    for (int i=1,j=numColumns;i<numHeaders;i++,j++){
      columns[j] = new WSTableColumn(columnHeaders[i],(char)i,String.class,true,true);
      }

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_CSV){
      Resource_CSV resource = (Resource_CSV)text;

      int columnNum = (int)code;

      if (columnNum < columnHeaders.length){
        return resource.getColumn(columnNum);
        }
      }

    return super.getColumnValue(text,code);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColumnValue(Resource text, char code, Object value) {
    if (text instanceof Resource_CSV){
      Resource_CSV resource = (Resource_CSV)text;

      int columnNum = (int)code;

      if (columnNum < columnHeaders.length){
        resource.setColumn(columnNum,value.toString());
        return;
        }
      }

    super.setColumnValue(text,code,value);
    }


  }
