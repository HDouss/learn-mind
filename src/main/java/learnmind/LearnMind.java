package learnmind;

import learnmind.player.MonteCarloEpsilonSoft;
import learnmind.player.Player;

/**
 * Main class that just intiates a normal human playing master mind.
 * So for now, there is no learning yet.
 * @author hdouss
 *
 */
public class LearnMind {

    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        int colors = 6;

        Player p = new MonteCarloEpsilonSoft(colors, 0.1);
        p.learn(10);
        int s = 0;
        final int games = 1000000;
        /*for (int j = 0; j < games; ++j) {
            s += p.play();
        }
        System.out.println(
            String.format(
                "For colours count: %d, total reward after %d games is: %d",
                colors, games, s
            )
        );
        System.out.println((System.currentTimeMillis() - t) / 1000);*/
    }

}
