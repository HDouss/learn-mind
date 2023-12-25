package learnmind.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A state is a list of {@link Row}s.
 * @author hdouss
 *
 */
public class State {

    /**
     * List of rows.
     */
    private final List<Row> rows;

    /**
     * State constructor.
     * @param row Rows array
     */
    public State(final Row... row) {
        this(Arrays.asList(row));
    }

    /**
     * State constructor.
     * @param rows Rows list
     */
    public State(final List<Row> rows) {
        this.rows = new ArrayList<Row>(rows);
    }

    /**
     * Accessor for the board rows.
     * @return Board rows.
     */
    public List<Row> rows() {
        return this.rows;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((rows == null) ? 0 : rows.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        State other = (State) obj;
        if (rows == null) {
            if (other.rows != null)
                return false;
        } else if (!rows.equals(other.rows))
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (final Row current : this.rows) {
            sb.append(current.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
