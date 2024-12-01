package com.hacktheborder;



import com.hacktheborder.utilities.QuestionManager;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.util.Duration;

public class ApplicationManager {
    public static QuestionManager questionManager;
    public static Question currentQuestion;

    public static void initialize() {
        questionManager = new QuestionManager();
        currentQuestion = questionManager.getNextQuestion();
    }


    public static String getCodeMirrorText() {
        return Main.gameController.getOutput();
    }


    public static void setupNextQuestion() {
        if(!questionManager.isEmpty()) {
            currentQuestion = questionManager.getNextQuestion();
            Main.gameController.displayMultipleChoice();
            Main.multipleChoiceController.resetButtons();
            Main.gameController.loadWebViewContent();
        } else {
            Main.mainController.addCenter(Main.endGame);
        }
    }


    public static void resetCodeArea() {
        Main.multipleChoiceController.displayEditableText();
    }


    
    public static String getSanitizedJavaCode() {
        return currentQuestion.getJavaCode()
        .replace("\\", "\\\\") 
        .replace("'", "\\'")   
        .replace("\n", "\\n")    
        .replace("\r", "");   
    }


    public static class AnimationEffects {
            public static void playShakeEffect(Node button, String color, boolean correct, boolean reset) {
                if (button instanceof Region) {
                    ((Region) button).setStyle("-fx-background-color: " + color + ";");
                }
                // Scale up transition
                ScaleTransition scaleUp = new ScaleTransition(Duration.millis(250), button);
                scaleUp.setToX(1.1); // Increase width by 10%
                scaleUp.setToY(1.1); // Increase height by 10%
    
                // Shake effect (left-right movement)
                TranslateTransition shake = new TranslateTransition(Duration.millis(50), button);
                if(!correct) {
                    shake.setByX(10); // Move 10px to the right
                    shake.setCycleCount(6); // Repeat 6 times
                    shake.setAutoReverse(true); // Move back and forth
                }
    
                // Scale down transition
                ScaleTransition scaleDown = new ScaleTransition(Duration.millis(150), button);
                scaleDown.setToX(1.0); // Return to normal width
                scaleDown.setToY(1.0); // Return to normal height
    
                if(reset) {
                    scaleDown.setOnFinished(e -> button.setStyle(""));
                }

                // Combine all transitions
                SequentialTransition sequentialTransition = new SequentialTransition(scaleUp, shake, scaleDown);
                sequentialTransition.play();
            }
    }
}
