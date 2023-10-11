package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FBGameTest {
    private FBGame testGame;
    @BeforeEach
    public void beforeTest(){
        testGame = new FBGame(6, 10);
    }

    @Test
    public void testTick(){
        testGame.tick();
        assertEquals(1, testGame.getTubes().size());
        assertFalse(testGame.isEnded());
    }

    @Test
    public void testTickTillNewTube() {
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();

        assertEquals(2, testGame.getTubes().size());
    }

    @Test
    public void testTickTillTubeGone() {
        testGame.tick();
        Tube oldtube = testGame.getTubes().getFirst();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();

        assertFalse(oldtube.equals(testGame.getTubes().getFirst()));
    }


    @Test
    public void testTickALot() {
        for(int i = 0; i < 1000; i++){
            testGame.tick();
            if(i % 7 == 0){
                testGame.getBird().flap();
            }
        }
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        assertTrue(testGame.isEnded());
    }



    @Test
    public void testTickTillEnd() {
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        testGame.tick();
        assertTrue(testGame.isEnded());
    }

    @Test
    public void testIsValidPositionPositive(){
        assertTrue(testGame.isValidPosition(new Position(3, 9)));
    }

    @Test
    public void testIsValidPositionNegative(){
        assertFalse(testGame.isValidPosition(new Position(3, 10)));
    }

    @Test
    public void testGetScore(){
        assertEquals(0, testGame.getScore().getPoints());
    }

    @Test
    public void testGetGround(){
        assertEquals(7, testGame.getGround().size());
    }
}
