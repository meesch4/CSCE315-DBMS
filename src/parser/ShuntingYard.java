package parser;

import dbms.Condition;
import org.antlr.v4.runtime.tree.ParseTree;

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
    private static ArrayList<String> parsed_leaves;
    private static Stack<String> post_fixed;

    public ShuntingYard() {
        // Empty constructor
    }

    public static Stack<String> evaluate(ParseTree input) {
        find_leaves(input); // fills parsed_leaves
        build_stack(); // fills post_fixed stack
        return post_fixed;
    }

    private static void build_stack() {
        // Comparisons have precedence over operators
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
    private static void find_leaves(ParseTree input) {
        if (input.getChildCount() == 0) {
            parsed_leaves.add(input.getText());
            return;
        } else {
            for (int i = 0; i < input.getChildCount(); i++) {
                find_leaves(input.getChild(i));
            }
        }
    }
}

