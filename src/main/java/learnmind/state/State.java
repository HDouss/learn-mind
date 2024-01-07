package learnmind.state;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A state is a list of {@link Row}s.
 * @author hdouss
 *
 */
public class State {

    /**
     * List of rows.
     */
    protected final Set<Row> rows;

    /**
     * State constructor.
     * @param row Rows array
     */
    public State(final Row... row) {
        this(new HashSet<>(Arrays.asList(row)));
    }

    /**
     * State constructor from a string representation.
     * @param str String representation of a result
     */
    public State(final String str) {
        this(
            Arrays.stream(
                str.length() == 0 ? new String[0] : str.split(System.lineSeparator())
            ).map(Row::new).collect(Collectors.toSet())
        );
    }

    /**
     * State constructor.
     * @param rows Rows list
     */
    public State(final Set<Row> rows) {
        this.rows = new HashSet<Row>(rows);
    }

    /**
     * Accessor for the board rows.
     * @return Board rows.
     */
    public Set<Row> rows() {
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
        if (!(obj instanceof State))
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
