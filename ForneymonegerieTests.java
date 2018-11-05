//Lucille Njoo

package forneymonegerie;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ForneymonegerieTests {
    
    // =================================================
    // Test Configuration
    // =================================================
    
    // Used as the basic empty menagerie to test; the @Before
    // method is run before every @Test
    Forneymonegerie fm;
    @Before
    public void init () {
        fm = new Forneymonegerie();
    }

    
    // =================================================
    // Unit Tests
    // =================================================
    
    @Test
    public void testEmpty() {
        assertTrue(fm.empty());
        
        fm.collect("Dampymon");
        assertFalse(fm.empty());
        
        fm.collect("Dampymon");
        fm.releaseType("Dampymon");
        assertTrue(fm.empty());
        
        fm.collect("Dampymon");
        fm.release("Dampymon");
        assertTrue(fm.empty());
    }
    
    @Test
    public void testSize() {
        //size increases properly with collect
        assertEquals(0, fm.size());
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(2, fm.size());
        fm.collect("Burnymon");
        assertEquals(3, fm.size());
        
        //size decreases properly with release
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertEquals(6, fm.size());
        fm.release("Dampymon");
        assertEquals(5, fm.size());
        fm.release("Burnymon");
        assertEquals(4, fm.size());
        fm.release("Leafymon");
        assertEquals(4, fm.size());
        
        //size decreases properly with releaseType
        fm.releaseType("Dampymon");
        assertEquals(1, fm.size());
        fm.releaseType("Leafymon");
        assertEquals(1, fm.size());
        fm.releaseType("Burnymon");
        assertEquals(0, fm.size());
    }

    @Test
    public void testTypeSize() {
        assertEquals(0, fm.typeSize());
        
        //typeSize increases properly with collect
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(1, fm.typeSize());
        fm.collect("Burnymon");
        assertEquals(2, fm.typeSize());
        fm.collect("Burnymon");
        assertEquals(2, fm.typeSize());
        
        //typeSize decreases properly with release and releaseType
        fm.releaseType("Dampymon"); 
        assertEquals(1, fm.typeSize());
        
        fm.releaseType("Leafymon");
        assertEquals(1, fm.typeSize());
        
        fm.collect("Zappymon");
        assertEquals(2, fm.typeSize());
        
        fm.release("Burnymon");
        fm.release("Burnymon");
        assertEquals(1, fm.typeSize());
    }

    @Test
    public void testCollect() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm.contains("Burnymon"));
        
        assertEquals(3, fm.size());
        assertEquals(2, fm.typeSize());
        
        //returns correct booleans
        assertFalse(fm.collect("Dampymon"));
        assertTrue(fm.collect("Zappymon"));
        fm.releaseType("Dampymon");
        assertTrue(fm.collect("Dampymon"));  
    }

    @Test
    public void testRelease() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        assertEquals(2, fm.size());
        assertEquals(1, fm.typeSize());
        fm.release("Dampymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
        
        fm.release("Dampymon");
        assertEquals(0, fm.size());
        assertEquals(0, fm.typeSize());
        
        //returns correct booleans
        fm.collect("Dampymon");
        assertTrue(fm.release("Dampymon"));
        assertFalse(fm.release("Dampymon"));
        fm.collect("Burnymon");
        assertFalse(fm.release("Zappymon"));
    }

    @Test
    public void testReleaseType() {
        fm.releaseType("Dampymon");
        assertEquals(0, fm.size());
        assertEquals(0, fm.typeSize());
        
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertEquals(3, fm.size());
        assertEquals(2, fm.typeSize());
        fm.releaseType("Dampymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
        
        fm.releaseType("Zappymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
    }
    
    @Test
    public void testCountType() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertEquals(2, fm.countType("Dampymon"));
        assertEquals(1, fm.countType("Burnymon"));
        assertEquals(0, fm.countType("forneymon"));
        
        fm.release("Dampymon");
        assertEquals(1, fm.countType("Dampymon"));
        fm.release("Dampymon");
        assertEquals(0, fm.countType("Dampymon"));
        
        fm.collect("Zappymon");
        fm.collect("Zappymon");
        assertEquals(2, fm.countType("Zappymon"));
        fm.releaseType("Zappymon");
        assertEquals(0, fm.countType("Zappymon"));
    }

    @Test
    public void testContains() {
        assertFalse(fm.contains("Dampymon"));
        
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        assertTrue(fm.contains("Dampymon"));
        assertTrue(fm.contains("Burnymon"));
        assertFalse(fm.contains("forneymon"));
        
        fm.releaseType("Dampymon");
        assertFalse(fm.contains("Dampymon"));
        fm.collect("Zappymon");
        assertTrue(fm.contains("Zappymon"));
        fm.release("Zappymon");
        assertFalse(fm.contains("Zappymon"));
    }

    @Test
    public void testNth() {
        boolean exceptionThrown = false;
        try {
            fm.nth(0);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
        
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        fm.collect("Zappymon");
        fm.collect("Dampymon");
        assertEquals("Dampymon", fm.nth(0));
        assertEquals("Dampymon", fm.nth(1));
        assertEquals("Burnymon", fm.nth(2));
        assertEquals("Zappymon", fm.nth(3));
        
        exceptionThrown = false;
        try {
            fm.nth(4);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
        
        fm.collect("Burnymon");
        assertEquals("Burnymon", fm.nth(3));
        assertEquals("Zappymon", fm.nth(4));
        
        fm.releaseType("Burnymon");
        assertEquals("Dampymon", fm.nth(0));
        assertEquals("Dampymon", fm.nth(1));
        assertEquals("Zappymon", fm.nth(2));
    }

    @Test
    public void testRarestType() {
        assertEquals(null, fm.rarestType());
        
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Zappymon");
        assertEquals("Zappymon", fm.rarestType());
        fm.collect("Zappymon");
        assertEquals("Zappymon", fm.rarestType());
        
        fm.collect("Zappymon");
        assertEquals("Dampymon", fm.rarestType());

        fm.collect("Burnymon");
        assertEquals("Burnymon", fm.rarestType());
        
        //in event of a tie, will return most recently collected ForneymonType
        fm.collect("Leafymon");
        assertEquals("Leafymon", fm.rarestType());
        fm.collect("Windymon");
        assertEquals("Windymon", fm.rarestType());
    }

    @Test
    public void testClone() {
        fm.collect("Dampymon");
        fm.collect("Dampymon");
        fm.collect("Burnymon");
        Forneymonegerie dolly = fm.clone();
        assertEquals(dolly.countType("Dampymon"), 2);
        assertEquals(dolly.countType("Burnymon"), 1);
        
        assertEquals(fm.size(), dolly.size());
        assertEquals(fm.typeSize(), dolly.typeSize());
        assertEquals(fm.nth(0), dolly.nth(0));
        assertEquals(fm.nth(1), dolly.nth(1));
        assertEquals(fm.nth(2), dolly.nth(2));
        
        dolly.collect("Zappymon");
        assertFalse(fm.contains("Zappymon"));
        
        fm.releaseType("Dampymon");
        assertEquals(1, fm.size());
        assertEquals(1, fm.typeSize());
        assertEquals(4, dolly.size());
        assertEquals(3, dolly.typeSize());
        
        //clone of a clone works as expected
        Forneymonegerie molly = dolly.clone();
        assertEquals("[ \"Dampymon\": 2, \"Burnymon\": 1, \"Zappymon\": 1 ]", molly.toString());
        assertEquals("[ \"Dampymon\": 2, \"Burnymon\": 1, \"Zappymon\": 1 ]", dolly.toString());
        assertEquals(2, molly.countType("Dampymon"));
        assertEquals(1, molly.countType("Burnymon"));
        assertEquals(1, molly.countType("Zappymon"));
        dolly.releaseType("Zappymon");
        assertTrue(molly.contains("Zappymon"));
    }

    @Test
    public void testTrade() {
        Forneymonegerie fm1 = new Forneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonegerie fm2 = new Forneymonegerie();
        fm2.collect("Zappymon");
        fm2.collect("Leafymon");
        fm1.trade(fm2);
        assertTrue(fm1.contains("Zappymon"));
        assertTrue(fm1.contains("Leafymon"));
        
        assertEquals(2, fm1.size());
        assertEquals(2, fm1.typeSize());
        
        assertTrue(fm2.contains("Dampymon"));
        assertTrue(fm2.contains("Burnymon"));
        assertFalse(fm1.contains("Dampymon"));
        
        assertEquals(3, fm2.size());
        assertEquals(2, fm2.typeSize());
        
        //changes to fm2 do not affect fm1
        fm2.collect("Windymon");
        assertEquals(4, fm2.size());
        assertEquals(3, fm2.typeSize());
        assertFalse(fm1.contains("Windymon"));
    }

    @Test
    public void testToString() {
        assertEquals("[  ]", fm.toString());
        
        fm.collect("Burnymon");
        assertEquals("[ \"Burnymon\": 1 ]", fm.toString());
        fm.collect("Dampymon");
        assertEquals("[ \"Burnymon\": 1, \"Dampymon\": 1 ]", fm.toString());
        fm.collect("Burnymon");
        assertEquals("[ \"Burnymon\": 2, \"Dampymon\": 1 ]", fm.toString());
        fm.collect("Zappymon");
        assertEquals("[ \"Burnymon\": 2, \"Dampymon\": 1, \"Zappymon\": 1 ]", fm.toString());
        fm.collect("Dampymon");
        assertEquals("[ \"Burnymon\": 2, \"Dampymon\": 2, \"Zappymon\": 1 ]", fm.toString());

        fm.release("Burnymon");
        assertEquals("[ \"Burnymon\": 1, \"Dampymon\": 2, \"Zappymon\": 1 ]", fm.toString());
        fm.releaseType("Dampymon");
        assertEquals("[ \"Burnymon\": 1, \"Zappymon\": 1 ]", fm.toString());
        
        fm.collect("Dampymon");
        assertEquals("[ \"Burnymon\": 1, \"Zappymon\": 1, \"Dampymon\": 1 ]", fm.toString());
    }
    
    @Test
    public void testDiffMon() {
        Forneymonegerie fm1 = new Forneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonegerie fm2 = new Forneymonegerie();
        fm2.collect("Dampymon");
        fm2.collect("Zappymon");
        Forneymonegerie fm3 = Forneymonegerie.diffMon(fm1, fm2);
        assertEquals(fm3.countType("Dampymon"), 1);
        assertEquals(fm3.countType("Burnymon"), 1);
        assertFalse(fm3.contains("Zappymon"));
        
        assertEquals("[ \"Dampymon\": 1, \"Burnymon\": 1 ]", fm3.toString());
        
        fm3.collect("Leafymon");
        assertFalse(fm1.contains("Leafymon"));
        assertFalse(fm2.contains("Leafymon"));
        
        //diffMon should be empty for two identical Forneymonegeries
        Forneymonegerie fm4 = fm3.clone();
        assertEquals("[ \"Dampymon\": 1, \"Burnymon\": 1, \"Leafymon\": 1 ]", fm4.toString());
        Forneymonegerie shouldBeEmpty = Forneymonegerie.diffMon(fm4,  fm3);
        System.out.println(fm3.toString());
        System.out.println(fm3.size());
        System.out.println(fm3.typeSize());
        
        System.out.println(fm4.toString());
        System.out.println(fm4.size());
        System.out.println(fm4.typeSize());
        
        System.out.println(shouldBeEmpty.toString());
        System.out.println(shouldBeEmpty.size());
        System.out.println(shouldBeEmpty.typeSize());
        assertTrue(Forneymonegerie.diffMon(fm4, fm3).empty());
        
        Forneymonegerie fm5 = new Forneymonegerie();
        fm5.collect("Dampymon");
        fm5.collect("Dampymon");
        fm5.collect("Burnymon");
        Forneymonegerie fm6 = new Forneymonegerie();
        fm6.collect("Dampymon");
        fm6.collect("Dampymon");
        fm6.collect("Zappymon");
        Forneymonegerie fm7 = Forneymonegerie.diffMon(fm5, fm6);
        assertFalse(fm7.contains("Dampymon"));
        assertEquals("[ \"Burnymon\": 1 ]", fm7.toString());
    }

    @Test
    public void testSameForneymonegerie() {
        Forneymonegerie fm1 = new Forneymonegerie();
        fm1.collect("Dampymon");
        fm1.collect("Dampymon");
        fm1.collect("Burnymon");
        Forneymonegerie fm2 = new Forneymonegerie();
        fm2.collect("Burnymon");
        fm2.collect("Dampymon");
        fm2.collect("Dampymon");
        assertTrue(Forneymonegerie.sameCollection(fm1, fm2));
        assertTrue(Forneymonegerie.sameCollection(fm2, fm1));
        fm2.collect("Leafymon");
        assertFalse(Forneymonegerie.sameCollection(fm1, fm2));
        
        //clones are the same Forneymonegerie
        Forneymonegerie fm3 = fm1.clone();
        assertTrue(Forneymonegerie.sameCollection(fm1, fm3));
        assertTrue(Forneymonegerie.sameCollection(fm3, fm1));
        
        //both are empty
        Forneymonegerie fm4 = new Forneymonegerie();
        Forneymonegerie fm5 = new Forneymonegerie();
        assertTrue(Forneymonegerie.sameCollection(fm4, fm5));
    }

    
}
