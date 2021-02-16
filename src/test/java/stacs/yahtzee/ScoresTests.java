package stacs.yahtzee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoresTests {

    private Scores testScores;

    @BeforeEach
    public void setup(){
        testScores = new Scores(3);
    }

    @Test
    public void exists(){
        assertNotNull(testScores);
    }

    @Test
    public void testSetup(){
        assertEquals(testScores.np,3);
        assertNotNull(testScores.scoreCard);
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(testScores.scoreCard[i][j] , -1);
            }
        }
    }

    @Test
    public void alternatePlayerNo(){
        Scores testNo = new Scores(5);
        assertEquals(testNo.np , 5);
        assertEquals(testNo.scoreCard[0].length,5);
    }

    @Test
    public void getInitPlayerScore(){
        for (int i = 1; i < 4; i++) {
            int[] testPlayerScore = testScores.getPlayerScore(i);
            assertArrayEquals(testPlayerScore,new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        }
    }

    @Test
    public void getInitScorecard(){
        int[][] scoreCardTest = testScores.getScorecard();
        assertEquals(scoreCardTest[0].length,3);
        assertEquals(scoreCardTest.length,13);
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(scoreCardTest[i][j],-1);
            }
        }
    }

    @Test
    public void updateScorecard(){
        testScores.updateScorecard(30,11,1);
        int[][] scoreCardTest = testScores.getScorecard();
        assertEquals(30,scoreCardTest[11][0]);
        testScores.updateScorecard(18,6,2);
        int[] playerScoreTest = testScores.getPlayerScore(2);
        assertEquals(18,playerScoreTest[6]);
    }

    @Test
    public void sumInitScores(){
        int[] scores = testScores.sumScores();
        for (int i = 0; i < 3; i++) {
            assertEquals(0,scores[i]);
        }
    }

    @Test
    public void sumTripleUpperScores() {
        for (int i = 1; i < 7; i++) {
            testScores.updateScorecard(3*i, i-1, 1);
        }
        assertEquals(98,testScores.sumScores()[0]);
        testScores.updateScorecard(25, 10, 1);
        testScores.updateScorecard(0, 11, 1);
        testScores.updateScorecard(40, 12, 1);
        assertEquals(163,testScores.sumScores()[0]);
    }

    @Test
    public void sumNotTripleUpperScores() {
        for (int i = 1; i < 7; i++) {
            testScores.updateScorecard(2*i, i-1, 2);
        }
        assertEquals(42,testScores.sumScores()[1]);
        testScores.updateScorecard(0, 7, 2);
        testScores.updateScorecard(0, 8, 2);
        testScores.updateScorecard(50, 9, 2);
        assertEquals(92,testScores.sumScores()[1]);
    }

    @Test
    public void getWinner(){
        testScores.updateScorecard(0, 1, 1);
        testScores.updateScorecard(26, 8 , 2);
        testScores.updateScorecard(15, 12, 3);
        assertEquals(2,testScores.getWinner()[0]);
        assertEquals(26,testScores.getWinner()[1]);
    }




}
