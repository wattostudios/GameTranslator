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

import org.watto.manipulator.*;

import java.io.File;

/**
**********************************************************************************************
A dummy plugin that is used to show an "All Files" in the ReadArchivePluginList. Not actually
a plugin, just returns a rating > 25 for all files, so everything is shown. Can also be used
as a dummy plugin if needing to access something in abstract classes Plugin or ArchivePlugin.
<br><br>
Also used as a dummy when making a new archive, or before an archive is loaded, as opposed to
the older way of using a null.
**********************************************************************************************
**/
public class AllFilesPlugin extends ArchivePlugin {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public AllFilesPlugin() {
    setName("All Files");
    canRead = true;
    canWrite = true;
    canRename = true;

    games = new String[]{"All Files"};
    extensions = new String[]{"*"};
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getMatchRating(FileManipulator fm) {
    return 26;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Resource[] read(File path) {
    return null;
    }


  }

