package com.hacktheborder.controller;


import com.hacktheborder.ApplicationManager;
import com.hacktheborder.Main;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


@SuppressWarnings("exports")

public class MainMenuController {

    @FXML
    public VBox centerVBox;

    @FXML
    public VBox stuff;

    @FXML
    public StackPane stackPane;

    

    @FXML
    public ImageView coverImage;

    @FXML
    public TextField teamNameTextField;

    @FXML
    public Button submitButton;

    @FXML
    public ToggleButton settingsButton;


    public void initialize() {
        bindComponentsToMainPane(Main.mainController.rootBorderPane);
    }


    public void bindComponentsToMainPane(BorderPane mainPane) {
  
        centerVBox.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.3));
        centerVBox.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.7));

  

        teamNameTextField.prefWidthProperty().bind(centerVBox.widthProperty().multiply(0.5));

    
        settingsButton.setOnAction(event -> method());


        teamNameTextField.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.05));

        VBox.setMargin(submitButton, new Insets(10, 0, 10, 0));

    }


    public void method() {
        if(settingsButton.isSelected()) {
            stackPane.getChildren().setAll(stuff, ApplicationManager.SETTINGS);
        } else {
            stackPane.getChildren().setAll(ApplicationManager.SETTINGS, stuff);
        }
    }


    public String getTeamNameTextFieldText() {
        return teamNameTextField.getText();
    }


    public void submit() {
        ApplicationManager.onMainMenuSubmitButtonPressed();
        teamNameTextField.setText(null);
    }
}
