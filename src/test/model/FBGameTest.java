package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FBGameTest {
    private FBGame testGame;

    @BeforeEach
    public void beforeTest() {
        testGame = new FBGame();
    }

    @Test
    public void testTick() {
        testGame.update();
        assertEquals(1, testGame.getTubes().size());
        assertFalse(testGame.isEnded());
    }



    @Test
    public void testTickALot() {
        for (int i = 0; i < 1000; i++) {
            testGame.update();
            if (i % 7 == 0) {
                testGame.getBird().flap();
            }
        }
        testGame.update();
        testGame.update();
        testGame.update();
        testGame.update();
        testGame.update();
        assertTrue(testGame.isEnded());
    }


    @Test
    public void testTickTillEnd() {
        for (int i = 0; i < testGame.HEIGHT; i++) {
            testGame.update();
        }
        assertTrue(testGame.isEnded());
    }

    @Test
    public void testIsValidPositionPositive() {
        assertTrue(testGame.isValidPosition(3, 9));
    }

    @Test
    public void testIsValidPosition() {
        for(int i = 0; i < testGame.WIDTH / 2; i++) {
            testGame.update();
        }
        int x_pos = testGame.WIDTH / 2;
        int y_pos = (testGame.getTubes().getFirst().getSpaceStart() + testGame.getTubes().getFirst().getSpaceEnd()) / 2;
        assertTrue(testGame.isValidPosition(x_pos, y_pos));
    }

    @Test
    public void testGetScore() {
        assertEquals(0, testGame.getScore().getPoints());
    }
}
