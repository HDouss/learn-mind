package learnmind.state;

/**
 * A code with shifted digits. 
 * @author hdouss
 *
 */
public class Shifted extends Code {

    public Shifted(final Code code, final int max) {
        this(code, code.num() / 1000, max);
    }

    public Shifted(final Code code, final int shift, final int max) {
        super(Shifted.shift(code.num(), shift, max));
    }

    private static String shift(final int num, final int shift, final int max) {

        return null;
    }

}
