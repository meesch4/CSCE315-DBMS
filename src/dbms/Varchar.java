package dbms;

/** Contains all of the possible types to be used within the database
 *  Used by Parser & Database when determining what type a column should be
 */
public class Varchar extends Type {
    int length;

    public Varchar(int length) {
        this.length = length;
    }

    public int getLength() { return length; }
}

