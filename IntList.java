//Lucille Njoo

package intlist;

public class IntList {

    // Fields
    private int[] items;
    private int   size;
    private static final int START_SIZE = 8;
    
    // Constructor
    IntList () {
        items = new int[START_SIZE];
        size  = 0;
    }
    
    public int getSize() {
        return size;
    }

    public int getAt(int index) {
        indexValidityCheck(index);
        return items[index];
    }

    public void append(int toAdd) {
        checkAndGrow();
        items[size] = toAdd;
        size++;
    }
    
    public void prepend (int toAdd) {
        insertAt(toAdd, 0);
    }

    public void insertAt (int toAdd, int index) {
        if (index == size) {
            append(toAdd);
        } else {
            indexValidityCheck(index);
            checkAndGrow();
            shiftRight(index);
            items[index] = toAdd;
            size++;  
        }
    }
    
    public void removeAll (int toRemove) {
        for (int i = 0; i < size; i++) {
            if (items[i] == toRemove) {
                removeAt(i);
                i--;
            }
        }
    }

    public void removeAt(int index) {
        indexValidityCheck(index);
        shiftLeft(index);
        size--;
    }
    
    public String toString () {
        String list = new String("");
        list += items[0];
        for (int i = 1; i < size; i++) {
            list += ", " + items[i];
        }
        return list;
    }
    
    private void indexValidityCheck (int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    /*
     * Expands the size of the list whenever it is at
     * capacity
     */
    private void checkAndGrow () {
        // Case: big enough to fit another item, so no
        // need to grow
        if (size < items.length) {
            return;
        }
        
        // Case: we're at capacity and need to grow
        // Step 1: create new, bigger array; we'll
        // double the size of the old one
        int[] newItems = new int[items.length * 2];
        
        // Step 2: copy the items from the old array
        for (int i = 0; i < items.length; i++) {
            newItems[i] = items[i];
        }
        
        // Step 3: update IntList reference
        items = newItems;
    }
    
    /*
     * Shifts all elements to the right of the given
     * index one left
     */
    private void shiftLeft (int index) {
        for (int i = index; i < size-1; i++) {
            items[i] = items[i+1];
        }
    }
    
    /*
     * Shifts all elements to the right of the given
     * index one right
     */
    private void shiftRight (int index) {
        for (int i = size; i > index; i--) {
            items[i] = items[i-1];
        }
    }
    
    
    public static void main (String[] args) {
        IntList inty = new IntList();
        
        inty.append(3);
        inty.append(6);
        inty.append(8);
        inty.append(2);
        //3, 6, 8, 2
        System.out.println(inty.toString());
        
        inty.prepend(5);
        inty.prepend(0);
        //0, 5, 3, 6, 8, 2
        System.out.println(inty.toString());
        
        inty.insertAt(7, 1);
        //0, 7, 5, 3, 6, 8, 2
        System.out.println(inty.toString());
        
        inty.removeAt(3);
        //0, 7, 5, 6, 8, 2
        System.out.println(inty.toString());
        
        inty.insertAt(5, 3);
        //0, 7, 5, 5, 6, 8, 2
        inty.insertAt(5, 6);
        //0, 7, 5, 5, 6, 8, 5, 2
        System.out.println(inty.toString());
        
        inty.removeAll(5);
        //0, 7, 6, 8, 2
        System.out.println(inty.toString());
        
        System.out.println(inty.getAt(0)); //0
        System.out.println(inty.getAt(4)); //2
        System.out.println(inty.getAt(2)); //6
        
        try {
            inty.insertAt(9, 10);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("threw exception");
        }
        
        inty.insertAt(10, 5); //should simply append
        //0, 7, 6, 8, 2, 10
        System.out.println(inty.toString());
    }
    
}