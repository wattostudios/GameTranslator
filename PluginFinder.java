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

import org.watto.ErrorLogger;
import org.watto.component.*;
import org.watto.manipulator.*;

import java.io.*;

public class PluginFinder{

/**
**********************************************************************************************

**********************************************************************************************
**/
  public PluginFinder() {
    }


/**
**********************************************************************************************
does not do sorting!
**********************************************************************************************
**/
  public static RatedPlugin[] findPlugins(File path, Class pluginType) {
    try {
      WSPlugin[] plugins = new ArchivePlugin[0];

      // should we only use plugins with rating > 15%
      boolean checkRating = false;

      if (pluginType == ArchivePlugin.class){
        plugins = (WSPlugin[])WSPluginManager.group("Archive").getPlugins();
        checkRating = true;
        }



      FileManipulator fm = new FileManipulator(path,"r");

      RatedPlugin[] results = new RatedPlugin[plugins.length];
      int zeroPos = plugins.length - 1;
      int startPos = 0;


      if (pluginType == ArchivePlugin.class){
        for (int i=0;i<results.length;i++){
          //if (plugins[i] == null){
          //  zeroPos
          //  continue();
          //  }

          fm.seek(0);
          int rating = ((ArchivePlugin)plugins[i]).getMatchRating(fm);
          if (checkRating && rating < 15){
            // failed plugin
            zeroPos--;
            }
          else {
            // successful plugin
            results[startPos] = new RatedPlugin(plugins[i],rating);
            startPos++;
            }
          }
        }

      fm.close();

      // resize the results array
      RatedPlugin[] temp = results;
      results = new RatedPlugin[startPos];
      System.arraycopy(temp,0,results,0,startPos);

      return results;
      }
    catch (Throwable t){
      ErrorLogger.log(t);
      return null;
      }
    }


  }