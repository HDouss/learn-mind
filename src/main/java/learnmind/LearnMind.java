package learnmind;

import learnmind.player.Human;

/**
 * Main class that just intiates a normal human playing master mind.
 * So for now, there is no learning yet.
 * @author hdouss
 *
 */
public class LearnMind {

    public static void main(String[] args) {
        Human h = new Human(6);
        h.play();
    }

}
