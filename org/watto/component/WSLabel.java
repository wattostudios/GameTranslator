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

import org.watto.Language;
import org.watto.event.*;
import org.watto.plaf.AquanauticTheme;
import org.watto.xml.XMLNode;

import java.awt.AWTEvent;
import java.awt.FontMetrics;
import java.awt.Dimension;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

/**
**********************************************************************************************
A Label
**********************************************************************************************
**/
public class WSLabel extends JLabel implements WSComponent {


  /** The code for the language and settings **/
  String code = null;

  /** the position of this component in its parent **/
  String position = null;

  /** the border width of this component **/
  int borderWidth = -1;
  /** the height was specified in the XML **/
  boolean fixedHeight = false;
  /** the width was specified in the XML **/
  boolean fixedWidth = false;
  /** is this object stored in the repository? **/
  boolean inRepository = true;

  /** allow wrapping of the text? **/
  boolean wrap = false;

  /** show a border on the panel? **/
  boolean showBorder = false;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSLabel(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSLabel(XMLNode node){
    super();
    buildObject(node);
    registerEvents();
    }



///////////////
//
// Configurable
//
///////////////

/**
**********************************************************************************************
Build this object from the <i>node</i>
@param node the XML node that indicates how to build this object
**********************************************************************************************
**/
  public void buildObject(XMLNode node){
    WSHelper.buildObject(node,this);
    //WSRepository.add(this);

    int align = WSHelper.parseAlignment(node.getAttribute("horizontal-alignment"));
    setHorizontalAlignment(align);
    setHorizontalTextPosition(align);

    align = WSHelper.parseAlignment(node.getAttribute("vertical-alignment"));
    setVerticalAlignment(align);
    setVerticalTextPosition(align);

    String wrap = node.getAttribute("wrap");
    if (wrap != null && wrap.equals("true")){
      setWrap(true);
      }

    String tag = node.getAttribute("showBorder");
    if (tag != null){
      setShowBorder(WSHelper.parseBoolean(tag));
      }

    setForeground(AquanauticTheme.COLOR_TEXT);

    setIcons();
    }


/**
**********************************************************************************************
Builds an XMLNode that describes this object
@return an XML node with the details of this object
**********************************************************************************************
**/
  public XMLNode buildXML(){
    XMLNode node = WSHelper.buildXML(this);
    String hAlign = WSHelper.parseAlignment(getHorizontalAlignment());
    if (!hAlign.equals("center")){
      node.setAttribute("horizontal-alignment",hAlign);
      }

    String vAlign = WSHelper.parseAlignment(getVerticalAlignment());
    if (!vAlign.equals("center")){
      node.setAttribute("vertical-alignment",vAlign);
      }

    if (wrap){
      node.setAttribute("wrap","true");
      }

    if (getShowBorder()){
      node.addAttribute("showBorder","true");
      }

    return node;
    }


/**
**********************************************************************************************
Registers the events that this component generates
**********************************************************************************************
**/
  public void registerEvents(){
    enableEvents(
                 AWTEvent.COMPONENT_EVENT_MASK |
                 AWTEvent.CONTAINER_EVENT_MASK |
                 AWTEvent.MOUSE_EVENT_MASK |
                 AWTEvent.MOUSE_MOTION_EVENT_MASK |
                 AWTEvent.HIERARCHY_EVENT_MASK |
                 AWTEvent.HIERARCHY_BOUNDS_EVENT_MASK |
                 AWTEvent.INPUT_METHOD_EVENT_MASK |
                 WSComponent.WS_EVENT_MASK
                 );
    }



///////////////
//
// Class-Specific Methods
//
///////////////


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void changeBorder(){
    int n = 0;
    int s = 0;
    int e = 0;
    int w = 0;

    if (showBorder){
      int pad = borderWidth;
      if (pad <= -1){
        pad = AquanauticTheme.MENU_BORDER_WIDTH+2;
        }

      n = pad;
      s = pad;
      e = pad;
      w = pad;
      }

    setBorder(new EmptyBorder(n,w,s,e));
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean getShowBorder(){
    return showBorder;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public boolean getWrap(){
    return wrap;
    }


/**
**********************************************************************************************

**********************************************************************************************
**/

  public Dimension increaseSize(Dimension size){
    double w = size.getWidth();
    double h = size.getHeight();

    if (wrap){
      // increases the height for the wrapped text
      String[] lines = WordWrap.wrap(getText(),this,(int)w);

      FontMetrics metrics = getGraphics().getFontMetrics();
      h += (metrics.getHeight() * (lines.length-1));
      }

    // still need this method so that it gets the height correctly before it is actually set in setShowLabel
    if (showBorder){
      int pad = AquanauticTheme.MENU_BORDER_WIDTH;
      h += pad;
      w += pad+pad;
      }

    size.setSize(w,h);
    return size;
    }


/**
**********************************************************************************************
Loads the icons for this component using the <i>code</i>.
**********************************************************************************************
**/
  public void setIcons(){
    String className = WSHelper.getClassName(this);

    URL normalIcon = getClass().getResource("images/" + className + "/" + code + "_n.gif");
    if (normalIcon != null){
      setIcon(new ImageIcon(normalIcon));
      }

    URL disabledIcon = getClass().getResource("images/" + className + "/" + code + "_d.gif");
    if (disabledIcon != null){
      setDisabledIcon(new ImageIcon(disabledIcon));
      }

    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setShowBorder(boolean showBorder){
    this.showBorder = showBorder;
    changeBorder();
    }


/**
**********************************************************************************************
Sets the text of this component in the Language, thus making it have a fixed name when
getText() is called
@param text the text
**********************************************************************************************
**/
  public void setText(String text){
    String langCode = WSHelper.getClassName(this) + "_" + code + "_Text";
    Language.set(langCode,text);

    langCode = WSHelper.getClassName(this) + "_" + code + "_Small";
    Language.set(langCode,text);
    }


/**
**********************************************************************************************
Calls setText() on the super
**********************************************************************************************
**/
  public void setText_Super(String text){
    super.setText(text);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
  public void setWrap(boolean wrap){
    this.wrap = wrap;
    }



///////////////
//
// Default Implementations
//
///////////////

/**
**********************************************************************************************
Compares this components to another component, based on its getText()
@param obj the component to compare to
@return an integer indicating the sorting order
**********************************************************************************************
**/
  public int compareTo(Object obj){
    return WSHelper.compare(this,obj);
    }

/**
**********************************************************************************************
Gets the border width of this component
@return the border width
**********************************************************************************************
**/
  public int getBorderWidth(){
    return borderWidth;
    }

/**
**********************************************************************************************
Gets the <i>code</i> for this component. The code is used for Language and other properties.
@return the code
**********************************************************************************************
**/
  public String getCode(){
    return code;
    }

/**
**********************************************************************************************
Gets the <i>fixedHeight</i> for this component. Returns the height, if fixed, or -1 if not
@return the fixedHeight, or -1 if the height isn't fixed
**********************************************************************************************
**/
  public int getFixedHeight(){
    if (fixedHeight){
      return getHeight();
      }
    return -1;
    }

/**
**********************************************************************************************
Gets the <i>fixedWidth</i> for this component. Returns the width, if fixed, or -1 if not
@return the fixedWidth, or -1 if the width isn't fixed
**********************************************************************************************
**/
  public int getFixedWidth(){
    if (fixedWidth){
      return getWidth();
      }
    return -1;
    }

/**
**********************************************************************************************
Is this component stored in the repository?
@return true if it is stored in the repository, false if not.
**********************************************************************************************
**/
  public boolean getInRepository(){
    return inRepository;
    }

/**
**********************************************************************************************
Gets the listeners on this component.
@return the listeners
**********************************************************************************************
**/
  public Object[] getListenerList(){
    return listenerList.getListenerList();
    }

/**
**********************************************************************************************
Gets the position of this component in its parent
@return the position
**********************************************************************************************
**/
  public String getPosition(){
    return position;
    }

/**
**********************************************************************************************
Gets the small text of this component. If the component has a language-dependent small text,
it will be returned, otherwise it will return "".
@return the text of this component
**********************************************************************************************
**/
  public String getSmallText(){
    return WSHelper.getSmallText(this);
    }

/**
**********************************************************************************************
Gets the text of this component. If the component has a language-dependent text, it will be
returned, otherwise it will return "".
@return the text of this component
**********************************************************************************************
**/
  public String getText(){
    return WSHelper.getText(this);
    }

/**
**********************************************************************************************
Gets the tooltip text of this component. If the component has a language-dependent tooltip
text, it will be returned, otherwise it will return "".
@return the tooltip text of this component
**********************************************************************************************
**/
  public String getToolTipText(){
    return WSHelper.getToolTipText(this);
    }

/**
**********************************************************************************************
Processes the given event
@param event the event that was triggered
**********************************************************************************************
**/
  public void processEvent(AWTEvent event){
    super.processEvent(event); // handles any normal listeners
    WSEventHandler.processEvent(this,event); // passes events to the caller
    }

/**
**********************************************************************************************
Sets the border width of this component
DOES NOT ACTUALLY SET THE BORDER WIDTH, ONLY SETS THE BORDER WIDTH VARIABLE VALUE
@param borderWidth the width of the border
**********************************************************************************************
**/
  public void setBorderWidth(int borderWidth){
    this.borderWidth = borderWidth;
    }

/**
**********************************************************************************************
Sets the code for this components
@param code the code
**********************************************************************************************
**/
  public void setCode(String code){
    this.code = code;
    }

/**
**********************************************************************************************
Sets whether the component has a fixed height or not. doesn't actually set the height though.
@param fixedHeight true if the height is fixed
**********************************************************************************************
**/
  public void setFixedHeight(boolean fixedHeight){
    this.fixedHeight = fixedHeight;
    }

/**
**********************************************************************************************
Sets whether the component has a fixed width or not. doesn't actually set the width though.
@param fixedWidth true if the width is fixed
**********************************************************************************************
**/
  public void setFixedWidth(boolean fixedWidth){
    this.fixedWidth = fixedWidth;
    }

/**
**********************************************************************************************
Sets whether this component is focused
@param focused true if the component is focused, false otherwise
**********************************************************************************************
**/
  public void setFocus(boolean focused){
    if (focused){
      requestFocusInWindow();
      }
    }

/**
**********************************************************************************************
Sets whether this item should be stored in the repository or not
@param inRepository should it be stord in the repository?
**********************************************************************************************
**/
  public void setInRepository(boolean inRepository){
    this.inRepository = inRepository;
    }

/**
**********************************************************************************************
Sets the position of this component in its parent
DOES NOT ACTUALLY SET THE POSITION, ONLY SETS THE POSITION VARIABLE VALUE
@param position the position
**********************************************************************************************
**/
  public void setPosition(String position){
    this.position = position;
    }

/**
**********************************************************************************************
Gets the name of this component. Same as getText();
@return the name of this component
**********************************************************************************************
**/
  public String toString(){
    return getText();
    }



///////////////
//
// JLabel-implemented
//
///////////////

/**
**********************************************************************************************
Gets the border [Automatically implemented if extending a JLabel]
**********************************************************************************************
**/
/*
  public Border getBorder(){
    return super.getBorder();
    }
*/

/**
**********************************************************************************************
Gets the maximum size [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public Dimension getMaximumSize(){
    return increaseSize(super.getMaximumSize());
    }


/**
**********************************************************************************************
Gets the minimum size [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public Dimension getMinimumSize(){
    return increaseSize(super.getMinimumSize());
    }

/**
**********************************************************************************************
Gets the parent component
**********************************************************************************************
**/
/*
  public Object getParent(){
    return super.getParent();
    }
*/

/**
**********************************************************************************************
Gets the preferred size [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public Dimension getPreferredSize(){
    return increaseSize(super.getPreferredSize());
    }

/**
**********************************************************************************************
Is this component focused? [Automatically implemented if extending a JLabel]
**********************************************************************************************
**/
/*
  public boolean hasFocus(){
    return super.hasFocus();
    }
*/

/**
**********************************************************************************************
Is this component enabled? [Automatically implemented if extending a JLabel]
**********************************************************************************************
**/
/*
  public boolean isEnabled(){
    return super.isEnabled();
    }
*/

/**
**********************************************************************************************
Is this component opaque? [Automatically implemented if extending a JLabel]
**********************************************************************************************
**/
  public boolean isOpaque(){
    return false;
    }

/**
**********************************************************************************************
Is this component visible? [Automatically implemented if extending a JLabel]
**********************************************************************************************
**/
/*
  public boolean isVisible(){
    return super.isVisible();
    }
*/

/**
**********************************************************************************************
Sets the border [Automatically implemented if extending a JLabel]
**********************************************************************************************
**/
/*
  public void setBorder(Border border){
    super.setBorder(border);
    }
*/

/**
**********************************************************************************************
Sets whether this component is enabled.
@param enabled true if the component is enabled, false otherwise
**********************************************************************************************
**/
/*
  public void setEnabled(boolean enabled){
    super.setEnabled(enabled);
    }
*/

/**
**********************************************************************************************
Sets the maximum size [Automatically implemented if extending a JLabel]
**********************************************************************************************
**/
/*
  public void setMaximumSize(Dimension d){
    super.setMaximumSize(d);
    }
*/

/**
**********************************************************************************************
Sets the minimum size [Automatically implemented if extending a JLabel]
**********************************************************************************************
**/
/*
  public void setMinimumSize(Dimension d){
    super.setMinimumSize(d);
    }
*/

/**
**********************************************************************************************
Sets the opacity
@param opaque true if opaque, false otherwise
**********************************************************************************************
**/
/*
  public void setOpaque(boolean opaque){
    super.setOpaque(opaque);
    }
*/

/**
**********************************************************************************************
Sets the preferred size [Automatically implemented if extending a JLabel]
**********************************************************************************************
**/
/*
  public void setPreferredSize(Dimension d){
    super.setPreferredSize(d);
*/

/**
**********************************************************************************************
Sets whether the component is visible
@param showing true if the component is visible, false if hidden
**********************************************************************************************
**/
/*
  public void setVisible(boolean visible){
    super.setVisible(visible);
    }
*/



  }