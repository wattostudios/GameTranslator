////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                            WATTO STUDIOS JAVA PROGRAM TEMPLATE                             //
//                  Template Classes and Helper Utilities for Java Programs                   //
//                                    http://www.watto.org                                    //
//                                                                                            //
//                           Copyright (C) 2004-2008  WATTO Studios                           //
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

package org.watto.plaf;

import org.watto.*;

import javax.swing.*;
import java.awt.*;

/**
**********************************************************************************************

**********************************************************************************************
**/
public class ThemeLoader {

/**
**********************************************************************************************

**********************************************************************************************
**/
  public ThemeLoader(){

    // install the theme
    new AquanauticLookAndFeel();

    // set up the color scheme
    int colors = Settings.getInt("InterfaceColors");
    if (colors != 0){
      AquanauticTheme.setColors(new Color(colors));
      }

    // change to the theme
    setLookAndFeel("Aquanautic");
    }


/**
**********************************************************************************************
Sets the Look and Feel to that with the name <i>chosenLookFeel</i>
@param chosenLookFeel the name of the Look and Feel to load
**********************************************************************************************
**/
  public static void setLookAndFeel(String chosenLookFeel) {
    try {

      UIManager.LookAndFeelInfo[] lookFeels = UIManager.getInstalledLookAndFeels();
      for (int i=0;i<lookFeels.length;i++){
        if (lookFeels[i].getName().equals(chosenLookFeel)){
          UIManager.setLookAndFeel(lookFeels[i].getClassName());
          // not needed because this is called before any interface objects are created
          //SwingUtilities.updateComponentTreeUI(this);
          }
        }

      }
    catch (Throwable t){
      ErrorLogger.log(t);
      }
    }


  }