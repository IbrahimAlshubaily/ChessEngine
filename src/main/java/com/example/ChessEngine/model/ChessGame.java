package com.example.ChessEngine.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class ChessGame {
    HashMap<Position, String> pieces = new HashMap<>();
    public ChessGame() {
        pieces.put(new Position(0,0), "WhitePiece");
        pieces.put(new Position(3, 4), "GrayPiece");
        pieces.put(new Position(7,7), "BlackPiece");
    }
    public void addPiece(String name, int row, int col) {
        pieces.put(new Position(row,col), name);
    }
    public JSONArray getPieces() {
        JSONArray out = new JSONArray();
        for (Position pos : pieces.keySet()) {
            JSONObject currPiece = new JSONObject("{ name: "+pieces.get(pos) +", "+ pos + "}");
            out.put(currPiece);
        }
        return out;
    }
}
