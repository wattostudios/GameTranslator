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

import org.watto.component.WSComboBox;
import org.watto.event.WSKeyableInterface;
import org.watto.event.WSKeyableListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;
import javax.swing.plaf.basic.*;

/**
**********************************************************************************************
  UI for JComboBoxs
**********************************************************************************************
**/
public class AquanauticComboBoxUI extends MetalComboBoxUI {


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static ComponentUI createUI(JComponent c) {
    return new AquanauticComboBoxUI();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void installUI(JComponent c) {
    super.installUI(c);

    JComboBox combo = (JComboBox)c;
    combo.setRenderer(new AquanauticComboBoxCurrentItemRenderer());
    //combo.setRequestFocusEnabled(false);
    combo.setRequestFocusEnabled(true);
    combo.setOpaque(false);

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void uninstallUI(JComponent c) {
    super.uninstallUI(c);

    JComboBox combo = (JComboBox)c;
    combo.setRequestFocusEnabled(true);
    combo.setOpaque(true);

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  protected ComboBoxEditor createEditor() {
    MetalComboBoxEditor editor = new MetalComboBoxEditor();
    JTextField edit = (JTextField)editor.getEditorComponent();
    edit.setBackground(AquanauticTheme.COLOR_MID);
    edit.setOpaque(false);
    //edit.setBorder(new EmptyBorder(0,0,0,0));

    // THIS IS PROBABLY WHERE WE SET THE COLOR OF EDITABLE FILECHOOSER FIELDS (JTEXTFIELD)
    // MAYBE CALL setUI() AND PASS IN A SPECIAL UI FOR THE PAINTING

    return editor;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  protected ComboPopup createPopup() {
    AquanauticComboPopup comboPop = new AquanauticComboPopup(comboBox);

    int pad = AquanauticTheme.BORDER_WIDTH;
    comboPop.setBorder(new EmptyBorder(0,0,0,0));

    JList list = comboPop.getList();
    list.setOpaque(false);
    //list.setBackground(AquanauticTheme.COLOR_DARK);
    list.setSelectionBackground(AquanauticTheme.COLOR_MID);
    list.setSelectionForeground(AquanauticTheme.COLOR_TEXT);

    //comboPop.scroller.setOpaque(false);

    return comboPop;

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  protected JButton createArrowButton() {
    JButton button = super.createArrowButton();
    button.setOpaque(false);
    return button;
    }


/**
**********************************************************************************************
  THIS IS FOR COMPATABILITY WITH JAVA 1.5
**********************************************************************************************
**/
  public void paintCurrentValue(Graphics g,Rectangle bounds,boolean hasFocus) {
    ListCellRenderer renderer;
    if (comboBox instanceof WSComboBox){
      renderer = ((WSComboBox)comboBox).getCurrentValueRenderer();
      }
    else {
      renderer = comboBox.getRenderer();
      }

    Component c;

    int padIn = AquanauticTheme.BORDER_WIDTH;

    if (hasFocus && !isPopupVisible(comboBox)) {
      c = renderer.getListCellRendererComponent(listBox,comboBox.getSelectedItem(),-1,true,false);
      }
    else {
      c = renderer.getListCellRendererComponent(listBox,comboBox.getSelectedItem(),-1,false,false);
      c.setBackground(new Color(0,0,0,0));
      }

    c.setFont(comboBox.getFont());

    if (hasFocus && !isPopupVisible(comboBox)) {
      c.setForeground(Color.BLACK);
      c.setBackground(new Color(0,0,0,0));
      //c.setBackground(Color.RED);
      }
    else {
      if (comboBox.isEnabled()) {
        c.setForeground(Color.BLACK);
        c.setBackground(new Color(0,0,0,0));
        //c.setBackground(Color.RED);
        }
      else {
        c.setForeground(AquanauticTheme.COLOR_MID);
        c.setBackground(new Color(0,0,0,0));
        //c.setBackground(Color.RED);
        }
      }

    boolean shouldValidate = false;
    if (c instanceof JPanel)  {
      shouldValidate = true;
      }

    //((JComponent)c).setOpaque(false);
    currentValuePane.paintComponent(g,c,comboBox,bounds.x+padIn,bounds.y,bounds.width-padIn-padIn,bounds.height, shouldValidate);
    }


/**
**********************************************************************************************
  THIS IS FOR COMPATABILITY WITH JAVA 1.5
**********************************************************************************************
**/
  public void paintCurrentValueBackground(Graphics g,Rectangle bounds,boolean hasFocus){

    int w = (int)bounds.getWidth();
    int h = (int)bounds.getHeight();

    //AquanauticPainter.paintGradient(g,0,0,w,h);
    //AquanauticPainter.paintLines(g,0,0,w,h);
    AquanauticPainter.paintCurvedGradient((Graphics2D)g,0,0,w,h);

    }



  }