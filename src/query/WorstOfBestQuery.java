package query;

import dbms.Dbms;
import dbms.RowNode;
import dbms.SqlExecutor;
import dbms.TableRootNode;
import query.interfaces.IWorstOfBestQuery;

public class WorstOfBestQuery implements IWorstOfBestQuery {
    private SqlExecutor executor;

    public WorstOfBestQuery(SqlExecutor executor) {
        this.executor = executor;
    }

    /**
     * Given an actor's name, returns the worst ranked movie of the director that directed that actor's best ranked movie
     */
    @Override
    public String calcWorstOfBests(String actorName) {
        // Get actors best movie
        int bestMovieID = getMembersBestWorstMovie(actorName, true);

        // Get the director for it
        String director = "";

        // Get directors' worst movie
        int worstMovie = getMembersBestWorstMovie(director, false);

        String worstMovieName = null; // Need its name

        return worstMovieName;
    }

    // Returns the actor's best rated movie(movieID)
    public int getMembersBestWorstMovie(String name, boolean getBest) {
        //  1) Get all of the actors movies
        TableRootNode actorMovies = executor.execute("GetAllActorMovies", name);

        //  2) Get the corresponding movies from movies DB and get it's rating, then compare it with the others
        int goalRating = getBest ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int goalMovie = -1; // Movie ID of the best movie
        for(RowNode movie : actorMovies.getRowNodes().values()) {
            int movieID = (int) movie.getDataField(0);

            TableRootNode ratingTable = executor.execute("worstofbest/GetMovieRating", movieID);
            int rating = -1;

            for(RowNode node : ratingTable.getRowNodes().values()) {
                rating = (int) node.getDataField(0);
            }

            if(rating == -1) {
                System.err.println("getMembersBestWorstMovie Error: Couldn't retrieve rating!");
            }

            if(getBest) {
                if(rating > goalRating) {
                    goalRating = rating;
                    goalMovie = movieID;
                }
            } else {
                if(rating < goalRating) {
                    goalRating = rating;
                    goalMovie = movieID;
                }
            }
        }

        System.out.println("Movie " + goalMovie + " Rating " + goalRating);

        return goalMovie;
    }

    public String getDirectorOfMovie(int movieID) {
        TableRootNode directorTable = executor.execute("worstofbest/GetDirector", movieID);
        String director = null;
        for(RowNode row : directorTable.getRowNodes().values()) {
            director = row.getDataField(0).toString();
        }

        return director;
    }
}
