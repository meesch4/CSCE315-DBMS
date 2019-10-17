package query;

import dbms.Dbms;
import dbms.SqlExecutor;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CoverRolesQueryTests {
    private SqlExecutor executor = new SqlExecutor(new Dbms());
    private CoverRolesQuery query = new CoverRolesQuery(executor);

    @Test
    public void coverRolesQuery_joker_hasHeathLedger() {
        List<String> ret = query.calcActorsWhichPlayedCharacter("Joker");

        assertTrue(ret.contains("Heath Ledger"));
    }

    // Doesn't work atm
    @Test
    public void coverRolesQuery_batman_containsBigActors() {
        List<String> ret = query.calcActorsWhichPlayedCharacter("Batman");

        for(String s : ret)
            System.out.println(s);

        assertTrue(ret.contains("Ben Affleck"));
        assertTrue(ret.contains("Christian Bale"));
    }

}
