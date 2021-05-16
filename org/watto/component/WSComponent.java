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

import org.watto.xml.XMLNode;

import java.awt.AWTEvent;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.border.Border;


/**
**********************************************************************************************
A component that is part of the WSProgram template. These components are designed to build
atop a JComponent, providing extra functionality and XML construction interfaces.
**********************************************************************************************
**/
public interface WSComponent extends Comparable, XMLObject{

  public static final long WS_EVENT_MASK = 72057594037927936L;

/**
**********************************************************************************************
Compares this components to another component, based on its getText()
@param obj the component to compare to
@return an integer indicating the sorting order
**********************************************************************************************
**/
  public int compareTo(Object obj);

/**
**********************************************************************************************
Gets the border [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public Border getBorder();

/**
**********************************************************************************************
Gets the border width of this component
@return the border width
**********************************************************************************************
**/
  public int getBorderWidth();

/**
**********************************************************************************************
Gets the <i>code</i> for this component. The code is used for Language and other properties.
@return the code
**********************************************************************************************
**/
  public String getCode();

/**
**********************************************************************************************
Gets the <i>fixedHeight</i> for this component. Returns the height, if fixed, or -1 if not
@return the fixedHeight, or -1 if the height isn't fixed
**********************************************************************************************
**/
  public int getFixedHeight();

/**
**********************************************************************************************
Gets the <i>fixedWidth</i> for this component. Returns the width, if fixed, or -1 if not
@return the fixedWidth, or -1 if the width isn't fixed
**********************************************************************************************
**/
  public int getFixedWidth();

/**
**********************************************************************************************
Is this component stored in the repository?
@return true if it is stored in the repository, false if not.
**********************************************************************************************
**/
  public boolean getInRepository();

/**
**********************************************************************************************
Gets the listeners on this component.
@return the listeners
**********************************************************************************************
**/
  public Object[] getListenerList();

/**
**********************************************************************************************
Gets the maximum size [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public Dimension getMaximumSize();

/**
**********************************************************************************************
Gets the minimum size [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public Dimension getMinimumSize();

/**
**********************************************************************************************
Gets the parent component
**********************************************************************************************
**/
  public Container getParent();

/**
**********************************************************************************************
Gets the position of this component in its parent
@return the position
**********************************************************************************************
**/
  public String getPosition();

/**
**********************************************************************************************
Gets the preferred size [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public Dimension getPreferredSize();

/**
**********************************************************************************************
Gets the small text of this component. If the component has a language-dependent small text,
it will be returned, otherwise it will return "".
@return the text of this component
**********************************************************************************************
**/
  public String getSmallText();

/**
**********************************************************************************************
Gets the text of this component. If the component has a language-dependent text, it will be
returned, otherwise it will return "".
@return the text of this component
**********************************************************************************************
**/
  public String getText();

/**
**********************************************************************************************
Gets the tooltip text of this component. If the component has a language-dependent tooltip
text, it will be returned, otherwise it will return "".
@return the tooltip text of this component
**********************************************************************************************
**/
  public String getToolTipText();

/**
**********************************************************************************************
Is this component focused? [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public boolean hasFocus();

/**
**********************************************************************************************
Is this component enabled? [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public boolean isEnabled();

/**
**********************************************************************************************
Is this component opaque? [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public boolean isOpaque();

/**
**********************************************************************************************
Is this component visible? [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public boolean isVisible();

/**
**********************************************************************************************
Processes the given event
@param event the event that was triggered
**********************************************************************************************
**/
  public void processEvent(AWTEvent event);

/**
**********************************************************************************************
Registers the events that this component generates
**********************************************************************************************
**/
  public void registerEvents();

/**
**********************************************************************************************
Sets the border [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public void setBorder(Border border);

/**
**********************************************************************************************
Sets the border width of this component
DOES NOT ACTUALLY SET THE BORDER WIDTH, ONLY SETS THE BORDER WIDTH VARIABLE VALUE
@param borderWidth the width of the border
**********************************************************************************************
**/
  public void setBorderWidth(int borderWidth);

/**
**********************************************************************************************
Sets the code for this components
@param code the code
**********************************************************************************************
**/
  public void setCode(String code);

/**
**********************************************************************************************
Sets whether this component is enabled [Automatically implemented if extending a JComponent]
@param enabled true if the component is enabled, false otherwise
**********************************************************************************************
**/
  public void setEnabled(boolean enabled);

/**
**********************************************************************************************
Sets whether the component has a fixed height or not. doesn't actually set the height though.
@param fixedHeight true if the height is fixed
**********************************************************************************************
**/
  public void setFixedHeight(boolean fixedHeight);

/**
**********************************************************************************************
Sets whether the component has a fixed width or not. doesn't actually set the width though.
@param fixedWidth true if the width is fixed
**********************************************************************************************
**/
  public void setFixedWidth(boolean fixedWidth);

/**
**********************************************************************************************
Sets whether this component is focused
@param focused true if the component is focused, false otherwise
**********************************************************************************************
**/
  public void setFocus(boolean focused);

/**
**********************************************************************************************
Sets whether this item should be stored in the repository or not
@param inRepository should it be stord in the repository?
**********************************************************************************************
**/
  public void setInRepository(boolean inRepository);

/**
**********************************************************************************************
Sets the maximum size [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public void setMaximumSize(Dimension d);

/**
**********************************************************************************************
Sets the minimum size [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public void setMinimumSize(Dimension d);

/**
**********************************************************************************************
Sets the opacity [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public void setOpaque(boolean opaque);

/**
**********************************************************************************************
Sets the position of this component in its parent
DOES NOT ACTUALLY SET THE POSITION, ONLY SETS THE POSITION VARIABLE VALUE
@param position the position
**********************************************************************************************
**/
  public void setPosition(String position);

/**
**********************************************************************************************
Sets the preferred size [Automatically implemented if extending a JComponent]
**********************************************************************************************
**/
  public void setPreferredSize(Dimension d);

/**
**********************************************************************************************
Sets whether the component is visible [Automatically implemented if extending a JComponent]
@param showing true if the component is visible, false if hidden
**********************************************************************************************
**/
  public void setVisible(boolean visible);

  }