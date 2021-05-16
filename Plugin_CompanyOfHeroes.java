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
public class Plugin_CompanyOfHeroes extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_CompanyOfHeroes() {

    super("CompanyOfHeroes","CompanyOfHeroes");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(true);

    setGames("Company Of Heroes");
    setExtensions("ucs");
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

      // 2 - Unicode Header
      if (fm.readByte() == 255 && fm.readByte() == 254){
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


      int realNumFiles = 0;

      // read the resources
      while(fm.getFilePointer() < arcSize){
        String line = fm.readUnicodeLine();
        if (line.length() == 0){
          continue;
          }

        int tabPos = line.indexOf('\t');
        String id = line.substring(0,tabPos);
        String original = line.substring(tabPos+1);

        //original,id
        resources[realNumFiles] = new Resource_CompanyOfHeroes(original,id);
        WSProgressDialog.setValue(realNumFiles);
        realNumFiles++;
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


      // 2 - Unicode Header (255,254)
      fm.writeByte(255);
      fm.writeByte(254);



      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        String id = "";

        if (text instanceof Resource_CompanyOfHeroes){
          Resource_CompanyOfHeroes resource = (Resource_CompanyOfHeroes)text;
          id = resource.getID();
          }


        // X - ID Number (Unicode)
        // 2 - Tab Character (9,0)
        // X - Text String (Unicode)
        // 4 - New Line Characters (13,0,10,0)
        fm.writeUnicodeString(id + '\t' + translated + '\r' + '\n');


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
  return new Resource_CompanyOfHeroes();
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
    columns[numColumns] = new WSTableColumn("ID",'I',String.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_CompanyOfHeroes){
      Resource_CompanyOfHeroes resource = (Resource_CompanyOfHeroes)text;

      if (code == 'I'){
        return resource.getID();
        }
      }

    return super.getColumnValue(text,code);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColumnValue(Resource text, char code, Object value) {
    if (text instanceof Resource_CompanyOfHeroes){
      Resource_CompanyOfHeroes resource = (Resource_CompanyOfHeroes)text;

      if (code == 'I'){
        resource.setID(value.toString());
        return;
        }
      }

    super.setColumnValue(text,code,value);
    }


  }
