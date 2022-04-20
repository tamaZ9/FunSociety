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

    /**
     * @return the value (normalized between 0 and 1) of white pawns eaten
     * */
    protected double whiteEatenValue(){
        return ((double) STARTINGWHITE - this.numWhite) / STARTINGWHITE;
    }

    /**
     * @return the value (normalized between 0 and 1) of black pawns eaten
     * */
    protected double blackEatenValue(){
        return ((double) STARTINGBLACK - this.numBlack) / STARTINGBLACK;
    }

    /**
     * @param row the row to check
     * @param col the column to check
     * @return true if the position described by the params is a Camp
     * */
    protected boolean isCamp(int row, int col){
        return (((row == 0 || row == 8) && (col >= 3 && col <= 5)) || ((row == 1 || row == 7) && (col == 4))
                || ((col == 0 || col == 8) && (row >= 3 && row <= 5)) || ((col == 1 || col == 7) && (row == 4)));
    }

    /**
    * @return true if the king is adjacent to the throne
    * */
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

    /**
     * @return the number of enemies surrounding the king (camps included)
     * */
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

    /**
     * @return true if the king is on the central square (area where he can't find a direct path to escape)
     * */
    protected boolean isKingOnCentralSquare(){
        return ((kingPosition[0] >= 3 && kingPosition[0] <= 5) && (kingPosition[1] >= 3 && kingPosition[1] <= 5));
    }

    /**
     * @return the number of possible escapes going up or down (0, 1 or 2)
     * */
    protected int possibleKingEscapesVertical(){
        int result = 2;

        // Check above the king
        for (int i = kingPosition[0] - 1; i >= 0; i--){
            if (!(state.getPawn(i, kingPosition[1]).equalsPawn("O"))){
                result--;
                break;
            }
        }

        // Check under the king
        for (int i = kingPosition[0] + 1; i < state.getBoard().length; i++){
            if (!(state.getPawn(i, kingPosition[1]).equalsPawn("O"))){
                result--;
                break;
            }
        }

        return result;
    }

    /**
     * @return the number of possible escapes going left or right (0, 1 or 2)
     * */
    protected int possibleKingEscapesHorizontal(){
        int result = 2;

        // Check the left direction
        for (int j = kingPosition[1] - 1; j >= 0; j--){
            if (!(state.getPawn(kingPosition[0], j).equalsPawn("O"))){
                result--;
                break;
            }
        }

        // Check the right direction
        for (int j = kingPosition[1] + 1; j < state.getBoard().length; j++){
            if (!(state.getPawn(kingPosition[0], j).equalsPawn("O"))){
                result--;
                break;
            }
        }

        return result;
    }

    // Heuristic value
    public abstract double evaluateState();

}
