import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import parser.SqlBaseListener;
import parser.antlr.SQLGrammarLexer;
import parser.antlr.SQLGrammarParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Main function, where we do everything
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        List<String> lines = new ArrayList<>();

        /* Uncomment when we're ready to load files
        String filePath = "src/text.txt";
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.length() != 0)
                lines.add(line);
        }
        */

        SqlBaseListener listener = new SqlBaseListener();
        // for(String line : lines) {
        CharStream charStream = CharStreams.fromString("hello13");
        SQLGrammarLexer lexer = new SQLGrammarLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        SQLGrammarParser parser = new SQLGrammarParser(tokenStream);

        lexer.removeErrorListeners();
        parser.removeErrorListeners();

        String text = parser.literal().getText();
        // System.out.println()

        // }
    }
}