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

import org.watto.component.*;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

/**
**********************************************************************************************
  UI for JButtons
**********************************************************************************************
**/

public class AquanauticColorPanelUI extends BasicPanelUI {

/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    //int bor = AquanauticTheme.BORDER_WIDTH;
    //c.setBorder(new EmptyBorder(bor,bor,bor,bor));
    return new AquanauticColorPanelUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);

    JPanel panel = (JPanel) c;
    panel.setOpaque(false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);

    JPanel panel = (JPanel) c;
    panel.setOpaque(true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g, JComponent c){
    WSColorPanel panel = (WSColorPanel)c;
    Color color = panel.getColor();
    g.setColor(color);

    int x = 0;
    int y = 0;
    int w = panel.getWidth();
    int h = w; // forces to a square

    // determines the vertical placement, in case the height is larger that 'h'
    int height = panel.getHeight();
    if (height > h){
      y = (height-h)/2;
      }

    g.fillRect(x,y,w,h);

    AquanauticPainter.paintSquareBorder((Graphics2D)g,x-1,y-1,w+2,h+2);

    }


  }
