package com.hacktheborder.model;

public class Team {
    private String teamName;
    private int currentGameScore;
    private int teamHighScore;


    public Team(String teamName, int currentGameScore, int teamHighScore) {
        this.teamName = teamName;
        this.currentGameScore = currentGameScore;
        this.teamHighScore = teamHighScore;
    }

    public void updateCurrentScore(int newScore) {
        currentGameScore += newScore + 1;
    }

    public String getTeamName() {
        return this.teamName;
    }

    public int getCurrentGameScore() {
        return this.currentGameScore;
    }

    public int getTeamHighScore() {
        return this.teamHighScore;
    }
}
