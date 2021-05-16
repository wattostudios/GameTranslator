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
  UI for JMenuItems
**********************************************************************************************
**/
public class AquanauticMenuItemUI extends BasicMenuItemUI {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent x) {
    return new AquanauticMenuItemUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);

    JMenuItem menu = (JMenuItem)c;
    menu.setBorderPainted(false);
    menu.setOpaque(false);

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);

    JMenuItem menu = (JMenuItem)c;
    menu.setBorderPainted(true);
    menu.setOpaque(true);

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paintBackground(Graphics g, JMenuItem menu, Color bgColor) {
    ButtonModel model = menuItem.getModel();

    int w = menu.getWidth();
    int h = menu.getHeight();

    if (menu.isOpaque()){
      g.setColor(AquanauticTheme.COLOR_BG);
      g.fillRect(0,0,w,h);
      }

    if (model.isArmed() || (menuItem instanceof JMenu && model.isSelected())) {
      //AquanauticPainter.paintMidFill(g,0,0,w,h);
      //AquanauticPainter.paintFocusBorder(g,0,0,w,h);
      AquanauticPainter.paintCurvedGradient((Graphics2D)g,0,0,w,h);
      }

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paintText(Graphics g, JMenuItem menuItem, Rectangle textRect, String text){
    AquanauticPainter.enableAntialias(g);

    ButtonModel model = menuItem.getModel();
    FontMetrics metrics = g.getFontMetrics();

    int pad = AquanauticTheme.BORDER_WIDTH;

    int textHeight =  metrics.getHeight();
    int textTop = pad + textHeight/2 + 3;
    int textLeft = (int)textRect.getX();

    int underline = menuItem.getDisplayedMnemonicIndex();

    if (!model.isEnabled()){
      // Disabled
      AquanauticPainter.paintUnderlineShadowText((Graphics2D)g,text,underline,textLeft,textTop,AquanauticTheme.COLOR_MID,AquanauticTheme.COLOR_LIGHT);
      //g.setColor(AquanauticTheme.COLOR_LIGHT);
      //BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,underline,textLeft+1,textTop+1);
      //g.setColor(AquanauticTheme.COLOR_MID);
      //BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,underline,textLeft,textTop);

      }
    else {
      // Enabled
      AquanauticPainter.paintUnderlineText((Graphics2D)g,text,underline,textLeft,textTop);
      //g.setColor(Color.BLACK);
      //BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,underline,textLeft,textTop);
      }

    }

  }