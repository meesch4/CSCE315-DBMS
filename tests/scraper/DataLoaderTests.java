package scraper;

import csce315.project1.MovieDatabaseParser;
import dbms.Dbms;
import dbms.RowNode;
import dbms.SqlExecutor;
import dbms.TableRootNode;
import org.junit.Test;
import scraper.DataLoader;
import query.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DataLoaderTests {

    Dbms db = new Dbms();
    SqlExecutor sql = new SqlExecutor(db);
    Query quer = new Query(sql);
    @Test // Load in just the movies_single file and assert that it forms correctly
    public void loadAllMovies_moviesSingle_doesLoadCorrectly() throws IOException {
        // Arrange
        DataLoader loader = new DataLoader();
        MovieDatabaseParser parser = new MovieDatabaseParser();
        TableRootNode table = loader.createMovieTable();

        // Act
        loader.loadAllMovies(parser, table, "movies_single");

        // Assert
        Object[] expectedData = new Object[] { 862, "Toy Story", "Animation,Comedy,Family", 77};
        RowNode expected = new RowNode(expectedData);

        assertEquals(expected, table.getRowNodes().get("862"));
    }

    @Test
    public void loadALlCredits_creditsSingle_doesLoadCorrectly() throws IOException {
        // Arrange
        DataLoader loader = new DataLoader();
        MovieDatabaseParser parser = new MovieDatabaseParser();
        TableRootNode table = loader.createMovieTable();

        // Act
        loader.loadAllCredits(parser, table, "credits_single");

        // Assert
        Object[] expectedDataCast = new Object[] { "52fe4284c3a36847f8024f95", 862, "Tom Hanks", "Woody (voice)", 1 };
        RowNode expectedCast = new RowNode(expectedDataCast);

        Object[] expectedDataCrew = new Object[] { "52fe4284c3a36847f8024f4f", 862, "Joss Whedon", "Screenplay", 0 };
        RowNode expectedCrew = new RowNode(expectedDataCrew);

        assertEquals(expectedCast, table.getRowNodes().get("52fe4284c3a36847f8024f95"));
        assertEquals(expectedCrew, table.getRowNodes().get("52fe4284c3a36847f8024f4f"));

    }
    @Test
    public void costarQueryTest() throws IOException {

        String Tom = "";

        Tom = quer.calcMostCommonGenre("James Gandolfini");
        System.out.println(Tom);

    }
}
