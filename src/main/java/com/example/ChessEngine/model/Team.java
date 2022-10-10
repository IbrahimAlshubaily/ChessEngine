package com.example.ChessEngine.model;

public enum Team{
    WHITE,
    BLACK,
    ;
    public Team getOpponent() {
        return this == Team.WHITE ? Team.BLACK : Team.WHITE;
    }

}

