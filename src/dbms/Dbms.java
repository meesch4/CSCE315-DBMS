package dbms;

import java.util.*;

/**
 * The internal representation of our database
 * Contains all of the tables, and maybe their rows as well?
 */
public class Dbms {
    // Maps each table name to their internal representation
    // Includes temporary tables as well
    HashMap<String, Object> tables;

    public Dbms() {
        tables = new HashMap();
    }

    // Returns a temporary table name. Need to ensure we dont' use them, could use a rolling count?
    public String getTempTableName() {
        return "";
    }
}
