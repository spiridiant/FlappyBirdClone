package persistence;

import org.junit.jupiter.api.Test;
import model.FBGame;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FBGameJsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        FBGameJsonReader reader = new FBGameJsonReader("./data/noSuchFile.json");
        try {
            FBGame game = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyFBGame() {
        FBGameJsonReader reader = new FBGameJsonReader("./data/testReaderEmptyFBGame.json");
        try {
            FBGame game = reader.read();
            assertEquals(0, game.getTubes().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralFBGame() {
        FBGameJsonReader reader = new FBGameJsonReader("./data/testReaderGeneralFBGame.json");
        try {
            FBGame game = reader.read();
            assertEquals(2, game.getTubes().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
