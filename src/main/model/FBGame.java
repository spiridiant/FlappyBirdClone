package model;


import java.util.ArrayList;
import java.util.List;

public class FBGame {

    public static final int TICKS_PER_SECOND = 10;
    private Bird bird;
    private List<Tube> tubes;
    private Score score;
    private boolean ended = false;
    private int maxX;
    private int maxY;

    public FBGame(int maxX, int maxY) {
        score = new Score();
        bird = new Bird(50,0);
        tubes = new ArrayList<Tube>();
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public void tick() {

    }

    /**
     * Returns whether a given position is in bounds
     * and not already occupied
     */
    public boolean isValidPosition(Position pos) {
        return  !hasFallen(pos) &&
                !hasCollided(pos);
    }

    private boolean hasCollided(Position pos) {
        return false;
    }

    private boolean hasFallen(Position pos) {
        return pos.getY() > maxY;
    }

    public int getScore() {
        return score.getScore();
    }

    public boolean isEnded() {
        return ended;
    }

    public List<Tube> getTubes() {
        return tubes;
    }

    public Bird getBird() {
        return bird;
    }
}
