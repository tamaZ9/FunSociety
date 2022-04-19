package it.unibo.ai.didattica.competition.tablut.funsociety.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class BlackHeuristic extends Heuristic{

    public BlackHeuristic(State state) {
        super(state);
    }

    @Override
    public double evaluateState() {
        //TODO:
        /*
        *  Considerare il re accerchiato
        *  Bloccare gli escape
        *  Evitare di perdere troppe pedine per riuscire a mangiare il re
        *  Limitare i movimenti del re se e' nel castello
        * */
        return 0;
    }
}
