package learnmind.learning;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
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
 * The best selected action is epsilon-greedy. 
 * @author hdouss
 *
 */
public class EpsilonGreedyPolicy extends Policy {

    /**
     * Randomization threshold calculated with epsilon parameter.
     */
    private final double thresh;

    /**
     * Random generator.
     */
    private static final Random rnd = new Random(7865);

    /**
     * Default constructor with empty map.
     * @param cnt Colors count used in the game
     * @param eps Epsilon parameter
     */
    public EpsilonGreedyPolicy(final int cnt, final double eps) {
        this(new HashMap<>(), cnt, eps);
    }

    /**
     * Constructor with starting outcomes.
     * @param results Starting outcomes
     * @param cnt Colors count used in the game
     * @param eps Epsilon parameter
     */
    public EpsilonGreedyPolicy(final Map<State, MinHeap<Score>> results,
        final int cnt, final double eps) {
        super(results, cnt);
        this.thresh = 1 - eps + eps / Math.pow(cnt, 4);
    }

    /**
     * Retrieves the action associated to the state. Builds a new random code
     * for a state that does not have an association. This epsilon greedy implementation
     * selects a random action for with probability 'eps - eps/card(actions)'
     * and the best action with probability '1 - eps + eps/card(actions)'
     * @param state State
     * @return Policy result for the given state
     */
    public Code get(final State state) {
        final ShiftedState shifted = new ShiftedState(state, this.count());
        Code result = new ShiftedCode(
            new RandomCode(this.count(), state), shifted.shift(), this.count()
        );
        Code best = super.get(shifted);
        if (EpsilonGreedyPolicy.rnd.nextDouble() < this.thresh) {
            result = best;
        } else {
            while (best.equals(result)) {
                result = new ShiftedCode(
                    new RandomCode(this.count(), state), shifted.shift(), this.count()
                );
            }
            MinHeap<Score> heap = this.outcomes.get(shifted);
            Score sc = new Score(result, 0, -0.5);
            if (heap == null) {
                final MinHeap<Score> minheap = new MinHeap<Score>(3);
                this.outcomes.put(shifted, minheap);
                heap = minheap;
            }
            Node<Score> current = heap.node(sc);
            if (current == null) {
                heap.insert(new Node<Score>(sc, -sc.value));
            }
        }
        return new ShiftedCode(result, -shifted.shift(), this.count());
    }

    @Override
    public Code best(State state) {
        return super.get(state);
    }
}
