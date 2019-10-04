package dbms;

import types.Type;

import java.util.*;

/**
 * The internal representation of our database
 * Contains all of the tables, and maybe their rows as well?
 */
public class Dbms implements IDbms {
    // Maps each table name to their internal representation
    // Includes temporary tables as well
    private HashMap<String, TableRootNode> tables;
    //private HashMap<String, Object> tempTables;

    // Should we have a temporary/local tables?

    public Dbms() {
        tables = new HashMap();
    }
    //public Dbms() { tempTables = new HashMap(); }

    @Override
    public void createTable(String tableName, List<String> columnNames, List<Type> columnTypes, List<String> primaryKeys) {
        if((columnNames.size() != columnTypes.size())) { // Doesn't need to be an equal amount of primaryKeys & columnTypes
            System.out.println("Improper input");
            return;
        }
        ArrayList<Attribute> attributesList = new ArrayList<Attribute>();
        Iterator<String> iter = primaryKeys.iterator();
        Iterator<Type> iterType = columnTypes.iterator();
        int i = 0; // Rename to columnIndex? Why not just do a for i = 0 loop?
        for(String element : columnNames){ //iterate through, make the attribute list
            String pkeyel = ""; // How is this even used?
            // String pkeyel = iter.next();
            Type typeel = iterType.next();
            Attribute temp;
            temp = new Attribute(element, i, typeel, pkeyel);
            i++;
            attributesList.add(temp); ///this creates the attributes list
        }
        TableRootNode table = new TableRootNode(tableName, attributesList); //creates table
        tables.put(tableName, table); //puts new table root node into hashmap with name as key
    }

    @Override
    public void insertFromRelation(String tableInsertInto, String tableInsertFrom) {
        //we will need to work on handling the creation of temporary tables for insert command


        //Works by taking all the leaves of the tableInsertFrom and adding them to tableInsertInto
        //essentially just take the arraylist of row nodes in tablefrom and append it to the array list of rownodes in insert into
        ArrayList<Attribute> attListFrom;
        TableRootNode tableFrom = (TableRootNode) tables.get(tableInsertFrom);
        attListFrom = tableFrom.getAttributes();
        ArrayList<Attribute> attListInto;
        TableRootNode tableInto = (TableRootNode) tables.get(tableInsertInto);
        attListInto = tableInto.getAttributes();
        if(attListFrom != attListInto){ //may not work properly as a comparison, if so just remove since data should be clean
            System.out.println("Mismatched attirbutes");
            return;
        }
        List<RowNode> rowListFrom = tableFrom.getRowNodes();
        for(RowNode rowFrom : rowListFrom){ //pulls each row node from table from
            tableInto.addRow(rowFrom);  //inserts them into table into
        }


    }

    @Override
    public void insertFromValues(String tableInsertInto, List<Object> valuesFrom) {
        //verify that the attributes match up, and then add a new node to rownodes
        //this verification is currently fairly naive, as it simply checks the length of the list versus
        //the size of the attribute list of the table it's being inserted into.

        TableRootNode temp = (TableRootNode) tables.get(tableInsertInto);
        int lengAttributes = temp.getAttributeSize();
        if(valuesFrom.size() != lengAttributes){
            System.out.println("Mismatched attribute length");
            return;
        }
        Object[] rowVals = new Object[lengAttributes];
        int i = 0;
        for(Object val : valuesFrom){
            rowVals[i] = val;
            i++;
        }
        RowNode newRowNode = new RowNode(rowVals);//creates new row node
        temp.addRow(newRowNode);//adds row node

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
    public String rename(String tableName, List<String> newColumnNames) { //should this really return a string?
        String newName = tableName + "temp";
        ArrayList<Attribute> attributes = tables.get(tableName).getAttributes();
        List<RowNode> kids = tables.get(tableName).getRowNodes();
        TableRootNode tempTable = new TableRootNode(newName, attributes, kids);
        int i = 0;
        for(String name : newColumnNames){
            tempTable.setAttributeName(name, i);
            i++;
        }
        tables.put(newName, tempTable);
        return newName;
    }

    @Override
    public String union(String table1, String table2) {
        String newTable = table1 + " " + table2; //the output table name will be a combination of the two table names
        ArrayList<Attribute> newAttributes = tables.get(table1).getAttributes(); //*****requires matching Attributes*****
        List<RowNode> newRows = tables.get(table1).getRowNodes();
        List<RowNode> newRows2 = tables.get(table2).getRowNodes();
        newRows.addAll(newRows2);
        Set<RowNode> noDupes = new HashSet<>(newRows); //remove duplicates
        newRows.clear(); //clear list
        newRows.addAll(noDupes);  //add new children without duplicates

        TableRootNode newTableRoot = new TableRootNode(newTable, newAttributes, newRows);
        tables.put(newTable, newTableRoot); //add the union to the tables hashmap
        return newTable;
    }

    @Override
    public String select(String tableFrom, Condition condition){

        return"Blah";
    }

    @Override
    public String difference(String table1, String table2) {
        //String tempTable = getTempTableName();
        String tempTableName = table1 + "-" + table2;
        ArrayList<Attribute> tempAttributes = tables.get(table1).getAttributes();
        TableRootNode tempTable = new TableRootNode(tempTableName, tempAttributes);
        for(RowNode row : tables.get(table1).children){ //for all row nodes in table 1
            if(!(tables.get(table2).children.contains(row))){//if the row node is not in table 2
                tempTable.addRow(row); //place it in the new temp table (create a table with all elements in table 1 but not in table 2)
            }
        }
        tables.put(tempTableName, tempTable);//add new table to hash map

        return tempTableName;
    }

    @Override
    public String product(String table1, String table2) {
        String tempName = table1 + "cross" + table2;
        ArrayList<Attribute> tempAttributes;
        tempAttributes = tables.get(table1).getAttributes();
        tempAttributes.addAll(tables.get(table2).getAttributes()); //creates attribute list with both sets of attributes
        TableRootNode tempTable = new TableRootNode(tempName, tempAttributes); //new table
        List<RowNode> tableOneLeaves = tables.get(table1).getRowNodes();//leaves from table 1
        List<RowNode> tableTwoLeaves = tables.get(table2).getRowNodes(); //leaves from table 2

        for(RowNode rowOne : tableOneLeaves){
            for(RowNode rowTwo : tableTwoLeaves){


                int lengOne = rowOne.getDataFields().length;
                int lengTwo = rowTwo.getDataFields().length;
                int leng = lengOne + lengTwo;
                Object[] newDataFields = new Object[leng];

                for(int i = 0; i < lengOne; i++){
                    newDataFields[i] = rowOne.getDataField(i);
                }
                for(int j = 0; j < lengTwo; j++){
                    newDataFields[j+lengOne] = rowTwo.getDataField(j);
                }
                RowNode newRow = new RowNode(newDataFields);
                tempTable.addRow(newRow);


            }
        }

        return tempName;
    }

    @Override
    public void show(String table) {

    }

    @Override
    public void delete(String table) {
        //iterate through children, deleting the objects,
        //then delete the main node
        TableRootNode toDelete = (TableRootNode) tables.get(table);
        toDelete.children = null;
        toDelete = null;
        tables.remove(table);
        return;
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
    @Override
    public void exit() {
        //end the entire program, and save data
        //just call write and then kill the listener
    }

    @Override
    public TableRootNode getTable(String tableName) {
        return tables.get(tableName);
    }

    private int tempCount = 0; // current temp table we're on
    private String getTempTableName() { return ("temp" + tempCount++); }
}
