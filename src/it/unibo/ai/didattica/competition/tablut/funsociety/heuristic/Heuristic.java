package it.unibo.ai.didattica.competition.tablut.funsociety.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public abstract class Heuristic {

    protected State state;

    public Heuristic(State state){
        this.state = state;
    }

    /**
     * @param state
     * @return true if K is on the throne, false otherwise.
     */
    public boolean checkKingPosition(State state){
        return state.getPawn(4, 4).equalsPawn("K");
    }

    // Heuristic value
    public abstract double evaluateState();

}
