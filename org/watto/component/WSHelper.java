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

import java.awt.event.InputEvent;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
**********************************************************************************************
Helper and common methods used by WSComponents
**********************************************************************************************
**/
public class WSHelper {

  /** the singleton instance of the helper **/
  static WSHelper instance = new WSHelper();

  /** the class that defines the root directory to locate images etc. **/
  static Class resourcePath = null;


/**
**********************************************************************************************
Constructor, if needed.
**********************************************************************************************
**/
  public WSHelper(){
    }


/**
**********************************************************************************************
Gets the singleton instance of the helper
@return the <i>instance</i>
**********************************************************************************************
**/
  public static WSHelper getInstance(){
    return instance;
    }


/**
**********************************************************************************************
Builds a WSComponent from the <i>child</i>. The component that is built depends on the tag
name of the <i>child</i>. For example, a tag name of WSButton will build a WSButton component.
All built components that implement WSComponent are added to the WSRepository. (unless they
have the attribute repository="false")
@param child the tag that describes the component
@param caller the object (usually JComponent) that contains this component, or more formally,
              the object that receives events from this component.
@return the JComponent object built from the <i>child</i>, or null if the <i>child</i> had
        errors or simply isn't a valid component
**********************************************************************************************
**/
  public static JComponent buildComponent(XMLNode child){
    String childTag = "org.watto.component." + child.getTag();

    try {
      ClassLoader cl = ClassLoader.getSystemClassLoader();
      JComponent component = (JComponent)cl.loadClass(childTag).getConstructor(new Class[]{XMLNode.class}).newInstance(new Object[]{child});
      return component;
      }
    catch (Throwable t){
      try {
        childTag = child.getTag();
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        JComponent component = (JComponent)cl.loadClass(childTag).getConstructor(new Class[]{XMLNode.class}).newInstance(new Object[]{child});
        return component;
        }
      catch (Throwable t2){
        System.out.println("BAD CLASS: " + childTag);
        ErrorLogger.log(t2);
        //return null;
        return new WSPanel(XMLReader.readString("<WSPanel />"));
        }
      }

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void buildObject(XMLNode node, WSComponent component){
    //((JComponent)component).removeAll(); // this removes the buttons from WSComboBox, etc too - need an alternative

    parseBorderWidthAttribute(node.getAttribute("border-width"),component);
    parseCodeAttribute(node.getAttribute("code"),component);
    parseEnabledAttribute(node.getAttribute("enabled"),component);
    parseOpaqueAttribute(node.getAttribute("opaque"),component);
    parseVisibleAttribute(node.getAttribute("visible"),component);
    parseWidthHeightAttribute(node.getAttribute("width"),node.getAttribute("height"),component);

    parseRepositoryAttribute(node.getAttribute("repository"),component);

    String position = node.getAttribute("position");
    if (position != null){
      component.setPosition(position);
      }
    }


/**
**********************************************************************************************
Constructs the XMLNode representation of this component
@param component the component
@return the XMLNode used to construct the component
**********************************************************************************************
**/
  public static XMLNode buildXML(WSComponent component){
    XMLNode node = new XMLNode(getClassName(component));

    String code = component.getCode();
    try {
      Integer.parseInt(code);
      }
    catch (Throwable t){
      node.setAttribute("code",component.getCode()); // only set if not an automatically generated hash
      }

    if (!component.isEnabled()){
      node.setAttribute("enabled","false"); // only set if not enabled
      }

    int fixedHeight = component.getFixedHeight();
    if (fixedHeight != -1){
      node.setAttribute("height",""+fixedHeight);
      }

    if (!component.isOpaque()){
      node.setAttribute("opaque","false"); // only set if not opaque
      }

    if (!component.isVisible()){
      node.setAttribute("visible","false"); // only set if not visible
      }

    int fixedWidth = component.getFixedWidth();
    if (fixedWidth != -1){
      node.setAttribute("width",""+fixedWidth);
      }

    String position = component.getPosition();
    if (position != null){
      node.setAttribute("position",position);
      }

    int borderWidth = component.getBorderWidth();
    if (borderWidth != -1){
      node.setAttribute("border-width",""+borderWidth);
      }

    if (!component.getInRepository()){
      node.setAttribute("repository","false"); // only set if not in the repository
      }

    return node;
    }


/**
**********************************************************************************************
Compares 2 components, based on its getText()
@param mainObj the main component
@param compObj the component to compare against
@return an integer indicating the sorting order
**********************************************************************************************
**/
  public static int compare(WSComponent mainObj, WSComponent compObj){
    return mainObj.getText().compareTo(compObj.getText());
    }


/**
**********************************************************************************************
Compares 2 components, based on its getText()
@param mainObj the main component
@param compObj the component to compare against (instance of WSComponent)
@return an integer indicating the sorting order
**********************************************************************************************
**/
  public static int compare(WSComponent mainObj, Object compObj){
    return compare(mainObj,(WSComponent)compObj);
    }


/**
**********************************************************************************************
Fires an event
@param event the event to be fired
@param component the component firing the event
**********************************************************************************************
**/
  public static void fireEvent(WSEvent event, WSComponent component){
    /*
    Object[] listeners = component.getListenerList();
    for (int i = listeners.length-2; i>=0; i-=2) {
      if (listeners[i]==WSEventListener.class) {
        ((WSEventListener)listeners[i+1]).eventOccurred(event);
        }
      }
    */
    component.processEvent(event);
    }


/**
**********************************************************************************************
Fires an event
@param eventID the ID of the event to fire
@param component the component firing the event
**********************************************************************************************
**/
  public static void fireEvent(int eventID, WSComponent component){
    fireEvent(new WSEvent(component,eventID),component);
    }


/**
**********************************************************************************************
Fires a generic event
@param component the component firing the event
**********************************************************************************************
**/
  public static void fireEvent(WSComponent component){
    fireEvent(new WSEvent(component,WSEvent.GENERIC_EVENT),component);
    }


/**
**********************************************************************************************
Gets the class name of the component
@param component the component
@return the class name
**********************************************************************************************
**/
  public static String getClassName(WSComponent component){
    Class cl = component.getClass();
    String name = cl.getName();
    Package pack = cl.getPackage();

    if (pack != null){
      name = name.substring(pack.getName().length()+1);
      }

    return name;
    }


/**
**********************************************************************************************
Gets a resource such as an image. Tries to get it from the location of <i>rootPath</i>
@param path the name of the resource to retrieve
@return the URL to the resource, or null if rootPath=null or the resource doesn't exist.
**********************************************************************************************
**/
  public static URL getResource(String path){
    try {
      if (resourcePath != null){
        return resourcePath.getResource(path);
        }
      else {
        return null;
        }
      }
    catch (Throwable t){
      return null;
      }
    }


/**
**********************************************************************************************
Gets the small text of this component. If the component has a language-dependent small text,
it will be returned, otherwise it will return "".
@return the text of this component
**********************************************************************************************
**/
  public static String getSmallText(WSComponent component){
    String code = component.getCode();
    String langCode = getClassName(component) + "_" + code + "_Small";
    if (Language.has(langCode,false)){
      return Language.get(langCode);
      }
    return getText(component);
    }


/**
**********************************************************************************************
Gets the text of this component. If the component has a language-dependent text, it will be
returned, otherwise it will return "".
@return the text of this component
**********************************************************************************************
**/
  public static String getText(WSComponent component){
    String code = component.getCode();
    if (code == null){
      return "";
      }
    String langCode = getClassName(component) + "_" + code + "_Text";
    if (Language.has(langCode)){
      return Language.get(langCode);
      }
    return "";
    }


/**
**********************************************************************************************
Gets the tooltip text of this component. If the component has a language-dependent tooltip
text, it will be returned, otherwise it will return "".
@return the tooltip text of this component
**********************************************************************************************
**/
  public static String getToolTipText(WSComponent component){
    String code = component.getCode();
    String langCode = getClassName(component) + "_" + code + "_Tooltip";
    if (Language.has(langCode)){
      return Language.get(langCode);
      }
    return "";
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static int parseAlignment(String code){
    if (code == null){
      return SwingConstants.CENTER;
      }
    else if (code.equalsIgnoreCase("center")){
      return SwingConstants.CENTER;
      }
    else if (code.equalsIgnoreCase("left")){
      return SwingConstants.LEFT;
      }
    else if (code.equalsIgnoreCase("right")){
      return SwingConstants.RIGHT;
      }
    else if (code.equalsIgnoreCase("top")){
      return SwingConstants.TOP;
      }
    else if (code.equalsIgnoreCase("bottom")){
      return SwingConstants.BOTTOM;
      }
    else if (code.equalsIgnoreCase("leading")){
      return SwingConstants.LEADING;
      }
    else if (code.equalsIgnoreCase("trailing")){
      return SwingConstants.TRAILING;
      }
    else {
      return SwingConstants.CENTER;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static String parseAlignment(int alignment){
    if (alignment == SwingConstants.CENTER){
      return "center";
      }
    else if (alignment == SwingConstants.LEFT){
      return "left";
      }
    else if (alignment == SwingConstants.RIGHT){
      return "right";
      }
    else if (alignment == SwingConstants.TOP){
      return "top";
      }
    else if (alignment == SwingConstants.BOTTOM){
      return "bottom";
      }
    else if (alignment == SwingConstants.LEADING){
      return "leading";
      }
    else if (alignment == SwingConstants.TRAILING){
      return "trailing";
      }
    else {
      return "center";
      }
    }


/**
**********************************************************************************************
Changes a String boolean (ie "true" or "false") into a boolean
@param code "true" or "false"
@return true or false, depending on the value of the input
**********************************************************************************************
**/
  public static boolean parseBoolean(String code){
    if (code.equalsIgnoreCase("true")){
      return true;
      }
    if (code.equalsIgnoreCase("false")){
      return false;
      }
    else {
      return true;
      }
    }


/**
**********************************************************************************************
Configures the component using the given attribute value
@param value the value of this attribute
@param component the component to configure
**********************************************************************************************
**/
  public static void parseBorderWidthAttribute(String value, WSComponent component){
    try {
      if (value != null){
        int size = Integer.parseInt(value);
        component.setBorder(new EmptyBorder(size,size,size,size));
        component.setBorderWidth(size);
        }
      }
    catch (Throwable t){
      throwAttributeException("border-width",value);
      }
    }


/**
**********************************************************************************************
Configures the component using the given attribute value
@param value the value of this attribute
@param component the component to configure
**********************************************************************************************
**/
  public static void parseCodeAttribute(String value, WSComponent component){
    if (value != null){
      component.setCode(value);
      }
    //WSRepository.add(component);
    }


/**
**********************************************************************************************
Configures the component using the given attribute value
@param value the value of this attribute
@param component the component to configure
**********************************************************************************************
**/
  public static void parseEnabledAttribute(String value, WSComponent component){
    if (value != null){
      component.setEnabled(parseBoolean(value));
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static int parseHorizontalScrollBarPolicy(String code){
    if (code == null){
      return ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
      }
    else if (code.equalsIgnoreCase("true")){
      return ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS;
      }
    else if (code.equalsIgnoreCase("false")){
      return ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
      }
    else {
      return ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static String parseHorizontalScrollBarPolicy(int policy){
    if (policy == ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS){
      return "true";
      }
    else if (policy == ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER){
      return "false";
      }
    else {
      return "auto";
      }
    }


/**
**********************************************************************************************
Changes a String integer into an int
@param code an integer number as a String
@return the int number, or -1 if conversion was unsuccessful
**********************************************************************************************
**/
  public static int parseInt(String code){
    try {
      return Integer.parseInt(code);
      }
    catch (Throwable t){
      return -1;
      }
    }


/**
**********************************************************************************************
Changes a String mnemonic (such as "A") into a keycode value
@param code the String mnemonic
@return the keycode value of the mnemonic
**********************************************************************************************
**/
  public static int parseMnemonic(String code){
    if (code == null || code.length() != 1){
      return 0;
      }

    try {
      return KeyStroke.getKeyStroke(code.toUpperCase()).getKeyCode();
      }
    catch (Throwable t){
      return 0;
      }
    }


/**
**********************************************************************************************
Changes a keycode value of a mnemonic into a String (such as "A")
@param mnemonic the mnemonic keycode value
@return a String representation of the mnemonic
**********************************************************************************************
**/
  public static String parseMnemonic(int mnemonic){
    if (mnemonic <= 0){
      return null;
      }

    try {
      return KeyStroke.getKeyStroke(mnemonic,0).toString().substring(8,9);
      }
    catch (Throwable t){
      return null;
      }
    }


/**
**********************************************************************************************
Configures the component using the given attribute value
@param value the value of this attribute
@param component the component to configure
**********************************************************************************************
**/
  public static void parseOpaqueAttribute(String value, WSComponent component){
    if (value != null){
      component.setOpaque(parseBoolean(value));
      }
    else {
      component.setOpaque(true);
      }
    }


/**
**********************************************************************************************
Changes a String orientation (ie "horizontal" or "vertical") into an orientation value
@param code the orientation
@return the actual orientation value
**********************************************************************************************
**/
  public static int parseOrientation(String code){
    if (code.equalsIgnoreCase("horizontal")){
      return SwingConstants.HORIZONTAL;
      }
    else if (code.equalsIgnoreCase("vertical")){
      return SwingConstants.VERTICAL;
      }
    else {
      return SwingConstants.HORIZONTAL;
      }
    }


/**
**********************************************************************************************
Changes an orientation value into a String orientation (ie "horizontal" or "vertical")
@param orientation the orientation value
@return the String representation of the orientation
**********************************************************************************************
**/
  public static String parseOrientation(int orientation){
    if (orientation == SwingConstants.HORIZONTAL){
      return "horizontal";
      }
    else if (orientation == SwingConstants.VERTICAL){
      return "vertical";
      }
    else {
      return "horizontal";
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static String parsePosition(String position, boolean fromStringToJava){
    if (fromStringToJava){
      if (position == null){
        return BorderLayout.CENTER;
        }
      else if (position.equalsIgnoreCase("center")){
        return BorderLayout.CENTER;
        }
      else if (position.equalsIgnoreCase("north")){
        return BorderLayout.NORTH;
        }
      else if (position.equalsIgnoreCase("south")){
        return BorderLayout.SOUTH;
        }
      else if (position.equalsIgnoreCase("east")){
        return BorderLayout.EAST;
        }
      else if (position.equalsIgnoreCase("west")){
        return BorderLayout.WEST;
        }
      else {
        return BorderLayout.CENTER;
        }
      }
    else {
      if (position == null){
        return "center";
        }
      else if (position.equals(BorderLayout.CENTER)){
        return "center";
        }
      else if (position.equals(BorderLayout.NORTH)){
        return "north";
        }
      else if (position.equals(BorderLayout.SOUTH)){
        return "south";
        }
      else if (position.equals(BorderLayout.EAST)){
        return "east";
        }
      else if (position.equals(BorderLayout.WEST)){
        return "west";
        }
      else {
        return "center";
        }
      }
    }


/**
**********************************************************************************************
Configures the component using the given attribute value
@param value the value of this attribute
@param component the component to configure
**********************************************************************************************
**/
  public static void parseRepositoryAttribute(String value, WSComponent component){
    if (value == null || ! value.equals("false")){
      component.setInRepository(true);
      WSRepository.add(component);
      }
    else {
      component.setInRepository(false);
      }
    }


/**
**********************************************************************************************
Changes a String shortcut (such as "ctrl t") into a KeyStroke
@param code the shortcut String
@return the KeyStroke of the shortcut
**********************************************************************************************
**/
  public static KeyStroke parseShortcut(String code){
    if (code == null){
      return null;
      }

    try {
      return KeyStroke.getKeyStroke(code);
      }
    catch (Throwable t){
      return null;
      }
    }


/**
**********************************************************************************************
Changes a KeyStroke into a String shortcut (such as "ctrl t")
@param shortcut the KeyStroke shortcut
@return the string representation of the shortcut
**********************************************************************************************
**/
  public static String parseShortcut(KeyStroke shortcut){
    if (shortcut == null){
      return null;
      }

    try {
      String code = "";

      int leftPos = 8;

      int modifiers = shortcut.getModifiers();
      if ((modifiers & InputEvent.SHIFT_DOWN_MASK) == InputEvent.SHIFT_DOWN_MASK){
        code += "shift ";
        leftPos += 6;
        }
      if ((modifiers & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK){
        code += "ctrl ";
        leftPos += 5;
        }
      if ((modifiers & InputEvent.ALT_DOWN_MASK) == InputEvent.ALT_DOWN_MASK){
        code += "alt ";
        leftPos += 4;
        }

      //code += shortcut.getKeyChar();
      //code += shortcut.toString().substring(8,9);
      code += shortcut.toString().substring(leftPos);

      return code;
      }
    catch (Throwable t){
      return null;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static int parseVerticalScrollBarPolicy(String code){
    if (code == null){
      return ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
      }
    else if (code.equalsIgnoreCase("true")){
      return ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
      }
    else if (code.equalsIgnoreCase("false")){
      return ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER;
      }
    else {
      return ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
      }
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static String parseVerticalScrollBarPolicy(int policy){
    if (policy == ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS){
      return "true";
      }
    else if (policy == ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER){
      return "false";
      }
    else {
      return "auto";
      }
    }


/**
**********************************************************************************************
Configures the component using the given attribute value
@param value the value of this attribute
@param component the component to configure
**********************************************************************************************
**/
  public static void parseVisibleAttribute(String value, WSComponent component){
    if (value != null){
      component.setVisible(parseBoolean(value));
      }
    }


/**
**********************************************************************************************
Configures the component using the given attribute value (width and/or height)
@param width the width value of this attribute
@param height the height value of this attribute
@param component the component to configure
**********************************************************************************************
**/
  public static void parseWidthHeightAttribute(String width, String height, WSComponent component){
    int h = -1;
    int w = -1;

    if (width != null){
      try {
        w = Integer.parseInt(width);
        component.setFixedWidth(true);
        }
      catch (Throwable t){
        throwAttributeException("width",width);
        }
      }

    if (height != null){
      try {
        h = Integer.parseInt(height);
        component.setFixedHeight(true);
        }
      catch (Throwable t){
        throwAttributeException("height",height);
        }
      }

    Dimension d;
    if (h != -1 && w != -1){
      d = new Dimension(w,h);
      component.setMaximumSize(d);
      component.setMinimumSize(d);
      component.setPreferredSize(d);
      }
    else if (h != -1){
      d = component.getMaximumSize();
      d.height = h;
      component.setMaximumSize(d);

      d = component.getMinimumSize();
      d.height = h;
      component.setMinimumSize(d);

      d = component.getPreferredSize();
      d.height = h;
      component.setPreferredSize(d);
      }
    else if (w != -1){
      d = component.getMaximumSize();
      d.width = w;
      component.setMaximumSize(d);

      d = component.getMinimumSize();
      d.width = w;
      component.setMinimumSize(d);

      d = component.getPreferredSize();
      d.width = w;
      component.setPreferredSize(d);
      }

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public static void setResourcePath(Object resourcePathIn){
    resourcePath = resourcePathIn.getClass();
    }


/**
**********************************************************************************************
Sets the text of this component. Usually used to clear the text (ie setting it to "") or to
give it a fixed value like a number.
@param component the component
@param text the new text value.
**********************************************************************************************
**/
  public static void setText(WSComponent component, String text){
    String code = component.getCode();
    if (code == null){
      return;
      }
    String langCode = getClassName(component) + "_" + code + "_Text";
    Language.set(langCode,text);
    }


/**
**********************************************************************************************
Throws (and catches) an XMLException for the attribute value pair. Used for error logging.
@param attribute the attribute name
@param value the value of this attribute
**********************************************************************************************
**/
  public static void throwAttributeException(String attribute, String value){
    try {
      throw new XMLException("The " + attribute + " attribute was invalid: \"" + value + "\"");
      }
    catch (Throwable t){
      ErrorLogger.log(t);
      }
    }




  }
