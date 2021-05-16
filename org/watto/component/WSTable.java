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

import org.watto.event.*;
import org.watto.plaf.AquanauticTheme;
import org.watto.plaf.AquanauticTableHeaderUI;
import org.watto.xml.XMLNode;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import javax.swing.JTable;
import javax.swing.event.*;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.table.*;
import java.util.Vector;

/**
**********************************************************************************************
A Template
**********************************************************************************************
**/
public class WSTable extends JTable implements WSComponent {


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

  /** true if a double-click event should be triggered when editing a cell
      (because the event is consumed before it gets to processEvent()) **/
  private boolean buildingEditor = false;

  /** keys that aren't allowed to be processed automatically by this table
      (because removing them from the actionmap/inputmap doesn't work!) **/
  private Hashtable<String,String> disabledKeys = new Hashtable<String,String>(1);


/**
**********************************************************************************************
Constructor for extended classes only
**********************************************************************************************
**/
  WSTable(){
    super();
    }


/**
**********************************************************************************************
Constructor to construct the component from an XMLNode <i>tree</i>
@param node the XMLNode describing this component
**********************************************************************************************
**/
  public WSTable(XMLNode node){
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

    setRowHeight(AquanauticTheme.TEXT_HEIGHT+8 + getRowMargin());
    //WSRepository.add(this);

    getTableHeader().setUI((TableHeaderUI)AquanauticTableHeaderUI.createUI(this));
    }


/**
**********************************************************************************************
Builds an XMLNode that describes this object
@return an XML node with the details of this object
**********************************************************************************************
**/
  public XMLNode buildXML(){
    XMLNode node = WSHelper.buildXML(this);
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
    }




///////////////
//
// Class-Specific Methods
//
///////////////



/**
**********************************************************************************************
Calls <code>WSTableColumnableInterface.onColumnResize(c,columns,e)</code> when one of the
table columns are resized
@param e the event that was triggered
**********************************************************************************************
**/
  public void columnMarginChanged(ChangeEvent e){
    super.columnMarginChanged(e);
    WSEventHandler.processEvent(this,e); // passes events to the caller
    }


/**
**********************************************************************************************
Calls <code>WSSelectableInterface.onSelect(c,e)</code> when a table item is selected
@param e the event that was triggered
**********************************************************************************************
**/
  public void valueChanged(ListSelectionEvent e){
    super.valueChanged(e);
    WSEventHandler.processEvent(this,e); // passes events to the caller
    }


/**
**********************************************************************************************
Dis-allows processing of default key actions in the disabledKeys hashtable.
**********************************************************************************************
**/
  public boolean processKeyBinding(javax.swing.KeyStroke ks, java.awt.event.KeyEvent e, int condition, boolean pressed) {
    if (disabledKeys.get(ks.toString()) != null){
      return true;
      }
    return super.processKeyBinding(ks, e,  condition,  pressed);
    }


/**
**********************************************************************************************
The InputMap/ActionMap is set in the LookAndFeel to have default key actions. Removing the
actions from the ActionMap/InputMap does not work, so instead add the keys to disable to a
hashtable, and stop processing them in processKeyBinding().
**********************************************************************************************
**/
  public void disableAutomaticKeyEvent(String key) {
    disabledKeys.put(key,"!");
    }


/**
**********************************************************************************************
Readies the editor. This has been overwritten so that it will trigger a DoubleClickEvent if
one should occur (as the creation of the editor consumes the event)
**********************************************************************************************
**/
  public java.awt.Component prepareEditor(javax.swing.table.TableCellEditor editor, int row, int column){
    if (buildingEditor){
      processEvent(new MouseEvent(this,MouseEvent.MOUSE_CLICKED,0,0,0,0,2,false,MouseEvent.BUTTON1));
      }
    return super.prepareEditor(editor,row,column);
    }


/**
**********************************************************************************************

**********************************************************************************************
**/
public void setModel(TableModel model){
  super.setModel(model);
  if (model instanceof WSTableModel){
    ((WSTableModel)model).configureTable(this);
    }
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
    //return WSHelper.getToolTipText(this);
    return null;
    }

/**
**********************************************************************************************
Processes the given event
@param event the event that was triggered
**********************************************************************************************
**/
  public void processEvent(AWTEvent event){
    if (event instanceof MouseEvent && event.getID() == MouseEvent.MOUSE_PRESSED){
      buildingEditor = true; // for allowing double-clicks when the editor is being constructed
      }

    super.processEvent(event); // handles any normal listeners
    WSEventHandler.processEvent(this,event); // passes events to the caller

    buildingEditor = false;
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
public void add(String[] s){
  addItem(s);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void addItem(Object[] anObject){
  TableModel model = getModel();
  if (model instanceof DefaultTableModel){
    ((DefaultTableModel)model).addRow(anObject);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void addSelection(int start, int end){
  addRowSelectionInterval(start,end);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void addSelectionInterval(int start, int end){
  addRowSelectionInterval(start,end);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void changeSelection(int index){
  if (isRowSelected(index)){
    removeRowSelectionInterval(index,index);
    }
  else {
    addRowSelectionInterval(index,index);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void changeSelection(int row, int column){
  changeSelection(row,column,true,false);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void changeSelection(int index, boolean toggle, boolean extend){
  int numCols = getColumnCount();
  for (int i=0;i<numCols;i++){
    changeSelection(index,i,toggle,extend);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Component configureEditor(TableCellEditor editor, Object item){
  return prepareEditor(editor,getIndex(item),0);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void ensureIndexIsVisible(int index){
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public TableCellRenderer getCellRenderer(){
  return getDefaultRenderer(Object.class);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public TableCellRenderer getComponent(){
  return getCellRenderer();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Component getComponentAt(int row, int column){
  Object item = getValueAt(row,column);
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
public Object[][] getData(){
  int numRows = getRowCount();
  int numCols = getColumnCount();

  Object[][] data = new Object[numRows][numCols];

  for (int i=0;i<numRows;i++){
    for (int j=0;j<numCols;j++){
      data[i][j] = getValueAt(i,j);
      }
    }

  return data;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public TableCellEditor getEditor(){
  return getDefaultEditor(Object.class);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getFirstVisibleIndex(){
  return rowAtPoint(new Point(0,0));
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getFixedCellHeight(){
  return getRowHeight(0);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getFixedCellWidth(){
  return getColumnModel().getColumn(0).getWidth();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getIndex(Object object){
  int numRows = getRowCount();
  int numCols = getColumnCount();

  for (int i=0;i<numRows;i++){
    for (int j=0;j<numCols;j++){
      if (getValueAt(i,j).equals(object)){
        return i;
        }
      }
    }

  return -1;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getItem(int row, int column){
  return getValueAt(row,column);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getItemAt(int row, int column){
  return getValueAt(row,column);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getItemCount(){
  return getRowCount();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[][] getItems(){
  return getData();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getLastVisibleIndex(){
  return rowAtPoint(new Point(getWidth(),getHeight()));
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getLeadSelectionIndex(){
  int numRows = getRowCount();
  for (int i=0;i<numRows;i++){
    if (isRowSelected(i)){
      return i;
      }
    }
  return -1;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getMaximumRowCount(){
  return getLastVisibleIndex() - getFirstVisibleIndex();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getMaxSelectionIndex(){
  int numRows = getRowCount();
  for (int i=numRows-1;i>=0;i--){
    if (isRowSelected(i)){
      return i;
      }
    }
  return -1;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getMinSelectionIndex(){
  return getLeadSelectionIndex();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getObject(int row, int column){
  return getValueAt(row,column);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getObjectAt(int row, int column){
  return getValueAt(row,column);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[][] getObjects(){
  return getData();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public TableCellRenderer getRenderer(){
  return getDefaultRenderer(Object.class);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Component getSelectedComponent(){
  Object item = getValueAt(getSelectedRow(),getSelectedColumn());
  if (item instanceof Component){
    return (Component)item;
    }
  return null;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getSelectedIndex(){
  return getSelectedRow();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int[] getSelectedIndices(){
  return getSelectedRows();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getSelectedItem(){
  return getValueAt(getSelectedRow(),getSelectedColumn());
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getSelectedItemCount(){
  return getSelectedRowCount();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[][] getSelectedItems(){
  int[] selected = getSelectedRows();
  int numCols = getColumnCount();
  Object[][] items = new Object[selected.length][numCols];

  for (int i=0;i<selected.length;i++){
    for (int j=0;j<numCols;j++){
      items[i][j] = getValueAt(i,j);
      }
    }

  return items;
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getSelectedObject(){
  return getValueAt(getSelectedRow(),getSelectedColumn());
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getSelectedObjectCount(){
  return getSelectedRowCount();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[][] getSelectedObjects(){
  return getSelectedItems();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getSelectedValue(){
  return getValueAt(getSelectedRow(),getSelectedColumn());
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getSelectedValueCount(){
  return getSelectedRowCount();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[][] getSelectedValues(){
  return getSelectedItems();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object getValue(int row, int column){
  return getItem(row,column);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Object[][] getValues(){
  return getData();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int getVisibleRowCount(){
  return getMaximumRowCount();
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int indexAtLocation(int x, int y){
  return rowAtPoint(new Point(x,y));
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int indexAtPoint(Point p){
  return rowAtPoint(p);
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
public void insert(Component[] c, int index){
  TableModel model = getModel();
  if (model instanceof DefaultTableModel){
    ((DefaultTableModel)model).insertRow(index,c);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void insert(String[] s, int index){
  TableModel model = getModel();
  if (model instanceof DefaultTableModel){
    ((DefaultTableModel)model).insertRow(index,s);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void insertItemAt(Object[] anObject, int index){
  TableModel model = getModel();
  if (model instanceof DefaultTableModel){
    ((DefaultTableModel)model).insertRow(index,anObject);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void insertObjectAt(Object[] anObject, int index){
  insertItemAt(anObject,index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void insertValueAt(Object[] anObject, int index){
  insertItemAt(anObject,index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public int locationToIndex(Point location){
  return rowAtPoint(location);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public Point pointAtIndex(int index){
  return new Point(0,0);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeAllItems(){
  setModel(new DefaultTableModel());
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeItem(int index){
  TableModel model = getModel();
  if (model instanceof DefaultTableModel){
    ((DefaultTableModel)model).removeRow(index);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeItem(Object object){
  TableModel model = getModel();
  if (model instanceof DefaultTableModel){
    ((DefaultTableModel)model).removeRow(getIndex(object));
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

  TableModel model = getModel();
  if (model instanceof DefaultTableModel){
    DefaultTableModel m = (DefaultTableModel)model;

    for (int i=index.length-1;i>=0;i--){
      m.removeRow(index[i]);
      }
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeItems(Object[] objects){
  TableModel model = getModel();
  if (model instanceof DefaultTableModel){
    DefaultTableModel m = (DefaultTableModel)model;

    int numRows = m.getRowCount();
    int numCols = m.getColumnCount();
    int numObjects = objects.length;

    int[] removeIndexes = new int[numObjects];
    int numRemove = 0;

    for (int i=0;i<numRows;i++){
      for (int c=0;c<numCols;c++){
        Object item = m.getValueAt(i,c);
        for (int j=0;j<numObjects;j++){
          if (objects[j].equals(item)){
            removeIndexes[numRemove] = i;
            numRemove++;
            break;
            }
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
public void removeSelection(int start, int end){
  removeRowSelectionInterval(start,end);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void removeSelectionInterval(int start, int end){
  removeRowSelectionInterval(start,end);
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
public void setCellRenderer(TableCellRenderer renderer){
  setDefaultRenderer(Object.class,renderer);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setComponentAt(int row, int column, Component c){
  setValueAt(c,row,column);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setData(Object[][] data){
  setModel(new DefaultTableModel(data,new String[data.length]));
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setData(Object[][] data, String[] headings){
  setModel(new DefaultTableModel(data,headings));
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setData(Vector data){
  setModel(new DefaultTableModel(data,new Vector(data.size())));
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setEditor(TableCellEditor editor){
  setDefaultEditor(Object.class,editor);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setFixedCellHeight(int height){
  setRowHeight(height);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setFixedCellWidth(int width){
  TableColumnModel model = getColumnModel();
  for (int i=0;i<model.getColumnCount();i++){
    model.getColumn(i).setWidth(width);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setListData(Object[][] data){
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
public void setMaximumRowCount(int count){
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelected(Component c){
  setSelected((Object)c);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelected(Object anObject){
  int index = getIndex(anObject);
  setRowSelectionInterval(index,index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedComponent(Component c){
  setSelected((Object)c);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedIndex(int index){
  setRowSelectionInterval(index,index);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedIndices(int[] indices){
  clearSelection();
  for (int i=0;i<indices.length;i++){
    addRowSelectionInterval(indices[i],indices[i]);
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
public void setSelectedItem(Object anObject){
  setSelected(anObject);
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
  TableModel model = getModel();
  if (model instanceof DefaultTableModel){
    DefaultTableModel m = (DefaultTableModel)model;

    int numRows = m.getRowCount();
    int numCols = m.getColumnCount();
    int numObjects = anObject.length;

    int[] selIndexes = new int[numObjects];
    int numSel = 0;

    for (int i=0;i<numRows;i++){
      for (int c=0;c<numCols;c++){
        Object item = m.getValueAt(i,c);
        for (int j=0;j<numObjects;j++){
          if (anObject[j].equals(item)){
            selIndexes[numSel] = i;
            numSel++;
            break;
            }
          }
        }
      }

    if (numSel < numObjects){
      int[] temp = selIndexes;
      selIndexes = new int[numSel];
      System.arraycopy(temp,0,selIndexes,0,numSel);
      }

    setSelectedItems(selIndexes);
    }
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedObject(int item){
  setSelectedItem(item);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedObject(Object anObject){
  setSelectedItem(anObject);
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
public void setSelectedObjects(Object[] anObject){
  setSelectedItems(anObject);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedValue(int item){
  setSelectedItem(item);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedValue(Object anObject){
  setSelectedItem(anObject);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectedValue(Object anObject, Boolean scrollTo){
  setSelectedItem(anObject);
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
public void setSelectedValues(Object[] anObject){
  setSelectedItems(anObject);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setSelectionInterval(int start, int end){
  setRowSelectionInterval(start,end);
  }

/**
**********************************************************************************************

**********************************************************************************************
**/
public void setVisibleRowCount(int count){
  }



///////////////
//
// JTable-implemented
//
///////////////

/**
**********************************************************************************************
Gets the border [Automatically implemented if extending a JTable]
**********************************************************************************************
**/
/*
  public Border getBorder(){
    return super.getBorder();
    }
*/

/**
**********************************************************************************************
Gets the maximum size [Automatically implemented if extending a JTable]
**********************************************************************************************
**/
/*
  public Dimension getMaximumSize(){
    return super.getMaximumSize();
    }
*/

/**
**********************************************************************************************
Gets the minimum size [Automatically implemented if extending a JTable]
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
Gets the preferred size [Automatically implemented if extending a JTable]
**********************************************************************************************
**/
/*
  public Dimension getPreferredSize(){
    return super.getPreferredSize();
    }
*/

/**
**********************************************************************************************
Is this component focused? [Automatically implemented if extending a JTable]
**********************************************************************************************
**/
/*
  public boolean hasFocus(){
    return super.hasFocus();
    }
*/

/**
**********************************************************************************************
Is this component enabled? [Automatically implemented if extending a JTable]
**********************************************************************************************
**/
/*
  public boolean isEnabled(){
    return super.isEnabled();
    }
*/

/**
**********************************************************************************************
Is this component opaque? [Automatically implemented if extending a JTable]
**********************************************************************************************
**/
/*
  public boolean isOpaque(){
    return super.isOpaque();
    }
*/

/**
**********************************************************************************************
Is this component visible? [Automatically implemented if extending a JTable]
**********************************************************************************************
**/
/*
  public boolean isVisible(){
    return super.isVisible();
    }
*/

/**
**********************************************************************************************
Sets the border [Automatically implemented if extending a JTable]
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
Sets the maximum size [Automatically implemented if extending a JTable]
**********************************************************************************************
**/
/*
  public void setMaximumSize(Dimension d){
    super.setMaximumSize(d);
    }
*/

/**
**********************************************************************************************
Sets the minimum size [Automatically implemented if extending a JTable]
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
Sets the preferred size [Automatically implemented if extending a JTable]
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