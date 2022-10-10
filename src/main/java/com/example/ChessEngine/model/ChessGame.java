package com.example.ChessEngine.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ChessGame {
    private static final int MIN_MAX_SEARCH_DEPTH = 3;
    private final ChessBoard  chessBoard;
    private final MinMax playerOne;
    private final MinMax playerTwo;

    private Team turn;

    public ChessGame() {

        chessBoard = new ChessBoard();

        turn = Team.WHITE;
        playerOne = new MinMax(Team.WHITE, MIN_MAX_SEARCH_DEPTH);
        playerTwo = new MinMax(Team.BLACK, MIN_MAX_SEARCH_DEPTH);
    }

    public boolean isGameOver() {
        return chessBoard.isGameOver();
    }
    public void reset() {
        chessBoard.reset();
    }

    public boolean move(ChessBoardMove move, Team turn){
        if (chessBoard.isValidMove(move, turn)) {
            chessBoard.move(move);
            return true;
        }
        return false;
    }

    public void minMaxStep() {
        if (isGameOver()) return;
        MinMax agent = turn == playerOne.team ? playerOne : playerTwo;
        turn = turn.getOpponent();
        chessBoard.move(agent.getBestMove(chessBoard));
    }

    public JSONArray getPieces() {
        Map<ChessBoardPosition, ChessPiece> pieces = chessBoard.getPieces();
        JSONArray out = new JSONArray();
        for (ChessBoardPosition pos : pieces.keySet()) {
            JSONObject currPiece = new JSONObject("{ name: " + pieces.get(pos) +", "+ pos + "}");
            out.put(currPiece);
        }
        return out;
    }
}
