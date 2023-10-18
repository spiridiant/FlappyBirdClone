package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.*;

/**
 * The flappy bird game board
 * contain the bird, the tube and the ground
 */
public class FBGame implements Writable {

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
        this.maxX = maxX;
        this.maxY = maxY;
        score = new Score();
        bird = new Bird(maxX / 2, maxY / 2);
        tubes = new ArrayDeque<>();
        random = new Random();
        ground = new ArrayList<>();
        for (int i = 0; i <= maxX; i++) {
            ground.add(new Position(i, maxY));
        }
    }

    public FBGame(int maxX, int maxY, Bird bird, Score score, Deque<Tube> tubes) {
        this.maxX = maxX;
        this.maxY = maxY;
        this.score = score;
        this.bird = bird;
        this.tubes = tubes;
        random = new Random();
        ground = new ArrayList<>();
        for (int i = 0; i <= maxX; i++) {
            ground.add(new Position(i, maxY));
        }
    }

    /**
     * MODIFIES:    this
     * EFFECT:      tick the game,
     * change the status of the bird base on user input
     * end the game base on position of the bird
     */
    public void tick() {
        handleTubes();

        if (!this.bird.isFlapping()) {
            bird.falls();
        }
        if (!isValidPosition(this.bird.getPosition())) {
            ended = true;
        }
        bird.setFlapping(false);
    }


    /**
     * MODIFIES:    this
     * EFFECT:      delete tube that are out of screen
     * generate new tube when the last tube has moved to the middle of the screen
     * move all tubes to the left
     */
    private void handleTubes() {
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

    /**
     * MODIFIES:    this
     * EFFECT:      add a new tube with specified space between two parts of the body
     */
    private void genNewTube() {
        int spaceLength = maxY / 5 * 2;
        int spaceStart = random.nextInt(maxY - spaceLength);
        Tube newTube = new Tube(maxX, spaceStart, spaceStart + spaceLength, maxY);
        tubes.add(newTube);
    }

    /**
     * REQUIRES:    pos is not null
     * EFFECT:      Returns whether a given position is above ground
     * and has not collided with a tube
     */
    public boolean isValidPosition(Position pos) {
        return !hasFallen(pos) && !hasCollided(pos);
    }

    /**
     * REQUIRES:    pos is not null
     * MODIFIES:    this
     * EFFECT:      check if a given position overlap with a tube
     * if it's passing a tube but didn't collide with it,
     * increment the score
     */
    private boolean hasCollided(Position pos) {
        for (Tube tube : tubes) {
            if (tube.getX() == bird.getX()) {
                if (pos.getY() < 0 || tube.getBody().contains(pos)) {
                    return true;
                } else {
                    score.incrementScore();
                }
            }
        }
        return false;
    }

    /**
     * REQUIRES:    pos is not null
     * EFFECT:      check if a given position has fall on the ground
     */
    private boolean hasFallen(Position pos) {
        return pos.getY() >= maxY;
    }

    public Score getScore() {
        return score;
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

    /**
     * MODIFIES:    this
     * Effect:      convert the instance of this object to json format
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("maxX", maxX);
        json.put("maxY", maxY);
        json.put("score", score.toJson());
        json.put("bird", bird.toJson());
        json.put("tubes", tubesToJson());
        return json;
    }

    /**
     * Effect:      convert the tubes to json format
     */
    private JSONArray tubesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Tube tube : tubes) {
            jsonArray.put(tube.toJson());
        }

        return jsonArray;
    }
}
