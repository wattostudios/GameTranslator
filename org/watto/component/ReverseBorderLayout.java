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

import java.awt.*;
import java.util.Hashtable;

/**
**********************************************************************************************
As the name suggests, this is the complete opposite of a BorderLayout.
<br><br>
In a BorderLayout, the components on the outside are set to their minimum size, and the CENTER
component fills the remaining space.
<br>
In a ReverseBorderLayout, the CENTER component is set to its minimum size, and the components
on the outside fill the remaining space.
<br><br>
The remaining space around the outside is assigned equally to the associated components. For
example, if there is both a NORTH and SOUTH component, both components will use half of the
remaining space. If only 1 of these components exists, it will use all the remaining space.
**********************************************************************************************
**/
public class ReverseBorderLayout implements LayoutManager2,
                                 java.io.Serializable {

  int hgap;
  int vgap;

  Component north;
  Component west;
  Component east;
  Component south;
  Component center;

  Component firstLine;
  Component lastLine;
  Component firstItem;
  Component lastItem;

  public static final String NORTH  = "North";
  public static final String SOUTH  = "South";
  public static final String EAST   = "East";
  public static final String WEST   = "West";
  public static final String CENTER = "Center";

  public static final String BEFORE_FIRST_LINE = "First";
  public static final String AFTER_LAST_LINE = "Last";
  public static final String BEFORE_LINE_BEGINS = "Before";
  public static final String AFTER_LINE_ENDS = "After";

  public static final String PAGE_START = BEFORE_FIRST_LINE;
  public static final String PAGE_END = AFTER_LAST_LINE;
  public static final String LINE_START = BEFORE_LINE_BEGINS;
  public static final String LINE_END = AFTER_LINE_ENDS;

  private static final long serialVersionUID = -8658291919501921765L;


/**
**********************************************************************************************
Constructor, with an empty vertical and horizontal gap between components
**********************************************************************************************
**/
  public ReverseBorderLayout() {
    this(0, 0);
    }


/**
**********************************************************************************************
Constructor, setting the horizontal and vertical gaps between components
@param hgap the horizontal gap
@param vgap the vertical gap
**********************************************************************************************
**/
  public ReverseBorderLayout(int hgap, int vgap) {
    this.hgap = hgap;
    this.vgap = vgap;
    }


/**
**********************************************************************************************
Gets the horizontal gap between components
@return the horizontal gap
**********************************************************************************************
**/
  public int getHgap() {
    return hgap;
    }


/**
**********************************************************************************************
Sets the horizontal gap between components
@param hgap the horizontal gap
**********************************************************************************************
**/
  public void setHgap(int hgap) {
    this.hgap = hgap;
    }


/**
**********************************************************************************************
Gets the vertical gap between components
@return the vertical gap
**********************************************************************************************
**/
  public int getVgap() {
    return vgap;
    }


/**
**********************************************************************************************
Sets the vertical gap between components
@param vgap the vertical gap
**********************************************************************************************
**/
  public void setVgap(int vgap) {
    this.vgap = vgap;
    }


/**
**********************************************************************************************
Adds a component to the layout
@param comp the component to add
@param constraints the position in the layout to add this component
**********************************************************************************************
**/
  public void addLayoutComponent(Component comp, Object constraints) {
    synchronized (comp.getTreeLock()) {
      if ((constraints == null) || (constraints instanceof String)) {
        addLayoutComponent((String)constraints, comp);
        }
      else {
        throw new IllegalArgumentException("cannot add to layout: constraint must be a string (or null)");
        }
      }
    }


/**
**********************************************************************************************
Adds a component to the layout
@param comp the component to add
@param name the position in the layout to add this component
**********************************************************************************************
**/
  public void addLayoutComponent(String name, Component comp) {
    synchronized (comp.getTreeLock()) {
    /* Special case:  treat null the same as "Center". */
    if (name == null) {
      name = "Center";
      }

  /* Assign the component to one of the known regions of the layout. */
    if ("Center".equals(name)) {
      center = comp;
      }
    else if ("North".equals(name)) {
      north = comp;
      }
    else if ("South".equals(name)) {
      south = comp;
      }
    else if ("East".equals(name)) {
      east = comp;
      }
    else if ("West".equals(name)) {
      west = comp;
      }
    else if (BEFORE_FIRST_LINE.equals(name)) {
      firstLine = comp;
      }
    else if (AFTER_LAST_LINE.equals(name)) {
      lastLine = comp;
      }
    else if (BEFORE_LINE_BEGINS.equals(name)) {
      firstItem = comp;
      }
    else if (AFTER_LINE_ENDS.equals(name)) {
      lastItem = comp;
      }
    else {
      throw new IllegalArgumentException("cannot add to layout: unknown constraint: " + name);
      }
    }
  }


/**
**********************************************************************************************
Removes a component from the layout
@param comp the component to remove
**********************************************************************************************
**/
  public void removeLayoutComponent(Component comp) {
    synchronized (comp.getTreeLock()) {
      if (comp == center) {
        center = null;
        }
      else if (comp == north) {
        north = null;
        }
      else if (comp == south) {
        south = null;
        }
      else if (comp == east) {
        east = null;
        }
      else if (comp == west) {
        west = null;
        }

      if (comp == firstLine) {
        firstLine = null;
        }
      else if (comp == lastLine) {
        lastLine = null;
        }
      else if (comp == firstItem) {
        firstItem = null;
        }
      else if (comp == lastItem) {
        lastItem = null;
        }
      }
    }


/**
**********************************************************************************************
Gets the minimum layout size of this component
@param target the component upon which the display is drawn
@return the minimum size
**********************************************************************************************
**/
  public Dimension minimumLayoutSize(Container target) {
    synchronized (target.getTreeLock()) {
      Dimension dim = new Dimension(0, 0);

      boolean ltr = target.getComponentOrientation().isLeftToRight();
      Component c = null;

    if ((c=getChild(EAST,ltr)) != null) {
      Dimension d = c.getMinimumSize();
      dim.width += d.width + hgap;
      dim.height = Math.max(d.height, dim.height);
      }
    if ((c=getChild(WEST,ltr)) != null) {
      Dimension d = c.getMinimumSize();
      dim.width += d.width + hgap;
      dim.height = Math.max(d.height, dim.height);
      }
    if ((c=getChild(CENTER,ltr)) != null) {
      Dimension d = c.getMinimumSize();
      dim.width += d.width;
      dim.height = Math.max(d.height, dim.height);
      }
    if ((c=getChild(NORTH,ltr)) != null) {
      Dimension d = c.getMinimumSize();
      dim.width = Math.max(d.width, dim.width);
      dim.height += d.height + vgap;
      }
    if ((c=getChild(SOUTH,ltr)) != null) {
      Dimension d = c.getMinimumSize();
      dim.width = Math.max(d.width, dim.width);
      dim.height += d.height + vgap;
      }

    Insets insets = target.getInsets();
    dim.width += insets.left + insets.right;
    dim.height += insets.top + insets.bottom;

    return dim;
    }
  }


/**
**********************************************************************************************
Gets the preferred layout size of this component
@param target the component upon which the display is drawn
@return the preferred size
**********************************************************************************************
**/
  public Dimension preferredLayoutSize(Container target) {
    synchronized (target.getTreeLock()) {
      Dimension dim = new Dimension(0, 0);

      boolean ltr = target.getComponentOrientation().isLeftToRight();
      Component c = null;

      if ((c=getChild(EAST,ltr)) != null) {
        Dimension d = c.getPreferredSize();
        dim.width += d.width + hgap;
        dim.height = Math.max(d.height, dim.height);
        }
      if ((c=getChild(WEST,ltr)) != null) {
        Dimension d = c.getPreferredSize();
        dim.width += d.width + hgap;
        dim.height = Math.max(d.height, dim.height);
        }
      if ((c=getChild(CENTER,ltr)) != null) {
        Dimension d = c.getPreferredSize();
        dim.width += d.width;
        dim.height = Math.max(d.height, dim.height);
        }
      if ((c=getChild(NORTH,ltr)) != null) {
        Dimension d = c.getPreferredSize();
        dim.width = Math.max(d.width, dim.width);
        dim.height += d.height + vgap;
        }
      if ((c=getChild(SOUTH,ltr)) != null) {
        Dimension d = c.getPreferredSize();
        dim.width = Math.max(d.width, dim.width);
        dim.height += d.height + vgap;
        }

      Insets insets = target.getInsets();
      dim.width += insets.left + insets.right;
      dim.height += insets.top + insets.bottom;

      return dim;
      }
    }


/**
**********************************************************************************************
Gets the maximum layout size of this component
@param target the component upon which the display is drawn
@return the maximum possible size
**********************************************************************************************
**/
  public Dimension maximumLayoutSize(Container target) {
    return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }


/**
**********************************************************************************************
Gets the X alignment
@param parent the parent component
@return 0.5f
**********************************************************************************************
**/
  public float getLayoutAlignmentX(Container parent) {
    return 0.5f;
    }


/**
**********************************************************************************************
Gets the Y alignment
@param parent the parent component
@return 0.5f
**********************************************************************************************
**/
  public float getLayoutAlignmentY(Container parent) {
    return 0.5f;
    }


/**
**********************************************************************************************
Invalidates the layout, making way for a refresh
@param target the component upon which the display is drawn
**********************************************************************************************
**/
  public void invalidateLayout(Container target) {
    }


/**
**********************************************************************************************
Sets the sizes and positions of the components of this panel
@param target the component upon which the display is drawn
**********************************************************************************************
**/
  public void layoutContainer(Container target) {
    synchronized (target.getTreeLock()) {
      Insets insets = target.getInsets();

      int top = insets.top;
      int left = insets.left;

      int height = target.getHeight() - top - insets.bottom;
      int width = target.getWidth() - left - insets.right;

      int height2 = 1;
      int width2 = 1;

      int centerHeight = 1;
      int centerWidth = 1;

      int bottom = target.getHeight() + top;
      int right = target.getWidth() + left;

      boolean ltr = target.getComponentOrientation().isLeftToRight();
      Component c = null;

      // first determine the size of the center component
      if ((c=getChild(CENTER,ltr)) != null) {
        c.setSize(width, height);
        Dimension d = c.getPreferredSize();

        // determine the remaining width/height around the center component
        centerHeight = d.height;
        centerWidth = d.width;

        height2 = (height - centerHeight) / 2;
        width2 = (width - centerWidth) / 2;
        }
      else {
        // set the hgap and vgap to half their size, so that the remaining components have
        // a correct gap between them
        if (vgap != 0){
          vgap /= 2;
          }
        if (hgap != 0){
          hgap /= 2;
          }
        }

      // set the sizes of each remaining component
      if ((c=getChild(NORTH,ltr)) != null) {
        c.setBounds(left, top, width, height2 - vgap);
        top += height2;
        height -= height2;
        }
      if ((c=getChild(SOUTH,ltr)) != null) {
        c.setBounds(left, bottom - height2 + vgap, width, height2-vgap);
        height -= height2;
        }
      if ((c=getChild(WEST,ltr)) != null) {
        c.setBounds(left, top, width2 - hgap, height);
        left += width2;
        width -= width2;
        }
      if ((c=getChild(EAST,ltr)) != null) {
        c.setBounds(right - width2 + hgap, top, width2 - hgap, height);
        width -= width2;
        }

      // now set the size of the center component
      if ((c=getChild(CENTER,ltr)) != null) {
        c.setBounds(left,top,width,height);
        }

      }
    }


/**
**********************************************************************************************
Gets a child for use in laying out the component
@param key the component to get
@parm ltr left to right layout?
@return the component, or null if the cpmonent doesn't exist
**********************************************************************************************
**/
  private Component getChild(String key, boolean ltr) {
    Component result = null;

    if (key == NORTH) {
      result = (firstLine != null) ? firstLine : north;
      }
    else if (key == SOUTH) {
      result = (lastLine != null) ? lastLine : south;
      }
    else if (key == WEST) {
      result = ltr ? firstItem : lastItem;
      if (result == null) {
        result = west;
        }
      }
    else if (key == EAST) {
      result = ltr ? lastItem : firstItem;
      if (result == null) {
        result = east;
        }
      }
    else if (key == CENTER) {
      result = center;
      }

    if (result != null && !result.isVisible()) {
      result = null;
      }

    return result;
    }


/**
**********************************************************************************************
Gets a String representation of this object
@return a String describing the object.
**********************************************************************************************
**/
  public String toString() {
    return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + "]";
    }

  }
