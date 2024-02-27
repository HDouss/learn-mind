package learnmind.player;

import javafx.util.Pair;
import learnmind.environment.Environment;
import learnmind.environment.Feedback;
import learnmind.learning.Policy;
import learnmind.learning.StateActionValues;
import learnmind.state.Code;
import learnmind.state.State;

/**
 * Sarsa algorithm. 
 * @author hdouss
 *
 */
public class Sarsa extends MonteCarloEpsilonSoft {

    /**
     * Learning rate.
     */
    private final double rate;

    /**
     * Constructor with the number of colors used in the game, epsilon and learning rate parameters.
     * @param cnt Colors count used in the game
     * @param eps Epsilon parameter
     * @param alpha Learning rate
     */
    public Sarsa(final int cnt, final double eps, final double alpha) {
        super(new StateActionValues(cnt, eps));
        this.rate = alpha;
    }

    /**
     * Constructor with the policy and learning rate parameter.
     * @param policy Policy
     * @param alpha Learning rate
     */
    public Sarsa(final Policy policy, final double alpha) {
        super(policy);
        this.rate = alpha;
    }

    @Override
    public void learn(final int episodes) {
        for (int idx = 0; idx < episodes; ++idx) {
            Environment env = this.initial();
            State state = env.current();
            Code action = this.first(env);
            Feedback feed = env.action(action);
            State stateAfter = env.current();
            Code actionAfter = feed.finished() ? null : this.policy().get(stateAfter);
            this.policy().update(
                new Pair<>(state, action),
                new Pair<>(stateAfter, actionAfter),
                feed.reward(), this.rate
            );
            while (!feed.finished()) {
                state = stateAfter;
                action = actionAfter;
                feed = env.action(action);
                stateAfter = env.current();
                actionAfter = feed.finished() ? null : this.policy().get(stateAfter);
                this.policy().update(
                    new Pair<>(state, action),
                    new Pair<>(stateAfter, actionAfter),
                    feed.reward(), this.rate
                );
            }
        }
    }

}
