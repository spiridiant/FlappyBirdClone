package model;

import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;


/**
 * Represent the tube in the game
 * the tube has two parts with a space between
 */
public class Tube implements Writable {
    public static final int WIDTH = 30;
    public static final Color COLOR = Color.GREEN;
    private int xcoor;

    public int getSpaceStart() {
        return spaceStart;
    }

    public int getSpaceEnd() {
        return spaceEnd;
    }

    private int spaceStart;
    private int spaceEnd;
    private int maxY;

    public Tube(int x, int spaceStart, int spaceEnd, int maxY) {
        this.xcoor = x;
        this.spaceStart = spaceStart;
        this.spaceEnd = spaceEnd;
        this.maxY = maxY;
    }


    /**
     * MODIFIES:    this
     * EFFECT:      move the tube to the left by one
     */
    public void moveLeft() {
        xcoor -= 2;
    }

    public int getX() {
        return xcoor;
    }


    /**
     * MODIFIES:    this
     * Effect:      convert the instance of this object to json format
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("x", xcoor);
        json.put("spaceStart", spaceStart);
        json.put("spaceEnd", spaceEnd);
        json.put("maxY", maxY);
        return json;
    }
}
