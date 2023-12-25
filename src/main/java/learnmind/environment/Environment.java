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
     * List of rows representing the board.
     */
    private final List<Row> rows;

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
        this.rows = new ArrayList<Row>();
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
        boolean generated = rows == 0;
        while (!generated) {
            Feedback feedback = null;
            for (int idx = 0; idx < rows; ++idx) {
                feedback = this.action(new RandomCode(this.count));
                if (feedback.finished()) {
                    break;
                }
            }
            if (!feedback.finished()) {
                generated = true;
            }
        }
        return new State(this.rows);
    }

    /**
     * Takes an action, evaluates it and returns the feedback.
     * @param guess Action took by the player
     * @return Action feedback
     */
    public Feedback action(final Code guess) {
        this.rows.add(new Row(guess, new Result(this.code, guess)));
        return new Feedback(new State(this.rows));
    }
}
