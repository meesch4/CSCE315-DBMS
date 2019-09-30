package parser;

import dbms.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import parser.antlr.SQLGrammarLexer;
import parser.antlr.SQLGrammarParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class SqlBaseListenerTests {
    @Test
    public void createCmd_normal_passesCorrectArguments() {
        String input = "CREATE TABLE tableName (name VARCHAR(20), kind VARCHAR(8), years INTEGER) PRIMARY KEY (name, kind);";
        String expectedTableName = "tableName";
        List<String> expectedColumnNames = new ArrayList<>(
                Arrays.asList("name", "kind", "years")
        );
        List<Type> expectedColumnTypes = new ArrayList<>( // Can't compare since they're objects without a "equals" method
                Arrays.asList(new Varchar(20), new Varchar(8), new IntType())
        );
        List<String> expectedPrimaryKeys = new ArrayList<>(
                Arrays.asList("name", "kind")
        );

        FakeDbms fake = new FakeDbms();

        run(input, fake);

        assertEquals(expectedTableName, fake.tableName);
        assertEquals(expectedColumnNames, fake.columnNames);
        assertEquals(expectedPrimaryKeys, fake.primaryKeys);
    }

    @Test
    public void insertCmd_valuesFrom_passesCorrectArguments() {
        String input = "INSERT INTO tableName VALUES FROM (\"Joe\", \"cat\", 4);";
        String expectedTableName = "tableName";
        List<Object> expectedLiterals = new ArrayList<>(
                Arrays.asList("Joe", "cat", 4)
        );

        FakeDbms fake = new FakeDbms();

        run(input, fake);

        assertEquals(fake.tableName, expectedTableName);
        assertEquals(fake.valuesFrom, expectedLiterals);
    }

    private void run(String input, IDbms db) {
        SqlBaseListener listener = new SqlBaseListener(db);

        CharStream charStream = CharStreams.fromString(input);
        SQLGrammarLexer lexer = new SQLGrammarLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        SQLGrammarParser parser = new SQLGrammarParser(tokenStream);

        lexer.removeErrorListeners();
        parser.removeErrorListeners();

        SQLGrammarParser.ProgramContext programContext = parser.program();
        ParseTreeWalker walker = new ParseTreeWalker();

        walker.walk(listener, programContext);
    }
}

class FakeDbms implements IDbms {
    String tableName;
    List<String> columnNames;
    List<Type> columnTypes;
    List<String> primaryKeys;

    List<Object> valuesFrom;
    String tableInsertFrom;

    @Override
    public void createTable(String tableName, List<String> columnNames, List<Type> columnTypes, List<String> primaryKeys) {
        this.tableName = tableName;
        this.columnNames = columnNames;
        this.columnTypes = columnTypes;
        this.primaryKeys = primaryKeys;
    }

    @Override
    public void insertFromRelation(String tableInsertInto, String tableInsertFrom) {

    }

    @Override
    public void insertFromValues(String tableInsertInto, List<Object> valuesFrom) {
        this.tableName = tableInsertInto;
        this.valuesFrom = valuesFrom;
    }

    @Override
    public String projection(String tableFrom, List<String> columnNames) {
        return null;
    }

    @Override
    public Table getTable(String tableName) {
        return null;
    }
}