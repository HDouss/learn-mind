package learnmind;

import learnmind.player.MonteCarlo;

/**
 * Main class that just intiates a normal human playing master mind.
 * So for now, there is no learning yet.
 * @author hdouss
 *
 */
public class LearnMind {

    public static void main(String[] args) {
        MonteCarlo mc = new MonteCarlo(6);
        mc.learn(1000000);
        mc.play();
    }

}
