//Lucille Njoo

package linked_forneymonegerie;

import java.util.NoSuchElementException;

public class LinkedForneymonegerie implements LinkedForneymonegerieInterface {

    // Fields
    // -----------------------------------------------------------
    private ForneymonType head, tail;
    private int size, typeSize, modCount;
    
    
    // Constructor
    // -----------------------------------------------------------
    LinkedForneymonegerie () {
        size = 0;
        typeSize = 0;
        modCount = 0;
        head = null;
        tail = head;
    }
    
    
    // Methods
    // -----------------------------------------------------------
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
        return insertForneymon(toAdd, 1);
    }
    
    public boolean release (String toRemove) {
        return removeForneymon(toRemove, 1);
    }
    
    public void releaseType (String toNuke) {
        ForneymonType current = findForneymonType(toNuke);
        if (current != null) {
            removeForneymon(toNuke, current.count);
        }
    }
    
    public int countType (String toCount) {
        ForneymonType current = findForneymonType(toCount);
        if (current == null) {
            return 0;
        }
        return current.count;
    }
    
    public boolean contains (String toCheck) {
        return findForneymonType(toCheck) != null;
    }
    
    public String rarestType () {
        if (typeSize == 0) {
            return null;
        }
        ForneymonType rarest = head;
        ForneymonType current = head;
        for (int i = 0; i < typeSize; i++) {
            if (current.count <= rarest.count) {
                rarest = current;
            }
            current = current.next;
        }
        return rarest.type;
    }
    
    public LinkedForneymonegerie clone () {
        LinkedForneymonegerie copy = new LinkedForneymonegerie();
        if (empty()) {
            return copy;
        }
        ForneymonType originalCurrent = head;
        while (originalCurrent != null) {
            copy.insertForneymon(originalCurrent.type, originalCurrent.count);
            originalCurrent = originalCurrent.next;
        }
        return copy;
    }
    
    public void trade (LinkedForneymonegerie other) {
        ForneymonType holdHead = head;
        ForneymonType holdTail = tail;
        int holdSize = size;
        int holdTypeSize = typeSize;
        head = other.head;
        tail = other.tail;
        size = other.size;
        typeSize = other.typeSize;
        other.head = holdHead;
        other.tail = holdTail;
        other.size = holdSize;
        other.typeSize = holdTypeSize;
        modCount++;
        other.modCount++;
    }
    
    public Iterator getIterator () {
        if (empty()) {
            throw new IllegalStateException();
        }
        return new Iterator(this);
    }
    
    public String toString () {
        String[] result = new String[typeSize];
        ForneymonType current = head;
        for (int i = 0; i < typeSize; i++) {
            result[i] = "\"" + current.type + "\": " + current.count;
            current = current.next;
        }
        return "[ " + String.join(", ", result) + " ]";
    }
    
    
    // -----------------------------------------------------------
    // Static methods
    // -----------------------------------------------------------
    
    public static LinkedForneymonegerie diffMon (LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
        LinkedForneymonegerie clone = y1.clone();
        ForneymonType cloneMatch;
        for (ForneymonType y2Current = y2.head; y2Current != null; y2Current = y2Current.next) {
            cloneMatch = clone.findForneymonType(y2Current.type);
            if (cloneMatch != null) {
                clone.removeForneymon(cloneMatch.type, y2Current.count);
            }
        }
        return clone;
    }
    
    public static boolean sameCollection (LinkedForneymonegerie y1, LinkedForneymonegerie y2) {
        return y1.typeSize() == y2.typeSize() && y1.size() == y2.size() && diffMon(y1, y2).empty();
    }
    
    // Private helper methods
    // -----------------------------------------------------------

    private ForneymonType findForneymonType (String typeToFind) {
        for (ForneymonType n = head; n != null; n = n.next) {
            if (n.type.equals(typeToFind)) {
                return n;
            }
        }
        return null;
    }
    
    private void garbageCollect (ForneymonType toRemove) {
        if (toRemove.prev != null) {
            toRemove.prev.next = toRemove.next;
        } 
        if (toRemove.next != null) {
            toRemove.next.prev = toRemove.prev;
        }
        if (toRemove == head) {
            head = head.next;
        }
        if (toRemove == tail) {
            tail = tail.prev;
        }
        typeSize--;
    }
    
    private boolean insertForneymon (String typeToAdd, int countToAdd) {
        modCount++;
        size += countToAdd;
        ForneymonType existingType = findForneymonType(typeToAdd);
        //Case: the ForneymonType typeToAdd does not exist in the list
        if (existingType == null) {
            ForneymonType newType = new ForneymonType(typeToAdd, countToAdd);
            if (size == countToAdd) { //if the LinkedForneymonegerie was empty to begin with
                head = newType;
            }
            else if (tail != null) {
                tail.next = newType;
            }
            newType.prev = tail;
            tail = newType;
            typeSize++;
            return true;
        }
        //Case: the ForneymonType typeToAdd already exists in the list
        existingType.count += countToAdd;
        return false;
    }
    
    private boolean removeForneymon (String typeToRemove, int countToRemove) {
        ForneymonType current = findForneymonType(typeToRemove);
        if (current != null) {
            modCount++;
            int newCount = current.count - countToRemove;
            if (newCount <= 0) {
                size -= current.count;
                garbageCollect(current);
            } else {
                current.count = newCount;
                size -= countToRemove;
            }
            return true;
        }
        return false;
    }
    
        
    // Inner Classes
    // -----------------------------------------------------------
    
    public class Iterator implements LinkedForneymonegerieIteratorInterface {
        LinkedForneymonegerie owner;
        ForneymonType current;
        private int itModCount;
        private int index;
        
        Iterator (LinkedForneymonegerie y) {
            owner = y;
            current = head;
            itModCount = owner.modCount;
            index = 0;
        }
        
        public boolean hasNext () {
            if (!isValid()) {
                return false;
            }
            return !(index == current.count - 1 && current.next == null);

        }
        
        public boolean hasPrev () {
            if (!isValid()) {
                return false;
            }
            return ((index > 0) || (current.prev != null));
        }
        
        public boolean isValid () {
            return itModCount == owner.modCount;
        }
        
        public String getType () {
            return current.type;
        }

        public void next () {
            iteratorValidityThrow();
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            //Case: currently at the last Forneymon in a certain type
            if (index == current.count - 1) {
                current = current.next;
                index = 0;
            }
            //Case: not at the last Forneymon in a certain type
            else {
                index++;
            }   
        }
        
        public void prev () {
            iteratorValidityThrow();
            if (!hasPrev()) {
                throw new NoSuchElementException();
            }
            //Case: currently at the first Forneymon in a certain ForneymonType
            if (index == 0) {
                current = current.prev;
                index = current.count - 1;
            }
            //Case: not at the first Forneymon in a certain ForneymonType
            else {
                index--;
            }   
        }
        
        public void replaceAll (String toReplaceWith) {
            iteratorValidityThrow();
            //Case: already pointing to the type that is the same as toReplaceWith
            if (toReplaceWith.equals(current.type)) {
                return;
            }
            ForneymonType preexisting = owner.findForneymonType(toReplaceWith);
            itModCount+=2;
            insertForneymon(toReplaceWith, current.count);    
            removeForneymon(current.type, current.count);
            current = preexisting != null ? preexisting : owner.findForneymonType(toReplaceWith);   

        }
        
        private void iteratorValidityThrow() {
            if (!isValid()) {
                throw new IllegalStateException();
            }
        }
        
    }
    
    private class ForneymonType {
        ForneymonType next, prev;
        String type;
        int count;
        
        ForneymonType (String t, int c) {
            type = t;
            count = c;
        }
    }

}