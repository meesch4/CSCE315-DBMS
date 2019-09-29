package parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import parser.antlr.*;
import dbms.*;

import java.util.List;

// From following the "Guide - ANTLR4 Listener Setup" here: https://docs.google.com/document/d/1kCv5ODfAh8HZZ8uugMLcqMqJLvs9RQDmhvBGt3l2Fzo/edit
public class SqlBaseListener extends SQLGrammarBaseListener {
    private Dbms dbms;

    public SqlBaseListener() {
        this.dbms = new Dbms();
    }

    @Override public void enterCommand(SQLGrammarParser.CommandContext ctx) { }
    @Override public void exitCommand(SQLGrammarParser.CommandContext ctx) { }

    @Override public void enterQuery(SQLGrammarParser.QueryContext ctx) { }
    @Override public void exitQuery(SQLGrammarParser.QueryContext ctx) { }

    @Override public void enterCreate_cmd(SQLGrammarParser.Create_cmdContext ctx) {
        List<ParseTree> children = ctx.children;

        for(ParseTree child : children) {
            System.out.println(child.getText());
        }

        // String relation_name = children.get(0).getText();

    }
    @Override public void exitCreate_cmd(SQLGrammarParser.Create_cmdContext ctx) { }
    @Override public void enterShow_cmd(SQLGrammarParser.Show_cmdContext ctx) { }
    @Override public void exitShow_cmd(SQLGrammarParser.Show_cmdContext ctx) { }
    @Override public void enterUpdate_cmd(SQLGrammarParser.Update_cmdContext ctx) { }
    @Override public void exitUpdate_cmd(SQLGrammarParser.Update_cmdContext ctx) { }
    @Override public void enterInsert_cmd(SQLGrammarParser.Insert_cmdContext ctx) { }
    @Override public void exitInsert_cmd(SQLGrammarParser.Insert_cmdContext ctx) { }
    @Override public void enterDelete_cmd(SQLGrammarParser.Delete_cmdContext ctx) { }
    @Override public void exitDelete_cmd(SQLGrammarParser.Delete_cmdContext ctx) { }

    // Should return an integer
    @Override public void enterInteger(SQLGrammarParser.IntegerContext ctx) { }
    @Override public void exitInteger(SQLGrammarParser.IntegerContext ctx) { }

    // Return a string
    @Override public void enterIdentifier(SQLGrammarParser.IdentifierContext ctx) { }
    @Override public void exitIdentifier(SQLGrammarParser.IdentifierContext ctx) { }
    @Override public void enterString_literal(SQLGrammarParser.String_literalContext ctx) { }
    @Override public void exitString_literal(SQLGrammarParser.String_literalContext ctx) { }
    @Override public void enterLiteral(SQLGrammarParser.LiteralContext ctx) { }
    @Override public void exitLiteral(SQLGrammarParser.LiteralContext ctx) { }

    // Probably return a String
    @Override public void enterRelation_name(SQLGrammarParser.Relation_nameContext ctx) { }
    @Override public void exitRelation_name(SQLGrammarParser.Relation_nameContext ctx) { }
    @Override public void enterAttribute_name(SQLGrammarParser.Attribute_nameContext ctx) { }
    @Override public void exitAttribute_name(SQLGrammarParser.Attribute_nameContext ctx) { }

    @Override public void enterOperand(SQLGrammarParser.OperandContext ctx) { }
    @Override public void exitOperand(SQLGrammarParser.OperandContext ctx) { }

    // Return a Type, either Varchar or Integer
    @Override public void enterType(SQLGrammarParser.TypeContext ctx) { }
    @Override public void exitType(SQLGrammarParser.TypeContext ctx) { }

    // Return
    @Override public void enterAttribute_list(SQLGrammarParser.Attribute_listContext ctx) { }
    @Override public void exitAttribute_list(SQLGrammarParser.Attribute_listContext ctx) { }

    // Return a List<Type>
    @Override public void enterTyped_attribute_list(SQLGrammarParser.Typed_attribute_listContext ctx) { }
    @Override public void exitTyped_attribute_list(SQLGrammarParser.Typed_attribute_listContext ctx) { }

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
