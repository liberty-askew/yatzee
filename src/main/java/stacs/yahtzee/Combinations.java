package stacs.yahtzee;

import java.util.Arrays;
import java.util.Hashtable;

public class Combinations {

    final int[] DICE;
    final int[] PLAYERSCORE;
    public Hashtable<Integer,Integer> possibleScores;
    public Hashtable<Integer,String> comboTypes;

    /***
     * KEY
     * 0 - 1s
     * 1 - 2s
     * 2 - 3s
     * 3 - 4s
     * 4 - 5s
     * 5 - 6s
     * ---------------
     * 6 - 3 of a kind
     * 7 - 4 of a kind
     * 8 - Yahtzee | 50
     * 9 - Full House | 25
     * 10 - Small Straight | 30
     * 11 - Large Straight | 40
     * 12 - Chance
     */


    public Combinations(int[] dice  , int[] playerScore){
        this.PLAYERSCORE = playerScore;
        this.DICE = dice;
        this.possibleScores = new Hashtable<>();
        this.comboTypes = new Hashtable<>();
        calculateCombs();

        comboTypes.put(0,"Ones: ");
        comboTypes.put(1,"Twos: ");
        comboTypes.put(2,"Threes: ");
        comboTypes.put(3,"Fours: ");
        comboTypes.put(4,"Fives: ");
        comboTypes.put(5,"Sixes: ");
        comboTypes.put(6,"3 of a kind: ");
        comboTypes.put(7,"4 of a kind: ");
        comboTypes.put(8,"Yahtzee: ");
        comboTypes.put(9,"Full house: ");
        comboTypes.put(10,"Small straight: ");
        comboTypes.put(11,"Large straight: ");
        comboTypes.put(12,"Chance: ");
    }

    public Integer selectScore(int i){ //return null if val does not exist. Else returns score.
        Integer score = possibleScores.remove(i);
        try {
            if (score == null) {
                throw new IndexOutOfBoundsException();
            }
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("please select valid combination input.");
        }
        return score;
    }

    public void calculateCombs(){
        for (int i = 0; i <= 12; i++) {
                if (i <6){
                    howManyIs(i);
                    continue;
                }
                if(i < 9){
                    iOfAKind(i);
                    continue;
                }
                if(i == 9){ //full house
                    fullHouse();
                }
                if(i == 10){ //small run
                    smallStraight();
                }
                if(i == 11){
                    largeStraight();
                }
                if (i == 12){
                    possibleScores.put(i , Arrays.stream(DICE).sum());
                    continue;
                }
            }
        for (int i = 0; i <= 12; i++){
            if( PLAYERSCORE[i] != -1){
                possibleScores.remove(i);
            }
        }
    }

    private void howManyIs(int i){
        int total = 0;
        for (int j: DICE) {
            if(j == (i+1)){
                total += (i+1);
            }
        }
        possibleScores.put(i , total);
    }

    private void iOfAKind(int i){
        for (int j = 0; j < 6; j++) {
            if(possibleScores.get(j)!=null) {
                if (possibleScores.get(j)>= (i - 3) * (j+1)) {
                    possibleScores.put(i, Arrays.stream(DICE).sum());
                    return;
                }
            }
        }
        possibleScores.put(i, 0);
        return;
    }

    private void fullHouse(){
        if(possibleScores.get(6)!= null) {
            if (!compare(6, 0)) {
                for (int j = 0; j < 6; j++) {
                    if (possibleScores.get(j) != null) {
                        if (possibleScores.get(j) == 2 * j) {
                            possibleScores.put(9, 25);
                            return;
                        }
                    }
                }
            }
        }
        possibleScores.put(9, 0);
        return;
    }

    private void smallStraight(){
        inner: for (int j = 0; j < 3; j++) { //TODO: test
            for (int k = 0; k < 3; k++) {
                if(possibleScores.get(j+k)!= null) {
                    if (!compare(j+k,j+k+1)) {
                        break inner;
                    }
                }
            }
            possibleScores.put(10,30);
            return;
        }
        possibleScores.put(10,0);
        return;
    }

    private void largeStraight() {
        if(possibleScores.get(10)!= null) {
            if (possibleScores.get(10).equals(30)) {
                if (compare(0,1) && compare(4,5) ||
                        (compare(1,2) && compare(5,6))) {
                    possibleScores.put(11, 40);
                    return;
                }
            }
        }
        possibleScores.put(11, 0);
        return;
    }



    private boolean compare(int possScoreLookup , int comparable){
        Integer returnedScore = possibleScores.get(possScoreLookup);
        if(returnedScore == null){
            return false;
        }
        if(returnedScore == comparable){
            return true;
        }
        else{
            return false;
        }

    }


}
