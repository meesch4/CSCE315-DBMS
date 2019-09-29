package dbms;

import java.util.List;

// Functions that Dbms should implement
public interface IDbms {
    void createTable(String tableName, List<String> columnNames, List<Type> columnTypes, List<String> primaryKeys);

    void insertFromRelation(String tableInsertInto, String tableInsertFrom);
    void insertFromValues(String tableInsertInto, List<Object> valuesFrom);

    // Should return a (temporary) table name
    String projection(String tableFrom, List<String> columnNames);
    // String select(String tableFrom, ) // Need to represent expression as something

    // Attempt to get the Table with name tableName from the tables map(or whatever)
    Table getTable(String tableName);
}
