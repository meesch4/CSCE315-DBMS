package parser;

import dbms.*;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import parser.antlr.SQLGrammarLexer;
import parser.antlr.SQLGrammarParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public void insertCmdFromValues_normalInput_passesCorrectArguments() {
        String input = "INSERT INTO tableName VALUES FROM (\"Joe\", \"cat\", 4);";
        String expectedTableName = "tableName";
        List<Object> expectedLiterals = new ArrayList<>(
                Arrays.asList("Joe", "cat", 4)
        );

        FakeDbms fake = new FakeDbms();

        run(input, fake);

        assertEquals(fake.tableName, expectedTableName);
        assertEquals(fake.values, expectedLiterals);
    }

    @Test
    public void insertCmdFromRelation_singleTable_passesCorrectArguments() {
        String input = "INSERT INTO tableName VALUES FROM RELATION animals;";
        String expectedTableName = "tableName";
        String expectedTableInsertFrom = "animals";

        FakeDbms fake = new FakeDbms();

        run(input, fake);

        assertEquals(expectedTableName, fake.tableName);
        assertEquals(expectedTableInsertFrom, fake.table2);
    }

    @Test
    public void insertCmdFromRelation_projection_passesCorrectArguments() {
        String input = "INSERT INTO tableName VALUES FROM RELATION project (kind) animals;";
        String expectedTableName = "tableName";
        String expectedTableInsertFrom = "tempTable0";

        FakeDbms fake = new FakeDbms();

        run(input, fake);

        assertEquals(expectedTableName, fake.tableName);
        assertEquals(expectedTableInsertFrom, fake.table2);
    }

    @Test
    public void update_normalInput_passesCorrectArguments() {
        String input = "UPDATE tableName SET column = \"value\", column2 = 25 WHERE column = \"somethingelse\"";
        String expectedTableName = "tableName";
        List<String> expectedColumnNames = new ArrayList<>(
                Arrays.asList("column", "column2")
        );
        List<Object> expectedValues = new ArrayList<>(
                Arrays.asList("value", 25)
        );

        FakeDbms fake = new FakeDbms();

        run(input, fake);

        assertEquals(expectedTableName, fake.tableName);
        assertEquals(expectedColumnNames, fake.columnNames);
        assertEquals(expectedValues, fake.values);
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

/**
 * This class's purpose is basically to just retrieve the arguments that IDbms gets and stores them as members/fields,
 * so we can assert that they're what we're expecting
 */
class FakeDbms implements IDbms {
    String tableName;
    List<String> columnNames;
    List<Type> columnTypes;
    List<String> primaryKeys;

    List<Object> values;
    String table2;
    Condition condition;

    @Override
    public void createTable(String tableName, List<String> columnNames, List<Type> columnTypes, List<String> primaryKeys) {
        this.tableName = tableName;
        this.columnNames = columnNames;
        this.columnTypes = columnTypes;
        this.primaryKeys = primaryKeys;
    }

    @Override
    public void insertFromRelation(String tableInsertInto, String tableInsertFrom) {
        this.tableName = tableInsertInto;
        this.table2 = tableInsertFrom;
    }

    @Override
    public void insertFromValues(String tableInsertInto, List<Object> valuesFrom) {
        this.tableName = tableInsertInto;
        this.values = valuesFrom;
    }

    @Override
    public void update(String table, List<String> columnsToSet, List<Object> valuesToSetTo, Condition condition) {
        this.tableName = table;
        this.columnNames = columnsToSet;
        this.values = valuesToSetTo;
        this.condition = condition;
    }

    private int count = 0;
    @Override
    public String projection(String tableFrom, List<String> columnNames) {
        this.tableName = tableFrom;
        this.columnNames = columnNames;

        return "tempTable" + count++;
    }

    @Override
    public String rename(String tableName, List<String> newColumnNames) {
        this.tableName = tableName;
        this.columnNames = newColumnNames;

        return "tempTable" + count++;
    }

    @Override
    public String union(String table1, String table2) {
        this.tableName = table1;
        this.table2 = table2;

        return "tempTable" + count++;
    }

    @Override
    public String difference(String table1, String table2) {
        this.tableName = table1;
        this.table2 = table2;

        return "tempTable" + count++;
    }

    @Override
    public String product(String table1, String table2) {
        this.tableName = table1;
        this.table2 = table2;

        return "tempTable" + count++;
    }

    @Override public void show(String table) { }
    @Override public void delete(String table) { }
    @Override public void open(String table) { }
    @Override public void close(String table) { }
    @Override public void write(String table) { }
    @Override public void exit() { }

    @Override
    public Table getTable(String tableName) {
        return null;
    }
}