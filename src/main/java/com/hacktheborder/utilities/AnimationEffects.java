package com.hacktheborder.utilities;

import com.hacktheborder.Main;
import com.hacktheborder.controllers.*;

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
    public static Label scoreLabel = Main.gameController.questionScoreLabel;


    public static void animateScoreTimer() {
      
        score = 100;


        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(2), event -> {
                scoreLabel.setText("Question Score: " + score);
                score--;

            })
        );

        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely until stopped
        timeline.play(); 
      
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
