package learnmind.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import learnmind.state.Code;
import learnmind.state.RandomCode;
import learnmind.state.Result;
import learnmind.state.Row;
import learnmind.state.State;

/**
 * Environment class.
 * @author hdouss
 *
 */
public class Environment {

    /**
     * Random generator.
     */
    private static final Random random = new Random(6001);

    /**
     * List of rows representing the board.
     */
    private final List<Row> rows;

    /**
     * Code to be broken.
     */
    private final Code code;

    /**
     * Colors count used.
     */
    private final int count;

    /**
     * Current state of the environment.
     */
    private State current;

    /**
     * Environment constructor. Builds a new empty board.
     * @param colors Colors count used in the game
     */
    public Environment(final int colors) {
        this.rows = new ArrayList<Row>();
        this.current = new State(this.rows);
        this.code = new RandomCode(colors);
        this.count = colors;
    }

    /**
     * Generates a random state generated as a non final state limited to a max of 5 rows.
     * A random state is a real state which means that it has a real feedback against a real
     * code to be broken.
     */
    public void randomState() {
        int rows = Environment.random.nextInt(this.count - this.rows.size());
        if (rows != 0) {
            Feedback feedback = null;
            for (int idx = 0; idx < rows; ++idx) {
                RandomCode guess = new RandomCode(this.count, new State(this.rows));
                feedback = this.action(guess);
                if (feedback.finished()) {
                    this.rows.remove(feedback.last());
                    break;
                }
            }
        }
        this.current = new State(this.rows);
    }

    /**
     * Retrieves the current state.
     * @return Current environment state
     */
    public State current() {
        return this.current;
    }

    /**
     * Takes an action, evaluates it and returns the feedback.
     * @param guess Action took by the player
     * @return Action feedback
     */
    public Feedback action(final Code guess) {
        final Row last = new Row(guess, new Result(this.code, guess));
        final Feedback feedback = new Feedback(this.current, last, this.count);
        this.rows.add(last);
        this.current = new State(this.rows);
        return feedback;
    }
}
