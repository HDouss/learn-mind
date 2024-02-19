package learnmind.learning;

import learnmind.state.Code;

public class Score {

    /*
     * Action.
     */
    Code code;

    /*
     * Rewards count. 
     */
    Integer count;

    /*
     * Value.
     */
    Double value;

    /**
     * Constructor with score fields.
     * @param play Action
     * @param cnt Rewards count
     * @param val Value
     */
    public Score(final Code play, final int cnt, final double val) {
        this.code = play;
        this.count = cnt;
        this.value = val;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Score))
            return false;
        Score other = (Score) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        return true;
    }

}
