package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *      The leaderboard that stores the scores of the players
 */
public class Leaderboard {
    private List<Score> scores;

    public Leaderboard() {
        scores = new ArrayList<Score>();
    }

    /**
     * REQUIRES:    s is not null
     * MODIFIES:    this
     * EFFECT:      add s to the list of Score
     */
    public void addScore(Score s) {
        scores.add(s);
        Collections.sort(scores);
    }

    public List<Score> getScores() {
        return scores;
    }
}
