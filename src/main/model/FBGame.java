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

    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;

    private Bird bird;
    private Deque<Tube> tubes;
    private Score score;
    private boolean ended = false;
    private Random random;
    private Set<Tube> pastTubes;

    public FBGame() {
        score = new Score();
        bird = new Bird(WIDTH / 2, HEIGHT / 2);
        tubes = new ArrayDeque<>();
        random = new Random();
        pastTubes = new HashSet<>();
        EventLog.getInstance().logEvent(new Event("Game started."));
    }

    public FBGame(Bird bird, Score score, Deque<Tube> tubes) {
        this.score = score;
        this.bird = bird;
        this.tubes = tubes;
        random = new Random();
        pastTubes = new HashSet<>();
    }

    /**
     * MODIFIES:    this
     * EFFECT:      tick the game,
     * change the status of the bird base on user input
     * end the game base on position of the bird
     */
    public void update() {
        handleTubes();

        if (!this.bird.isFlapping()) {
            bird.falls();
        }
        if (!isValidPosition(bird.getX(), bird.getY())) {
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
        if (!tubes.isEmpty() && tubes.getFirst().getX() + tubes.getFirst().WIDTH < 0) {
            pastTubes.remove(tubes.getFirst());
            tubes.removeFirst();
        }
        if (tubes.isEmpty() || tubes.getLast().getX() < WIDTH / 2) {
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
        int spaceLength = HEIGHT / 5 * 2;
        int spaceStart = random.nextInt(HEIGHT - spaceLength);
        Tube newTube = new Tube(WIDTH, spaceStart, spaceStart + spaceLength, HEIGHT);
        tubes.add(newTube);
    }

    /**
     * REQUIRES:    pos is not null
     * EFFECT:      Returns whether a given position is above ground
     * and has not collided with a tube
     */
    public boolean isValidPosition(int x, int y) {
        return !hasFallen(y) && !hasCollided(x, y);
    }

    /**
     * REQUIRES:    pos is not null
     * MODIFIES:    this
     * EFFECT:      check if a given position overlap with a tube
     * if it's passing a tube but didn't collide with it,
     * increment the score
     */
    private boolean hasCollided(int x, int y) {
        for (Tube tube : tubes) {
            if (x + bird.SIZE_X >= tube.getX() && x <= tube.getX() + tube.WIDTH) {
                if (y >= tube.getSpaceStart() && y + bird.SIZE_Y < tube.getSpaceEnd()) {
                    if (!pastTubes.contains(tube)) {
                        score.incrementScore();
                        pastTubes.add(tube);
                    }
                    return false;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * REQUIRES:    pos is not null
     * EFFECT:      check if a given position has fall on the ground
     */
    private boolean hasFallen(int y) {
        return y >= HEIGHT;
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

    /**
     * MODIFIES:    this
     * Effect:      convert the instance of this object to json format
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("score", score.toJson());
        json.put("bird", bird.toJson());
        json.put("tubes", tubesToJson());
        EventLog.getInstance().logEvent(new Event("Game saved."));
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
