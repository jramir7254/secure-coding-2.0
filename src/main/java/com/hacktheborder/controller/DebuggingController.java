package com.hacktheborder.controller;

import com.hacktheborder.utilities.AnimationEffects;
import com.hacktheborder.ApplicationManager;
import com.hacktheborder.Main;
import com.hacktheborder.ApplicationManager.QuestionManager;
import com.hacktheborder.model.Question;
import com.hacktheborder.utilities.FileManager;
import com.hacktheborder.utilities.ThreadRunner;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


@SuppressWarnings("exports")
public class DebuggingController {

    @FXML
    public VBox centerVBox;

    @FXML
    public TextArea consoleOutputTextArea;

    @FXML 
    public HBox buttonHBoxContainer;

    @FXML
    public Button runButton;

    @FXML 
    public Button resetButton;

    @FXML 
    public Button nextQuestionButton;

    







    public void initialize() {

        centerVBox.prefWidthProperty().bind(Main.gameController.centerVBox.widthProperty().multiply(0.7));
        centerVBox.prefHeightProperty().bind(Main.gameController.centerVBox.heightProperty().multiply(0.35));


        consoleOutputTextArea.prefWidthProperty().bind(centerVBox.widthProperty());
        consoleOutputTextArea.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.7));


        HBox.setMargin(runButton, new Insets(20));
        runButton.prefWidthProperty().bind(centerVBox.widthProperty().multiply(0.35));    
        runButton.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.015)); 
        runButton.setOnAction(e -> writeAndRun());


        HBox.setMargin(resetButton, new Insets(20));
        resetButton.prefWidthProperty().bind(centerVBox.widthProperty().multiply(0.35));    
        resetButton.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.015)); 
        resetButton.setOnAction(e -> reset());


        nextQuestionButton.setOnAction(e -> nextQuestion());

    }




    public void nextQuestion() {
        runButton.setStyle("");
        consoleOutputTextArea.setText(null);
        nextQuestionButton.setVisible(false);
        ApplicationManager.setupNextQuestion();
        ApplicationManager.start();
    }






    public void reset() {
        ApplicationManager.resetCodeArea();
    }












    public void writeAndRun() {
        try {
            String javaCodeToWrite = ApplicationManager.getCodeMirrorText();
            FileManager writer = new FileManager();
            writer.writeToFile(javaCodeToWrite);
            ThreadRunner runner = new ThreadRunner(writer.getFile());

            runner.start();
            System.out.println("Thread Started\n");
            runner.join();
            System.out.println("Waiting on Thread\n");

            consoleOutputTextArea.setText(runner.getRunProcessOutput());
            writer.deleteFile();


            if(validateOutput()) {
                ApplicationManager.updateTeamScore();
                nextQuestionButton.setVisible(true);
                AnimationEffects.playShakeEffect(runButton, "#24e327", true, false);
            } else {
                AnimationEffects.wrongAnswerPenalty();
                AnimationEffects.playShakeEffect(runButton, "#e3210b", false, true);
            }


            if (!runner.isAlive()) {
                System.out.println("ThreadRunner has terminated successfully.\n");
            } else {
                System.err.println("ThreadRunner is still alive.\n");
            }
        } catch (Exception e) {

        }

    }










    public boolean validateOutput() {
        Question currQuestion = QuestionManager.getCurrentQuestion();
        return consoleOutputTextArea.getText().trim().equals(currQuestion.getExpectedOutput());
    }
}
