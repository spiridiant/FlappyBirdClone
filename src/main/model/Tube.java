package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;


/**
 * Represent the tube in the game
 * the tube has two parts with a space between
 */
public class Tube implements Writable {
    private Deque<Position> body;
    private int xcoor;
    private int spaceStart;
    private int spaceEnd;
    private int maxY;

    public Tube(int x, int spaceStart, int spaceEnd, int maxY) {
        body = new ArrayDeque<>();
        this.xcoor = x;
        this.spaceStart = spaceStart;
        this.spaceEnd = spaceEnd;
        this.maxY = maxY;
        fillBody();
    }

    /**
     * MODIFIES:    this
     * EFFECT:      fill all the position that forms the body of the tube
     * fill the space hash set
     */
    private void fillBody() {
        for (int i = -1; i < spaceStart; i++) {
            body.add(new Position(xcoor, i));
        }

        for (int i = spaceEnd; i < maxY; i++) {
            body.add(new Position(xcoor, i));
        }
    }

    /**
     * MODIFIES:    this
     * EFFECT:      move the tube to the left by one
     */
    public void moveLeft() {
        xcoor--;
        for (Position pos : body) {
            pos.toLeft();
        }
    }

    public Deque<Position> getBody() {
        return body;
    }

    public int getX() {
        return xcoor;
    }


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
