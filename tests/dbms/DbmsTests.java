package dbms;

import org.junit.Test;
import types.IntType;
import types.Type;
import types.Varchar;

import java.util.*;

import static org.junit.Assert.*;

public class DbmsTests {
    Dbms db = new Dbms();
    //union  I think this test is set up properly... I honestly don't really know
    //difference
    //product
    //select
    //
    @Test // Basically just tries to create table, then attempts to retrieve it from Dbms.getTable
    public void createTable_twoCols_createsCorrectAttributes() {
        // Arrange
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

        // Act
        sut.createTable(tableName, columnNames, columnTypes, primaryKeys);

        // Assert
        TableRootNode result= sut.getTable(tableName);
        // assertNotNull(result); // First make sure this key actually exists

        Attribute col1 = new Attribute("varcharCol", 0, new Varchar(20));
        Attribute col2 = new Attribute("intCol", 1, new IntType());

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
        RowNode ret = table.getRowNodes().get("string");

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

        assertEquals(2, table0.getRowNodes().size()); // Should have two entries

        RowNode actual = table0.getRowNodes().get("stuff");
        RowNode expected = new RowNode(new Object[] { "stuff", 0 });

        assertEquals(expected, actual);
    }

    @Test
    public void insertFromRelation_mismatchedAttributes_doesInsert() {
        String tableName0 = "table0", tableName1 = "table1";
        createTable(tableName0, 0);
        createTable(tableName1, 2);

        Object[] data0 = new Object[] { "string", 2 };
        Object[] data1 = new Object[] { 3, "stuff" };

        // Assumes insertFromValues works as well
        db.insertFromValues(tableName0, Arrays.asList(data0));
        db.insertFromValues(tableName1, Arrays.asList(data1));

        db.insertFromRelation(tableName0, tableName1);

        TableRootNode table0 = db.getTable(tableName0);

        RowNode actual = table0.getRowNodes().get("stuff"); // Where stuff is primaryKey
        RowNode expected = new RowNode(new Object[] { "stuff", 3 });

        assertEquals(2, table0.getRowNodes().size()); // Should have two entries
        assertEquals(expected, actual);
    }

    @Test // Tests if insertFromRelation updates the primaryKeys so there's only 1 row with the column of "string"
    public void insertFromRelation_mismatchedAttributes_doesRemovePrimaryKeyDuplicates() {
        String tableName0 = "table0", tableName1 = "table1";
        createTable(tableName0, 0);
        createTable(tableName1, 2);

        Object[] data0 = new Object[] { "string", 2 };
        Object[] data1 = new Object[] { 3, "string" };

        // Assumes insertFromValues works as well
        db.insertFromValues(tableName0, Arrays.asList(data0));
        db.insertFromValues(tableName1, Arrays.asList(data1));

        db.insertFromRelation(tableName0, tableName1);

        TableRootNode table0 = db.getTable(tableName0);

        RowNode actual = table0.getRowNodes().get("string"); // Where string is primaryKey
        RowNode expected = new RowNode(new Object[] { "string", 3 }); // Should update the row w/ value 2

        assertEquals(table0.getRowNodes().size(), 1); // Should only be 1 of the rows
        assertEquals(expected, actual);
    }

    @Test
    public void delete_doesDelete() {
        String tableName = "table0";
        createTable(tableName, 0);

        Object[] data0 = new Object[] { "one", 2};
        Object[] data1 = new Object[] { "two", 1};

        db.insertFromValues(tableName, Arrays.asList(data0));
        db.insertFromValues(tableName, Arrays.asList(data1));

        // Condition is varcharCol == "one" || intCol == 1
        db.delete(tableName, createCondition(0));

        TableRootNode table = db.getTable(tableName);

        RowNode deletedRow = table.getRowNodes().get("one");
        RowNode remainingRow = table.getRowNodes().get("two");

        assertEquals(1, table.getRowNodes().size());
        assertNull(deletedRow); // Shouldn't exist
        assertNotNull(remainingRow); // Shouldn't have been deleted
    }

    @Test
    public void show_test(){
        String tableName0 = "table0", tableName1 = "table1";
        createTable(tableName0, 0);
        createTable(tableName1, 1);

        Object[] data0 = new Object[] { "stuff", 2 };
        Object[] data1 = new Object[] { "newstuff" };

        // Assumes insertFromValues works as well
        db.insertFromValues(tableName0, Arrays.asList(data0));
        db.insertFromValues(tableName0, Arrays.asList(data1));
        db.show(tableName0);
        db.show(tableName1);
    }

