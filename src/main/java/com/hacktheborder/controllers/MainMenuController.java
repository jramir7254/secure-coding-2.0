package com.hacktheborder.controllers;

import com.hacktheborder.Main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainMenuController implements CenterController {

    @FXML
    public VBox centerVBox;

    @FXML
    public ImageView coverImage;

    @FXML
    public TextField teamNameTextField;

    @FXML
    public Button submitButton;


    public void bindComponentsToMainPane(BorderPane mainPane) {
  
        centerVBox.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.3));
        centerVBox.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.7));

        teamNameTextField.prefWidthProperty().bind(centerVBox.widthProperty().multiply(0.5));
        teamNameTextField.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.05));

        VBox.setMargin(submitButton, new Insets(10, 0, 10, 0));

    }


    public void submit() {
        try {

            
            Main.mainController.addCenter("/com/hacktheborder/GameContainer.fxml");
            System.out.println("added center");

        } catch (Exception e) {
            System.err.println("Exception Message from submit(): " + e.getMessage());
        }
    }
}
