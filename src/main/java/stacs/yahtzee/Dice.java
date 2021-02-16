package stacs.yahtzee;

import java.util.Arrays;
import java.util.Hashtable;

public class Dice {

    public int[] diceSet;
    public int[] possibleScores;
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

    public void setDiceSet(int[] newDiceSet) {
        try{
            for (int d: newDiceSet) {
                if(d < 1 || d > 6){
                    throw new IllegalArgumentException();
                }
            }
            if(newDiceSet.length != 5){
                throw new IllegalArgumentException();
            }
            else{
                this.diceSet = newDiceSet;
                calculateCombs();
                return;
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Invalid Dice Input");
        }
    }

    public void roll(int[] reRoll) {
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

    public int selectScore(int i){ //return null if val does not exist. Else returns score.
        return possibleScores[i];
    }

    public void calculateCombs(){
        System.out.println("START OF CALC COMBS");
        for (int i: diceSet) {
            System.out.print(i+" :");
        }
        possibleScores = new int[13];
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
                    possibleScores[i] = Arrays.stream(diceSet).sum();
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
        this.possibleScores[i] = total;
    }

    private void iOfAKind(int i){
        for (int j = 0; j < 6; j++) {
            if (possibleScores[j]>= (i - 3) * (j+1)) {
                possibleScores[i] = Arrays.stream(diceSet).sum();
                return;
            }
        }
        possibleScores[i] = 0;
        return;
    }

    private void fullHouse(){
            if (possibleScores[6] != 0) {
                for (int j = 0; j < 6; j++) {
                        if (possibleScores[j] == 2 * j) {
                            possibleScores[9] = 25;
                            return;
                        }

                }
            }
        possibleScores[9]  = 0;
        return;
    }

    private void smallStraight(){
        for (int j = 0; j < 3; j++) {
            boolean valid = true;
            for (int k = 0; k < 3; k++) {
                if (possibleScores[j+k] != j+k+1) {
                    valid = false;
                }
            }
            if(valid) {
                possibleScores[10] = 30;
                return;
            }
        }
        possibleScores[10] =0;
        return;
    }

    private void largeStraight() {
            if (possibleScores[10]==30) {
                if (possibleScores[0]==1 && possibleScores[4] ==5 ||
                        (possibleScores[1] ==2 && possibleScores[5] == 6)){
                    possibleScores[11] = 40;
                    return;
                }
            }

        possibleScores[11] = 0;
        return;
    }
}



