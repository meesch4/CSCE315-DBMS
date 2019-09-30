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

    private int tempCount = 0;
    private String getTempTableName() { return ("temp" + tempCount++); }
}
