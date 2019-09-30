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

    // Don't need anymore

    public SqlBaseListener(IDbms db) {
        this.dbms = db;
        relationNames = new ArrayDeque<>();
    }


    // @Override public void enterCreate_cmd(SQLGrammarParser.Create_cmdContext ctx) { }
    @Override public void exitCreate_cmd(SQLGrammarParser.Create_cmdContext ctx) {
        List<ParseTree> children = ctx.children;
        printChildren(children);

        // String tableName = ctx.children.get(1).getText();
        String tableName = relationNames.removeFirst(); // Not sure which we need to do

        List<String> primaryKeys = parseArguments(children.get(7).getText());

        List<String> columnNames = parseColumnNames(children.get(3));
        List<Type> columnTypes = parseColumnTypes(children.get(3));

        // Pass all of these values to Dbms
        dbms.createTable(tableName, columnNames, columnTypes, primaryKeys);
    }

    @Override public void exitInsert_cmd(SQLGrammarParser.Insert_cmdContext ctx) {
        // Remove first or remove last? Sometimes it'll be the same
        String tableInsertInto = relationNames.removeFirst(); // Table name we're inserting values into

        String insertType = ctx.children.get(2).getText();
        if(insertType.equals("VALUES FROM RELATION")) {
            String tableInsertFrom = relationNames.removeFirst(); // Table we're inserting values from. Can be a temporary table

            dbms.insertFromRelation(tableInsertInto, tableInsertFrom);
        } else { // We're just entering values from a list of literals
            List<Object> valuesToInsert = parseLiterals(ctx.children.get(4));

            dbms.insertFromValues(tableInsertInto, valuesToInsert);
        }
    }

    @Override public void exitUpdate_cmd(SQLGrammarParser.Update_cmdContext ctx) { }

    // Should all be straightforward
    @Override public void exitShow_cmd(SQLGrammarParser.Show_cmdContext ctx) { }
    @Override public void exitDelete_cmd(SQLGrammarParser.Delete_cmdContext ctx) { }
    @Override public void exitOpen_cmd(SQLGrammarParser.Open_cmdContext ctx) { }
    @Override public void exitClose_cmd(SQLGrammarParser.Close_cmdContext ctx) { }
    @Override public void exitWrite_cmd(SQLGrammarParser.Write_cmdContext ctx) { }
    @Override public void exitExit_cmd(SQLGrammarParser.Exit_cmdContext ctx) { }

    @Override public void exitRelation_name(SQLGrammarParser.Relation_nameContext ctx) {
        // What the hell do we do with relation name
        // System.out.println("Exit relation name");
        // String name = ctx.children.get(0).getText();
        String name = ctx.getText();
        relationNames.addLast(name);
    }

    @Override public void enterQuery(SQLGrammarParser.QueryContext ctx) { }
    @Override public void exitQuery(SQLGrammarParser.QueryContext ctx) { }

    @Override public void exitSelection(SQLGrammarParser.SelectionContext ctx) {
        // Do something with the resulting expression
        String tableFrom = relationNames.removeLast(); // Get the table we're getting values from?

        String tempTable = "tempTableName";

        relationNames.addLast(tempTable);
    }

    // Could call something on enterProjection to see how many attributes are in it before we enter it?
    // That way we know how much to get out
    @Override public void exitProjection(SQLGrammarParser.ProjectionContext ctx) {
        List<String> columnNames = parseArguments(ctx.children.get(2).getText());

        String tableFrom = relationNames.removeLast();
        // String tableFrom = ctx.children.get(4).getText();

        // Pass in the parameters to project, and it'll return a table name for a temporary table
        String tempTable = dbms.projection(tableFrom, columnNames); // = dbms.project(parameters)

        // Insert that temporary table name into relationName for whatever to use
        relationNames.addLast(tempTable); // Insert it into place
    }

    @Override public void exitRenaming(SQLGrammarParser.RenamingContext ctx) { }

    @Override public void enterOperand(SQLGrammarParser.OperandContext ctx) { }
    @Override public void exitOperand(SQLGrammarParser.OperandContext ctx) { }

    // Void?

    @Override public void exitCondition(SQLGrammarParser.ConditionContext ctx) { }
    @Override public void exitConjunction(SQLGrammarParser.ConjunctionContext ctx) { }
    @Override public void exitComparison(SQLGrammarParser.ComparisonContext ctx) { }
    @Override public void exitExpr(SQLGrammarParser.ExprContext ctx) { }
    @Override public void exitAtomic_expr(SQLGrammarParser.Atomic_exprContext ctx) { }

    // All return a table
    @Override public void exitUnion(SQLGrammarParser.UnionContext ctx) {
        printChildren(ctx.children);

        String table2 = relationNames.removeLast();
        String table1 = relationNames.removeLast();

        String tempTable = dbms.union(table1, table2);

        relationNames.addLast(tempTable);
    }

    @Override public void exitDifference(SQLGrammarParser.DifferenceContext ctx) {
        String table2 = relationNames.removeLast();
        String table1 = relationNames.removeLast();

        String tempTable = dbms.union(table1, table2);

        relationNames.addLast(tempTable);
    }

    @Override public void exitProduct(SQLGrammarParser.ProductContext ctx) {
        String table2 = relationNames.removeLast();
        String table1 = relationNames.removeLast();

        String tempTable = dbms.union(table1, table2);

        relationNames.addLast(tempTable);
    }

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
    private List<String> parseArguments(String arg) {
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
