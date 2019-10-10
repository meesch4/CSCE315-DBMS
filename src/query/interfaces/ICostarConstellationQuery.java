package query.interfaces;

import csce315.project1.*;

import java.util.List;

public interface ICostarConstellationQuery {
    /**
     *  Given an actor's name and an integer x, returns a list of other actors
     *  that appeared in x movies with that actor
     */
    List<Credits.CastMember> calcCostarApperances(String actorName, int namAppearances);
}
