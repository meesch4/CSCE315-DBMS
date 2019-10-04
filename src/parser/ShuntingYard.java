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
            if (parsed_leaves.get(i).equals("(")) {
                op_stack.push(parsed_leaves.get(i)); // push initial paren into op_stack
            }
            else if (parsed_leaves.get(i).equals(")")) {
                // pop off stack until closing paren is found and pop that off too
                while (!op_stack.empty() && !op_stack.peek().equals('(')) {
                    // pop off all stuff in between '(' and ')'
                    // parens not pushed into final stack
                    post_fixed.push(op_stack.pop());
                }
                if (!op_stack.empty()) {
                    op_stack.pop(); // popping off last paren '(' at bottom of stack
                }
            }
            else if (is_op(parsed_leaves.get(i))) {
                if (is_condition(op_stack.peek())) { // if operator on top of stack is a condition it takes precedence over operators and will be popped to post_fixed
                    post_fixed.push(op_stack.pop());
                }
                op_stack.push(parsed_leaves.get(i)); // operator is pushed after precedence is handled
            }
            else if (is_condition(parsed_leaves.get(i))) {
                op_stack.push(parsed_leaves.get(i)); // condition pushed to stack
            }
            else {
                // if element is not an operator or a condition it is a relation or table name and is inserted into post_fix by default
                post_fixed.push(parsed_leaves.get(i));
            }
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
            || input instanceof SQLGrammarParser.OrContext || input instanceof SQLGrammarParser.AndContext) {
            parsed_leaves.add(input.getText());

            return;
        } else {
            for (int i = 0; i < input.getChildCount(); i++) {
                find_leaves(input.getChild(i), parsed_leaves);
            }
        }
    }

    public static Condition create_condition(Stack<String> post_fixed) {
        Condition condition = null;

        return condition;
    }

    /** Helper functions **/
    public static Operator parseOperator(String opStr) {
        switch(opStr) {
            case "==":
                return Operator.EQUALS;
            case "!=":
                return Operator.NOT_EQUALS;
            case "<=":
                return Operator.LESS_EQ;
            case "<":
                return Operator.LESS;
            case ">=":
                return Operator.GREATER_EQ;
            case ">":
                return Operator.GREATER;
            case "&&":
                return Operator.AND;
            case "||":
                return Operator.OR;
        }

        return Operator.EQUALS;
    }

    // Could be a relationName, String literal, or
    public static Object parseLiteral(String str) {
        char firstChar = str.charAt(0); // Check str length?

        if(firstChar == '\"') // String literal
            // Shave off first and last characters(The quotes)
            return str.substring(1, str.length() - 1);
        else if((firstChar + "").matches("[0..9]"))
            return Integer.parseInt(str);
        else
            return new Attribute(str);
    }
}
