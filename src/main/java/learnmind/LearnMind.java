package learnmind;

import learnmind.learning.BestNext;
import learnmind.player.MonteCarloEpsilonSoft;
import learnmind.player.Player;
import learnmind.player.Qlearning;
import learnmind.player.Sarsa;

/**
 * Main class that just intiates a normal human playing master mind.
 * So for now, there is no learning yet.
 * @author hdouss
 *
 */
public class LearnMind {

    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        int colors = 2;

        Player p = new Qlearning(colors, 0.3, 0.8);
        p.learn(2_000_000_000);
        int verbose = 10;
        for (int j = 0; j < verbose ; ++j) {
            System.out.println(p.play(true));
        }
        int s = 0;
        final int games = 2_000_000;
        for (int j = 0; j < games; ++j) {
            s += p.play();
        }
        System.out.println(
            String.format(
                "For colours count: %d, total reward after %d games is: %d",
                colors, games, s
            )
        );
        System.out.println((System.currentTimeMillis() - t) / 1000);
    }

}
