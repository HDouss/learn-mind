package learnmind.state;


import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Tests for {@link ShiftedCode}.
 * @since 0.1
 */
public final class ShiftedCodeTest {

    private final static Code INPUT = new Code(1234);

    /**
     * {@link ShiftedCode} with positive shift.
     */
    @Test
    public void shiftsPositive() {
        MatcherAssert.assertThat(new ShiftedCode(ShiftedCodeTest.INPUT, 1, 5), Matchers.equalTo(new Code(2340)));
        MatcherAssert.assertThat(new ShiftedCode(ShiftedCodeTest.INPUT, 2, 5), Matchers.equalTo(new Code(3401)));
        MatcherAssert.assertThat(new ShiftedCode(ShiftedCodeTest.INPUT, 3, 5), Matchers.equalTo(new Code(4012)));
        MatcherAssert.assertThat(new ShiftedCode(ShiftedCodeTest.INPUT, 4, 5), Matchers.equalTo(new Code(123)));
        MatcherAssert.assertThat(new ShiftedCode(ShiftedCodeTest.INPUT, 5, 5), Matchers.equalTo(new Code(1234)));
        MatcherAssert.assertThat(new ShiftedCode(ShiftedCodeTest.INPUT, 6, 5), Matchers.equalTo(new Code(2340)));
    }

    /**
     * {@link ShiftedCode} with negative shift.
     */
    @Test
    public void shiftsNegative() {
        MatcherAssert.assertThat(new ShiftedCode(ShiftedCodeTest.INPUT, 0, 5), Matchers.equalTo(new Code(1234)));
        MatcherAssert.assertThat(new ShiftedCode(ShiftedCodeTest.INPUT, -1, 5), Matchers.equalTo(new Code(123)));
        MatcherAssert.assertThat(new ShiftedCode(ShiftedCodeTest.INPUT, -2, 5), Matchers.equalTo(new Code(4012)));
        MatcherAssert.assertThat(new ShiftedCode(ShiftedCodeTest.INPUT, -3, 5), Matchers.equalTo(new Code(3401)));
        MatcherAssert.assertThat(new ShiftedCode(ShiftedCodeTest.INPUT, -4, 5), Matchers.equalTo(new Code(2340)));
        MatcherAssert.assertThat(new ShiftedCode(ShiftedCodeTest.INPUT, -5, 5), Matchers.equalTo(new Code(1234)));
    }

    /**
     * {@link ShiftedCode} with first digit.
     */
    @Test
    public void shiftsByFirstDigit() {
        MatcherAssert.assertThat(new ShiftedCode(ShiftedCodeTest.INPUT, 5), Matchers.equalTo(new Code(123)));
    }
}
