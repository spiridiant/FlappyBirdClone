package model;

import java.awt.*;
import java.util.List;

public class Bird {
    private Position position;

    private int x;
    private int y;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position getPosition() {
        return position;
    }
}
