package dbms;

/**
 *  This class is only used to describe the attribute types; it will be used to check if a row has the
 *  proper attributes before adding it to a table.  If it is missing some attributes, they can be made null
 */
public class Attribute {
    Attribute(String name, int ind, Type type, String pkey) {
        attrName = name;
        index = ind;
        primaryKey = pkey;
    }

    int index;  //used to denote the index of the attribute within the row
    String attrName; //name of attribute, e.g. "age" for an age column
    Type type;
    String primaryKey;

    public int getSize() {
        if (getVC())
            return ((Varchar) type).length;

        return 0;
    }

    public String getName() {
        return attrName;
    }

    public boolean getVC() {
        return type instanceof Varchar;
    }

    public void setSize(int sz) {
        if(getVC())
            ((Varchar) type).length = sz;
    }

    public void setName(String nm) {
        this.attrName = nm;
    }
}

