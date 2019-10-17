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


    @Override
    public List<String> calcCostarAppearances(String actorName, int namAppearances) {

        List<String> output = new ArrayList<>();
        TableRootNode tempTable = sqlExecutor.execute("testTypeCast");
        output = CostarHelper(tempTable);
        System.out.println(output);
        return output;
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
