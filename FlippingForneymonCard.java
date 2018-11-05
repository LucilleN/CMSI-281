//Lucille Njoo

package forneymon.cardgame;

public class FlippingForneymonCard extends ForneymonCard {
    
    private boolean faceDown;
    
    FlippingForneymonCard() {
        super();
        faceDown = true;
    }
    
    FlippingForneymonCard(String n, String t, boolean f) {
        super(n, t);
        faceDown = f;
    }
    
    public boolean flip() {
        faceDown = !faceDown;
        return faceDown;
    }
    
    public int match(FlippingForneymonCard other) {
        if (this.faceDown || other.faceDown) {
            return 2;
        } else if (this.getName().equals(other.getName()) && this.getType().equals(other.getType())) {
            return 1;
        } else {
            return 0;
        }
    }
    
    public String toString() {
        if (faceDown) {
            return "?: ?";
        } else {
            return super.toString();
        }
    }
    
    public boolean getFaceDown() {
        return faceDown;
    }
    
}



