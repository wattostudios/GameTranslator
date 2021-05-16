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
import org.watto.plaf.AquanauticTheme;
import org.watto.xml.XMLNode;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Point;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.ListCellRenderer;
import javax.swing.event.ListSelectionEvent;
import java.util.Vector;

/**
**********************************************************************************************
A Template
**********************************************************************************************
**/
public class WSList extends JList implements WSComponent,
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


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSList(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSList(XMLNode node){
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

    addItemsFromNode(node);

    // this is replaced by the line in setModel();
    /*
    // if we don't set the cell height, a dump occurs when doing addSelectionInterval()
    // in Task_ReloadDirectoryList when trying to re-select rows in the list
    int cellHeight = AquanauticTheme.TEXT_HEIGHT+8;
    if (cellHeight < 17){ // 17 = 16 (icon height) + 1
      cellHeight = 17;
      }
    setFixedCellHeight(cellHeight);
    */
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
                 AWTEvent.ITEM_EVENT_MASK | //A selectable item (button, checkbox, combobox, radiobutton, list)
                 AWTEvent.HIERARCHY_EVENT_MASK | //Children changes (add/remove, show/hide)
                 AWTEvent.HIERARCHY_BOUNDS_EVENT_MASK | //Children changes  (size)
                 WSComponent.WS_EVENT_MASK
                 );

    // needs to add the listener for selection changes on the list.
    // it still passes it through the normal event heirachy for processing.
    addListSelectionListener(new WSSelectableListener(this));

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
            throw new WSComponentException("Invalid tag name for WSList child: " + childTag);
            }

          }
        catch (Throwable t){
          ErrorLogger.log(t);
          }
        }
      setListData(values);
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
      ListModel model = getModel();

      int numChildren = model.getSize();

      for (int i=0;i<numChildren;i++){
        String child = (String)model.getElementAt(i);
        node.addChild(new XMLNode("Item",child));
        }

      }
    }


