package dbms;

import java.util.*;

/**
 *  Use new to instantiate a table root node, with the name and attribute list.
 *  similarly, create the attribute list using a for loop to pop the attributes off the attribute stack
 */
public class tableRootNode { //node containing relation name and attributes of table (column types)
    public tableRootNode(String name, ArrayList<Attribute> attributes){
        relationName = name;
        attList = attributes;
    }
    public tableRootNode(String name, ArrayList<Attribute> attributes, List<rowNode> kids){
        relationName = name;
        attList = attributes;
        children = kids;
    }

    String relationName; // the table's name
    ArrayList<Attribute> attList; // Rename this to attributes? No need to call it a list
    List<rowNode> children;

    public void setName(String nm){
        this.relationName = nm;
    }

    public void addRow(rowNode row){
        this.children.add(row);
    }

    public void setAttributeName(String name, int index){
        Attribute tempAtt = attList.get(index); //get attribute that is being changed
        tempAtt.setName(name); //change name of attribute
        attList.set(index, tempAtt); //set in arraylist
    }

    public ArrayList<Attribute> getAttributes() { return this.attList; }

    public List<rowNode> getRowNodes() { return this.children; }

    public Attribute getAttribute(int index){ return this.attList.get(index); }

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

class rowNode {
    rowNode(Object[] objects){ //can be used to pass a premade Object array to the class
        dataFields = objects;
    }
    tableRootNode parent;
    Object[] dataFields = new Object[this.parent.getAttributeSize()];
    //this Object array contains all the VARCHARS and integers in the rows
    public Object getDataField(int index){
        return dataFields[index];
    }
    public void setDataField(int index, Object data){
        dataFields[index] = data;
    }
    public String getRelation(){
        return this.parent.relationName;
    }
}
