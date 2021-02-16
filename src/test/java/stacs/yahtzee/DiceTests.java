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
        System.out.println(testDice);
        int[] diceSet = testDice.getDiceSet();
        for (int d: diceSet) {
            assertTrue(d >0 && d<7);
        }
    }

    @Test
    void setValidDice(){
        testDice.setDiceSet(new int[]{1,2,3,4,5});
        assertArrayEquals(testDice.getDiceSet(),new int[]{1,2,3,4,5});
        assertEquals(testDice.possibleScores[11],40);
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
        testDice.setDiceSet(new int[]{1,6,8,-1,4});
        assertFalse(Arrays.equals(new int[]{1,6,8,-1,4},testDice.getDiceSet()));
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
        assertEquals(testDice.selectScore(1),4);
    }


    @Test
    void diceCombs1s() {
        System.out.println("START OF TEST");
        testDice.setDiceSet(new int[]{1,1,1,1,1});
        int[] newset = testDice.getDiceSet();
        for (int i: newset) {
            System.out.println(i);
        }
        assertEquals(testDice.selectScore(0),5);
        assertEquals(testDice.selectScore(1),0);
        assertEquals(testDice.selectScore(2),0);
        assertEquals(testDice.selectScore(3),0);
        assertEquals(testDice.selectScore(4),0);
        assertEquals(testDice.selectScore(5),0);
        assertEquals(testDice.selectScore(6),5);
        assertEquals(testDice.selectScore(7),5);
        assertEquals(testDice.selectScore(8),5);
        assertEquals(testDice.selectScore(9),0);
        assertEquals(testDice.selectScore(10),0);
        assertEquals(testDice.selectScore(11),0);
        assertEquals(testDice.selectScore(12),5);
    }

    @Test
    void diceCombsRun() {
        int[] dice = new int[]{1, 2, 3, 4, 5};
        Dice comb = new Dice();
        comb.setDiceSet(dice);
        assertEquals(comb.selectScore(10), 30);
        assertEquals(comb.selectScore(11), 40);
        assertEquals(comb.selectScore(12), 15);
    }

    @Test
    void diceCombsFullHouse() {
        int[] dice = new int[]{2,2,2,4,4};
        int[] score = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        Dice comb = new Dice();
        comb.setDiceSet(dice);
        assertEquals(comb.selectScore(1), 6);
        assertEquals(comb.selectScore(9), 25);
        assertEquals(comb.selectScore(3), 8);
    }

}
