package com.example.ChessEngine.controller;

import com.example.ChessEngine.model.ChessBoardMove;
import com.example.ChessEngine.model.ChessBoardPosition;
import com.example.ChessEngine.model.ChessGame;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class ChessController {
    ChessGame currGame = new ChessGame();

    @GetMapping("/pieces")
    public ResponseEntity<String> getPieces() {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(currGame.getPieces().toString(), headers, HttpStatus.CREATED);
    }

    @GetMapping(value = "/moves{position}")
    public ResponseEntity<String> moves(@PathVariable String position) {
        HttpHeaders headers = new HttpHeaders();
        ChessBoardPosition pos = parsePosition(position);
        return new ResponseEntity<>(currGame.getMoves(pos).toString(), headers, HttpStatus.CREATED);
    }


    @RequestMapping(value = "/move", produces = "application/json")
    public ResponseEntity<String> movePiece(@RequestBody String body) {

        String[] inputs =  body.strip().split(",");
        ChessBoardPosition source = parsePosition(inputs[0]);
        ChessBoardPosition destination = parsePosition(inputs[1]);
        ChessBoardMove move = new ChessBoardMove(source, destination);
        currGame.move(move);
        return new ResponseEntity<>("Moved from "+source+ " to "+destination, HttpStatus.CREATED);
    }

    private ChessBoardPosition parsePosition(String position){
        String[] input =  position.split("-");
        int row = Integer.parseInt(input[0]);
        int col = Integer.parseInt(input[1]);
        return new ChessBoardPosition(row, col);

    }
}
