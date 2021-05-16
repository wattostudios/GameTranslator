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
import javax.swing.plaf.basic.*;

/**
**********************************************************************************************
  UI for JLabels
**********************************************************************************************
**/

public class AquanauticStatusBarUI extends BasicLabelUI {

/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticStatusBarUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);

    int pad = AquanauticTheme.MENU_BORDER_WIDTH;
    c.setBorder(new EmptyBorder(pad,pad,pad,pad));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g, JComponent c){
    JLabel label = (JLabel) c;

    int pad = AquanauticTheme.BORDER_WIDTH;

    int x = 0;
    int y = 0;
    int w = label.getWidth();
    int h = label.getHeight();

    AquanauticPainter.paintSquareGradient((Graphics2D)g,x,y,w,h);

    String text = label.getText();

    if (text != null && !text.equals("")){
      FontMetrics metrics = g.getFontMetrics();
      int textHeight =  metrics.getHeight();

      int textLeft = pad;
      int textTop = textHeight;

      paintText(label,g,text,textLeft,textTop);
      }

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paintText(JLabel l, Graphics g, String text, int textLeft, int textTop){
    AquanauticPainter.paintText((Graphics2D)g,text,textLeft,textTop);
    //AquanauticPainter.enableAntialias(g);
    //g.setColor(AquanauticTheme.COLOR_TEXT);
    //g.drawString(text,textLeft,textTop);
    }


  }
