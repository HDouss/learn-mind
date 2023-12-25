package learnmind.player;

import learnmind.learning.Policy;

/**
 * A player interface that can learn and play.
 * @author hdouss
 *
 */
public interface Player {

    /**
     * Initiates a learning phase.
     * @param episodes Games number to learn from
     */
    public void learn(final int episodes);

    /**
     * Plays a game.
     */
    public void play();

    /**
     * Gives the learned policy.
     * @return The learned policy
     */
    public Policy policy();
}
