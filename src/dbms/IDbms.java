package dbms;

import types.Type;

import java.util.List;

// Functions that Dbms should implement
public interface IDbms {
    void createTable(String tableName, List<String> columnNames, List<Type> columnTypes, List<String> primaryKeys);

    void insertFromRelation(String tableInsertInto, String tableInsertFrom);
    void insertFromValues(String tableInsertInto, List<Object> valuesFrom);

    void update(String table, List<String> columnsToSet, List<Object> valuesToSetTo, Condition condition);

    // Should return a (temporary) table name
    String projection(String tableFrom, List<String> columnNames);
    String select(String tableFrom, Condition condition); // Need to represent expression as something
    String rename(String tableName, List<String> newColumnNames);

    String union(String table1, String table2);
    String difference(String table1, String table2);
    String product(String table1, String table2);

    void show(String table);
    void open(String table);
    void close(String table);
    void write(String table);
    void exit();
    void returnTable(String table);

    void delete(String table, Condition condition);

    // Attempt to get the Table with name tableName from the tables map(or whatever)
    TableRootNode getTable(String tableName);

    void renameTable(String oldName, String newName);
}
