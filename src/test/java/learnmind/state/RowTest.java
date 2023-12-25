package learnmind.state;


import java.util.Random;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link Row}.
 * @since 0.1
 */
public final class RowTest {

    /**
     * {@link Row} could be built using a string representation.
     */
    @Test
    public void buildsAsString() {
        final Random rnd = new Random();
        final Result result = new Result(rnd.nextInt(5), rnd.nextInt(5));
        final Code code = new Code(new RandomCode(rnd.nextInt(9) + 1).num());
        final Row row = new Row(code, result);
        MatcherAssert.assertThat(new Row(row.toString()), Matchers.equalTo(row));
    }

}
