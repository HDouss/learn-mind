package learnmind.player;

/**
 * A player interface that can learn and play.
 * @author hdouss
 *
 */
public interface Player {

    /**
     * Initiates a learning phase.
     */
    public void learn();

    /**
     * Plays a game.
     */
    public void play();
}
