package it.unibo.ai.didattica.competition.tablut.funsociety.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class WhiteHeuristic extends Heuristic{

    private static final int[] weights = {50, 30, 20};
    private static final int KINGESCAPE = 0;
    private static final int KINGSURROUNDING = 1;
    private static final int EATENPAWNS = 2;

    public WhiteHeuristic(State state) {
        super(state);
    }

    @Override
    public double evaluateState() {
        //TODO:
        /*
        *  Considerare la distanza del re dall'escape
        *  Pedine nemiche intorno al re
        *  Pedine mangiate (nemici o alleati?)
        *  Possibilita di essere mangiato
        * */

        double result = 0;

        result += weights[KINGESCAPE] * kingEscapeValue();
        result += weights[KINGSURROUNDING] * kingSurroundingValue();
        result += weights[EATENPAWNS] * this.blackEatenValue();

        return result;
    }

    private double kingEscapeValue(){

        //TODO
        return 0;
    }

    /**
     * @return the value of enemy pawns surrounding the king, normalized using the number of enemies required to eat the king on the base of his position
     * */
    private double kingSurroundingValue(){
        int enemyPiecesAround = this.enemyPiecesAroundKing();

        // Se il re e' adiacente al castello servono 3 pezzi per eliminarlo
        if (isKingNearThrone()){
            return ((3.0 - enemyPiecesAround) / 3);
        } else if (checkKingPosition(state)) {
            // Se il re e' sul trono ce ne vogliono 4
            return ((4.0 - enemyPiecesAround) / 4);
        } else {
            // Normalmente ne servono 2 (nel conteggio dei nemici e' gia' presente la citadel)
            return ((2.0 - enemyPiecesAround) / 2);
        }
    }
}
