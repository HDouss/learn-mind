package learnmind;

import learnmind.player.MonteCarlo;
import learnmind.player.Player;
import learnmind.player.Random;

/**
 * Main class that just intiates a normal human playing master mind.
 * So for now, there is no learning yet.
 * @author hdouss
 *
 */
public class LearnMind {

    public static void main(String[] args) {
        int colors = 4;
        Player p = new MonteCarlo(colors);
        p.learn(5000000);
        int s = 0;
        final int games = 1000000;
        for (int j = 0; j < games; ++j) {
            s += p.play();
        }
        System.out.println(
            String.format(
                "For colours count: %d, total reward after %d games is: %d",
                colors, games, s
            )
        );
    }

}
