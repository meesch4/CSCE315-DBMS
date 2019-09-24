package parser;

import org.antlr.v4.runtime.CharStreams;
import parser.antlr.testLexer;

public class Parser {
    public static void main(String[] args) { // Temp main function
        testLexer lexer = new testLexer(CharStreams.fromString("INSERT INTO regex VALUES FROM (regex, regex);"));
    }
}
