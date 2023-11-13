package model;

import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;

/**
 * Represents the bird in the game
 */
public class Bird implements Writable {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 15;
    public static final int fallLength = 2;
    public static final int flapLength = -100;
    public static final Color COLOR = Color.RED;

    private int xcoor;
    private int ycoor;

    public int getY() {
        return ycoor;
    }

    private boolean flapping;

    public Bird(int x, int y) {
        this.xcoor = x;
        this.ycoor = y;
        flapping = false;
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
