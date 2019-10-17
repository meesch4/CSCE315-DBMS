package query;

import dbms.Dbms;
import dbms.SqlExecutor;
import org.junit.Test;

import static org.junit.Assert.*;

public class WorstOfBestQueryTests {
    SqlExecutor exec = new SqlExecutor(new Dbms());

    @Test
    public void calcWorstOfBests_isCorrect() {
        WorstOfBestQuery query = new WorstOfBestQuery(exec);

        String movie = query.calcWorstOfBests("Tom Hanks");

        System.out.println(movie);
    }

    @Test
    public void getMembersBestMovie_tomHanks_doesGetBestMovie() {
        WorstOfBestQuery query = new WorstOfBestQuery(exec);

        int bestMovie = query.getMembersBestWorstMovie("Tom Hanks", true);

        System.out.println(bestMovie);
    }

    @Test
    public void getDirector__doesGetDirector() {
        WorstOfBestQuery query = new WorstOfBestQuery(exec);

        String director = query.getDirectorOfMovie(497); // The Green Mile

        assertEquals("Frank Darabont", director);
    }
}
