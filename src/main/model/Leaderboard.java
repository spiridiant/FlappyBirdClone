package model;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The leaderboard that stores the scores of the players
 */
public class Leaderboard implements Writable {

    private List<Score> scores;

    public Leaderboard() {
        this.scores = new ArrayList<>();
    }

    public Leaderboard(List<Score> scores) {
        this.scores = scores;
        EventLog.getInstance().logEvent(new Event("Leaderboard Loaded."));
    }

    /**
     * REQUIRES:    s is not null
     * MODIFIES:    this
     * EFFECT:      add s to the list of Score
     */
    public void addScore(Score s) {
        scores.add(s);
        Collections.sort(scores);
        EventLog.getInstance().logEvent(new Event("Score '" + s + "' has been added to the Leaderboard"));
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }


    public List<Score> getScores() {
        EventLog.getInstance().logEvent(new Event("Viewing scores on the Leaderboard"));
        return scores;
    }


    /**
     * MODIFIES:    this
     * Effect:      convert the instance of this object to json format
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("scores", scoresToJson());
        EventLog.getInstance().logEvent(new Event("Leaderboard Saved"));
        return json;
    }

    /**
     * MODIFIES:    this
     * Effect:      convert the list scores to json format
     */
    private JSONArray scoresToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Score score : scores) {
            jsonArray.put(score.toJson());
        }

        return jsonArray;
    }
}
