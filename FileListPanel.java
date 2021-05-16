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

import org.watto.*;
import org.watto.component.*;
import org.watto.xml.XMLReader;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.datatransfer.*;

public abstract class FileListPanel extends WSPanelPlugin {// implements WSDropableInterface{


/**
**********************************************************************************************

**********************************************************************************************
**/
public FileListPanel(String name){
  super();
  setCode(name);
  setLayout(new BorderLayout(2,2));
  constructInterface();
  }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract void changeSelection(int row);


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract void constructInterface();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getDescription(){

    String description = toString() + "\n\n" + Language.get("Description_FileListPanel");

    if (! isEnabled()){
      description += "\n\n" + Language.get("Description_PluginDisabled");
      }
    else {
      description += "\n\n" + Language.get("Description_PluginEnabled");
      }

    return description;

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract int getFirstSelectedRow();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract int getNumSelected();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract Resource getResource(int row);


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract Resource[] getSelected();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract void reload();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract void selectAll();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract void selectInverse();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract void selectNone();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract void selectResource(int row);


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void stopInlineEditing(){
    }


  }