////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                      AQUANAUTIC THEME                                      //
//                                  A Look And Feel For Java                                  //
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

import java.awt.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

/**
**********************************************************************************************
  Colors and other constants for the theme
**********************************************************************************************
**/

public class AquanauticTheme extends DefaultMetalTheme{

  public static int BORDER_WIDTH = 6;
  public static int MENU_BORDER_WIDTH = 3;
  public static final int ROUNDNESS = 8;

  //public static int STRIPE_WIDTH = 2;

  //public static int PADDING_WIDTH = 6;

  public static boolean USE_ANTIALIAS = true;

  //public static boolean SHOW_GRADIENT = true;
  //public static boolean ALPHA_ENABLED = true;

  public static Color COLOR_LIGHT = new Color(181,204,174);
  public static Color COLOR_MID = new Color(114,159,100);
  public static Color COLOR_DARK = new Color(61,100,49);

  public static Color COLOR_LIGHT_G = new Color(181,181,181);
  public static Color COLOR_MID_G = new Color(114,114,114);
  public static Color COLOR_DARK_G = new Color(61,61,61);

  public static Color COLOR_TEXT = new Color(0,0,0);
  public static Color COLOR_BG = new Color(255,255,255);

  public static Font FONT = Font.decode("Arial");


  // NEW in 3.0 - used for textfield labels
  public static int TEXT_HEIGHT = 8;

  //// NEW in 3.0 - color of tooltip painted on the background of the textfields (method not implemented)
  //public static Color COLOR_TOOLTIP = new Color(225,225,225);


  public static void setFont(Font font){
    TEXT_HEIGHT = font.getSize()-5;
    FONT = font;
    }


  public static void setLightColor(Color color){
    COLOR_LIGHT = color;
    }

  public static void setMidColor(Color color){
    COLOR_MID = color;
    }

  public static void setDarkColor(Color color){
    COLOR_DARK = color;
    }

  public static void setTextColor(Color color){
    COLOR_TEXT = color;
    }

  public static void setBackgroundColor(Color color){
    COLOR_BG = color;
    }


  // these are blue in Metal Default Theme
  protected ColorUIResource getPrimary1(){
    return new ColorUIResource(COLOR_DARK);
    }

  protected ColorUIResource getPrimary2(){
    return new ColorUIResource(COLOR_MID);
    }

  protected ColorUIResource getPrimary3(){
    return new ColorUIResource(COLOR_LIGHT);
    }

  // these are gray in Metal Default Theme
  protected ColorUIResource getSecondary1(){
    return new ColorUIResource(COLOR_BG);
    }

  protected ColorUIResource getSecondary2(){
    return new ColorUIResource(COLOR_BG);
    }

  protected ColorUIResource getSecondary3(){
    return new ColorUIResource(COLOR_BG);
    }


  public static void setColors(Color color){
    if (color == null){
      return;
      }

    int r = color.getRed();
    int g = color.getGreen();
    int b = color.getBlue();

    /*
    if (r==0 && b==0 && g==0){
      COLOR1 = COLOR1DEFAULT;
      COLOR2 = COLOR2DEFAULT;
      COLOR3 = COLOR3DEFAULT;
      return;
      }
    */

    COLOR_DARK = color;

    int rRange = (255 - r) / 5;
    if (rRange > 25){
      //rRange = 25;
      }

    int gRange = (255 - g) / 5;
    if (gRange > 25){
      //gRange = 25;
      }

    int bRange = (255 - b) / 5;
    if (bRange > 25){
      //bRange = 25;
      }

    r += rRange;
    g += gRange;
    b += bRange;

    COLOR_MID = new Color(r,g,b);

    r += rRange + rRange;
    g += gRange + gRange;
    b += bRange + bRange;

    COLOR_LIGHT = new Color(r,g,b);


    }


  }