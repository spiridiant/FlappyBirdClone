package ui;

import model.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *      The start menu in the console
 *      take user commands and execute them
 */
public class StartMenu {

    private Scanner input;
    private Leaderboard leaderboard;

    public StartMenu() {
        leaderboard = new Leaderboard();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    /**
     * EFFECT:      run the start menu
     *              get command from user
     *              execute commands
     */
    public void runStartMenu() throws IOException, InterruptedException {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
        System.exit(0);
    }

    /**
     * EFFECT:      display all the option user has
     */
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tg -> start new game");
        System.out.println("\tl -> view leaderboard");
        System.out.println("\tq -> quit");
    }

    /**
     * MODIFIES:    this
     * EFFECT:      processes user command
     *              if command not valid display error message
     */
    private void processCommand(String command) throws IOException, InterruptedException {
        if (command.equals("g")) {
            doNewGame();
        } else if (command.equals("l")) {
            doLeaderboard();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    /**
     * MODIFIES:    this
     * EFFECT:      instantiate a FlappyBirdGame object for a new game
     *              when the game finished and user score is positive
     *              ask for user's username to add the score to leaderboard
     */
    private void doNewGame() throws IOException, InterruptedException {
        FlappyBirdGame gameHandler = new FlappyBirdGame();

        gameHandler.start();

        Score score = gameHandler.getScore();
        if (score.getPoints() > 0) {
            System.out.println("Enter your username to add your score to the leaderboard.");
            String username = input.next();
            if (username != null || username.length() > 0) {
                score.setUsername(username);
                leaderboard.addScore(score);
            }
        }

    }

    /**
     * EFFECT:      display the leaderboard with an order from highest to lowest score
     *              if there's no record yet tell the user to play a game
     */
    private void doLeaderboard() {
        List<Score> scores = leaderboard.getScores();
        if (scores.isEmpty()) {
            System.out.println("No records yet, go play a game!");
        }
        for (int i = 0; i < scores.size(); i++) {
            System.out.println("User: " + scores.get(i).getUsername() + "  Score: " + scores.get(i).getPoints());
        }
    }

}
