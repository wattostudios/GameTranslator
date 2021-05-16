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
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.*;

/**
**********************************************************************************************
  UI for JToolTips
**********************************************************************************************
**/

public class AquanauticToolTipUI extends BasicToolTipUI {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticToolTipUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);

    //String text = ((JToolTip)c).getTipText();
    //if (text == null || text.length() <= 0){
    //  c.setBorder(new EmptyBorder(0,0,0,0));
    //  return;
    //  }

    int pad = AquanauticTheme.MENU_BORDER_WIDTH;
    c.setBorder(new EmptyBorder(pad,pad,pad,pad));
    c.setOpaque(false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);

    c.setBorder(BasicBorders.getTextFieldBorder());
    c.setOpaque(true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g, JComponent c){
    JToolTip tip = (JToolTip) c;

    String text = tip.getTipText();

    if (text == null || text.length() <= 0){
      return;
      }


    int pad = AquanauticTheme.MENU_BORDER_WIDTH;

    int w = tip.getWidth();
    int h = tip.getHeight();

    AquanauticPainter.paintSquareGradient((Graphics2D)g,0,0,w,h);

    int textLeft = pad+pad;
    int textTop = pad+pad+2;

    paintEnabledText(tip,g,text,textLeft,textTop);

    }



/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paintEnabledText(JToolTip l, Graphics g, String text, int textLeft, int textTop){
    FontMetrics metrics = g.getFontMetrics();
    int textHeight =  metrics.getHeight();

    textTop += textHeight/2;

    AquanauticPainter.paintText((Graphics2D)g,text,textLeft,textTop);
    //g.setColor(Color.BLACK);
    //g.drawString(text,textLeft,textTop);
    }



  }
