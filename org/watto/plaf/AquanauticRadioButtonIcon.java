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
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

/**
**********************************************************************************************
  Theme for JRadioButtons
**********************************************************************************************
**/

public class AquanauticRadioButtonIcon implements Icon {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getIconWidth() {
    return (14);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public int getIconHeight() {
    return (14);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paintIcon(Component c, Graphics g, int x, int y) {
    int w = getIconWidth();
    int h = getIconHeight();

    Graphics t = g.create(x,y,w,h);

    JRadioButton check = (JRadioButton)c;
    ButtonModel model = check.getModel();

    AquanauticPainter.paintCurvedGradient((Graphics2D)t,0,0,w,h);
    if (model.isSelected()){
      AquanauticPainter.paintDot((Graphics2D)t,0,0,w,h);
      }

    }


  }
