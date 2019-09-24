package parser;

/** Contains all of the possible types to be used within the database
 *  Used by Parser & Database when determining what type a column should be
 */
public enum Types {
    INT,
    FLOAT,
    VARCHAR,// variable-length string up to n characters
    CHAR,   // fixed-length string of n characters
    DATE,   // yyyy-mm-dd
    TIME    // hh:mm:ss
}
