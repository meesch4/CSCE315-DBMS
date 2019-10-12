package dbms;

import java.io.Serializable;
import java.util.*;

/**
 *  Use new to instantiate a table root node, with the name and attribute list.
 *  similarly, create the attribute list using a for loop to pop the attributes off the attribute stack
 */
public class TableRootNode implements Serializable { //node containing relation name and attributes of table (column types)
    public TableRootNode(String name, ArrayList<Attribute> attributes, ArrayList<Attribute> primaryKeys){
        relationName = name;
        this.attributes = attributes;
        children = new HashMap<>();
        this.primaryKeys = primaryKeys;
    }

    public TableRootNode(String name, ArrayList<Attribute> attributes, ArrayList<Attribute> primaryKeys, HashMap<String, RowNode> kids){
        relationName = name;
        this.attributes = attributes;
        children = kids;
        this.primaryKeys = primaryKeys;
    }

    String relationName;  //make private variables which have getters and setters.
    ArrayList<Attribute> attributes;  //rename to attributes
    ArrayList<Attribute> primaryKeys; // Have this as a row so the primaryKeys are in order
    HashMap<String, RowNode> children; // Rename to rows?

    public void addRow(RowNode row){
        int[] indices = new int[primaryKeys.size()];
        int i = 0;
        for(Attribute attribute : primaryKeys) {
            indices[i++] = attribute.index;
        }

        row.primaryKeyIndices = indices;

        String primaryKey = row.getPrimaryKeyValue();

        children.put(primaryKey, row);
    }

    public void setAttributeName(String name, int index){
        Attribute tempAtt = attributes.get(index); //get attribute that is being changed
        tempAtt.setName(name); //change name of attribute
        attributes.set(index, tempAtt); //set in arraylist
    }
    public ArrayList<Attribute> getAttributes() { return this.attributes; }
    public HashMap<String, RowNode> getRowNodes() {
        return this.children;
    }
    // public List<RowNode> getRowNodes() { return this.children; }
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
    public String getRelationName() { return this.relationName; }
    public void printAttributes(){
        System.out.println(this.attributes);
    }

}

