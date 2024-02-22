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
        MinHeap<Score> heap = this.outcomes.get(pair.getKey());
        if (heap == null) {
            heap = new MinHeap<>(this.max / 10);
            this.outcomes.put(pair.getKey(), heap);
        }
        Score elt = new Score(this.best(pair.getKey()), 0, StateActionValues.INITIAL_VALUE);
        Node<Score> current = heap.node(elt);
        if (current == null) {
            current = new Node<>(elt, -elt.value);
            heap.insert(current);
        }
        return heap.peek().element().value;
    }
}