package it.unibo.ai.didattica.competition.tablut.funsociety.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class WhiteHeuristic extends Heuristic{

    public WhiteHeuristic(State state) {
        super(state);
    }

    @Override
    public double evaluateState() {
        //TODO:
        /*
        *  Considerare la distanza del re dall'escape
        *  Pedine nemiche intorno al re
        *  Pedine mangiate
        *  Possibilita di essere mangiato
        * */
        return 0;
    }
}
