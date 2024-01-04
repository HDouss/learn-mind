package learnmind;

import learnmind.player.MonteCarlo;
import learnmind.player.Player;

/**
 * Main class that just intiates a normal human playing master mind.
 * So for now, there is no learning yet.
 * @author hdouss
 *
 */
public class LearnMind {

    public static void main(String[] args) {
        for (int i = 2; i < 7; i++) {
            Player p = new MonteCarlo(i);
            p.learn(7500000);
            int s = 0;
            final int games = 1000000;
            for (int j = 0; j < games; ++j) {
                s += p.play();
            }
            System.out.println(
                String.format(
                    "For colours count: %d, total reward after %d games is: %d",
                    i, games, s
                )
            );
            p = null;
            System.gc();
        }
    }

}
