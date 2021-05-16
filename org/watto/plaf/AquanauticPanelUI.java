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

import org.watto.component.WSPanel;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

/**
**********************************************************************************************
  UI for JPanels
**********************************************************************************************
**/

public class AquanauticPanelUI extends BasicPanelUI {

  private final static AquanauticPanelUI buttonUI = new AquanauticPanelUI();


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
  public void installUI(JComponent c) {
    super.installUI(c);
    //c.setOpaque(false);
    //c.setBackground(Color.WHITE);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);
    //c.setOpaque(true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g, JComponent c){
    int pad = AquanauticTheme.BORDER_WIDTH;
    int th = AquanauticTheme.TEXT_HEIGHT;

    int x = 0;
    int y = 0;
    int w = c.getWidth();
    int h = c.getHeight();

    AquanauticPainter.paintOpaque(g,c);

    if (c.getParent() instanceof JLayeredPane){
      AquanauticPainter.paintSolidBackground((Graphics2D)g,0,0,w,h,AquanauticTheme.COLOR_BG);
      }


    WSPanel wsp = null;
    if (c instanceof WSPanel){
      wsp = (WSPanel)c;
      }


    if (wsp != null && wsp.getShowLabel()){
      y += th;
      h -= th;
      }


    if (wsp != null && wsp.getShowBorder()){
      //AquanauticPainter.paintScrollPaneBorder((Graphics2D)g,x,y,w,h);
      AquanauticPainter.paintCurvedGradient((Graphics2D)g,x,y,w,h);
      //AquanauticPainter.paintGradientBackground((Graphics2D)g,x+4,y+4,w-8,h-8);
      }


    if (wsp != null && wsp.getShowLabel()){
      // paint the label
      String labelText = wsp.getLabel();

      // set the font before getting the metrics
      //g.setFont(g.getFont().deriveFont(Font.BOLD));
      g.setFont(AquanauticTheme.FONT.deriveFont(Font.BOLD));

      FontMetrics metrics = g.getFontMetrics();
      int textHeight = metrics.getHeight();
      int textWidth = metrics.stringWidth(labelText);

      // paint the border around the label
      g.setColor(AquanauticTheme.COLOR_DARK);

      if (!wsp.getShowBorder()){
        // clip - we only want the top border
        g.setClip(0,0,w,th+4);
        //AquanauticPainter.paintScrollPaneBorder((Graphics2D)g,x,y,w,h);
        AquanauticPainter.paintSquareSolid((Graphics2D)g,x,y,w,h);
        AquanauticPainter.paintSolidBackground((Graphics2D)g,x+4,y+4,w-8,h-8,AquanauticTheme.COLOR_BG);
        g.setClip(null);
        }

      //AquanauticPainter.paintFillBorder((Graphics2D)g,pad/2,0,textWidth+pad+pad,th+pad+pad,AquanauticTheme.COLOR_MID);
      AquanauticPainter.paintCurvedSolid((Graphics2D)g,pad/2,0,textWidth+pad+pad,th+pad+pad,AquanauticTheme.COLOR_MID);
      g.setClip(null);

      /*
      // paint the text
      AquanauticPainter.enableAntialias(g);
      //g.setFont(g.getFont().deriveFont(Font.BOLD));

      //g.setColor(AquanauticTheme.COLOR_LIGHT);
      g.setColor(AquanauticTheme.COLOR_TEXT);
      g.drawString(labelText,pad+pad/2+1,pad+textHeight/2+1);

      //g.setColor(AquanauticTheme.COLOR_TEXT);
      g.setColor(AquanauticTheme.COLOR_BG);
      g.drawString(labelText,pad+pad/2,pad+textHeight/2);
      */
      AquanauticPainter.paintShadowText((Graphics2D)g,labelText,pad+pad/2,pad+textHeight/2);

      }


    }


  }
