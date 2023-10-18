package persistence;

import model.Leaderboard;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LeaderboardJsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        LeaderboardJsonReader reader = new LeaderboardJsonReader("./data/noSuchFile.json");
        try {
            Leaderboard lb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyLeaderboard() {
        LeaderboardJsonReader reader = new LeaderboardJsonReader("./data/testReaderEmptyLeaderboard.json");
        try {
            Leaderboard lb = reader.read();
            assertEquals(0, lb.getScores().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralLeaderboard() {
        LeaderboardJsonReader reader = new LeaderboardJsonReader("./data/testReaderGeneralLeaderboard.json");
        try {
            Leaderboard lb = reader.read();
            assertEquals(3, lb.getScores().size());
            assertEquals("john", lb.getScores().get(0).getUsername());
            assertEquals(13, lb.getScores().get(0).getPoints());
            assertEquals("eli", lb.getScores().get(1).getUsername());
            assertEquals(12, lb.getScores().get(1).getPoints());
            assertEquals("abby", lb.getScores().get(2).getUsername());
            assertEquals(7, lb.getScores().get(2).getPoints());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
