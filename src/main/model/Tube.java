package model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Tube {
    private Deque<Position> body;
    private int x;
    private int spaceStart;
    private int spaceEnd;
    private int maxY;

    public Tube(int x, int spaceStart, int spaceEnd, int maxY) {
        body = new ArrayDeque<>();
        this.x = x;
        this.spaceStart = spaceStart;
        this.spaceEnd = spaceEnd;
        this.maxY = maxY;
        fillBody();
    }

    private void fillBody() {
        for (int i = 0; i < spaceStart; i++) {
            body.add(new Position(x, i));
        }

        for (int i = spaceEnd; i < maxY; i++) {
            body.add(new Position(x, i));
        }
    }

    public void moveLeft() {
        int size = body.size();
        x--;
        for (int i = 0; i < size; i++) {
            int y = body.getFirst().getY();
            body.add(new Position(x, y));
            body.removeFirst();
        }

    }

    public Deque<Position> getBody() {
        return body;
    }

    public int getX() {
        return x;
    }


}
