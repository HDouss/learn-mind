package learnmind.player;

import java.util.ArrayList;
import java.util.List;
import learnmind.environment.Environment;
import learnmind.environment.Feedback;
import learnmind.learning.Policy;
import learnmind.state.Code;
import learnmind.state.RandomCode;
import learnmind.state.Row;
import learnmind.state.State;

/**
 * Monte Carlo exploring starts learning alogorithm. 
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
        this(cnt, new Policy(cnt));
    }

    /**
     * Constructor with the number of colors used in the game, and a starting policy to learn from.
     * @param cnt Colors count used in the game
     * @param policy Policy to start learning from
     */
    public MonteCarlo(final int cnt, final Policy policy) {
        this.count = cnt;
        this.policy = policy;
    }

    @Override
    public void learn(final int episodes) {
        for (int idx = 0; idx < episodes; ++idx) {
            Environment env = new Environment(this.count);
            env.randomState();
            Code action = new RandomCode(this.count);
            Feedback feed = env.action(action);
            int steps = 1;
            while (!feed.finished()) {
                feed = env.action(this.policy.get(feed.state()));
                steps++;
            }
            final int reward = feed.reward();
            List<Row> rows = feed.state().rows();
            for (int stp = 0; stp < steps; ++stp) {
                Code currentAction = rows.get(rows.size() - 1 - stp).code();
                State currentState = new State(rows.subList(0, rows.size() - 1 - stp));
                this.policy.add(currentState, currentAction, reward);
            }
        }
    }

    @Override
    public Policy policy() {
        return this.policy;
    }

    @Override
    public void play() {
        Environment env = new Environment(this.count);
        boolean finished = false;
        Feedback feed = null;
        State current = new State(new ArrayList<>(0));
        while (!finished) {
            feed = env.action(this.policy.get(current));
            MonteCarlo.output(feed);
            current = feed.state();
            finished = feed.finished();
        }
        System.out.println(String.format("Final reward is: %d", feed.reward()));
    }

    /**
     * Prints feedback as an output to the console. 
     * @param feed Feedback
     */
    private static void output(Feedback feed) {
        System.out.println("Current state:");
        System.out.println(feed.state().toString());
    }

}
