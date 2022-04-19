package it.unibo.ai.didattica.competition.tablut.funsociety.search;

import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;

public class IterativeDeepeningAlphaBeta extends IterativeDeepeningAlphaBetaSearch<State, Action, State.Turn> {

    public IterativeDeepeningAlphaBeta(Game<State, Action, State.Turn> game, double utilMin, double utilMax, int time) {
        super(game, utilMin, utilMax, time);
    }

    // Override perche', da documentazione, l'implementazione originale:
    // This implementation returns the utility value for
    // terminal states and (utilMin + utilMax) / 2 for non-terminal
    // states.
    // --> euristica applicata solo agli stati finali
    @Override
    protected double eval(State state, State.Turn turn) {
        super.eval(state, turn);

        return game.getUtility(state, turn);
    }

    /**
     * Overrided to print the metrics
     * @param state the current state
     * @return the action chosen
     */
    @Override
    public Action makeDecision(State state) {
        Action a = super.makeDecision(state);
        System.out.println("Expanded nodes = " + getMetrics().get(METRICS_NODES_EXPANDED) + " , maximum depth = " + getMetrics().get(METRICS_MAX_DEPTH));
        return a;
    }
}
