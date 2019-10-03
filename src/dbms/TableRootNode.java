package dbms;

import java.util.*;

/**
 *  Use new to instantiate a table root node, with the name and attribute list.
 *  similarly, create the attribute list using a for loop to pop the attributes off the attribute stack
 */
public class TableRootNode { //node containing relation name and attributes of table (column types)
    public TableRootNode(String name, ArrayList<Attribute> attributes){
        relationName = name;
        attList = attributes;
    }
    public TableRootNode(String name, ArrayList<Attribute> attributes, List<RowNode> kids){
        relationName = name;
        attList = attributes;
        children = kids;
    }
    String relationName;  //make private variables which have getters and setters.
    ArrayList<Attribute> attList;  //rename to attributes

    List<RowNode> children;
    public void setName(String nm){
        this.relationName = nm;
    }
    public void addRow(RowNode row){
        this.children.add(row);
    }   //delete unused funcitons
    public void setAttributeName(String name, int index){
        Attribute tempAtt = attList.get(index); //get attribute that is being changed
        tempAtt.setName(name); //change name of attribute
        attList.set(index, tempAtt); //set in arraylist
    }
    public ArrayList<Attribute> getAttributes() { return this.attList; }
    public List<RowNode> getRowNodes() { return this.children; }
    public Attribute getAttribute(int index){
        return this.attList.get(index);
    }
    public void printAttributes(){
        System.out.println(this.attList);
    }
    public int getAttributeSize(){
        return this.attList.size();
    }
    //public void removeAttribute(int index){
    //    attList.remove(index);
    //}
    public ArrayList<Attribute> getAttributeList(){
        return this.attList;
    }
}

