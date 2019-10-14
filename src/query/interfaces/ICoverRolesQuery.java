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
     */
    List<String> calcActorsWhichPlayedCharacter(String characterName);
}
