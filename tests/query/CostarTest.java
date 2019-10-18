package query;

import dbms.Dbms;
import dbms.SqlExecutor;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CostarTest {
    Dbms db = new Dbms();
    SqlExecutor sql = new SqlExecutor(db);
    Query quer = new Query(sql);

    @Test
    public void costarQueryTest() throws IOException {

        List<String> Tom = new ArrayList<>();

        Tom = quer.calcCostarAppearances("Emma Stone", 3);
        for(String name : Tom) {
            System.out.println(name);
        }
    }
}
