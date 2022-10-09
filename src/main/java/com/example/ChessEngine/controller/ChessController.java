package com.example.ChessEngine.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChessController {

    @GetMapping("/start")
    public String init() {
        return "X";
    }
}
