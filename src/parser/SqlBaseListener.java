package parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.antlr.*;
import dbms.*;

import java.util.*;

public class SqlBaseListener extends SQLGrammarBaseListener {
    private IDbms dbms;

    private ArrayDeque<String> relationNames; // Table names, this could probably be a stack
    private ArrayDeque<Type> types; // Only used for CREATE

    // Don't need anymore

    public SqlBaseListener(IDbms db) {
        this.dbms = db;
        relationNames = new ArrayDeque<>();
        types = new ArrayDeque<>();
    }

    @Override public void enterQuery(SQLGrammarParser.QueryContext ctx) { }
    @Override public void exitQuery(SQLGrammarParser.QueryContext ctx) { }

    // @Override public void enterCreate_cmd(SQLGrammarParser.Create_cmdContext ctx) { }
    @Override public void exitCreate_cmd(SQLGrammarParser.Create_cmdContext ctx) {
        List<ParseTree> children = ctx.children;
        printChildren(children);

        String tableName = ctx.children.get(1).getText();

        List<String> primaryKeys = parseArguments(children.get(7).getText());

        List<String> columnNames = parseColumnNames(children.get(3));
        List<Type> columnTypes = parseColumnTypes(children.get(3));

        // Pass all of these values to Dbms
        dbms.createTable(tableName, columnNames, columnTypes, primaryKeys);
    }

    @Override public void exitInsert_cmd(SQLGrammarParser.Insert_cmdContext ctx) {
        // Remove first or remove last? Sometimes it'll be the same
        String tableInsertInto = relationNames.removeFirst(); // Table name we're inserting values into
        printChildren(ctx.children);

        String insertType = ctx.children.get(2).getText();
        if(insertType.equals("VALUES FROM RELATION")) {
            String tableInsertFrom = relationNames.removeFirst(); // Table we're inserting values from. Can be a temporary table

            dbms.insertFromRelation(tableInsertInto, tableInsertFrom);
        } else { // We're just entering values from a list of literals
            List<Object> valuesToInsert = parseLiterals(ctx.children.get(4));

            dbms.insertFromValues(tableInsertInto, valuesToInsert);
        }
    }

    @Override public void exitShow_cmd(SQLGrammarParser.Show_cmdContext ctx) { }
    @Override public void exitUpdate_cmd(SQLGrammarParser.Update_cmdContext ctx) { }
    @Override public void exitDelete_cmd(SQLGrammarParser.Delete_cmdContext ctx) { }

    // Return a string
    @Override public void exitLiteral(SQLGrammarParser.LiteralContext ctx) {
        String text = ctx.getText();
        Object obj = text;

        if(text.substring(0, 1).matches("[0-9]")) {
            obj = Integer.parseInt(text);
        }

        // literals.addLast(obj);
    }

    @Override public void exitRelation_name(SQLGrammarParser.Relation_nameContext ctx) {
        // What the hell do we do with relation name
        // System.out.println("Exit relation name");
        // String name = ctx.children.get(0).getText();
        String name = ctx.getText();
        relationNames.addLast(name);
    }

    // Don't actually need
    @Override public void exitAttribute_name(SQLGrammarParser.Attribute_nameContext ctx) {
        String name = ctx.getText();
        // String name = ctx.children.get(0).getText();
        // attributeNames.addLast(name);
    }

    @Override public void enterOperand(SQLGrammarParser.OperandContext ctx) { }
    @Override public void exitOperand(SQLGrammarParser.OperandContext ctx) { }

    // Void?
    @Override public void exitOpen_cmd(SQLGrammarParser.Open_cmdContext ctx) { }
    @Override public void exitClose_cmd(SQLGrammarParser.Close_cmdContext ctx) { }
    @Override public void exitWrite_cmd(SQLGrammarParser.Write_cmdContext ctx) { }
    @Override public void exitExit_cmd(SQLGrammarParser.Exit_cmdContext ctx) { }

