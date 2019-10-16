package query;

import dbms.RowNode;
import dbms.SqlExecutor;
import dbms.TableRootNode;
import query.interfaces.ICoverRolesQuery;

import java.util.ArrayList;
import java.util.List;

public class CoverRolesQuery implements ICoverRolesQuery {
    private SqlExecutor executor;

    public CoverRolesQuery(SqlExecutor executor) {
        this.executor = executor;
    }

    /**
     * Given a character's name
     * Returns a list of actors that have played that character in a movie
     *
     * Example
     * In: Joker
     * Out: Heath Ledger, Joaquin Phoenix, Jack Nicholson, Mark Hamil, Jared Leto, etc
     */
    @Override
    public List<String> calcActorsWhichPlayedCharacter(String characterName) {
        TableRootNode actorsTable = executor.execute("coverroles/GetActorsForCharacter", characterName);

        List<String> actorNames = new ArrayList<>();
        for(RowNode row : actorsTable.getRowNodes().values()) {
            String actorName = (String) row.getDataField(0);

            actorNames.add(actorName);
        }

        return actorNames;
    }
}
