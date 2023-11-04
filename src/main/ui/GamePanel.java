package ui;

import model.FBGame;
import model.Leaderboard;
import model.Score;
import model.Tube;
import persistence.FBGameJsonReader;
import persistence.FBGameJsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The flappy bird game in the text terminal
 */
public class GamePanel extends JPanel implements MouseListener {

    private static final String GAME_STORE = "./data/FBGame.json";
    private static final int INTERVAL = 10;
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 500;
    private static final int GROUND_HEIGHT = 5;

    private FBGame game;
    private Leaderboard leaderboard;
    private CardLayout cl;
    private JPanel flappyBird;
    private JPanel gameOverPanel;
    private FBGameJsonWriter jsonWriter;
    private FBGameJsonReader jsonReader;
    private Timer timer;
    private JLabel score;

    public GamePanel(Leaderboard leaderboard, CardLayout cl, JPanel flappyBird) {
        this.jsonWriter = new FBGameJsonWriter(GAME_STORE);
        this.jsonReader = new FBGameJsonReader(GAME_STORE);
        this.leaderboard = leaderboard;
        this.cl = cl;
        this.flappyBird = flappyBird;
        this.game = new FBGame();
        makeGamerOverPanel();
        flappyBird.add(gameOverPanel, "over");

        addKeyListener(new KeyHandler());
        addMouseListener(this);

        score = new JLabel(String.valueOf(game.getScore().getPoints()));
        score.setForeground(Color.white);
        score.setOpaque(false);
        score.setHorizontalAlignment(SwingConstants.CENTER);
        score.setFont(new Font("MV Boli", Font.PLAIN, 50));

        add(score, BorderLayout.NORTH);

        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(new Color(13, 101, 175, 255));

        addTimer();
    }

    /**
     * From SnakeConsole with changes
     * MODIFIES:    this
     * EFFECT:      Begins the game and method does not leave execution
     * until game is complete.
     */
    public void start() {
        cl.show(flappyBird, "game");

        setFocusable(true);
        requestFocusInWindow();

        timer.start();
    }

    /**
     * MODIFIES:    this
     * EFFECT:      resume the game where the user last saved
     */
    public void resume() {
        loadGame();
        start();
    }

    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        timer = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (game.isEnded()) {
                    cl.show(flappyBird, "over");
                    timer.stop();
                }
                game.update();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
        updateScore();
    }

    private void makeGamerOverPanel() {
        gameOverPanel = new JPanel();
        JTextField textField = new JTextField("Enter your username here");
        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                JTextField source = (JTextField)e.getComponent();
                source.setText("");
                source.removeFocusListener(this);
            }
        });
        textField.setPreferredSize(new Dimension(200,30));
        JButton restart = new JButton("restart");
        JButton add = new JButton("Add to Leaderboard");
        JButton back = new JButton("Back to the Menu");
        add.addActionListener((ActionEvent e) -> {
            String username = textField.getText();
            leaderboard.addScore(new Score(username, game.getScore().getPoints()));
        });
        restart.addActionListener((ActionEvent e) -> {
            game = new FBGame();
            start();
        });
        back.addActionListener((ActionEvent e) -> {
            cl.show(flappyBird, "menu");
        });
        gameOverPanel.add(textField);
        gameOverPanel.add(add);
        gameOverPanel.add(restart);
        gameOverPanel.add(back);
    }

    private void updateScore() {
        score.setText(String.valueOf(game.getScore().getPoints()));
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
        drawTubes(g);
        drawBird(g);
        drawGround(g);
    }


    /**
     * EFFECT:      draw the tubes on the terminal
     */
    private void drawTubes(Graphics g) {
        for (Tube tube : game.getTubes()) {
            Color savedCol = g.getColor();
            g.setColor(tube.COLOR);

            g.fillRect(tube.getX(), 0, tube.WIDTH, tube.getSpaceStart());
            g.fillRect(tube.getX(), tube.getSpaceEnd(), tube.WIDTH, PANEL_HEIGHT);

            g.setColor(savedCol);
        }
    }

    /**
     * EFFECT:      draw the bird on the terminal
     */
    private void drawBird(Graphics g) {
        Color savedCol = g.getColor();
        g.setColor(game.getBird().COLOR);
        g.fillOval(game.getBird().getX(), game.getBird().getY(), game.getBird().SIZE_X, game.getBird().SIZE_Y);
        g.setColor(savedCol);
    }

    /**
     * EFFECT:      draw the ground on the terminal
     */
    private void drawGround(Graphics g) {
        Color savedCol = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(0, PANEL_HEIGHT - GROUND_HEIGHT, PANEL_WIDTH, GROUND_HEIGHT);
        g.setColor(savedCol);
    }

    public Score getScore() {
        return game.getScore();
    }

    /*
     * A key handler to respond to key events
     * From SpaceInvaders
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            processKey(e.getKeyCode());
        }
    }

    //process the keystroke input by user
    public void processKey(int keyCode) {
        if (keyCode == KeyEvent.VK_SPACE) {
            game.getBird().flap();
        } else if (keyCode == KeyEvent.VK_S) {
            saveGame();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        game.getBird().flap();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