    @Test
    public void union_doesCombineTables() {
        String tableName0 = "table0", tableName1 = "table1", tableName2 = "table2";
        ArrayList<Attribute> attributes = new ArrayList<>();
        Attribute col1 = new Attribute("varcharCol", 0, new Varchar(20));
        Attribute col2 = new Attribute("intCol", 1, new IntType());
        attributes.add(col1);
        attributes.add(col2);
        ArrayList<Attribute> primaryKeys = new ArrayList<>(attributes);


        Object[] rowData0 = new Object[] { "string", 1};
        Object[] rowData1 = new Object[] { "new", 1};
        Object[] rowData2 = new Object[] { "test", 0};

        RowNode row0 = new RowNode(rowData0);
        RowNode row1 = new RowNode(rowData1);
        RowNode row2 = new RowNode(rowData2);

        TableRootNode table0 = new TableRootNode(tableName0, attributes, primaryKeys);
        TableRootNode table1 = new TableRootNode(tableName1, attributes, primaryKeys);
        TableRootNode table2 = new TableRootNode(tableName2, attributes, primaryKeys);

        table0.addRow(row0);
        table0.addRow(row1);

        table1.addRow(row0);
        table1.addRow(row2);

        table2.addRow(row0);
        table2.addRow(row1);
        table2.addRow(row2);

        db.tables.put(tableName0, table0);
        db.tables.put(tableName1, table1);
        db.tables.put(tableName2, table2);

        String newTable = db.union(tableName1, tableName2);
        TableRootNode unionTable = (TableRootNode) db.tables.get(newTable);
        // Assumes insertFromValues works as well


        String newTableName = db.union(tableName0, tableName1);

        TableRootNode unionNewTable = db.getTable(newTableName);

        //System.out.println("union test");
        assertEquals(unionTable.getRowNodes().size(), 3); // Should have three entries (since duplicate should be removed.)

        HashMap<String, RowNode> manualRowNodes = db.getTable(newTable).getRowNodes();
        HashMap<String, RowNode> unionRowNodes = db.getTable(unionNewTable.relationName).getRowNodes();

        assertEquals(manualRowNodes, unionRowNodes);
        //System.out.println("unionTest end");
    }

