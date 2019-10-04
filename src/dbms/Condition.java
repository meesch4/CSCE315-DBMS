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
        Object value = null;
        Object literal = null;
        switch (op) {
            case AND:
                return evaluate((Condition) cond.left, row) && evaluate((Condition) cond.right, row);
            case OR:
                return evaluate((Condition) cond.left, row) || evaluate((Condition) cond.right, row);
            case EQUALS:
                // At this point, cond.left (Or cond.right) is going to be an attribute
                // We need to find what index this attribute is from the table(we only have it's name at this point)
                // then retrieve that corresponding value from the RowNode. Only then can we compare

                return value.equals(literal);
            case NOT_EQUALS:
                return !value.equals(literal);
            case LESS_EQ: // <=
                return (int) value <= (int) literal;
            case LESS:
                return (int) value < (int) literal;
            case GREATER_EQ:
                return (int) value >= (int) literal;
            case GREATER:
                return (int) value > (int) literal;
            default: break;
        }

        return false; // base case?
    }

    public Object getAttributeValue(TableRootNode table, Attribute attribute, RowNode row) {
        int attrIndex = 0;
        return row.getDataField(attrIndex);
    }
}
