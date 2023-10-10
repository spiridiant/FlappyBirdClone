package model;

public class Score implements Comparable<Score> {

    private String username;
    private int score;

    public Score() {
        this.username = null;
        this.score = 0;
    }

    public void incrementScore() {
        this.score++;
    }

    public int getScore() {
        return score;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public int compareTo(Score other) {
        return Integer.compare(this.score, other.score);
    }
}
