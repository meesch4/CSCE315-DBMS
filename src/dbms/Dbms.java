package dbms;

import java.util.*;

/**
 * The internal representation of our database
 * Contains all of the tables, and maybe their rows as well?
 */
public class Dbms {
    HashMap<String, Object> tables;

    public Dbms() {
        tables = new HashMap();
    }
}
