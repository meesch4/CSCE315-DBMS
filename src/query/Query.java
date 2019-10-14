package query;

import dbms.Dbms;
import dbms.TableRootNode;
import query.interfaces.*;
import dbms.RowNode;
import dbms.SqlExecutor;



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
