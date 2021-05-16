////////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                            //
//                                       XML UTILITIES                                        //
//                Java Classes for Reading, Writing, and Manipulating XML Files               //
//                                    http://www.watto.org                                    //
//                                                                                            //
//                           Copyright (C) 2003-2008  WATTO Studios                           //
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

package org.watto.xml;

import javax.swing.tree.*;
import java.util.Enumeration;

/**
**********************************************************************************************
A tree node used to store the contents of an XML tag, and to navigate the tree. Implements the
standard interfaces TreeNode and MutableTreeNode so that the tree can be used by all existing
classes dealing with these trees (such as JTree).
**********************************************************************************************
**/
public class XMLNode implements TreeNode, MutableTreeNode, Comparable {

  /** the name of the tag **/
  public String tag = "";
  /** the content of the tag **/
  public String content = "";
  /** the sttributes of the tag **/
  public String[][] attributes = new String[0][2];

  /** the children under this tag **/
  protected XMLNode[] children = new XMLNode[0];
  /** the parent of this tag **/
  protected XMLNode parent = null;

  // USED BY METHODS getChild(name) AND removeChild(name) TO LOCATE THE NEXT MATCHING TAGS
  /** the current childs position in the array **/
  protected int childPos = 0;
  /** the name of te current child **/
  protected String childName = "";


/// CONSTRUCTORS ///


/**
**********************************************************************************************
Constructor
**********************************************************************************************
**/
  public XMLNode(){
    }


/**
**********************************************************************************************
Constructor specifying the node text
@param name the tag name
**********************************************************************************************
**/
  public XMLNode(String tag){
    this.tag = tag;
    }


/**
**********************************************************************************************
Constructor specifying the node text and content
@param name the tag name
@param content the content
**********************************************************************************************
**/
  public XMLNode(String tag, String content){
    this.tag = tag;
    this.content = content;
    }


/**
**********************************************************************************************
Constructor specifying the node text and attributes
@param name the node text
@param attributes the attributes
**********************************************************************************************
**/
  public XMLNode(String tag, String[][] attributes){
    this.tag = tag;
    this.attributes = attributes;
    }


/**
**********************************************************************************************
Constructor specifying the node text, attributes, and content
@param name the node text
@param attributes the attributes
@param content the content
**********************************************************************************************
**/
  public XMLNode(String tag, String[][] attributes, String content){
    this.tag = tag;
    this.content = content;
    this.attributes = attributes;
    }


/// XML NODE METHODS ///


/**
**********************************************************************************************
Gets the string representation of this tag
@return the tag name
**********************************************************************************************
**/
  public String toString(){
    return getTag();
    }


/**
**********************************************************************************************
Gets the attributes of this tag
@return the attributes
**********************************************************************************************
**/
  public String[][] getAttributes(){
    return attributes;
    }


/**
**********************************************************************************************
Gets the value of the attribute with name <i>key</i>
@param key the key of te attribute to match.
@return the value of the matching attribute
**********************************************************************************************
**/
  public String getAttribute(String key){
    for (int i=0;i<attributes.length;i++){
      if (attributes[i][0].equals(key)){
        return attributes[i][1];
        }
      }
    return null;
    }


/**
**********************************************************************************************
Gets the value of the attribute at <i>number</i>
@param number the attribute number to get
@return the value of the attribute
**********************************************************************************************
**/
  public String[] getAttribute(int number){
    if (number < 0 || number >= attributes.length){
      return null;
      }
    return attributes[number];
    }


/**
**********************************************************************************************
Gets the number of attributes on the tag
@return the number of attributes
**********************************************************************************************
**/
  public int getAttributeCount(){
    return attributes.length;
    }


/**
**********************************************************************************************
Does this tag have any attributes?
@return true if the tag has any attributes, false if it has no attributes
**********************************************************************************************
**/
  public boolean hasAttributes(){
    if (attributes.length == 0){
      return false;
      }
    else {
      return true;
      }
    }


/**
**********************************************************************************************
Adds an attribute to the tag
@param key the key of the attribute
@param value the value of the attribute
**********************************************************************************************
**/
  public void addAttribute(String key, String value){
    int oldSize = attributes.length;

    String[][] temp = attributes;
    attributes = new String[oldSize+1][2];
    System.arraycopy(temp,0,attributes,0,oldSize);

    attributes[oldSize] = new String[]{key,value};
    }


/**
**********************************************************************************************
Sets the value of an attribute on the tag. If the attribute does not exist, it is added.
@param key the key of the attribute
@param value the value of the attribute
**********************************************************************************************
**/
  public void setAttribute(String key, String value){
    for (int i=0;i<attributes.length;i++){
      if (attributes[i][0].equals(key)){
        attributes[i][1] = value;
        return;
        }
      }

    addAttribute(key,value);
    }


/**
**********************************************************************************************
Gets the name of the tag
@return the name of the tag
**********************************************************************************************
**/
  public String getTag(){
    return tag;
    }


/**
**********************************************************************************************
Sets the name of the tag
@param tag the name of the tag
**********************************************************************************************
**/
  public void setTag(String tag){
    this.tag = tag;
    }


/**
**********************************************************************************************
Gets the content of the tag
@return the content of the tag
**********************************************************************************************
**/
  public String getContent(){
    return content;
    }


/**
**********************************************************************************************
Sets the content of this tag
@param content the content of the tag
**********************************************************************************************
**/
  public void setContent(String content){
    this.content = content;
    }


/**
**********************************************************************************************
Appends <i>content</i> to the current content of this tag
@param content the content to append
**********************************************************************************************
**/
  public void addContent(String content){
    this.content += content;
    }


/**
**********************************************************************************************
Does this tag have any content?
@return true if the tag has content, false if it has no content
**********************************************************************************************
**/
  public boolean hasContent(){
    if (content.length() == 0){
      return false;
      }
    else {
      return true;
      }
    }


/// USEFUL DEFAULT MUTABLE TREE NODE METHODS ///


/**
**********************************************************************************************
Adds a child to this tag.
@param child the child to add.
**********************************************************************************************
**/
  public void addChild(XMLNode child){
    if (child == null){
      return;
      }

    int oldSize = children.length;

    XMLNode[] temp = children;
    children = new XMLNode[oldSize+1];
    System.arraycopy(temp,0,children,0,oldSize);

    children[oldSize] = child;

    child.setParent(this);
    }


/**
**********************************************************************************************
Adds a <i>child</i> to this tag at the specified <i>index</i> position in the <i>children</i>
array.
@param child the child to add.
@param index the position to insert the child
**********************************************************************************************
**/
  public void addChild(XMLNode child, int index) throws XMLException{
    if (index > children.length){
      throw new XMLException ("Index " + index + " is greater than the child array length " + children.length);
      }

    if (index == children.length){
      addChild(child);
      }

    int oldSize = children.length;

    XMLNode[] temp = children;
    children = new XMLNode[oldSize+1];

    System.arraycopy(temp,0,children,0,index);
    children[index] = child;
    System.arraycopy(temp,0,children,index+1,oldSize-index);

    child.setParent(this);

    if (childPos >= index){
      childPos++;
      }
    }


/**
**********************************************************************************************
Adds the <i>newChildren</i> to this tag.
@param newChildren the children to add.
**********************************************************************************************
**/
  public void addChildren(XMLNode[] newChildren){
    int oldSize = children.length;
    int newSize = oldSize + newChildren.length;

    XMLNode[] temp = children;
    children = new XMLNode[newSize];
    System.arraycopy(temp,0,children,0,oldSize);

    System.arraycopy(newChildren,0,children,oldSize,newChildren.length);

    for (int i=0;i<newChildren.length;i++){
      newChildren[i].setParent(this);
      }

    childPos = 0;
    }


/**
**********************************************************************************************
Gets the number of children of this tag.
@return the number of children
**********************************************************************************************
**/
  public int getChildCount(){
    return children.length;
    }


/**
**********************************************************************************************
Does this tag have any children?
@return true if the tag has at least 1 child, false if it has no children
**********************************************************************************************
**/
  public boolean hasChildren(){
    if (children.length == 0){
      return false;
      }
    else {
      return true;
      }
    }


/**
**********************************************************************************************
Gets the child at the specified position <i>num</i> in the <i>children</i> array.
@return the child at position <i>num</i>.
**********************************************************************************************
**/
  public XMLNode getChild(int num) throws XMLException {
    checkIsBranch();
    if (num >= children.length){
      throw new XMLException("There are only " + children.length + " children on this node");
      }

    childPos = num;
    childName = children[num].getTag();

    return children[num];
    }


/**
**********************************************************************************************
If <i>name</i> is the same as <i>childName</i>, it will return the next child with this
<i>name</i>. Otherwise, it will start searching for the matching child from the start of the
<i>children</i> array. If no matching child is found, it will return <i>null</i>.
@return the next child with the tag name <i>name</i>
**********************************************************************************************
**/
  public XMLNode getChild(String name) throws XMLException {
    checkIsBranch();

    if (childName.equals(name)){
      // want to find the next child with this name
      childPos++;

      if (childPos >= children.length){
        childPos = 0;
        }

      }
    else {
      childPos = 0;
      }

    for (int i=childPos;i<children.length;i++){
      if (children[i].getTag().equals(name)){
        childPos = i;
        childName = children[i].getTag();
        return children[i];
        }
      }

    for (int i=0;i<childPos;i++){
      if (children[i].getTag().equals(name)){
        childPos = i;
        childName = children[i].getTag();
        return children[i];
        }
      }

    // there were no children with this name

    childPos = 0;
    childName = "";

    return null;

    }


/**
**********************************************************************************************
Gets all the children of this tag
@return all <i>children</i>
**********************************************************************************************
**/
  public XMLNode[] getChildren() {
    try {
      checkIsBranch();
      }
    catch (XMLException e){
      return new XMLNode[0];
      }
    return children;
    }


/**
**********************************************************************************************
Gets all the children of this tag that have the tag name <i>name</i>.
@return an array of all children with the tag name <i>name</i>.
**********************************************************************************************
**/
  public XMLNode[] getChildren(String name) {
    try {
      checkIsBranch();
      }
    catch (XMLException e){
      return new XMLNode[0];
      }

    XMLNode[] matchingChildren = new XMLNode[children.length];
    int matchingChildPos = 0;

    for (int i=0;i<children.length;i++){
      if (children[i].getTag().equals(name)){
        matchingChildren[matchingChildPos] = children[i];
        matchingChildPos++;
        }
      }

    if (matchingChildPos == matchingChildren.length){
      return matchingChildren;
      }

    XMLNode[] temp = matchingChildren;
    matchingChildren = new XMLNode[matchingChildPos];
    System.arraycopy(temp,0,matchingChildren,0,matchingChildPos);

    return matchingChildren;
    }


/**
**********************************************************************************************
Removes the <i>num</i> child from this tag.
@param num the number of the child to remove from the <i>children</i> array.
@return the child that was removed
**********************************************************************************************
**/
  public XMLNode removeChild(int num){
    try {
      checkIsBranch();
      }
    catch (XMLException e){
      return null;
      }

    if (num < 0 || num >= children.length){
      return null;
      }

    XMLNode removedChild = children[num];

    int oldSize = children.length;

    XMLNode[] temp = children;
    children = new XMLNode[oldSize-1];
    System.arraycopy(temp,0,children,0,num);

    System.arraycopy(temp,num+1,children,num,oldSize-num-1);

    // adjust the current child details.
    if (childPos >= num){
      childPos--;
      if (childPos < 0){
        childPos = 0;
        }

      childName = children[childPos].getTag();
      }

    removedChild.setParent(null);

    return removedChild;

    }


/**
**********************************************************************************************
Removes the <i>child</i> from this tag.
<br><br>
Note that this child must be in the <i>children</i>
array, as it matches on the reference, <b>not</b> on the tag name. To remove a tag by its name,
use the method <i>removeChild(String name)</i>.
@param child the child to remove.
@return the child that was removed
**********************************************************************************************
**/
  public XMLNode removeChild(XMLNode child){
    for (int i=0;i<children.length;i++){
      if (children[i] == child){
        return removeChild(i);
        }
      }

    return null;
    }


/**
**********************************************************************************************
Removes the next child with this <i>name</i>
@param name the name of the next child to remove.
@return the child that was removed
**********************************************************************************************
**/
  public XMLNode removeChild(String name){
    if (childName.equals(name)){
      // want to find the next child with this name
      childPos++;

      if (childPos >= children.length){
        childPos = 0;
        }

      }
    else {
      childPos = 0;
      }

    for (int i=childPos;i<children.length;i++){
      if (children[i].getTag().equals(name)){
        return removeChild(i);
        }
      }

    for (int i=0;i<childPos;i++){
      if (children[i].getTag().equals(name)){
        return removeChild(i);
        }
      }

    return null;
    }


/**
**********************************************************************************************
Removes all the children with this <i>name</i>
@param name the name of the children to remove.
@return the number of removed children.
**********************************************************************************************
**/
  public int removeChildren(String name){
    try {
      checkIsBranch();
      }
    catch (XMLException e){
      return 0;
      }

    XMLNode[] oldChildren = children;
    children = new XMLNode[oldChildren.length];
    int insertPos = 0;

    for (int i=0;i<oldChildren.length;i++){
      if (oldChildren[i].getTag().equals(name)){
        // this child should be removed
        }
      else {
        children[insertPos] = oldChildren[i];
        insertPos++;
        }
      }

    int numRemoved = oldChildren.length - insertPos;

    oldChildren = children;
    children = new XMLNode[insertPos];
    System.arraycopy(oldChildren,0,children,0,insertPos);

    childPos = 0;

    return numRemoved;

    }


/**
**********************************************************************************************
Removes all the matching children from the tag.
@param remChildren the children to remove.
@return the number of removed children.
**********************************************************************************************
**/
  public int removeChildren(XMLNode[] remChildren){
    try {
      checkIsBranch();
      }
    catch (XMLException e){
      return 0;
      }

    XMLNode[] oldChildren = children;
    children = new XMLNode[oldChildren.length];
    int insertPos = 0;

    int numRemChildren = remChildren.length;

    for (int i=0;i<oldChildren.length;i++){

      String curName = oldChildren[i].getTag();
      boolean removed = false;

      for (int c=0;c<numRemChildren;c++){
        if (remChildren[c].getTag().equals(curName)){
          // this child should be removed
          removed = true;

          // reduce the search size of the remChildren array so it gets quicker over time
          numRemChildren--;
          remChildren[c] = remChildren[numRemChildren];

          break;
          }
        }

      if (!removed){
        children[insertPos] = oldChildren[i];
        insertPos++;
        }

      }

    int numRemoved = oldChildren.length - insertPos;

    oldChildren = children;
    children = new XMLNode[insertPos];
    System.arraycopy(oldChildren,0,children,0,insertPos);

    childPos = 0;

    return numRemoved;

    }


/**
**********************************************************************************************
Removes all children from the tag.
@return the number of removed children.
**********************************************************************************************
**/
  public int removeAllChildren(){
    int numRemoved = children.length;
    children = new XMLNode[0];

    childPos = 0;

    return numRemoved;
    }


/**
**********************************************************************************************
Sets the current child to children[<i>num</i>]. The current child is used when getting and
removing tags by name.
@param num the number of the child to make current.
**********************************************************************************************
**/
  public void setCurrentChild(int num) throws XMLException {
    checkIsBranch();

    if (num >= children.length){
      throw new XMLException("There are only " + children.length + " children on this node");
      }

    childPos = num;
    childName = children[num].getTag();
    }


/**
**********************************************************************************************
Sets the current child. The current child is used when getting and removing tags by name. The
<i>child</i> must be in the <i>children</i> array, as it matches by reference <b>not</b> by name
@param child the child to make current
**********************************************************************************************
**/
  public void setCurrentChild(XMLNode child) throws XMLException {
    checkIsBranch();

    int num = getChildPosition(child);

    if (num == -1){
      throw new XMLException("The tag " + child.getTag() + " is not a child of this node");
      }

    childPos = num;
    childName = children[num].getTag();
    }


/**
**********************************************************************************************
Gets the current child.
@return the current child
**********************************************************************************************
**/
  public XMLNode getCurrentChild() throws XMLException {
    checkIsBranch();
    if (childPos >= children.length || childPos < 0){
      throw new XMLException("There is no current child");
      }
    return children[childPos];
    }


/**
**********************************************************************************************
Gets the position of the <i>child</i> in the <i>children</i> array.
@return the position of the child, or -1 if the child does not exist on this tag
**********************************************************************************************
**/
  public int getChildPosition(XMLNode child){
    for (int i=0;i<children.length;i++){
      if (children[i] == child){
        return i;
        }
      }
    return -1;
    }


/**
**********************************************************************************************
Determines whether this node is a leaf (ie. has no children)
@return true if there are no children, false if there are children
**********************************************************************************************
**/
  public boolean isLeaf(){
    if (children.length == 0){
      return true;
      }
    else {
      return false;
      }
    }


/**
**********************************************************************************************
Determines whether this node is a branch (ie. has at least 1 child)
@return true if there is at least 1 child, false if there are no children
**********************************************************************************************
**/
  public boolean isBranch(){
    if (children.length == 0){
      return false;
      }
    else {
      return true;
      }
    }


/**
**********************************************************************************************
Determines whether this node is the root (ie has no parent)
@return true if it is the root, false if it isn't the root
**********************************************************************************************
**/
  public boolean isRoot(){
    if (parent == null){
      return true;
      }
    else {
      return false;
      }
    }


/**
**********************************************************************************************
Determines whether this node is empty (ie has no parent and no children)
@return true if the node is empty, false if is isn't empty
**********************************************************************************************
**/
  public boolean isEmpty(){
    if (parent == null && children.length == 0){
      return true;
      }
    else {
      return false;
      }
    }


/**
**********************************************************************************************
Sets the <i>parent</i> of this node. This is done automatically when adding or removing children.
@param parent the parent node
**********************************************************************************************
**/
  public void setParent(XMLNode parent){
    this.parent = parent;
    }


/**
**********************************************************************************************
Gets the <i>parent</i> of this node.
@return the <i>parent</i>, or null if this node is the root.
**********************************************************************************************
**/
  public TreeNode getParent(){
    return parent;
    }


/**
**********************************************************************************************
Gets the <i>parent</i> of this node.
@return the <i>parent</i>, or null if this node is the root.
**********************************************************************************************
**/
  public XMLNode getParentNode(){
    return parent;
    }


/// OTHER OPTIONAL DEFAULT MUTABLE TREE NODE METHODS ///


/**
**********************************************************************************************
Gets the maximum depth of the tree rooted at this node.
@return the maximum depth under this node
**********************************************************************************************
**/
  public int getDepth(){
    int depth = 0;

    for (int i=0;i<children.length;i++){
      int childDepth = children[i].getDepth() + 1;
      if (childDepth > depth){
        depth = childDepth;
        }
      }

    return depth;
    }


/**
**********************************************************************************************
Gets the depth of this node from the root.
@return the depth of this node from the root, or 0 if this node is the root
**********************************************************************************************
**/
  public int getLevel(){
    int level = 0;

    XMLNode parentNode = parent;
    while (parentNode != null){
      parentNode = (XMLNode)parentNode.getParent();
      level++;
      }

    return level;
    }


/**
**********************************************************************************************
Gets the child following the specified <i>child</i>.
@return the following child, or null if this is the last child in the <i>children</i> array.
**********************************************************************************************
**/
  public XMLNode getChildAfter(XMLNode child) throws XMLException {
    checkNotNull(child);
    checkIsBranch();

    for (int i=0;i<children.length;i++){
      if (children[i] == child){
        int nextChildPos = i+1;
        if (nextChildPos >= children.length){
          return null;
          }
        return children[nextChildPos];
        }
      }

    throw new XMLException("The tag " + child.getTag() + " is not a child of this node");
    }


/**
**********************************************************************************************
Gets the child preceeding the specified <i>child</i>.
@return the preceeding child, or null if this is the first child in the <i>children</i> array.
**********************************************************************************************
**/
  public XMLNode getChildBefore(XMLNode child) throws XMLException {
    checkNotNull(child);
    checkIsBranch();

    for (int i=0;i<children.length;i++){
      if (children[i] == child){
        int prevChildPos = i-1;
        if (prevChildPos < 0){
          return null;
          }
        return children[prevChildPos];
        }
      }

    throw new XMLException("The tag " + child.getTag() + " is not a child of this node");
    }


/**
**********************************************************************************************
Gets the first child of this node.
@return the first child in the <i>children</i> array.
**********************************************************************************************
**/
  public XMLNode getFirstChild() throws XMLException {
    checkIsBranch();
    return children[0];
    }


/**
**********************************************************************************************
Gets the last child of this node.
@return the last child in the <i>children</i> array.
**********************************************************************************************
**/
  public XMLNode getLastChild() throws XMLException {
    checkIsBranch();
    return children[children.length-1];
    }


/**
**********************************************************************************************
Gets the path from this node to the root. This node is the last node of the path.
@return the path to the root
**********************************************************************************************
**/
  public XMLNode[] getPath(){
    int levels = getLevel() + 1;

    XMLNode[] path = new XMLNode[levels];
    path[levels-1] = this;

    XMLNode parentNode = parent;
    for (int i=levels-2;i>=0;i--){
      path[i] = parentNode;
      parentNode = (XMLNode)parentNode.getParent();
      }

    return path;
    }


/**
**********************************************************************************************
Gets the root of the tree that this node belongs to.
@return the root node.
**********************************************************************************************
**/
  public XMLNode getRoot(){

    if (isRoot()){
      return this;
      }

    XMLNode parentNode = parent;
    while (parentNode != null){
      XMLNode newParentNode = (XMLNode)parentNode.getParent();
      if (newParentNode == null){
        return parentNode;
        }
      else {
        parentNode = newParentNode;
        }
      }

    return null;
    }


/**
**********************************************************************************************
Is the <i>node</i> an ancestor of this node?
@return true if the <i>node</i> is an ancestor of this node, false if it isn't an ancestor.
**********************************************************************************************
**/
  public boolean isNodeAncestor(XMLNode node){
    try {
      checkNotNull(node);
      }
    catch (XMLException e){
      return false;
      }

    if (node == this){
      return true;
      }

    XMLNode parentNode = parent;
    while (parentNode != null){
      if (node == parentNode){
        return true;
        }
      parentNode = (XMLNode)parentNode.getParent();
      }

    return false;
    }


/**
**********************************************************************************************
Is the <i>node</i> a child of this node?
@return true if the <i>node</i> is an child of this node, false if it isn't a child.
**********************************************************************************************
**/
  public boolean isNodeChild(XMLNode node){
    try {
      checkNotNull(node);
      }
    catch (XMLException e){
      return false;
      }

    if (node.getParent() == this){
      return true;
      }
    else {
      return false;
      }
    }


/**
**********************************************************************************************
Is the <i>node</i> a descendant of this node?
@return true if the <i>node</i> is a descendant of this node, false if it isn't a descendant.
**********************************************************************************************
**/
  public boolean isNodeDescendant(XMLNode node){
    try {
      checkNotNull(node);
      }
    catch (XMLException e){
      return false;
      }

    if (node == this){
      return true;
      }

    XMLNode parentNode = (XMLNode)node.getParent();
    while (parentNode != null){
      if (parentNode == this){
        return true;
        }
      parentNode = (XMLNode)parentNode.getParent();
      }

    return false;
    }


/**
**********************************************************************************************
Is this node an ancestor of <i>node</i>?
@return true if this node is an ancestor of <i>node</i>, false if it isn't an ancestor.
**********************************************************************************************
**/
  public boolean isAncestorOf(XMLNode node){
    return node.isNodeDescendant(this);
    }


/**
**********************************************************************************************
Is this node a child of <i>node</i>?
@return true if this node is a child of <i>node</i>, false if it isn't a child.
**********************************************************************************************
**/
  public boolean isChildOf(XMLNode node){
    try {
      checkNotNull(node);
      }
    catch (XMLException e){
      return false;
      }

    if (node == parent){
      return true;
      }
    else {
      return false;
      }
    }


/**
**********************************************************************************************
Is this node a descendant of <i>node</i>?
@return true if this node is a descentant of <i>node</i>, false if it isn't a descendant.
**********************************************************************************************
**/
  public boolean isDescendantOf(XMLNode node){
    return node.isNodeAncestor(this);
    }


/**
**********************************************************************************************
Is the <i>node</i> related to this node (ie. are both nodes in the same tree) ?
@return true if the <i>node</i> is related to this node, false if it isn't related.
**********************************************************************************************
**/
  public boolean isNodeRelated(XMLNode node){
    try {
      checkNotNull(node);
      }
    catch (XMLException e){
      return false;
      }

    if (getRoot() == node.getRoot()){
      return true;
      }
    else {
      return false;
      }
    }


/**
**********************************************************************************************
Does this node have the same parent as <i>node</i>?
@return true if they have the same parent, false if they have different parents.
**********************************************************************************************
**/
  public boolean isNodeSibling(XMLNode node){
    try {
      checkNotNull(node);
      checkNotNull(parent);
      }
    catch (XMLException e){
      return false;
      }

    if (parent == node.getParent()){
      return true;
      }
    else {
      return false;
      }
    }


/// MUTABLE TREE NODE METHODS ///


/**
**********************************************************************************************
Inserts a <i>child</i> into the position <i>index</i> of the <i>children</i> array
@param child the child to insert
@param index the position to insert the child
**********************************************************************************************
**/
  public void insert(MutableTreeNode child, int index){
    try {
      checkXMLNode(child);
      addChild((XMLNode)child,index);
      }
    catch (XMLException e){
      return;
      }
    }


/**
**********************************************************************************************
Removes the child at the specified <i>index</i> of the <i>children</i> array
@param index the position of the child to remove
**********************************************************************************************
**/
  public void remove(int index){
    removeChild(index);
    }


/**
**********************************************************************************************
Removes the child <i>node</i> from the <i>children</i> array
@param node the child to remove
**********************************************************************************************
**/
  public void remove(MutableTreeNode node){
    try {
      checkXMLNode(node);
      }
    catch (XMLException e){
      return;
      }
    removeChild((XMLNode)node);
    }


/**
**********************************************************************************************
Removes this node from its parent
**********************************************************************************************
**/
  public void removeFromParent(){
    parent.removeChild(this);
    parent = null;
    }


/**
**********************************************************************************************
Sets the parent of this node
@param newParent the new parent of this node
**********************************************************************************************
**/
  public void setParent(MutableTreeNode newParent){
    try {
      checkXMLNode(newParent);
      }
    catch (XMLException e){
      return;
      }
    setParent((XMLNode)newParent);
    }


/**
**********************************************************************************************
Sets the name of the tag. If the <i>object</i> is not a <i>String</i>, it will use the <i>toString()</i>
output for the tag name.
@param a new tag name object
**********************************************************************************************
**/
  public void setUserObject(Object object){
    tag = object.toString();
    }


/// TREE NODE METHODS ///


/**
**********************************************************************************************
Gets an enumeration of the children of this node
@return an <i>XMLChildEnumeration</i> of the children
**********************************************************************************************
**/
  public Enumeration children(){
    return new XMLChildEnumeration(this);
    }


/**
**********************************************************************************************
Does this node allow children?
@return true
**********************************************************************************************
**/
  public boolean getAllowsChildren(){
    return true;
    }


/**
**********************************************************************************************
Gets the child at the position <i>childIndex</i> of the <i>children</i> array
@return the child
**********************************************************************************************
**/
  public TreeNode getChildAt(int childIndex){
    try {
      return getChild(childIndex);
      }
    catch (XMLException e){
      return null;
      }
    }


/**
**********************************************************************************************
Gets the index of the child <i>node</i> in the <i>children</i> array
@return the index of the child
**********************************************************************************************
**/
  public int getIndex(TreeNode node){
    try {
      checkXMLNode(node);
      }
    catch (XMLException e){
      return -1;
      }
    return getChildPosition((XMLNode)node);
    }


/// SORTING METHODS ///


/**
**********************************************************************************************
Compares this XMLNode to another XMLNode (for sorting)
**********************************************************************************************
**/
  public int compareTo(Object otherObject) {
    if (otherObject instanceof XMLNode){
      int tagCompare = tag.compareTo(((XMLNode)otherObject).getTag());
      if (tagCompare == 0){
        return content.compareTo(((XMLNode)otherObject).getContent());
        }
      return tagCompare;
      }
    else {
      return content.compareTo(otherObject.toString());
      }
    }


/**
**********************************************************************************************
Sorts the children on this node.
@param recurrance true will sort all the way through the tree, false will only sort the
children on this one node
**********************************************************************************************
**/
  public void sort(boolean recurrance) {
    java.util.Arrays.sort(children);
    if (recurrance){
      for (int i=0;i<children.length;i++){
        children[i].sort(recurrance);
        }
      }
    }



/// PROTECTED METHODS ///


/**
**********************************************************************************************
Throws an exception if this node is a leaf (ie has no children)
**********************************************************************************************
**/
  protected void checkIsBranch() throws XMLException{
    if (isLeaf()){
      throw new XMLException("This child has no children");
      }
    }


/**
**********************************************************************************************
Throws an exception if the object <i>obj</i> (usually an XMLNode) is null
**********************************************************************************************
**/
  protected void checkNotNull(Object obj) throws XMLException{
    if (obj == null){
      throw new XMLException("Object cannot be null");
      }
    }


/**
**********************************************************************************************
Throws an exception if the object <i>obj</i> is not an XMLNode. Used for MutableTreeNode methods.
**********************************************************************************************
**/
  protected void checkXMLNode(Object obj) throws XMLException{
    if (!(obj instanceof XMLNode)){
      throw new XMLException("Object is not an XMLNode");
      }
    }


/// ANALYSIS METHODS ///


/**
**********************************************************************************************
Writes the structure of the tree starting at this node to the output console
**********************************************************************************************
**/
  public void printTree() {
    try {
      String output = "";

      int level = getLevel() * 2;
      for (int i=0;i<level;i++){
        output += " ";
        }

      output += getTag();

      if (attributes.length > 0){
        output += " (";

        for (int i=0;i<attributes.length;i++){
          if (i != 0){
            output += ", ";
            }
          output += attributes[i][0];
          output += "=";
          output += attributes[i][1];
          }

        output += ")";
        }

      if (content.length() != 0){
        output += " : ";
        output += getContent();
        }

      System.out.println(output);

      int numChildren = getChildCount();
      for (int i=0;i<numChildren;i++){
        getChild(i).printTree();
        }
      }
    catch (Throwable t){
      t.printStackTrace();
      }
    }


  }