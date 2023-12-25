package learnmind.learning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import learnmind.state.Code;
import learnmind.state.RandomCode;
import learnmind.state.State;

/**
 * Policy implementation that holds an association between stated and codes (actions).
 * An empty action for a state builds a random code for this state.
 * It also holds the list of Returns which are associations between a pair of (State, Action)
 * and the list of returns got during the learning process.
 * @author hdouss
 *
 */
public class Policy {

    /**
     * Set of outcomes.
     */
    final Map<Pair<State, Code>, Pair<List<Integer>, Double>> outcomes;

    /**
     * Best code per state.
     */
    final Map<State, Code> best;

    /**
     * Colors count used in the game
     */
    private final int count;

    /**
     * Default constructor with empty map.
     */
    public Policy(final int cnt) {
        this(new HashMap<>(), cnt);
    }

    /**
     * Constructor with starting outcomes.
     * @param results Starting outcomes
     * @param cnt Colors count used in the game
     */
    public Policy(final Map<Pair<State, Code>, Pair<List<Integer>, Double>> results,
        final int cnt) {
        this.outcomes = results;
        this.best = Policy.best(results);
        this.count = cnt;
    }

    /**
     * Adds a reward to the state-code pair
     * @param state State
     * @param code Code
     * @param reward Reward
     * @return Best code associated to a state
     */
    public Code add(final State state, final Code code, final Integer reward) {
        final Pair<State, Code> pair = new Pair<>(state, code);
        Pair<List<Integer>, Double> results = this.outcomes.get(pair);
        Pair<List<Integer>, Double> newresults;
        if (results == null) {
            results = new Pair<>(new ArrayList<>(), 0.);
        }
        final List<Integer> allrewards = results.getKey();
        allrewards.add(reward);
        final int size = allrewards.size();
        final Double sofar = results.getValue();
        final double average = size == 1 ? reward : sofar + (reward - sofar) / size;
        newresults = new Pair<>(allrewards, average);
        this.outcomes.put(pair, newresults);
        Code current = this.best.get(state);
        if (current == null) {
            this.best.put(state, code);
        } else {
            if (average > this.outcomes.get(new Pair<>(state, current)).getValue()) {
                this.best.put(state, code);
            }
        }
        return this.best.get(state);
    }

    /**
     * Retrieves the action associated to the state. Builds a new random code
     * for a state that does not have an association.
     * @param state State
     * @return Policy result for the given state
     */
    public Code get(final State state) {
        Code result = this.best.get(state);
        if (result == null) {
            result = new RandomCode(this.count);
            this.best.put(state, result);
            this.outcomes.put(new Pair<>(state, result), new Pair<>(new ArrayList<>(), 0.));
        }
        return result;
    }

    /**
     * Calculates the best action for each state as defined by the results.
     * @param results Map of results
     * @return Map associating a state to a code
     */
    private static Map<State, Code> best(
        Map<Pair<State, Code>, Pair<List<Integer>, Double>> results) {
        Map<State, Code> result = new HashMap<>();
        for (Pair<State, Code> pair : results.keySet()) {
            final State state = pair.getKey();
            Code current = result.get(state);
            if (current == null) {
                result.put(state, pair.getValue());
            } else {
                Double score = results.get(pair).getValue();
                if (score > results.get(new Pair<>(state, current)).getValue()) {
                    result.put(state, pair.getValue());
                }
            }
        }
        return result;
    }

}
