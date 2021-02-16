package stacs.yahtzee;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ScoresTests {

    private Dice testDice;

    @BeforeEach
    public void setup(){
        testDice = new Dice();
    }

    @Test
    public void exists(){
        assertNotNull(testDice);
    }
}
