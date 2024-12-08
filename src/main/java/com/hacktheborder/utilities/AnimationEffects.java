package com.hacktheborder.utilities;



import com.hacktheborder.Main;
import com.hacktheborder.model.Settings;
import com.hacktheborder.ApplicationManager.TeamManager;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class AnimationEffects {
    private static int score;
    private static int points;
    private static int questionTimeInSeconds;
    private static int totalTimeInSeconds;
    private static int penaltyAmount;
    private static int changeInTime;

    public static Label scoreLabel = Main.gameController.questionScoreLabel;
    public static Label questionTimeLabel = Main.mainController.questionTimeLabel;
    public static Label totalTimeLabel = Main.mainController.totalTimeLabel;
    public static Label currentScore = Main.mainController.currentScoreLabel;

    private static Timeline questionScore;
    private static Timeline questionTimeTimeline;
    private static Timeline totalTimeTimeline;


    public static void animateUpdatedTeamScore(int newScore) {
        String newText = String.format("Current Score: %d +(%d)", newScore, points + 1);
        currentScore.setText(newText);
        playShakeEffect(currentScore, "#24e327", true, true);

    }


    public static int getCurrentQuestionScore() {
        return points;
    }


    public static void initialize(Settings settings) {
        score = settings.getPointsPerQuestion();
        points = score;
        penaltyAmount = settings.getPenaltyAmount();
        questionTimeInSeconds = 0;
        totalTimeInSeconds = settings.getTimeLimit();

        if(settings.isTimeLimitEnabled()) {
            changeInTime = -1;
        } else {
            changeInTime = 1;
        }


        questionScore = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                scoreLabel.setText("Question Score: " + ( points-- ));
            })
        );
        questionScore.setCycleCount(Timeline.INDEFINITE); 


        questionTimeTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                questionTimeLabel.setText("Question Time: " + formatTime( questionTimeInSeconds++ ));
            })
        );
        questionTimeTimeline.setCycleCount(Timeline.INDEFINITE); 


        totalTimeTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                totalTimeLabel.setText("Total Time: " + formatTime( totalTimeInSeconds ));
                totalTimeInSeconds += changeInTime;
            })
        );
        totalTimeTimeline.setCycleCount(Timeline.INDEFINITE); 
    }


    public static void startAllTimelines() {
        questionTimeTimeline.play();
        questionScore.play();
        totalTimeTimeline.play();
    }



    public static void stopAllTimelines() {
        questionTimeTimeline.stop();
        questionScore.stop();
        totalTimeTimeline.stop();
    }

    public static void stopAndResetQuestionAndScoreTimelines() {
        questionTimeTimeline.stop();
        questionScore.stop();
        points = score;
        questionTimeInSeconds = 0; 
    }



    public static void stopQuestionScoreTimeline() {
        questionScore.stop();
    }


    public static void startQuestionScoreTimeline() {
        currentScore.setText("Current Score: " + TeamManager.getCurrentTeam().getCurrentGameScore());
        scoreLabel.setText("Question Score: " + score);
        questionScore.jumpTo(Duration.seconds(1));
        questionScore.play();
    }


    public static void resetQuestionScoreTimeline() {
        points = score;
    }






    public static void stopQuestionTimer() {
        questionTimeTimeline.stop();
    }

    public static void startQuestionTimer() {
        questionTimeLabel.setText("Question Time: 00:00");
        questionTimeTimeline.jumpTo(Duration.seconds(1));
        questionTimeTimeline.play();
    }

    public static void resetQuestionTimer() {
        questionTimeInSeconds = 0; 
    }



    private static String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }


    public static void wrongAnswerPenalty() {
        points -= penaltyAmount;
        String s = String.format("Question Score: %d (-%d)", points, penaltyAmount);
        scoreLabel.setText(s);
        playShakeEffect(scoreLabel, "#e3210b", false, true);

    }
   


    public static void playShakeEffect(Node node, String color, boolean correct, boolean reset) {
        if (node instanceof ToggleButton || node instanceof Button) {
            ((Region) node).setStyle("-fx-background-color: " + color + ";");
        }

        if (node instanceof Label) {
            ((Region) node).setStyle("-fx-text-fill: " + color + ";");
        }
        // Scale up transition
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(250), node);
        scaleUp.setToX(1.1); // Increase width by 10%
        scaleUp.setToY(1.1); // Increase height by 10%

        // Shake effect (left-right movement)
        TranslateTransition shake = new TranslateTransition(Duration.millis(50), node);
        if (!correct) {
            shake.setByX(10); // Move 10px to the right
            shake.setCycleCount(6); // Repeat 6 times
            shake.setAutoReverse(true); // Move back and forth
        }

        // Scale down transition
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(150), node);
        scaleDown.setToX(1.0); // Return to normal width
        scaleDown.setToY(1.0); // Return to normal height

        if (reset) {
            scaleDown.setOnFinished(e -> node.setStyle(""));
        }

        // Combine all transitions
        SequentialTransition sequentialTransition = new SequentialTransition(scaleUp, shake, scaleDown);
        sequentialTransition.play();
    }
}
