package ui;

import model.Leaderboard;
import model.Score;
import persistence.LeaderboardJsonReader;
import persistence.LeaderboardJsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class LeaderboardPanel extends JPanel {

    private static final String LEADERBOARD_STORE = "./data/Leaderboard.json";
    private static final int TOP_HEIGHT = 30;
    private static final int BOARD_HEIGHT = 470;
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 500;
    private LeaderboardJsonWriter jsonWriter;
    private LeaderboardJsonReader jsonReader;
    private Leaderboard leaderboard;
    private CardLayout cl;
    private JPanel flappyBird;
    private ArrayList<JLabel> labels;

    public LeaderboardPanel(Leaderboard leaderboard, CardLayout cl, JPanel flappyBird) {
        this.cl = cl;
        this.flappyBird = flappyBird;
        this.leaderboard = leaderboard;
        jsonWriter = new LeaderboardJsonWriter(LEADERBOARD_STORE);
        jsonReader = new LeaderboardJsonReader(LEADERBOARD_STORE);

        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        List<Score> scores = leaderboard.getScores();

        JPanel board = new JPanel();
        board.setPreferredSize(new Dimension(PANEL_WIDTH, BOARD_HEIGHT));
        board.setBackground(Color.cyan);
        board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));

        JButton back = new JButton("Back to the Menu");
        back.addActionListener(e -> cl.show(flappyBird, "menu"));
        JButton save = new JButton("Save");
        save.addActionListener(e -> saveLeaderboard());
        JButton load = new JButton("Load");
        load.addActionListener(e -> loadLeaderboard());

        JPanel top = new JPanel();
        top.setPreferredSize(new Dimension(PANEL_WIDTH, TOP_HEIGHT));
        top.add(back);
        top.add(save);
        top.add(load);
        top.setBackground(Color.blue);

        for (int i = 0; i < max(scores.size(), 3); i++) {
            if (i == 0) {
                JLabel gold = new JLabel();
                gold.setBackground(new Color(255, 255, 0));
                gold.setOpaque(true);
                gold.setVerticalAlignment(JLabel.CENTER);
                gold.setHorizontalAlignment(JLabel.CENTER);
                gold.setAlignmentX(Component.CENTER_ALIGNMENT);
                if (i < scores.size()) {
                    gold.setText("Gold: " + scores.get(i).getUsername() + "    " + scores.get(i).getPoints());
                } else {
                    gold.setText("Empty");
                }
                board.add(gold);
            } else if (i == 1) {
                JLabel silver = new JLabel();
                silver.setBackground(new Color(222, 222, 222));
                silver.setOpaque(true);
                silver.setVerticalAlignment(JLabel.CENTER);
                silver.setHorizontalAlignment(JLabel.CENTER);
                silver.setAlignmentX(Component.CENTER_ALIGNMENT);
                if (i < scores.size()) {
                    silver.setText("Silver: " + scores.get(i).getUsername() + "    " + scores.get(i).getPoints());
                } else {
                    silver.setText("Empty");
                }
                board.add(silver);
            } else if (i == 2) {
                JLabel bronze = new JLabel();
                bronze.setBackground(new Color(255, 111, 0));
                bronze.setOpaque(true);
                bronze.setVerticalAlignment(JLabel.CENTER);
                bronze.setHorizontalAlignment(JLabel.CENTER);
                bronze.setAlignmentX(Component.CENTER_ALIGNMENT);
                if (i < scores.size()) {
                    bronze.setText("Bronze: " + scores.get(i).getUsername() + "    " + scores.get(i).getPoints());
                } else {
                    bronze.setText("Empty");
                }
                board.add(bronze);
            } else {
                board.add(new JLabel(scores.get(i).getUsername() + "    " + scores.get(i).getPoints()));
            }
        }

        add(top);
        add(Box.createVerticalStrut(0));
        add(board);
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

//        if (scores.isEmpty()) {
//        System.out.println("\nNo records yet, go play a game!");
//    }
//        System.out.println();
//        for (int i = 0; i < scores.size(); i++) {
//        System.out.println("User: " + scores.get(i).getUsername() + "  Score: " + scores.get(i).getPoints());
//    }
}
