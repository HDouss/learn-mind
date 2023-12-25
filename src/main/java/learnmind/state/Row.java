package learnmind.state;

/**
 * A row is a pair of a {@link Code} and its corresponding {@link Result}.
 * @author hdouss
 *
 */
public class Row {

    /**
     * Row guess.
     */
    private final Code guess;

    /**
     * Row result.
     */
    private final Result result;

    /**
     * Row constructor with the passed guess and result.
     * @param guess Row guess
     * @param result Guess result
     */
    public Row(final Code guess, final Result result) {
        this.guess = guess;
        this.result = result;
    }

    /**
     * Row constructor from a string representation.
     * @param str String representation of a result
     */
    public Row(final String str) {
        this(
            new Code(str.split("\\s--\\s")[0].split(":\\s")[1]),
            new Result(str.split("\\s--\\s")[1].split(":\\s")[1])
        );
    }

    /**
     * Acessor for the result.
     * @return Row's result
     */
    public Result result() {
        return this.result;
    }

    /**
     * Acessor for the action.
     * @return Row's guess
     */
    public Code code() {
        return this.guess;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((guess == null) ? 0 : guess.hashCode());
        result = prime * result
            + ((this.result == null) ? 0 : this.result.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Row))
            return false;
        Row other = (Row) obj;
        if (guess == null) {
            if (other.guess != null)
                return false;
        } else if (!guess.equals(other.guess))
            return false;
        if (result == null) {
            if (other.result != null)
                return false;
        } else if (!result.equals(other.result))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("Guess: %s -- Result: %s", this.guess, this.result);
    }
}
