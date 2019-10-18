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
import gui.MainWindow;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Main function, where we do everything
// TODO: Needs to be changed to display the GUI
// TODO: Also this needs to be refactored into its own class
public class Main {
    public static void main(String[] args) {
        SqlExecutor executor = new SqlExecutor(new Dbms());

        MainWindow m = new MainWindow(executor);
        m.setLocationByPlatform(true);
        m.setVisible(true);
    }
}
