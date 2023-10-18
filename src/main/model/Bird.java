package model;

import org.json.JSONObject;
import persistence.Writable;

/**
 * Represents the bird in the game
 */
public class Bird implements Writable {

    private final int flapLength = -7;
    private final int fallLength = 1;
    private int xcoor;
    private int ycoor;
    private boolean flapping;

    public Bird(int x, int y) {
        this.xcoor = x;
        this.ycoor = y;
        flapping = false;
    }

    public Position getPosition() {
        return new Position(xcoor, ycoor);
    }

    /**
     * REQUIRES:    flapping is false
     * MODIFIES:    this
     * EFFECT:      set the bird's state to flap
     * adjust the y coordinate
     */
    public void flap() {
        flapping = true;
        this.ycoor += flapLength;
    }

    /**
     * EFFECT:      check if the bird is flapping
     */
    public boolean isFlapping() {
        return flapping;
    }

    /**
     * MODIFIES:    this
     * EFFECT:      adjust the bird's y coordinate to make it fall to the ground
     */
    public void falls() {
        this.ycoor += fallLength;
    }

    public int getX() {
        return xcoor;
    }

    public void setFlapping(boolean flapping) {
        this.flapping = flapping;
    }

    /**
     * MODIFIES:    this
     * Effect:      convert the instance of this object to json format
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", xcoor);
        json.put("y", ycoor);
        return json;
    }
}
