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
public class Plugin_GhostRecon_TXT extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_GhostRecon_TXT() {

    super("GhostRecon_TXT","GhostRecon_TXT");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Ghost Recon");
    setExtensions("txt"); // MUST BE LOWER CASE
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

      if (fm.getFile().getName().equals("strings.txt")){
        rating += 25;
        }

      // 2 - //
      if (fm.readString(2).equals("//")){
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


      int numFiles = Archive.getMaxFiles();



      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);



      // read the resources
      int realNumFiles = 0;
      while (fm.getFilePointer() < arcSize){
        String line = fm.readLine();

        if (line.length() < 2){
          // blank line
          continue;
          }
        else if (line.charAt(0) == '/' && line.charAt(1) == '/'){
          // comment
          continue;
          }

        int quote1pos = line.indexOf('\"');
        int quote2pos = line.indexOf('\"',quote1pos+1);
        int quote3pos = line.indexOf('\"',quote2pos+1);
        int quote4pos = line.indexOf('\"',quote3pos+1);

        if (quote4pos <= 0){
          // bad line
          continue;
          }

        String code = line.substring(quote1pos+1,quote2pos);
        String original = line.substring(quote3pos+1,quote4pos);

        //original,code
        resources[realNumFiles] = new Resource_GhostRecon_TXT(original,code);
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




      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        String code = "";

        if (text instanceof Resource_GhostRecon_TXT){
          Resource_GhostRecon_TXT resource = (Resource_GhostRecon_TXT)text;
          code = resource.getCode();
          }


        fm.writeString("\t\"" + code + "\"\t\t\"" + translated + "\"" + (char)13 + "" + (char)10);

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
  return new Resource_GhostRecon_TXT();
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
    if (text instanceof Resource_GhostRecon_TXT){
      Resource_GhostRecon_TXT resource = (Resource_GhostRecon_TXT)text;

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
      if (text instanceof Resource_GhostRecon_TXT){
        Resource_GhostRecon_TXT resource = (Resource_GhostRecon_TXT)text;

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
