package it.unibo.ai.didattica.competition.tablut.funsociety.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class BlackHeuristic extends Heuristic{

    private static final int[] weights = {40, 5, 10, 30, 5};

    private static final int EATENPAWNS = 0;
    private static final int KINGSURROUNDING = 1;
    private static final int BLOCKEDESCAPES = 2;
    private static final int BLACKREMAINING = 3;
    private static final int KINGESCAPES = 4;

    private static final int[][] escapes = {{1, 1}, {1, 2}, {2, 1}, {1, 6}, {1, 7}, {2, 7}, {6, 1}, {7, 1}, {7, 2}, {6, 7}, {7, 6}, {7, 7}};

    public BlackHeuristic(State state) {
        super(state);
    }

    @Override
    public double evaluateState() {
        /*
        *  Considerare il re accerchiato
        *  Bloccare gli escape
        *  Considerare le pedine bianche mangiate e le pedine nere rimaste
        *  Considerare le vie di fuga possibili del re
        * */

        double result = 0;

        result += this.whiteEatenValue() * weights[EATENPAWNS];
        result += this.kingSurroundingValue() * weights[KINGSURROUNDING];
        result += (1 - this.blackEatenValue()) * weights[BLACKREMAINING];
        result += this.blockedEscapesValue() * weights[BLOCKEDESCAPES];
        result -= (this.possibleKingEscapesHorizontal() > 0 || this.possibleKingEscapesVertical() > 0) ? weights[KINGESCAPES] : 0;

        return result;
    }


    /**
     * @return the value of enemy pawns surrounding the king, normalized using the number of enemies required to eat the king on the base of his position
     * */
    private double kingSurroundingValue(){
        int enemyPiecesAround = this.enemyPiecesAroundKing();

        // Se il re e' adiacente al castello servono 3 pezzi per eliminarlo
        if (isKingNearThrone()){
            return ((enemyPiecesAround) / 3.0);
        } else if (checkKingPosition(state)) {
            // Se il re e' sul trono ce ne vogliono 4
            return ((enemyPiecesAround) / 4.0);
        } else {
            // Normalmente ne servono 2 (nel conteggio dei nemici e' gia' presente la citadel)
            return ((enemyPiecesAround) / 2.0);
        }
    }

    /**
     * @return the number of blocked escapes normalized by the number of total escapes
     * */
    private double blockedEscapesValue(){
        // L'obiettivo e' bloccare gli escape in modo che il re non possa raggiungerli
        double result = 0;
        for (int[] escape : escapes){
            if (state.getPawn(escape[0], escape[1]).equalsPawn("B")){
                result++;
            }
        }

        return (result / escapes.length);
    }


}
