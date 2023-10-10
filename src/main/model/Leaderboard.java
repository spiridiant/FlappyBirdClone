package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Leaderboard {
    private List<Score> scores;

    public Leaderboard() {
        scores = new ArrayList<Score>();
    }

    public void addScore(Score s) {
        scores.add(s);
        Collections.sort(scores);
    }

    public List<Score> getScores() {
        return scores;
    }
}
