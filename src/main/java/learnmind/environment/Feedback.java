package learnmind.environment;

import java.util.List;
import learnmind.state.Row;
import learnmind.state.State;

public class Feedback {

    /**
     * Maximum number of guesses allowed.
     */
    private static final int MAX_GUESSES = 10;

    /**
     * Game state.
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
     * Feedback constructor of a state.
     * @param state State for which the feedback is built
     */
    public Feedback(final State state) {
        this.state = state;
        this.reward = Feedback.reward(state);
        this.finished = Feedback.finished(state);
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
     * Accessor for the state.
     * @return State
     */
    public State state() {
        return this.state;
    }

    /**
     * Whether the game is finished.
     * @return true if the game is finished
     */
    private static boolean finished(final State state) {
        List<Row> rows = state.rows();
        int count = rows.size();
        return count == Feedback.MAX_GUESSES
            || rows.get(count - 1).result().blacks() == 4;
    }

    /**
     * Calculates the reward.
     * @param state State to evaluate
     * @return Reward value
     */
    private static int reward(final State state) {
        List<Row> rows = state.rows();
        int count = rows.size();
        int result = 0;
        if (rows.get(count - 1).result().blacks() == 4) {
            result = Feedback.MAX_GUESSES - count + 1;
        } else {
            if (count == Feedback.MAX_GUESSES) {
                result = -1;
            }
        }
        return result;
    }

}
