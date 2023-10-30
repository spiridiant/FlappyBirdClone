package ui;

import model.*;
import persistence.LeaderboardJsonWriter;
import persistence.LeaderboardJsonReader;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.System.exit;

/**
 * The start menu in the console
 * take user commands and execute them
 */
public class StartMenuPanel extends JPanel {

    private static final String LEADERBOARD_STORE = "./data/Leaderboard.json";

    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 500;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 30;
    private JButton start;
    private JButton resume;
    private JButton view;
    private JButton quit;

    private Leaderboard leaderboard;
    private LeaderboardJsonWriter jsonWriter;
    private LeaderboardJsonReader jsonReader;
    private CardLayout cl;
    private JPanel flappyBird;

    public StartMenuPanel(CardLayout cl, JPanel flappyBird) {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(null);
        addButtons();

        this.cl = cl;
        this.flappyBird = flappyBird;

        leaderboard = new Leaderboard();
        jsonWriter = new LeaderboardJsonWriter(LEADERBOARD_STORE);
        jsonReader = new LeaderboardJsonReader(LEADERBOARD_STORE);
    }

    /**
     * EFFECT:      display all the option user has
     */
    private void addButtons() {
        start = new JButton("Start New Game");
        resume = new JButton("Resume Saved Game");
        view = new JButton("View Leaderboard");
        quit = new JButton("Quit");

        start.setBounds(200, 280, BUTTON_WIDTH, BUTTON_HEIGHT);
        resume.setBounds(200, 330, BUTTON_WIDTH, BUTTON_HEIGHT);
        view.setBounds(200, 380, BUTTON_WIDTH, BUTTON_HEIGHT);
        quit.setBounds(200, 430, BUTTON_WIDTH, BUTTON_HEIGHT);
        addCommands();
        this.add(start);
        this.add(resume);
        this.add(view);
        this.add(quit);
    }

    /**
     * MODIFIES:    this
     * EFFECT:      processes user command
     * if command not valid display error message
     */
    private void addCommands() {
        start.addActionListener(e -> {
            try {
                doGame(true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        resume.addActionListener(e -> {
            try {
                doGame(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });
        view.addActionListener(e -> printLeaderboard());
        quit.addActionListener(e -> exit(0));
    }

    /**
     * MODIFIES:    this
     * EFFECT:      instantiate a FlappyBirdGame object for a new game
     * when the game finished and user score is positive
     * ask for user's username to add the score to leaderboard
     */
    private void doGame(boolean newGame) throws IOException, InterruptedException {
        GamePanel gameHandler = new GamePanel();

        if (newGame) {
            gameHandler.start();
        } else {
            gameHandler.resume();
        }

        Score score = gameHandler.getScore();
        if (score.getPoints() > 0) {
            System.out.println("Enter your username to add your score to the leaderboard.");
            String username = " ";
            if (username != null || username.length() > 0) {
                score.setUsername(username);
                leaderboard.addScore(score);
            }
        }

    }
    /**
     * EFFECT:      display the leaderboard with an order from highest to lowest score
     * if there's no record yet tell the user to play a game
     */
    private void printLeaderboard() {
        cl.show(flappyBird, "leaderboard");
    }

    // EFFECTS: saves the leaderboard to file
    private void saveLeaderboard() {
        try {
            jsonWriter.open();
            jsonWriter.write(leaderboard);
            jsonWriter.close();
            System.out.println("Saved current leaderboard to " + LEADERBOARD_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + LEADERBOARD_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads leaderboard from file
    private void loadLeaderboard() {
        try {
            leaderboard = jsonReader.read();
            System.out.println("Loaded saved leaderboard from " + LEADERBOARD_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + LEADERBOARD_STORE);
        }
    }
}
