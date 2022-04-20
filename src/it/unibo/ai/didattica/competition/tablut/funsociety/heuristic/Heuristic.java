package it.unibo.ai.didattica.competition.tablut.funsociety.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public abstract class Heuristic {

    protected State state;
    protected int numWhite;
    protected int numBlack;
    protected static int STARTINGWHITE = 8;
    protected static int STARTINGBLACK = 16;

    protected int[] kingPosition;

    public Heuristic(State state){
        this.state = state;
        this.numWhite = 0;
        this.numBlack = 0;
        this.kingPosition = new int[2];
        countPawns();
    }

    /**
     * @param state the current state
     * @return true if K is on the throne, false otherwise.
     */
    protected boolean checkKingPosition(State state){
        return state.getPawn(4, 4).equalsPawn("K");
    }

    /**
     * Pawns count (not including the King)
     * This method retrieves also the king position
     */
    private void countPawns(){
        for (int i = 0; i < this.state.getBoard().length; i++){
            for (int j = 0; j < this.state.getBoard().length; j++){
                if (state.getPawn(i, j).equalsPawn("W")){
                    this.numWhite++;
                } else if (state.getPawn(i, j).equalsPawn("B")){
                    this.numBlack++;
                } else if (state.getPawn(i, j).equalsPawn("K")){
                    this.kingPosition[0] = i;
                    this.kingPosition[1] = j;
                }
            }
        }
    }

    protected double whiteEatenValue(){
        return (STARTINGWHITE - this.numWhite) / STARTINGWHITE;
    }

    protected double blackEatenValue(){
        return (STARTINGBLACK - this.numBlack) / STARTINGBLACK;
    }

    //Check if the position is a camp
    protected boolean isCamp(int row, int col){
        return (((row == 0 || row == 8) && (col >= 3 && col <= 5)) || ((row == 1 || row == 7) && (col == 4))
                || ((col == 0 || col == 8) && (row >= 3 && row <= 5)) || ((col == 1 || col == 7) && (row == 4)));
    }

    protected boolean isKingNearThrone(){
        // Sopra il re
        if (kingPosition[0]-1 >= 0 && (this.state.getPawn(kingPosition[0] - 1, kingPosition[1]).equalsPawn("T"))){
            return true;
        }

        // Sotto il re
        if (kingPosition[0]+1 < state.getBoard().length && (this.state.getPawn(kingPosition[0] + 1, kingPosition[1]).equalsPawn("T"))){
            return true;
        }

        // A sinistra del re
        if (kingPosition[1]-1 >= 0 && (this.state.getPawn(kingPosition[0], kingPosition[1] - 1).equalsPawn("T"))){
            return true;
        }

        // A destra del re
        if (kingPosition[1]+1 < state.getBoard().length && (this.state.getPawn(kingPosition[0], kingPosition[1] + 1).equalsPawn("T"))){
            return true;
        }

        return false;
    }

    protected int enemyPiecesAroundKing(){
        int result = 0;
        // Sopra il re
        if (kingPosition[0]-1 >= 0 && (this.state.getPawn(kingPosition[0] - 1, kingPosition[1]).equalsPawn("B")
                || isCamp(kingPosition[0] - 1, kingPosition[1]))){
            result++;
        }

        // Sotto il re
        if (kingPosition[0]+1 < state.getBoard().length && (this.state.getPawn(kingPosition[0] + 1, kingPosition[1]).equalsPawn("B")
                || isCamp(kingPosition[0] + 1, kingPosition[1]))){
            result++;
        }

        // A sinistra del re
        if (kingPosition[1]-1 >= 0 && (this.state.getPawn(kingPosition[0], kingPosition[1] - 1).equalsPawn("B")
                || isCamp(kingPosition[0], kingPosition[1] - 1))){
            result++;
        }

        // A destra del re
        if (kingPosition[1]+1 < state.getBoard().length && (this.state.getPawn(kingPosition[0], kingPosition[1] + 1).equalsPawn("B")
                || isCamp(kingPosition[0], kingPosition[1] + 1))){
            result++;
        }

        return result;
    }

    // Heuristic value
    public abstract double evaluateState();

}
