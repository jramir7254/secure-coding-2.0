package com.hacktheborder.utilities;


import com.hacktheborder.ApplicationManager;
import com.hacktheborder.Main;
import com.hacktheborder.ApplicationManager.TeamManager;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class AnimationEffects {
    public static int score;
    public static int questionTimeInSeconds;
    public static int totalTimeInSeconds;
    public static Label scoreLabel = Main.gameController.questionScoreLabel;
    public static Label questionTimeLabel = Main.mainController.questionTimeLabel;
    public static Label totalTimeLabel = Main.mainController.totalTimeLabel;
    public static Label currentScore = Main.mainController.currentScoreLabel;
    public static Timeline questionScore;
    public static Timeline questionTimeTimeline;
    public static Timeline totalTimeTimeline;


    public static void updateTeamScore() {
        String newText = String.format("Current Score: %d +(%d)", TeamManager.getCurrentTeam().getCurrentGameScore(), score + 1);
        currentScore.setText(newText);
        playShakeEffect(currentScore, "#24e327", true, true);

    }


    public static void animateScoreTimer() {
      
        score = 100;


        questionScore = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                scoreLabel.setText("Question Score: " + score);
                score--;

            })
        );

        questionScore.setCycleCount(Timeline.INDEFINITE); // Run indefinitely until stopped
        questionScore.play(); 
      
    }


    public static void stopQuestionScoreTimeline() {
        questionScore.stop();
    }

    public static void startQuestionScoreTimeline() {
        questionScore.play();
    }

    public static void resetQuestionScoreTimeline() {
        score = 100;
    }





    public static void animateQuestionTime() {
      
        questionTimeInSeconds = 0;


        questionTimeTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                questionTimeLabel.setText("Question Time: " + formatTime(questionTimeInSeconds));
                questionTimeInSeconds++;

            })
        );

        questionTimeTimeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely until stopped
        questionTimeTimeline.play(); 
      
    }

    public static void stopQuestionTimer() {
        questionTimeTimeline.stop();
    }

    public static void startQuestionTimer() {
        questionTimeTimeline.play();
    }


    public static void resetQuestionTimer() {
        questionTimeInSeconds = 0; 
    }







    public static void animateTotalTime() {
      
        totalTimeInSeconds = 0;


        totalTimeTimeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                totalTimeLabel.setText("Total Time: " + formatTime(totalTimeInSeconds));
                totalTimeInSeconds++;

            })
        );

        totalTimeTimeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely until stopped
        totalTimeTimeline.play(); 
      
    }


    private static String formatTime(int seconds) {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }




    public static void wrongAnswerPenalty() {
        //playShakeEffect(scoreLabel, "#e3210b", false, true);
        score -= 10;
        scoreLabel.setText("Question Score: " + score + " (-10)");
        playShakeEffect(scoreLabel, "#e3210b", false, true);

    }
   


    public static void playShakeEffect(Node node, String color, boolean correct, boolean reset) {
        if (node instanceof ToggleButton) {
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
        if(!correct) {
            shake.setByX(10); // Move 10px to the right
            shake.setCycleCount(6); // Repeat 6 times
            shake.setAutoReverse(true); // Move back and forth
        }

        // Scale down transition
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(150), node);
        scaleDown.setToX(1.0); // Return to normal width
        scaleDown.setToY(1.0); // Return to normal height

        if(reset) {
            scaleDown.setOnFinished(e -> node.setStyle(""));
        }

        // Combine all transitions
        SequentialTransition sequentialTransition = new SequentialTransition(scaleUp, shake, scaleDown);
        sequentialTransition.play();
    }
}
