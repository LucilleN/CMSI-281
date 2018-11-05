//Lucille Njoo

package forneymon.cardgame;

abstract public class ForneymonCard {
    
    private String type;
    private String name;
    
    ForneymonCard() {
        type = "Burnymon";
        name = "MissingNu";
    }
    
    ForneymonCard(String n, String t) {
        if (t.equals("Burnymon") || t.equals("Dampymon") || t.equals("Leafymon")) {
            name = n;
            type = t;
        } else {
            throw new IllegalArgumentException();
        }
    }
    
    public String toString() {
        return (type + ": " + name);
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }

}
