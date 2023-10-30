package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.*;
import persistence.FBGameJsonReader;
import persistence.FBGameJsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The flappy bird game in the text terminal
 */
public class GamePanel extends JPanel {

    private static final String GAME_STORE = "./data/FBGame.json";
    private static final int INTERVAL = 10;
    private FBGame game;
    private Screen screen;
    private WindowBasedTextGUI endGui;
    private FBGameJsonWriter jsonWriter;
    private FBGameJsonReader jsonReader;

    public GamePanel() {
        jsonWriter = new FBGameJsonWriter(GAME_STORE);
        jsonReader = new FBGameJsonReader(GAME_STORE);
    }

    /**
     * From SnakeConsole with changes
     * MODIFIES:    this
     * EFFECT:      Begins the game and method does not leave execution
     * until game is complete.
     */
    public void start() throws IOException, InterruptedException {
        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        TerminalSize terminalSize = screen.getTerminalSize();

        game = new FBGame(
                (terminalSize.getColumns() - 1) / 2,
                terminalSize.getRows() - 2
        );

        addTimer();
    }

    /**
     * MODIFIES:    this
     * EFFECT:      resume the game where the user last saved
     */
    public void resume() throws IOException, InterruptedException {
        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        loadGame();

        addTimer();
    }

    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.update();
                repaint();
            }
        });

        t.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

//        if (game.isOver()) {
//            gameOver(g);
//        }
    }

    /**
     * EFFECT:      save the game
     */
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved current game to " + GAME_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + GAME_STORE);
        }
    }

    /**
     * EFFECT:      load the saved game
     */
    private void loadGame() {
        try {
            game = jsonReader.read();
            System.out.println("Loaded saved leaderboard from " + GAME_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + GAME_STORE);
        }
    }


    /**
     * From SnakeConsole with changes
     * EFFECT:      Renders the current screen.
     * Draws the end screen if the game has ended, otherwise
     * draws the score, snake, and food.
     */
    private void drawGame(Graphics g) {
//        if (game.isEnded()) {
//            if (endGui == null) {
//                drawEndScreen();
//            }
//            return;
//        }

        drawScore(g);
        drawBird(g);
        drawTubes(g);
        drawGround(g);
    }


    /**
     * MODIFIES:    this
     * EFFECT:      Sets the birds flappy state user's keystroke
     */
    private void handleUserInput() throws IOException {
        KeyStroke stroke = screen.pollInput();
        if (stroke != null && stroke.getKeyType() == KeyType.Character && stroke.getCharacter() == ' ') {
            game.getBird().flap();
        }
        if (stroke != null && stroke.getKeyType() == KeyType.Character && stroke.getCharacter() == 's') {
            saveGame();
        }
    }


    /**
     * From SnakeConsole with changes
     * EFFECT:      draw the end screen when the game ends
     */
    private void drawEndScreen() {
        endGui = new MultiWindowTextGUI(screen);

        new MessageDialogBuilder()
                .setTitle("Game over!")
                .setText("You finished with a score of " + game.getScore().getPoints() + "!")
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(endGui);
    }

    /**
     * From SnakeConsole with changes
     * EFFECT:      draw the score panel in game
     */
    private void drawScore(Graphics g) {
        TextGraphics text = screen.newTextGraphics();
        text.setBackgroundColor(TextColor.ANSI.RED);
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(1, 0, "Score: ");

        text = screen.newTextGraphics();
        text.setBackgroundColor(TextColor.ANSI.RED);
        text.setForegroundColor(TextColor.ANSI.WHITE);
        text.putString(8, 0, String.valueOf(game.getScore().getPoints()));
    }

    /**
     * EFFECT:      draw the tubes on the terminal
     */
    private void drawTubes(Graphics g) {
        for (Tube tube : game.getTubes()) {
            for (Position pos : tube.getBody()) {
                drawPosition(pos, TextColor.ANSI.GREEN, '|', true);
            }
        }
    }

    /**
     * EFFECT:      draw the bird on the terminal
     */
    private void drawBird(Graphics g) {
        drawPosition(game.getBird().getPosition(), TextColor.ANSI.RED, '%', false);
    }

    /**
     * EFFECT:      draw the ground on the terminal
     */
    private void drawGround(Graphics g) {
        for (Position pos : game.getGround()) {
            drawPosition(pos, TextColor.ANSI.CYAN, '*', true);
        }
    }

    /**
     * From SnakeConsole with changes
     * EFFECT:      Draws a character in a given position on the terminal.
     * If wide, it will draw the character twice to make it appear wide.
     */
    private void drawPosition(Position pos, TextColor color, char c, boolean wide) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(pos.getX() * 2, pos.getY() + 1, String.valueOf(c));

        if (wide) {
            text.putString(pos.getX() * 2 + 1, pos.getY() + 1, String.valueOf(c));
        }
    }

    public Score getScore() {
        return game.getScore();
    }
}
