package learnmind.learning;

import java.util.HashMap;
import java.util.Map;
import learnmind.state.Code;
import learnmind.state.RandomCode;
import learnmind.state.State;

/**
 * Policy implementation that holds an association between stated and codes (actions).
 * An empty action for a state builds a random code for this state.
 * @author hdouss
 *
 */
public class Policy {

    /**
     * Set of rules which is an association between a state and a code.
     */
    final Map<State, Code> rules;

    /**
     * Colors count used in the game
     */
    final int count;

    /**
     * Default constructor with empty rules.
     * @param cnt Colors count used in the game
     */
    public Policy(final int cnt) {
        this(new HashMap<>(), cnt);
    }

    /**
     * Constructor with starting rules.
     * @param rules
     */
    public Policy(final Map<State, Code> rules, final int cnt) {
        this.rules = rules;
        this.count = cnt;
    }

    /**
     * Modifies the action for the given state.
     * @param state State to modify
     * @param code Code to associate with the state
     */
    public void modify(final State state, final Code code) {
        this.rules.put(state, code);
    }

    /**
     * Retrieves the action associated to the state. Builds a new random code
     * for a state that does not have an association.
     * @param state State
     * @return Policy result for the given state
     */
    public Code get(final State state) {
        Code result = this.rules.get(state);
        if (result == null) {
            result = new RandomCode(this.count);
            this.modify(state, result);
        }
        return result;
    }
}
