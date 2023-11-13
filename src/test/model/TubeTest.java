package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TubeTest {

    private Tube tube;

    @BeforeEach
    public void beforeTest(){
        tube = new Tube(10, 5, 10, 20);
    }

    @Test
    public void testConstructors(){
        assertEquals(10, tube.getX());
        assertEquals(5, tube.getSpaceStart());
        assertEquals(10, tube.getSpaceEnd());
    }

    @Test
    public void testMoveLeft(){
        tube.moveLeft();
        assertEquals(10 + tube.leftLength, tube.getX());
    }

    @Test
    public void testMoveLeftThrice(){
        tube.moveLeft();
        tube.moveLeft();
        tube.moveLeft();

        assertEquals(10 + tube.leftLength * 3, tube.getX());
    }
}
