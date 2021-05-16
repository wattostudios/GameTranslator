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

import java.io.*;
import javax.swing.*;

public abstract class DirectoryListPanel extends WSPanelPlugin{


/**
**********************************************************************************************
Constructor
**********************************************************************************************
**/
  public DirectoryListPanel() {
    super();
    }


/**
**********************************************************************************************
Constructor
**********************************************************************************************
**/
  public DirectoryListPanel(String name) {
    super();
    setCode(name);
    }


/**
**********************************************************************************************
  Checks the current directory when it is being repainted incase any files are added/removed
**********************************************************************************************
**/
  public abstract void checkFilesExist();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract void constructInterface(File directory);


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract DirectoryListPanel getNew();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void dropFiles(Resource[] resources){
    // Overwritten by the specific panel
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract File[] getAllSelectedFiles();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getDescription(){

    String description = toString() + "\n\n" + Language.get("Description_DirectoryListPanel");

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
  public abstract File getCurrentDirectory();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract File getSelectedFile();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract void reload();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract void scrollToSelected();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract void setMatchFilter(FileFilter filter);


/**
**********************************************************************************************

**********************************************************************************************
**/
  public abstract void setMultipleSelection(boolean multi);


  }