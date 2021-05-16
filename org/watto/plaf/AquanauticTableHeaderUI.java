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

import org.watto.component.WSHelper;

import java.awt.*;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.plaf.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.*;
import java.net.URL;

/**
**********************************************************************************************
  UI for JTableHeaders
**********************************************************************************************
**/

public class AquanauticTableHeaderUI extends BasicTableHeaderUI {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticTableHeaderUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paint(Graphics g, JComponent c){
    JTableHeader header = (JTableHeader) c;
    JTable table = header.getTable();

    int pad = AquanauticTheme.MENU_BORDER_WIDTH;

    TableColumnModel model = (TableColumnModel)header.getColumnModel();

    int x = 0;
    int h = header.getHeight();

    FontMetrics metrics = g.getFontMetrics();

    ImageIcon editableIcon = null;
    int iconWidth = 0;
    int iconHeight = 0;
    URL icon = WSHelper.getResource("images/WSTableHeader/editable.png");
    if (icon != null){
      editableIcon = new ImageIcon(icon);
      iconWidth = editableIcon.getIconWidth();
      iconHeight = editableIcon.getIconHeight();
      }

    for (int i=0;i<model.getColumnCount();i++){
      TableColumn column = model.getColumn(i);
      boolean editable = false;

      try {
        editable = table.isCellEditable(0,i);
        if (editableIcon == null){
          editable = false;
          }
        }
      catch (Throwable t){
        // ignore errors from non-WSTable components
        // (even though this class is only used by WSTable)
        editable = false;
        }

      String text = column.getHeaderValue().toString();
      int textWidth = metrics.stringWidth(text);

      int w = column.getWidth();
      int wDraw = w;

      if (editable){
        w -= (iconWidth+pad); // need 6 for the editable icon
        }

      while (textWidth > w){
        // resize the header text so it fits
        int lastSpace = text.lastIndexOf(" ");
        if (lastSpace > 0){
          // cut the last word off
          text = text.substring(0,lastSpace);
          }
        else {
          // cut letters off
          text = text.substring(0,text.length()-1);
          }

        textWidth = metrics.stringWidth(text);

        if (text.equals("")){
          break;
          }

        }

      AquanauticPainter.paintSquareGradient((Graphics2D)g,x-1,-1,wDraw+2,h+1);

      if (editable){
        editableIcon.paintIcon(c,g,x+pad,(h-iconHeight)/2);
        }

      int textLeft = x + (w/2 - textWidth/2);
      if (editable){
        textLeft += iconWidth + pad;
        }
      int textTop = pad+pad;

      paintEnabledText(header,g,text,textLeft,textTop);

      x += wDraw;
      }


    }



/**
**********************************************************************************************

**********************************************************************************************
**/
  public void paintEnabledText(JTableHeader l, Graphics g, String text, int textLeft, int textTop){
    FontMetrics metrics = g.getFontMetrics();
    int textHeight =  metrics.getHeight();

    textTop += textHeight/2;

    AquanauticPainter.paintText((Graphics2D)g,text,textLeft,textTop);
    //g.setColor(Color.BLACK);
    //g.drawString(text,textLeft,textTop);
    }



  }
