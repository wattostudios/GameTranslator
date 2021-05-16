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
import org.watto.component.WSPluginException;
import org.watto.component.WSTableColumn;
import org.watto.manipulator.FileManipulator;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Plugin_CodeOfHonor extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_CodeOfHonor() {

    super("CodeOfHonor","CodeOfHonor");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Code of Honor: The French Foreign Legion");
    setExtensions("scr");
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

      // line
      if (fm.readString(8).equals("String( ")){
        rating += 50;
        }
      else {
        fm.seek(0);
        if (fm.readString(2).equals("//")){
          rating += 5;
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


      // 4 - Number Of Text Strings
      int numFiles = Archive.getMaxFiles();



      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      // read the resources
      int realNumFiles = 0;
      for (int i=0;i<numFiles;i++){
        String line = fm.readLine().trim();
        if (line.length() == 0){
          // blank line
          }
        else if (line.length() >= 2 && line.substring(0,2).equals("//")){
          // comment
          }
        else if (line.length() > 8 && line.substring(0,8).equals("String( ")){
          // String( "code", "value")

          int codeStart = line.indexOf("\"") + 1;
          int codeEnd = line.indexOf("\"",codeStart);

          int valueStart = line.indexOf("\"",codeEnd) + 1;
          int valueEnd = line.lastIndexOf("\"");

          String code = line.substring(codeStart,codeEnd);
          String original = line.substring(valueStart,valueEnd);

          //original,code
          resources[realNumFiles] = new Resource_CodeOfHonor(original,code);
          WSProgressDialog.setValue(realNumFiles);
          realNumFiles++;
          }
        else {
          // bad line
          throw new WSPluginException("Invalid Line: " + line);
          }

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
        String code = "";

        if (text instanceof Resource_CodeOfHonor){
          Resource_CodeOfHonor resource = (Resource_CodeOfHonor)text;
          code = resource.getCode();
          }

        // String( "code", "value")
        fm.writeLine("String( \"" + code + "\", \"" + translated + "\")");

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
  return new Resource_CodeOfHonor();
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
    if (text instanceof Resource_CodeOfHonor){
      Resource_CodeOfHonor resource = (Resource_CodeOfHonor)text;

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
    if (text instanceof Resource_CodeOfHonor){
      Resource_CodeOfHonor resource = (Resource_CodeOfHonor)text;

      if (code == 'C'){
        resource.setCode(value.toString());
        return;
        }
      }

    super.setColumnValue(text,code,value);
    }


  }
