package query.interfaces;

import csce315.project1.*;

public interface ITypecastingQuery {
    /**
     *  Given an actor's name, returns the most common genre the actor has played in
     *
     *
     *  //project a table of genre values and movie ids from a table where we've selected all credits containing the input actor's name.
     *
     *  project (genre, movieId) (select (actorName == inputName) credits)
     *
     *
     *  ///Pass this temporary table root node to a java function
     *  String typeCast(TableRootNode genre){
     *      HashMap<String, int> genreCount;
     *      HashMap<String, int> movieIds;
     *      int maxCount = 0;
     *      String maxGenre = "";
     *      //I definitely fucked up the for each, but that can be fixed.  but other than that, this should be correct.
     *      for(genre.Children.Entry<String, RowNode> rowEntry : tableRows.entrySet()) { //iterate through each credit's genre list
     *          String genreList = rowEntry.getValue(); //get the genre list as a string
     *          String[] genres = genreList.split(","); //split the string by commas, to separate out each individual genre
     *
     *          ///////////////////////////////////////////////////////////////////
     *          int MovieId = GET MOVIE ID FROM THE ROW ENTRY.  //need to fix this
     *          movieIds.put(movieId, 0);
     *          ///////////////////////////////////////////////////////////////////
     *          if(!(movieIds.containsKey(movieId))){ //if the movie id has not already been checked ... i.e. this handles Tyler Perry
     *          {
     *              for(String genre : genres){ //for each of these genres, add them to the running count
     *                  if(genreCount.containsKey(genre)){ //check if the genre count exists
     *                      int count = genreCount.get(genre); /
     *                      count++;
     *                      genreCount.replace(genre, (count));  //add one to the counter for that genre.
     *                      if(count > maxCount){
     *                          maxCount = count;
     *                          maxGenre = genre; //update the max genre
     *                      }
     *                  }else{
     *                      genreCount.put(genre, 1); //put one as the count if the genre was not already in the genre count list.
     *                  }
     *              }
     *          }
     *      }
     *      return maxGenre;
     *
     *  }
     */
    Movie.Genre calcMostCommonGenre(String actorName);
}
