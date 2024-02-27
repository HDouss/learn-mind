package learnmind.player;

import learnmind.learning.BestNext;

/**
 * Q-learning algorithm. 
 * @author hdouss
 *
 */
public class Qlearning extends Sarsa {

    /**
     * Constructor with the number of colors used in the game, epsilon and learning rate parameters.
     * @param cnt Colors count used in the game
     * @param eps Epsilon parameter
     * @param alpha Learning rate
     */
    public Qlearning(final int cnt, final double eps, final double alpha) {
        super(new BestNext(cnt, eps), alpha);
    }

}
