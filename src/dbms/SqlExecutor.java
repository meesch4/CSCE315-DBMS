package dbms;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.SqlBaseListener;
import parser.antlr.SQLGrammarLexer;
import parser.antlr.SQLGrammarParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SqlExecutor {
    private final String sqlPath = "src/query/sql/";
    private final String fileExtension = ".txt";

    private Dbms db;

    public SqlExecutor(Dbms db) {
        this.db = db;
    }

    // Given a sql file, executes it and returns the table it returned, if any
    public TableRootNode execute(String fileName) { // Where should we handle the exception?
        SqlBaseListener listener = new SqlBaseListener(this.db);
        List<String> lines = this.loadLines(fileName);

        for(String line : lines) {
            SQLGrammarLexer lexer = new SQLGrammarLexer(CharStreams.fromString(line));
            SQLGrammarParser parser = new SQLGrammarParser(new CommonTokenStream(lexer));

            lexer.removeErrorListeners();
            parser.removeErrorListeners();

            SQLGrammarParser.ProgramContext programContext = parser.program();
            ParseTreeWalker walker = new ParseTreeWalker();

            walker.walk(listener, programContext);
        }

        return db.getTable("RETURNED");
    }

    private List<String> loadLines(String fileName) {
        List<String> lines = new ArrayList<>();

        // String filePath = sqlPath + fileName + fileExtension;
        String filePath = fileName;

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.length() != 0) {
                    if(line.length() >= 2 && line.substring(0, 2).equals("//")) // Ignore commented lines
                        continue;

                    lines.add(line);
                }
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
