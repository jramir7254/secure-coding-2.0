package com.hacktheborder.model;

public class Settings {
    private boolean isOnlineGame;
    private boolean isTimeLimitEnabled;
    private int defaultTimeLimit;
    private int scorePerQuestionAmount;
    private int penaltyAmount;
    private boolean isReplayEnabled;

    public Settings() {
        setDefaultSettings();
    }

    public void setDefaultSettings() {
        isOnlineGame = false;
        isTimeLimitEnabled = false;
        scorePerQuestionAmount = 100;
        penaltyAmount = 5;
        isReplayEnabled = true;
    }

    public void setOnlineSetting(boolean isOnline) {
        isOnlineGame = isOnline;
    }

    public void setTimeLimitSetting(boolean timeLimit, int timeAmount) {
        isTimeLimitEnabled = timeLimit;
        defaultTimeLimit = timeAmount;
    }

    public void setScoringSetting(int scorePerQuestion, int penaltyAmount) {
        scorePerQuestionAmount = scorePerQuestion;
        this.penaltyAmount = penaltyAmount;
    }

    public void setReplaySetting(boolean replayAllowed) {
        isReplayEnabled = replayAllowed;
    }

    public int getPointsPerQuestion() {
        return scorePerQuestionAmount;
    }

    public int getPenaltyAmount() {
        return penaltyAmount;
    }

    public boolean isTimeLimitEnabled() {
        return isTimeLimitEnabled;
    }

    public int getTimeLimit() {
        return defaultTimeLimit * 60;
    }

    public boolean isOnlineGame() {
        return isOnlineGame;
    }

    public String toString() {
        return "Online Game: " + isOnlineGame + 
               "\nTime Limit Enabled: " + isTimeLimitEnabled + 
               "\nTime Limit: " + defaultTimeLimit + 
               "\nPoints per Question: " + scorePerQuestionAmount + 
               "\nPenalty Amount: " + penaltyAmount + 
               "\nAllow Replay: " + isReplayEnabled;
    }
}
