package com.hacktheborder.controller;

import com.hacktheborder.utilities.AnimationEffects;
import com.hacktheborder.ApplicationManager;
import com.hacktheborder.Main;
import com.hacktheborder.ApplicationManager.AnimationManager;
import com.hacktheborder.ApplicationManager.GameControllerManager;
import com.hacktheborder.ApplicationManager.QuestionManager;
import com.hacktheborder.ApplicationManager.TeamManager;
import com.hacktheborder.model.Question;
import com.hacktheborder.utilities.FileManager;
import com.hacktheborder.utilities.ThreadRunner;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


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


    public void setTextToDebug(String expectedOutput) {
        System.out.println(expectedOutput);
        String s = "Expected Output: \n" + expectedOutput;

        consoleOutputTextArea.setPromptText(s);
    }



    public void nextQuestion() {
        TeamManager.addQuestionData();
        runButton.setStyle("");
        consoleOutputTextArea.setText(null);
        nextQuestionButton.setVisible(false);
        runButton.setDisable(false);
        resetButton.setDisable(false);
        ApplicationManager.setupNextQuestion();
       
    }






    public void reset() {
        consoleOutputTextArea.setText(null);
        GameControllerManager.resetCodeArea();
    }












    public void writeAndRun() {
        try {
            String javaCodeToWrite = GameControllerManager.getCodeMirrorText();
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
                AnimationManager.animateCorrectAnswerChoice(runButton);
                QuestionManager.updateDebuggingAttempts(javaCodeToWrite, true);
                TeamManager.updateTeamScore();
                nextQuestionButton.setVisible(true);
                runButton.setDisable(true);
                resetButton.setDisable(true);
                
            } else {
                QuestionManager.updateDebuggingAttempts(javaCodeToWrite, false);
                AnimationEffects.wrongAnswerPenalty();
                AnimationManager.animateWrongAnswerChoice(runButton);
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
