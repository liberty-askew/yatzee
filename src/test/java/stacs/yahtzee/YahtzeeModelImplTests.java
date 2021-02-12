package stacs.yahtzee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 
 */
public class YahtzeeModelImplTests {
  
  // add variables for the things you set up in setup()

  @BeforeEach
  void setup() {

  }
  
  @Test
  void testPlayerSetUp() {
    YahtzeeModelImpl model = new YahtzeeModelImpl(2);
    assertEquals(2,model.playerNo);
    assertEquals(new int[]{0, 0, 0, 0, 0, 0}, model.diceSet);
    assertEquals(13, model.scoreCard.length);
    assertEquals(2, model.scoreCard[0].length);
  }

  @Test
  void testVoidSetUp() {
    Exception exception = assertThrows(Exception.class, () -> {
      YahtzeeModelImpl model = new YahtzeeModelImpl(1);
    });
    String expectedMessage = "Must be at least 2 players.";
    String actualMessage = exception.getMessage();
    assertEquals(actualMessage,expectedMessage);
  }

  @Test
  void rollDice(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    assertEquals(new int[]{0, 0, 0, 0, 0, 0}, model.diceSet);
    model.rollDice(new int[]{1, 1, 1, 1, 1, 1}, 0);
    for (int i = 0; i < 7; i++) {
      assertTrue(model.diceSet[i] != 0);
    }
  }

  @Test
  void rollVoidDice(){
    YahtzeeModelImpl model = new YahtzeeModelImpl(3);
    Exception exception = assertThrows(Exception.class, () -> {
      model.rollDice(new int[]{1, 1, 1, 1, 1, 1}, 4);
    });
  }

  @Test
  void takeTurn(){
  }

  @Test
  void playRound(){
    //use mokito to track how many times' take turn is played in game
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

}

