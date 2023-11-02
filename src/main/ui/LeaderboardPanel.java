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
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 500;
    private LeaderboardJsonWriter jsonWriter;
    private LeaderboardJsonReader jsonReader;
    private Leaderboard leaderboard;
    private CardLayout cl;
    private JPanel flappyBird;
    private ArrayList<JLabel> labels;
    private JPanel board;
    private JPanel topBar;
    private JScrollPane scrollPane;

    public LeaderboardPanel(Leaderboard leaderboard, CardLayout cl, JPanel flappyBird) {
        this.cl = cl;
        this.flappyBird = flappyBird;
        this.leaderboard = leaderboard;
        this.labels = new ArrayList<>();
        jsonWriter = new LeaderboardJsonWriter(LEADERBOARD_STORE);
        jsonReader = new LeaderboardJsonReader(LEADERBOARD_STORE);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        makeBoard();
        updateBoard();

        makeTopBar();

        add(topBar);
        add(Box.createVerticalStrut(0));

        add(scrollPane);
    }

    private void makeTopBar() {
        JButton back = new JButton("Back to the Menu");
        back.addActionListener(e -> cl.show(flappyBird, "menu"));
        JButton save = new JButton("Save");
        save.addActionListener(e -> saveLeaderboard());
        JButton load = new JButton("Load");
        load.addActionListener(e -> loadLeaderboard());

        topBar = new JPanel();
        topBar.setPreferredSize(new Dimension(PANEL_WIDTH, TOP_HEIGHT));
        topBar.add(back);
        topBar.add(save);
        topBar.add(load);
        topBar.setBackground(Color.gray);
    }

    private void makeBoard() {
        board = new JPanel();
        scrollPane = new JScrollPane(board);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        board.setBackground(Color.cyan);
        board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));
    }

    private void updateBoard() {
        board.removeAll();
        board.add(Box.createVerticalStrut(20));
        updateScores();
        for (int i = 0; i < labels.size(); i++) {
            board.add(labels.get(i));
            board.add(Box.createVerticalStrut(5));
        }
        board.revalidate();
        board.repaint();
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private void updateScores() {
        labels.clear();
        List<Score> scores = leaderboard.getScores();
        for (int i = 0; i < max(scores.size(), 3); i++) {
            labels.add(new JLabel());
            if (i >= scores.size() && (i == 0 || i == 1 || i == 2)) {
                labels.get(i).setText("Vacant");
            } else {
                labels.get(i).setText(String.format("%-14s %-3s",
                        scores.get(i).getUsername(), scores.get(i).getPoints()));
            }
            labels.get(i).setBackground(new Color(255, 255, 255));
            labels.get(i).setMaximumSize(new Dimension(100, 30));
            labels.get(i).setMinimumSize(new Dimension(100, 30));
            labels.get(i).setPreferredSize(new Dimension(100, 30));
            labels.get(i).setBorder(BorderFactory.createLineBorder(Color.blue, 2));
            labels.get(i).setOpaque(true);
            labels.get(i).setVerticalAlignment(JLabel.CENTER);
            labels.get(i).setHorizontalAlignment(JLabel.CENTER);
            labels.get(i).setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        board.setPreferredSize(new Dimension(PANEL_WIDTH, max(PANEL_HEIGHT - TOP_HEIGHT, 40 * labels.size())));
        renderTopThree();
    }

    private void renderTopThree() {
        JLabel gold = labels.get(0);
        gold.setBackground(new Color(255, 234, 0));

        JLabel silver = labels.get(1);
        silver.setBackground(new Color(213, 213, 213));

        JLabel bronze = labels.get(2);
        bronze.setBackground(new Color(208, 93, 0));
    }

    // MODIFIES: this
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
            leaderboard.getScores().clear();
            Leaderboard loaded = jsonReader.read();
            for (Score score : loaded.getScores()) {
                leaderboard.addScore(score);
            }
            updateBoard();
            System.out.println("Loaded saved leaderboard from " + LEADERBOARD_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + LEADERBOARD_STORE);
        }
    }
}
