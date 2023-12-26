package learnmind.learning;


import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import learnmind.player.MonteCarlo;

/**
 * Tests for {@link Policy}.
 * @since 0.1
 */
public final class PolicyTest {

    /**
     * {@link Policy} could be built using a string representation.
     */
    @Test
    public void buildsAsString() {
        MonteCarlo mc = new MonteCarlo(6);
        mc.learn(50);
        final Policy policy = mc.policy();
        MatcherAssert.assertThat(
            new Policy(policy.toString()).toString(),
            Matchers.equalTo(policy.toString())
        );
    }

}
