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
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicGraphicsUtils;

/**
**********************************************************************************************
Paints backgrounds, borders, and texts
**********************************************************************************************
**/
public class AquanauticPainter {


/**
**********************************************************************************************
Turns on anti-aliasing
**********************************************************************************************
**/
  public static void enableAntialias(Graphics g){
    enableAntialias((Graphics2D)g);
    }


/**
**********************************************************************************************
Turns on anti-aliasing
**********************************************************************************************
**/
  public static void enableAntialias(Graphics2D g2){
    if (AquanauticTheme.USE_ANTIALIAS){
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      }
    }


/**
**********************************************************************************************
If the component is opaque, it paints the background in color COLOR_BG
**********************************************************************************************
**/
  public static void paintOpaque(Graphics g, JComponent c){
    if (c.isOpaque()){
      g.setColor(AquanauticTheme.COLOR_BG);
      g.fillRect(0,0,c.getWidth(),c.getHeight());
      }
    }


//////////////////
//
// BORDER
//
/////////////////


/**
**********************************************************************************************
Paints a curved border
**********************************************************************************************
**/
  public static void paintCurvedBorder(Graphics2D g2, int x, int y, int w, int h){
    paintCurvedBorder(g2,x,y,w,h,AquanauticTheme.COLOR_DARK,AquanauticTheme.COLOR_LIGHT);
    }


/**
**********************************************************************************************
Paints a curved border
**********************************************************************************************
**/
  public static void paintCurvedBorder(Graphics2D g2, int x, int y, int w, int h, Color border, Color gloss){
    int round = AquanauticTheme.ROUNDNESS;

    enableAntialias(g2);

    // GLOSS
    g2.setColor(gloss);
    g2.drawRoundRect(x+2,y+2,w-5,h-5,round,round);

    // BORDER
    g2.setColor(border);
    g2.drawRoundRect(x+1,y+1,w-3,h-3,round,round);
    }


/**
**********************************************************************************************
Paints a square border
**********************************************************************************************
**/
  public static void paintSquareBorder(Graphics2D g2, int x, int y, int w, int h){
    paintSquareBorder(g2,x,y,w,h,AquanauticTheme.COLOR_DARK,AquanauticTheme.COLOR_LIGHT);
    }


/**
**********************************************************************************************
Paints a square border
**********************************************************************************************
**/
  public static void paintSquareBorder(Graphics2D g2, int x, int y, int w, int h, Color border, Color gloss){
    enableAntialias(g2);

    // GLOSS
    g2.setColor(gloss);
    g2.drawRect(x+2,y+2,w-5,h-5);

    // BORDER
    g2.setColor(border);
    g2.drawRect(x+1,y+1,w-3,h-3);
    }



//////////////////
//
// BACKGROUND
//
/////////////////


/**
**********************************************************************************************
Paints a gradient background
**********************************************************************************************
**/
  public static void paintGradientBackground(Graphics2D g2, int x, int y, int w, int h){
    paintGradientBackground(g2,x,y,w,h,AquanauticTheme.COLOR_MID,AquanauticTheme.COLOR_LIGHT);
    }


/**
**********************************************************************************************
Paints a gradient background
**********************************************************************************************
**/
  public static void paintGradientBackground(Graphics2D g2, int x, int y, int w, int h, Color startGradient, Color finishGradient){
    enableAntialias(g2);

    g2.setPaint(new GradientPaint(0,y,startGradient,0,h+y,finishGradient,false));
    g2.fillRect(x,y,w,h);
    }


/**
**********************************************************************************************
Paints a solid color background
**********************************************************************************************
**/
  public static void paintSolidBackground(Graphics2D g2, int x, int y, int w, int h, Color background){
    enableAntialias(g2);

    g2.setColor(background);
    g2.fillRect(x,y,w,h);
    }



//////////////////
//
// COMBO - BACKGROUND + BORDER
//
/////////////////


/**
**********************************************************************************************
Paints a curved border with a gradient background
**********************************************************************************************
**/
  public static void paintCurvedGradient(Graphics2D g2, int x, int y, int w, int h){
    paintCurvedGradient(g2,x,y,w,h,AquanauticTheme.COLOR_MID,AquanauticTheme.COLOR_LIGHT);
    }


/**
**********************************************************************************************
Paints a curved border with a gradient background
**********************************************************************************************
**/
  public static void paintCurvedGradient(Graphics2D g2, int x, int y, int w, int h, Color startGradient, Color finishGradient){
    int round = AquanauticTheme.ROUNDNESS;

    // BACKGROUND
    Shape clip = g2.getClip();
    g2.clip(new RoundRectangle2D.Float(x+1,y+1,w-3,h-3,round,round));
    paintGradientBackground(g2,x,y,w,h,startGradient,finishGradient);
    g2.setClip(clip);

    // BORDER
    paintCurvedBorder(g2,x,y,w,h);
    }


/**
**********************************************************************************************
Paints a curved border with a gradient background
**********************************************************************************************
**/
  public static void paintCurvedGradient(Graphics2D g2, int x, int y, int w, int h, Color border, Color gloss, Color startGradient, Color finishGradient){
    int round = AquanauticTheme.ROUNDNESS;

    // BACKGROUND
    Shape clip = g2.getClip();
    g2.clip(new RoundRectangle2D.Float(x+1,y+1,w-3,h-3,round,round));
    paintGradientBackground(g2,x,y,w,h,startGradient,finishGradient);
    g2.setClip(clip);

    // BORDER
    paintCurvedBorder(g2,x,y,w,h,border,gloss);
    }


/**
**********************************************************************************************
Paints a curved border with a solid background
**********************************************************************************************
**/
  public static void paintCurvedSolid(Graphics2D g2, int x, int y, int w, int h){
    paintCurvedSolid(g2,x,y,w,h,AquanauticTheme.COLOR_MID);
    }


/**
**********************************************************************************************
Paints a curved border with a solid background
**********************************************************************************************
**/
  public static void paintCurvedSolid(Graphics2D g2, int x, int y, int w, int h, Color background){
    int round = AquanauticTheme.ROUNDNESS;

    // BACKGROUND
    Shape clip = g2.getClip();
    g2.clip(new RoundRectangle2D.Float(x+1,y+1,w-3,h-3,round,round));
    paintSolidBackground(g2,x,y,w,h,background);
    g2.setClip(clip);

    // BORDER
    paintCurvedBorder(g2,x,y,w,h);
    }


/**
**********************************************************************************************
Paints a curved border with a solid background
**********************************************************************************************
**/
  public static void paintCurvedSolid(Graphics2D g2, int x, int y, int w, int h, Color border, Color gloss, Color background){
    int round = AquanauticTheme.ROUNDNESS;

    // BACKGROUND
    Shape clip = g2.getClip();
    g2.clip(new RoundRectangle2D.Float(x+1,y+1,w-3,h-3,round,round));
    paintSolidBackground(g2,x,y,w,h,background);
    g2.setClip(clip);

    // BORDER
    paintCurvedBorder(g2,x,y,w,h,border,gloss);
    }


/**
**********************************************************************************************
Paints a square border with a gradient background
**********************************************************************************************
**/
  public static void paintSquareGradient(Graphics2D g2, int x, int y, int w, int h){
    paintSquareGradient(g2,x,y,w,h,AquanauticTheme.COLOR_MID,AquanauticTheme.COLOR_LIGHT);
    }


/**
**********************************************************************************************
Paints a square border with a gradient background
**********************************************************************************************
**/
  public static void paintSquareGradient(Graphics2D g2, int x, int y, int w, int h, Color startGradient, Color finishGradient){
    // BACKGROUND
    Shape clip = g2.getClip();
    g2.clip(new Rectangle2D.Float(x+1,y+1,w-3,h-3));
    paintGradientBackground(g2,x,y,w,h,startGradient,finishGradient);
    g2.setClip(clip);

    // BORDER
    paintSquareBorder(g2,x,y,w,h);
    }


/**
**********************************************************************************************
Paints a square border with a gradient background
**********************************************************************************************
**/
  public static void paintSquareGradient(Graphics2D g2, int x, int y, int w, int h, Color border, Color gloss, Color startGradient, Color finishGradient){
    // BACKGROUND
    Shape clip = g2.getClip();
    g2.clip(new Rectangle2D.Float(x+1,y+1,w-3,h-3));
    paintGradientBackground(g2,x,y,w,h,startGradient,finishGradient);
    g2.setClip(clip);

    // BORDER
    paintSquareBorder(g2,x,y,w,h,border,gloss);
    }


/**
**********************************************************************************************
Paints a square border with a solid background
**********************************************************************************************
**/
  public static void paintSquareSolid(Graphics2D g2, int x, int y, int w, int h){
    paintSquareSolid(g2,x,y,w,h,AquanauticTheme.COLOR_MID);
    }


/**
**********************************************************************************************
Paints a square border with a solid background
**********************************************************************************************
**/
  public static void paintSquareSolid(Graphics2D g2, int x, int y, int w, int h, Color background){
    // BACKGROUND
    Shape clip = g2.getClip();
    g2.clip(new Rectangle2D.Float(x+1,y+1,w-3,h-3));
    paintSolidBackground(g2,x,y,w,h,background);
    g2.setClip(clip);

    // BORDER
    paintSquareBorder(g2,x,y,w,h);
    }


/**
**********************************************************************************************
Paints a square border with a solid background
**********************************************************************************************
**/
  public static void paintSquareSolid(Graphics2D g2, int x, int y, int w, int h, Color border, Color gloss, Color background){
    // BACKGROUND
    Shape clip = g2.getClip();
    g2.clip(new Rectangle2D.Float(x+1,y+1,w-3,h-3));
    paintSolidBackground(g2,x,y,w,h,background);
    g2.setClip(clip);

    // BORDER
    paintSquareBorder(g2,x,y,w,h,border,gloss);
    }



//////////////////
//
// TEXT
//
/////////////////



/**
**********************************************************************************************
Paints a text string
**********************************************************************************************
**/
  public static void paintText(Graphics2D g2, String text, int x, int y){
    paintText(g2,text,x,y,AquanauticTheme.COLOR_TEXT);
    }


/**
**********************************************************************************************
Paints a text string
**********************************************************************************************
**/
  public static void paintText(Graphics2D g2, String text, int x, int y, Color textColor){
    enableAntialias(g2);

    g2.setColor(textColor);
    g2.drawString(text,x,y);
    }


/**
**********************************************************************************************
Paints a text string, with a shadow
**********************************************************************************************
**/
  public static void paintShadowText(Graphics2D g2, String text, int x, int y){
    paintShadowText(g2,text,x,y,AquanauticTheme.COLOR_BG,AquanauticTheme.COLOR_TEXT);
    }


/**
**********************************************************************************************
Paints a text string, with a shadow
**********************************************************************************************
**/
  public static void paintShadowText(Graphics2D g2, String text, int x, int y, Color textColor, Color shadowColor){
    paintText(g2,text,x+1,y+1,shadowColor);
    paintText(g2,text,x,y,textColor);
    }


/**
**********************************************************************************************
Paints a text string, underlining the given character
**********************************************************************************************
**/
  public static void paintUnderlineText(Graphics2D g2, String text, int underline, int x, int y){
    paintUnderlineText(g2,text,underline,x,y,AquanauticTheme.COLOR_TEXT);
    }


/**
**********************************************************************************************
Paints a text string, underlining the given character
**********************************************************************************************
**/
  public static void paintUnderlineText(Graphics2D g2, String text, int underline, int x, int y, Color textColor){
    enableAntialias(g2);

    g2.setColor(textColor);
    BasicGraphicsUtils.drawStringUnderlineCharAt(g2,text,underline,x,y);
    }


/**
**********************************************************************************************
Paints a text string, underlining the given character, and with a shadow
**********************************************************************************************
**/
  public static void paintUnderlineShadowText(Graphics2D g2, String text, int underline, int x, int y){
    paintUnderlineShadowText(g2,text,underline,x,y,AquanauticTheme.COLOR_BG,AquanauticTheme.COLOR_TEXT);
    }


/**
**********************************************************************************************
Paints a text string, underlining the given character, and with a shadow
**********************************************************************************************
**/
  public static void paintUnderlineShadowText(Graphics2D g2, String text, int underline, int x, int y, Color textColor, Color shadowColor){
    paintUnderlineText(g2,text,underline,x+1,y+1,shadowColor);
    paintUnderlineText(g2,text,underline,x,y,textColor);
    }




//////////////////
//
// OTHER
//
/////////////////


/**
**********************************************************************************************
Paints a cross, as in a CheckBox
**********************************************************************************************
**/
  public static void paintCross(Graphics2D g2, int x, int y, int w, int h){
    g2.setColor(AquanauticTheme.COLOR_TEXT);
    g2.drawLine(x+4,y+4,w-5,h-5);
    g2.drawLine(x+4,h-5,w-5,y+4);
    }


/**
**********************************************************************************************
Paints a dot, as in a RadioButton
**********************************************************************************************
**/
  public static void paintDot(Graphics2D g2, int x, int y, int w, int h){
    int round = AquanauticTheme.ROUNDNESS;

    g2.setColor(AquanauticTheme.COLOR_TEXT);
    g2.fillRoundRect(x+4,y+4,w-8,h-8,round,round);
    }




  }