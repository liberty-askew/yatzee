package stacs.yahtzee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 
 */
public class YahtzeeModelImplTests {


  private final ByteArrayOutputStream out = new ByteArrayOutputStream();
  private final ByteArrayOutputStream err = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;


  // add variables for the things you set up in setup()

  @BeforeEach
  void setup() {

  }
  
  @Test
  void testPlayerSetUp() {
    YahtzeeModelImpl model = new YahtzeeModelImpl(2);
    assertEquals(2,model.noPlayers);
    assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, model.diceSet);
    int[][] card = model.scores.getScorecard();
    assertEquals(13, card.length);
    assertEquals(2, card[0].length);
  }

  @Test
  void testVoidSetUp() {
    YahtzeeModelImpl model = new YahtzeeModelImpl(1);
    assertEquals(0,model.noPlayers);
    assertNull(model.diceSet);
  }

  @Test
  void rollDice(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    model.rollDice(new int[]{1, 1, 1, 1, 1, 1}, 0);
    for (int i = 0; i < 6; i++) {
      assertTrue(model.diceSet[i] != 0);
    }
  }

  @Test
  void rollDiceRange1_6() {
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    int[] oldDice = new int[]{0, 0, 0, 0, 0, 0};
    model.rollDice(new int[]{1, 1, 1, 1, 1, 1}, 1);
    for (int i : model.diceSet) {
      assertTrue(i<7 && i>0);
    }
  }


  @Test
  void rollVoidDice(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    int[] oldDice = new int[]{0, 0, 0, 0, 0, 0};
    model.rollDice(new int[]{1, 1, 1, 1, 1, 1}, 4);
    assertArrayEquals(oldDice,model.diceSet);
  }

  @Test
  void takeTurn(){
  }

  @Test
  void protectedScorecard(){
    //make YZmodel
    // try to update scores.
  }

  @Test
  void setScorecard(){
    //make YZmodel
    // try to update scores.
  }

  @Test
  void scoresInheritance(){
    YahtzeeModelImpl testmodel = new YahtzeeModelImpl(3);
    Scores testscore = testmodel.scores;
    assertEquals(testmodel , testscore.game);
  }

  @Test
  void playRound(){
    YahtzeeModelImpl model = spy(new YahtzeeModelImpl(3));
    model.newGame();
    verify(model,times(1)).takeTurn(1,1 ); //increase for all numbers?
  }

  @Test
  void diceCombs1s() {
    int[] dice = new int[]{1,1,1,1,1,1};
    int[] score = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    Combinations comb = new Combinations(dice,score);
    assertEquals(comb.selectScore(1),6);
    assertEquals(comb.selectScore(2),0);
    assertEquals(comb.selectScore(3),0);
    assertEquals(comb.selectScore(4),0);
    assertEquals(comb.selectScore(5),0);
    assertEquals(comb.selectScore(6),0);
    assertEquals(comb.selectScore(7),6);
    assertEquals(comb.selectScore(8),6);
    assertEquals(comb.selectScore(9),6);
    assertEquals(comb.selectScore(10),0);
    assertEquals(comb.selectScore(11),0);
    assertEquals(comb.selectScore(12),0);
    assertEquals(comb.selectScore(13),6);
  }

  @Test
  void diceCombsRun() {
    int[] dice = new int[]{1, 2, 3, 4, 5};
    int[] score = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    Combinations comb = new Combinations(dice, score);
    assertEquals(comb.selectScore(11), 30);
    assertEquals(comb.selectScore(12), 40);
    assertEquals(comb.selectScore(13), 15);
  }

  @Test
  void diceCombsFullHouse() {
    int[] dice = new int[]{2,2,2,4,4};
    int[] score = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    Combinations comb = new Combinations(dice, score);
    assertEquals(comb.selectScore(2), 6);
    assertEquals(comb.selectScore(10), 25);
    assertEquals(comb.selectScore(4), 8);
  }

  @Test
  void playGame() {
    //use mokito to track how many times' play round is used in game
  }

  @Test
  void winnerCorrect(){
    //hard code scores and test that the correct winner is calculated.
  }

  @Test
  void selectComboValid(){
    //hard code dice combo and check correct valid combo offered.
  }

  @Test
  void selectComboInvalid(){
    //hard code dice combo and check if zero combo chosen.
  }

  @Test
  void scoring(){
    //hard code dice combo and check if zero combo chosen.
  }

  @Test
  void playerUseCase(){
    //test player in whole game and test score accruement
  }

  /***
  @Test
  void updateScorecard(){
    YahtzeeModel game = mock(YahtzeeModel.class);
    Scores testscore = new Scores(5,game);
    int[][] oldScorecard = testscore.getScorecard();
    oldScorecard[5][6] = 4;
    //make YZmodel
    // try to update scores.
  }
  */

}

