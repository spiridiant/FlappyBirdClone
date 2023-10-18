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
        assertEquals(16, tube.getBody().size());
    }

    @Test
    public void testMoveLeft(){
        tube.moveLeft();
        assertEquals(9, tube.getX());
        assertEquals(16, tube.getBody().size());
    }

    @Test
    public void testMoveLeftThrice(){
        tube.moveLeft();
        tube.moveLeft();
        tube.moveLeft();

        assertEquals(7, tube.getX());
        assertEquals(16, tube.getBody().size());
    }
}
