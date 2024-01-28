package learnmind.player;

import learnmind.environment.Environment;
import learnmind.learning.EpsilonGreedyPolicy;
import learnmind.learning.Policy;
import learnmind.state.Code;

/**
 * Monte Carlo epsilon soft learning algorithm. 
 * @author hdouss
 *
 */
public class MonteCarloEpsilonSoft extends MonteCarlo {

    /**
     * Constructor with the number of colors used in the game.
     * @param cnt Colors count used in the game
     * @param eps Epsilon parameter
     */
    public MonteCarloEpsilonSoft(final int cnt, final double eps) {
        this(new EpsilonGreedyPolicy(cnt, eps));
    }

    /**
     * Constructor with the number of colors used in the game, and a starting policy to learn from.
     * @param policy Policy to start learning from
     * @param eps Epsilon parameter
     */
    public MonteCarloEpsilonSoft(final Policy policy) {
        super(policy);
    }

    @Override
    protected Code first(final Environment env) {
        return this.policy().get(env.current());
    }

    @Override
    protected Environment initial() {
        return new Environment(this.policy().count());
    }
}
