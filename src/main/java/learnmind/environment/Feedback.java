package learnmind.environment;

import learnmind.state.Row;
import learnmind.state.State;

public class Feedback {

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
     * @param count Max colors
     */
    public Feedback(final State state, final Row last, final int count) {
        this.state = state;
        this.last = last;
        this.reward = Feedback.reward(state, last);
        this.finished = Feedback.finished(state, last, count);
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
     * @param state Game state before the action
     * @param last Last played action with its result
     * @param count Max colors
     * @return true if the game is finished
     */
    private static boolean finished(final State state, final Row last, final int count) {
        return state.rows().size() == count+1
            || last.result().blacks() == 4;
    }

    /**
     * Calculates the reward.
     * @param state State before the action
     * @param last Last played action with its result
     * @return Reward value
     */
    /*private static int reward(final State state, final Row last) {
        List<Row> rows = state.rows();
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
    }*/


    /**
     * Calculates the reward.
     * @param state State before the action
     * @param last Last played action with its result
     * @return Reward value
     */
    private static int reward(final State state, final Row last) {
        int result = -1;
        if (last.result().blacks() == 4) {
            result = 10;
        }
        return result;
    }
}
