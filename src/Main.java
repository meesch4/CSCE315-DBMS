import dbms.Dbms;
import dbms.SqlExecutor;
import dbms.TableRootNode;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.SqlBaseListener;
import parser.antlr.SQLGrammarLexer;
import parser.antlr.SQLGrammarParser;
import gui.skeleton;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Main function, where we do everything
// TODO: Needs to be changed to display the GUI
// TODO: Also this needs to be refactored into its own class
public class Main {
    // GUI Here
    // Create queries here, and pass them to the GUI?
    // Dbms here as well

    public static void main(String[] args) throws FileNotFoundException {
        List<String> lines = new ArrayList<>();

        String file = "test";
        Dbms db = new Dbms();
        SqlExecutor exec = new SqlExecutor(db);

        TableRootNode table = exec.execute(file);

        skeleton.generateGUI();

        System.out.println("");
    }
}
