package stacs.yahtzee;

public class Scores{
    private static int[][] scoreCard;
    public YahtzeeModel game;

    public Scores(int np,YahtzeeModel game){
        this.scoreCard = new int[13][np];
        this.game =  game;
    }

    public int[][] getScorecard(){
        return scoreCard;
    }

    private void updateScorecard(int x , int cat , int pn){
        scoreCard[cat][pn] = x;
    }

    private void calculateScores() {

    }

}
