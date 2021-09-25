package com.example.demo.service;

import com.example.demo.entities.Game;
import com.example.demo.entities.PlayTransaction;
import com.example.demo.models.GameResult;
import com.example.demo.models.GameStatus;
import com.example.demo.models.PlayTransactionType;
import com.example.demo.models.WinningStatus;
import com.example.demo.repository.GameRepository;
import com.example.demo.repository.PlayTransactionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class GameService {

    private GameRepository gameRepository;
    private PlayTransactionRepository playTransactionRepository;

    public GameService(GameRepository gameRepository, PlayTransactionRepository playTransactionRepository) {
        this.gameRepository = gameRepository;
        this.playTransactionRepository = playTransactionRepository;
    }

    @Transactional
    public Game startGame(String playerName) {
        Game game = new Game();
        game.setStatus(GameStatus.STARTED);
        game.setDate(new Date());
        game.setPlayerName(playerName);
        game = gameRepository.save(game);
        return game;
    }

    @Transactional
    public PlayTransaction play(PlayTransactionType userPlay, Long gameId) {
        PlayTransaction playTransaction = new PlayTransaction();
        playTransaction.setDate(new Date());
        playTransaction.setUserPlayType(userPlay);
        Game game = new Game();
        game.setId(gameId);
        playTransaction.setGame(game);
        // generate random play to computer
        int randomInt = getRandomNumber();
        PlayTransactionType computerPlay = PlayTransactionType.values()[randomInt];
        playTransaction.setComputerPlayType(computerPlay);
        // decide the winner
        WinningStatus winningStatus = checkUserWinningStatus(userPlay, computerPlay);
        playTransaction.setWinningStatus(winningStatus);
        playTransaction = playTransactionRepository.save(playTransaction);
        return playTransaction;
    }

    @Transactional
    public GameResult finish(Long gameId) {
        // update game Status to Finished
        gameRepository.updateStatusById(GameStatus.FINISHED, gameId);
        GameResult gameResult = new GameResult();
        // get total result of transactions for user
        List<Object[]> resultMap = playTransactionRepository.getFinalResultByGameId(gameId);
        resultMap.forEach((object) -> {
            if (WinningStatus.WIN.toString().equals(object[0]+"")) {
                gameResult.setWinCount(((Long) object[1]).intValue());
            } else if (WinningStatus.FAIL.toString().equals(object[0]+"")) {
                gameResult.setFailCount(((Long) object[1]).intValue());
            } else if (WinningStatus.EQUAL.toString().equals(object[0]+"")) {
                gameResult.setEqualsCount(((Long) object[1]).intValue());
            }
            gameResult.setTransactionsCount(gameResult.getWinCount() + gameResult.getFailCount() + gameResult.getEqualsCount());
            if(gameResult.getWinCount() > gameResult.getFailCount()) {
                gameResult.setFinalStatus(WinningStatus.WIN);
            } else {
                gameResult.setFinalStatus(WinningStatus.FAIL);
            }
        });
        return gameResult;
    }

    public WinningStatus checkUserWinningStatus(PlayTransactionType userPlayTransactionType, PlayTransactionType computerPlayTransactionType) {
        if(userPlayTransactionType.equals(computerPlayTransactionType)) {
            return WinningStatus.EQUAL;
        } else if(userPlayTransactionType.equals(PlayTransactionType.ROCK)) {
            if(computerPlayTransactionType.equals(PlayTransactionType.PAPER)) {
                return WinningStatus.WIN;
            } else {
                return WinningStatus.FAIL;
            }
        } else if(userPlayTransactionType.equals(PlayTransactionType.PAPER)) {
            if(computerPlayTransactionType.equals(PlayTransactionType.SCISSORS)) {
                return WinningStatus.WIN;
            } else {
                return WinningStatus.FAIL;
            }
        } else if(userPlayTransactionType.equals(PlayTransactionType.SCISSORS)) {
            if(computerPlayTransactionType.equals(PlayTransactionType.ROCK)) {
                return WinningStatus.WIN;
            } else {
                return WinningStatus.FAIL;
            }
        }
        return null;
    }

    public int getRandomNumber() {
        return (int) (Math.random() * (3));
    }

}
