package learnmind.state;


import java.util.Random;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link Code}.
 * @since 0.1
 */
public final class CodeTest {

    /**
     * {@link Code} could be built using a string representation.
     */
    @Test
    public void buildsAsString() {
        final Random rnd = new Random();
        final Code code = new RandomCode(rnd.nextInt(9) + 1);
        MatcherAssert.assertThat(new Code(code.toString()), Matchers.equalTo(new Code(code.num())));
    }

}
