package stacs.yahtzee;

/**
 *
 */
public class YahtzeeModelImpl implements YahtzeeModel {

    public int noPlayers; //number of players
    public int[] diceSet;
    public Scores scores;
    public Combinations combs;
    public int rollNo;
    public int playerTurn;
    public int roundNo;

    public static void main(String[] args) {
        int[] dice = new int[]{1,1,1,1,1,1};
        int[] score = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        Combinations comb = new Combinations(dice,score);
        for (int i = 0; i < 12; i++) {
            System.out.println(comb.selectScore(i) + " " + i);
        }
    }


    public YahtzeeModelImpl(int np) {
        try {
            if (np < 2) {
                throw new Exception();
            }
            this.noPlayers = np;
            this.diceSet = new int[]{0, 0, 0, 0, 0};
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

    public int[] getDiceSet(){
        return diceSet;
    }

    public void updateCombs(int[] diceSet){
        this.combs = new Combinations(diceSet, scores.getPlayerScore(playerTurn));
        return;
    }

    @Override
    public void rollDice(int[] reRoll) {
        this.rollNo += 1;
        try {
            if (rollNo > 3 || rollNo < 0) {
                throw new IllegalArgumentException();
            }
            for (int i = 0; i < 5; i++) {
                if (reRoll[i] == 1) {
                    this.diceSet[i] = (int) Math.ceil(Math.random() * 6);
                }
            }
            System.out.print("Dice: [ ");
            for (int d : diceSet) {
                System.out.print(d + " ");
            }
            System.out.print("]");
            if (rollNo == 3) {
                updateCombs(diceSet);
                printCombinations();
                return;
            }
        }
        catch (Exception e){ //might not be needed?
            System.out.println("No more than 3 rolls of dice each go.");
        }
    }

    @Override
    public void choseComb(int cat) {
        if(combs == null){
            updateCombs(diceSet);
        }
        Integer result = this.combs.selectScore(cat);
        if (result != null) {
            scores.updateScorecard(result, cat, playerTurn);
        }
        endTurn();
    }

    @Override
    public void printCombinations(){
        for (int i = 0; i <= 12 ; i++) {
            if(combs.possibleScores.get(i) != null){
                System.out.println(i+". "+combs.comboTypes.get(i)+combs.possibleScores.get(i));
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
