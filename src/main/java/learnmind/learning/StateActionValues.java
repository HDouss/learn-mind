package learnmind.learning;

import javafx.util.Pair;
import learnmind.state.Code;
import learnmind.state.State;

/**
 * State action values as a map.
 * @author hdouss
 *
 */
public class StateActionValues extends EpsilonGreedyPolicy {

    /**
     * Initial value for non terminal states.
     */
    protected static final Double INITIAL_VALUE = -0.5;

    /**
     * Constructor.
     * @param cnt Colours count
     * @param eps Epsilon parameter
     */
    public StateActionValues(int cnt, double eps) {
        super(cnt, eps);
    }

    @Override
    public void update(final Pair<State, Code> before, final Pair<State, Code> after,
        final Integer reward, final double rate) {
        Pair<Integer, Double> outcome = this.outcomes.get(before);
        Double value = null;
        if (outcome == null) {
            value = StateActionValues.INITIAL_VALUE;
            this.outcomes.put(before, new Pair<>(0, value));
            this.best.put(before.getKey(), before.getValue());
        } else {
            value = outcome.getValue();
        }
        Double newvalue = this.next(after);
        Double result = value + rate * (reward + newvalue - value);
        this.outcomes.put(before, new Pair<>(0, result));
        State state = before.getKey();
        Code code = before.getValue();
        Code current = this.best.get(state);
        if (current == null) {
            this.best.put(state, code);
        } else {
            if (result > this.outcomes.get(new Pair<>(state, current)).getValue()) {
                this.best.put(state, code);
            }
        }
    }

    /**
     * Gets the next value to update the current state/action with; given the next state/action.
     * @param pair Next state/action pair.
     * @return Value to update value with
     */
    protected Double next(final Pair<State, Code> pair) {
        final Pair<Integer, Double> outcome = this.outcomes.get(pair);
        Double newvalue = null;
        if (outcome == null) {
            newvalue = pair.getValue() == null ? 0 : StateActionValues.INITIAL_VALUE;
            this.outcomes.put(pair, new Pair<>(0, newvalue));
            this.best.put(pair.getKey(), pair.getValue());
        } else {
            newvalue = outcome.getValue();
        }
        return newvalue;
    }
}