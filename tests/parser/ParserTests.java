package parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.Test;
import parser.antlr.SQLGrammarLexer;
import parser.antlr.SQLGrammarParser;

import static org.junit.Assert.*;

public class ParserTests {
    public SQLGrammarParser getParserFromText(String text) {
        SQLGrammarLexer lexer = new SQLGrammarLexer(CharStreams.fromString(text));
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);

        return new SQLGrammarParser(tokenStream);
    }

    @Test // These tests are pretty useless, as the ANTLR errors aren't thrown like actual errors
    public void identifier_shouldAllow() {
        String[] inputs ={ "a", "csce315", "h_ello", "_variable" };

        for(String in : inputs) {
            System.out.print(in + " ");
            SQLGrammarParser parser = getParserFromText(in);

            String actual = parser.identifier().getText();

            assertEquals(in, actual);
        }
    }

    @Test // Checks to see if the VARCHAR gets the correct size of it
    public void type_varchar_getsCorrectSize() {
        String input = "VARCHAR(20)";
        String expected = "20";

        SQLGrammarParser parser = getParserFromText(input);

        String actual = parser.type().integer().getText();

        assertEquals(expected, actual);
    }
}
