package stacs.yahtzee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;

public class DiceTests {

    private Dice testDice;

    @BeforeEach
    public void setup(){
        testDice = new Dice();
    }

    @Test
    public void exists(){
        assertNotNull(testDice);
    }


    @Test
    public void testDiceSetUp() {
        assertEquals(testDice.diceSet.length,5);
        assertTrue(testDice.possibleScores instanceof int[]);
        assertEquals(testDice.possibleScores.length,13);
        assertTrue(testDice.comboTypes instanceof Hashtable);
        assertEquals(testDice.comboTypes.size(),13);

    }

    @Test
    public void getDice(){
        int[] diceSet = testDice.getDiceSet();
        for (int d: diceSet) {
            assertTrue(d >0 && d<7);
        }
    }

    @Test
    void setValidDice(){
        testDice.setDiceSet(new int[]{1,2,3,4,5});
        assertArrayEquals(testDice.getDiceSet(),new int[]{1,2,3,4,5});
        assertEquals(40,testDice.possibleScores[11]);
    }

    @Test
    void setInValidDice(){
        int[] oldDice = testDice.getDiceSet();
        testDice.setDiceSet(new int[]{1,2,3,4,5,6});
        assertFalse(Arrays.equals(new int[]{1,2,3,4,5,6},testDice.getDiceSet()));
        assertTrue(Arrays.equals(oldDice,testDice.getDiceSet()));
        testDice.setDiceSet(new int[]{1});
        assertFalse(Arrays.equals(new int[]{1},testDice.getDiceSet()));
        assertTrue(Arrays.equals(oldDice,testDice.getDiceSet()));
        testDice.setDiceSet(new int[]{1,6,8,2,4});
        assertFalse(Arrays.equals(new int[]{1,6,8,2,4},testDice.getDiceSet()));
        assertTrue(Arrays.equals(oldDice,testDice.getDiceSet()));
        testDice.setDiceSet(new int[]{1,6,8,-2,4});
        assertFalse(Arrays.equals(new int[]{1,6,8,-2,4},testDice.getDiceSet()));
        assertTrue(Arrays.equals(oldDice,testDice.getDiceSet()));
    }

    @Test
    void roll(){
        int[] oldDice = testDice.getDiceSet();
        testDice.roll(new int[]{0, 1, 0, 1, 0});
        int[] newDice = testDice.getDiceSet();
        for (int i = 0; i < 3; i++) {
            assertEquals(oldDice[(i*2)],newDice[(i*2)]);
        }
    }

    @Test
    void compareNotNull(){

    }

    @Test
    void compareNull(){

    }

    @Test
    void selectScore(){
        testDice.setDiceSet(new int[]{2,2,3,4,5});
        assertEquals(4,testDice.selectScore(1));
    }


    @Test
    void diceCombs1s() {
        testDice.setDiceSet(new int[]{1,1,1,1,1});
        int[] newset = testDice.getDiceSet();
        assertEquals(5,testDice.selectScore(0));
        assertEquals(0,testDice.selectScore(1));
        assertEquals(0,testDice.selectScore(2));
        assertEquals(0,testDice.selectScore(3));
        assertEquals(0,testDice.selectScore(4));
        assertEquals(0,testDice.selectScore(5));
        assertEquals(5,testDice.selectScore(6));
        assertEquals(5,testDice.selectScore(7));
        assertEquals(5,testDice.selectScore(8));
        assertEquals(0,testDice.selectScore(9));
        assertEquals(0,testDice.selectScore(10));
        assertEquals(0,testDice.selectScore(11));
        assertEquals(5,testDice.selectScore(12));
    }

    @Test
    void diceCombsRunLow() {
        int[] dice = new int[]{1, 2, 3, 4, 5};
        Dice comb = new Dice();
        comb.setDiceSet(dice);
        assertEquals(30,comb.selectScore(10));
        assertEquals(40,comb.selectScore(11));
        assertEquals(15,comb.selectScore(12));
    }

    @Test
    void diceCombsRunHigh() {
        int[] dice = new int[]{2, 3, 4, 5, 6};
        Dice comb = new Dice();
        comb.setDiceSet(dice);
        assertEquals(30,comb.selectScore(10));
        assertEquals(40,comb.selectScore(11));
    }

    @Test
    void diceCombsFullHouse() {
        int[] dice = new int[]{2,2,2,4,4};
        Dice comb = new Dice();
        comb.setDiceSet(dice);
        assertEquals(6,comb.selectScore(1));
        assertEquals(25,comb.selectScore(9));
        assertEquals(8,comb.selectScore(3));
    }

}
