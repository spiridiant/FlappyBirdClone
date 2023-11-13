package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BirdTest {

    private Bird bird;

    @BeforeEach
    public void beforeTest(){
        bird = new Bird(10, 10);
    }

    @Test
    public void testConstructor(){
        assertEquals(10, bird.getX());
        assertEquals(10, bird.getY());
        assertFalse(bird.isFlapping());
        bird.setFlapping(true);
        assertTrue(bird.isFlapping());
    }

    @Test
    public void testFlap(){
        bird.flap();
        assertTrue(bird.isFlapping());
        assertEquals(10 + bird.flapLength, bird.getY());
    }

    @Test
    public void testFlapTwice(){
        bird.flap();
        bird.flap();
        assertTrue(bird.isFlapping());
        assertEquals(10 + bird.flapLength  * 2, bird.getY());
    }

    @Test
    public void testFall(){
        bird.falls();
        assertFalse(bird.isFlapping());
        assertEquals(10 + bird.fallLength, bird.getY());
    }

    @Test
    public void testFallTwice(){
        bird.falls();
        bird.falls();
        assertFalse(bird.isFlapping());
        assertEquals(10 + bird.fallLength * 2, bird.getY());
    }


}
