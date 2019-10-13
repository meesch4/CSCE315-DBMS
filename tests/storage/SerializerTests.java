package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dbms.Dbms;
import dbms.RowNode;
import dbms.TableRootNode;
import org.junit.Test;
import types.IntType;
import types.Type;
import types.Varchar;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

// Tests pure serialization, not JsonSerializer
// However since JsonSerializer is this code to a T, not testing JsonSerializer
public class SerializerTests {
    @Test
    public void test_serializeable() {
        TableRootNode table = createExpectedTable();

        try {
            FileOutputStream fileOut = new FileOutputStream("src/" + table.getRelationName() + ".ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(table);
            out.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_loadsSerializedTable() {
        try {
            FileInputStream fileIn = new FileInputStream("src/tableName.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            TableRootNode table = (TableRootNode) in.readObject();
            in.close();
            fileIn.close();

            TableRootNode expected = createExpectedTable();

            assertEquals(table.getRelationName(), expected.getRelationName());
            assertEquals(table.getAttributes(), expected.getAttributes());
            assertEquals(table.getRowNodes(), expected.getRowNodes());

        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }
    }

    // The table that should be returned from example.json
    private TableRootNode createExpectedTable() {
        Dbms db = new Dbms();

        List<String> columnNames = new ArrayList<>(
                Arrays.asList("varcharCol", "intCol")
        );
        List<Type> columnTypes = new ArrayList<>(
                Arrays.asList(new Varchar(20), new IntType())
        );
        List<String> primaryKeys = new ArrayList<>(
                Arrays.asList("varcharCol")
        );

        String tableName = "tableName";

        RowNode row1 = new RowNode(new Object[] { "col1Data", 1 });
        RowNode row2 = new RowNode(new Object[] { "col1DataAgain", 2 });

        db.createTable(tableName, columnNames, columnTypes, primaryKeys);

        TableRootNode table = db.getTable(tableName);
        table.addRow(row1);
        table.addRow(row2);

        return table;
    }
}
