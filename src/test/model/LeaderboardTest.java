package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardTest {
    Leaderboard testBoard;
    Score score1;
    Score score2;
    Score score3;

    @BeforeEach
    public void beforeTest(){
        testBoard = new Leaderboard();
        score1 = new Score();
        score2 = new Score();
        score3 = new Score();

        addPoints(score1, 5);
        addPoints(score2, 4);
        addPoints(score3, 6);
    }

    @Test
    public void testAddOneScore(){
        testBoard.addScore(score1);

        List<Score> scores = testBoard.getScores();
        assertEquals(1, scores.size());
        assertEquals(score1, scores.get(0));
    }

    @Test
    public void testAddMultiScores(){
        testBoard.addScore(score1);
        testBoard.addScore(score2);
        testBoard.addScore(score3);

        List<Score> scores = testBoard.getScores();
        assertEquals(3, scores.size());
        assertEquals(score3, scores.get(0));
        assertEquals(score1, scores.get(1));
        assertEquals(score2, scores.get(2));
    }


    private void addPoints(Score score, int points){
        for(int i = 0; i < points; i++){
            score.incrementScore();
        }
    }

}
