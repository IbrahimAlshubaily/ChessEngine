package com.example.ChessEngine.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

public abstract class ChessPiece {
    private final Team team;
    private final Direction[] directions;
    private final int nSteps;
    private final String repr;
    private final int heuristicValue;

    public ChessPiece(Team team, String repr, Direction[] directions, int nSteps, int heuristicValue) {
        this.team = team;
        this.repr = repr;
        this.nSteps = nSteps;
        this.directions = directions;
        this.heuristicValue = heuristicValue;
    }

    public Stream<ChessBoardMove> getMoves(ChessBoard board, ChessBoardPosition currPosition){
        return Arrays.stream(this.directions)
                .flatMap((dir) -> getMovesInDirection(board, currPosition, dir));
    }
    private Stream<ChessBoardMove> getMovesInDirection(ChessBoard board, ChessBoardPosition currPosition, Direction dir){
        int nSteps = this.nSteps;
        ArrayList<ChessBoardMove> result = new ArrayList<>();
        ChessBoardMove currMove = new ChessBoardMove(currPosition, currPosition).step(dir);
        while (board.isValidMove(currMove) && nSteps-- > 0 ){
            result.add(currMove);
            if (board.isOpponent(currMove)) break;
            currMove = currMove.step(dir);
        }
        Collections.shuffle(result);
        return result.stream();
    }

    public Team getTeam() { return team; }
    public boolean isKing() { return repr.equalsIgnoreCase("King"); }
    public boolean isPawn() { return repr.equalsIgnoreCase("Pawn"); }
    public int getHeuristicValue() {
        return heuristicValue;
    }
    @Override
    public String toString() { return team.toString().toLowerCase()+'-'+ repr.toLowerCase(); }
}

class Queen extends ChessPiece{
    Queen(Team team){ super(team, "Queen", Direction.getQueenDirections(team), 8, 50); }
}
class King extends ChessPiece{
    King(Team team){ super(team, "King", Direction.getQueenDirections(team), 1, 1_000_000); }
}
class Knight extends ChessPiece{
    Knight(Team team){ super(team, "Knight", Direction.getKnightDirections(team), 1, 3); }
}
class Bishop extends ChessPiece{

    Bishop(Team team){ super(team, "Bishop", Direction.getBishopMoves(team), 8, 5); }

}
class Rook extends ChessPiece{
    Rook(Team team){ super(team, "Rook", Direction.getRookDirections(team), 8, 5); }
}
class Pawn extends ChessPiece{
    private final Direction[] directions;
    public Pawn(Team team){
        super(team, "Pawn", null, 2, 1);
        this.directions = Direction.getPawnDirections(team);
    }
    public Stream<ChessBoardMove> getMoves(ChessBoard board, ChessBoardPosition currPosition){
        ChessBoardMove baseMove = new ChessBoardMove(currPosition, currPosition);
        ArrayList<ChessBoardMove> result = getForwardMoves(board, baseMove, directions[0]);
        addDiagonalMove(board, baseMove.step(directions[1]), result);
        addDiagonalMove(board, baseMove.step(directions[2]), result);
        Collections.shuffle(result);
        return result.stream();
    }

    private ArrayList<ChessBoardMove> getForwardMoves(ChessBoard board, ChessBoardMove baseMove, Direction forward) {
        ArrayList<ChessBoardMove> result = new ArrayList<>(4);
        ChessBoardMove currMove = baseMove.step(forward);
        if (isValidForwardMove(board, currMove)){
            result.add(currMove);
            currMove = currMove.step(forward);
            if (isValidForwardMove(board, currMove))
                result.add(currMove);
        }
        return result;
    }

    private void addDiagonalMove(ChessBoard board, ChessBoardMove currMove, ArrayList<ChessBoardMove> result){
        if (isValidDiagonalMove(board, currMove)){
            result.add(currMove);
        }
    }

    private Boolean isValidForwardMove(ChessBoard board, ChessBoardMove currMove) {
        return board.isInBounds(currMove.destination) && board.isEmpty(currMove.destination);
    }
    private boolean isValidDiagonalMove(ChessBoard board, ChessBoardMove currMove){
        return  board.isInBounds(currMove.destination) && board.isOpponent(currMove);
    }

}