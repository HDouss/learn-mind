package learnmind.state;

/**
 * A code is a set of 4 ordered digits to be placed in the mastermind board.
 * This class represents the code to be broken as well as the guesses made by the player. 
 * @author hdouss
 *
 */
public class Code {

    /**
     * Numerical representation of the code.
     */
    private final int num;

    /**
     * Code constructor. Arguments are ordered from leftmost digit to the rightmost digit
     * to be placed in the board.
     * @param one First digit
     * @param two Second digit
     * @param three Third digit
     * @param four Fourth digit
     */
    public Code(final int one, final int two, final int three, final int four) {
        this.num = 1000 * one + 100 * two + 10 * three + four;
    }

    /**
     * Code constructor from the numerical representation.
     * @param num Numerical representation
     */
    public Code(final int num) {
        this.num = num;
    }

    /**
     * Code constructor from a string representation.
     * @param str String representation of a code
     */
    public Code(final String str) {
        this(Integer.parseInt(str));
    }

    /**
     * Accessor for the numerical representation of the code.
     * @return A numerical representation of the code.
     */
    public int num() {
        return this.num;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + num;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Code))
            return false;
        Code other = (Code) obj;
        if (num != other.num)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("%04d", this.num);
    }
}
