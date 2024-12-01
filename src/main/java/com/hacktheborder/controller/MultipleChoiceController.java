package com.hacktheborder.controller;


import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hacktheborder.ApplicationManager;
import com.hacktheborder.Main;
import com.hacktheborder.ApplicationManager.AnimationManager;
import com.hacktheborder.ApplicationManager.QuestionManager;
import com.hacktheborder.model.Question;
import com.hacktheborder.utilities.AnimationEffects;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;


@SuppressWarnings("exports")
public class MultipleChoiceController {
    @FXML
    public VBox buttonVBoxContainer;

    @FXML
    private ToggleGroup multipleChoiceSelection;

    @FXML
    public Button submitButton;

    public Button nextSectionButton;








    public void initialize() {
        buttonVBoxContainer.prefWidthProperty().bind(Main.gameController.centerVBox.widthProperty().multiply(0.7));
        buttonVBoxContainer.prefHeightProperty().bind(Main.gameController.centerVBox.heightProperty().multiply(0.35));
        setButtonLayout();
    }












    private void setButtonLayout() {

        for (Node node : buttonVBoxContainer.getChildren()) {
            if (node instanceof ToggleButton) {
                ToggleButton button = (ToggleButton) node; 
                button.prefWidthProperty().bind(Main.gameController.centerVBox.widthProperty().multiply(0.5));      
                button.prefHeightProperty().bind(Main.gameController.centerVBox.heightProperty().multiply(0.015));   
                VBox.setMargin(button, new Insets(10, 0, 10, 0));
            }
        }

        nextSectionButton = new Button("Next Section");
        nextSectionButton.getStyleClass().add("buttons");
        VBox.setMargin(nextSectionButton, new Insets(20, 0, 5, 0));
        nextSectionButton.prefWidthProperty().bind(Main.gameController.centerVBox.widthProperty().multiply(0.5));    
        nextSectionButton.prefHeightProperty().bind(Main.gameController.centerVBox.heightProperty().multiply(0.015)); 

        nextSectionButton.setOnAction(e -> nextSection());

        submitButton.prefWidthProperty().bind(Main.gameController.centerVBox.widthProperty().multiply(0.5));    
        submitButton.prefHeightProperty().bind(Main.gameController.centerVBox.heightProperty().multiply(0.015));   
        submitButton.setOnAction(e -> validateAnswer());
        VBox.setMargin(submitButton, new Insets(20, 0, 5, 0));

    }






    public void resetButtons() {
        for (Node node : buttonVBoxContainer.getChildren()) {
            if (node instanceof ToggleButton) {
                ToggleButton button = (ToggleButton) node; 
                button.setStyle("");    
            }
        
        }
        buttonVBoxContainer.getChildren().remove(nextSectionButton);
        buttonVBoxContainer.getChildren().add(submitButton);
        multipleChoiceSelection.selectToggle(null);
    }



    public void nextSection() {
        ApplicationManager.displayDebuggingPanel();
    }



    public void validateAnswer() {
        if (multipleChoiceSelection.getSelectedToggle() == null) {
            return;
        }

    
        ToggleButton selectedButton = (ToggleButton) multipleChoiceSelection.getSelectedToggle();
        
        if (!selectedButton.getText().equals(QuestionManager.getCurrentQuestionAnswer())) {
            AnimationManager.animateWrongAnswerChoice(selectedButton);
            shuffleMultipleChoiceButtons();
            

        } else {
            ApplicationManager.updateTeamScore();
            AnimationManager.animateCorrectAnswerChoice(selectedButton);
            buttonVBoxContainer.getChildren().remove(submitButton);
            buttonVBoxContainer.getChildren().add(nextSectionButton);
        }

        multipleChoiceSelection.selectToggle(null);
        
    }


















    public void shuffleMultipleChoiceButtons() {
        List<ToggleButton> toggles = new ArrayList<>();

        for (var node : buttonVBoxContainer.getChildren()) {
            if (node instanceof ToggleButton) {
                toggles.add((ToggleButton) node);
            }
        }

        // Shuffle the toggles
        Collections.shuffle(toggles);

        // Get the index of the submit button
        int submitIndex = buttonVBoxContainer.getChildren().indexOf(submitButton);

        // Clear the VBox and re-add shuffled toggles
        buttonVBoxContainer.getChildren().clear();
        buttonVBoxContainer.getChildren().addAll(toggles);

        // Add the submit button back at the same position
        buttonVBoxContainer.getChildren().add(submitIndex, submitButton);
    }





}



















