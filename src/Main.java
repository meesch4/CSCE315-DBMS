import dbms.Dbms;
import dbms.DuplicateKeyException;
import dbms.Table;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import parser.SqlBaseListener;
import parser.antlr.SQLGrammarLexer;
import parser.antlr.SQLGrammarParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Main function, where we do everything
public class Main {
    public static void main(String[] args) {
        try {
            String tableName = "animals";
            String[] attributes = {"Name","Kind","Years"};
            String[] domainString = {"VARCHAR(20)","VARCHAR(8)","INTEGER"};
            String[] primaryKey = {"Name","Kind"};
            Table animals = new Table(tableName,attributes,domainString,primaryKey);
            ArrayList<Object> newTuple = new ArrayList<Object>(Arrays.asList("Joe","cat",4));
            ArrayList<Object> newTuple1 = new ArrayList<Object>(Arrays.asList("Spot","dog",10));
            ArrayList<Object> newTuple2 = new ArrayList<Object>(Arrays.asList("Snoopy","dog",10));
            ArrayList<Object> newTuple3 = new ArrayList<Object>(Arrays.asList("Tweety","bird",1));
            ArrayList<Object> newTuple4 = new ArrayList<Object>(Arrays.asList("Joe","bird",2));
            animals.insert(newTuple);
            animals.insert(newTuple1);
            animals.insert(newTuple2);
            animals.insert(newTuple3);
            animals.insert(newTuple4);
            animals.toString();
            String[] conditionAttribute = {"Years"};
            String[] conditionMark = {"=="};
            Object[] conditionValue = {10};
            String[] andOr = {};
            animals.delete(conditionAttribute, conditionMark, conditionValue, andOr);
            animals.toString();
            System.out.println(animals);
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Please make sure number of attributes equals to number of domain(constraints)");
            e.printStackTrace();
        }
        catch(IndexOutOfBoundsException e) {
            System.out.println("Please make sure number of elements in each row is equal to number of attributes");
            e.printStackTrace();
        }
        catch(IllegalArgumentException e) {
            System.out.println("Please make sure the type of each element matches the type/character count required by the attribute");
            System.out.println("If you are updating, please make sure your field name/entry value is valid");
            e.printStackTrace();

        }
        catch(DuplicateKeyException e) {
            System.out.println("Please do not insert tuple with duplicate primary key value");
            e.printStackTrace();
        }
    }
}
