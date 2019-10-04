package parser;

import dbms.Attribute;
import dbms.Condition;
import dbms.Operator;
import org.antlr.v4.runtime.tree.ParseTree;
import parser.antlr.SQLGrammarParser;

import java.util.*;
/**
 * Contains static methods that given a string, returns a Condition object
 */
public class ShuntingYard {
    /**
     *  Given an text input, creates ShuntingYard object containing a postfix stack after performing the shunting-yard algorithm
     *  Takes in parse tree as input and returns post_fixed stack
     *  Requires a ShuntingYard object to be created for internal data structures
     */

    public ShuntingYard() {
        // Empty constructor
    }

    public static Condition evaluate(ParseTree input) {
        List<String> parsed_leaves = new ArrayList<>(); // Assigned by find_leaves, as its passed by reference
        find_leaves(input, parsed_leaves); // fills parsed_leaves
        Stack<String> post_fixed = build_stack(parsed_leaves); // fills post_fixed stack

        return create_condition(post_fixed);
    }

    private static Stack<String> build_stack(List<String> parsed_leaves) {
        // Comparisons have precedence over operators
        Stack<String> post_fixed = new Stack<>();
        Stack<String> op_stack = new Stack<String>(); // single operator stack*
        // * used because only comparisons and operators need to be pushed to op_stack, relation_names can go directly to post_fixed

        for (int i = 0; i < parsed_leaves.size(); i++) {
            String parsedVal = parsed_leaves.get(i);

            if (parsedVal.equals("(")) {
                op_stack.push(parsedVal); // push initial paren into op_stack
            }
            else if (parsedVal.equals(")")) {
                // pop off stack until closing paren is found and pop that off too
                while (!op_stack.empty() && !op_stack.peek().equals("(")) {
                    // pop off all stuff in between '(' and ')'
                    // parens not pushed into final stack
                    post_fixed.push(op_stack.pop());
                }
                if (!op_stack.empty()) {
                    op_stack.pop(); // popping off last paren '(' at bottom of stack
                }
            }
            else if (is_op(parsedVal)) {
                if (is_condition(op_stack.peek())) { // if operator on top of stack is a condition it takes precedence over operators and will be popped to post_fixed
                    post_fixed.push(op_stack.pop());
                }
                op_stack.push(parsedVal); // operator is pushed after precedence is handled
            }
            else if (is_condition(parsedVal)) {
                op_stack.push(parsedVal); // condition pushed to stack
            }
            else {
                // if element is not an operator or a condition it is a relation or table name and is inserted into post_fix by default
                post_fixed.push(parsedVal);
            }
        }

        // TODO: Remove comment after checking it's right/wrong @Neil
        // @Gannon this is necessary because when passing condition.ctx into ShuntingYard the parens in ( condition ) are not included when passing ctx.getChildren(2)
        // if we can determine a way to include the parens for condition itself we wouldn't need this since everything between paired parens would be popped
        while(!op_stack.empty()) {
            post_fixed.push(op_stack.pop());
        }
        return post_fixed;
    }

    private static boolean is_op (String possible_op) {
        if (possible_op.equals("||") || possible_op.equals("&&") || possible_op.equals("*")
                || possible_op.equals("+") || possible_op.equals("-") || possible_op.equals("&")) {
            return true;
        }
        return false;
    }


    private static boolean is_condition(String possible_cond) {
        if (possible_cond.equals("==") || possible_cond.equals("!=") || possible_cond.equals(">=")
                || possible_cond.equals("<=") || possible_cond.equals(">") || possible_cond.equals("<")) {
            return true;
        }
        return false;
    }

    // Recursively finds all leaves in tree and appends to an arrayList in ShuntingYard class
    public static void find_leaves(ParseTree input, List<String> parsed_leaves) {
        String text = input.getText();

        if (input instanceof SQLGrammarParser.OperandContext || input instanceof SQLGrammarParser.OperatorContext
                || input instanceof SQLGrammarParser.OrContext || input instanceof SQLGrammarParser.AndContext
                || input instanceof SQLGrammarParser.ParencloseContext || input instanceof SQLGrammarParser.ParenopenContext) {
            parsed_leaves.add(input.getText());
            return;
        } else {
            for (int i = 0; i < input.getChildCount(); i++) {
                find_leaves(input.getChild(i), parsed_leaves);
            }
        }
    }

    // Given a post fixed stack, create the according condition
    public static Condition create_condition(Stack<String> post_fixed) {
        Condition root = new Condition();
        Object left = null;
        Object right = null;

        // Honestly really just brainstorming right now, not positive what to do here
        // Should push the conditions we make into a stack?
        Stack<Condition> conditions = new Stack<>();

        Condition curr = root;
        while(!post_fixed.empty()) {
            String val = post_fixed.pop();

            if(is_op(val) || is_condition(val)) { // any operator should be handled the same
                // What do we do now?
                Operator op = parse_operator(val);
                // If curr already has an op, create another one(which will curr's child)
                if(curr.op != null) {
                    curr = new Condition();
                }

                conditions.push(curr);
                curr.op = op;

            } else { // relationName, or varchar/int
                Object literal_or_relation = parse_literal(val); // Terrible name
                if(curr.right == null) {
                    curr.right = literal_or_relation;
                } else if(curr.left == null) {
                    curr.left = literal_or_relation; // WE've completed curr

                    conditions.pop();
                    if(!conditions.empty()) {
                        Condition topOfStack = conditions.peek();

                        if(topOfStack.right == null) {
                            topOfStack.right = curr;
                        } else if(topOfStack.left == null) {
                            topOfStack.left = curr; // We've completed topOfStack

                            conditions.pop();
                        }
                    }
                }
            }
        }

        return root;
    }

    // Delete this after we complete create_condition
    /*
    If the condition is: (kind == "cat" || (kind == "dog" && age > 5)), this is what the Condition object should look like
    // In order, the condition objects are formed from the bottom condition(age > 5) upwards, hence the use of a stack
        condition: {
            op: ||
            left: { // Nested condition
                left: kind
                right: "cat"
                op: ==
            }
            right: { // Nested condition
                operator: &&
                left: { // Another nested condition
                    operator: ==
                    left: kind
                    right: dog
                }
                right: {
                    operator: >
                    left: age
                    right: 5
                },
            }
       }

     */

    /** Helper functions **/
    // Parses the according operator string into the Operator enum
    private static Operator parse_operator(String opStr) {
        switch(opStr) {
            case "==": return Operator.EQUALS;
            case "!=": return Operator.NOT_EQUALS;
            case "<=": return Operator.LESS_EQ;
            case "<":  return Operator.LESS;
            case ">=": return Operator.GREATER_EQ;
            case ">":  return Operator.GREATER;
            case "&&": return Operator.AND;
            case "||": return Operator.OR;
        }

        return Operator.EQUALS;
    }

    // Could be a relationName, String literal, or
    private static Object parse_literal(String str) {
        char first_char = str.charAt(0); // Check str length?

        if(first_char == '\"') // String literal
            // Shave off first and last characters(The quotes)
            return str.substring(1, str.length() - 1);
        else if((first_char + "").matches("[0..9]"))
            return Integer.parseInt(str);
        else
            return new Attribute(str);
    }
}
