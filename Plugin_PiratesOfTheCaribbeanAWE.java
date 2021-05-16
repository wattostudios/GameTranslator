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
public class Plugin_PiratesOfTheCaribbeanAWE extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_PiratesOfTheCaribbeanAWE() {

    super("PiratesOfTheCaribbeanAWE","Pirates Of The Caribbean: At Worlds End");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Pirates Of The Caribbean: At Worlds End");
    setExtensions("lag","aps");
    setPlatforms("PC");

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getMatchRating(FileManipulator fm) {
    try {

      int rating = 0;

      for (int i=0;i<extensions.length;i++){
        if (fm.getExtension().equals(extensions[i])){
          rating += 25;
          break;
          }
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
      WSProgressDialog.setMaximum(arcSize);

      // read the resources
      int realNumFiles = 0;
      while (fm.getFilePointer() < arcSize){

        // code=value
        String line = fm.readLine();
        if (line == null || line.length() <= 0){
          // blank line
          continue;
          }

        char firstChar = line.charAt(0);
        if (firstChar == ';' || firstChar == '/' || firstChar == '['){
          // comment
          continue;
          }

        int equalPos = line.indexOf('=');
        if (equalPos <= 0){
          // bad line
          continue;
          }

        String code = line.substring(0,equalPos);
        String original = line.substring(equalPos+1);

        //original,code
        resources[realNumFiles] = new Resource_PiratesOfTheCaribbeanAWE(original,code);
        WSProgressDialog.setValue(fm.getFilePointer());
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



      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        String code = "";

        if (text instanceof Resource_PiratesOfTheCaribbeanAWE){
          Resource_PiratesOfTheCaribbeanAWE resource = (Resource_PiratesOfTheCaribbeanAWE)text;
          code = resource.getCode();
          }

        // code=value
        fm.writeString(code + "=" + translated);

        // 2 - new line chars
        fm.writeByte(13);
        fm.writeByte(10);

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
  return new Resource_PiratesOfTheCaribbeanAWE();
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
    if (text instanceof Resource_PiratesOfTheCaribbeanAWE){
      Resource_PiratesOfTheCaribbeanAWE resource = (Resource_PiratesOfTheCaribbeanAWE)text;

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
    try {
      if (text instanceof Resource_PiratesOfTheCaribbeanAWE){
        Resource_PiratesOfTheCaribbeanAWE resource = (Resource_PiratesOfTheCaribbeanAWE)text;

        if (code == 'C'){
          resource.setCode((String)value);
          return;
          }
        }
      }
    catch (Throwable t){
      }

    super.setColumnValue(text,code,value);
    }


  }
