package stacs.yahtzee;

import java.util.HashMap;

/**
 *
 */
public class YahtzeeModelImpl implements YahtzeeModel {
    int playerNo; //number of players
    int[] diceSet;
    int[][] scoreCard;

    public static void main(String[] args) {
        YahtzeeModel model = new YahtzeeModelImpl(2);
        model.rollDice(new int[]{0,1,1,1,1,1} ,2) ;
    }
    public YahtzeeModelImpl(int np) {
        try {
            if (np < 2) {
                throw new Exception();
            }
            this.playerNo = np;
            this.diceSet = new int[]{0, 0, 0, 0, 0, 0};
            this.scoreCard = new int[13][np];
        }
        catch (Exception e){
            System.out.println("Must be at least 2 players.");
            System.out.println(playerNo);
            System.out.println(diceSet);
            System.out.println(scoreCard);
        }
    }



    @Override
    public void newGame() {

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
