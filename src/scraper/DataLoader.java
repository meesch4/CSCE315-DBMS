package scraper;

//
//
//

import csce315.project1.Credits;
import csce315.project1.Movie;
import csce315.project1.MovieDatabaseParser;
import dbms.Attribute;
import dbms.Dbms;
import dbms.RowNode;
import dbms.TableRootNode;
import dbms.storage.TableSerializer;
import types.IntType;
import types.Varchar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    public TableRootNode createMovieTable() {
        String tableName = "movies";

        // Columns: id, title, genres(list), rating(Int)
        Attribute idAttr = new Attribute("id", 0, new IntType()); // Primary key for the table
        Attribute titleAttr = new Attribute("title", 1, new Varchar(50)); // What should the length be?

        // List of genre names separated by commas
        Attribute genreAttr = new Attribute("genres", 2, new Varchar(100)); // Arbitrary length, might want to increase

        // 10 point scale(i.e. 7.7), so need to multiple by 100 to make it an int
        Attribute ratingAttr = new Attribute("rating", 3, new IntType());

        ArrayList<Attribute> attributes = new ArrayList<>(
                Arrays.asList(idAttr, titleAttr, genreAttr, ratingAttr)
        );

        ArrayList<Attribute> primaryKeys = new ArrayList<>(Arrays.asList(idAttr));

        return new TableRootNode(tableName, attributes, primaryKeys);
    }

    // Create the attributes & primary keys for the moviesTable
    public TableRootNode createCreditsTable() {
        // Columns: creditID, movieID, name, character/job, actorID, cast/crew
        String tableName = "credits";

        Attribute creditIdAttr = new Attribute("creditID", 0, new Varchar(30)); // Length?
        Attribute movieIdAttr = new Attribute("movieID", 1, new IntType());
        Attribute nameAttr = new Attribute("name", 2, new Varchar(30));
        // If they are crew, then character will be their "job"
         Attribute characterNameAttr = new Attribute("character", 3, new Varchar(30));
         Attribute castOrCrewAttr = new Attribute("isCast", 4, new IntType()); // Boolean, 0 for crew, 1 for cast

        ArrayList<Attribute> attributes = new ArrayList<>(
                Arrays.asList(creditIdAttr, movieIdAttr, nameAttr, characterNameAttr, castOrCrewAttr)
        );

        ArrayList<Attribute> primaryKeys = new ArrayList<>(Arrays.asList(creditIdAttr));

        return new TableRootNode(tableName, attributes, primaryKeys);
    }

    // Deserializes the movies from parser and loads them as rows into table
    public void loadAllMovies(MovieDatabaseParser parser, TableRootNode table, String fileName) throws IOException {
        List<Movie> moviesList = parser.deserializeMovies("src/scraper/inputs/" + fileName + ".json");

        for(Movie movie : moviesList) {
            // Create the row values
            Object[] values = new Object[4];

            values[0] = movie.getId(); // movieId
            values[1] = movie.getTitle();
            values[3] = (int) (movie.getVote_average() * 10);

            StringBuilder genresBuilder = new StringBuilder();
            List<Movie.Genre> genres = movie.getGenres();
            for(int i = 0; i < genres.size(); i++) {
                Movie.Genre genre = genres.get(i);

                genresBuilder.append(genre.getName());
                if(i + 1 < genres.size()) { // If it's not the last one
                    genresBuilder.append(",");
                }
            }

            String genresList = genresBuilder.toString();
            values[2] = genresList;

            // Calling addRow will assign the appropriate primaryKeyIndices to RowNode
            table.addRow(new RowNode(values));
        }
    }

    int i = 0;
    public void loadAllCredits(MovieDatabaseParser parser, TableRootNode table, String fileName) throws IOException {
        List<Credits> creditsList = parser.deserializeCredits("src/scraper/inputs/" + fileName + ".json");

        for(Credits credits : creditsList) {
            int movieId = Integer.parseInt(credits.getId());

            for(Credits.CastMember castMember : credits.getCastMember()) {
                Object[] values = new Object[5];

                values[0] = castMember.getCredit_id();
                values[1] = movieId;
                values[2] = castMember.getName();
                values[3] = castMember.getCharacter();
                values[4] = 1; // isCast, which is true(1)

                table.addRow(new RowNode(values));
            }

            for(Credits.CrewMember crewMember : credits.getCrewMember()) {
                Object[] values = new Object[5];

                values[0] = crewMember.getCredit_id();
                values[1] = movieId;
                values[2] = crewMember.getName();
                values[3] = crewMember.getJob();
                values[4] = 0; // isCast, which is false(0)

                table.addRow(new RowNode(values));

                if(crewMember.getJob().length() == 0) {
                    System.out.println("Job is empty");
                }
            }
        }
    }

    /**
     *  Runs the MovieDatabaseParser and loads the appropriate ro
     */
    public static void main(String args[]) throws IOException {
        DataLoader loader = new DataLoader();
        MovieDatabaseParser parser = new MovieDatabaseParser();

        TableRootNode movieTable = loader.createMovieTable();
        loader.loadAllMovies(parser, movieTable, "movies");
        System.out.println("Done with movies");

        // 117164
        TableRootNode creditsTable = loader.createCreditsTable();
        loader.loadAllCredits(parser, creditsTable, "credits");
        System.out.println("Done with credits");

        TableSerializer.saveToFile(movieTable);
        TableSerializer.saveToFile(creditsTable);
    }
}
