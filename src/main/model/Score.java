package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents the score of the player, stores the points and username
 */
public class Score implements Comparable<Score>, Writable {

    private String username;
    private int points;

    public Score() {
        this.username = null;
        this.points = 0;
    }

    public Score(String username, int points) {
        this.username = username;
        this.points = points;
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

    /**
     * EFFECT:      Convert the object to String
     */
    @Override
    public String toString() {
        return username + ": " + points;
    }


    /**
     * MODIFIES:    this
     * Effect:      convert the instance of this object to json format
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("points", points);
        return json;
    }
}
