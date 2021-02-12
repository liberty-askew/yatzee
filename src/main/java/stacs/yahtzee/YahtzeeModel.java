package stacs.yahtzee;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 */
public interface YahtzeeModel {

  // define the methods here that a class calling 
  // into the model would call. Don't forget to 
  // add JavaDoc comments!
  // put methods in here which a user would need to use.
  /**
   * Sets up a new game of yatzee;
   * setDice = [0,0,0,0,0,0]
   * scoreCard = new int[13][n] of (-1)
   */
  void newGame();

  /**
   * Updates the game's set of dice.
   * @param reRoll - index of die to be re-rolled. Len=5, reRoll[i]={0,1}, when rollNo = 0 reRoll = [1,1,1,1,1]
   * @param rollNo - which turn it is. max = 2.
   */
  void rollDice(int[] reRoll, int rollNo);

  /**
   * Calculates scores for each player. Prints out to terminal. Can be called at any point in the game and is printed
   * at the end of each round.
   * @param pn - 0< player number <= n
   */
   void calculateScores(int pn);

  /**
   * Gets the valid categories for a games' set of dice and player number (dependent on scoreCard).
   * @param pn
   * ? Have combination enumerators?
   */
   HashMap<String, Integer> validComb(int pn);

    /**
     * Can only be used at the end of each players turn and once per turn.
     * ? implement by keeping a log of playerNo and round to check no repeats
     * Calls validComb to get valid categories.
     * Player selects category if appropriate and the scorecard is updated.
     */
    void choseComb(int playerNo, int round);




}
