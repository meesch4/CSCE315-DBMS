package query;

import csce315.project1.Credits;
import csce315.project1.Movie;
import query.interfaces.*;
import csce315.project1.Credits;
import csce315.project1.Movie;
import query.interfaces.*;
import types.Type;
import types.Varchar;

import java.util.*;

    /**
     * The internal representation of our database
     * Contains all of the tables, and maybe their rows as well?
     */
public class Query implements ICoverRolesQuery, IDegreeOfSeparationQuery, ITypecastingQuery, ICostarConstellationQuery, IWorstOfBest {
    @Override
    List<Credits.CastMember> calcCostarApperances(String actorName, int namAppearances){

    }
    @Override
    List<Credits.CastMember> calcActorsWhichPlayedCharacter(String characterName){

    }
    @Override
    Movie.Genre calcMostCommonGenre(String actorName){

    }
    @Override
    Object calcDegreeOfSeparation(String actorName1, String actorName2){

    }
    @Override
    Movie calcWorstOfBests(String actorName){
        
    }
}
