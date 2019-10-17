package query;

import dbms.*;
import query.interfaces.*;


import java.util.*;

/**
 * The internal representation of our database
 * Contains all of the tables, and maybe their rows as well?
 */
public class Query implements IDegreeOfSeparationQuery, ITypecastingQuery, ICostarConstellationQuery {

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
    //String typeCast(TableRootNode genreTable) {
    //    HashMap<String, Integer> genreCount = new HashMap<>();
    //    HashMap<Integer, Integer> movieIds = new HashMap<>();
    //    int maxCount = 0;
    //    String maxGenre = "";
    //    int genreIndex = -1;
    //    int movIdIndex = -1;
    //    for(Attribute att : genreTable.getAttributes()){
    //        if(att.getName() == "id"){
    //            movIdIndex = att.getIndex();
    //        }
    //        if(att.getName() == "genres"){
    //            genreIndex = att.getIndex();
    //        }
    //    }
    //    if(genreIndex != -1 && movIdIndex != -1) { //prevents searching through tables that were improperly constructed
    //        for (Map.Entry<String, RowNode> rowEntry : genreTable.getRowNodes().entrySet()) { //iterate through each movie's genre list
    //            RowNode row = rowEntry.getValue();
    //            String genreList = (String) row.getDataField(genreIndex); //get the genre list as a string
    //            String[] genres = genreList.split(","); //split the string by commas, to separate out each individual genre
//
    //            int MovieId = (int) row.getDataField(movIdIndex);
//
    //            if (!(movieIds.containsKey(MovieId))) { //if the movie id has not already been checked ... i.e. this handles Tyler Perry
    //                {
    //                    for (String genre : genres) { //for each of these genres, add them to the running count
    //                        if (genreCount.containsKey(genre)) { //check if the genre count exists
    //                            int count = genreCount.get(genre);
    //                            count++;
    //                            genreCount.replace(genre, (count));  //add one to the counter for that genre.
    //                            if (count > maxCount) {
    //                                maxCount = count;
    //                                maxGenre = genre; //update the max genre
    //                            }
    //                        } else {
    //                            genreCount.put(genre, 1); //put one as the count if the genre was not already in the genre count list.
    //                        }
    //                    }
    //                }
    //            }
    //            movieIds.put(MovieId, 0);
    //        }
    //    }else{
    //        maxGenre = "";
    //        System.out.println("Table projection/selection failed.");
    //    }
    //    return maxGenre;
    //}


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
    public String calcMostCommonGenre(String actorName){
        TableRootNode movieIDs = sqlExecutor.execute("GetAllActorMovies", actorName);
        Set<Object> IDs = new HashSet<Object>();
        for(Map.Entry<String, RowNode> rowEntry : movieIDs.getRowNodes().entrySet()){
            RowNode row = rowEntry.getValue();
            for(Object a : row.getDataFields()) {
                IDs.add(a);
            }
        }
        HashMap<String, Integer> genreCount = new HashMap<String,Integer>();

        //The following may seem like it has a ton of unnecessary nested loops, but I wasn't sure how best to read
        //the single element in the hashmaps without just using a for each loop.
        //most of these loops have a single iteration, so it's going to run in O(n*1*1...)

        for(Object a : IDs){
            TableRootNode genres = sqlExecutor.execute("GetGenreByMovieID", a);
            RowNode row = null;
            for(Map.Entry<String, RowNode> rowEntry : genres.getRowNodes().entrySet()){
                row = rowEntry.getValue();
            }
            if(row != null){
                for(Object genresField : row.getDataFields()){
                    String genresString = (String) genresField;
                    String[] genreList = genresString.split(",");
                    for(String genre : genreList){
                        if(genreCount.containsKey(genre)){
                            int count = genreCount.get(genre);
                            count += 1;
                            genreCount.replace(genre, count);
                        }else{
                            genreCount.put(genre,1);
                        }
                    }
                }
            }
        }
        Integer max = null;
        String maxString = "";
        for(Map.Entry<String, Integer> entry : genreCount.entrySet()){
            if(max == null){
                max = entry.getValue();
                maxString = entry.getKey();
            }else if(max < entry.getValue()){
                max = entry.getValue();
                maxString = entry.getKey();
            }
        }

        return maxString;
    }

    // Don't need to implement
    @Override
    public List<String> calcDegreeOfSeparation(String actorName1, String actorName2){
        List<String> blah = new ArrayList<>();
        return blah;
    }
}
