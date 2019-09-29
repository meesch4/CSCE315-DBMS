package parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.antlr.*;
import dbms.*;

import java.util.*;

// From following the "Guide - ANTLR4 Listener Setup" here: https://docs.google.com/document/d/1kCv5ODfAh8HZZ8uugMLcqMqJLvs9RQDmhvBGt3l2Fzo/edit
public class SqlBaseListener extends SQLGrammarBaseListener {
    private Dbms dbms;

    private ArrayDeque<String> relationNames; // Table names
    private ArrayDeque<String> attributeNames; // Variable names, column names, etc
    private ArrayDeque<Type> types; // Only used for CREATE
    private ArrayDeque<Object> literals; // Can be strings or numbers

    public SqlBaseListener() {
        this.dbms = new Dbms();
        relationNames = new ArrayDeque<>();
        attributeNames = new ArrayDeque<>();
        types = new ArrayDeque<>();
        literals = new ArrayDeque<>();
    }

    @Override public void enterQuery(SQLGrammarParser.QueryContext ctx) { }
    @Override public void exitQuery(SQLGrammarParser.QueryContext ctx) { }

    // @Override public void enterCreate_cmd(SQLGrammarParser.Create_cmdContext ctx) { }
    @Override public void exitCreate_cmd(SQLGrammarParser.Create_cmdContext ctx) {
        String tableName = ctx.children.get(1).getText();
        System.out.println(tableName);

        List<String> primaryKeys = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();
        List<Type> columnTypes = new ArrayList<>();

        int i = 0;
        while(!types.isEmpty()) { // Get the columns and their according types
            columnNames.add(attributeNames.removeFirst());
            columnTypes.add(types.removeFirst());
            // System.out.println("Colummn name " + columnNames.get(i++));
        }

        i = 0;
        while(!attributeNames.isEmpty()) {
            primaryKeys.add(attributeNames.removeFirst());
            // System.out.println("Primary Key " + primaryKeys.get(i++));
        }

        // Pass all of these values to Dbms
    }

    @Override public void exitInsert_cmd(SQLGrammarParser.Insert_cmdContext ctx) {
        String tableToInsert = relationNames.removeFirst();

        String insertType = ctx.children.get(2).getText();
        if(insertType.equals("VALUES FROM RELATION")) {
            // Get the table we're going to get values from

        } else { // We're just entering values from a list of literals

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

        literals.addLast(obj);
    }

    @Override public void exitRelation_name(SQLGrammarParser.Relation_nameContext ctx) {
        // What the hell do we do with relation name
        System.out.println("Exit relation name");
        // oString name = ctx.children.get(0).getText();
        String name = ctx.getText();
        relationNames.addLast(name);
    }

    @Override public void exitAttribute_name(SQLGrammarParser.Attribute_nameContext ctx) {
        String name = ctx.getText();
        // String name = ctx.children.get(0).getText();
        attributeNames.addLast(name);
    }

    @Override public void enterOperand(SQLGrammarParser.OperandContext ctx) { }
    @Override public void exitOperand(SQLGrammarParser.OperandContext ctx) { }

    // Return a Type, either Varchar or Integer
    @Override public void enterType(SQLGrammarParser.TypeContext ctx) {
        // Check whether it's a varchar or integer
        List<ParseTree> children = ctx.children;
        String typeName = children.get(0).getText();
        Type type;

        if(typeName.equals("VARCHAR")) {
            int size = Integer.parseInt(children.get(2).getText());

            type = new Varchar(size);
        } else { // Integer
            type = new IntType();
        }

        types.addLast(type);
    }
    @Override public void exitType(SQLGrammarParser.TypeContext ctx) { }

    // Return a List<Type>
    @Override public void enterTyped_attribute_list(SQLGrammarParser.Typed_attribute_listContext ctx) { }
    @Override public void exitTyped_attribute_list(SQLGrammarParser.Typed_attribute_listContext ctx) {
        // Get all of the relations & types, then get them as a list
    }

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
    @Override public void exitSelection(SQLGrammarParser.SelectionContext ctx) { }
    @Override public void exitProjection(SQLGrammarParser.ProjectionContext ctx) { }

    // Void?
    @Override public void exitRenaming(SQLGrammarParser.RenamingContext ctx) { }

    // Probably returns a Table
    @Override public void exitUnion(SQLGrammarParser.UnionContext ctx) { }
    @Override public void exitDifference(SQLGrammarParser.DifferenceContext ctx) { }
    @Override public void exitProduct(SQLGrammarParser.ProductContext ctx) { }

    @Override public void exitProgram(SQLGrammarParser.ProgramContext ctx) { }
    @Override public void exitEveryRule(ParserRuleContext ctx) { }
    @Override public void visitTerminal(TerminalNode node) { }
    @Override public void visitErrorNode(ErrorNode node) { }
}
