package com.example.demo.models;

public class GameResult {

    private int transactionsCount;
    private int winCount;
    private int failCount;
    private int equalsCount;
    private WinningStatus finalStatus;

    public int getTransactionsCount() {
        return transactionsCount;
    }

    public void setTransactionsCount(int transactionsCount) {
        this.transactionsCount = transactionsCount;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        this.failCount = failCount;
    }

    public int getEqualsCount() {
        return equalsCount;
    }

    public void setEqualsCount(int equalsCount) {
        this.equalsCount = equalsCount;
    }

    public WinningStatus getFinalStatus() {
        return finalStatus;
    }

    public void setFinalStatus(WinningStatus finalStatus) {
        this.finalStatus = finalStatus;
    }
}
