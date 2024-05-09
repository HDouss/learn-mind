package learnmind.state;

/**
 * A code with shifted digits. 
 * @author hdouss
 *
 */
public class ShiftedCode extends Code {

    /**
     * Constructor. Shifts the code by its first digit, producing a code with a leading 0.
     * @param code Code to be shifted
     * @param max Max colors used
     */
    public ShiftedCode(final Code code, final int max) {
        this(code, - code.num() / 1000, max);
    }

    /**
     * Constructor. Shifts the code by the passed amount.
     * @param code Code to be shifted
     * @param shift Shifting amount
     * @param max Max colors used
     */
    public ShiftedCode(final Code code, final int shift, final int max) {
        super(ShiftedCode.shift(code.num(), shift, max));
    }

    /**
     * Shifts a number by the passed amount, given the max used colors.
     * @param num Number to be shifted
     * @param shift Shifting amount
     * @param max Max colors used
     * @return Shifting result
     */
    private static int shift(final int num, final int shift, final int max) {
        final int first = Math.floorMod((num / 1000) + shift, max);
        final int second = Math.floorMod(((num / 100) % 10) + shift, max);
        final int third = Math.floorMod(((num / 10) % 10) + shift, max);
        final int forth = Math.floorMod((num % 10) + shift, max);
        return 1000 * first + 100 * second + 10 * third + forth;
    }

}
