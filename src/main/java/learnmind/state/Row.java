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
     * Acessor for the result.
     * @return Row's result
     */
    public Result result() {
        return this.result;
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
        if (getClass() != obj.getClass())
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

}
