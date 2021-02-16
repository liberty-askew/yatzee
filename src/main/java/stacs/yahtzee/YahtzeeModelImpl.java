package stacs.yahtzee;


public class YahtzeeModelImpl implements YahtzeeModel {

    public int noPlayers; //number of players
    public Scoreboard scoreboard;
    public Dice dice;
    public int rollNo; //{1..3}
    public int playerTurn; // {1..noPlayers}
    public int roundNo; // {1..13}


    /**
     * Implements the Yahtzee game model. Model has a scoreboard and a dice.
     * User interacts with the game using methods in interface only. Model handles
     * turn taking, round numbers and ending game automatically.
     * @param noPlayers - number of players > 1
     */
    public YahtzeeModelImpl(int noPlayers) {

        try {
            if (noPlayers < 2) { //throws exception if less than 2 players in game.
                throw new Exception();
            }
            this.noPlayers = noPlayers;
            this.dice = new Dice();
            this.scoreboard = new Scoreboard(noPlayers);
            initRound(); //starts round for game.
        }
        catch (Exception e){
            System.out.println("Must be at least 2 players.");
        }
    }

    /**
     * Increments the game round number. Starts player 1s turn for this round.
     */
    public void initRound() {

        roundNo += 1;
        this.playerTurn = 0;
        initTurn();
    }

    /**
     * Increments player's turn number. Starts the first dice roll for player's turn.
     */
    public void initTurn(){

        this.playerTurn += 1;
        this.rollNo = 0;
        rollDice(new int[] {1,1,1,1,1}); //rolls dice at start of turn.
    }


    @Override
    public int[] rollDice(int[] reRoll) {

        this.rollNo += 1; //counter for each time dice rolled. Max = 3
        try {
            if (rollNo > 3 ) {
                throw new IndexOutOfBoundsException();
            }
            if(reRoll.length!= 5){ //5 dice in game.
                throw new IllegalArgumentException();
            }
            for (int i: reRoll) {
                if(i!=0 && i!=1){  //1 - reroll. 0 - fix value.
                    throw new IllegalArgumentException();
                }
            }
            dice.roll(reRoll);
            printCombinations(); //prints output for user of valid combinations.
            return dice.getDiceSet();
        }
        catch (IndexOutOfBoundsException e){ //maximum 3 rolls per turn.
            System.out.println("No more than 3 rolls of dice each go.");
        }
        catch (IllegalArgumentException e){
            System.out.println("Invalid roll input.");
        }
        return null;
    }


    @Override
    public void choseComb(int cat) {

        int result = dice.selectScore(cat);
        try {
            //player can only select categories which they have not already allocated.
            if (scoreboard.getPlayerScore(playerTurn)[cat] != -1 ) {
                throw new IndexOutOfBoundsException();
            }
            else{
                scoreboard.updateScorecard(result, cat, playerTurn);
                endTurn(); //once selected score the player's turn is over.
            }
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("Please select valid combination input.");
        }
    }


    @Override
    public void printCombinations(){

        System.out.println();
        for (int i = 0; i <= 12 ; i++) {
            //only prints combination categories which the player has not already allocated.
            if(scoreboard.getPlayerScore(playerTurn)[i] == -1){
                System.out.println(i+". "+dice.comboTypes.get(i)+dice.possibleScores[i]);
            }
        }
    }


    @Override
    public void printScores() {

        int[] result = scoreboard.sumScores();
        System.out.println("Scores:");
        for (int i = 0; i < noPlayers; i++) {
            System.out.println("Player" + (i + 1) + " : " + result[i]);
        }
    }


    /**
     * Automatically triggered when category chosen. Triggers next player's turn
     * or starts new round once all the players taken a turn.
     */
    public void endTurn(){

        if(playerTurn == noPlayers){
            endRound();
            return;
        }
        initTurn();
    }


    /**
     * Triggered once all players have taken a turn.
     * Ends game automatically once 13 rounds have been played.
     */
    public void endRound(){

        if(roundNo == 13){
            endGame();
            return;
        }
        initRound();
    }


    @Override
    public void endGame(){

        System.out.println("GAME OVER");
        printScores();
        System.out.println("Winner: Player"+ scoreboard.getWinner()[0]+" Score:"+ scoreboard.getWinner()[1]);
        dice = null; //resets key attributes so user must start new game to continue.
        rollNo = 0;
        playerTurn = 0;
        roundNo = 0;
    }
}
