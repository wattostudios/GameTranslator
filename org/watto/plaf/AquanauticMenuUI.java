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
  UI for JMenus
**********************************************************************************************
**/
public class AquanauticMenuUI extends BasicMenuUI {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent x) {
    return new AquanauticMenuUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);

    JMenu menu = (JMenu) c;
    menu.setOpaque(false);
    menu.setBorderPainted(false);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);

    JMenu menu = (JMenu) c;
    menu.setOpaque(true);
    menu.setBorderPainted(true);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  /*
  public void paint(Graphics g, JComponent c) {
    JMenu menu = (JMenu) c;

    int pad = AquanauticTheme.BORDER_WIDTH;

    int w = menu.getWidth();
    int h = menu.getHeight();


    String text = menu.getText();

    int textLeft = pad;
    int textTop = pad;

    if (!(menu.getParent() instanceof JMenuBar)){
      textTop = pad + 2;
      }

    int underline = menu.getDisplayedMnemonicIndex();


    ButtonModel model = menuItem.getModel();

    if (!model.isEnabled()){
      paintDisabledText(g,text,textLeft,textTop,underline);
      }
    else if (model.isArmed() || model.isSelected() || model.isPressed()) {
      AquanauticPainter.paintPressedBorder((Graphics2D)g,0,0,w,h);
      paintNormalText(g,text,textLeft,textTop,underline);
      //paintSelectedText(g,text,textLeft,textTop,underline);
      }
    else {
      paintNormalText(g,text,textLeft,textTop,underline);
      }

    if (! menu.isTopLevelMenu()){
      int iconLeft = w - pad - arrowIcon.getIconWidth();
      int iconTop = h/2 - arrowIcon.getIconHeight()/2;
      arrowIcon.paintIcon(c, g, iconLeft,iconTop);
      }

    }
  */


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

      Shape oldClip = g.getClip();
      g.setClip(0,0,w,h);

      if (menu.getParent() instanceof JPopupMenu){
        // sub-menu
        AquanauticPainter.paintCurvedGradient((Graphics2D)g,0,0,w+10,h);
        }
      else {
        // main menu
        int pad = AquanauticTheme.BORDER_WIDTH;
        AquanauticPainter.paintCurvedSolid((Graphics2D)g,pad/2,0,w-pad/2,h+10,AquanauticTheme.COLOR_MID);
        }
      g.setClip(oldClip);

      }
    //else if (!(menu.getParent() instanceof JPopupMenu)) {
    //  // need to paint the background deliberately for top-level menus, else the border stays
    //  int pad = AquanauticTheme.BORDER_WIDTH;
    //  AquanauticPainter.paintBackground((Graphics2D)g,0,-pad,w,h+pad);
    //  }
    //else {
    //  if (menu.isOpaque()){
    //    g.setColor(AquanauticTheme.COLOR_BG);
    //    g.fillRect(0,0,w,h);
    //    }
    //  }


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

    if (!(menuItem.getParent() instanceof JPopupMenu)){
      // main menu
      textLeft += pad/2;
      }

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
      if (model.isArmed() || (menuItem instanceof JMenu && model.isSelected())) {
        AquanauticPainter.paintUnderlineShadowText((Graphics2D)g,text,underline,textLeft,textTop);
        //g.setColor(AquanauticTheme.COLOR_TEXT);
        //BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,underline,textLeft+1,textTop+1);
        //g.setColor(AquanauticTheme.COLOR_BG);
        //BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,underline,textLeft,textTop);
        }
      else {
        AquanauticPainter.paintUnderlineText((Graphics2D)g,text,underline,textLeft,textTop);
        //g.setColor(AquanauticTheme.COLOR_TEXT);
        //BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,underline,textLeft,textTop);
        }
      }

    }




/**
**********************************************************************************************

**********************************************************************************************
**/
  /*
  public void paintNormalText(Graphics g, String text, int textLeft, int textTop, int underline){
    AquanauticPainter.enableAntialias(g);

    FontMetrics metrics = g.getFontMetrics();
    int textHeight =  metrics.getHeight();

    textTop += textHeight/2;

    g.setColor(Color.BLACK);
    BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,underline,textLeft,textTop);
    }
  */


/**
**********************************************************************************************

**********************************************************************************************
**/
  /*
  public void paintSelectedText(Graphics g, String text, int textLeft, int textTop, int underline){
    FontMetrics metrics = g.getFontMetrics();
    int textHeight =  metrics.getHeight();

    textTop += textHeight/2;


    //g.setColor(AquanauticTheme.COLOR_DARK);
    //BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,underline,textLeft-1,textTop-1);
    //BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,underline,textLeft-1,textTop+1);
    //BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,underline,textLeft+1,textTop+1);
    //BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,underline,textLeft+1,textTop-1);


    g.setColor(AquanauticTheme.COLOR_LIGHT);
    BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,underline,textLeft,textTop);
    }
  */


/**
**********************************************************************************************

**********************************************************************************************
**/
  /*
  public void paintDisabledText(Graphics g, String text, int textLeft, int textTop, int underline){
    FontMetrics metrics = g.getFontMetrics();
    int textHeight =  metrics.getHeight();

    textTop += textHeight/2;

    g.setColor(AquanauticTheme.COLOR_LIGHT);
    BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,underline,textLeft+1,textTop+1);

    g.setColor(AquanauticTheme.COLOR_MID);
    BasicGraphicsUtils.drawStringUnderlineCharAt(g,text,underline,textLeft,textTop);
    }
  */


  }