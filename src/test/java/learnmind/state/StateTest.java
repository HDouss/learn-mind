package learnmind.state;

import java.util.Random;
import learnmind.environment.Environment;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link State}.
 * @since 0.1
 */
public final class StateTest {

    /**
     * {@link State} could be built using a string representation.
     */
    @Test
    public void buildsAsString() {
        final Random rnd = new Random();
        final Environment environment = new Environment(rnd.nextInt(9) + 1);
        environment.randomState();
        final State state = environment.current();
        MatcherAssert.assertThat(new State(state.toString()), Matchers.equalTo(state));
    }

    /**
     * {@link State} can accept empty string.
     */
    @Test
    public void acceptsEmptyString() {
        final State state = new State("");
        MatcherAssert.assertThat(state.rows().size(), Matchers.equalTo(0));
    }
}
