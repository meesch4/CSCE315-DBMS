package query;

import csce315.project1.MovieDatabaseParser;
import dbms.RowNode;
import dbms.TableRootNode;
import org.junit.Test;
import scraper.DataLoader;

import java.io.IOException;

import static org.junit.Assert.*;

public class DataLoaderTests {
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
        loader.loadAllMovies(parser, table, "credits_single");

        // Assert
        Object[] expectedData = new Object[] { 862, "Toy Story", "Animation,Comedy,Family", 77};
        RowNode expected = new RowNode(expectedData);

        assertEquals(expected, table.getRowNodes().get("862"));
    }
}
