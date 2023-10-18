package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreTest {
    private Score score1;
    private Score score2;
    private Score score3;

    @BeforeEach
    public void beforeTest(){
        score1 = new Score();
        score2 = new Score();
        score3 = new Score("test3", 5);

        score1.setUsername("test1");
        score2.setUsername("test2");
        addPoints(score2, 5);
    }

    @Test
    public void testConstructor(){
        assertEquals("test1", score1.getUsername());
        assertEquals("test2", score2.getUsername());
        assertEquals("test3", score3.getUsername());

        assertEquals(0, score1.getPoints());
        assertEquals(5, score2.getPoints());
        assertEquals(5, score3.getPoints());
    }

    @Test
    public void testIncrementScore(){
        addPoints(score1, 10);
        assertEquals(10, score1.getPoints());
    }

    @Test
    public void testCompare(){
        assertEquals(1, score1.compareTo(score2));
        assertEquals(0, score2.compareTo(score3));
        assertEquals(-1, score2.compareTo(score1));
    }

    private void addPoints(Score score, int points){
        for(int i = 0; i < points; i++){
            score.incrementScore();
        }
    }
}
