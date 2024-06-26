package learnmind.learning;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import learnmind.heap.MinHeap;
import learnmind.heap.Node;
import learnmind.state.Code;
import learnmind.state.RandomCode;
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
        Code result = new RandomCode(this.count(), state);
        Code best = super.get(state);
        if (EpsilonGreedyPolicy.rnd.nextDouble() < this.thresh) {
            result = best;
        } else {
            while (best.equals(result)) {
                result = new RandomCode(this.count(), state);
            }
            MinHeap<Score> heap = this.outcomes.get(state);
            Score sc = new Score(result, 0, -0.5);
            if (heap == null) {
                final MinHeap<Score> minheap = new MinHeap<Score>(3);
                this.outcomes.put(state, minheap);
                heap = minheap;
            }
            Node<Score> current = heap.node(sc);
            if (current == null) {
                heap.insert(new Node<Score>(sc, -sc.value));
            }
        }
        return result;
    }

    @Override
    public Code best(State state) {
        return super.get(state);
    }
}
