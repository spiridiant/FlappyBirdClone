package persistence;


import model.Event;
import model.EventLog;
import model.Leaderboard;
import model.Score;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Leaderboard from JSON object and returns it
    private Leaderboard parseLeaderboard(JSONObject jsonObject) {
        Leaderboard ld = new Leaderboard(getScores(jsonObject));
        return ld;
    }

    private List<Score> getScores(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("scores");
        List<Score> scores = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextScore = (JSONObject) json;
            scores.add(getScore(nextScore));
        }
        return scores;
    }

    private Score getScore(JSONObject nextScore) {
        String username = nextScore.getString("username");
        int points = nextScore.getInt("points");
        return new Score(username, points);
    }
}
