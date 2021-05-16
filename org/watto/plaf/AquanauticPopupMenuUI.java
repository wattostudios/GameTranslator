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
import javax.swing.border.*;
import javax.swing.plaf.basic.*;

/**
**********************************************************************************************
  UI for JPopupMenus
**********************************************************************************************
**/

public class AquanauticPopupMenuUI extends BasicPopupMenuUI {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticPopupMenuUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);

    JPopupMenu menu = (JPopupMenu)c;
    menu.setOpaque(false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);

    JPopupMenu menu = (JPopupMenu)c;
    menu.setOpaque(true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g, JComponent c){
    int x = 0;
    int y = 0;
    int w = c.getWidth();
    int h = c.getHeight();

    AquanauticPainter.paintOpaque(g,c);
    AquanauticPainter.paintSquareGradient((Graphics2D)g,x-1,y-1,w+2,h+2);

    try {
      JMenu parent = (JMenu)((JPopupMenu)c).getInvoker();
      String parentMenuLabel = parent.getText();

      int pad = AquanauticTheme.BORDER_WIDTH;
      int menubor = AquanauticTheme.MENU_BORDER_WIDTH;

      if (parent.getParent() instanceof JPopupMenu){
        // sub-menu
        FontMetrics metrics = g.getFontMetrics();
        int textHeight = metrics.getHeight();

        Shape oldClip = g.getClip();
        g.setClip(0,menubor+1,menubor-1,textHeight+pad-1);

        //AquanauticPainter.paintBackground((Graphics2D)g,0,0,w,h);
        //AquanauticPainter.paintEmptyBorder((Graphics2D)g,-pad,menubor,pad+pad+pad,textHeight+pad+1);
        AquanauticPainter.paintGradientBackground((Graphics2D)g,0,0,w,h);
        AquanauticPainter.paintCurvedBorder((Graphics2D)g,-pad,menubor,pad+pad+pad,textHeight+pad+2);

        g.setClip(oldClip);

        }
      else {
        // main menu
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(parentMenuLabel);

        Icon parentMenuIcon = parent.getIcon();
        if (parentMenuIcon != null){
          textWidth += parentMenuIcon.getIconWidth() + menubor;
          }

        //g.setColor(AquanauticTheme.COLOR_MID);
        //g.fillRect(menubor,0,textWidth+pad+pad-menubor,pad);

        g.setClip(menubor+menubor/2+1,0,textWidth+pad+pad-2,menubor-1);
        //AquanauticPainter.paintBackground((Graphics2D)g,0,0,w,h);
        //AquanauticPainter.paintEmptyBorder((Graphics2D)g,menubor,-pad,textWidth+pad+pad+pad-menubor-1,pad+pad+pad);
        AquanauticPainter.paintGradientBackground((Graphics2D)g,0,0,w,h);
        AquanauticPainter.paintCurvedBorder((Graphics2D)g,menubor,-pad,textWidth+pad+pad+pad-menubor-1,pad+pad+pad);
        }

      }
    catch (Throwable t){
      }


    }

  }
