package ui;

import java.io.IOException;

/**
 *      The main class that starts the program
 */
public class FlappyBird {
    public static void main(String[] args) throws IOException, InterruptedException {
        StartMenu start = new StartMenu();
        start.runStartMenu();
    }
}
