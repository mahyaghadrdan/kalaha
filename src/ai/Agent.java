/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import kalaha.GameState;

/**
 *
 * @author NT
 */
public class Agent {

    public static final int DEPTH = 5;
    private int MIN = 1;
    private int MAX = 2;

    public Agent(int player) {
        if (player == 1) {
            MAX = 1;
            MIN = 2;
        }
    }

    public int getBestMove(GameState currGameState, int currentDepth) {
        int bestMove = -1;
        int bestScoreAtDepth = -1000;
        
        int player = currGameState.getNextPlayer();

        if (currGameState.gameEnded() || currentDepth > DEPTH) {
            return evaluate(currGameState, player);
        }

        for (int i = 1; i <= 6; i++) {

            if (currGameState.moveIsPossible(i)) {

                GameState clone = currGameState.clone();
                clone.makeMove(i);

                int moveScore = getBestMove(clone, currentDepth + 1);

                if (player == MAX && bestScoreAtDepth < moveScore) {
                    bestScoreAtDepth = moveScore;
                    bestMove = i;
                }

                if (player == MIN && moveScore < bestScoreAtDepth) {
                    bestScoreAtDepth = moveScore;
                    bestMove = i;
                }
            }
        }

        return bestMove;
    }

    public int evaluate(GameState currentGameState, int player) {
        return currentGameState.getScore(player == MIN ? MAX : MIN);
    }
}
