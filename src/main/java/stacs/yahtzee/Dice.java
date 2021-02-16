package stacs.yahtzee;

import java.util.Arrays;
import java.util.Hashtable;

public class Dice {

    public int[] diceSet;
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

    public static void main(String[] args) {
        Dice testDice = new Dice();
        int[] diceSet = testDice.getDiceSet();
        for (int d: diceSet) {
            System.out.println(d >0 && d<7);
        }
    }

    public Dice(){
        this.diceSet = new int[5];
        comboTypes = new Hashtable<>();
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
        System.out.println(comboTypes.size());
        roll(new int[]{1,1,1,1,1});
    }

    public int[] getDiceSet() {
        return this.diceSet;
    }

    public void setDiceSet(int[] diceSet) {
        try{
            for (int d: diceSet) {
                if(d < 1 || d > 6){
                    throw new IllegalArgumentException();
                }
            }
            if(diceSet.length != 5){
                throw new IllegalArgumentException();
            }
            else{
                this.diceSet = diceSet;
                calculateCombs();
            }
        }
        catch (IllegalArgumentException e) {

        }
        if(diceSet.length == 5){
        }
    }

    public void roll(int[] reRoll) {
        this.possibleScores = new Hashtable<>();
        int[] newRoll = new int[5];
        for (int i = 0; i < 5; i++) {
            if (reRoll[i] == 1) {
                newRoll[i] = (int) Math.ceil(Math.random() * 6);
            }
            else{
                newRoll[i] = diceSet[i];
            }
        }
        setDiceSet(newRoll);
        System.out.print("Dice: [ ");
        for (int d : diceSet) {
            System.out.print(d + " ");
        }
        System.out.print("]");
    }

    public Integer selectScore(int i){ //return null if val does not exist. Else returns score.
        return possibleScores.get(i);
    }

    public void calculateCombs(){
        this.possibleScores = new Hashtable<>();
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
                    possibleScores.put(i , Arrays.stream(diceSet).sum());
                    continue;
                }
            }
    }

    private void howManyIs(int i){
        int total = 0;
        for (int j: diceSet) {
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
                    possibleScores.put(i, Arrays.stream(diceSet).sum());
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
