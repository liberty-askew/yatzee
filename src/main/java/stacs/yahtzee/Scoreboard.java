package stacs.yahtzee;

import java.util.Arrays;

public class Scoreboard {

    public int noPlayers;
    public int[][] scoreCard; //stores players scores. col: player, row: combination score.
                               //e.g scoreCard[1][2] = player 3's score for Twos.

    /**
     * Handles the game's scoreboard. Updates players scores and calculates scores.
     * @param noPlayers - number of players in the game > 1.
     */
    public Scoreboard(int noPlayers){

        this.noPlayers = noPlayers;
        this.scoreCard = new int[13][noPlayers];
        for (int[] type: scoreCard) { //Category score set to -1 by default to represent it
            Arrays.fill(type,-1); //has not been assigned yet by player.
        }
    }

    /**
     * @param playerNo - player number > 0
     * @return - int[13] of the player's score for each category
     *             where int[5] = playerNo's score for Sixes. If int[i] = -1 then
     *             player has not selected this category yet.
     */
    public int[] getPlayerScore(int playerNo){

        int[] playerScore = new int[13];
        for (int i = 0; i < 13; i++) {
            playerScore[i] = scoreCard[i][playerNo-1];
        }
        return playerScore;
    }

    /**
     * Updates score card for specific player's score for a category.
     * @param x - score for category cat.
     * @param cat - category to update e.g cat = 2 for Threes
     * @param playerNo - player no > 0
     */
    public void updateScorecard(int x , int cat , int playerNo){

        scoreCard[cat][playerNo-1] = x;
    }

    /**
     * Sums the scores for each player at any point in game when called.
     * @return - int[noPlayer] where int[0] = player 1's score.
     */
    public int[] sumScores() {

        int[] results = new int[noPlayers];
        for (int i = 0; i < noPlayers; i++) {
            int sum = 0;
            for (int j = 0; j < 13; j++) {
                if(scoreCard[j][i] != -1){ //do not include -1 place holders for unassigned categories.
                    sum += scoreCard[j][i];
                }
                if (j == 5 && sum >= 63) {
                    sum += 35; //bonus 35 points for score > 63 in upper section.
                }
            }
            results[i] = sum;
        }
        return results;
    }

    /**
     * Gets the winner of the game and their score. Can be called at any point and is
     * automatically triggered at the end of the game.
     * @return - int[2] where int[0] = winning player number & int[1] = winning score.
     */
    public int[] getWinner(){

        int[] scores = sumScores();
        int[] result = new int[]{-1, -1};
        for (int i = 0; i < noPlayers; i++) {
            if (scores[i] > result[1]) {
                result = new int[]{(i+1),scores[i]};
            }
        }
        return result;
    }
}

