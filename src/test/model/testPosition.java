package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class testPosition {

    private Position position1;
    private Position position2;
    private Position position3;

    @BeforeEach
    public void beforeTest(){
        position1 = new Position(10, 20);
        position2 = new Position(10, 20);
        position3 = new Position(20, 20);
    }

    @Test
    public void testConstructor(){
        assertEquals(10, position1.getX());
        assertEquals(20, position1.getY());
    }

    @Test
    public void testEqualTrue(){
        assertTrue(position1.equals(position1));
        assertTrue(position1.equals(position2));
    }

    @Test
    public void testEqualFalse(){
        assertFalse(position1.equals(position3));
    }

    @Test
    public void testHashCode(){
        assertEquals(Objects.hash(10, 20), position1.hashCode());
    }
}
