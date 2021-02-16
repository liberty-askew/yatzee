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

    public static void main(String[] args) {
        int[] dice = new int[]{1,1,1,1,1,1};
        int[] score = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    }


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


    /**
    public void updateCombs(int[] diceSet){
        this.combs = new Dice(diceSet, scores.getPlayerScore(playerTurn));
        return;
    }
    */

    @Override
    public int[] rollDice(int[] reRoll) {
        this.rollNo += 1;
        try {
            if (rollNo > 3 || rollNo < 0) {
                throw new IllegalArgumentException();
            }
            else{
                dice.roll(reRoll);
            }
            return dice.getDiceSet();
            /**
            if (rollNo == 3) {
                updateCombs(diceSet);
                printCombinations();
                return;
            }
             */
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
            if (result == null || scores.getPlayerScore(playerTurn)[cat] != -1 ) {
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


    /**
     public static void main(String[] args) {
     YahtzeeModel model = new YahtzeeModelImpl(2);
     model.rollDice(new int[]{0,1,1,1,1,1} ,2) ;

     }
     private void playRound(int round) {
     for (int j = 0; j < this.noPlayers; j++) {
     initTurn(j, round);
     this.rollNo = 0;
     }
     scores.printScores();
     }



     public void takeTurn(int playerNo, int round) {
     Scanner myObj = new Scanner(System.in);
     for (int rn = 0; rn <= 3; rn++) {

     }
     int[] playerScore = scores.getPlayerScore(playerNo);
     Combinations combs = new Combinations(diceSet , playerScore);
     combs.printCombs();
     System.out.println("Select score:");
     }

     * REMOVED FROM RE ROLL FOR NOW.
     String rollIn = myObj.nextLine();
     int[] reRoll = new int[]{0, 0, 0, 0, 0, 0};
     if(rollIn.length()==0){
     break;
     }
     String[] rolls = rollIn.split(" ");
     for (String r: rolls) {
     reRoll[Integer.valueOf(r)] = 1;
     }
     rollDice(reRoll , rn);
     }
     */

}
