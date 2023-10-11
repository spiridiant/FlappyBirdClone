package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class testBird {

    private Bird bird;

    @BeforeEach
    public void beforeTest(){
        bird = new Bird(10, 10);
    }

    @Test
    public void testConstructor(){
        assertEquals(10, bird.getX());
        assertEquals(new Position(10, 10), bird.getPosition());
        assertFalse(bird.isFlapping());
        bird.setFlapping(true);
        assertTrue(bird.isFlapping());
    }

    @Test
    public void testFlap(){
        bird.flap();
        assertTrue(bird.isFlapping());
        assertEquals(new Position(10, 3), bird.getPosition());
    }

    @Test
    public void testFlapTwice(){
        bird.flap();
        bird.flap();
        assertTrue(bird.isFlapping());
        assertEquals(new Position(10, -4), bird.getPosition());
    }

    @Test
    public void testFall(){
        bird.falls();
        assertFalse(bird.isFlapping());
        assertEquals(new Position(10, 11), bird.getPosition());
    }

    @Test
    public void testFallTwice(){
        bird.falls();
        bird.falls();
        assertFalse(bird.isFlapping());
        assertEquals(new Position(10, 12), bird.getPosition());
    }


}
