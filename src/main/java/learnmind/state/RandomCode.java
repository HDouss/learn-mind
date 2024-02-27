package learnmind.state;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

    /**
     * Random code constructor excluding codes played in the passed state.
     * @param max Colors count used in the game
     * @param state State to exclude played codes from 
     */
    public RandomCode(final int max, final State state) {
        super(RandomCode.unplayed(max, state));
    }

    /**
     * Builds a random code that was not played in the passed state.
     * @param max Colors count
     * @param state State
     * @return A numerical representation of the code
     */
    private static Integer unplayed(final int max, final State state) {
        Code play = new RandomCode(max);
        final List<Code> played = state.rows().stream().map(
            r -> r.code()
        ).collect(Collectors.toList());
        while (played.contains(play)) {
            play = new RandomCode(max);
        }
        return play.num();
    }
}
