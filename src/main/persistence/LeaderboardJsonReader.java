package persistence;


import model.Leaderboard;
import model.Score;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads Leaderboard from JSON data stored in file
public class LeaderboardJsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public LeaderboardJsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Leaderboard from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Leaderboard read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLeaderboard(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Leaderboard from JSON object and returns it
    private Leaderboard parseLeaderboard(JSONObject jsonObject) {
        Leaderboard ld = new Leaderboard();
        addScores(ld, jsonObject);
        return ld;
    }

    // MODIFIES: ld
    // EFFECTS: parses Scores from JSON object and adds them to workroom
    private void addScores(Leaderboard ld, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("scores");
        for (Object json : jsonArray) {
            JSONObject nextScore = (JSONObject) json;
            addScore(ld, nextScore);
        }
    }

    // MODIFIES: ld
    // EFFECTS: parses score from JSON object and adds it to Leaderboard
    private void addScore(Leaderboard ld, JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        int points = jsonObject.getInt("points");
        Score score = new Score(username, points);
        ld.addScore(score);
    }
}
