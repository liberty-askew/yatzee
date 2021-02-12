package stacs.yahtzee;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 */
public class YahtzeeModelImplTests {


  private final ByteArrayOutputStream out = new ByteArrayOutputStream();
  private final ByteArrayOutputStream err = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;


  // add variables for the things you set up in setup()


  @Before
  public void setStreams() {
    System.setOut(new PrintStream(out));
    System.setErr(new PrintStream(err));
  }

  @After
  public void restoreInitialStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @BeforeEach
  void setup() {

  }
  
  @Test
  void testPlayerSetUp() {
    YahtzeeModelImpl model = new YahtzeeModelImpl(2);
    assertEquals(2,model.playerNo);
    assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, model.diceSet);
    assertEquals(13, model.scoreCard.length);
    assertEquals(2, model.scoreCard[0].length);
  }

  @Test
  void testVoidSetUp() {
    YahtzeeModelImpl model = new YahtzeeModelImpl(1);
    assertEquals(0,model.playerNo);
    assertNull(model.scoreCard);
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

