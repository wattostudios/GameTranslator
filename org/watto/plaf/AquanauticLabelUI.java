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

import org.watto.component.WSLabel;
import org.watto.component.WordWrap;

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

public class AquanauticLabelUI extends BasicLabelUI {

/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticLabelUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g, JComponent c) {

    if (c instanceof WSLabel){
      WSLabel wsl = (WSLabel)c;

      if (wsl.getShowBorder()){
        AquanauticPainter.paintCurvedGradient((Graphics2D)g,0,0,c.getWidth(),c.getHeight());
        }

      if (wsl.getWrap()){
        paintWrap(g,c);
        }
      else {
        super.paint(g,c);
        }

      }
    else {
      super.paint(g,c);
      }

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paintWrap(Graphics g, JComponent c) {

    Rectangle paintIconR = new Rectangle();
    Rectangle paintTextR = new Rectangle();
    Rectangle paintViewR = new Rectangle();
    Insets paintViewInsets = new Insets(0, 0, 0, 0);

    WSLabel label = (WSLabel)c;
    String text = label.getText();
    Icon icon = (label.isEnabled()) ? label.getIcon() : label.getDisabledIcon();

    int x = 0;
    int y = 0;
    int w = label.getWidth();
    int h = label.getHeight();

    if (label.isEnabled()){
      //AquanauticPainter.paintBorder((Graphics2D)g,x,y,w,h);
      }

    if ((icon == null) && (text == null)) {
      return;
      }

    FontMetrics fm = g.getFontMetrics();
    Insets insets = c.getInsets(paintViewInsets);

    paintViewR.x = insets.left;
    paintViewR.y = insets.top;
    paintViewR.width = w - (insets.left + insets.right);
    paintViewR.height = h - (insets.top + insets.bottom);

    paintIconR.x = paintIconR.y = paintIconR.width = paintIconR.height = 0;
    paintTextR.x = paintTextR.y = paintTextR.width = paintTextR.height = 0;

    boolean centered = false;
    FontMetrics metric = null;
    if (label.getHorizontalAlignment() == SwingConstants.CENTER){
      centered = true;
      try {
        metric = c.getGraphics().getFontMetrics();
        }
      catch (Throwable t2){
        }
      }

    if (icon != null) {
      icon.paintIcon(c, g, paintIconR.x, paintIconR.y);
      }

    if (text != null) {
        // paint multiple lines
        String[] lines = WordWrap.wrap(text,c);

        //int textX = paintTextR.x;
        //int textY = paintTextR.y + fm.getAscent();
        int textX = insets.left;
        int textY = insets.top + fm.getAscent();

        int textHeight = fm.getHeight();

        textY += (h-(lines.length*textHeight))/2;

        for (int i=0;i<lines.length;i++){
          if (centered){
            textX = (w - metric.stringWidth(lines[i]))/2;
            }

          paintWrapText(label, g, lines[i], textX, textY);

          textY += textHeight;
          }

        }
      else {
        // paint single line, as per normal
        int textX = paintTextR.x;
        int textY = paintTextR.y + fm.getAscent();

        String clippedText = layoutCL(label, fm, text, icon, paintViewR, paintIconR, paintTextR);
        paintWrapText(label, g, clippedText, textX, textY);

        }


    }


/**
**********************************************************************************************
NOTE: l.getText() instead of <i>text</i> so that we don't have the "..."
**********************************************************************************************
**/
  public void paintEnabledText(JLabel l, Graphics g, String text, int textLeft, int textTop){
    AquanauticPainter.paintText((Graphics2D)g,l.getText(),textLeft,textTop,l.getForeground());
    }


/**
**********************************************************************************************
NOTE: l.getText() instead of <i>text</i> so that we don't have the "..."
**********************************************************************************************
**/
  public void paintDisabledText(JLabel l, Graphics g, String text, int textLeft, int textTop){
    AquanauticPainter.paintShadowText((Graphics2D)g,l.getText(),textLeft,textTop,AquanauticTheme.COLOR_MID,AquanauticTheme.COLOR_LIGHT);
    }



/**
**********************************************************************************************
NOTE: NOES NOT USE l.getText() because this is used for WRAP and should use the text given to it!
**********************************************************************************************
**/
  public void paintWrapText(JLabel l, Graphics g, String text, int textLeft, int textTop){
    AquanauticPainter.paintText((Graphics2D)g,text,textLeft,textTop,l.getForeground());
    }



  }
