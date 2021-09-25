package com.example.demo.controller;

import com.example.demo.entities.Game;
import com.example.demo.entities.PlayTransaction;
import com.example.demo.models.GameResult;
import com.example.demo.models.PlayTransactionType;
import com.example.demo.service.GameService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public Game startGame(@RequestParam String playerName) {
        return gameService.startGame(playerName);
    }

    @PostMapping("/play")
    public PlayTransaction play(@RequestParam  PlayTransactionType playTransactionType, @RequestParam  Long gameId) {
        return gameService.play(playTransactionType, gameId);
    }

    @PostMapping("/finish")
    public GameResult finishGame(@RequestParam Long gameId) {
        return gameService.finish(gameId);
    }

}
