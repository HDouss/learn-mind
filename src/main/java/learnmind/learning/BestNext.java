package learnmind.learning;

import javafx.util.Pair;
import learnmind.state.Code;
import learnmind.state.State;

/**
 * State action values as a map.
 * @author hdouss
 *
 */
public class BestNext extends StateActionValues {

    /**
     * Constructor.
     * @param cnt Colours count
     * @param eps Epsilon parameter
     */
    public BestNext(int cnt, double eps) {
        super(cnt, eps);
    }

    @Override
    protected Double next(final Pair<State, Code> pair) {
        if (pair.getValue() == null) {
            return 0.;
        }
        return this.outcomes.get(pair.getKey()).peek().element().value;
    }
}