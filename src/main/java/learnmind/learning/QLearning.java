package learnmind.learning;

import javafx.util.Pair;
import learnmind.state.Code;
import learnmind.state.State;

/**
 * State action values as a map.
 * @author hdouss
 *
 */
public class QLearning extends StateActionValues {

    /**
     * Constructor.
     * @param cnt Colours count
     * @param eps Epsilon parameter
     */
    public QLearning(int cnt, double eps) {
        super(cnt, eps);
    }

    @Override
    protected Double next(final Pair<State, Code> pair) {
        final State nextState = pair.getKey();
        final Pair<State, Code> key = new Pair<>(nextState, this.best(nextState));
        final Pair<Integer, Double> outcome = this.outcomes.get(key);
        Double newvalue = null;
        if (outcome == null) {
            newvalue = pair.getValue() == null ? 0 : StateActionValues.INITIAL_VALUE;
            this.outcomes.put(key, new Pair<>(0, newvalue));
            this.best.put(pair.getKey(), pair.getValue());
        } else {
            newvalue = outcome.getValue();
        }
        return newvalue;
    }
}