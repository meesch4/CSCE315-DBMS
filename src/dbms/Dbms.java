package dbms;

import java.util.*;

/**
 * The internal representation of our database
 * Contains all of the tables, and maybe their rows as well?
 */
public class Dbms implements IDbms {
    // Maps each table name to their internal representation
    // Includes temporary tables as well
    private HashMap<String, tableRootNode> tables;
    //private HashMap<String, Object> tempTables;

    // Should we have a temporary/local tables?

    public Dbms() {
        tables = new HashMap();
    }
    //public Dbms() { tempTables = new HashMap(); }

    @Override
    public void createTable(String tableName, List<String> columnNames, List<Type> columnTypes, List<String> primaryKeys) {
        if((columnNames.size() != columnTypes.size())|| (columnTypes.size() != primaryKeys.size())){
            System.out.println("Improper input");
            return;
        }
        ArrayList<Attribute> attributesList = new ArrayList<Attribute>();
        Iterator<String> iter = primaryKeys.iterator();
        Iterator<Type> iterType = columnTypes.iterator();
        int i = 0;
        for(String element : columnNames){ //iterate through, make the attribute list
            String pkeyel = iter.next();
            Type typeel = iterType.next();
            Attribute temp;
            temp = new Attribute(element, i, typeel, pkeyel);
            i++;
            attributesList.add(temp); ///this creates the attributes list
        }
        tableRootNode table = new tableRootNode(tableName, attributesList); //creates table
        tables.put(tableName, table); //puts new table root node into hashmap with name as key
    }

    @Override
    public void insertFromRelation(String tableInsertInto, String tableInsertFrom) { //we will need to work on handling the
        //creation of temporary tables for insert command


        //Works by taking all the leaves of the tableInsertFrom and adding them to tableInsertInto
        //essentially just take the arraylist of row nodes in tablefrom and append it to the array list of rownodes in insert into
        ArrayList<Attribute> attListFrom;
        tableRootNode tableFrom = (tableRootNode) tables.get(tableInsertFrom);
        attListFrom = tableFrom.getAttributes();
        ArrayList<Attribute> attListInto;
        tableRootNode tableInto = (tableRootNode) tables.get(tableInsertInto);
        attListInto = tableInto.getAttributes();
        if(attListFrom != attListInto){ //may not work properly as a comparison, if so just remove since data should be clean
            System.out.println("Mismatched attirbutes");
            return;
        }
        List<rowNode> rowListFrom = tableFrom.getRowNodes();
        for(rowNode rowFrom : rowListFrom){ //pulls each row node from table from
            tableInto.addRow(rowFrom);  //inserts them into table into
        }


    }

    @Override
    public void insertFromValues(String tableInsertInto, List<Object> valuesFrom) {
        //verify that the attributes match up, and then add a new node to rownodes
        //this verification is currently fairly naive, as it simply checks the length of the list versus
        //the size of the attribute list of the table it's being inserted into.

        tableRootNode temp = (tableRootNode) tables.get(tableInsertInto);
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
        rowNode newRowNode = new rowNode(rowVals);//creates new row node
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
        List<rowNode> kids = tables.get(tableName).getRowNodes();
        tableRootNode tempTable = new tableRootNode(newName, attributes, kids);
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
        List<rowNode> newRows = tables.get(table1).getRowNodes();
        List<rowNode> newRows2 = tables.get(table2).getRowNodes();
        newRows.addAll(newRows2);
        Set<rowNode> noDupes = new HashSet<>(newRows); //remove duplicates
        newRows.clear(); //clear list
        newRows.addAll(noDupes);  //add new children without duplicates

        tableRootNode newTableRoot = new tableRootNode(newTable, newAttributes, newRows);
        tables.put(newTable, newTableRoot); //add the union to the tables hashmap
        return newTable;
    }

    @Override
    public String difference(String table1, String table2) {
        //String tempTable = getTempTableName();
        String tempTableName = table1 + "-" + table2;
        ArrayList<Attribute> tempAttributes = tables.get(table1).getAttributes();
        tableRootNode tempTable = new tableRootNode(tempTableName, tempAttributes);
        for(rowNode row : tables.get(table1).children){ //for all row nodes in table 1
            if(!(tables.get(table2).children.contains(row))){//if the row node is not in table 2
                tempTable.addRow(row); //place it in the new temp table (create a table with all elements in table 1 but not in table 2)
            }
        }
        tables.put(tempTableName, tempTable);//add new table to hash map

        return tempTableName;
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
        tableRootNode toDelete = (tableRootNode) tables.get(table);
        toDelete.children = null;
        toDelete = null;
        tables.remove(table);
        return;
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
    public Table getTable(String tableName) { //tables are referenced by their root node, I haven't been using the table type
        //I can go through and modify this to accomodate that type, but it's already handled by the root node type.
        //alternatively, we can do fewer modifications by simply making the Table type refer to tableRootNode
        //return tables.get(tableName);
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
        public tableRootNode(String name, ArrayList<Attribute> attributes, List<rowNode> kids){
            relationName = name;
            attList = attributes;
            children = kids;
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
        public void setAttributeName(String name, int index){
            Attribute tempAtt = attList.get(index); //get attribute that is being changed
            tempAtt.setName(name); //change name of attribute
            attList.set(index, tempAtt); //set in arraylist
        }
        public ArrayList<Attribute> getAttributes() { return this.attList; }
        public List<rowNode> getRowNodes() { return this.children; }
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

    public class rowNode {
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
