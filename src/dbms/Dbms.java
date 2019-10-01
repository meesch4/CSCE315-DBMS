package dbms;

import java.util.*;

/**
 * The internal representation of our database
 * Contains all of the tables, and maybe their rows as well?
 */
public class Dbms implements IDbms {
    // Maps each table name to their internal representation
    // Includes temporary tables as well
    private HashMap<String, Object> tables;

    // Should we have a temporary/local tables?

    public Dbms() {
        tables = new HashMap();
    }

    @Override
    public void createTable(String tableName, List<String> columnNames, List<Type> columnTypes, List<String> primaryKeys) {
        if((columnNames.size() != columnTypes.size())|| (columnTypes.size() != primaryKeys.size())){
            System.out.println("Improper input");
            return;
        }
        ArrayList<Attribute> attributesList = new ArrayList<Attribute>;
        Iterator<String> iter = primaryKeys.iterator();
        Iterator<Type> iterType = columnTypes.iterator();
        int i = 0;
        for(String element : columnNames){ //iterate through, make the attribute list
            String pkeyel = iter.next();
            Type typeel = iterType.next();
            Attribute temp;
            temp = new Attribute(element, i, typeel, pkeyel););
            i++;
            attributesList.add(temp); ///this creates the attributes list
        }
        tableRootNode table = new tableRootNode(tableName, attributesList); //creates table
        tables.put(tableName, table); //puts new table root node into hashmap with name as key
    }

    @Override
    public void insertFromRelation(String tableInsertInto, String tableInsertFrom) {
        //Works by taking all the leaves of the tableInsertFrom and adding them to tableInsertInto
        //essentially just take the arraylist of row nodes in tablefrom and append it to the array list of rownodes in insert into

    }

    @Override
    public void insertFromValues(String tableInsertInto, List<Object> valuesFrom) {
        //verify that the attributes match up, and then add a new node to rownodes
    }

    @Override
    public void update(String table, List<String> columnsToSet, List<Object> valuesToSetTo, Condition condition) {
        //e.g. update animals set age == 10 if age >= 10.

    }

    @Override
    public String projection(String tableFrom, List<String> columnNames) {
        String tempTable = getTempTableName();

        return tempTable;
    }

    @Override
    public String rename(String tableName, List<String> newColumnNames) {
        String tempTable = getTempTableName();

        return tempTable;
    }

    @Override
    public String union(String table1, String table2) {
        String tempTable = getTempTableName();

        return tempTable;
    }

    @Override
    public String difference(String table1, String table2) {
        String tempTable = getTempTableName();

        return tempTable;
    }

    @Override
    public String product(String table1, String table2) {
        String tempTable = getTempTableName();

        return tempTable;
    }

    @Override
    public void show(String table) {

    }

    @Override
    public void delete(String table) {
        //iterate through children, deleting the objects,
        //then delete the main node
    }

    @Override
    public void open(String table) {

    }

    @Override
    public void close(String table) {

    }

    @Override
    public void write(String table) {

    }

    @Override
    public void exit() {
        //end the entire program, and save data
        //just call write and then kill the listener
    }

    @Override
    public Table getTable(String tableName) {
        return null;
    }

    private int tempCount = 0;
    private String getTempTableName() { return ("temp" + tempCount++); }

    /**
     *  This class is only used to describe the attribute types; it will be used to check if a row has the
     *  proper attributes before adding it to a table.  If it is missing some attributes, they can be made null
     */
    public class Attribute {
        Attribute(String name, int ind, Type type, String pkey){
            attrName = name;
            index = ind;
            primaryKey = pkey;
        }
        int index;  //used to denote the index of the attribute within the row
        String attrName; //name of attribute, e.g. "age" for an age column
        Type type;
        String primaryKey;

        public int getSize(){
            if(getVC())
                return ((Varchar) type).length;

            return 0;
        }
        public String getName() {
            return attrName;
        }

        public boolean getVC(){
            return type instanceof Varchar;
        }

        public void setSize(int sz){
            if(getVC())
                ((Varchar) type).length = sz;
        }

        public void setName(String nm){
            this.attrName = nm;
        }
    }

    /**
     *  Use new to instantiate a table root node, with the name and attribute list.
     *  similarly, create the attribute list using a for loop to pop the attributes off the attribute stack
     */
    public class tableRootNode { //node containing relation name and attributes of table (column types)
        public tableRootNode(String name, ArrayList<Attribute> attributes){
            relationName = name;
            attList = attributes;
        }
        String relationName;
        ArrayList<Attribute> attList;

        List<RowNode> children;
        public void setName(String nm){
            this.relationName = nm;
        }
        public void addRow(RowNode row){
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

    public class RowNode {
        RowNode(Object[] objects){ //can be used to pass a premade Object array to the class
            dataFields = objects;
        }
        TableRootNode parent;
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
