package query.interfaces;

import csce315.project1.Credits;

import java.util.List;

public interface ICoverRolesQuery {
    /**
     * Given a character's name
     * Returns a list of actors that have played that character in a movie
     *
     * Example
     * In: Joker
     * Out: Heath Ledger, Joaquin Phoenix, Jack Nicholson, Mark Hamil, Jared Leto, etc
     *
     * //create table of actor names where the credit has the proper character name using this sql command
     * project (actorName) (select (characterName == inputName) credits) //also, actorName is the attribute for the actor name in the credits table, just to be clear.
     *
     * ArrayList<String>(TableRootNode tempTable){
     *      Set<String> nameSet = new Set<>();
     *      ArrayList<String> outputlist = new ArrayList<>();
     *      for(each RowNode : tempTable.children){ //totally improper syntax, just putting this in as a place holder, before fixing the hashmap for each
     *      //either way, this is supposed to iterate through all the string values for the actor names.
     *          nameSet.add(rownode.value); //just to prevent duplicates.
     *      }
     *      for(each name : outputSet){
     *          outputList.add(name);  //add to the output list
     *      }
     *      return outputList;  //this is the final list of actor names who have played the joker.  should be final return value of the query function.
     * }
     */
    List<Credits.CastMember> calcActorsWhichPlayedCharacter(String characterName);
}
