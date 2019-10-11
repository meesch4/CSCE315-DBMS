package dbms.storage;

import dbms.TableRootNode;

import java.util.HashMap;

/**
 *  Saves and loads Tables to and from JSON files
 */
public class JsonSerializer {
    /**
     *  Saves the given table to a file, which is named according to its table name
     */
    public void serialize(TableRootNode table) {

    }

    /**
     *  Given a fileName(tableName), returns the according Table, or null if it couldn't be loaded
     */
    public TableRootNode deserialze(String filePath) {
        TableRootNode table = null;

        return table;
    }
}
