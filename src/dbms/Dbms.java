package dbms;

import dbms.storage.TableSerializer;
import scraper.DataLoader;
import types.Type;
import types.Varchar;

import java.util.*;

/**
 * The internal representation of our database
 * Contains all of the tables, and maybe their rows as well?
 */
public class Dbms implements IDbms {
    // Maps each table name to their internal representation
    // Includes temporary tables as well
    public HashMap<String, TableRootNode> tables;
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
        ArrayList<Attribute> newPrimaryKeys = new ArrayList<>();
        Iterator<String> iter = primaryKeys.iterator();
        Iterator<Type> iterType = columnTypes.iterator();
        int i = 0; // Rename to columnIndex? Why not just do a for i = 0 loop?
        for(String element : columnNames){ //iterate through, make the attribute list
            Type typeel = iterType.next();
            Attribute temp;
            temp = new Attribute(element, i, typeel);
            i++;
            attributesList.add(temp); ///this creates the attributes list

            if(primaryKeys.contains(element))
                newPrimaryKeys.add(temp);
        }

        TableRootNode table = new TableRootNode(tableName, attributesList, newPrimaryKeys); //creates table
        tables.put(tableName, table); //puts new table root node into hashmap with name as key
    }

    @Override
    public void insertFromRelation(String tableInsertInto, String tableInsertFrom) {
        // Works by taking all the leaves of the tableInsertFrom and adding them to tableInsertInto
        TableRootNode tableFrom = tables.get(tableInsertFrom);
        ArrayList<Attribute> attListFrom = tableFrom.getAttributes();
        TableRootNode tableInto = tables.get(tableInsertInto);
        ArrayList<Attribute> attListInto = tableInto.getAttributes();

        // when an attribute in the attributes from list matches the name and type of an attribute in the attributes into list,
        //this loop will create a tuple of the form [indexFrom, indexInto] to inform the next code block which columns in the
        //FROM table to map to the which columns in the INTO table.
        ArrayList<int[]> tuples = new ArrayList<>();
        for(Attribute attributeInto : attListInto) {
            boolean found = false;
            // Used for determining default values
            int type = attributeInto.type instanceof Varchar ? 0 : 1; // 0 is a varchar, 1 is an integer.
            for(Attribute attributeFrom : attListFrom) {
                if((attributeFrom.getName().equals(attributeInto.getName()))){     //ONLY CHECKS THAT ATTRIBUTE NAMES MATCH, DOES NOT CHECK IF THE TYPES MATCH
                    int[] newTup = new int[]{attributeFrom.index, attributeInto.index, type};
                    //attListFrom.remove(attributeFrom);
                    tuples.add(newTup);
                    found = true;
                    break;
                }
            }
            if(!found){
                int[] newTup = new int[]{-1, attributeInto.index, type};
                tuples.add(newTup);
            }
        }
        /*while(!(attListFrom.isEmpty())){
            for(Attribute attributeFrom : attListFrom){
                Object[] newTup = new Object[]{attributeFrom.index, null};
                tuples.add(newTup);
            }
        }*/



        // List<RowNode> rowListFrom = tableFrom.getRowNodes();
        HashMap<String, RowNode> rowMapFrom = tableFrom.getRowNodes();
        for(RowNode rowFrom : rowMapFrom.values()){ //pulls each row node from table from
            Object[] newDataFields = new Object[attListInto.size()];
            for(int[] tuple : tuples){
                int fromIndex = tuple[0];
                int toIndex = tuple[1];
                if(fromIndex == -1){ //if the value is not present in the from table
                    newDataFields[toIndex] = (tuple[2] == 0 ? "" : 0); //set value to null
                }else{
                    newDataFields[toIndex] = rowFrom.getDataField(fromIndex);
                }
            }

            RowNode newRow = new RowNode(newDataFields);
            tableInto.addRow(newRow);  //inserts them into table into
        }
    }

    @Override // Should be done
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
    public void update(String tableName, List<String> columnsToSet, List<Object> valuesToSetTo, Condition condition) {
        TableRootNode table = tables.get(tableName);

        //e.g. update animals set age == 10 if age >= 10.
        // List<Integer> rowsToUpdate = new ArrayList<>(); // Contains the rows to update, by which rowIndex they are in
        List<String> rowKeysToUpdate = new ArrayList<>(); // Could also do a set

        // Iterate through all of table.rows
        // List<RowNode> tableRows = table.getRowNodes();
        HashMap<String, RowNode> tableRows = table.getRowNodes();
        for(Map.Entry<String, RowNode> rowEntry : tableRows.entrySet()) {
            String key = rowEntry.getKey(); // The generated primary key or UUID
            // Evaluate the condition and add it to rowsToUpdate if it's true
            if(Condition.evaluate(condition, rowEntry.getValue(), table)){ // Might need to pass in tab
                rowKeysToUpdate.add(key);
            }
        }

        // Mapped as (column index : value to update column with)
        Map<Integer, Object> colsToUpdate = new HashMap<>();

        for(int i = 0; i < columnsToSet.size(); i++) {
            String colName = columnsToSet.get(i);
            Object valueToSet = valuesToSetTo.get(i);

            int colIndex = table.getAttributeWithName(colName).index;
            colsToUpdate.put(colIndex, valueToSet);
        }

        // Update the according rows
        for(String rowKey : rowKeysToUpdate) {
            RowNode row = tableRows.get(rowKey); // Pass by reference?

            // Iterate through colIndicesToUpdate and set the according value from valuesToSetTo
            for(Map.Entry<Integer, Object> colValuePair : colsToUpdate.entrySet()) {
                row.setDataField(colValuePair.getKey(), colValuePair.getValue());
            }

            // Remove it from being referenced w/ its old row key
            // Might not always the value that's used by the primaryKey, but it shouldn't affect it
            tableRows.remove(rowKey);

            String newRowKey = row.getPrimaryKeyValue();
            tableRows.put(newRowKey, row);
        }
    }

    @Override
    public String projection(String tableFrom, List<String> columnNames) {
        String tempTable = getTempTableName();
        ArrayList<Attribute> origAttributes = tables.get(tableFrom).getAttributes();
        ArrayList<Integer> indices = new ArrayList<>();
        ArrayList<Attribute> newAttributes = new ArrayList<>();
        ArrayList<Attribute> newPrimaryKeys = new ArrayList<>();

        int j = 0;
        for(Attribute att : origAttributes){ // find the indices of the columns we need to maintain
            if(columnNames.contains(att.getName())){
                indices.add(att.index);
                Attribute newAttribute;
                newAttribute = new Attribute(att.getName(), j++, att.getType());
                newAttributes.add(newAttribute);

                if(tables.get(tableFrom).primaryKeys.contains(att)) {
                    newPrimaryKeys.add(newAttribute);
                }
            }
        }

        // If we don't have any primary keys, make them equal to all of the attributes
        if(newPrimaryKeys.isEmpty()) {
            newPrimaryKeys = new ArrayList<>(newAttributes);
        }

        TableRootNode newTable = new TableRootNode(tempTable, newAttributes, newPrimaryKeys);

        for(RowNode row : tables.get(tableFrom).getRowNodes().values()){ //iterate through tableFrom's rows
            Object[] data = new Object[j]; //create new dataFields object[]

            int i = 0;
            for(Integer index : indices){//iterate through column indices we're interested in
                data[i] = (row.dataFields[index]);//add data to dataFields
                i++;
            }
            RowNode newRow = new RowNode(data);
            newTable.addRow(newRow);
        }
        tables.put(tempTable, newTable);
        return tempTable;
    }

    @Override
    public String rename(String tableName, List<String> newColumnNames) { //should this really return a string?
        String newName = getTempTableName();
        TableRootNode oldTable = tables.get(tableName);
        TableRootNode tempTable = new TableRootNode(newName, oldTable.getAttributes(), oldTable.primaryKeys, oldTable.getRowNodes());
        int i = 0;
        for(String name : newColumnNames){
            tempTable.setAttributeName(name, i++);
        }
        tables.put(newName, tempTable);
        return newName;
    }

    @Override
    public String union(String table1, String table2) {
        String newTable = getTempTableName(); //the output table name will be a combination of the two table names
        ArrayList<Attribute> newAttributes = tables.get(table1).getAttributes(); //*****requires matching Attributes*****
        // List<RowNode> newRows = tables.get(table1).getRowNodes();
        HashMap<String, RowNode> newRows = new HashMap<>(tables.get(table1).getRowNodes()); // Make a copy so we don't modify the main one
        //List<RowNode> newRows2 = tables.get(table2).getRowNodes();
        HashMap<String, RowNode> newRows2 = tables.get(table2).getRowNodes();
        newRows.putAll(newRows2);
        // Set<RowNode> noDupes = new HashSet<>(newRows); //remove duplicates
        // newRows.clear(); //clear list
        // newRows.addAll(noDupes);  //add new children without duplicates

        TableRootNode newTableRoot = new TableRootNode(newTable, newAttributes, tables.get(table1).primaryKeys, newRows);
        tables.put(newTable, newTableRoot); //add the union to the tables hashmap

        return newTable;
    }

    @Override
    public String select(String tableFrom, Condition condition){
        String tempTableName = getTempTableName();

        TableRootNode table = tables.get(tableFrom);
        ArrayList<Attribute> attributes = table.getAttributes();
        TableRootNode newTable = new TableRootNode(tempTableName, attributes, table.primaryKeys); // Set its primary keys to be tableFrom's

        for(RowNode row : tables.get(tableFrom).getRowNodes().values()) { //iterate through row nodes
            boolean include = Condition.evaluate(condition, row, table);

            if(include) {
                newTable.addRow(row);
            }
        }

        tables.put(tempTableName, newTable);
        return tempTableName;
    }

    @Override
    public String difference(String table1, String table2) {
        //String tempTable = getTempTableName();
        String tempTableName = getTempTableName();
        ArrayList<Attribute> tempAttributes = tables.get(table1).getAttributes();
        TableRootNode tempTable = new TableRootNode(tempTableName, tempAttributes, tables.get(table1).primaryKeys);

        for(Map.Entry<String, RowNode> rowEntry : tables.get(table1).getRowNodes().entrySet()){ //for all row nodes in table 1
            if(!(tables.get(table2).getRowNodes().containsKey(rowEntry.getKey()))) { //if the row node is not in table 2
                tempTable.addRow(rowEntry.getValue()); //place it in the new temp table (create a table with all elements in table 1 but not in table 2)
            }
        }
        tables.put(tempTableName, tempTable);//add new table to hash map

        // Transfer primary keys to new tables

        return tempTableName;
    }

    // Primary key is every single column
    @Override
    public String product(String table1, String table2) {
        String tempName = getTempTableName();
        ArrayList<Attribute> tempAttributes = new ArrayList<>(tables.get(table1).getAttributes());
        ArrayList<Attribute> secondAttributes = cloneAttributes(tables.get(table2).getAttributes());

        int k = tempAttributes.size();
        for(Attribute att : secondAttributes){
            att.index = att.index + k;
        }
        tempAttributes.addAll(secondAttributes); //creates attribute list with both sets of attributes

        // Primary keys are equivalent to every single column for product
        TableRootNode tempTable = new TableRootNode(tempName, tempAttributes, tempAttributes); //new table
        // TODO: Temp table's primary keys should be every single attribute

        for(RowNode rowOne : tables.get(table1).getRowNodes().values()){
            for(RowNode rowTwo : tables.get(table2).getRowNodes().values()){
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
        tables.put(tempName, tempTable);
        return tempName;
    }

    @Override
    public void show(String tableName) {
        String s;
        ArrayList<Attribute> attributes = tables.get(tableName).getAttributes();
        final int colWidth = 25;
        String line = "";
        s = " " + tableName + "\n" ;
        for(int k = 0; k< attributes.size(); k++) {
            s += "--------------------------";
        }
        if (attributes.size() == 0){
            s += "";
        } else {
            s += "|\n";
        }
        for(int i = 0; i< attributes.size(); i++) {
            Attribute attr = attributes.get(i);

            line += attributes.get(i).getName() ;

            if(attr.type instanceof Varchar) {
                line += ": Varchar(" + ((Varchar) attr.type).length + ")";
            } else {
                line += ": Int";
            }

            while(line.length()<(colWidth)) {
                line += " ";
            }

            if (i == (attributes.size()-1)){
                line += " |";
            } else {
                line += "|";
            }
            s += line;
            line = "";
        }
        s += "\n";
        for(int l = 0; l< attributes.size(); l++) {
            s += "--------------------------";
        }
        if (attributes.size() == 0){
            s += "";
        } else {
            s += "|\n";
        }

        TableRootNode table = (TableRootNode) tables.get(tableName);
        // List<RowNode> rowList = table.getRowNodes();
        HashMap<String, RowNode> rowMap = table.getRowNodes();
        for(RowNode currRow : rowMap.values()) {
            for(int j = 0; j<currRow.getDataFields().length; j++) {
                Object data = currRow.getDataField(j);

                // Display "(empty)" for empty string
                if(data instanceof String && ((String) data).length() == 0)
                    line += "(empty)";
                else
                    line += data;

                while(line.length()<colWidth) {
                    line += " ";
                }
                if (j == (currRow.getDataFields().length-1)){
                    line += " |";
                } else {
                    line += "|";
                }
                s += line;
                line = "";
            }
            s += "\n";
            for(int m = 0; m< attributes.size(); m++) {
                s += "--------------------------";
            }
            if (attributes.size() == 0){
                s += "";
            } else {
                s += "|\n";
            }
        }

        if (attributes.size() == 0){
            s = tableName + "\n";
            System.out.println(s + "Empty Table");
        } else {
            System.out.println(s);
        }



    }

    @Override
    public void delete(String tableName, Condition condition) {
        TableRootNode table = tables.get(tableName);

        Set<String> rowKeysToRemove = new HashSet<>();

        // List<RowNode> tableRows = table.getRowNodes();
        Map<String, RowNode> tableRows = table.getRowNodes();

        for(Map.Entry<String, RowNode> rowEntry : tableRows.entrySet()) {
            if(Condition.evaluate(condition, rowEntry.getValue(), table)) {
                rowKeysToRemove.add(rowEntry.getKey());
            }
        }

        for(String rowKey : rowKeysToRemove) {
            RowNode removed = tableRows.remove(rowKey);
        }
    }

    @Override
    public void returnTable(String tableName) {
        // Basically just renames the table to "returned"
        renameTable(tableName, "RETURNED");
    }

    // Opens a table(table + .db) from storage
    @Override public void open(String tableName) {
        if(tables.containsKey(tableName)) // Don't load the table if it's already loaded
            return;

        DataLoader loader = new DataLoader();
        // TableRootNode table = TableSerializer.loadFromFile(tableName);
        TableRootNode table = loader.getTable(tableName);
        if(table == null) {
            System.err.println("Could not load table " + tableName);
            return; // Do something
        }

        tables.put(tableName, table); // Assuming table isn't null
    }

    // Save all changes to the relation(table) and close it(remove it from the table map?)
    @Override public void close(String table) {
        tables.remove(table); // Remove the table from the Table map
    }

    // Write the table to the disk? Might want to change this to accept a default filepath
    @Override public void write(String tableName) {
        TableRootNode table = this.getTable(tableName);

        TableSerializer.saveToFile(table);
    }

    // Exit from the interpreter, dunno what should happen here
    @Override
    public void exit() {
        //end the entire program, and save data
        //just call write and then kill the listener
        // Is this even necessary to implement?
    }

    // Removes the (key, value) pair with oldName and replaces it with newName
    public void renameTable(String oldName, String newName) {
        TableRootNode table = tables.remove(oldName);
        table.relationName = newName;
        tables.put(newName, table);
    }

    @Override
    public TableRootNode getTable(String tableName) {
        return tables.get(tableName);
    }

    private int tempCount = 0; // current temp table we're on
    private String getTempTableName() { return ("temp" + tempCount++); }

    private ArrayList<Attribute> cloneAttributes(ArrayList<Attribute> attributesToClone) {
        ArrayList<Attribute> ret = new ArrayList<>();

        for(Attribute attr : attributesToClone) {
            ret.add(new Attribute(attr));
        }

        return ret;
    }
}
