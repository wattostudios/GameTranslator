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
  UI for JProgressBars
**********************************************************************************************
**/

public class AquanauticProgressBarUI extends BasicProgressBarUI {

  private final static AquanauticProgressBarUI buttonUI = new AquanauticProgressBarUI();

  static boolean indeterminateGoingRight = true;
  static int indeterminateLeftPos = 0;


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
  public void paintIndeterminate(Graphics g, JComponent c){
    paint(g,c);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g, JComponent c){
    JProgressBar progress = (JProgressBar) c;

    int w = progress.getWidth();
    int h = progress.getHeight();

    AquanauticPainter.paintCurvedBorder((Graphics2D)g,0,0,w,h);

    if (progress.isIndeterminate()){
      if (indeterminateGoingRight){
        indeterminateLeftPos+=2;
        }
      else {
        indeterminateLeftPos-=2;
        }

      if (indeterminateLeftPos <= 0 || indeterminateLeftPos+50 >= w){
        indeterminateGoingRight = !indeterminateGoingRight;
        }

      AquanauticPainter.paintCurvedGradient((Graphics2D)g,indeterminateLeftPos,0,50,h);

      }
    else {
      Insets inset = progress.getInsets();
      int amountFull = getAmountFull(inset,w,h);

      // current progress
      int thumbWidth = 0;
      int thumbHeight = 0;
      if (progress.getOrientation() == JProgressBar.HORIZONTAL) {
        thumbWidth = amountFull;
        thumbHeight = h;
        }
      else {
        thumbWidth = w;
        thumbHeight = amountFull;
        }

      Graphics t = g.create(0,0,thumbWidth,thumbHeight);

      AquanauticPainter.paintCurvedGradient((Graphics2D)t,0,0,thumbWidth,thumbHeight);

      if (progress.isStringPainted()){
        double complete = progress.getPercentComplete();
        complete = ((double)((int)(complete*1000))/10);

        String text = "" + complete + "%";

        java.awt.FontMetrics metrics = g.getFontMetrics();
        int textHeight =  metrics.getHeight();
        int textWidth =  metrics.stringWidth(text);

        int textTop = thumbHeight - textHeight/2 + (AquanauticTheme.BORDER_WIDTH/2);
        int textLeft = w/2 - textWidth/2;

        AquanauticPainter.paintText((Graphics2D)g,text,textLeft,textTop);
        //g.setColor(Color.BLACK);
        //g.drawString(text,textLeft,textTop);
        }
      }

    }


  }
