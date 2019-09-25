package parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.antlr.*;

public class Parser {
    public Parser() { }

    public static void main(String[] args) { // Temp main function
        testLexer lexer = new testLexer(CharStreams.fromString("INSERT INTO tableName VALUES FROM (name, thing);"));
        testParser parser = new testParser(new CommonTokenStream(lexer));

        String name = parser.prule().insert().tableName().getText();
        System.out.println(name);

        String thing = parser.prule().select().getText();
        System.out.println(thing);
    }
}
