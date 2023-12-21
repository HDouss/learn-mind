package learnmind.state;

/**
 * A result is a two digits number that represents accuracy
 * for a {@link Code} representing a guess.
 * The first digit represents the black key peg count.
 * The second digit represents the white key peg count.
 * @author hdouss
 *
 */
public class Result {

    /**
     * Numerical representation of the result.
     */
    private final int num;

    /**
     * Result constructor. Arguments represents the number of black key pegs,
     * then the number of white key pegs.  
     * @param black Number of black key pegs
     * @param white Number of white key pegs
     */
    public Result(final int black, final int white) {
        this.num = Result.representation(black, white);
    }

    /**
     * Result constructor. Arguments represents code to be broken
     * and the guess made by the player.
     * @param secret Code to be broken
     * @param guess Player guess
     */
    public Result(final Code secret, final Code guess) {
        this.num = Result.evaluate(secret, guess);
    }

    /**
     * Black pegs count.
     * @return Black pegs count.
     */
    public int blacks() {
        return this.num / 10;
    }

    /**
     * White pegs count.
     * @return White pegs count.
     */
    public int whites() {
        return this.num % 10;
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
        if (getClass() != obj.getClass())
            return false;
        Result other = (Result) obj;
        if (num != other.num)
            return false;
        return true;
    }

    /**
     * Evaluates the guess against the secret.
     * @param secret Code to be broken
     * @param guess Guess
     * @return A numerical representation of a result
     */
    private static int evaluate(final Code secret, final Code guess) {
        int black = 0;
        int white = 0;
        int[] first = new int[4];
        int[] second = new int[4];
        for (int idx = 0; idx < 4; ++idx) {
            if (first[idx] == second[idx]) {
                black++;
                first[idx] = -1;
                second[idx] = -1;
            }
        }
        for (int idx = 0; idx < 4; ++idx) {
            if (second[idx] != -1) {
                for (int jdx = 0; jdx < 4; ++jdx) {
                    if (second[idx] == first[jdx]) {
                        white++;
                        first[jdx] = -1;
                        break;
                    }
                }
            }
        }
        return Result.representation(black, white);
    }

    /**
     * Calculates a numerical representation of a black/white pegs count. 
     * @param black Number of black pegs.
     * @param white Number of white pegs.
     * @return A numerical representation of a result
     */
    private static int representation(final int black, final int white) {
        return 10 * black + white;
    }
}
