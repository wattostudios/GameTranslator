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

import org.watto.ErrorLogger;
import org.watto.Language;
import org.watto.event.*;
import org.watto.xml.XMLNode;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.ItemEvent;
import javax.swing.*;
import java.util.Vector;

/**
**********************************************************************************************
A Template
**********************************************************************************************
**/
public class WSComboBox extends JComboBox implements WSComponent,
                                                     WSSelectableInterface {


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

  /** Only adds the items to the XML (when saving) if they were given in the XML when loading **/
  boolean addedItemsFromNode = false;

  ListCellRenderer currentValueRenderer = null;


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  public WSComboBox(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSComboBox(XMLNode node){
    super();
    buildObject(node);
    registerEvents();

    // MUST DO THIS HERE so that it doesn't paint the gray background
    setOpaque(false);
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

    addItemsFromNode(node);
    }


/**
**********************************************************************************************
Builds an XMLNode that describes this object
@return an XML node with the details of this object
**********************************************************************************************
**/
  public XMLNode buildXML(){
    XMLNode node = WSHelper.buildXML(this);

    addItemsToNode(node);

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
                 AWTEvent.FOCUS_EVENT_MASK |
                 AWTEvent.KEY_EVENT_MASK |
                 AWTEvent.MOUSE_EVENT_MASK |
                 AWTEvent.MOUSE_MOTION_EVENT_MASK |
                 AWTEvent.ACTION_EVENT_MASK |
                 AWTEvent.ITEM_EVENT_MASK | //A selectable item (button, checkbox, combobox, radiobutton, list)
                 WSComponent.WS_EVENT_MASK
                 );

    // needs to add the listener for selection changes on the combobox.
    // it still passes it through the normal event heirachy for processing.
    addItemListener(new WSSelectableListener(this));

    }



///////////////
//
// Class-Specific Methods
//
///////////////

/**
**********************************************************************************************
Adds the children to the combo box
**********************************************************************************************
**/
  public void addItemsFromNode(XMLNode node){
    int numChildren = node.getChildCount();

    if (numChildren > 0){
      String[] values = new String[numChildren];
      for (int i=0;i<numChildren;i++){
        try {
          XMLNode child = node.getChild(i);
          String childTag = child.getTag();

          if (childTag.equalsIgnoreCase("Item")){
            String value = child.getAttribute("code");
            if (value == null){
              value = child.getContent();
              }
            else{
              value = Language.get(code);
              }
            values[i] = value;
            }
          else {
            throw new WSComponentException("Invalid tag name for WSComboBox child: " + childTag);
            }

          }
        catch (Throwable t){
          ErrorLogger.log(t);
          }
        }
      setModel(new DefaultComboBoxModel(values));
      addedItemsFromNode = true;
      }
    }


/**
**********************************************************************************************
Adds the children to the node
**********************************************************************************************
**/
  public void addItemsToNode(XMLNode node){
    if (addedItemsFromNode){ // only add items if they were build from the XML itself
      int numChildren = getItemCount();

      for (int i=0;i<numChildren;i++){
        String child = (String)getItemAt(i);
        node.addChild(new XMLNode("Item",child));
        }
      }

    }


/**
**********************************************************************************************
Gets the renderer used to paint the current value (if it differs from the renderer for the
items in the popup list)
**********************************************************************************************
**/
  public ListCellRenderer getCurrentValueRenderer(){
    if (currentValueRenderer == null){
      return getRenderer();
      }
    //((JLabel)currentValueRenderer).addKeyListener(new WSKeyableListener(this));
    return currentValueRenderer;
    }


/**
**********************************************************************************************
The event that is triggered from a WSSelectableListener when an item is deselected
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onDeselect(JComponent c, Object e){
    if (e instanceof ItemEvent){
      WSEventHandler.processEvent(this.getParent(),(ItemEvent)e);
      }
    return true;
    }


/**
**********************************************************************************************
The event that is triggered from a WSSelectableListener when an item is selected
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onSelect(JComponent c, Object e){
    if (e instanceof ItemEvent){
      WSEventHandler.processEvent(this.getParent(),(ItemEvent)e);
      }
    return true;
    }


/**
**********************************************************************************************
Sets the renderer used to paint the current value (if it differs from the renderer for the
items in the popup list)
**********************************************************************************************
**/
  public void setCurrentValueRenderer(ListCellRenderer currentValueRenderer){
    this.currentValueRenderer = currentValueRenderer;
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
// Methods For Consistency Between Swing Classes
//
///////////////

/**
**********************************************************************************************

**********************************************************************************************
**/
public void add(String s){
  addItem(s);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void addRowSelectionInterval(int index){
  setSelectedIndex(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void addSelection(int index){
  setSelectedIndex(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void addSelectionInterval(int index){
  setSelectedIndex(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void changeSelection(int index){
  setSelectedIndex(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void changeSelection(int index, boolean toggle, boolean extend){
  setSelectedIndex(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public boolean editCellAt(int index){
  setEditable(true);
  return true;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public ComboBoxEditor getCellEditor(){
  return getEditor();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public ListCellRenderer getCellRenderer(){
  return getRenderer();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Component getComponent(){
  return (Component)getRenderer();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Component getComponentAt(int index){
  Object item = getItemAt(index);
  if (item instanceof Component){
    return (Component)item;
    }
  return null;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getComponentIndex(Component c){
  return getIndex(c);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getIndex(Object object){
  for (int i=0;i<getItemCount();i++){
    if (getItemAt(i).equals(object)){
      return i;
      }
    }
  return -1;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public ComboBoxEditor getDefaultEditor(Class type){
  return getEditor();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public ListCellRenderer getDefaultRenderer(Class type){
  return getRenderer();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getFirstVisibleIndex(){
  return 0;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getFixedCellHeight(){
  if (getItemCount() > 0){
    Object item = getItemAt(0);
    if (item instanceof Component){
      return ((Component)item).getHeight();
      }
    }
  return -1;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getFixedCellWidth(){
  if (getItemCount() > 0){
    Object item = getItemAt(0);
    if (item instanceof Component){
      return ((Component)item).getWidth();
      }
    }
  return -1;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getItem(int index){
  return getItemAt(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getLastVisibleIndex(){
  return getItemCount();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getLeadSelectionIndex(){
  return getSelectedIndex();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getMaxSelectionIndex(){
  return getSelectedIndex();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getMinSelectionIndex(){
  return getSelectedIndex();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getObjectAt(int index){
  return getItemAt(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getRowCount(){
  return getItemCount();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getRowHeight(){
  return getFixedCellHeight();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getRowHeight(int row){
  return getFixedCellHeight();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Component getSelectedComponent(){
  Object item = getSelectedItem();
  if (item instanceof Component){
    return (Component)item;
    }
  return null;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int[] getSelectedIndices(){
  int index = getSelectedIndex();
  if (index != -1){
    return new int[]{index};
    }
  return new int[0];
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getSelectedItemCount(){
  int selectedIndex = getSelectedIndex();
  if (selectedIndex == -1){
    return 0;
    }
  return 1;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[] getSelectedItems(){
  return getSelectedObjects();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getSelectedObject(){
  return getSelectedItem();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getSelectedObjectCount(){
  return getSelectedItemCount();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getSelectedRow(){
  return getSelectedIndex();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getSelectedRowCount(){
  return getSelectedItemCount();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int[] getSelectedRows(){
  return getSelectedIndices();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getSelectedValue(){
  return getSelectedItem();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getSelectedValueCount(){
  return getSelectedItemCount();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[] getSelectedValues(){
  return getSelectedObjects();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getValueAt(int index){
  return getItemAt(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int indexAtLocation(int x, int y){
  return 0;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int indexAtPoint(Point p){
  return 0;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int indexOfComponent(Component c){
  return getComponentIndex(c);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Point indexToLocation(int index){
  return new Point(0,0);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void insert(Component c, int index){
  insertItemAt(c,index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void insert(String s, int index){
  insertItemAt(s,index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void insertObjectAt(Object object, int index){
  insertItemAt(object,index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void insertValueAt(Object object, int index){
  insertItemAt(object,index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Point pointAtIndex(int index){
  return indexToLocation(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Component prepareEditor(ComboBoxEditor editor, int index){
  configureEditor(editor,getItemAt(index));
  return null;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Component prepareRenderer(Component renderer, int index){
  return getComponentAt(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeItem(int index){
  removeItemAt(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeItems(int[] index){
  java.util.Arrays.sort(index);

  for (int i=index.length-1;i>=0;i--){
    removeItemAt(index[i]);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeItems(Object[] objects){
  int numItems = getItemCount();
  int numObjects = objects.length;

  int[] removeIndexes = new int[numObjects];
  int numRemove = 0;

  for (int i=0;i<numItems;i++){
    Object item = getItemAt(i);
    for (int j=0;j<numObjects;j++){
      if (objects[j].equals(item)){
        removeIndexes[numRemove] = i;
        numRemove++;
        break;
        }
      }
    }

  if (numRemove < numObjects){
    int[] temp = removeIndexes;
    removeIndexes = new int[numRemove];
    System.arraycopy(temp,0,removeIndexes,0,numRemove);
    }

  removeItems(removeIndexes);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeObject(int index){
  removeItemAt(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeObject(Object object){
  removeItem(object);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeObjects(int[] index){
  removeItems(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeObjects(Object[] objects){
  removeItems(objects);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeValue(int index){
  removeItemAt(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeValue(Object object){
  removeItem(object);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeValues(int[] index){
  removeItems(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeValues(Object[] objects){
  removeItems(objects);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setCellEditor(ComboBoxEditor editor){
  setEditor(editor);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setCellRenderer(ListCellRenderer renderer){
  setRenderer(renderer);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setComponentAt(int index, Component c){
  removeItemAt(index);
  insertItemAt(c,index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setData(Object[] data){
  setModel(new DefaultComboBoxModel(data));
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setData(Vector data){
  setModel(new DefaultComboBoxModel(data));
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setDefaultEditor(Class type, ComboBoxEditor editor){
  setEditor(editor);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setDefaultRenderer(Class type, ListCellRenderer renderer){
  setRenderer(renderer);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setFixedCellHeight(int height){
  int numItems = getItemCount();
  for (int i=0;i<numItems;i++){
    Object item = getItemAt(i);
    if (item instanceof Component){
      Component c = (Component)item;
      c.setSize(c.getWidth(),height);
      }
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setFixedCellWidth(int width){
  int numItems = getItemCount();
  for (int i=0;i<numItems;i++){
    Object item = getItemAt(i);
    if (item instanceof Component){
      Component c = (Component)item;
      c.setSize(width,c.getHeight());
      }
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setListData(Object[] data){
  setData(data);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setListData(Vector data){
  setData(data);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setRowHeight(int height){
  setFixedCellHeight(height);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setRowHeight(int row, int height){
  Object item = getItemAt(row);
  if (item instanceof Component){
    Component c = (Component)item;
    c.setSize(c.getWidth(),height);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelected(Component c){
  setSelectedItem(c);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelected(Object object){
  setSelectedItem(object);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedComponent(Component c){
  setSelectedItem(c);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedIndices(int[] indices){
  if (indices.length > 0){
    setSelectedIndex(indices[0]);
    }
  else {
    setSelectedIndex(-1);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedItem(int item){
  setSelectedIndex(item);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedItems(int[] items){
  setSelectedIndices(items);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedItems(Object[] object){
  if (object.length > 0){
    setSelectedItem(object[0]);
    }
  else {
    setSelectedItem(null);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedObject(int item){
  setSelectedIndex(item);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedObject(Object object){
  setSelectedObject(object);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedObjects(int[] items){
  setSelectedItems(items);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedObjects(Object[] object){
  setSelectedItems(object);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedValue(int item){
  setSelectedIndex(item);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedValue(Object object){
  setSelectedItem(object);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedValue(Object object, Boolean scrollTo){
  setSelectedItem(object);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedValues(int[] items){
  setSelectedItems(items);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedValues(Object[] object){
  setSelectedItems(object);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectionInterval(int index){
  setSelectedIndex(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setValueAt(Object value, int index){
  removeItemAt(index);
  insertItemAt(value,index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getValue(int index){
  return getItemAt(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getObject(int index){
  return getItemAt(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[] getItems(){
  ComboBoxModel model = getModel();

  int numItems = model.getSize();
  Object[] items = new Object[numItems];
  for (int i=0;i<numItems;i++){
    items[i] = model.getElementAt(i);
    }
  return items;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[] getObjects(){
  return getItems();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[] getValues(){
  return getItems();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[] getData(){
  return getItems();
  }



///////////////
//
// JComboBox-implemented
//
///////////////

/**
**********************************************************************************************
Gets the border [Automatically implemented if extending a JComboBox]
**********************************************************************************************
**/
/*
  public Border getBorder(){
    return super.getBorder();
    }
*/

/**
**********************************************************************************************
Gets the maximum size [Automatically implemented if extending a JComboBox]
**********************************************************************************************
**/
/*
  public Dimension getMaximumSize(){
    return super.getMaximumSize();
    }
*/

/**
**********************************************************************************************
Gets the minimum size [Automatically implemented if extending a JComboBox]
**********************************************************************************************
**/
/*
  public Dimension getMinimumSize(){
    return super.getMinimumSize();
    }
*/

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
Gets the preferred size [Automatically implemented if extending a JComboBox]
**********************************************************************************************
**/
/*
  public Dimension getPreferredSize(){
    return super.getPreferredSize();
    }
*/

/**
**********************************************************************************************
Is this component focused? [Automatically implemented if extending a JComboBox]
**********************************************************************************************
**/
/*
  public boolean hasFocus(){
    return super.hasFocus();
    }
*/

/**
**********************************************************************************************
Is this component enabled? [Automatically implemented if extending a JComboBox]
**********************************************************************************************
**/
/*
  public boolean isEnabled(){
    return super.isEnabled();
    }
*/

/**
**********************************************************************************************
Is this component opaque? [Automatically implemented if extending a JComboBox]
**********************************************************************************************
**/
/*
  public boolean isOpaque(){
    return super.isOpaque();
    }
*/

/**
**********************************************************************************************
Is this component visible? [Automatically implemented if extending a JComboBox]
**********************************************************************************************
**/
/*
  public boolean isVisible(){
    return super.isVisible();
    }
*/

/**
**********************************************************************************************
Sets the border [Automatically implemented if extending a JComboBox]
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
Sets the maximum size [Automatically implemented if extending a JComboBox]
**********************************************************************************************
**/
/*
  public void setMaximumSize(Dimension d){
    super.setMaximumSize(d);
    }
*/

/**
**********************************************************************************************
Sets the minimum size [Automatically implemented if extending a JComboBox]
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
Sets the preferred size [Automatically implemented if extending a JComboBox]
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