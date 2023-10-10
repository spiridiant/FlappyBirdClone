package model;

import java.awt.*;
import java.util.List;

public class Bird {

    private final int flapLength = -7;
    private final int fallLength = 1;
    private int x;


    private int y;
    private boolean flapping;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position getPosition() {
        return new Position(x, y);
    }

    public void flap() {
        flapping = true;
        this.y += flapLength;
    }

    public boolean isFlapping() {
        return flapping;
    }

    public void falls() {
        this.y += fallLength;
    }

    public int getX() {
        return x;
    }

    public void setFlapping(boolean flapping) {
        this.flapping = flapping;
    }
}
