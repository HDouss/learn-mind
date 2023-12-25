package learnmind.learning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import learnmind.state.Code;
import learnmind.state.State;

/**
 * Returns are association between a pair of (State, Action) and the list of returns got during
 * the learning process.
 * @author hdouss
 *
 */
public class Returns {

    /**
     * Set of outcomes.
     */
    final Map<Pair<State, Code>, Pair<List<Integer>, Double>> outcomes;

    /**
     * Default constructor with empty map.
     */
    public Returns() {
        this(new HashMap<>());
    }

    /**
     * Constructor with starting outcomes.
     * @param results Starting outcomes
     */
    public Returns(final Map<Pair<State, Code>, Pair<List<Integer>, Double>> results) {
        this.outcomes = results;
    }

    /**
     * Adds a reward to the state-code pair
     * @param state State
     * @param code Code
     * @param reward Reward
     */
    public void add(final State state, final Code code, final Integer reward) {
        final Pair<State, Code> pair = new Pair<>(state, code);
        Pair<List<Integer>, Double> results = this.outcomes.get(pair);
        Pair<List<Integer>, Double> newresults;
        if (results == null) {
            results = new Pair<>(new ArrayList<>(), 0.);
        }
        final List<Integer> allrewards = results.getKey();
        allrewards.add(reward);
        double average = results.getValue() + (reward - results.getValue()) / allrewards.size();
        newresults = new Pair<>(allrewards, average);
        this.outcomes.put(pair, newresults);
    }

}
