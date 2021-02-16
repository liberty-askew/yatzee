package stacs.yahtzee;

import java.util.Arrays;

public class Scores{

    private int[][] scoreCard;
    int np;

    public Scores(int np){
        this.np = np;
        this.scoreCard = new int[13][np];
        for (int[] type: scoreCard) {
            Arrays.fill(type,-1);
        }
    }

    public int[] getPlayerScore(int playerNo){
        int[] playerScore = new int[13];
        for (int i = 0; i < 13; i++) {
            playerScore[i] = scoreCard[i][playerNo-1];
        }
        return playerScore;
    }
    public int[][] getScorecard(){
        return scoreCard;
    }

    public void updateScorecard(int x , int cat , int pn){
        scoreCard[cat][pn-1] = x;
    }

    public int[] sumScores() {
        int[] results = new int[np];
        System.out.println(np);
        for (int i = 0; i < np; i++) { //iterates through players.
            int sum = 0;
            for (int j = 0; j < 13; j++) {
                if(scoreCard[j][i] != -1){
                    sum += scoreCard[j][i];
                }
                if (j == 5 && sum >= 63) {
                    sum += 35;
                }
            }
            results[i] = sum;
        }
        return results;
    }

    public int[] getWinner(){
        int[] scores = sumScores();
        int[] result = new int[]{-1, -1};
        for (int i = 0; i < np; i++) {
            if (scores[i] > result[1]) {
                result = new int[]{(i+1),scores[i]};
            }
        }
        return result;
    }
}

