package scraper;

//
//
//

/**
 * Most likely rename this at some point
 * This is basically going to use the MovieDatabaseParser, convert the data points into tables,
 * then move those tables into Dbms(or rather, save those tables to a file to be loaded later, using serialization)
 */
public class DataLoader {
}

// Won't actually be here, just for brainstorming of what we need
interface IDataLoader {
    // Will have a main that we only run once to do the scraping and convew
    void main();
}
