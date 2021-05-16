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
import org.watto.xml.*;
import org.watto.component.WSPluginException;
import org.watto.component.WSProgressDialog;
import org.watto.component.WSTableColumn;
import org.watto.manipulator.FileBuffer;
import org.watto.manipulator.FileManipulator;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Plugin_Desperados2 extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_Desperados2() {

    super("Desperados2","Desperados2");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Desperados 2: Cooper's Revenge");
    setExtensions("en","de","es","fr","it");
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

      // 38 - Header
      if (fm.readString(38).equals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")){
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
      //node.printTree();
      //node = node.getChild("resources");

      String language = node.getAttribute("lang");
      String name = node.getAttribute("name");

      Resource_Property[] properties = new Resource_Property[2];
      properties[0] = new Resource_Property(language,"Language");
      properties[1] = new Resource_Property(name,"Filename");
      setProperties(properties);


      int numFiles = node.getChildCount();
      check.numFiles(numFiles);


      Resource[] resources = new Resource[numFiles];
      WSProgressDialog.setMaximum(numFiles);


      // read the resources
      for (int i=0;i<numFiles;i++){
        // <text id="code">text</text>
        XMLNode child = node.getChild(i);
        if (child.getTag().equals("text")){
          String code = child.getAttribute("id");
          String original = child.getContent();

          //original,code
          resources[i] = new Resource_Desperados2(original,code);
          WSProgressDialog.setValue(i);
          }
        else {
          throw new WSPluginException("Invalid child tag - expected <text>: " + child.getTag());
          }

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

      XMLNode node = new XMLNode("resources");


      Resource_Property[] properties = getProperties();
      for (int i=0;i<properties.length;i++){
        String code = properties[i].getProperty();
        String translated = properties[i].getTranslated();

        if (code.equals("Language")){
          node.addAttribute("lang",translated);
          }
        else if (code.equals("Filename")){
          node.addAttribute("name",translated);
          }
        }



      WSProgressDialog.setMessage(Language.get("Progress_WritingFiles"));

      // Write the strings
      for (int i=0;i<numFiles;i++){
        Resource text = resources[i];

        String translated = text.getTranslated();
        String code = "";

        if (text instanceof Resource_Desperados2){
          Resource_Desperados2 resource = (Resource_Desperados2)text;
          code = resource.getCode();
          }

        XMLNode child = new XMLNode("text");
        child.addAttribute("id",code);

        node.addChild(child);

        WSProgressDialog.setValue(i);
        }



      // write the actual file
      FileManipulator fm = new FileManipulator(path,"rw");

      XMLWriter.setFile(fm);

      XMLWriter.writeHeader(1.0,"UTF-8");
      XMLWriter.writeDocType("resources.dtd");
      XMLWriter.write(node);

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
  return new Resource_Desperados2();
  }


/**
**********************************************************************************************
Sets up the default properties
**********************************************************************************************
**/
public void setDefaultProperties(boolean force){
  if (force || properties.length == 0){
    properties = new Resource_Property[2];
    properties[0] = new Resource_Property("Language");
    properties[1] = new Resource_Property("Filename");
    }
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
    if (text instanceof Resource_Desperados2){
      Resource_Desperados2 resource = (Resource_Desperados2)text;

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
    if (text instanceof Resource_Desperados2){
      Resource_Desperados2 resource = (Resource_Desperados2)text;

      if (code == 'C'){
        resource.setCode(value.toString());
        return;
        }
      }

    super.setColumnValue(text,code,value);
    }


  }
