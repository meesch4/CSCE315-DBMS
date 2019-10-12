// Generated from /Users/gannonprudomme/Documents/workspace/Java 315/src/parser/antlr/SQLGrammar.g4 by ANTLR 4.7.2
package parser.antlr;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SQLGrammarParser}.
 */
public interface SQLGrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void enterOperator(SQLGrammarParser.OperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#operator}.
	 * @param ctx the parse tree
	 */
	void exitOperator(SQLGrammarParser.OperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#integer}.
	 * @param ctx the parse tree
	 */
	void enterInteger(SQLGrammarParser.IntegerContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#integer}.
	 * @param ctx the parse tree
	 */
	void exitInteger(SQLGrammarParser.IntegerContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(SQLGrammarParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(SQLGrammarParser.IdentifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#string_literal}.
	 * @param ctx the parse tree
	 */
	void enterString_literal(SQLGrammarParser.String_literalContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#string_literal}.
	 * @param ctx the parse tree
	 */
	void exitString_literal(SQLGrammarParser.String_literalContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(SQLGrammarParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(SQLGrammarParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#relation_name}.
	 * @param ctx the parse tree
	 */
	void enterRelation_name(SQLGrammarParser.Relation_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#relation_name}.
	 * @param ctx the parse tree
	 */
	void exitRelation_name(SQLGrammarParser.Relation_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#attribute_name}.
	 * @param ctx the parse tree
	 */
	void enterAttribute_name(SQLGrammarParser.Attribute_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#attribute_name}.
	 * @param ctx the parse tree
	 */
	void exitAttribute_name(SQLGrammarParser.Attribute_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#operand}.
	 * @param ctx the parse tree
	 */
	void enterOperand(SQLGrammarParser.OperandContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#operand}.
	 * @param ctx the parse tree
	 */
	void exitOperand(SQLGrammarParser.OperandContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(SQLGrammarParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(SQLGrammarParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#literal_list}.
	 * @param ctx the parse tree
	 */
	void enterLiteral_list(SQLGrammarParser.Literal_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#literal_list}.
	 * @param ctx the parse tree
	 */
	void exitLiteral_list(SQLGrammarParser.Literal_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#attribute_list}.
	 * @param ctx the parse tree
	 */
	void enterAttribute_list(SQLGrammarParser.Attribute_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#attribute_list}.
	 * @param ctx the parse tree
	 */
	void exitAttribute_list(SQLGrammarParser.Attribute_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#typed_attribute_list}.
	 * @param ctx the parse tree
	 */
	void enterTyped_attribute_list(SQLGrammarParser.Typed_attribute_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#typed_attribute_list}.
	 * @param ctx the parse tree
	 */
	void exitTyped_attribute_list(SQLGrammarParser.Typed_attribute_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#open_cmd}.
	 * @param ctx the parse tree
	 */
	void enterOpen_cmd(SQLGrammarParser.Open_cmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#open_cmd}.
	 * @param ctx the parse tree
	 */
	void exitOpen_cmd(SQLGrammarParser.Open_cmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#close_cmd}.
	 * @param ctx the parse tree
	 */
	void enterClose_cmd(SQLGrammarParser.Close_cmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#close_cmd}.
	 * @param ctx the parse tree
	 */
	void exitClose_cmd(SQLGrammarParser.Close_cmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#write_cmd}.
	 * @param ctx the parse tree
	 */
	void enterWrite_cmd(SQLGrammarParser.Write_cmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#write_cmd}.
	 * @param ctx the parse tree
	 */
	void exitWrite_cmd(SQLGrammarParser.Write_cmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#exit_cmd}.
	 * @param ctx the parse tree
	 */
	void enterExit_cmd(SQLGrammarParser.Exit_cmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#exit_cmd}.
	 * @param ctx the parse tree
	 */
	void exitExit_cmd(SQLGrammarParser.Exit_cmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#or}.
	 * @param ctx the parse tree
	 */
	void enterOr(SQLGrammarParser.OrContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#or}.
	 * @param ctx the parse tree
	 */
	void exitOr(SQLGrammarParser.OrContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#and}.
	 * @param ctx the parse tree
	 */
	void enterAnd(SQLGrammarParser.AndContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#and}.
	 * @param ctx the parse tree
	 */
	void exitAnd(SQLGrammarParser.AndContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#parenopen}.
	 * @param ctx the parse tree
	 */
	void enterParenopen(SQLGrammarParser.ParenopenContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#parenopen}.
	 * @param ctx the parse tree
	 */
	void exitParenopen(SQLGrammarParser.ParenopenContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#parenclose}.
	 * @param ctx the parse tree
	 */
	void enterParenclose(SQLGrammarParser.ParencloseContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#parenclose}.
	 * @param ctx the parse tree
	 */
	void exitParenclose(SQLGrammarParser.ParencloseContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(SQLGrammarParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(SQLGrammarParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void enterConjunction(SQLGrammarParser.ConjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#conjunction}.
	 * @param ctx the parse tree
	 */
	void exitConjunction(SQLGrammarParser.ConjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(SQLGrammarParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(SQLGrammarParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(SQLGrammarParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(SQLGrammarParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#atomic_expr}.
	 * @param ctx the parse tree
	 */
	void enterAtomic_expr(SQLGrammarParser.Atomic_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#atomic_expr}.
	 * @param ctx the parse tree
	 */
	void exitAtomic_expr(SQLGrammarParser.Atomic_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#selection}.
	 * @param ctx the parse tree
	 */
	void enterSelection(SQLGrammarParser.SelectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#selection}.
	 * @param ctx the parse tree
	 */
	void exitSelection(SQLGrammarParser.SelectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#projection}.
	 * @param ctx the parse tree
	 */
	void enterProjection(SQLGrammarParser.ProjectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#projection}.
	 * @param ctx the parse tree
	 */
	void exitProjection(SQLGrammarParser.ProjectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#renaming}.
	 * @param ctx the parse tree
	 */
	void enterRenaming(SQLGrammarParser.RenamingContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#renaming}.
	 * @param ctx the parse tree
	 */
	void exitRenaming(SQLGrammarParser.RenamingContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#union}.
	 * @param ctx the parse tree
	 */
	void enterUnion(SQLGrammarParser.UnionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#union}.
	 * @param ctx the parse tree
	 */
	void exitUnion(SQLGrammarParser.UnionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#difference}.
	 * @param ctx the parse tree
	 */
	void enterDifference(SQLGrammarParser.DifferenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#difference}.
	 * @param ctx the parse tree
	 */
	void exitDifference(SQLGrammarParser.DifferenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#product}.
	 * @param ctx the parse tree
	 */
	void enterProduct(SQLGrammarParser.ProductContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#product}.
	 * @param ctx the parse tree
	 */
	void exitProduct(SQLGrammarParser.ProductContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#natural_join}.
	 * @param ctx the parse tree
	 */
	void enterNatural_join(SQLGrammarParser.Natural_joinContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#natural_join}.
	 * @param ctx the parse tree
	 */
	void exitNatural_join(SQLGrammarParser.Natural_joinContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#update_set_list}.
	 * @param ctx the parse tree
	 */
	void enterUpdate_set_list(SQLGrammarParser.Update_set_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#update_set_list}.
	 * @param ctx the parse tree
	 */
	void exitUpdate_set_list(SQLGrammarParser.Update_set_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#show_cmd}.
	 * @param ctx the parse tree
	 */
	void enterShow_cmd(SQLGrammarParser.Show_cmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#show_cmd}.
	 * @param ctx the parse tree
	 */
	void exitShow_cmd(SQLGrammarParser.Show_cmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#create_cmd}.
	 * @param ctx the parse tree
	 */
	void enterCreate_cmd(SQLGrammarParser.Create_cmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#create_cmd}.
	 * @param ctx the parse tree
	 */
	void exitCreate_cmd(SQLGrammarParser.Create_cmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#update_cmd}.
	 * @param ctx the parse tree
	 */
	void enterUpdate_cmd(SQLGrammarParser.Update_cmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#update_cmd}.
	 * @param ctx the parse tree
	 */
	void exitUpdate_cmd(SQLGrammarParser.Update_cmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#insert_cmd}.
	 * @param ctx the parse tree
	 */
	void enterInsert_cmd(SQLGrammarParser.Insert_cmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#insert_cmd}.
	 * @param ctx the parse tree
	 */
	void exitInsert_cmd(SQLGrammarParser.Insert_cmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#delete_cmd}.
	 * @param ctx the parse tree
	 */
	void enterDelete_cmd(SQLGrammarParser.Delete_cmdContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#delete_cmd}.
	 * @param ctx the parse tree
	 */
	void exitDelete_cmd(SQLGrammarParser.Delete_cmdContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(SQLGrammarParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(SQLGrammarParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(SQLGrammarParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(SQLGrammarParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SQLGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(SQLGrammarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link SQLGrammarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(SQLGrammarParser.ProgramContext ctx);
}