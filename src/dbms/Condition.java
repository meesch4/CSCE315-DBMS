package dbms;


//to construct the conditions from your post fixed stack, you need a recursive function which
//creates conditions, where you recurse if you have an && or an || operator.

//when you hit an operator, the next thing in the stack is going to be your right object
//after that you'll have the left object.
//If it's <= == etc, then those objects will be an attribute and value pair, and you don't need to recurse further.

//If you hit the &&/|| then you will recurse further, and the next object will be another operator.

public class Condition {
    // Filled with table names, operators, and operands
    // Might also contain Attributes
    // Will probably be the result of the shunting-yard algorithm
    public Object left;
    public Object right;
    public Operator op;

    //Syntax is garbage, and this needs to be properly implemented, but this explains the basic recursive structure of the evaluate funciton
    // I'll work through the specifics soon.
    public static boolean evaluate(Condition cond, RowNode row, TableRootNode table) {
        Operator op = cond.op;
        Object value = null;
        Object literal = null;

        switch (op) {
            case AND:
                return evaluate((Condition) cond.left, row, table) && evaluate((Condition) cond.right, row, table);
            case OR:
                return evaluate((Condition) cond.left, row, table) || evaluate((Condition) cond.right, row, table);
        }
        //Split since the following for loop need not execute for the recursive evaluate calls
        int indexToCompare;
        int leftRight = -1;
        for(int i = 0; i < table.getAttributeSize(); i++){
            if(cond.left == table.getAttribute(i).attrName){
                leftRight = 0;
                indexToCompare = i;
                i = table.getAttributeSize();
            }else if(cond.right == table.getAttribute(i).attrName){
                leftRight = 1;
                indexToCompare = i;
                i = table.getAttributeSize();
            }
        }
        switch (op){
            case EQUALS:
                // At this point, cond.left (Or cond.right) is going to be an attribute
                // We need to find what index this attribute is from the table(we only have it's name at this point)
                // then retrieve that corresponding value from the RowNode. Only then can we compare
                for(int i = 0; i < table.getRowNodes().size(); i++){
                    if(leftRight == 0){
                        if(cond.right == row.getDataField(i)){
                            return true;
                        }
                    }if(leftRight == 1) {
                        if (cond.left == row.getDataField(i)) {
                            return true;
                        }
                    }
                }
                return false;

            case NOT_EQUALS:
                for(int i = 0; i < table.getRowNodes().size(); i++){
                    if(leftRight == 0){
                        if(cond.right != row.getDataField(i)){
                            return true;
                        }
                    }if(leftRight == 1) {
                        if (cond.left != row.getDataField(i)) {
                            return true;
                        }
                    }
                }
                return false;
            case LESS_EQ: // <=
                for(int i = 0; i < table.getRowNodes().size(); i++){
                    if(leftRight == 0){
                        if((int) cond.right <= (int) row.getDataField(i)){
                            return true;
                        }
                    }if(leftRight == 1) {
                        if ((int) cond.left <= (int) row.getDataField(i)) {
                            return true;
                        }
                    }
                }
                return false;
            case LESS:
                for(int i = 0; i < table.getRowNodes().size(); i++){
                    if(leftRight == 0){
                        if((int) cond.right < (int) row.getDataField(i)){
                            return true;
                        }
                    }if(leftRight == 1) {
                        if ((int) cond.left < (int) row.getDataField(i)) {
                            return true;
                        }
                    }
                }
                return false;
            case GREATER_EQ:
                for(int i = 0; i < table.getRowNodes().size(); i++){
                    if(leftRight == 0){
                        if((int) cond.right >= (int) row.getDataField(i)){
                            return true;
                        }
                    }if(leftRight == 1) {
                        if ((int) cond.left >= (int) row.getDataField(i)) {
                            return true;
                        }
                    }
                }
                return false;
            case GREATER:
                for(int i = 0; i < table.getRowNodes().size(); i++){
                    if(leftRight == 0){
                        if((int) cond.right > (int) row.getDataField(i)){
                            return true;
                        }
                    }if(leftRight == 1) {
                        if ((int) cond.left > (int) row.getDataField(i)) {
                            return true;
                        }
                    }
                }
                return false;
            default: break;
        }

        return false; // base case?
    }

    public Object getAttributeValue(TableRootNode table, Attribute attribute, RowNode row) {
        int attrIndex = 0;
        return row.getDataField(attrIndex);
    }
}
