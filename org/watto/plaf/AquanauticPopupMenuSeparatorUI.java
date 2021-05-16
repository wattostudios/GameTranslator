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
import javax.swing.plaf.basic.*;

/**
**********************************************************************************************
  UI for JPopupMenuSeparators
**********************************************************************************************
**/

public class AquanauticPopupMenuSeparatorUI extends BasicPopupMenuSeparatorUI {

  private final static AquanauticPopupMenuSeparatorUI buttonUI = new AquanauticPopupMenuSeparatorUI();


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return buttonUI;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g, JComponent c){
    Dimension s = c.getSize();

    int leftPos = 0;
    int w = (int)s.getWidth();

    g.setColor(AquanauticTheme.COLOR_MID);
    g.drawLine(leftPos,0,w,0);
    g.setColor(AquanauticTheme.COLOR_LIGHT);
    g.drawLine(leftPos,1,w,1);


    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Dimension getPreferredSize(JComponent c){
    return new Dimension(0,2);
    }


  }
