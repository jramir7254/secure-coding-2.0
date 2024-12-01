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

}
