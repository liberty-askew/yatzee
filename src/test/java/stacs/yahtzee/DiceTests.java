package stacs.yahtzee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Hashtable;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(testDice.possibleScores instanceof Hashtable);
        assertEquals(testDice.possibleScores.size(),13);
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
        assertEquals(testDice.possibleScores.get(11),40);
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
        Dice comb = new Dice();
        comb.setDiceSet(new int[]{1,1,1,1,1,1});
        assertEquals(comb.selectScore(0),6);
        assertEquals(comb.selectScore(1),0);
        assertEquals(comb.selectScore(2),0);
        assertEquals(comb.selectScore(3),0);
        assertEquals(comb.selectScore(4),0);
        assertEquals(comb.selectScore(5),0);
        assertEquals(comb.selectScore(6),6);
        assertEquals(comb.selectScore(7),6);
        assertEquals(comb.selectScore(8),6);
        assertEquals(comb.selectScore(9),0);
        assertEquals(comb.selectScore(10),0);
        assertEquals(comb.selectScore(11),0);
        assertEquals(comb.selectScore(12),6);
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
