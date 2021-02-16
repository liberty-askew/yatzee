package stacs.yahtzee;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 */
public class YahtzeeModelImplTests {


  @Test
  void testModelSetUp() {
    YahtzeeModelImpl model = new YahtzeeModelImpl(2);
    assertEquals(2,model.noPlayers);
    for (int i: model.dice.getDiceSet()) {
      assertNotEquals(0,i);
    }
    int[][] card = model.scores.getScorecard();
    assertEquals(13, card.length);
    assertEquals(2, card[0].length);
    assertEquals(1,model.roundNo);
    assertEquals(1,model.playerTurn);
    assertEquals(1,model.rollNo);
  }

  @Test
  void testVoidSetUp() {
    YahtzeeModelImpl model = new YahtzeeModelImpl(1);
    assertEquals(0,model.noPlayers);
    assertNull(model.dice);
    assertNull(model.scores);
    assertEquals(0,model.rollNo);
    assertEquals(0,model.playerTurn);
    assertEquals(0,model.roundNo);
  }

  @Test
  void rollSelectedDice(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    int[] oldDice = model.dice.getDiceSet();
    int[] newDice = model.rollDice(new int[]{0, 1, 0, 1, 0});
    for (int i = 0; i < 3; i++) {
      assertEquals(oldDice[(i*2)],newDice[(i*2)]);
    }
  }


  @Test
  void rollVoidDice(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    model.rollDice(new int[]{1, 1, 1, 1, 1, 1});
    model.rollDice(new int[]{1, 1, 1, 1, 1, 1});
    int[] oldDice = model.dice.getDiceSet();
    model.rollDice(new int[]{1, 1, 1, 1, 1, 1});
    assertEquals(oldDice , model.dice.getDiceSet());
  }

  @Test
  void choseCat(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    model.dice.setDiceSet(new int[] {1,1,1,2,4});
    model.dice.calculateCombs();
    model.choseComb(0);
    int[] result = model.scores.sumScores();
    assertEquals(3,result[0]);
  }

  @Test
  void selectCatInvalid(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(2);
    model.choseComb(0);
    int[] oldScore = model.scores.getPlayerScore(1);
    model.choseComb(2);
    model.choseComb(0);
    assertTrue(Arrays.equals(model.scores.getPlayerScore(1),oldScore));
  }


  @Test
  void arbitraryGame(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(2);
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 2; j++) {
        model.choseComb(i);
      }
    }
    assertTrue(model.scores.getWinner()[0] == 1 || model.scores.getWinner()[0] == 2);
  }


  @Test
  void predeterminedGame(){ //TODO: add intermediate tests.
    YahtzeeModelImpl model = new YahtzeeModelImpl(2);
    int[][] presetDice = {{1,1,1,1,1},{2,2,2,2,2},{3,3,3,3,3},{4,4,4,4,4},{5,5,5,5,5},{6,6,6,6,6}};
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 2; j++) {
        model.dice.setDiceSet(presetDice[i%6]);
        model.choseComb(i);
      }
    }
    model.dice.setDiceSet(presetDice[3]); //P1
    model.choseComb(9);
    model.dice.setDiceSet(new int[]{5,5,6,6,6}); //P2
    model.choseComb(9);
    model.dice.setDiceSet(presetDice[4]); //P1
    model.choseComb(10);
    model.dice.setDiceSet(new int[]{1,2,3,4,5}); //P2
    model.choseComb(10);
    model.dice.setDiceSet(presetDice[5]); //P1
    model.choseComb(11);
    model.dice.setDiceSet(new int[]{1,2,3,4,5}); //P2
    model.choseComb(11);
    model.dice.setDiceSet(presetDice[1]); //P1
    model.choseComb(12);
    model.dice.setDiceSet(presetDice[1]); //P2
    model.choseComb(12);
    assertTrue(model.scores.getWinner()[0]==2);
    assertTrue(model.scores.sumScores()[0]==205);
    assertTrue(model.scores.sumScores()[1]==275);
  }


}

