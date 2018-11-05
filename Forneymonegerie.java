//Lucille Njoo

package forneymonegerie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.util.Arrays;

public class Forneymonegerie implements ForneymonegerieInterface {

    // Fields
    // ----------------------------------------------------------
    private ForneymonType[] collection;
    private int size;
    private int typeSize;
    private static final int START_SIZE = 16;
    
    
    // Constructor
    // ----------------------------------------------------------
    
    Forneymonegerie () {
        size = 0;
        typeSize = 0;
        collection = new ForneymonType[START_SIZE];
    }
    
    // Methods
    // ----------------------------------------------------------
    public boolean empty () {
        return size == 0;
    }
    
    public int size () {
        return size;
    }
    
    public int typeSize () {
        return typeSize;
    }
    
    public boolean collect (String toAdd) {
        size++;
        int index = getIndexOfType(toAdd);
        if (index != -1) {
            collection[index].count++;
            return false;
        } else {
            checkAndGrow();
            collection[typeSize] = new ForneymonType(toAdd, 1);
            typeSize++;
            return true;
        }        
    }
    
    public boolean release (String toRemove) {
        if (empty()) {
            return false;
        }
        int index = getIndexOfType(toRemove);
        if (index != -1) {
            collection[index].count--;
            size--;
            if (collection[index].count == 0) {
                garbageCollect(index);
            }
            return true;
        }
        return false;
    }
    
    public void releaseType (String toNuke) {
        if (empty()) {
            return;
        }
        int index = getIndexOfType(toNuke);
        if (index != -1) {
            size -= collection[index].count;
            garbageCollect(index);
        }
    }
    
    public int countType (String toCount) {
        int index = getIndexOfType(toCount);
        if (index == -1) {
            return 0;
        }
        return collection[index].count;
    }
    
    public boolean contains (String toCheck) {
        return getIndexOfType(toCheck) != -1;
    }
    
    public String nth (int n) {
        if (n >= size) {
            throw new IllegalArgumentException();
        }
        
        for (int i = 0; i < typeSize; i++) {
            n -= collection[i].count;
            if (n < 0) {
                return collection[i].type;
            }
        }
        return null;
    }
    
    public String rarestType () {
        if (typeSize == 0) {
            return null;
        }
        ForneymonType rarest = collection[0];
        for (int i = 1; i < typeSize; i ++) {
            if (collection[i].count <= rarest.count) {
                rarest = collection[i];
            }
        }
        return rarest.type;
    }
    
    public Forneymonegerie clone () {
        Forneymonegerie copy = new Forneymonegerie();
        copy.size = size;
        copy.typeSize = typeSize;
        copy.collection = new ForneymonType[collection.length];
        for (int i = 0; i < typeSize; i++) {
            copy.collection[i] = new ForneymonType(collection[i].type, collection[i].count);
        }
        return copy;
    }
    
    public void trade (Forneymonegerie other) {
        if (sameCollection(this, other)) {
            return;
        }
        Forneymonegerie placeHolder = new Forneymonegerie();
        placeHolder.size = size;
        placeHolder.typeSize = typeSize;
        placeHolder.collection = collection;
        size = other.size;
        typeSize = other.typeSize;
        collection = other.collection;
        other.size = placeHolder.size;
        other.typeSize = placeHolder.typeSize;
        other.collection = placeHolder.collection;
    }
    
    public String toString () {
        String allForneymons = new String("[ ");
        if (typeSize >= 1) {
            allForneymons += "\"" + collection[0].type + "\": " + collection[0].count;
        }
        if (typeSize > 1) {
            for (int i = 1; i < typeSize; i ++) {
                allForneymons += ", \"" + collection[i].type + "\": " + collection[i].count;
            }
        }
        allForneymons += " ]";
        return allForneymons;
    }
    
    
    // Static methods
    // ----------------------------------------------------------
    public static Forneymonegerie diffMon (Forneymonegerie y1, Forneymonegerie y2) {
        Forneymonegerie clone = y1.clone(); 
        for (int i = 0; i < y2.typeSize; i++) {
            int currentCloneIndex = clone.getIndexOfType(y2.collection[i].type);
            if (currentCloneIndex != -1) {
                if (y2.collection[i].count >= clone.collection[currentCloneIndex].count) {
                    clone.releaseType(y2.collection[i].type);
                } else {
                    clone.collection[currentCloneIndex].count -= y2.collection[i].count;
                    clone.size -= y2.collection[i].count;
                }
            }
        }
        return clone;
    }
    
    public static boolean sameCollection (Forneymonegerie y1, Forneymonegerie y2) {  
        return y1.typeSize() == y2.typeSize() && y1.size() == y2.size() && diffMon(y1, y2).empty();
    }
    
    
    // Private helper methods
    // ----------------------------------------------------------
    
    private int getIndexOfType (String typeName) {
        for (int i = 0; i < typeSize; i++) {
            if (collection[i].type.equals(typeName)) {
                return i;
            }
        }
        return -1;
    }
    
    private void shiftLeft (int index) {
        // Case: index is not at the very end
        if (index < collection.length - 1) {
            for (int i = index; i < collection.length - 1; i++) {
                collection[i] = collection[i+1];
            }
        } 
        // Case: want to shift left but index is already at the very end
//        else if (index == collection.length - 1 ){
//            collection[index] = null;
//        } else {
//            throw new IllegalArgumentException();
//        }
    }
    
    private void checkAndGrow () {
        if (typeSize < collection.length) {
            return;
        }
        ForneymonType[] newCollection = new ForneymonType[collection.length * 2];
        for (int i = 0; i < collection.length; i++) {
            newCollection[i] = collection[i];
        }
        collection = newCollection;
    }

    private void garbageCollect (int index) {
        shiftLeft(index);
        typeSize--;
    }
    
    // Private Classes
    // ----------------------------------------------------------
    private class ForneymonType {
        String type;
        int count;
        
        ForneymonType (String t, int c) {
            type = t;
            count = c;
        }
    }
    
}
