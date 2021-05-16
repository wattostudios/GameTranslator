////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                      GAME TRANSLATOR                                       //
//                            Game Language Translation Assistant                             //
//                                 http://www.watto.org/trans                                 //
//                                                                                            //
//                           Copyright (C) 2006-2009  WATTO Studios                           //
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

import org.watto.Settings;
import org.watto.component.*;
import org.watto.event.WSClickableInterface;
import org.watto.plaf.AquanauticTheme;
import org.watto.plaf.ThemeLoader;
import org.watto.xml.*;

import java.awt.BorderLayout;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
//import javax.swing.Popup;
//import javax.swing.PopupFactory;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSOptionButton_Font extends WSOptionPanel implements WSClickableInterface {

  WSButton button;
  WSTextField textField;
  WSPopupMenu popup;
  WSFontChooser fontChooser = null;


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSOptionButton_Font(XMLNode node){
    // NEED TO DO THIS HERE, OTHERWISE THE SETTING VARIABLE DOESN'T GET SAVED!!! (not sure why)
    //super(node);
    super();
    buildObject(node);
    registerEvents();
    //System.out.println("SETTING for " + getCode() + " = " + getSetting());
    }



///////////////
//
// Class-Specific Methods
//
///////////////


/**
**********************************************************************************************
Build this object from the <i>node</i>
@param node the XML node that indicates how to build this object
**********************************************************************************************
**/
  public void buildObject(XMLNode node){
    super.buildObject(node);

    setLayout(new BorderLayout());

    textField = new WSTextField(XMLReader.readString("<WSTextField code=\"SelectedFont\" showLabel=\"true\" editable=\"false\" />"));
    add(textField,BorderLayout.CENTER);

    button = new WSButton(XMLReader.readString("<WSButton code=\"" + setting + "\" />"));
    add(button,BorderLayout.EAST);

    textField.setText(AquanauticTheme.FONT.getName());

    //if (icon != null){
    //  JLabel label = new JLabel(icon);
    //  add(label,BorderLayout.WEST);
    //  }
    //setSetting(setting);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public String getComparison(){
    return button.getText();
    }


/**
**********************************************************************************************
The click event
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onClick(JComponent c, MouseEvent e){
    if (c instanceof WSComponent){
      String code = ((WSComponent)c).getCode();
      //System.out.println("COMPONENT CLICKING " + code);

      if (code.equals("CurrentFont")){
        // show the popup
        fontChooser = new WSFontChooser(new XMLNode());

        WSPanel panel = new WSPanel(new XMLNode());
        panel.add(fontChooser,BorderLayout.CENTER);
        panel.add(new WSButton(XMLReader.readString("<WSButton code=\"FontChooserClosePopup\" />")),BorderLayout.SOUTH);

        if (popup != null && popup.isVisible()){
          popup.setVisible(false);
          }
        popup = new WSPopupMenu(new XMLNode());
        popup.add(panel);
        popup.setOpaque(true);

        popup.show(this,0,0);
        }
      else if (code.equals("FontChooserClosePopup")){
        //closing the popup happens automatically, we just want to trigger the setting of the new font
        if (fontChooser != null){
          popup.setVisible(false);
          AquanauticTheme.setFont(fontChooser.getFont());
          ThemeLoader.setLookAndFeel("Aquanautic");

          //SwingUtilities.updateComponentTreeUI(GameTranslator.getInstance());
          //SwingUtilities.updateComponentTreeUI((JComponent)WSRepository.get("MainWindowFrame"));
          //GameTranslator.getInstance().reload();
          //SwingUtilities.updateComponentTreeUI((JComponent)WSRepository.get("Option_Appearance_CurrentFont"));

          //SwingUtilities.updateComponentTreeUI((JComponent)WSRepository.get("MainWindowFrame"));
          GameTranslator.getInstance().rebuild();
          }
        }
      }

    return true;
    }


/**
**********************************************************************************************
Processes the given event
@param event the event that was triggered
**********************************************************************************************
**/
  public void processEvent(AWTEvent event){
    if (event instanceof java.awt.event.MouseEvent){
      //System.out.println("" + event);
      }
    super.processEvent(event);
    }




  }