    // Creates a table with a row of {"one" 1} and updates it to {"two", 2}
    @Test
    public void update_doesUpdateTable() {
        String tableName = "table";
        createTable(tableName, 0);

        List<Object> initialData = new ArrayList<>(Arrays.asList("one", 1));

        db.insertFromValues(tableName, initialData);

        // SET varcharCol = "one" && intCol = 2 WHERE varcharCol == "one" || intCol == 1
        List<String> columnsToSet = new ArrayList<>(Arrays.asList("varcharCol", "intCol"));
        List<Object> valuesToSet = new ArrayList<>(Arrays.asList("two", 2));
        Condition condition = createCondition(0);

        db.update(tableName, columnsToSet, valuesToSet, condition);

        RowNode actual = db.tables.get(tableName).getRowNodes().get("two");
        RowNode expected = new RowNode(valuesToSet.toArray());

        assertEquals(actual, expected);
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

    private Condition createCondition(int which) {
        Condition root = new Condition();
        if(which == 0) { // varcharCol == "one" || intCol == 1
            root.op = Operator.OR;
            Condition left = new Condition();
            left.op = Operator.EQUALS;
            left.left = new Attribute("intCol");
            left.right = "5";
            Condition right = new Condition();
            right.op = Operator.EQUALS;
            right.left = new Attribute("varcharCol");
            right.right = "one";

            root.left = left;
            root.right = right;
        }

        return root;
    }

    private Condition expected(int which) {
        Condition root = new Condition();
        if(which == 1) { // kind == "cat" || kind == "dog"
            root.op = Operator.OR;
            Condition left = new Condition();
            left.op = Operator.EQUALS;
            left.left = new Attribute("kind");
            left.right = "cat";
            Condition right = new Condition();
            right.op = Operator.EQUALS;
            right.left = new Attribute("kind");
            right.right = "dog";

            root.left = left;
            root.right = right;
        } else if(which == 2) { // kind == "cat" || (kind == "dog" && age > 5))
            root.op = Operator.OR;
            Condition b = new Condition();
            b.op = Operator.AND;
            Condition c = new Condition();
            c.op = Operator.GREATER;
            c.left = new Attribute("age");
            c.right = 5;
            Condition d = new Condition();
            d.op = Operator.EQUALS;
            d.left = new Attribute("kind");
            d.right = "dog";
            b.left = d;
            b.right = c;
            Condition e = new Condition();
            e.op = Operator.EQUALS;
            e.left = new Attribute("kind");
            e.right = "cat";

            root.left = e;
            root.right = b;
        }

        return root;
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
                    Arrays.asList(new Varchar(20))
            );
            List<String> primaryKeys = new ArrayList<>(
                    Arrays.asList("varcharCol")
            );

            db.createTable(tableName, columnNames, columnTypes, primaryKeys);
        } else if(which == 2) {
            List<String> columnNames = new ArrayList<>(
                    Arrays.asList("intCol", "varcharCol")
            );
            List<Type> columnTypes = new ArrayList<>(
                    Arrays.asList(new IntType(), new Varchar(20))
            );
            List<String> primaryKeys = new ArrayList<>(
                    Arrays.asList("varcharCol")
            );

            db.createTable(tableName, columnNames, columnTypes, primaryKeys);
        }
    }

    @Test
    public void projection_test() {
        String tableName = "testTable";
        ArrayList<Attribute> attList = new ArrayList<>();
        Attribute att0 = new Attribute("name", 0, new Varchar(20));
        Attribute att1 = new Attribute("age", 1, new Varchar(20));
        Attribute att2 = new Attribute("derp", 2, new Varchar(20));
        attList.add(att0);
        attList.add(att1);
        attList.add(att2);

        Object[] row0Data = new Object[]{"bob", 0, 5};
        Object[] row1Data = new Object[]{"bobert", 1, 4};
        Object[] row2Data = new Object[]{"bobito", 2, 3};
        Object[] row3Data = new Object[]{"bobby", 3, 2};
        Object[] row4Data = new Object[]{"robert", 4, 1};
        Object[] row5Data = new Object[]{"bobbino", 5, 0};

        TableRootNode tableRoot = new TableRootNode(tableName, attList, attList);
        db.tables.put(tableName, tableRoot);
        RowNode row0 = new RowNode(row0Data);
        RowNode row1 = new RowNode(row1Data);
        RowNode row2 = new RowNode(row2Data);
        RowNode row3 = new RowNode(row3Data);
        RowNode row4 = new RowNode(row4Data);
        RowNode row5 = new RowNode(row5Data);
        tableRoot.addRow(row0);
        tableRoot.addRow(row1);
        tableRoot.addRow(row2);
        tableRoot.addRow(row3);
        tableRoot.addRow(row4);
        tableRoot.addRow(row5);
        List<String> colNames0 = new ArrayList<>();
        colNames0.add("name");
        colNames0.add("age");
        List<String> colNames1 = new ArrayList<>();
        colNames1.add("name");
        colNames1.add("age");
        colNames1.add("derp");
        List<String> colNames2 = new ArrayList<>();
        String outTable0 = db.projection(tableName, colNames0);
        String outTable1 = db.projection(tableName, colNames1);
        String outTable2 = db.projection(tableName, colNames2);

        // TODO: We need to add descriptions(in the console) of what these should do
        db.show(outTable0);
        db.show(outTable1);
        db.show(outTable2);

    }

    @Test
    public void product_test() {
        String tableName0 = "table0";
        String tableName1 = "table1";
        createTable(tableName0,  0);
        createTable(tableName1, 0);

        Object[] table0_data0 = new Object[] {"stuff", 1};
        Object[] table0_data1 = new Object[] {"stuff2", 2};
        RowNode table0_row0 = new RowNode(table0_data0);
        RowNode table0_row1 = new RowNode(table0_data1);
        db.tables.get(tableName0).addRow(table0_row0);
        db.tables.get(tableName0).addRow(table0_row1);

        Object[] table1_data0 = new Object[] {"stuff3", 3};
        Object[] table1_data1 = new Object[] {"stuff4", 4};
        RowNode table1_row0 = new RowNode(table1_data0);
        RowNode table1_row1 = new RowNode(table1_data1);
        db.tables.get(tableName1).addRow(table1_row0);
        db.tables.get(tableName1).addRow(table1_row1);

        String newTableName = db.product(tableName0, tableName1);
        TableRootNode newTable = db.getTable(newTableName);

        System.out.println("Should have 4 rows total");
        db.show(newTableName);
    }
    @Test
    public void difference_test() {
        String tableName0 = "table0";
        String tableName1 = "table1";

        createTable(tableName0,  0);
        createTable(tableName1, 0);

        Object[] data0 = new Object[] {"stuff", 1};
        Object[] data1 = new Object[] {"stuff3", 3};

        RowNode table0_row0 = new RowNode(data0);
        RowNode table1_row0 = new RowNode(data1);

        // Table0 contains 1 unique row and 1 shared row with table1
        db.tables.get(tableName0).addRow(table0_row0);
        db.tables.get(tableName0).addRow(table1_row0);
        db.tables.get(tableName1).addRow(table1_row0);

        String newTableName = db.difference(tableName0, tableName1);
        TableRootNode newTable =  db.getTable(newTableName);

        System.out.println("Should contain only one row");
        db.show(newTableName);
    }
}
