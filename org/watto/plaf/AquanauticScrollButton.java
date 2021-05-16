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
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

/**
**********************************************************************************************
  Themed buttons for WSScrollBars
**********************************************************************************************
**/

public class AquanauticScrollButton extends MetalScrollButton {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public AquanauticScrollButton(int direction, int width, boolean freeStanding) {
    super(direction, width, freeStanding);
    setBorder(new EmptyBorder(0,0,0,0));
    setOpaque(false);
  }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g){
    int x = 0;
    int y = 0;
    int w = getWidth();
    int h = getHeight();

    ButtonModel model = getModel();
    if (model.isArmed() && model.isPressed()){
      AquanauticPainter.paintCurvedGradient((Graphics2D)g,x,y,w,h);
      }
    else {
      AquanauticPainter.paintCurvedGradient((Graphics2D)g,x,y,w,h,AquanauticTheme.COLOR_MID,AquanauticTheme.COLOR_LIGHT);
      }

    }


}