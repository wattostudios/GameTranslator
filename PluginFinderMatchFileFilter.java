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

import java.io.*;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class PluginFinderMatchFileFilter implements FileFilter {

  ArchivePlugin plugin;


/**
**********************************************************************************************

**********************************************************************************************
**/
  public PluginFinderMatchFileFilter(ArchivePlugin plugin) {
    this.plugin = plugin;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean accept(File file) {

    if (file.isDirectory()){
      return true;
      }

    if (plugin instanceof AllFilesPlugin){
      // quicker when a filter isn't selected,
      // as it doesn't have to open each file in a FileManipulator and check it.
      return true;
      }

    if (plugin.getMatchRating(file) >= 15){
      return true;
      }
    else {
      return false;
      }

    }


  }

