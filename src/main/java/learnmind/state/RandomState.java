package learnmind.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import learnmind.environment.Environment;
import learnmind.environment.Feedback;

/**
 * A random state generated as a non final state limited to a max of 5 rows. A random state
 * is a real state which means that it has a real feedback against a real code to be broken. 
 * @author hdouss
 *
 */
public class RandomState extends State {

    /**
     * State constructor.
     * @param count Colors count used in the game
     */
    public RandomState(final int count) {
        super(RandomState.random(count));
    }

    /**
     * Generates a real random list of {@link Row}s.
     * @param count Colors count used in the game
     * @return A list of rows
     */
    private static List<Row> random(final int count) {
        int rows = new Random().nextInt(6);
        List<Row> result = new ArrayList<>(0);
        boolean generated = rows == 0;
        while (!generated) {
            final Environment env = new Environment(count);
            Feedback feedback = null;
            for (int idx = 0; idx < rows; ++idx) {
                feedback = env.action(new RandomCode(count));
                if (feedback.finished()) {
                    break;
                }
            }
            if (!feedback.finished()) {
                result = feedback.state().rows();
                generated = true;
            }
        }
        return result;
    }

}
