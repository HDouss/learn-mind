package learnmind.environment;

import java.util.ArrayList;
import java.util.List;
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
     * Environment constructor. Builds a new empty board.
     */
    public Environment(final int colors) {
        this.rows = new ArrayList<Row>();
        this.code = new RandomCode(colors);
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
