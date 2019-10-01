package dbms;

// If this gets to cluttered, move this to a separate file
public class RowNode {
    RowNode(Object[] objects){ //can be used to pass a premade Object array to the class
        dataFields = objects;
    }
    TableRootNode parent; // When would this be used?
    // Removed the initialization as parent is going to be null when it's called
    Object[] dataFields; // Contains all the VARCHARS/Integers in this row

    public Object getDataField(int index){
        return dataFields[index];
    }
    public void setDataField(int index, Object data){
        dataFields[index] = data;
    }
    public String getRelation(){
        return this.parent.relationName;
    }
}

