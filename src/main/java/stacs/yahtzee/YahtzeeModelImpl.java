package stacs.yahtzee;

/**
 *
 */
public class YahtzeeModelImpl implements YahtzeeModel {

    public int noPlayers; //number of players
    public Scoreboard scoreboard;
    public Dice dice;
    public int rollNo;
    public int playerTurn;
    public int roundNo;

    public YahtzeeModelImpl(int np) {
        try {
            if (np < 2) {
                throw new Exception();
            }
            this.noPlayers = np;
            this.dice = new Dice();
            this.scoreboard = new Scoreboard(np);
            initRound();
        }
        catch (Exception e){
            System.out.println("Must be at least 2 players.");
        }
    }

    public void initRound() {
        roundNo += 1;
        this.playerTurn = 0;
        initTurn();
    }

    public void initTurn(){
        this.playerTurn += 1;
        this.rollNo = 0;
        System.out.println("Player "+playerTurn);
        rollDice(new int[] {1,1,1,1,1});
    }

    @Override
    public int[] rollDice(int[] reRoll) {
        this.rollNo += 1;
        try {
            if (rollNo > 3 ) {
                throw new IndexOutOfBoundsException();
            }
            if(reRoll.length!= 5){
                throw new IllegalArgumentException();
            }
            for (int i: reRoll) {
                if(i!=0 && i!=1){
                    throw new IllegalArgumentException();
                }
            }
            dice.roll(reRoll);
            printCombinations();
            return dice.getDiceSet();
        }
        catch (IndexOutOfBoundsException e){ //might not be needed?
            System.out.println("No more than 3 rolls of dice each go.");
        }
        catch (IllegalArgumentException e){ //might not be needed?
            System.out.println("Invalid roll input");
        }
        return null;
    }

    @Override
    public void choseComb(int cat) {
        Integer result = dice.selectScore(cat);
        try {
            if (scoreboard.getPlayerScore(playerTurn)[cat] != -1 ) {
                throw new IndexOutOfBoundsException();
            }
            else{
                scoreboard.updateScorecard(result, cat, playerTurn);
                endTurn();
            }
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("please select valid combination input.");
        }
    }

    @Override
    public void printCombinations(){
        for (int i = 0; i <= 12 ; i++) {
            if(scoreboard.getPlayerScore(playerTurn)[i] == -1){
                System.out.println(i+". "+dice.comboTypes.get(i)+dice.possibleScores[i]);
            }
        }
    }

    @Override
    public void printScores() {
        int[] result = scoreboard.sumScores();
        System.out.println("Scores");
        for (int i = 0; i < noPlayers; i++) {
            System.out.println("Player" + (i + 1) + " : " + result[i]);
        }
    }

    public void endTurn(){
        if(playerTurn == noPlayers){
            endRound();
            return;
        }
        initTurn();
    }

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
        dice = null;
        rollNo = 0;
        playerTurn = 0;
        roundNo = 0;
    }


}
