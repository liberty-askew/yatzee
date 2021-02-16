package stacs.yahtzee;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class YahtzeeModelImplTests {


  @Test
  void testModelSetUp() {
    YahtzeeModelImpl model = new YahtzeeModelImpl(2);
    assertEquals(2,model.noPlayers);
    for (int i: model.dice.getDiceSet()) {
      assertNotEquals(0,i);
    }
    int[][] card = model.scoreboard.getScorecard();
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
    assertNull(model.scoreboard);
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
  void rollInvalidDice(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    int[] oldDice = model.dice.getDiceSet();
    model.rollDice(new int[]{0, 1, 0, 1});
    assertArrayEquals(oldDice,model.dice.getDiceSet());
    model.rollDice(new int[]{0, 1, 0, 1, 1, 1});
    assertArrayEquals(oldDice,model.dice.getDiceSet());
    model.rollDice(new int[]{0, 1, 0, 1, 3});
    assertArrayEquals(oldDice,model.dice.getDiceSet());
  }

  @Test
  void initRoundTest() {
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    model.initRound();
    assertEquals(2,model.roundNo);
    assertEquals(1,model.playerTurn);
    assertEquals(1,model.rollNo);
  }

  @Test
  void initTurnTest() {
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    model.initTurn();
    assertEquals(2,model.playerTurn);
    assertEquals(1,model.rollNo);
  }

  @Test
  void endRoundTest() {
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    model.endRound();
    assertEquals(2,model.roundNo);
    assertEquals(1,model.playerTurn);
    assertEquals(1,model.rollNo);
  }

  @Test
  void endTurnTest() {
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    model.endTurn();
    assertEquals(1,model.roundNo);
    assertEquals(2,model.playerTurn);
    assertEquals(1,model.rollNo);
  }


  @Test
  void fourthDiceRoll(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    model.rollDice(new int[]{1, 1, 1, 1, 1});
    model.rollDice(new int[]{1, 1, 1, 1, 1});
    int[] oldDice = model.dice.getDiceSet();
    model.rollDice(new int[]{1, 1, 1, 1, 1});
    assertEquals(oldDice , model.dice.getDiceSet());
  }

  @Test
  void choseCat(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    model.dice.setDiceSet(new int[] {1,1,1,2,4});
    model.dice.calculateCombs();
    model.choseComb(0);
    int[] result = model.scoreboard.sumScores();
    assertEquals(3,result[0]);
  }

  @Test
  void selectCatInvalid(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(2);
    model.choseComb(0);
    int[] oldScore = model.scoreboard.getPlayerScore(1);
    model.choseComb(2);
    model.choseComb(0);
    assertTrue(Arrays.equals(model.scoreboard.getPlayerScore(1),oldScore));
  }

  @Test
  void roundofGame(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(2);
    model.rollDice(new int[]{1,1,1,1,1}); //P1 R1
    model.rollDice(new int[]{1,1,1,1,0});
    assertEquals(3,model.rollNo);
    model.choseComb(1);
    assertEquals(2,model.playerTurn);
    assertEquals(1,model.roundNo);
    model.rollDice(new int[]{0,0,0,0,1}); //P2 R1
    assertEquals(2,model.rollNo);
    model.choseComb(3);
    assertEquals(1,model.playerTurn);
    assertEquals(2,model.roundNo);
   }


  @Test
  void arbitraryGame(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(4);
    for (int i = 0; i < 13; i++) {
      for (int j = 0; j < 4; j++) {
        model.choseComb(i);
      }
    }
    assertNull(model.dice);
    assertEquals(0,model.rollNo);
    assertEquals(0,model.playerTurn);
    assertEquals(0,model.roundNo);
    assertTrue(model.scoreboard.getWinner()[0] <5 && model.scoreboard.getWinner()[0] > 0 );
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
    assertEquals(2,model.scoreboard.getWinner()[0]);
    assertEquals(205,model.scoreboard.sumScores()[0]);
    assertEquals(275,model.scoreboard.sumScores()[1]);
  }


}

