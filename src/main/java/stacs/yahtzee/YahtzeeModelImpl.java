package stacs.yahtzee;

import java.util.HashMap;

/**
 *
 */
public class YahtzeeModelImpl implements YahtzeeModel {
    int noPlayers; //number of players
    int[] diceSet;
    Scores scores;

    public static void main(String[] args) {
        YahtzeeModel model = new YahtzeeModelImpl(2);
        model.rollDice(new int[]{0,1,1,1,1,1} ,2) ;

    }
    public YahtzeeModelImpl(int np) {
        try {
            if (np < 2) {
                throw new Exception();
            }
            this.noPlayers = np;
            this.diceSet = new int[]{0, 0, 0, 0, 0, 0};
            this.scores = new Scores(np , this);
        }
        catch (Exception e){
            System.out.println("Must be at least 2 players.");
        }
    }

    @Override
    public void newGame() {
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < this.noPlayers; j++) {
                takeTurn(j,i);
            }
        }
    }

    @Override
    public void takeTurn(int playerNo, int round) {

    }

    @Override
    public void rollDice(int[] reRoll, int rollNo) {
        try {
            if (rollNo > 3 || rollNo < 0) {
                throw new Exception();
            }
            for (int i = 0; i < 6; i++) {
                if (reRoll[i] == 1){
                    this.diceSet[i]= (int) Math.ceil(Math.random()*6);
                }
            }
        }
        catch (Exception e){ //might not be needed?
            System.out.println("No more than 3 rolls of dice each go.");
        }
    }

    @Override
    public void calculateScores(int pn) {

    }

    @Override
    public HashMap<String, Integer> validComb(int pn) {
        return null;
    }

    @Override
    public void choseComb(int playerNo, int round) {

    }
}
