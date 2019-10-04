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
    public boolean evaluate(Condition cond, RowNode row) {
        Operator op = cond.op;
        switch (op) {
            case AND:
                if (evaluate((Condition) cond.left, row) && evaluate(cond.right, row)) {
                    return true;
                } else {
                    return false;
                }
                break;
            case OR:
                if (evaluate(cond.left, row) || evaluate(cond.right, row)) {
                    return true;
                } else {
                    return false;
                }
                break;
            case LESS_EQ: // <=
                if (cond.left <= cond.right) { // Need to cast
                    return true;
                } else return false;
                break;

        }

        return false; // base case?
    }

    public Object getAttributeValue(TableRootNode table, Attribute attribute, RowNode row) {
        int attrIndex = 0;
        return row.getDataField(attrIndex);
    }
}
