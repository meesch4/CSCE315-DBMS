package query.interfaces;

import csce315.project1.*;

public interface ITypecastingQuery {
    /**
     *  Given an actor's name, returns the most common genre the actor has played in
     */
    Movie.Genre calcMostCommonGenre(String actorName);
}
