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

import org.watto.component.WSTextField;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.plaf.basic.*;

/**
**********************************************************************************************
  UI for JTextField
**********************************************************************************************
**/

public class AquanauticTextFieldUI extends BasicTextFieldUI {

  JTextField tf;
  boolean borderSet = false;

/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticTextFieldUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);
    tf = (JTextField)c;
    tf.setMinimumSize(new Dimension(tf.getHeight(),0));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paintSafely(Graphics g){
    if (tf instanceof WSTextField){
      if (! tf.isOpaque()){
        // forces the painting of the background if the component is !opaque
        // because if opaque it doesn't normally call paintBackground();
        paintBackground(g);
        }
      }
    super.paintSafely(g);
    }



/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paintBackground(Graphics g){
  //public void paintSafely(Graphics g){
    WSTextField wstf = null;
    if (tf instanceof WSTextField){
      wstf = (WSTextField)tf;
      }

    if (!borderSet){
      // Sets the border on the editor textField so that it fits correctly inside the
      // table and list cells of a JFileChooser dialog
      if (tf.getParent().getClass().toString().indexOf("MetalFileChooserUI") >= 0 && tf.getBorder().getBorderInsets(tf).bottom != 2){
        tf.setBorder(new EmptyBorder(2,2,2,2));
        }
      borderSet = true;
      }

    int x = 0;
    int y = 0;
    int w = tf.getWidth();
    int h = tf.getHeight();

    int pad = AquanauticTheme.BORDER_WIDTH;
    int th = AquanauticTheme.TEXT_HEIGHT;

    if (wstf != null && wstf.getShowLabel()){
      y += th;
      h -= th;
      }

    AquanauticPainter.paintOpaque(g,tf);

    if (! (tf.getParent() instanceof JTable)){
      AquanauticPainter.paintCurvedGradient((Graphics2D)g,x,y,w,h);
      }
    else {
      // paint an opaque background - it is an inline editor
      AquanauticPainter.paintGradientBackground((Graphics2D)g,x,y,w,h);
      }

    // paint the tooltip in the background
    /*String text = tf.getText();
    if (text == null || text.equals("")){
      String toolTipText = tf.getToolTipText();
      if (toolTipText != null && toolTipText.length() > 0){
        toolTipText = "Tip: " + toolTipText;
        Font originalFont = g.getFont();

        g.setFont(originalFont.deriveFont(Font.BOLD+Font.ITALIC));
        g.setColor(AquanauticTheme.COLOR_TOOLTIP);
        g.drawString(toolTipText,pad,h+1);
        g.setFont(originalFont);
        }
      }
    */

    if (wstf != null && wstf.getShowLabel()){
      // paint the label
      String labelText = wstf.getLabel();

      // set the font before getting the metrics
      //g.setFont(g.getFont().deriveFont(Font.BOLD));
      g.setFont(AquanauticTheme.FONT.deriveFont(Font.BOLD));

      FontMetrics metrics = g.getFontMetrics();
      int textHeight = metrics.getHeight();
      int textWidth = metrics.stringWidth(labelText);

      // paint the border around the label
      g.setColor(AquanauticTheme.COLOR_DARK);
      g.setClip(0,0,textWidth+pad+pad+pad/2,th+3);
      //g.clipRect(textHeight/4*3,pad/2+2,textWidth+pad+pad,1);
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


    /*
    g.setFont(AquanauticTheme.FONT);

    FontMetrics metrics = g.getFontMetrics();
    int textHeight = metrics.getHeight();


    // paint the text
    Shape clip = g.getClip();

    int clipWidth = w-pad-pad;
    g.setClip(new Rectangle2D.Float(pad,th+pad,clipWidth,textHeight));

    String text = tf.getText();
    int textWidth = metrics.stringWidth(text);

    y += textHeight - pad/2 + th;
    x += pad;
    if (textWidth > clipWidth){
      x = clipWidth - textWidth;
      }

    // paint the highlighted background
    int startSelection = tf.getSelectionStart();
    int endSelection = tf.getSelectionEnd();

    if (startSelection > 0 && endSelection > 0){
      int selectedWidth = metrics.stringWidth(text.substring(startSelection,endSelection));
      int selectedLeft = x + metrics.stringWidth(text.substring(0,startSelection));
      int selectedTop = th+pad;

      AquanauticPainter.paintSolidBackground((Graphics2D)g,selectedLeft,selectedTop,selectedWidth,textHeight,AquanauticTheme.COLOR_MID);
      }

    AquanauticPainter.paintText((Graphics2D)g,text,x,y);

    g.setClip(clip);
    */

    }



  }
