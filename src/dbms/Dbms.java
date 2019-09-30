package dbms;

import java.util.*;

/**
 * The internal representation of our database
 * Contains all of the tables, and maybe their rows as well?
 */
public class Dbms {
    // Maps each table name to their internal representation
    // Includes temporary tables as well
    HashMap<String, Object> tables;

    public Dbms() {
        tables = new HashMap();
    }

    // Returns a temporary table name. Need to ensure we dont' use them, could use a rolling count?
    public String getTempTableName() {
        return "";
    }

    public class Attribute { //This class is only used to describe the attribute types; it will be used to check if a
        //row has the proper attributes before adding it to a table.  If it is missing some attributes, they can be made
        //null
        Attribute(String name, int sz, boolean VCorInt, int ind){
            attrName = name;
            size = sz;
            isVarchar = VCorInt;
            index = ind;
        }
        int index;  //used to denote the index of the attribute within the row
        String attrName; //name of attribute, e.g. "age" for an age column
        boolean isVarchar; //1 == varchar, 0 == int
        int size; //size of varChar if necessary,  if an int, simply set to 0, as it will not be used
        public int getSize(){
            return size;
        };
        public String getName() {
            return attrName;
        };
        public boolean getVC(){
            return isVarchar;
        };
        public void setSize(int sz){
            this.size = sz;
        };
        public void setName(String nm){
            this.attrName = nm;
        };
        public void setVC(boolean isVC){
            this.isVarchar = isVC;
        };
    }

    //use new to instantiate a table root node, with the name and attribute list.
    //similarly, create the attribute list using a for loop to pop the attributes off the attribute stack

    public class tableRootNode { //node containing relation name and attributes of table (column types)
        public tableRootNode(String name, ArrayList<Attribute> attributes){
            relationName = name;
            attList = attributes;
        }
        String relationName;
        ArrayList<Attribute> attList;



        List<rowNode> children;
        public void setName(String nm){
            this.relationName = nm;
        }
        public void addRow(rowNode row){
            this.children.add(row);
        }
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

    public class rowNode{
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
}