package learnmind.state;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A shifted (some sort of normalization) state by the first digit of the first code.
 * @author hdouss
 *
 */
public class ShiftedState extends State {

    /**
     * Shifting amount.
     */
    private final int shift;

    /**
     * Constructor. Shifts a state codes.
     * @param state State to shift
     * @param max Max used colors
     */
    public ShiftedState(final State state, final int max) {
        super(ShiftedState.shift(state, max));
        if (state.rows.size() == 0) {
            this.shift = 0;
        } else {
            this.shift = - state.rows.get(0).code().num() / 1000;
        }
    }

    public int shift() {
        return this.shift;
    }
    /**
     * Shifts the given state with the first digit of the first code.
     * @param state State to shift
     * @param max Max used colors
     * @return a list of rows composing the state
     */
    private static List<Row> shift(final State state, final int max) {
        if (state.rows.size() == 0) {
            return new ArrayList<>();
        }
        final int shift = - state.rows.get(0).code().num() / 1000;
        return state.rows().stream().map(
            row -> new Row(new ShiftedCode(row.code(), shift, max), row.result())
        ).collect(Collectors.toList());
    }

}
