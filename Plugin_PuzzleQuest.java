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
import org.watto.xml.*;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Plugin_PuzzleQuest extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_PuzzleQuest() {

    super("PuzzleQuest","PuzzleQuest");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Puzzle Quest");
    setExtensions("xml");
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


      String header = fm.readString(13);
      if (header.equals("<?xml version")){
        fm.skip(10);
        header = fm.readString(13);
        }

      if (header.equals("<TextLibrary>")){
        rating += 50;
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


      XMLNode node = XMLReader.read(path);

      int numFiles = node.getChildCount();


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      // read the resources
      for (int i=0;i<numFiles;i++){
        XMLNode child = node.getChild(i);

        String code = child.getAttribute("tag");
        String original = child.getContent();

        //original,code
        resources[i] = new Resource_PuzzleQuest(original,code);
        WSProgressDialog.setValue(i);
        }

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

      int numFiles = resources.length;

      WSProgressDialog.setMaximum(numFiles);


      XMLNode root = new XMLNode("TextLibrary");


      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        String code = "";

        if (text instanceof Resource_PuzzleQuest){
          Resource_PuzzleQuest resource = (Resource_PuzzleQuest)text;
          code = resource.getCode();
          }

        XMLNode child = new XMLNode("Text",translated);
        child.setAttribute("tag",code);

        root.addChild(child);


        WSProgressDialog.setValue(i);
        }


      XMLWriter.write(path,root);

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
  return new Resource_PuzzleQuest();
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
    if (text instanceof Resource_PuzzleQuest){
      Resource_PuzzleQuest resource = (Resource_PuzzleQuest)text;

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
      if (text instanceof Resource_PuzzleQuest){
        Resource_PuzzleQuest resource = (Resource_PuzzleQuest)text;

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
