package model;

public class Score implements Comparable<Score> {

    private String username;
    private int points;

    public Score() {
        this.username = null;
        this.points = 0;
    }

    public void incrementScore() {
        this.points++;
    }

    public int getPoints() {
        return points;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int compareTo(Score other) {
        return Integer.compare(other.points, this.points);
    }
}
