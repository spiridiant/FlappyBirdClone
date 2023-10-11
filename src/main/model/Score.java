package model;

/**
 *      Represents the score of the player, stores the points and username
 */
public class Score implements Comparable<Score> {

    private String username;
    private int points;

    public Score() {
        this.username = null;
        this.points = 0;
    }

    /**
     * MODIFIES:    this
     * EFFECT:      increment the score by one
     */
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

    /**
     * REQUIRES:    other is not null
     * EFFECT:      compare this and other
     */
    @Override
    public int compareTo(Score other) {
        return Integer.compare(other.points, this.points);
    }
}
