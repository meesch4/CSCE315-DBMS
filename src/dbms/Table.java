package dbms;

import java.util.*;

public class Table {
    private HashMap<String, Type> columnTypes; // name: VARCHAR(20)
    private HashMap<String, Integer> columnIndices; // i.e. name is in index [0]
    private HashMap<String, Row> rows; // (primary key : row)
    // private ArrayList<Row> rows;

    public Table() { // Could have columns as the parameter
        columnTypes = new HashMap<String, Type>();
    }

    public void getRow() {

    }

    public void insertRow() {

    }
}

class Row {
    // private HashMap<String, Object> columns;
    private ArrayList<Object> columns;
}