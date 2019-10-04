package parser;

import dbms.Attribute;
import dbms.Condition;
import dbms.Operator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static org.junit.Assert.*;

public class ShuntingYardTests {
    @Test
    public void create_condition_doesCreateCorrectly1() {
        Stack<String> post_fix = createPostfix(1);

        Condition ret = ShuntingYard.create_condition(post_fix);
        Condition expected = expected(1);

        assertEquals(expected, ret);
    }

    @Test
    public void create_condition_doesCreateCorrectly2() {
        Stack<String> post_fix = createPostfix(2);

        Condition ret = ShuntingYard.create_condition(post_fix);
        Condition expected = expected(2);

        assertEquals(expected, ret);
    }

    private Condition expected(int which) {
        Condition root = new Condition();
        if(which == 1) { // kind == "cat" || kind == "dog"
            root.op = Operator.OR;
            Condition left = new Condition();
                left.op = Operator.EQUALS;
                left.left = new Attribute("kind");
                left.right = "cat";
            Condition right = new Condition();
                right.op = Operator.EQUALS;
                right.left = new Attribute("kind");
                right.right = "dog";

            root.left = left;
            root.right = right;
        } else if(which == 2) { // kind == "cat" || (kind == "dog" && age > 5))
            root.op = Operator.OR;
            Condition b = new Condition();
                b.op = Operator.AND;
                Condition c = new Condition();
                    c.op = Operator.GREATER;
                    c.left = new Attribute("age");
                    c.right = 5;
                Condition d = new Condition();
                    d.op = Operator.EQUALS;
                    d.left = new Attribute("kind");
                    d.right = "dog";
                b.left = d;
                b.right = c;
            Condition e = new Condition();
                e.op = Operator.EQUALS;
                e.left = new Attribute("kind");
                e.right = "cat";

            root.left = e;
            root.right = b;
        }

        return root;
    }

    private Stack<String> createPostfix(int which) {
        Stack<String> ret = new Stack<>();
        // kind == "cat" || kind == "dog"
        List<String> fill = new ArrayList<>();
        if (which == 1) {
            fill = new ArrayList<>(
                Arrays.asList(
                    "||", "==", "\"dog\"", "kind", "==", "\"cat\"", "kind"
                )
            );
        } else if(which == 2) {
            fill = new ArrayList<>(
                Arrays.asList(
                        "||", "&&", ">", "5", "age", "==", "\"dog\"", "kind", "==", "\"cat\"", "kind"
                )
            );
        }

        for (int i = fill.size() - 1; i >= 0; i--) {
            ret.push(fill.get(i));
        }

        return ret;
    }
}
