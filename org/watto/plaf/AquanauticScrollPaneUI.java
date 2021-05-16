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

import org.watto.component.WSScrollPane;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.metal.*;

/**
**********************************************************************************************
  UI for JScrollPanes
**********************************************************************************************
**/

public class AquanauticScrollPaneUI extends MetalScrollPaneUI {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticScrollPaneUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);

    JScrollPane sp = (JScrollPane)c;

    int pad = AquanauticTheme.BORDER_WIDTH;

    //JViewport colHeader = sp.getColumnHeader();
    //if (colHeader != null){
    //  sp.setViewportBorder(new EmptyBorder(0,pad,pad,pad));
    //  ((JComponent)colHeader.getView()).setBorder(new EmptyBorder(0,pad,0,pad));
    //  }
    //else {
      if (sp instanceof WSScrollPane){
        if (((WSScrollPane)sp).getShowLabel()){
          // showing a label
          sp.setViewportBorder(new EmptyBorder(2,pad,pad,pad));
          }
        else {
          // no label
          sp.setViewportBorder(new EmptyBorder(pad,pad,pad,pad));
          }
        }
    //  }

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g, JComponent c){
    JScrollPane scrollPane = (JScrollPane)c;

    WSScrollPane wssp = null;
    if (scrollPane instanceof WSScrollPane){
      wssp = (WSScrollPane)scrollPane;
      }

    int bor = AquanauticTheme.BORDER_WIDTH;

    int x = 0;
    int y = 0;
    int w = c.getWidth();
    int h = c.getHeight();

    int pad = AquanauticTheme.BORDER_WIDTH;
    int th = AquanauticTheme.TEXT_HEIGHT;

    if (wssp != null && wssp.getShowLabel()){
      y += th;
      h -= th;
      }

    //AquanauticPainter.paintOpaque(g,c);


    if (wssp != null && ! wssp.getShowBorder()){
      // don't show the border
      if (wssp != null && wssp.getShowBackground()){
        // paint the white background bit if the attribute = true
        AquanauticPainter.paintSolidBackground((Graphics2D)g,x,y,w,h,AquanauticTheme.COLOR_BG);
        }
      else {
        }
      }
    else {
      //AquanauticPainter.paintScrollPaneBorder((Graphics2D)g,x,y,w,h);
      AquanauticPainter.paintSquareSolid((Graphics2D)g,x,y,w,h);
      if ((wssp != null && wssp.getShowBackground()) || wssp == null){
        // paint the white background bit if it is a JComponent (ie not WSComponent) or if the attribute = true
        AquanauticPainter.paintSolidBackground((Graphics2D)g,x+4,y+4,w-8,h-8,AquanauticTheme.COLOR_BG);
        }
      }

    if (! (c.getParent() instanceof JSplitPane)){
      //AquanauticPainter.paintBorder((Graphics2D)g,x,y,w,h);
      }


    if (wssp != null && wssp.getShowLabel()){
      // paint the label
      String labelText = wssp.getLabel();

      // set the font before getting the metrics
      //g.setFont(g.getFont().deriveFont(Font.BOLD));
      g.setFont(AquanauticTheme.FONT.deriveFont(Font.BOLD));

      FontMetrics metrics = g.getFontMetrics();
      int textHeight = metrics.getHeight();
      int textWidth = metrics.stringWidth(labelText);

      // paint the border around the label
      g.setColor(AquanauticTheme.COLOR_DARK);
      // don't want to clip the border for this one, because we want the border to go fully around the label,
      // instead of looking just like a tab.
      //g.setClip(0,0,textWidth+pad+pad+pad+pad/2,th+3);
      //g.clipRect(textHeight/4*3,pad/2+2,textWidth+pad+pad,1);
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
