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
public class Plugin_UnrealTournament3 extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_UnrealTournament3() {

    super("UnrealTournament3","UnrealTournament3");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Unreal Tournament 3");
    setExtensions("int"); // MUST BE LOWER CASE
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

      // 1 - Group tag [
      if (fm.readString(1).equals("[")){
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
      String group = "";
      while (fm.getFilePointer() < arcSize){
        String line = fm.readLine();

        if (line.length() <= 0){
          // blank line
          continue;
          }
        else if (line.charAt(0) == '['){
          // group
          group = line.substring(1,line.length()-1);
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
        resources[realNumFiles] = new Resource_UnrealTournament3(original,group,code);
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


      // sort by groups
      java.util.Arrays.sort(resources);


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      String groupName = "";
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        String code = "";
        String group = "";

        if (text instanceof Resource_UnrealTournament3){
          Resource_UnrealTournament3 resource = (Resource_UnrealTournament3)text;
          code = resource.getCode();
          group = resource.getGroup();
          }

        if (!group.equals(groupName)){
          // write the group name
          fm.writeString("[" + group + "]");
          fm.writeByte(13);
          fm.writeByte(10);

          groupName = group;
          }

        // X - String Data
        fm.writeString(code + "=" + translated);
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
  return new Resource_UnrealTournament3();
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
    columns[numColumns+1] = new WSTableColumn("Group",'G',String.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_UnrealTournament3){
      Resource_UnrealTournament3 resource = (Resource_UnrealTournament3)text;

      if (code == 'G'){
        return resource.getGroup();
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
    try {
      if (text instanceof Resource_UnrealTournament3){
        Resource_UnrealTournament3 resource = (Resource_UnrealTournament3)text;

        if (code == 'G'){
          resource.setGroup((String)value);
          return;
          }
        else if (code == 'C'){
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
