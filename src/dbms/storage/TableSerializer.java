package dbms.storage;

import dbms.TableRootNode;

import java.io.*;

/**
 *  Saves and loads Tables to and from JSON files
 */
public class TableSerializer {
    private static String filePath = "src/dbms/storage/";

    /**
     *  Saves the given table to a file, which is named according to its table name
     */
    public static void saveToFile(TableRootNode table) {
        String filePath = TableSerializer.filePath + table.getRelationName() + ".ser";

        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(table);
            objectOut.close();
            fileOut.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Given a fileName(tableName), returns the according Table, or null if it couldn't be loaded
     */
    public static TableRootNode loadFromFile(String tableName) {
        String filePath = TableSerializer.filePath + tableName + ".ser";

        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            TableRootNode table = (TableRootNode) objectIn.readObject();

            objectIn.close();
            fileIn.close();

            return table;
        } catch(Exception e) { // Catch ClassNotFound and IOException the same
            e.printStackTrace();
        }

        // Failed, return null
        return null;
    }
}
