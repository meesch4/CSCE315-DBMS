package parser;

import types.IntType;
import types.Type;
import types.Varchar;
import org.antlr.v4.runtime.tree.ParseTree;
import parser.antlr.*;
import dbms.*;

import java.util.*;

public class SqlBaseListener extends SQLGrammarBaseListener {
    private IDbms dbms;

    private ArrayDeque<String> relationNames; // Used for both shunting-yard & for general combination of queries(i.e. project (...) (select (...) table))
    private Stack<Operator> operators;
    private Stack<String> operands; // Might be able to be a literal?

    // Don't need anymore

    public SqlBaseListener(IDbms db) {
        this.dbms = db;
        this.relationNames = new ArrayDeque<>();
        this.operators = new Stack<>();
        this.operands = new Stack<>();
    }


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
            List<Object> valuesToInsert = parseLiterals(ctx.children.get(4), 0, 2);

            dbms.insertFromValues(tableInsertInto, valuesToInsert);
        }
    }

    @Override public void exitUpdate_cmd(SQLGrammarParser.Update_cmdContext ctx) {
        printChildren(ctx.children);
        String tableName = relationNames.removeLast();

        // Parallel arrays
        List<String> columnsToSet = parseSetColumnNames(ctx.children.get(3));
        List<Object> literalsToSet = parseLiterals(ctx.children.get(3), 2, 4);

        // TODO: Implement ShuntingYard
        Stack<String> stack = ShuntingYard.evaluate(ctx.children.get(5));

        // dbms.update(tableName, columnsToSet, literalsToSet, condition);
    }

    // TODO: Implement Delete
    @Override public void exitDelete_cmd(SQLGrammarParser.Delete_cmdContext ctx) {
        String tableName = relationNames.removeFirst();

        String condition = ctx.children.get(3).getText(); // Parse this

        dbms.delete(tableName);
    }

    @Override public void exitShow_cmd(SQLGrammarParser.Show_cmdContext ctx) {
        String tableName = relationNames.removeFirst();
        dbms.show(tableName);
    }

    @Override public void exitOpen_cmd(SQLGrammarParser.Open_cmdContext ctx) {
        String tableName = relationNames.removeFirst();
        dbms.open(tableName);
    }

    @Override public void exitClose_cmd(SQLGrammarParser.Close_cmdContext ctx) {
        String tableName = relationNames.removeFirst();
        dbms.close(tableName);
    }

    @Override public void exitWrite_cmd(SQLGrammarParser.Write_cmdContext ctx) {
        String tableName = relationNames.removeFirst();
        dbms.write(tableName);
    }

    @Override public void exitExit_cmd(SQLGrammarParser.Exit_cmdContext ctx) {
        dbms.exit();
    }


    /********** QUERY METHODS ***********/

    @Override public void exitQuery(SQLGrammarParser.QueryContext ctx) {
        // If we're exiting the query, that means whatever expression that was on the right side of the <-
        // has already been calculated, thus we should have a (temp) table at the bottom of the queue
        String tempTable = relationNames.removeLast();

        String queryTableName = relationNames.removeLast(); // The table we're going to assign

        // TODO: Assign the tempTable variable's name/key to queryTableName
    }

    @Override public void exitSelection(SQLGrammarParser.SelectionContext ctx) {
        printChildren(ctx);
        // Do something with the resulting expression
        String tableFrom = relationNames.removeLast(); // Get the table we're getting values from?

        String tempTable = "tempTableName";

        ParseTree tree = ctx.children.get(2);
        Stack<String> stack = ShuntingYard.evaluate(tree);

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

    @Override public void exitRenaming(SQLGrammarParser.RenamingContext ctx) {
        List<String> newColumnNames = parseArguments(ctx.children.get(2).getText());

        String tableName = relationNames.removeLast();

        String tempTableName = dbms.rename(tableName, newColumnNames);

        relationNames.addLast(tempTableName);
    }

    // Don't think we need
    // @Override public void exitOperand(SQLGrammarParser.OperandContext ctx) { }

    @Override public void exitComparison(SQLGrammarParser.ComparisonContext ctx) { }


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


    @Override public void exitRelation_name(SQLGrammarParser.Relation_nameContext ctx) {
        String name = ctx.getText();
        relationNames.addLast(name);
    }


    // Given { "Joe", "hello", 5 }, returns an array containing them
    private List<Object> parseLiterals(ParseTree tree, int start, int offset) {
        List<Object> ret = new ArrayList<>();
        for(int i = start; i < tree.getChildCount(); i+=offset) { // Skip over commas
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

    private List<String> parseSetColumnNames(ParseTree tree) {
        List<String> ret = new ArrayList<>();
        printChildren(tree);

        for(int i = 0; i < tree.getChildCount(); i+=4)
            ret.add(tree.getChild(i).getText());

        return ret;
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
