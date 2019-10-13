package query.interfaces;

public interface IDegreeOfSeparationQuery {
    /**
     * Given two actor names, outputs:
     *  - The degree of separation (integer) - no need to actually return, see below
     *  - List of connecting movies - size of this list should be the actual degree of separation
     *  - List of connecting actors
     */
    Object calcDegreeOfSeparation(String actorName1, String actorName2);
}
