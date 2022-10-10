package com.example.ChessEngine.controller;

import com.example.ChessEngine.model.ChessGame;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ChessController {
    ChessGame currGame = new ChessGame();

    @GetMapping("/")
    public ResponseEntity<String> getPieces() {
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(currGame.getPieces().toString(), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/", produces = "application/json")
    public ResponseEntity<String> movePiece(@RequestBody String move) {
        String[] input =  move.split("-");
        int row = Integer.parseInt(input[0]);
        int col = Integer.parseInt(input[1]);
        //currGame.addPiece("newPiece", row, col);
        currGame.minMaxStep();
        return new ResponseEntity<>(move, HttpStatus.CREATED);
    }
}
