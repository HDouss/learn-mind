package learnmind.environment;

import java.util.Set;
import learnmind.state.Row;
import learnmind.state.State;

public class Feedback {

    /**
     * Maximum number of guesses allowed.
     */
    private static final int MAX_GUESSES = 10;

    /**
     * Game state before action.
     */
    private State state;

    /**
     * Reward.
     */
    private int reward;

    /**
     * Whether the game is finished.
     */
    private boolean finished;

    /**
     * Last played code with its result.
     */
    private Row last;

    /**
     * Feedback constructor of a state.
     * @param state State before the action was taken
     * @param last Last row after the action was played
     */
    public Feedback(final State state, final Row last) {
        this.state = state;
        this.last = last;
        this.reward = Feedback.reward(state, last);
        this.finished = Feedback.finished(state, last);
    }

    /**
     * Whether the game is finished.
     * @return true if the game is finished
     */
    public boolean finished() {
        return this.finished;
    }

    /**
     * Accessor for the reward.
     * @return Reward value
     */
    public int reward() {
        return this.reward;
    }

    /**
     * Accessor for the last row.
     * @return Last row last played code with its result
     */
    public Row last() {
        return this.last;
    }

    /**
     * Accessor for the state.
     * @return State
     */
    public State before() {
        return this.state;
    }

    /**
     * Whether the game is finished.
     * @param last Last played action with its result
     * @return true if the game is finished
     */
    private static boolean finished(final State state, Row last) {
        return state.rows().size() == Feedback.MAX_GUESSES - 1
            || last.result().blacks() == 4;
    }

    /**
     * Calculates the reward.
     * @param state State to evaluate
     * @param last Last played action with its result
     * @return Reward value
     */
    private static int reward(final State state, final Row last) {
        Set<Row> rows = state.rows();
        int count = rows.size();
        int result = 0;
        if (last.result().blacks() == 4) {
            result = Feedback.MAX_GUESSES - count;
        } else {
            if (count == Feedback.MAX_GUESSES - 1) {
                result = -1;
            }
        }
        return result;
    }

}
