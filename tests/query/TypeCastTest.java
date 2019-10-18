package query;

import dbms.Dbms;
import dbms.SqlExecutor;
import org.junit.Test;

import java.io.IOException;

public class TypeCastTest {
    Dbms db = new Dbms();
    SqlExecutor sql = new SqlExecutor(db);
    Query quer = new Query(sql);

    @Test
    public void typecastQueryTest() throws IOException {

        String Tom = "";

        Tom = quer.calcMostCommonGenre("James Gandolfini");
        System.out.println(Tom);

    }
}
