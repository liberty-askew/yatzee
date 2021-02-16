package stacs.yahtzee;

/**
 *
 */
public class YahtzeeModelImpl implements YahtzeeModel {

    public int noPlayers; //number of players
    public Scores scores;
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
            this.scores = new Scores(np);
            initRound();
        }
        catch (Exception e){
            System.out.println("Must be at least 2 players.");
        }
    }

    private void initRound() {
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
            if (rollNo > 3) {
                throw new IllegalArgumentException();
            }
            else{
                dice.roll(reRoll);
            }
            return dice.getDiceSet();
        }
        catch (Exception e){ //might not be needed?
            System.out.println("No more than 3 rolls of dice each go.");
        }
        return null;
    }

    @Override
    public void choseComb(int cat) {
        Integer result = dice.selectScore(cat);
        try {
            if (scores.getPlayerScore(playerTurn)[cat] != -1 ) {
                throw new IndexOutOfBoundsException();
            }
            else{
                scores.updateScorecard(result, cat, playerTurn);
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
            if(scores.getPlayerScore(playerTurn)[i] == -1){
                System.out.println(i+". "+dice.comboTypes.get(i)+dice.possibleScores[i]);
            }
        }
    }

    @Override
    public void printScores() {
        int[] result = scores.sumScores();
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
        System.out.println("Winner: Player"+scores.getWinner()[0]+" Score:"+scores.getWinner()[1]);
    }


}
