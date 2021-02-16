package stacs.yahtzee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreboardTests {

    private Scoreboard testScoreboard;

    @BeforeEach
    public void setup(){
        testScoreboard = new Scoreboard(3);
    }

    @Test
    public void exists(){
        assertNotNull(testScoreboard);
    }

    @Test
    public void testSetup(){
        assertEquals(testScoreboard.noPlayers,3);
        assertNotNull(testScoreboard.scoreCard);
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(testScoreboard.scoreCard[i][j] , -1);
            }
        }
    }

    @Test
    public void alternatePlayerNo(){
        Scoreboard testNo = new Scoreboard(5);
        assertEquals(testNo.noPlayers, 5);
        assertEquals(testNo.scoreCard[0].length,5);
    }

    @Test
    public void getInitPlayerScore(){
        for (int i = 1; i < 4; i++) {
            int[] testPlayerScore = testScoreboard.getPlayerScore(i);
            assertArrayEquals(testPlayerScore,new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1});
        }
    }

    @Test
    public void getInitScorecard(){
        int[][] scoreCardTest = testScoreboard.scoreCard;
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
        testScoreboard.updateScorecard(30,11,1);
        int[][] scoreCardTest = testScoreboard.scoreCard;
        assertEquals(30,scoreCardTest[11][0]);
        testScoreboard.updateScorecard(18,6,2);
        int[] playerScoreTest = testScoreboard.getPlayerScore(2);
        assertEquals(18,playerScoreTest[6]);
    }

    @Test
    public void sumInitScores(){
        int[] scores = testScoreboard.sumScores();
        for (int i = 0; i < 3; i++) {
            assertEquals(0,scores[i]);
        }
    }

    @Test
    public void sumTripleUpperScores() {
        for (int i = 1; i < 7; i++) {
            testScoreboard.updateScorecard(3*i, i-1, 1);
        }
        assertEquals(98, testScoreboard.sumScores()[0]);
        testScoreboard.updateScorecard(25, 10, 1);
        testScoreboard.updateScorecard(0, 11, 1);
        testScoreboard.updateScorecard(40, 12, 1);
        assertEquals(163, testScoreboard.sumScores()[0]);
    }

    @Test
    public void sumNotTripleUpperScores() {
        for (int i = 1; i < 7; i++) {
            testScoreboard.updateScorecard(2*i, i-1, 2);
        }
        assertEquals(42, testScoreboard.sumScores()[1]);
        testScoreboard.updateScorecard(0, 7, 2);
        testScoreboard.updateScorecard(0, 8, 2);
        testScoreboard.updateScorecard(50, 9, 2);
        assertEquals(92, testScoreboard.sumScores()[1]);
    }

    @Test
    public void getWinner(){
        testScoreboard.updateScorecard(0, 1, 1);
        testScoreboard.updateScorecard(26, 8 , 2);
        testScoreboard.updateScorecard(15, 12, 3);
        assertEquals(2, testScoreboard.getWinner()[0]);
        assertEquals(26, testScoreboard.getWinner()[1]);
    }




}
