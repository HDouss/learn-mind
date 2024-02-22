package learnmind.learning;

import javafx.util.Pair;
import learnmind.heap.MinHeap;
import learnmind.heap.Node;
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
        MinHeap<Score> heap = this.outcomes.get(before.getKey());
        if (heap == null) {
            heap = new MinHeap<>(this.max / 10);
            this.outcomes.put(before.getKey(), heap);
        }
        Score elt = new Score(before.getValue(), 0, StateActionValues.INITIAL_VALUE);
        Node<Score> current = heap.node(elt);
        boolean exists = true;
        if (current == null) {
            current = new Node<>(elt, -elt.value);
            exists = false;
        }
        Double newvalue = this.next(after);
        final Double sofar = current.element().value;
        Double result = sofar + rate * (reward + newvalue - sofar);
        if (exists) {
            current.update(-result);
            current.element().value = result;
            if (result != sofar) {
                heap.update(current.element());
            }
        } else {
            heap.insert(new Node<>(new Score(before.getValue(), 0, result), -result));
        }
    }

    /**
     * Gets the next value to update the current state/action with; given the next state/action.
     * @param pair Next state/action pair.
     * @return Value to update value with
     */
    protected Double next(final Pair<State, Code> pair) {
        MinHeap<Score> heap = this.outcomes.get(pair.getKey());
        if (heap == null) {
            heap = new MinHeap<>(this.max / 10);
            this.outcomes.put(pair.getKey(), heap);
        }
        Score elt = new Score(pair.getValue(), 0, StateActionValues.INITIAL_VALUE);
        Node<Score> current = heap.node(elt);
        if (current == null) {
            current = new Node<>(elt, -elt.value);
            heap.insert(current);
        }
        return heap.peek().element().value;
    }
}