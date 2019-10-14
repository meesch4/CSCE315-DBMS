package query;

import dbms.*;
import query.interfaces.*;


import java.util.*;

    /**
     * The internal representation of our database
     * Contains all of the tables, and maybe their rows as well?
     */
public class Query implements ICoverRolesQuery, IDegreeOfSeparationQuery, ITypecastingQuery, ICostarConstellationQuery, IWorstOfBestQuery {


    private SqlExecutor sqlExecutor;

    public Query(SqlExecutor sql) {
            this.sqlExecutor = sql;
        }

    ArrayList<String> CostarHelper (TableRootNode tempTable){
         Set<String> nameSet = new HashSet<>();
         ArrayList<String> outputList = new ArrayList<>();
         for(Map.Entry<String, RowNode> rowNodeEntry : tempTable.getRowNodes().entrySet()){ //totally improper syntax, just putting this in as a place holder, before fixing the hashmap for each
             //either way, this is supposed to iterate through all the string values for the actor names.
             RowNode rownode = rowNodeEntry.getValue();
             nameSet.add((String) rownode.getDataField(0)); //just to prevent duplicates.
         }
         for(String name : nameSet){
             outputList.add(name);  //add to the output list
         }
         return outputList;  //this is the final list of actor names who have played the joker.  should be final return value of the query function.
    }
    String typeCast(TableRootNode genre) {
        HashMap<String, int> genreCount;
        HashMap<String, int> movieIds;
        int maxCount = 0;
        String maxGenre = "";
        int genreIndex;
        int movIdIndex;
        for(Attribute att : genre.getAttributes()){
            if(att.getName() == "movieID"){
                movIdIndex = att.index;
            }
        }
        //I definitely fucked up the for each, but that can be fixed.  but other than that, this should be correct.
        for (Map.Entry<String, RowNode> rowEntry : genre.getRowNodes().entrySet()) { //iterate through each credit's genre list
            RowNode row = rowEntry.getValue();
            String genreList = (String) row.getDataField(0); //get the genre list as a string
            String[] genres = genreList.split(","); //split the string by commas, to separate out each individual genre
            ///////////////////////////////////////////////////////////////////
            int MovieId = GET MOVIE ID FROM THE ROW ENTRY.  //need to fix this
            movieIds.put(movieId, 0);
            ///////////////////////////////////////////////////////////////////
            if (!(movieIds.containsKey(movieId))) { //if the movie id has not already been checked ... i.e. this handles Tyler Perry
                {
                    for (String genre : genres) { //for each of these genres, add them to the running count
                        if (genreCount.containsKey(genre)) { //check if the genre count exists
                            int count = genreCount.get(genre); /
                            count++;
                            genreCount.replace(genre, (count));  //add one to the counter for that genre.
                            if (count > maxCount) {
                                maxCount = count;
                                maxGenre = genre; //update the max genre
                            }
                        } else {
                            genreCount.put(genre, 1); //put one as the count if the genre was not already in the genre count list.
                        }
                    }
                }
            }
            return maxGenre;
        }
    }


    @Override
    public List<String> calcCostarAppearances(String actorName, int namAppearances) {
        /**
         * Given a character's name
         * Returns a list of actors that have played that character in a movie
         *
         * Example
         * In: Joker
         * Out: Heath Ledger, Joaquin Phoenix, Jack Nicholson, Mark Hamil, Jared Leto, etc
         */
        //create table of actor names where the credit has the proper character name using this sql command
        //project (actorName) (select (characterName == inputName) credits) //also, actorName is the attribute for the actor name in the credits table, just to be clear.
        //call Costar Helper on the table made by SQL above.
        //return the arraylist made by costarhelper

        List<String> blah = new ArrayList<>();
        TableRootNode tempTable = sqlExecutor.execute("testTypeCast");
        blah = CostarHelper(tempTable);
        System.out.println(blah);
        return blah;
    }

    @Override
    public List<String> calcActorsWhichPlayedCharacter(String characterName){
        List<String> blah = new ArrayList<>();
        return blah;
    }
    @Override
    public String calcMostCommonGenre(String actorName){
        String blah = "";

           //Given an actor's name, returns the most common genre the actor has played in


           //project a table of genre values and movie ids from a table where we've selected all credits containing the input actor's name.

           //project (genre, movieId) (select (actorName == inputName) credits)


           ///Pass this temporary table root node to a java function


        return blah;
    }
    @Override
    public List<String> calcDegreeOfSeparation(String actorName1, String actorName2){
        List<String> blah = new ArrayList<>();
        return blah;
    }
    @Override
    public String calcWorstOfBests(String actorName){
        String blah = "";
        return blah;
    }
}
