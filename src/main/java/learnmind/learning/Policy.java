package learnmind.learning;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javafx.util.Pair;
import learnmind.state.Code;
import learnmind.state.RandomCode;
import learnmind.state.State;

/**
 * Policy implementation that holds an association between stated and codes (actions).
 * An empty action for a state builds a random code for this state that was not played before.
 * It also holds the list of Returns which are associations between a pair of (State, Action)
 * and the list of returns got during the learning process.
 * @author hdouss
 *
 */
public class Policy {

    /**
     * Set of outcomes.
     */
    final Map<Pair<State, Code>, Pair<Integer, Double>> outcomes;

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
     * @param cnt Colors count used in the game
     */
    public Policy(final int cnt) {
        this(new HashMap<>(), cnt);
    }

    /**
     * Policy constructor from a string representation.
     * @param str String representation of a policy
     */
    public Policy(final String str) {
        final String[] first = str.split(", outcomes=\\{");
        this.count = Integer.parseInt(first[0].split("=")[1]);
        final String outcomesAsString = first[1].substring(0, first[1].length() - 2);
        this.outcomes = new HashMap<>();
        final String[] map = outcomesAsString.split(";\\s");
        for (int idx = 0; idx < map.length; ++idx) {
            final String[] entry = map[idx].split("=");
            this.outcomes.put(
                new Pair<>(new State(entry[0]), new Code(entry[1])),
                new Pair<>(Integer.parseInt(entry[2]), Double.parseDouble(entry[3]))
            );
        }
        this.best = Policy.best(this.outcomes);
    }

    /**
     * Constructor with starting outcomes.
     * @param results Starting outcomes
     * @param cnt Colors count used in the game
     */
    public Policy(final Map<Pair<State, Code>, Pair<Integer, Double>> results,
        final int cnt) {
        this.outcomes = results;
        this.best = Policy.best(results);
        this.count = cnt;
    }

    /**
     * Accessor for count.
     * @return Colors count used in the game
     */
    public int count() {
        return this.count;
    }

    /**
     * Updates the value of a state/code pair given the following state/code pair and the received
     * reward.
     * @param before State/code pair to update
     * @param after State/code pair following the pair to update
     * @param reward current reward
     * @param rate Learning rate
     */
    public void update(final Pair<State, Code> before, final Pair<State, Code> after,
        Integer reward, final double rate) {
        Pair<Integer, Double> results = this.outcomes.get(before);
        if (results == null) {
            results = new Pair<>(0, 0.);
        }
        Integer rewardsCnt = results.getKey();
        rewardsCnt++;
        final Double sofar = results.getValue();
        final double average = rewardsCnt == 1 ? reward : sofar + (reward - sofar) / rewardsCnt;
        final Pair<Integer, Double> newresults = new Pair<>(rewardsCnt, average);
        this.outcomes.put(before, newresults);
        State state = before.getKey();
        Code code = before.getValue();
        Code current = this.best.get(state);
        if (current == null) {
            this.best.put(state, code);
        } else {
            if (average > this.outcomes.get(new Pair<>(state, current)).getValue()) {
                this.best.put(state, code);
            }
        }
    }

    /**
     * Retrieves the action selected by this policy for the given state. Builds a new random code
     * for a state that does not have an association.
     * @param state State
     * @return Policy result for the given state
     */
    public Code get(final State state) {
        Code result = this.best.get(state);
        if (result == null) {
            result = new RandomCode(this.count);
            final List<Code> played = state.rows().stream().map(
                r -> r.code()
            ).collect(Collectors.toList());
            while (played.contains(result)) {
                result = new RandomCode(this.count);
            }
            this.best.put(state, result);
            this.outcomes.put(new Pair<>(state, result), new Pair<>(0, -0.5));
        }
        return result;
    }

    /**
     * Retrieves the best action associated to the state. Builds a new random code
     * for a state that does not have an association.
     * @param state State
     * @return Best result for the given state
     */
    public Code best(final State state) {
        return this.get(state);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Policy [count=");
        builder.append(count);
        builder.append(", outcomes=");
        builder.append(this.toString(outcomes));
        builder.append("]");
        return builder.toString();
    }

    /**
     * Calculates the best action for each state as defined by the results.
     * @param results Map of results
     * @return Map associating a state to a code
     */
    private static Map<State, Code> best(
        Map<Pair<State, Code>, Pair<Integer, Double>> results) {
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

    /**
     * This method is a redefintion of the toString of map JDK method.
     * The purpose is to use a different separator between map entries. 
     * @param map Map to convert
     * @return A string representation of the map
     */
    private <K, V> String toString(Map<K, V> map) {
        Iterator<Entry<K, V>> i = map.entrySet().iterator();
        if (!i.hasNext()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            Entry<K, V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key == this ? "(this Map)" : key);
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value);
            if (!i.hasNext())
                return sb.append('}').toString();
            sb.append(';').append(' ');
        }
    }
}
