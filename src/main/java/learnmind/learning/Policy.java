package learnmind.learning;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javafx.util.Pair;
import learnmind.heap.MinHeap;
import learnmind.heap.Node;
import learnmind.state.Code;
import learnmind.state.RandomCode;
import learnmind.state.ShiftedCode;
import learnmind.state.ShiftedState;
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
    final Map<State, MinHeap<Score>> outcomes;

    /**
     * Colors count used in the game.
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
     * Constructor with starting outcomes.
     * @param results Starting outcomes
     * @param cnt Colors count used in the game
     */
    public Policy(final Map<State, MinHeap<Score>> results, final int cnt) {
        this.outcomes = results;
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
        MinHeap<Score> heap = this.outcomes.get(before.getKey());
        if (heap == null) {
            heap = new MinHeap<>(3);
            this.outcomes.put(before.getKey(), heap);
        }
        Score elt = new Score(before.getValue(), 0, 0.);
        Node<Score> current = heap.node(elt);
        boolean exists = true;
        if (current == null) {
            current = new Node<>(elt, -elt.value);
            exists = false;
        }
        Integer rewardsCnt = current.element().count;
        rewardsCnt++;
        final Double sofar = current.element().value;
        final double average = rewardsCnt == 1 ? reward : sofar + (reward - sofar) / rewardsCnt;
        if (exists) {
            current.update(-average);
            current.element().count = rewardsCnt;
            current.element().value = average;
            if (average != sofar) {
                heap.update(current.element());
            }
        } else {
            heap.insert(new Node<>(new Score(before.getValue(), rewardsCnt, average), -average));
        }
    }

    /**
     * Retrieves the action selected by this policy for the given state. Builds a new random code
     * for a state that does not have an association.
     * @param state State
     * @return Policy result for the given state
     */
    public Code get(final State state) {
        final ShiftedState shifted = new ShiftedState(state, this.count);
        MinHeap<Score> result = this.outcomes.get(shifted);
        if (result == null) {
            Code play = new RandomCode(this.count, shifted);
            Score sc = new Score(play, 0, -0.5);
            final MinHeap<Score> minheap = new MinHeap<Score>(3);
            minheap.insert(new Node<Score>(sc, -sc.value));
            this.outcomes.put(shifted, minheap);
            result = minheap;
        }
        return new ShiftedCode(result.peek().element().code, - shifted.shift(), this.count);
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
