package parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.antlr.*;
import dbms.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// From following the "Guide - ANTLR4 Listener Setup" here: https://docs.google.com/document/d/1kCv5ODfAh8HZZ8uugMLcqMqJLvs9RQDmhvBGt3l2Fzo/edit
public class SqlBaseListener extends SQLGrammarBaseListener {
    private Dbms dbms;

    private Stack<String> relationNames; // Table names
    private Stack<String> attributeNames; // Variable names, column names, etc
    private Stack<Type> types; // Only used for CREATE
    private Stack<Object> literals; // Can be strings or numbers

    public SqlBaseListener() {
        this.dbms = new Dbms();
        relationNames = new Stack();
        attributeNames = new Stack();
        types = new Stack();
        literals = new Stack();
    }

    @Override public void enterCommand(SQLGrammarParser.CommandContext ctx) { }
    @Override public void exitCommand(SQLGrammarParser.CommandContext ctx) { }

    @Override public void enterQuery(SQLGrammarParser.QueryContext ctx) { }
    @Override public void exitQuery(SQLGrammarParser.QueryContext ctx) { }

    // @Override public void enterCreate_cmd(SQLGrammarParser.Create_cmdContext ctx) { }
    @Override public void exitCreate_cmd(SQLGrammarParser.Create_cmdContext ctx) {
        String tableName = relationNames.pop();
        List<String> primaryKeys = new ArrayList<>();
        int primaryKeyCount = attributeNames.size() - types.size(); // How many primary keys there actually are
        for(int i = 0; i < primaryKeyCount; i++) {
            primaryKeys.add(attributeNames.pop());
        }

        List<String> columnNames = new ArrayList<>();
        List<Type> columnTypes = new ArrayList<>();
        while(types.empty()) { // Get the columns and their according types
            columnNames.add(attributeNames.pop());
            columnTypes.add(types.pop());
        }

        // Pass all of these values to Dbms
    }
    @Override public void exitShow_cmd(SQLGrammarParser.Show_cmdContext ctx) { }
    @Override public void exitUpdate_cmd(SQLGrammarParser.Update_cmdContext ctx) { }
    @Override public void exitInsert_cmd(SQLGrammarParser.Insert_cmdContext ctx) {
        String tableToInsert = relationNames.pop();
    }
    @Override public void exitDelete_cmd(SQLGrammarParser.Delete_cmdContext ctx) { }

    // Should return an integer
    @Override public void exitInteger(SQLGrammarParser.IntegerContext ctx) { }

    // Return a string
    @Override public void exitIdentifier(SQLGrammarParser.IdentifierContext ctx) { }
    @Override public void exitString_literal(SQLGrammarParser.String_literalContext ctx) { }
    @Override public void exitLiteral(SQLGrammarParser.LiteralContext ctx) { }

    @Override public void exitRelation_name(SQLGrammarParser.Relation_nameContext ctx) {
        // What the hell do we do with relation name
        System.out.println("Exit relation name");
        String name = ctx.children.get(0).getText();
        attributeNames.push(name);
    }

    @Override public void exitAttribute_name(SQLGrammarParser.Attribute_nameContext ctx) {
        String name = ctx.children.get(0).getText();
        relationNames.push(name);
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

        types.push(type);
    }
    @Override public void exitType(SQLGrammarParser.TypeContext ctx) { }

    // Return a List<Type>
    @Override public void enterTyped_attribute_list(SQLGrammarParser.Typed_attribute_listContext ctx) { }
    @Override public void exitTyped_attribute_list(SQLGrammarParser.Typed_attribute_listContext ctx) {
        // Get all of the relations & types, then get them as a list
    }

    // Void?
    @Override public void enterOpen_cmd(SQLGrammarParser.Open_cmdContext ctx) { }
    @Override public void exitOpen_cmd(SQLGrammarParser.Open_cmdContext ctx) { }
    @Override public void enterClose_cmd(SQLGrammarParser.Close_cmdContext ctx) { }
    @Override public void exitClose_cmd(SQLGrammarParser.Close_cmdContext ctx) { }
    @Override public void enterWrite_cmd(SQLGrammarParser.Write_cmdContext ctx) { }
    @Override public void exitWrite_cmd(SQLGrammarParser.Write_cmdContext ctx) { }
    @Override public void enterExit_cmd(SQLGrammarParser.Exit_cmdContext ctx) { }
    @Override public void exitExit_cmd(SQLGrammarParser.Exit_cmdContext ctx) { }

    @Override public void enterCondition(SQLGrammarParser.ConditionContext ctx) { }
    @Override public void exitCondition(SQLGrammarParser.ConditionContext ctx) { }
    @Override public void enterConjunction(SQLGrammarParser.ConjunctionContext ctx) { }
    @Override public void exitConjunction(SQLGrammarParser.ConjunctionContext ctx) { }
    @Override public void enterComparison(SQLGrammarParser.ComparisonContext ctx) { }
    @Override public void exitComparison(SQLGrammarParser.ComparisonContext ctx) { }
    @Override public void enterExpr(SQLGrammarParser.ExprContext ctx) { }
    @Override public void exitExpr(SQLGrammarParser.ExprContext ctx) { }
    @Override public void enterAtomic_expr(SQLGrammarParser.Atomic_exprContext ctx) { }
    @Override public void exitAtomic_expr(SQLGrammarParser.Atomic_exprContext ctx) { }
    @Override public void enterSelection(SQLGrammarParser.SelectionContext ctx) { }
    @Override public void exitSelection(SQLGrammarParser.SelectionContext ctx) { }
    @Override public void enterProjection(SQLGrammarParser.ProjectionContext ctx) { }
    @Override public void exitProjection(SQLGrammarParser.ProjectionContext ctx) { }

    // Void?
    @Override public void enterRenaming(SQLGrammarParser.RenamingContext ctx) { }
    @Override public void exitRenaming(SQLGrammarParser.RenamingContext ctx) { }

    // Probably returns a Table
    @Override public void enterUnion(SQLGrammarParser.UnionContext ctx) { }
    @Override public void exitUnion(SQLGrammarParser.UnionContext ctx) { }
    @Override public void enterDifference(SQLGrammarParser.DifferenceContext ctx) { }
    @Override public void exitDifference(SQLGrammarParser.DifferenceContext ctx) { }
    @Override public void enterProduct(SQLGrammarParser.ProductContext ctx) { }
    @Override public void exitProduct(SQLGrammarParser.ProductContext ctx) { }

    @Override public void enterProgram(SQLGrammarParser.ProgramContext ctx) { }
    @Override public void exitProgram(SQLGrammarParser.ProgramContext ctx) { }
    @Override public void enterEveryRule(ParserRuleContext ctx) { }
    @Override public void exitEveryRule(ParserRuleContext ctx) { }
    @Override public void visitTerminal(TerminalNode node) { }
    @Override public void visitErrorNode(ErrorNode node) { }
}
