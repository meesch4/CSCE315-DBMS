package dbms;

import dbms.RowNode;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class DbmsTests {
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
}
