package learnmind.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import learnmind.environment.Environment;
import learnmind.environment.Feedback;
import learnmind.learning.Policy;
import learnmind.state.Code;
import learnmind.state.RandomCode;

/**
 * Monte Carlo exploring starts learning algorithm. 
 * @author hdouss
 *
 */
public class MonteCarlo implements Player {

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
    public MonteCarlo(final int cnt) {
        this(new Policy(cnt));
    }

    /**
     * Constructor with the number of colors used in the game, and a starting policy to learn from.
     * @param policy Policy to start learning from
     */
    public MonteCarlo(final Policy policy) {
        this.count = policy.count();
        this.policy = policy;
    }

    @Override
    public void learn(final int episodes) {
        for (int idx = 0; idx < episodes; ++idx) {
            Environment env = new Environment(this.count);
            final Set<Code> initial = env.randomState().rows().stream().map(
                r -> r.code()
            ).collect(Collectors.toSet());
            Code action = new RandomCode(this.count);
            while (initial.contains(action)) {
                action = new RandomCode(this.count);
            }
            List<Feedback> feeds = new ArrayList<>();
            Feedback feed = env.action(action);
            feeds.add(feed);
            while (!feed.finished()) {
                feed = env.action(this.policy.get(env.current()));
                feeds.add(feed);
            }
            final int reward = feed.reward();
            for (final Feedback step : feeds) {
                this.policy.add(step.before(), step.last().code(), reward);
            }
        }
    }

    @Override
    public Policy policy() {
        return this.policy;
    }

    @Override
    public int play() {
        Environment env = new Environment(this.count);
        boolean finished = false;
        Feedback feed = null;
        while (!finished) {
            feed = env.action(this.policy.get(env.current()));
            finished = feed.finished();
        }
        return feed.reward();
    }

}
