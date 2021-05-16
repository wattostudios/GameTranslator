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

import org.watto.component.*;
import org.watto.*;

import javax.swing.table.*;
import javax.swing.event.*;

public class FileListModel_Table_Properties extends FileListModel_Table {

/**
**********************************************************************************************

**********************************************************************************************
**/
  public FileListModel_Table_Properties() {
    super();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reload() {
    readPlugin = Archive.getReadPlugin();
    resources = readPlugin.getProperties();
    columns = readPlugin.getViewingPropColumns();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reload(Resource[] resources) {
    this.resources = resources;
    readPlugin = Archive.getReadPlugin();
    columns = readPlugin.getViewingPropColumns();
    }



  }