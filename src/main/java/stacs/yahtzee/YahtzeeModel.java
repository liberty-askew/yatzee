package stacs.yahtzee;

/**
 *
 */
public interface YahtzeeModel {


  /**
   * Updates the game's set of dice.
   * @param reRoll - index of die to be re-rolled. Len=5, reRoll[i]={0,1}, when rollNo = 0 reRoll = [1,1,1,1,1]

   */
  void rollDice(int[] reRoll);

  /**
   * Can only be used at the end of each players turn and once per turn.
   * ? implement by keeping a log of playerNo and round to check no repeats
   * Calls validComb to get valid categories.
   * Player selects category if appropriate and the scorecard is updated.
   */
  void choseComb(int cat);

  void printScores();

  void printCombinations();

  void endGame();
}
