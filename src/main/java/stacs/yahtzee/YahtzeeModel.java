package stacs.yahtzee;


public interface YahtzeeModel {

  /**
   * Rerolls the dice. Can be called up to twice per player's turn. Raises exception if used more
   * than twice per turn.Will print out the valid scoring combinations for the dice rolled.
   * @param reRoll - index of die to be re-rolled. Len=5, reRoll[i]={0,1} e.g reRoll = [1,1,1,1,1]
   *               reRolls all 6 dice. reRoll = [1,0,0,0,0] only rerolls first die.
   */
  int[] rollDice(int[] reRoll);

  /**
   * Can only be used at any time during player's turn to chose a scoring combination. Will raise
   * exception if cat is not from the valid scoring output. Having called choseComb game will progress
   * to next player's turn and update the previous player's score card appropriately.
   * @param cat - catergory to select. Must be from valid scoring output.
   */
  void choseComb(int cat);

  /**
   * Prints each player's total score at that point in the game.
   * Can be called at any time and is automatically triggered at the end of each round.
   */
  void printScores();

  /**
   * Prints the valid scoring combinations for whichever player who's turn it is and the current
   * value on the dice.
   * Can be called at any time and is automatically triggered when dice are rolled.
   */
  void printCombinations();

  /**
   * Ends the game. Will calculate and print out player's scores and the winner.
   * Can be called at any time and is automatically triggered at the end.
   */
  void endGame();
}
