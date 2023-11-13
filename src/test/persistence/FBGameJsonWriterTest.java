package persistence;

import model.Bird;
import model.FBGame;
import model.Score;
import model.Tube;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FBGameJsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            FBGame game = new FBGame();
            FBGameJsonWriter writer = new FBGameJsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyFBGame() {
        try {
            FBGame game = new FBGame();
            FBGameJsonWriter writer = new FBGameJsonWriter("./data/testWriterEmptyFBGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            FBGameJsonReader reader = new FBGameJsonReader("./data/testWriterEmptyFBGame.json");
            game = reader.read();
            assertEquals(0, game.getTubes().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralFBGame() {
        try {
            Bird bird = new Bird(10, 10);
            Score score = new Score("test", 10);
            Deque<Tube > tubes = new ArrayDeque<>();
            tubes.add(new Tube(5, 5, 10, 20));
            FBGame game = new FBGame(bird, score, tubes);

            FBGameJsonWriter writer = new FBGameJsonWriter("./data/testWriterGeneralFBGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            FBGameJsonReader reader = new FBGameJsonReader("./data/testWriterGeneralFBGame.json");
            game = reader.read();
            assertEquals(1, game.getTubes().size());
            assertEquals(10, game.getBird().getX());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
