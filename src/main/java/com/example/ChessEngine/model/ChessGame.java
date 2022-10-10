package com.example.ChessEngine.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;


public class ChessGame {
    private static final int MIN_MAX_SEARCH_DEPTH = 3;
    private final ChessBoard  chessBoard;
    private final MinMax playerTwo;

    public ChessGame() {

        chessBoard = new ChessBoard();
        //playerOne = new MinMax(Team.WHITE, MIN_MAX_SEARCH_DEPTH);
        playerTwo = new MinMax(Team.WHITE, MIN_MAX_SEARCH_DEPTH);
    }

    public boolean isGameOver() {
        return chessBoard.isGameOver();
    }
    public void reset() {
        chessBoard.reset();
    }

    public void move(ChessBoardMove move){
        if (!chessBoard.isValid(move)) {
            return;
        }
        chessBoard.move(move);
        minMaxStep();
    }

    public void minMaxStep() {
        chessBoard.move(playerTwo.getBestMove(chessBoard));
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

    public JSONArray getMoves(ChessBoardPosition position) {
        JSONArray out = new JSONArray();
        for (ChessBoardPosition destination : chessBoard.getMoves(position)) {
            JSONObject currPiece = new JSONObject("{"+destination+"}");
            out.put(currPiece);
        }
        return out;
    }
}
