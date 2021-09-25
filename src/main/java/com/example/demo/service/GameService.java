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
    public PlayTransaction play(PlayTransactionType playTransactionType, Long gameId) {
        PlayTransaction playTransaction = new PlayTransaction();
        playTransaction.setDate(new Date());
        playTransaction.setUserPlayType(playTransactionType);
        Game game = new Game();
        game.setId(gameId);
        playTransaction.setGame(game);
        // generate random play to computer
        Random random = new Random();
        int randomInt = random.nextInt(3) + 1;
        PlayTransactionType computerPlay = PlayTransactionType.values()[randomInt];
        playTransaction.setComputerPlayType(computerPlay);
        // decide the winner

        playTransaction = playTransactionRepository.save(playTransaction);
        return playTransaction;
    }

    public GameResult finish(Long gameId) {
        // update game Status to Finished
        gameRepository.updateStatusById(GameStatus.FINISHED, gameId);
        GameResult gameResult = new GameResult();
        // get total result of transactions for user
        Map<String,Long> resultMap = playTransactionRepository.getFinalResultByGameId(gameId);
        resultMap.forEach((s, count) -> {
            if (WinningStatus.WIN.toString().equals(s)) {
                gameResult.setWinCount(count.intValue());
            } else if (WinningStatus.FAIL.toString().equals(s)) {
                gameResult.setFailCount(count.intValue());
            } else if (WinningStatus.EQUAL.toString().equals(s)) {
                gameResult.setEqualsCount(count.intValue());
            }
            gameResult.setTransactionsCount(gameResult.getWinCount() + gameResult.getFailCount() + gameResult.getEqualsCount());
            if(gameResult.getWinCount() > gameResult.getFailCount()) {
                gameResult.setFinalStatus(WinningStatus.WIN);
            }
        });
        return gameResult;
    }

}
