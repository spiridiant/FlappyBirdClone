package ui;

import model.Leaderboard;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The main class that starts the program
 */
public class FlappyBird extends JFrame {

    private JPanel flappyBird;
    private Leaderboard leaderboard;

    public FlappyBird() {
        super("Flappy Bird");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setResizable(false);

        flappyBird = new JPanel();
        CardLayout cl = new CardLayout();
        flappyBird.setLayout(cl);
        leaderboard = new Leaderboard();

        StartMenuPanel startMenuPanel = new StartMenuPanel(leaderboard, cl, flappyBird);
        flappyBird.add(startMenuPanel, "menu");

        cl.show(flappyBird, "menu");

        add(flappyBird);
        
        pack();
        centreOnScreen();
        setVisible(true);
    }



    // Centres frame on desktop, from SpaceInvader
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    /**
     * EFFECT:      Start the program
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        new FlappyBird();
    }
}
