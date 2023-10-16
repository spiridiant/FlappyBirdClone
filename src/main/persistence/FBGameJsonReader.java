package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Stream;

public class FBGameJsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public FBGameJsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Leaderboard from file and returns it;
    // throws IOException if an error occurs reading data from file
    public FBGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFBGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Leaderboard from JSON object and returns it
    private FBGame parseFBGame(JSONObject jsonObject) {
        int maxX = jsonObject.getInt("maxX");
        int maxY = jsonObject.getInt("maxY");
        Bird bird = readBird(jsonObject);
        Score score = readScore(jsonObject);
        Deque<Tube> tubes = readTubes(jsonObject);

        FBGame game = new FBGame(maxX, maxY, bird, score, tubes);
        return game;
    }

    private Deque<Tube> readTubes(JSONObject jsonObject) {
        Deque<Tube> tubes = new ArrayDeque<>();
        JSONArray jsonArray = jsonObject.getJSONArray("tubes");
        for (Object json : jsonArray) {
            JSONObject nextTube = (JSONObject) json;
            tubes.add(readTube(nextTube));
        }
        return tubes;
    }

    private Tube readTube(JSONObject jsonObject) {
        int x = jsonObject.getInt("x");
        int spaceStarts = jsonObject.getInt("spaceStart");
        int spaceEnds = jsonObject.getInt("spaceEnds");
        int maxY = jsonObject.getInt("maxY");
        Tube tube = new Tube(x, spaceStarts, spaceEnds, maxY);
        return tube;
    }

    private Score readScore(JSONObject jsonObject) {
        Score score = new Score(jsonObject.getString("username"), jsonObject.getInt("points"));
        return score;
    }

    private Bird readBird(JSONObject jsonObject) {
        Bird bird = new Bird(jsonObject.getInt("x"), jsonObject.getInt("y"));
        return bird;
    }
}
