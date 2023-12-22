package learnmind.player;

import java.util.Scanner;
import learnmind.environment.Environment;
import learnmind.environment.Feedback;
import learnmind.state.Code;

/**
 * Human player gives the user the opportunity to play. Learning is not supported.
 * @author hdouss
 *
 */
public class Human implements Player {

    /**
     * Colors count.
     */
    private final int count;

    /**
     * Constructor with the number of colors used in the game.
     * @param cnt Colors count used in the game
     */
    public Human(final int cnt) {
        this.count = cnt;
    }

    @Override
    public void learn() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void play() {
        Environment env = new Environment(this.count);
        boolean finished = false;
        Feedback feed = null;
        final Scanner scanner = new Scanner(System.in);
        while (!finished) {
            feed = env.action(Human.input(scanner));
            Human.output(feed);
            finished = feed.finished();
        }
        System.out.println(String.format("Final reward is: %d", feed.reward()));
        scanner.close();
    }

    /**
     * Prints feedback as an output to the console. 
     * @param feed Feedback
     */
    private static void output(Feedback feed) {
        System.out.println("Current state:");
        System.out.println(feed.state().toString());
    }

    /**
     * Gets input from the console input stream.
     * @param scanner Input stream scanner
     * @return The code input by the player
     */
    private static Code input(final Scanner scanner) {
        System.out.println("Give your guess:");
        final int guess = Integer.parseInt(scanner.next());
        return new Code(
            guess / 1000, (guess % 1000) / 100, (guess % 100) / 10, guess % 10
        );
    }

}
