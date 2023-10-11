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

import java.io.IOException;

/**
 *      The flappy bird game in the text terminal
 */
public class FlappyBirdGame {
    private FBGame game;
    private Screen screen;
    private WindowBasedTextGUI endGui;

    /**
     * From SnakeConsole with changes
     * MODIFIES:    this
     * EFFECT:      Begins the game and method does not leave execution
     *              until game is complete.
     */
    public void start() throws IOException, InterruptedException {
        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        TerminalSize terminalSize = screen.getTerminalSize();

        game = new FBGame(
                (terminalSize.getColumns() - 1) / 2,
                terminalSize.getRows() - 2
        );

        beginTicks();
    }

    /**
     * From SnakeConsole with changes
     * MODIFIES: this
     * EFFECT:      Handles one cycle in the game by taking user input,
     *              ticking the game internally, and rendering the effects
     */
    private void tick() throws IOException {
        handleUserInput();

        game.tick();

        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();
        render();
        screen.refresh();

        screen.setCursorPosition(new TerminalPosition(screen.getTerminalSize().getColumns() - 1, 0));
    }

    /**
     * From SnakeConsole with changes
     * EFFECT:      Renders the current screen.
     *              Draws the end screen if the game has ended, otherwise
     *              draws the score, snake, and food.
     */
    private void render() {
        if (game.isEnded()) {
            if (endGui == null) {
                drawEndScreen();
            }

            return;
        }

        drawScore();
        drawBird();
        drawTubes();
        drawGround();
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
    }

    /**
     * From SnakeConsole
     * EFFECT:      Begins the game cycle. Ticks once every Game.TICKS_PER_SECOND until
     *              game has ended and the endGui has been exited.
     */
    private void beginTicks() throws IOException, InterruptedException {
        while (!game.isEnded() || endGui.getActiveWindow() != null) {
            tick();
            Thread.sleep(1000L / game.TICKS_PER_SECOND);
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
    private void drawScore() {
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
    private void drawTubes() {
        for (Tube tube : game.getTubes()) {
            for (Position pos : tube.getBody()) {
                drawPosition(pos, TextColor.ANSI.GREEN, '|', true);
            }
        }
    }

    /**
     * EFFECT:      draw the bird on the terminal
     */
    private void drawBird() {
        drawPosition(game.getBird().getPosition(), TextColor.ANSI.RED, '.', false);
    }

    /**
     * EFFECT:      draw the ground on the terminal
     */
    private void drawGround() {
        for (Position pos : game.getGround()) {
            drawPosition(pos, TextColor.ANSI.CYAN, '|', true);
        }
    }

    /**
     * From SnakeConsole with changes
     * EFFECT:      Draws a character in a given position on the terminal.
     *              If wide, it will draw the character twice to make it appear wide.
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