    @Override public void exitCondition(SQLGrammarParser.ConditionContext ctx) { }
    @Override public void exitConjunction(SQLGrammarParser.ConjunctionContext ctx) { }
    @Override public void exitComparison(SQLGrammarParser.ComparisonContext ctx) { }
    @Override public void exitExpr(SQLGrammarParser.ExprContext ctx) { }
    @Override public void exitAtomic_expr(SQLGrammarParser.Atomic_exprContext ctx) { }

    @Override public void exitSelection(SQLGrammarParser.SelectionContext ctx) {
        // Do something with the resulting expression
        String tableFrom = relationNames.removeLast(); // Get the table we're getting values from?

        String tempTable = "tempTableName";

        relationNames.addLast(tempTable);
    }

    // Could call something on enterProjection to see how many attributes are in it before we enter it?
    // That way we know how much to get out
    @Override public void exitProjection(SQLGrammarParser.ProjectionContext ctx) {
        List<String> columnNames = new ArrayList<>();
        List<ParseTree> children = ctx.children;

        // How many attributes do we remove? Could there ever be more attributes in the stack that aren't for projection here?
        // Remove first or remove last?

        String tableFrom = children.get(4).getText();

        // Pass in the parameters to project, and it'll return a table name for a temporary table
        String tempTable = "tempTableName"; // = dbms.project(parameters)

        // Insert that temporary table name into relationName for whatever to use
        relationNames.addLast(tempTable); // Insert it into place
    }

    @Override public void exitRenaming(SQLGrammarParser.RenamingContext ctx) { }

    // All return a table
    @Override public void exitUnion(SQLGrammarParser.UnionContext ctx) { }
    @Override public void exitDifference(SQLGrammarParser.DifferenceContext ctx) { }
    @Override public void exitProduct(SQLGrammarParser.ProductContext ctx) { }

    // Given { "Joe", "hello", 5 }, returns an array containing them
    private List<Object> parseLiterals(ParseTree tree) {
        List<Object> ret = new ArrayList<>();
        for(int i = 0; i < tree.getChildCount(); i+=2) { // Skip over commas
            String literal = tree.getChild(i).getText();

            Object obj;
            if(literal.contains("\"")) { // Then a string
                obj = literal.substring(1, literal.length() - 1); // Remove the quotes

            } else {
                obj = Integer.parseInt(literal);
            }

            ret.add(obj);
        }

        return ret;
    }

    // Given like nameVARCHAR(20), kindINTEGER, returns [name, kind]
    private List<String> parseColumnNames(ParseTree tree) {
        List<String> ret = new ArrayList<>();
        for(int i = 0; i < tree.getChildCount(); i+=3) {
            ParseTree child = tree.getChild(i);

            ret.add(child.getText());
        }

        return ret;
    }

    private List<Type> parseColumnTypes(ParseTree tree) {
        List<Type> ret = new ArrayList<>();
        for(int i = 1; i < tree.getChildCount(); i+= 3) {
            ParseTree typeTree = tree.getChild(i);
            String typeName = typeTree.getChild(0).getText();

            Type type;
            if(typeName.equals("VARCHAR")) {
                int size = Integer.parseInt(typeTree.getChild(2).getText());

                type = new Varchar(size);
            } else { // Integer
                type = new IntType();
            }

            ret.add(type);
        }

        return ret;
    }

    // Given like "name,kind,etc" returns a list like ["name", "kind", "etc"]
    public List<String> parseArguments(String arg) {
        return new ArrayList<String>(
                Arrays.asList(arg.split(","))
        );
    }

    private void printQueue(ArrayDeque<String> toPrint) {
        for(String o : toPrint) {
            System.out.println(o);
        }
    }

    private void printChildren(List<ParseTree> children) {
        for(int i = 0; i < children.size(); i++) {
            ParseTree child = children.get(i);
            System.out.println(i + " " + child.getText());
        }
    }

    private void printChildren(ParseTree tree) {
        for(int i = 0; i < tree.getChildCount(); i++) {
            System.out.println(i + " " + tree.getChild(i).getText());
        }
    }
}
