package learnmind.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javafx.util.Pair;
import learnmind.environment.Environment;
import learnmind.environment.Feedback;
import learnmind.learning.Policy;
import learnmind.state.Code;
import learnmind.state.RandomCode;
import learnmind.state.State;

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
     * Constructor with the starting policy to learn from.
     * @param policy Policy to start learning from
     */
    public MonteCarlo(final Policy policy) {
        this.count = policy.count();
        this.policy = policy;
    }

    @Override
    public void learn(final int episodes) {
        for (int idx = 0; idx < episodes; ++idx) {
            Environment env = this.initial();
            Code action = this.first(env);
            List<Feedback> feeds = new ArrayList<>();
            Feedback feed = env.action(action);
            feeds.add(feed);
            while (!feed.finished()) {
                feed = env.action(this.policy.get(env.current()));
                feeds.add(feed);
            }
            Collections.reverse(feeds);
            State after = env.current();
            Code code = null;
            Integer reward = feed.reward();
            for (final Feedback step : feeds) {
                this.policy.update(
                    new Pair<>(step.before(), step.last().code()),
                    new Pair<>(after, code),
                    reward, 0.
                );
                after = step.before();
                code = step.last().code();
                reward += step.reward();
            }
            System.out.println(this.policy.toString());
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

    /**
     * Builds the first action to play in the learning process.
     * @param env Current (initial) environment
     * @return Action to play
     */
    protected Code first(final Environment env) {
        final List<Code> initial = env.current().rows().stream().map(
            r -> r.code()
        ).collect(Collectors.toList());
        Code action = new RandomCode(this.count);
        while (initial.contains(action)) {
            action = new RandomCode(this.count);
        }
        return action;
    }

    /**
     * Builds an initial environment for learning.
     * @return A learning environment
     */
    protected Environment initial() {
        Environment env = new Environment(this.count);
        env.randomState();
        return env;
    }

}
