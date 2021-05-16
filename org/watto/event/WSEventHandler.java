////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                            WATTO STUDIOS JAVA PROGRAM TEMPLATE                             //
//                  Template Classes and Helper Utilities for Java Programs                   //
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

package org.watto.event;

import org.watto.component.*;

import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.EventObject;
import javax.swing.border.EmptyBorder;

/**
**********************************************************************************************
EventHandler and common methods used by WSComponents
**********************************************************************************************
**/
public class WSEventHandler {

  /** the singleton instance of the helper **/
  static WSEventHandler instance = new WSEventHandler();


/**
**********************************************************************************************
Constructor, if needed.
**********************************************************************************************
**/
  public WSEventHandler(){
    }


/**
**********************************************************************************************
Gets the singleton instance of the helper
@return the <i>instance</i>
**********************************************************************************************
**/
  public static WSEventHandler getInstance(){
    return instance;
    }



/**
**********************************************************************************************
Gets the parent of the given component
**********************************************************************************************
**/
  public static Component getParent(Object component){
    if (component instanceof Component){
      //if (component instanceof WSPopupMenu){
      //  return ((WSPopupMenu)component).getRealParent();
      //  }
      if (component instanceof JPopupMenu){
        return ((JPopupMenu)component).getInvoker();
        }
      else {
        return ((Component)component).getParent();
        }
      }
    return null;
    }


/**
**********************************************************************************************
Processes a particular event type
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void processActionEvent(Object component, ActionEvent event){
    triggerOnSelectEvent(component,event);
    }


/**
**********************************************************************************************
Processes a particular event type
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void processChangeEvent(Object component, ChangeEvent event){
    if (event.getSource() instanceof TableColumnModel){
      triggerOnColumnResizeEvent(component,event);
      }
    }


/**
**********************************************************************************************
Processes a particular event type
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void processComponentEvent(Object component, ComponentEvent event){
    int id = event.getID();

    if (id == ComponentEvent.COMPONENT_RESIZED){
      triggerOnResizeEvent(component,event);
      }
    }


/**
**********************************************************************************************
Processes the event
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void processEvent(Object component, EventObject event){
    if (event instanceof AWTEvent){
      if (event instanceof MouseEvent){ //WSClickable, WSDoubleClickable, WSDragable, WSHoverable, WSRightClickable
        processMouseEvent(component,(MouseEvent)event);
        }
      else if (event instanceof FocusEvent){ //WSFocusable
        processFocusEvent(component,(FocusEvent)event);
        }
      else if (event instanceof KeyEvent){ //WSKeyable
        processKeyEvent(component,(KeyEvent)event);
        }
      else if (event instanceof ActionEvent){ //WSSelectable(ComboBox)
        processActionEvent(component,(ActionEvent)event);
        }
      else if (event instanceof ItemEvent){ //WSSelectable (RadioButton, Checkbox)
        processItemEvent(component,(ItemEvent)event);
        }
      else if (event instanceof WSEvent){ //WSEventable (MUST BE BEFORE COMPONENT EVENT, AS WSEVENT IS A COMPONENT EVENT)
        processGenericEvent(component,(WSEvent)event);
        }
      else if (event instanceof ComponentEvent){ //Resize os a component
        processComponentEvent(component,(ComponentEvent)event);
        }
      }
    else {
      if (event instanceof ListSelectionEvent){ //WSSelectable (List, Table)
        processListSelectionEvent(component,(ListSelectionEvent)event);
        }
      else if (event instanceof ChangeEvent){ //WSTableColumnable (Table)
        processChangeEvent(component,(ChangeEvent)event);
        }
      else if (event instanceof TreeSelectionEvent){ //WSSelectable (Tree)
        processTreeSelectionEvent(component,(TreeSelectionEvent)event);
        }
      else if (event instanceof HyperlinkEvent){ //WSLinkable (EditorPane)
        processHyperlinkEvent(component,(HyperlinkEvent)event);
        }
      }
    }


/**
**********************************************************************************************
Processes a particular event type
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void processFocusEvent(Object component, FocusEvent event){
    int id = event.getID();

    if (id == FocusEvent.FOCUS_GAINED){
      triggerOnFocusEvent(component,event);
      }
    else if (id == FocusEvent.FOCUS_LOST){
      triggerOnFocusOutEvent(component,event);
      }
    }


/**
**********************************************************************************************
Processes a generic event type
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void processGenericEvent(Object component, WSEvent event){
    triggerOnEventEvent(component,event);
    }


/**
**********************************************************************************************
Processes a particular event type
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void processHyperlinkEvent(Object component, HyperlinkEvent event){
    if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
      triggerOnLinkEvent(component,event);
      }
    }


/**
**********************************************************************************************
Processes a particular event type
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void processItemEvent(Object component, ItemEvent event){
    int id = event.getStateChange();

    if (id == ItemEvent.SELECTED){
      triggerOnSelectEvent(component,event);
      }
    else if (id == ItemEvent.DESELECTED){
      triggerOnDeselectEvent(component,event);
      }
    }


/**
**********************************************************************************************
Processes a particular event type
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void processKeyEvent(Object component, KeyEvent event){
    if (event.getID() == KeyEvent.KEY_RELEASED){
      if (event.getKeyCode() == KeyEvent.VK_ENTER){
        triggerOnEnterEvent(component,event);
        }

      triggerOnKeyPressEvent(component,event);
      }
    }


/**
**********************************************************************************************
Processes a particular event type
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void processListSelectionEvent(Object component, ListSelectionEvent event){
    if (! event.getValueIsAdjusting()){
      triggerOnSelectEvent(component,event);
      }
    }


/**
**********************************************************************************************
Processes a particular event type
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void processMouseEvent(Object component, MouseEvent event){
    int id = event.getID();

    if (id == MouseEvent.MOUSE_CLICKED){
      if (event.getClickCount() == 2){
        triggerOnDoubleClickEvent(component,event);
        }
      }
    else if (id == MouseEvent.MOUSE_RELEASED){
      if (event.getButton() != MouseEvent.BUTTON1){
        triggerOnRightClickEvent(component,event);
        }
      else {
        triggerOnClickEvent(component,event);
        }

      }
    else if (id == MouseEvent.MOUSE_ENTERED){
      triggerOnHoverEvent(component,event);
      }
    else if (id == MouseEvent.MOUSE_EXITED){
      triggerOnHoverOutEvent(component,event);
      }
    else if (id == MouseEvent.MOUSE_MOVED){
      triggerOnMotionEvent(component,event);
      }
    }


/**
**********************************************************************************************
Processes a particular event type
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void processTreeSelectionEvent(Object component, TreeSelectionEvent event){
    if (event.isAddedPath()){
      triggerOnSelectEvent(component,event);
      }
    else {
      triggerOnDeselectEvent(component,event);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnClickEvent(Object component, MouseEvent event){
    while (component != null){
      if (component instanceof WSClickableInterface){
        // only stop if this particular event was processed
        if (((WSClickableInterface)component).onClick((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnColumnResizeEvent(Object component, ChangeEvent event){
    while (component != null){
      if (component instanceof WSTableColumnableInterface){
        // only stop if this particular event was processed
        if (((WSTableColumnableInterface)component).onColumnResize((TableColumnModel)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnDoubleClickEvent(Object component, MouseEvent event){
    while (component != null){
      if (component instanceof WSDoubleClickableInterface){
        // only stop if this particular event was processed
        if (((WSDoubleClickableInterface)component).onDoubleClick((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnEnterEvent(Object component, KeyEvent event){
    while (component != null){
      if (component instanceof WSEnterableInterface){
        // only stop if this particular event was processed
        if (((WSEnterableInterface)component).onEnter((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnEventEvent(Object component, WSEvent event){
    while (component != null){
      if (component instanceof WSEventableInterface){
        // only stop if this particular event was processed
        if (((WSEventableInterface)component).onEvent((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnFocusEvent(Object component, FocusEvent event){
    while (component != null){
      if (component instanceof WSFocusableInterface){
        // only stop if this particular event was processed
        if (((WSFocusableInterface)component).onFocus((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnFocusOutEvent(Object component, FocusEvent event){
    while (component != null){
      if (component instanceof WSFocusableInterface){
        // only stop if this particular event was processed
        if (((WSFocusableInterface)component).onFocusOut((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnHoverEvent(Object component, MouseEvent event){
    while (component != null){
      if (component instanceof WSHoverableInterface){
        // only stop if this particular event was processed
        if (((WSHoverableInterface)component).onHover((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnHoverOutEvent(Object component, MouseEvent event){
    while (component != null){
      if (component instanceof WSHoverableInterface){
        // only stop if this particular event was processed
        if (((WSHoverableInterface)component).onHoverOut((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnKeyPressEvent(Object component, KeyEvent event){
    while (component != null){
      if (component instanceof WSKeyableInterface){
        // only stop if this particular event was processed
        if (((WSKeyableInterface)component).onKeyPress((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnLinkEvent(Object component, HyperlinkEvent event){
    while (component != null){
      if (component instanceof WSLinkableInterface){
        // only stop if this particular event was processed
        if (((WSLinkableInterface)component).onHyperlink((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnMotionEvent(Object component, MouseEvent event){
    while (component != null){
      if (component instanceof WSMotionableInterface){
        // only stop if this particular event was processed
        if (((WSMotionableInterface)component).onMotion((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnResizeEvent(Object component, ComponentEvent event){
    while (component != null){
      if (component instanceof WSResizableInterface){
        // only stop if this particular event was processed
        if (((WSResizableInterface)component).onResize((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnRightClickEvent(Object component, MouseEvent event){
    while (component != null){
      if (component instanceof WSRightClickableInterface){
        // only stop if this particular event was processed
        if (((WSRightClickableInterface)component).onRightClick((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnSelectEvent(Object component, ActionEvent event){
    while (component != null){
      if (component instanceof WSSelectableInterface){
        // only stop if this particular event was processed
        if (((WSSelectableInterface)component).onSelect((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnSelectEvent(Object component, ItemEvent event){
    while (component != null){
      if (component instanceof WSSelectableInterface){
        // only stop if this particular event was processed
        if (((WSSelectableInterface)component).onSelect((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnDeselectEvent(Object component, ItemEvent event){
    while (component != null){
      if (component instanceof WSSelectableInterface){
        // only stop if this particular event was processed
        if (((WSSelectableInterface)component).onDeselect((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnSelectEvent(Object component, ListSelectionEvent event){
    while (component != null){
      if (component instanceof WSSelectableInterface){
        // only stop if this particular event was processed
        try {
          if (((WSSelectableInterface)component).onSelect((JComponent)event.getSource(),event)){
            return;
            }
          }
        catch (Throwable t){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnSelectEvent(Object component, TreeSelectionEvent event){
    while (component != null){
      if (component instanceof WSSelectableInterface){
        // only stop if this particular event was processed
        if (((WSSelectableInterface)component).onSelect((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }


/**
**********************************************************************************************
Triggers a particular event type on the components' caller
@param component the component that triggered the event
@param event the event
**********************************************************************************************
**/
  public static void triggerOnDeselectEvent(Object component, TreeSelectionEvent event){
    while (component != null){
      if (component instanceof WSSelectableInterface){
        // only stop if this particular event was processed
        if (((WSSelectableInterface)component).onDeselect((JComponent)event.getSource(),event)){
          return;
          }
        }
      component = getParent(component);
      }
    }




  }
