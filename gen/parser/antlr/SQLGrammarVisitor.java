// Generated from /Users/gannonprudomme/Documents/workspace/Java 315/src/parser/antlr/SQLGrammar.g4 by ANTLR 4.7.2
package parser.antlr;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SQLGrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SQLGrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#operator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperator(SQLGrammarParser.OperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#integer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInteger(SQLGrammarParser.IntegerContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(SQLGrammarParser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#string_literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitString_literal(SQLGrammarParser.String_literalContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(SQLGrammarParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#relation_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelation_name(SQLGrammarParser.Relation_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#attribute_name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute_name(SQLGrammarParser.Attribute_nameContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#operand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOperand(SQLGrammarParser.OperandContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(SQLGrammarParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#literal_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral_list(SQLGrammarParser.Literal_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#attribute_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute_list(SQLGrammarParser.Attribute_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#typed_attribute_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTyped_attribute_list(SQLGrammarParser.Typed_attribute_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#open_cmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOpen_cmd(SQLGrammarParser.Open_cmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#close_cmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClose_cmd(SQLGrammarParser.Close_cmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#write_cmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWrite_cmd(SQLGrammarParser.Write_cmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#exit_cmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExit_cmd(SQLGrammarParser.Exit_cmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#or}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOr(SQLGrammarParser.OrContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#and}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd(SQLGrammarParser.AndContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#parenopen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenopen(SQLGrammarParser.ParenopenContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#parenclose}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenclose(SQLGrammarParser.ParencloseContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#condition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondition(SQLGrammarParser.ConditionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#conjunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConjunction(SQLGrammarParser.ConjunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(SQLGrammarParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(SQLGrammarParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#atomic_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomic_expr(SQLGrammarParser.Atomic_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#selection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelection(SQLGrammarParser.SelectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#projection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProjection(SQLGrammarParser.ProjectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#renaming}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRenaming(SQLGrammarParser.RenamingContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#union}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnion(SQLGrammarParser.UnionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#difference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDifference(SQLGrammarParser.DifferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#product}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProduct(SQLGrammarParser.ProductContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#natural_join}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNatural_join(SQLGrammarParser.Natural_joinContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#update_set_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdate_set_list(SQLGrammarParser.Update_set_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#show_cmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShow_cmd(SQLGrammarParser.Show_cmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#create_cmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCreate_cmd(SQLGrammarParser.Create_cmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#update_cmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUpdate_cmd(SQLGrammarParser.Update_cmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#insert_cmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInsert_cmd(SQLGrammarParser.Insert_cmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#delete_cmd}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDelete_cmd(SQLGrammarParser.Delete_cmdContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(SQLGrammarParser.CommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(SQLGrammarParser.QueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link SQLGrammarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(SQLGrammarParser.ProgramContext ctx);
}