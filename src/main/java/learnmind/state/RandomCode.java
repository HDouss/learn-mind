package learnmind.state;

import java.util.Random;

public class RandomCode extends Code {

    /**
     * Random generator.
     */
    private static final Random random = new Random(1234);

    /**
     * Random code constructor. 
     * @param max Colors count used in the game 
     */
    public RandomCode(final int max) {
        super(random.nextInt(max), random.nextInt(max), random.nextInt(max), random.nextInt(max));
    }

}
