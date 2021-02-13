package stacs.yahtzee;

import java.util.Arrays;
import java.util.Hashtable;

public class Combinations {

    final int[] DICE;
    final int[] PLAYERSCORE;
    public Hashtable<Integer,Integer> possibleScores;

    /***
     * KEY
     * 1 - 1s
     * 2 - 2s
     * 3 - 3s
     * 4 - 4s
     * 5 - 5s
     * 6 - 6s
     * ---------------
     * 7 - 3 of a kind
     * 8 - 4 of a kind
     * 9 - Yahtzee | 50
     * 10 - Full House | 25
     * 11 - Small Straight | 30
     * 12 - Large Straight | 40
     * 13 - Chance
     */

    public static void main(String[] args) {
        int[] dice = new int[]{1,1,1,1,1,1};
        int[] score = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        Combinations comb = new Combinations(dice,score);
    }
    public Combinations(int[] dice  , int[] playerScore){
        this.PLAYERSCORE = playerScore;
        this.DICE = dice;
        this.possibleScores = new Hashtable<>();
        calculateCombs();
    }

    public Integer selectScore(int i){ //return null if val does not exist. Else returns score.
        return (possibleScores.get(i));
    }

    public void calculateCombs(){
        for (int i = 1; i <= 13; i++) {
            outer: if(PLAYERSCORE[i-1] == -1){
                if (i <7){
                    howManyIs(i);
                    break outer;
                }
                if(i < 10){
                    iOfAKind(i);
                    break outer;
                }
                if(i == 10){ //full house
                    int threeOAK = possibleScores.get(7);
                    if (threeOAK != 0){
                        for (int j = 1; j < 7; j++) {
                            if (possibleScores.get(j) == 2*j) {
                                possibleScores.put(10,25);
                                break outer;
                            }
                        }
                    }
                    possibleScores.put(i,0);
                    break outer;
                }
                if(i == 11){ //small run
                    inner: for (int j = 0; j < 3; j++) {
                        for (int k = 1; k < 4; k++) {
                            if(!possibleScores.get(j+k).equals(j+k)){
                                break inner;
                            }
                        }
                        possibleScores.put(i,30);
                        break outer;
                    }
                    possibleScores.put(i,0);
                    break outer;
                }
                if(i == 12){
                    if(possibleScores.get(11)==30){
                        if ((possibleScores.get(1) == 1 && possibleScores.get(5) == 5) ||
                                (possibleScores.get(2) == 2 && possibleScores.get(5) == 6)){
                            possibleScores.put(i,40);
                            break outer;
                        }
                    }
                    possibleScores.put(i,0);
                    break outer;
                }
                if (i == 13){
                    possibleScores.put(i , Arrays.stream(DICE).sum());
                    break outer;
                }
            }
        }
    }

    private void howManyIs(int i){
        int total = 0;
        for (int j: DICE) {
            if(j == i){
                total += i;
            }
        }
        possibleScores.put(i , total);
    }

    private void iOfAKind(int i){
        for (int j = 1; j < 7; j++) {
            if (possibleScores.get(j) >= (i-4) * j) {
                possibleScores.put(i, Arrays.stream(DICE).sum());
                return;
            }
        }
        possibleScores.put(i, 0);
        return;
    }
}
