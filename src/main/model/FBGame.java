package model;


import java.util.*;

public class FBGame {

    public static final int TICKS_PER_SECOND = 5;
    private Bird bird;
    private Deque<Tube> tubes;
    private Score score;
    private boolean ended = false;
    private int maxX;
    private int maxY;
    private Random random;
    private List<Position> ground;

    public FBGame(int maxX, int maxY) {
        score = new Score();
        bird = new Bird(50, 0);
        tubes = new ArrayDeque<>();
        random = new Random();
        ground = new ArrayList<>();
        this.maxX = maxX;
        this.maxY = maxY;
        for (int i = 0; i <= maxX; i++) {
            ground.add(new Position(i, maxY));
        }
    }

    public void tick() {
        if (!tubes.isEmpty() && tubes.getFirst().getX() < 0) {
            tubes.removeFirst();
        }
        if (tubes.isEmpty() || tubes.getLast().getX() < maxX / 2) {
            genNewTube();
        }
        for (Tube t : tubes) {
            t.moveLeft();
        }
    }

    private void genNewTube() {
        int spaceLength = maxY / 5 * 2;
        int spaceStart = random.nextInt(maxY - spaceLength);
        Tube newTube = new Tube(maxX, spaceStart, spaceStart + spaceLength, maxY);
        tubes.add(newTube);
    }

    /**
     * Returns whether a given position is in bounds
     * and not already occupied
     */
    public boolean isValidPosition(Position pos) {
        return !hasFallen(pos) &&
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

    public Deque<Tube> getTubes() {
        return tubes;
    }

    public Bird getBird() {
        return bird;
    }

    public List<Position> getGround() {
        return ground;
    }
}
