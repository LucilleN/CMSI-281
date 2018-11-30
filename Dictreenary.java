//Lucille Njoo Homework 4

package dictreenary;

import java.util.ArrayList;

public class Dictreenary implements DictreenaryInterface {

    // Fields
    // -----------------------------------------------------------
    TTNode root;
    
    // Constructor
    // -----------------------------------------------------------
    Dictreenary () {}
    
    // Methods
    // -----------------------------------------------------------
    
    public boolean isEmpty () {
        return root == null;
    }
    
    public void addWord (String toAdd) {
        String suffix = normalizeWord(toAdd);
        root = addWord(root, suffix);
    }
    
    public boolean hasWord (String query) {
        String word = normalizeWord(query);
        return hasWord(root, word);
    }
    
    public String spellCheck (String query) {
        String normalized = normalizeWord(query);
        if (hasWord(normalized)) { return normalized; }
        String correctedWord;
        for (int i = 0; i < normalized.length() - 1; i++) {
            //if char at index i and index i+1 are the same, switching won't change anything, so just continue
            if (normalized.charAt(i) == normalized.charAt(i+1)) { continue; }
            //swap letters
            correctedWord = normalized.substring(0, i) + normalized.charAt(i+1) + normalized.charAt(i) + normalized.substring(i+2);
            if (hasWord(correctedWord)) {
                return correctedWord;
            }
        }
        return null;
    }
    
    public ArrayList<String> getSortedWords () {
        ArrayList<String> sortedWords = new ArrayList<String>();
        getWords(root, "", sortedWords);
        return sortedWords;
    }

    // Helper Methods
    // -----------------------------------------------------------
    
    private String normalizeWord (String s) {
        // Edge case handling: empty Strings illegal
        if (s == null || s.equals("")) {
            throw new IllegalArgumentException();
        }
        return s.trim().toLowerCase();
    }
    
    /*
     * Returns:
     *   int less than 0 if c1 is alphabetically less than c2
     *   0 if c1 is equal to c2
     *   int greater than 0 if c1 is alphabetically greater than c2
     */
    private int compareChars (char c1, char c2) {
        return Character.toLowerCase(c1) - Character.toLowerCase(c2);
    }
    
    // [!] Add your own helper methods here!
    private TTNode buildDown (String suffix) {
        //MAKE THIS LESS CLUNKY, ESP WITH COMPARISONS REPEATED
        TTNode subTreeRoot = new TTNode(suffix.charAt(0), suffix.length() == 1);
        TTNode current = subTreeRoot;
        for (int i = 1; i < suffix.length(); i++) {
            current.mid = new TTNode(suffix.charAt(i), i == suffix.length()-1);
            current = current.mid;
        }
        return subTreeRoot;
    }
    
    private TTNode addWord(TTNode current, String suffix) {
        //base case:
        if (current == null) {
            current = buildDown(suffix);
            return current;
        }
        
        //recursive cases:
        int charComparison = compareChars(current.letter, suffix.charAt(0));
        if (charComparison == 0) {
            if (suffix.length() == 1) {
                current.wordEnd = true;
            } else {
                current.mid = addWord(current.mid, suffix.substring(1));
            }
            return current;
        } else if (charComparison < 0) {
            current.right = addWord(current.right, suffix);
            return current;
        } else { //if (charComparison > 0) 
            current.left = addWord(current.left, suffix);
            return current;
        }
    }
    
    private boolean hasWord (TTNode current, String word) {
        //Base case: we got to the end of the word/tree and either letters did not match or there somehow was no word end
        if (current == null || word.length() == 0) {
            return false;
        } 
        
        //recursive cases
        int charComparison = compareChars(current.letter, word.charAt(0));
        if (charComparison == 0) {
            if (word.length() == 1 && current.wordEnd) {
                return true;
            }
            return hasWord(current.mid, word.substring(1));
        }
        if (charComparison < 0) {
            return hasWord(current.right, word);
        }
        if (charComparison > 0) {
            return hasWord(current.left, word);
        }
        
        return false;
    }

    private void getWords (TTNode current, String prefix, ArrayList<String> sortedWords) {
        //base case:
        if (current == null) { return; }
        
        //Recursive cases:
        //Case 1: Go left first
        getWords(current.left, prefix, sortedWords);
        
        //Case 2: Go down the middle
        String newPrefix = prefix + current.letter;
        if (current.wordEnd) {
            sortedWords.add(newPrefix);
        }
        getWords(current.mid, newPrefix, sortedWords);
        
        //Case 3: Go right
        getWords(current.right, prefix, sortedWords);
        
    }
    
    // TTNode Internal Storage
    // -----------------------------------------------------------
    
    /*
     * Internal storage of Dictreenary words
     * as represented using a Ternary Tree with TTNodes
     */
    private class TTNode {
        
        boolean wordEnd;
        char letter;
        TTNode left, mid, right;
        
        TTNode (char c, boolean w) {
            letter  = c;
            wordEnd = w;
        }
        
    }
    
}
