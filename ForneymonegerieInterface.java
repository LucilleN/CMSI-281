//Lucille Njoo

package forneymonegerie;

public interface ForneymonegerieInterface {

    /**Returns true if the Forneymonegerie has no Forneymon inside.*/
    boolean empty ();
    
    /**Returns the total number of Forneymon in the Forneymonegerie.*/
    int size ();
    
    /**Returns the number of unique Forneymon types in the Forneymonegerie.*/
    int typeSize ();
    
    /**Adds the Forneymon type indicated by typeToAdd to the end of the Forneymonegerie 
     * if typeToAdd is not already in the Forneymonegerie, and returns true, 
     * or updates the count of typeToAdd if it already exists in the collection, and returns false.*/
    boolean collect (String typeToAdd);
    
    /**Removes 1 Forneymon of the given typeToRelease from the Forneymonegerie, 
     * and returns true if at least 1 was removed this way.*/
    boolean release (String typeToRelease);
    
    /**Returns the number of Forneymon of the given typeToCount found in the Forneymonegerie.*/
    int countType (String typeToCount);
    
    /**Removes all Forneymon of the given typeToNuke from the Forneymonegerie.*/
    void releaseType (String typeToNuke);
    
    /**Returns true if the given typeToCheck appears at least once inside of the Forneymonegerie.*/
    boolean contains (String typeToCheck);
    
    /**Returns the type of the nth Forneymon in the Forneymonegerie, 
     * clustered by the order in which their type was collected.*/
    String nth (int n);
    
    /**Returns the ForneymonType that occurs least frequently in the Forneymonegerie, 
     * or the ForneymonType that was collected the most recently in the event of a tie. */
    String rarestType ();
    
    /**Returns a new Forneymonegerie object with the same ForneymonTypes, 
     * number of Forneymon, and in the same collection order.*/
    Forneymonegerie clone ();
    
    /**Swaps the contents of the calling Forneymonegerie and the specified other.*/
    void trade (Forneymonegerie other);
}
