package dbms;

import org.junit.Test;
import types.IntType;
import types.Type;
import types.Varchar;

import java.util.*;

import static org.junit.Assert.*;

public class DbmsTests {
    Dbms db = new Dbms();

    @Test // Basically just tries to create table, then attempts to retrieve it from Dbms.getTable
    public void createTable_twoCols_createsCorrectAttributes() {
        String tableName = "table0";
        List<String> columnNames = new ArrayList<>(
                Arrays.asList("varcharCol", "intCol")
        );
        List<Type> columnTypes = new ArrayList<>(
            Arrays.asList(new Varchar(20), new IntType())
        );
        List<String> primaryKeys = new ArrayList<>(
                Arrays.asList("varcharCol")
        );

        Dbms sut = new Dbms();
        sut.createTable(tableName, columnNames, columnTypes, primaryKeys);

        TableRootNode result= sut.getTable(tableName);
        // assertNotNull(result); // First make sure this key actually exists

        Attribute col1 = new Attribute("varcharCol", 0, new Varchar(20), "");
        Attribute col2 = new Attribute("intCol", 1, new IntType(), "");

        // Make sure the attributes are what we expect
        assertEquals(result.getAttribute(0), col1);
        assertEquals(result.getAttribute(1), col2);
    }

    @Test // Insert values into the table and check that the correct row was created
    public void insertFromValues_alignedAttributes_doesInsert() {
        String tableName = "table0";
        createTable(tableName, 0);

        Object[] data = new Object[] { "string", 2 };

        List<Object> dataList = new ArrayList<>(
                Arrays.asList(data)
        );

        db.insertFromValues(tableName, dataList);

        TableRootNode table = db.getTable(tableName);
        RowNode ret = table.getRowNodes().get(0);

        RowNode expected = new RowNode(data);
        assertEquals(ret, expected);
    }

    @Test
    public void insertFromRelation_alignedAttributes_doesInsert() {
        String tableName0 = "table0", tableName1 = "table1";
        createTable(tableName0, 0);
        createTable(tableName1, 1);

        Object[] data0 = new Object[] { "string", 2 };
        Object[] data1 = new Object[] { "stuff" };

        // Assumes insertFromValues works as well
        db.insertFromValues(tableName0, Arrays.asList(data0));
        db.insertFromValues(tableName1, Arrays.asList(data1));

        db.insertFromRelation(tableName0, tableName1);

        TableRootNode table0 = db.getTable(tableName0);

        assertEquals(table0.getRowNodes().size(), 2); // Should have two entries

        RowNode actual = table0.getRowNodes().get(1);
        RowNode expected = new RowNode(new Object[] { "stuff", 0 });

        assertEquals(expected, actual);
    }

    @Test
    public void union_doesCombineTables() {

    }

    @Test // Inputs two identical rowNodes and checks if the Set only contains 1 RowNode total
    public void rowNode_hashCode_setRemovesDuplicates() {
        Object[] table1 = new Object[] { "string", 1};
        Object[] table2 = new Object[] { "string", 1};

        RowNode node = new RowNode(table1);
        RowNode node2 = new RowNode(table2);

        Set<RowNode> set = new HashSet<>();
        set.add(node); set.add(node2);

        System.out.println(set.size());
        assertEquals(set.size(), 1);
    }

    // Creates a basic table
    // Which is just a selector
    private void createTable(String tableName, int which) {
        if(which == 0) {
            List<String> columnNames = new ArrayList<>(
                    Arrays.asList("varcharCol", "intCol")
            );
            List<Type> columnTypes = new ArrayList<>(
                    Arrays.asList(new Varchar(20), new IntType())
            );
            List<String> primaryKeys = new ArrayList<>(
                    Arrays.asList("varcharCol")
            );

            db.createTable(tableName, columnNames, columnTypes, primaryKeys);
        } else if(which == 1) {
            List<String> columnNames = new ArrayList<>(
                    Arrays.asList("varcharCol")
            );
            List<Type> columnTypes = new ArrayList<>(
                    Arrays.asList(new Varchar(20), new IntType())
            );
            List<String> primaryKeys = new ArrayList<>(
                    Arrays.asList("varcharCol")
            );

            db.createTable(tableName, columnNames, columnTypes, primaryKeys);
        }
    }
}