/**
**********************************************************************************************
The event that is triggered from a WSSelectableListener when an item is deselected
@param c the component that triggered the event
@param e the event that occurred
**********************************************************************************************
**/
  public boolean onDeselect(JComponent c, Object e){
    if (e instanceof ListSelectionEvent){
      WSEventHandler.processEvent(this.getParent(),(ListSelectionEvent)e);
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
    if (e instanceof ListSelectionEvent){
      WSEventHandler.processEvent(this.getParent(),(ListSelectionEvent)e);
      }
    return true;
    }


/**
**********************************************************************************************
Overwritten to return 1 less, because it is partially covered by the horizontal scrollbar
**********************************************************************************************
**/
  public int getVisibleRowCount(){
    return super.getVisibleRowCount()-1;
    }


/**
**********************************************************************************************
When setting the model, changes the fixed cell height depending on the item.
This is required because a list doesn't always have text, but sometimes other things, like in
the OptionGroup_ToolbarBuilder
**********************************************************************************************
**/
public void setModel(ListModel model){
  /* // don't actually want this any more
  try {
    if (model.getSize() >= 1){
      Object item = model.getElementAt(0);
      int cellHeight = (int)getCellRenderer().getListCellRendererComponent(this,item,0,false,false).getPreferredSize().getHeight();
      setFixedCellHeight(cellHeight);
      }
    }
  catch (Throwable t){
    ErrorLogger.log(t);
    }
  */
  super.setModel(model);
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
  ListModel model = getModel();
  if (model instanceof DefaultListModel){
    ((DefaultListModel)model).addElement(s);
    }
  else {
    add (new JLabel(s));
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void addItem(Object anObject){
  if (anObject instanceof Component){
    add((Component)anObject);
    }
  else {
    ListModel model = getModel();
    if (model instanceof DefaultListModel){
      ((DefaultListModel)model).addElement(anObject);
      }
    else {
      add (new JLabel(anObject.toString()));
      }
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void addRowSelectionInterval(int start, int end){
  addSelectionInterval(start,end);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void addSelection(int start, int end){
  addSelectionInterval(start,end);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void changeSelection(int index){
  if (isSelectedIndex(index)){
    removeSelectionInterval(index,index);
    }
  else {
    addSelectionInterval(index,index);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void changeSelection(int index, boolean toggle, boolean extend){
  if (extend){
    addSelectionInterval(index,index);
    }
  else {
    if (toggle){
      changeSelection(index);
      }
    else {
      setSelectedIndex(index);
      }
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void changeSelection(int row, int column, boolean toggle, boolean extend){
  changeSelection(row,toggle,extend);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public ListCellRenderer getComponent(){
  return getCellRenderer();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Component getComponentAt(int index){
  Object item = getModel().getElementAt(index);
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
  ListModel model = getModel();
  int numItems = model.getSize();

  for (int i=0;i<numItems;i++){
    if (model.getElementAt(i).equals(object)){
      return i;
      }
    }
  return -1;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[] getData(){
  ListModel model = getModel();
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
public ListCellRenderer getDefaultRenderer(Class type){
  return getCellRenderer();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getItem(int index){
  return getModel().getElementAt(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getItemAt(int index){
  return getItem(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getItemCount(){
  return getModel().getSize();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[] getItems(){
  return getData();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getMaximumRowCount(){
  return getVisibleRowCount();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getObject(int index){
  return getItem(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getObjectAt(int index){
  return getItem(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[] getObjects(){
  return getData();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public ListCellRenderer getRenderer(){
  return getCellRenderer();
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
  Object item = getSelectedValue();
  if (item instanceof Component){
    return (Component)item;
    }
  return null;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getSelectedItem(){
  return getSelectedValue();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getSelectedItemCount(){
  return getSelectedValues().length;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[] getSelectedItems(){
  return getSelectedValues();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getSelectedObject(){
  return getSelectedValue();
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
public Object[] getSelectedObjects(){
  return getSelectedValues();
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
public int getSelectedValueCount(){
  return getSelectedItemCount();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getValue(int index){
  return getItem(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getValueAt(int index){
  return getItem(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getValueAt(int row, int column){
  return getItem(row);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[] getValues(){
  return getData();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int indexAtLocation(int x, int y){
  return locationToIndex(new Point(x,y));
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int indexAtPoint(Point p){
  return locationToIndex(p);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int indexOfComponent(Component c){
  return getIndex(c);
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
public void insertItemAt(Object anObject, int index){
  ListModel model = getModel();
  if (model instanceof DefaultListModel){
    ((DefaultListModel)model).add(index,anObject);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void insertObjectAt(Object anObject, int index){
  insertItemAt(anObject,index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void insertValueAt(Object anObject, int index){
  insertItemAt(anObject,index);
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
public Component prepareRenderer(Component renderer, int index){
  Object item = getItem(index);
  if (item instanceof Component){
    return (Component)item;
    }
  return renderer;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Component prepareRenderer(Component renderer, int row, int column){
  return prepareRenderer(renderer,row);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeAllItems(){
  setModel(new DefaultListModel());
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeItem(int index){
  ListModel model = getModel();
  if (model instanceof DefaultListModel){
    ((DefaultListModel)model).remove(index);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeItem(Object object){
  ListModel model = getModel();
  if (model instanceof DefaultListModel){
    ((DefaultListModel)model).removeElement(object);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeItemAt(int index){
  removeItem(index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeItems(int[] index){
  java.util.Arrays.sort(index);

  ListModel model = getModel();
  if (model instanceof DefaultListModel){
    DefaultListModel m = (DefaultListModel)model;

    for (int i=index.length-1;i>=0;i--){
      m.remove(index[i]);
      }
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeItems(Object[] objects){
  ListModel model = getModel();
  if (model instanceof DefaultListModel){
    DefaultListModel m = (DefaultListModel)model;

    int numItems = m.getSize();
    int numObjects = objects.length;

    int[] removeIndexes = new int[numObjects];
    int numRemove = 0;

    for (int i=0;i<numItems;i++){
      Object item = m.getElementAt(i);
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

  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeObject(int index){
  removeItem(index);
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
public void removeRowSelectionInterval(int start, int end){
  removeSelectionInterval(start,end);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeSelection(int start, int end){
  removeSelectionInterval(start,end);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeValue(int index){
  removeItem(index);
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
public int rowAtPoint(Point p){
  return indexAtPoint(p);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void selectAll(){
  setSelectionInterval(0,getModel().getSize());
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setComponentAt(int index, Component c){
  ListModel model = getModel();
  if (model instanceof DefaultListModel){
    ((DefaultListModel)model).set(index,c);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setData(Object[] data){
  DefaultListModel model = new DefaultListModel();
  model.ensureCapacity(data.length);
  for (int i=0;i<data.length;i++){
    model.addElement(data[i]);
    }
  setModel(model);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setData(Vector data){
  DefaultListModel model = new DefaultListModel();
  model.ensureCapacity(data.size());
  for (int i=0;i<data.size();i++){
    model.addElement(data.get(i));
    }
  setModel(model);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setDefaultRenderer(Class type, ListCellRenderer renderer){
  setCellRenderer(renderer);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setMaximumRowCount(int count){
  setVisibleRowCount(count);
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
  setFixedCellHeight(height);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelected(Component c){
  setSelectedValue(c,true);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelected(Object anObject){
  setSelectedValue(anObject,true);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedComponent(Component c){
  setSelectedValue(c,true);
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
public void setSelectedItem(Object anObject){
  setSelectedValue(anObject,true);
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
public void setSelectedItems(Object[] anObject){
  for (int i=0;i<anObject.length;i++){
    setSelectedValue(anObject,false);
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
public void setSelectedObject(Object anObject){
  setSelectedValue(anObject,true);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedObjects(int[] items){
  setSelectedIndices(items);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedObjects(Object[] anObject){
  setSelectedItems(anObject);
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
public void setSelectedValue(Object anObject){
  setSelectedValue(anObject,true);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedValues(int[] items){
  setSelectedIndices(items);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedValues(Object[] anObject){
  setSelectedItems(anObject);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setValueAt(Object value, int index){
  ListModel model = getModel();
  if (model instanceof DefaultListModel){
    ((DefaultListModel)model).set(index,value);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setValueAt(Object value, int row, int column){
  setValueAt(value,row);
  }



///////////////
//
// JList-implemented
//
///////////////

/**
**********************************************************************************************
Gets the border [Automatically implemented if extending a JList]
**********************************************************************************************
**/
/*
  public Border getBorder(){
    return super.getBorder();
    }
*/

/**
**********************************************************************************************
Gets the maximum size [Automatically implemented if extending a JList]
**********************************************************************************************
**/
/*
  public Dimension getMaximumSize(){
    return super.getMaximumSize();
    }
*/

/**
**********************************************************************************************
Gets the minimum size [Automatically implemented if extending a JList]
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
Gets the preferred size [Automatically implemented if extending a JList]
**********************************************************************************************
**/
/*
  public Dimension getPreferredSize(){
    return super.getPreferredSize();
    }
*/

/**
**********************************************************************************************
Is this component focused? [Automatically implemented if extending a JList]
**********************************************************************************************
**/
/*
  public boolean hasFocus(){
    return super.hasFocus();
    }
*/

/**
**********************************************************************************************
Is this component enabled? [Automatically implemented if extending a JList]
**********************************************************************************************
**/
/*
  public boolean isEnabled(){
    return super.isEnabled();
    }
*/

/**
**********************************************************************************************
Is this component opaque? [Automatically implemented if extending a JList]
**********************************************************************************************
**/
/*
  public boolean isOpaque(){
    return super.isOpaque();
    }
*/

/**
**********************************************************************************************
Is this component visible? [Automatically implemented if extending a JList]
**********************************************************************************************
**/
/*
  public boolean isVisible(){
    return super.isVisible();
    }
*/

/**
**********************************************************************************************
Sets the border [Automatically implemented if extending a JList]
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
Sets the maximum size [Automatically implemented if extending a JList]
**********************************************************************************************
**/
/*
  public void setMaximumSize(Dimension d){
    super.setMaximumSize(d);
    }
*/

/**
**********************************************************************************************
Sets the minimum size [Automatically implemented if extending a JList]
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
Sets the preferred size [Automatically implemented if extending a JList]
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