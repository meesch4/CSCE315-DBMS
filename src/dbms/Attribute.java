package dbms;

import types.Type;
import types.Varchar;

import java.io.Serializable;

/**
 *  This class is only used to describe the attribute types; it will be used to check if a row has the
 *  proper attributes before adding it to a table.  If it is missing some attributes, they can be made null
 */
public class Attribute implements Serializable {
    Attribute(String name, int ind, Type type){
        attrName = name;
        index = ind;
        this.type = type;
    }

    public Attribute(String name) {
        index = -1;
        type = null;
        this.attrName = name;
    }

    // Copy constructor
    public Attribute(Attribute toClone) {
        this.attrName = toClone.getName();
        this.type = toClone.getType();
        this.index = toClone.index;
    }


    int index;  //used to denote the index of the attribute within the row
    String attrName; //name of attribute, e.g. "age" for an age column
    Type type;

    public int getSize(){
        if(getVC())
            return ((Varchar) type).length;

        return 0;
    }
    public String getName() {
        return attrName;
    }

    public boolean getVC(){
        return type instanceof Varchar;
    }

    public void setSize(int sz){
        if(getVC())
            ((Varchar) type).length = sz;
    }

    public Type getType(){
        return this.type;
    }


    public void setName(String nm){
        this.attrName = nm;
    }

    @Override public boolean equals(Object obj) {
        if(!(obj instanceof Attribute)) {
            return false;
        }

        Attribute other = (Attribute) obj;

        if(attrName.equals(other.attrName)) {
            if(this.index == other.index) {
                if(type == null && other.type == null)
                    return true;

                if(type.getClass().equals(other.type.getClass())) {
                    if(type instanceof Varchar) { // Compare sizes if both Varchar
                        return ((Varchar) type).length == ((Varchar) other.type).length;
                    } else { // Both integers, return true
                        return true;
                    }
                }
            }
        }

        return false;
    }

}

