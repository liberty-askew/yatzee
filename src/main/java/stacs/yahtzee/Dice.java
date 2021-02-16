package stacs.yahtzee;

import java.util.Arrays;
import java.util.Hashtable;

public class Dice {

    public int[] diceSet;
    public int[] possibleScores;
    public Hashtable<Integer,String> comboTypes;

    /***
     * KEY
     * Index of possibleScores - Combination Type
     * e.g possibleScores[1] = score for 2s combination.
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

    /**
     * Handles the game's dice and calculates scoring combinations.
     */
    public Dice(){

        this.diceSet = new int[5];
        roll(new int[]{1,1,1,1,1}); //rolls all 5 dice at start.

        comboTypes = new Hashtable<>();  //used for storing and printing combination category names.
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


    public int[] getDiceSet() {
        return this.diceSet;
    }


    public void setDiceSet(int[] newDiceSet) {

        try{
            for (int d: newDiceSet) {
                if(d < 1 || d > 6){ //throws exception if dice values not {1-6}
                    throw new IllegalArgumentException();
                }
            }
            if(newDiceSet.length != 5){ //throws exception if try to throw more than 5 dice.
                throw new IllegalArgumentException();
            }
            else{
                this.diceSet = newDiceSet;
                calculateCombs();
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Invalid Dice Input");
        }
    }

    /**
     * Rolls dice randomly and updates diceSet.
     * @param reRoll -  index of die to be re-rolled. Len=5, reRoll[i]={0,1} e.g reRoll = [1,1,1,1,1]
     *                  reRolls all 6 dice. reRoll = [1,0,0,0,0] only rerolls first die.
     */
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
        System.out.println();
        System.out.print("Dice: [ "); //Print out for user
        for (int d : diceSet) {
            System.out.print(d + " ");
        }
        System.out.print("]");
    }

    /**
     * Gets the score for a category of combination based on the dice values.
     * @param cat - category of combination user wants to select.
     * @return - score for category based on dice.
     */
    public int selectScore(int cat){

        return possibleScores[cat];
    }

    /**
     * Calculates the possible score for each combination {0-12} and updates possibleScore.
     * Independent of whether player has already used this combination.
     */
    public void calculateCombs(){

        possibleScores = new int[13];
        for (int i = 0; i <= 12; i++) {
                if (i <6){ // Ones, Twos....Sixes
                    howManyIs(i);
                    continue;
                }
                if(i < 9){ //3 or 4 of a Kind and Yahtzee
                    iOfAKind(i);
                    continue;
                }
                if(i == 9){
                    fullHouse();
                }
                if(i == 10){
                    smallStraight();
                }
                if(i == 11){
                    largeStraight();
                }
                if (i == 12){ //Chance
                    possibleScores[i] = Arrays.stream(diceSet).sum();
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
    }


    private void fullHouse(){

        if (possibleScores[6] != 0) { //must have 3 of a kind.
            for (int j = 0; j < 6; j++) {
                    if (possibleScores[j] == 2 * j) {
                        possibleScores[9] = 25;
                        return;
                    }

            }
        }
        possibleScores[9]  = 0;
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
    }


    private void largeStraight() {

        if (possibleScores[10]==30) { //must be a small straight.
            if (possibleScores[0]==1 && possibleScores[4] ==5 ||
                    (possibleScores[1] ==2 && possibleScores[5] == 6)){
                possibleScores[11] = 40;
                return;
            }
        }
        possibleScores[11] = 0;
    }
}



