package query.interfaces;

import csce315.project1.Movie;

public interface IWorstOfBest {
    /**
     * Given an actor's name, returns the worst ranked movie of the director that directed that actor's best ranked movie
     * Will need to convert the scale from a 10-point to 100-point scale
     */
    Movie calcWorstOfBests(String actorName);
}
