package persistence;

import model.Leaderboard;
import model.Score;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LeaderboardJsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Leaderboard lb = new Leaderboard();
            LeaderboardJsonWriter writer = new LeaderboardJsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyLeaderboard() {
        try {
            Leaderboard lb = new Leaderboard();
            LeaderboardJsonWriter writer = new LeaderboardJsonWriter("./data/testWriterEmptyLeaderboard.json");
            writer.open();
            writer.write(lb);
            writer.close();

            LeaderboardJsonReader reader = new LeaderboardJsonReader("./data/testWriterEmptyLeaderboard.json");
            lb = reader.read();
            assertEquals(0, lb.getScores().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLeaderboard() {
        try {
            Leaderboard lb = new Leaderboard();
            lb.addScore(new Score("test1", 10));
            lb.addScore(new Score("test2", 15));
            LeaderboardJsonWriter writer = new LeaderboardJsonWriter("./data/testWriterGeneralLeaderboard.json");
            writer.open();
            writer.write(lb);
            writer.close();

            LeaderboardJsonReader reader = new LeaderboardJsonReader("./data/testWriterGeneralLeaderboard.json");
            lb = reader.read();
            List<Score> scores = lb.getScores();
            assertEquals(2, scores.size());
            assertEquals(15, scores.get(0).getPoints());
            assertEquals("test2", scores.get(0).getUsername());
            assertEquals(10, scores.get(1).getPoints());
            assertEquals("test1", scores.get(1).getUsername());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
