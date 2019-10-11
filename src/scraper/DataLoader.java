package scraper;

//
//
//

import csce315.project1.Credits;
import csce315.project1.Movie;
import csce315.project1.MovieDatabaseParser;
import dbms.TableRootNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Most likely rename this at some point
 * This is basically going to use the MovieDatabaseParser, convert the data points into tables,
 * then move those tables into Dbms(or rather, save those tables to a file to be loaded later, using serialization)
 */
public class DataLoader {
    TableRootNode moviesTable;
    TableRootNode creditsTable;

    // Create the attributes/columns for the moviesTable
    private void createMovieTable() {
        // Columns: id, title, genres(list), rating(Int)
    }

    // Create
    private void createCreditsTable() {
        // Columns: creditID, cast/crew?, name, role/character, movieID, actorID
    }

    /**
     *  Runs the MovieDatabaseParser and loads the appropriate ro
     */
    public static void main(String args[]) throws IOException {
        MovieDatabaseParser parser = new MovieDatabaseParser();

        List<Movie> moviesList = parser.deserializeMovies("src/scraper/inputs/movies_single.json");
        List<Credits> creditsList = parser.deserializeCredits("src/scraper/inputs/credits_single.json");
    }
}
