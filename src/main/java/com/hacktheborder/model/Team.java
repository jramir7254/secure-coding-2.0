package com.hacktheborder.model;


import com.hacktheborder.utilities.TeamData;

public class Team {
    private String teamName;
    private int currentGameScore;
    private int teamHighScore;
    public TeamData currentTeamData;


    public Team(String teamName, int currentGameScore, int teamHighScore) {
        this.teamName = teamName;
        this.currentGameScore = currentGameScore;
        this.teamHighScore = teamHighScore;
        currentTeamData = new TeamData(this);
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


    public String toString() {
        String team = String.format("Team: %s  |  Game Score: %d  |  High Score: %d", teamName, currentGameScore, teamHighScore);
        return team + currentTeamData;
    }
}
