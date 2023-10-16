package model;

import java.util.Objects;

/**
 * From SnakeConsole
 * Represents a position in the game space.
 */
public class Position {
    private int xcoor;
    private int ycoor;

    public Position(int x, int y) {
        this.xcoor = x;
        this.ycoor = y;
    }

    public int getX() {
        return xcoor;
    }

    public int getY() {
        return ycoor;
    }

    /**
     * REQUIRES:    o is not null
     * EFFECT:      check if this and o is equal, i.e. have the same x and y
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position pos = (Position) o;
        return xcoor == pos.xcoor && ycoor == pos.ycoor;
    }

    /**
     * EFFECT:      return the hash code of this
     */
    @Override
    public int hashCode() {
        return Objects.hash(xcoor, ycoor);
    }
}
