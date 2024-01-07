package learnmind.environment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
     * Set of rows representing the board.
     */
    private final Set<Row> rows;

    /**
     * Code to be broken.
     */
    private final Code code;

    /**
     * Code to be broken.
     */
    private final int count;

    /**
     * Environment constructor. Builds a new empty board.
     * @param colors Colors count used in the game
     */
    public Environment(final int colors) {
        this.rows = new HashSet<Row>();
        this.code = new RandomCode(colors);
        this.count = colors;
    }

    /**
     * Generates a random state generated as a non final state limited to a max of 5 rows.
     * A random state is a real state which means that it has a real feedback against a real
     * code to be broken.
     * @return A random state
     */
    public State randomState() {
        int rows = new Random().nextInt(6 - this.rows.size());
        if (rows != 0) {
            Feedback feedback = null;
            List<Code> played = new ArrayList<Code>(rows);
            for (int idx = 0; idx < rows; ++idx) {
                RandomCode guess = new RandomCode(this.count);
                while (played.contains(guess)) {
                    guess = new RandomCode(this.count);
                }
                feedback = this.action(guess);
                played.add(guess);
                if (feedback.finished()) {
                    this.rows.remove(feedback.last());
                    break;
                }
            }
        }
        return new State(this.rows);
    }

    /**
     * Retrieves the current state.
     * @return Current environment state
     */
    public State current() {
        return new State(this.rows);
    }

    /**
     * Takes an action, evaluates it and returns the feedback.
     * @param guess Action took by the player
     * @return Action feedback
     */
    public Feedback action(final Code guess) {
        final Row last = new Row(guess, new Result(this.code, guess));
        final Feedback feedback = new Feedback(new State(this.rows), last);
        this.rows.add(last);
        return feedback;
    }
}
