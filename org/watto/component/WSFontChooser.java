////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                            WATTO STUDIOS JAVA COMPONENT TOOLKIT                            //
//                    A Collection Of Components To Build Java Applications                   //
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

package org.watto.component;

import org.watto.*;
import org.watto.event.*;
import org.watto.xml.*;

import java.awt.*;
import java.io.File;
import javax.swing.JComponent;


/**
**********************************************************************************************
A ExtendedTemplate
**********************************************************************************************
**/
public class WSFontChooser extends WSPanel implements WSSelectableInterface,
                                                      WSEnterableInterface,
                                                      WSEventableInterface{


  // the components generated from the XML, that we need to reference frequently
  WSList fontNameChooser;
  WSList fontSizeChooser;
  WSList fontStyleChooser;

  WSTextField fontName;
  WSTextField fontSize;
  WSTextField fontStyle;

  WSSmallColorChooser fontColor;

  WSLabel fontPreview;

  /** removes the reiteration when loading the interface **/
  boolean building = false;


  // is a keyPress event being done?
  boolean doingKeyPress = false;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSFontChooser(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSFontChooser(XMLNode node){
    super(node);
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

    setOpaque(true);

    setLayout(new BorderLayout());

    // Build an XMLNode tree containing all the elements on the screen
    XMLNode srcNode = XMLReader.read(new File(Settings.getString("WSFontChooserXML")));

    // Build the components from the XMLNode tree
    Component component = WSHelper.buildComponent(srcNode);
    add(component,BorderLayout.CENTER);

    // setting up this object in the repository
    setCode(((WSComponent)component).getCode());
    //WSRepository.add(this);

    loadDefaults();
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public Font getFont(){
    try {
      Font font = Font.decode(fontName.getText());
      float size = Float.parseFloat(fontSize.getText());
      int style = Font.PLAIN;

      String styleChosen = fontStyle.getText();
      if (styleChosen.equals(Language.get("WSFontChooser_FontStyle_Italic"))){
        style = Font.ITALIC;
        }
      else if (styleChosen.equals(Language.get("WSFontChooser_FontStyle_Bold"))){
        style = Font.BOLD;
        }
      else {
        style = Font.PLAIN;
        }

      if (styleChosen.equals(Language.get("WSFontChooser_FontStyle_BoldItalic"))){
        // special - bolditalic needs bold and italic done together
        return font.deriveFont(Font.BOLD+Font.ITALIC).deriveFont(size);
        }

      return font.deriveFont(style).deriveFont(size);
      }
    catch (Throwable t){
      return null;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void loadDefaults(){
    // referencing the components
    fontNameChooser = (WSList)WSRepository.get("FontNameChooser");
    fontSizeChooser = (WSList)WSRepository.get("FontSizeChooser");
    fontStyleChooser = (WSList)WSRepository.get("FontStyleChooser");

    fontName = (WSTextField)WSRepository.get("FontName");
    fontSize = (WSTextField)WSRepository.get("FontSize");
    fontStyle = (WSTextField)WSRepository.get("FontStyle");

    fontColor = (WSSmallColorChooser)WSRepository.get("SmallColorChooser");

    fontPreview = (WSLabel)WSRepository.get("FontPreview");


    // populate the lists
    String[] fontNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    fontNameChooser.setListData(fontNames);

    String[] fontSizes = new String[]{"8","9","10","11","12","14","16","18","20","22","24","26","28","36","48","72"};
    fontSizeChooser.setListData(fontSizes);

    String[] fontStyles = new String[]{"Plain","Bold","Italic","BoldItalic"};
    for (int i=0;i<fontStyles.length;i++){
      fontStyles[i] = Language.get("WSFontChooser_FontStyle_" + fontStyles[i]);
      }
    fontStyleChooser.setListData(fontStyles);


    // populate the comboboxes
    //String[] fontColors = new String[]{"BLACK","BLUE","CYAN","DARK_GRAY","GRAY","GREEN","LIGHT_GRAY","MAGENTA","ORANGE","PINK","RED","WHITE","YELLOW"};
    //fontColor.setModel(new DefaultComboBoxModel(fontColors));


    // select the correct rows in the lists (based on the Settings)
    setFontName(Settings.get("WSFontChooser_FontName_Selected"));
    setFontSize(Settings.get("WSFontChooser_FontSize_Selected"));
    setFontStyle(Language.get("WSFontChooser_FontStyle_" + Settings.get("WSFontChooser_FontStyle_Selected")));


    // register listeners on the lists
    //fontNameChooser.addListSelectionListener(new WSSelectableListener(this));
    fontSizeChooser.addListSelectionListener(new WSSelectableListener(this));
    fontStyleChooser.addListSelectionListener(new WSSelectableListener(this));

    fontName.addKeyListener(new WSEnterableListener(this));
    fontSize.addKeyListener(new WSEnterableListener(this));
    fontStyle.addKeyListener(new WSEnterableListener(this));


    reloadPreview();
    }


/**
**********************************************************************************************
The event that is triggered from a WSSelectableListener when an item is deselected
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onDeselect(JComponent c, Object e){
    return false;
    }


/**
**********************************************************************************************
The event that is triggered from a WSEnterableListener when a processing key is pressed
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onEnter(JComponent c, java.awt.event.KeyEvent e){
    doingKeyPress = true;

    if (c == fontName){
      // Font Name List
      setFontName(fontName.getText());
      reloadPreview();
      }
    else if (c == fontSize){
      // Font Size List
      setFontSize(fontSize.getText());
      reloadPreview();
      }
    else if (c == fontStyle){
      // Font Style List
      setFontStyle(fontStyle.getText());
      reloadPreview();
      }

    doingKeyPress = false;
    return true;
    }


/**
**********************************************************************************************
Triggered when the color is changed in the WSSmallColorChooser
**********************************************************************************************
**/
  public boolean onEvent(JComponent c, WSEvent e){
    if (e.getID() == WSEvent.COLOR_CHANGED){
      reloadPreview();
      return true;
      }
    return false;
    }


/**
**********************************************************************************************
The event that is triggered from a WSSelectableListener when an item is selected
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onSelect(JComponent c, Object e){
    if (c instanceof WSList){
      if (doingKeyPress){
        // prevents duplicate reloading of the preview when the user types in a value AND
        // when the value is also in the list.
        return true;
        }

      if (c == fontNameChooser){
        // Font Name List
        setFontName((String)fontNameChooser.getSelectedValue());
        reloadPreview();
        }
      else if (c == fontSizeChooser){
        // Font Size List
        Object size = fontSizeChooser.getSelectedValue();
        if (size != null){
          setFontSize((String)size);
          reloadPreview();
          }
        }
      else if (c == fontStyleChooser){
        // Font Style List
        setFontStyle((String)fontStyleChooser.getSelectedValue());
        reloadPreview();
        }
      return true;
      }
    return false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void reloadPreview(){
    try {
      if (building){
        return;
        }

      Color color = fontColor.getColor();
      fontPreview.setForeground(color);

      fontPreview.setFont(getFont());
      }
    catch (Throwable t){
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setFontName(String name){
    if (building){
      return;
      }
    building = true;

    int selIndex = fontNameChooser.getSelectedIndex();

    // select it in the list, if it is a listed name
    if (name != null){
      fontNameChooser.setSelectedValue(name,true);
      }

    if (fontNameChooser.getSelectedIndex() == -1){
      name = Settings.get("WSFontChooser_FontName_Selected");
      if (name == null || name.equals("")){
        //fontNameChooser.setSelectedIndex(0);
        name = (String)fontNameChooser.getSelectedValue();
        }
      }

    if (selIndex == fontNameChooser.getSelectedIndex()){
      name = (String)fontNameChooser.getSelectedValue();
      }

    if (name != null){
      fontName.setText(name);
      Settings.set("WSFontChooser_FontName_Selected",name);
      }

    building = false;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setFontSize(String size){
    if (building){
      return;
      }
    building = true;

    try {
      Integer.parseInt(size); // only allow numbers
      }
    catch (Throwable t){
      size = Settings.get("WSFontChooser_FontSize_Selected");
      if (size == null || size.equals("")){
        fontSizeChooser.setSelectedIndex(4);
        size = (String)fontSizeChooser.getSelectedValue();
        }
      }

    // select it in the list, if it is a listed number
    fontSizeChooser.setSelectedValue(size,true);


    Object selected = fontSizeChooser.getSelectedValue();
    if (selected != null && ! ((String)selected).equals(size)){
      fontSizeChooser.clearSelection();
      }


    fontSize.setText(size);
    Settings.set("WSFontChooser_FontSize_Selected",size);

    building = false;
    }


/**
**********************************************************************************************
This has to be handled differently, because BOLDITALIC is implemented oddly, and the language
should not be saved.
**********************************************************************************************
**/
  public void setFontStyle(String style){
    if (building){
      return;
      }
    building = true;

    int selIndex = fontStyleChooser.getSelectedIndex();

    // select it in the list, if it is a listed number
    fontStyleChooser.setSelectedValue(style,true);

    if (fontStyleChooser.getSelectedIndex() == -1){
      style = Settings.get("WSFontChooser_FontStyle_Selected");
      if (style == null || style.equals("")){
        fontStyleChooser.setSelectedIndex(0);
        style = (String)fontSizeChooser.getSelectedValue();
        }
      }

    if (selIndex == fontStyleChooser.getSelectedIndex()){
      style = (String)fontStyleChooser.getSelectedValue();
      }

    fontStyle.setText(style);

    // set language-independent names for the setting
    if (style != null){
      if (style.equals(Language.get("WSFontChooser_FontStyle_Italic"))){
        style = "Italic";
        }
      else if (style.equals(Language.get("WSFontChooser_FontStyle_Bold"))){
        style = "Bold";
        }
      else if (style.equals(Language.get("WSFontChooser_FontStyle_BoldItalic"))){
        style = "BoldItalic";
        }
      else {
        style = "Plain";
        }

      Settings.set("WSFontChooser_FontStyle_Selected",style);
      }

    building = false;
    }




  }