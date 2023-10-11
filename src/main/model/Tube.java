package model;

import java.util.ArrayDeque;
import java.util.Deque;


/**
 *      Represent the tube in the game
 *      the tube has two parts with a space between
 */
public class Tube {
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
     */
    private void fillBody() {
        for (int i = 0; i < spaceStart; i++) {
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
        int size = body.size();
        xcoor--;
        for (int i = 0; i < size; i++) {
            int y = body.getFirst().getY();
            body.add(new Position(xcoor, y));
            body.removeFirst();
        }

    }

    public Deque<Position> getBody() {
        return body;
    }

    public int getX() {
        return xcoor;
    }


}
