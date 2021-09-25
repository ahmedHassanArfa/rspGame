package com.example.demo.entities;

import com.example.demo.models.PlayTransactionType;
import com.example.demo.models.WinningStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class PlayTransaction {

    @Id
    @GeneratedValue
    private Long id;
    private Date date;
    private PlayTransactionType userPlayType;
    private PlayTransactionType computerPlayType;
    private WinningStatus winningStatus;

    @ManyToOne
    private Game game;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PlayTransactionType getUserPlayType() {
        return userPlayType;
    }

    public void setUserPlayType(PlayTransactionType userPlayType) {
        this.userPlayType = userPlayType;
    }

    public PlayTransactionType getComputerPlayType() {
        return computerPlayType;
    }

    public void setComputerPlayType(PlayTransactionType computerPlayType) {
        this.computerPlayType = computerPlayType;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public WinningStatus getWinningStatus() {
        return winningStatus;
    }

    public void setWinningStatus(WinningStatus winningStatus) {
        this.winningStatus = winningStatus;
    }

}
