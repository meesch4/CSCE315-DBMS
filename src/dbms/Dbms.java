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
        tables = new HashMap<>();
    }

    @Override
    public void createTable(String tableName, List<String> columnNames, List<Type> columnTypes, List<String> primaryKeys) {
        // Create table root Node
    }

    @Override
    public void insertFromRelation(String tableInsertInto, String tableInsertFrom) {

    }

    @Override
    public void insertFromValues(String tableInsertInto, List<Object> valuesFrom) {

    }

    @Override
    public void update(String table, List<String> columnsToSet, List<Object> valuesToSetTo, Condition condition) {

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

    // Will also tk
    @Override public void delete(String table) {

    }

    // Should print out the given table
    @Override public void show(String table) {

    }

    // Opens a table(table + .db) from storage
    @Override public void open(String table) {
        String fileName = table = ".db";

        // Call XMLStorageManager and load it in
    }

    // Save all changes to the relation(table) and close it(remove it from the table map?)
    @Override public void close(String table) {

    }

    // Write the table to the disk? Might want to change this to accept a default filepath
    @Override public void write(String table) {
        String fileName = table + ".db"; // Assuming table is in the tables map

        // Call XMLStorageManager and save it
    }

    // Exit from the interpreter, dunno what should happen here
    @Override public void exit() {

    }

    @Override public Table getTable(String tableName) {
        return null;
    }

    private int tempCount = 0; // current temp table we're on
    private String getTempTableName() { return ("temp" + tempCount++); }

    /**
     *  This class is only used to describe the attribute types; it will be used to check if a row has the
     *  proper attributes before adding it to a table.  If it is missing some attributes, they can be made null
     */
    public class Attribute {
        Attribute(String name, int ind, Type type){
            attrName = name;
            index = ind;
        }
        int index;  //used to denote the index of the attribute within the row
        String attrName; //name of attribute, e.g. "age" for an age column
        Type type;

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
    public class TableRootNode { //node containing relation name and attributes of table (column types)
        public TableRootNode(String name, ArrayList<Attribute> attributes){
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
