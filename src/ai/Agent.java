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

    public static int DEPTH = 20;
    private int MIN = 1;
    private int MAX = 2;
    private long stopTime;
    private int bestMove = -1;

    public Agent(int player, long stoptime) {
        if (player == 1) {
            MAX = 1;
            MIN = 2;
        }

        this.stopTime = stoptime;
    }

    public int getBestMove(GameState currGameState, int currentDepth, int maxDepth) {

        int bestScoreAtDepth = -1000;
        int bestMove = -1;

        int player = currGameState.getNextPlayer();

        if (currGameState.gameEnded() || currentDepth > maxDepth) {
            return evaluate(currGameState, player);
        }

        long currentTime = System.currentTimeMillis();

        if ((this.stopTime - currentTime) > 0) {

            while (maxDepth < DEPTH) {
                System.out.println("Current depth is " + (currentDepth));

                for (int i = 1; i <= 6; i++) {

                    if (currGameState.moveIsPossible(i)) {

                        GameState clone = currGameState.clone();
                        clone.makeMove(i);

                        int moveScore = getBestMove(clone, currentDepth + 1, maxDepth);

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

                this.bestMove = bestMove;
                maxDepth++;
            }
        }

        return this.bestMove;
    }
 
    public int evaluate(GameState currentGameState, int player) {
        return currentGameState.getScore(player == MIN ? MAX : MIN);
    }
}
