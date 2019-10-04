package dbms;

import java.util.*;

/**
 *  Use new to instantiate a table root node, with the name and attribute list.
 *  similarly, create the attribute list using a for loop to pop the attributes off the attribute stack
 */
public class TableRootNode { //node containing relation name and attributes of table (column types)
    public TableRootNode(String name, ArrayList<Attribute> attributes){
        relationName = name;
        this.attributes = attributes;
        children = new ArrayList<>();
    }

    public TableRootNode(String name, ArrayList<Attribute> attributes, List<RowNode> kids){
        relationName = name;
        this.attributes = attributes;
        children = kids;
    }
    String relationName;  //make private variables which have getters and setters.
    ArrayList<Attribute> attributes;  //rename to attributes

    List<RowNode> children;

    public void addRow(RowNode row){
        this.children.add(row);
    }
    public void setAttributeName(String name, int index){
        Attribute tempAtt = attributes.get(index); //get attribute that is being changed
        tempAtt.setName(name); //change name of attribute
        attributes.set(index, tempAtt); //set in arraylist
    }
    public ArrayList<Attribute> getAttributes() { return this.attributes; }
    public List<RowNode> getRowNodes() { return this.children; }
    public int getAttributeSize(){
        return this.attributes.size();
    }
    public ArrayList<Attribute> getAttributeList(){
        return this.attributes;
    }

    public Attribute getAttributeWithName(String name) { // This is why attributes should be a Map
        for(Attribute attribute : this.attributes) {
            if(attribute.attrName.equals(name)) {
                return attribute;
            }
        }

        return null;
    }

    // Below used for testing
    public Attribute getAttribute(int index){
        return this.attributes.get(index);
    }
    public void printAttributes(){
        System.out.println(this.attributes);
    }

}

