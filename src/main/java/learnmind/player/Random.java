package learnmind.player;

import learnmind.environment.Environment;
import learnmind.environment.Feedback;
import learnmind.learning.Policy;
import learnmind.state.State;

/**
 * Completely random player. 
 * @author hdouss
 *
 */
public class Random implements Player {

    /**
     * Colors count.
     */
    private final int count;

    /**
     * Calculated policy.
     */
    private final Policy policy;

    /**
     * Constructor with the number of colors used in the game.
     * @param cnt Colors count used in the game
     */
    public Random(final int cnt) {
        this(new Policy(cnt));
    }

    /**
     * Constructor with the number of colors used in the game, and a starting policy to learn from.
     * @param policy Policy to start learning from
     */
    public Random(final Policy policy) {
        this.count = policy.count();
        this.policy = policy;
    }

    @Override
    public void learn(final int episodes) {
    }

    @Override
    public Policy policy() {
        return this.policy;
    }

    @Override
    public int play() {
        return this.play(false);
    }

    @Override
    public int play(final boolean verbose) {
        Environment env = new Environment(this.count);
        boolean finished = false;
        Feedback feed = null;
        State current = env.current();
        while (!finished) {
            feed = env.action(this.policy.get(current).code());
            current = env.current();
            finished = feed.finished();
        }
        if (verbose) {
            System.out.println(env.current());
        }
        return feed.reward();
    }

}
