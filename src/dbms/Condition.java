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

        // Set value or literal
        if(cond.left instanceof Attribute) {
            value = getAttributeValue(table, ((Attribute) cond.left).attrName, row);
            literal = cond.right;
        } else {
            literal = cond.left;
            value = getAttributeValue(table, ((Attribute) cond.right).attrName, row);
        }

        switch (op){
            case EQUALS:
                return value.equals(literal);
            case NOT_EQUALS:
                return !value.equals(literal);
            case LESS_EQ:
                return (int) value <= (int) literal;
            case LESS:
                return (int) value < (int) literal;
            case GREATER_EQ:
                return (int) value >= (int) literal;
            case GREATER:
                return (int) value > (int) literal;
            default:
                // Probably throw an error here
                break;
        }

        return false; // base case?
    }

    private static Object getAttributeValue(TableRootNode table, String attributeName, RowNode row) {
        int attrIndex = table.getAttributeWithName(attributeName).index;

        return row.getDataField(attrIndex);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Condition)) {
            return false;
        }

        Condition other = (Condition) obj;

        return op == other.op && left.equals(other.left) && right.equals(other.right);
    }
}
