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
import org.watto.component.WSPluginException;
import org.watto.component.WSProgressDialog;
import org.watto.component.WSTableColumn;
import org.watto.manipulator.FileManipulator;

import java.io.File;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class Plugin_EuropaUniversalis3 extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Plugin_EuropaUniversalis3() {

    super("EuropaUniversalis3","EuropaUniversalis3");

    //           read write replace
    setProperties(true,true,true);
    setAllowsUnicode(false);

    setGames("Europa Universalis III");
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
        rating += 25;
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

      int realNumFiles = 0;

      // read the resources
      while (fm.getFilePointer() < arcSize){

        // 1. Code
        // 2. English
        // 3. French
        // 4. German
        // 5. Polish
        // 6. Spanish
        // 7. Portuguese
        // 8. Blank
        // 9. The letter "x"
        String line = fm.readLine();

        String[] values = line.split(";");

        if (values.length != 9){
          throw new WSPluginException("Invalid line - expected exactly 9 values, but instead found " + values.length);
          }

        //english(original),code,french,german,polish,spanish,portuguese,blank,x
        resources[realNumFiles] = new Resource_EuropaUniversalis3(values[1],values[0],values[2],values[3],values[4],values[5],values[6],values[7],values[8]);

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
        String french = "";
        String german = "";
        String polish = "";
        String spanish = "";
        String portuguese = "";
        String blank = "";
        String x = "";

        if (text instanceof Resource_EuropaUniversalis3){
          Resource_EuropaUniversalis3 resource = (Resource_EuropaUniversalis3)text;
          code = resource.getCode();
          french = resource.getFrench();
          german = resource.getGerman();
          polish = resource.getPolish();
          spanish = resource.getSpanish();
          portuguese = resource.getPortuguese();
          blank = resource.getBlank();
          x = resource.getX();
          }

        // 1. Code
        // 2. English
        // 3. French
        // 4. German
        // 5. Polish
        // 6. Spanish
        // 7. Portuguese
        // 8. Blank
        // 9. The letter "x"
        fm.writeLine(code + ";" + translated + ";" + french + ";" + german + ";" + polish + ";" + spanish + ";" + portuguese + ";" + blank + ";" + x);


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
  return new Resource_EuropaUniversalis3();
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
    WSTableColumn[] columns = new WSTableColumn[numColumns+8];
    System.arraycopy(baseColumns,0,columns,0,numColumns);

    // add the additional columns...

    //code,languageCode,class,editable,sortable
    columns[numColumns]   = new WSTableColumn("Code"      ,'C',String.class,true,true);
    columns[numColumns+1] = new WSTableColumn("French"    ,'1',String.class,true,true);
    columns[numColumns+2] = new WSTableColumn("German"    ,'2',String.class,true,true);
    columns[numColumns+3] = new WSTableColumn("Polish"    ,'3',String.class,true,true);
    columns[numColumns+4] = new WSTableColumn("Spanish"   ,'4',String.class,true,true);
    columns[numColumns+5] = new WSTableColumn("Portuguese",'5',String.class,true,true);
    columns[numColumns+6] = new WSTableColumn("Blank"     ,'6',String.class,true,true);
    columns[numColumns+7] = new WSTableColumn("X"         ,'7',String.class,true,true);

    return columns;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Object getColumnValue(Resource text, char code) {
    if (text instanceof Resource_EuropaUniversalis3){
      Resource_EuropaUniversalis3 resource = (Resource_EuropaUniversalis3)text;

      if (code == 'C'){
        return resource.getCode();
        }
      else if (code == '1'){
        return resource.getFrench();
        }
      else if (code == '2'){
        return resource.getGerman();
        }
      else if (code == '3'){
        return resource.getPolish();
        }
      else if (code == '4'){
        return resource.getSpanish();
        }
      else if (code == '5'){
        return resource.getPortuguese();
        }
      else if (code == '6'){
        return resource.getBlank();
        }
      else if (code == '7'){
        return resource.getX();
        }
      }

    return super.getColumnValue(text,code);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setColumnValue(Resource text, char code, Object value) {
    if (text instanceof Resource_EuropaUniversalis3){
      Resource_EuropaUniversalis3 resource = (Resource_EuropaUniversalis3)text;

      if (code == 'C'){
        resource.setCode(value.toString());
        return;
        }
      if (code == '1'){
        resource.setFrench(value.toString());
        return;
        }
      if (code == '2'){
        resource.setGerman(value.toString());
        return;
        }
      if (code == '3'){
        resource.setPolish(value.toString());
        return;
        }
      if (code == '4'){
        resource.setSpanish(value.toString());
        return;
        }
      if (code == '5'){
        resource.setPortuguese(value.toString());
        return;
        }
      if (code == '6'){
        resource.setBlank(value.toString());
        return;
        }
      if (code == '7'){
        resource.setX(value.toString());
        return;
        }
      }

    super.setColumnValue(text,code,value);
    }


  }
