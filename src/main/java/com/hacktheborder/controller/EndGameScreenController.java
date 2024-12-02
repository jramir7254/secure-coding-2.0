package com.hacktheborder.controller;

import com.hacktheborder.Main;
import com.hacktheborder.ApplicationManager.TeamManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


@SuppressWarnings("exports")
public class EndGameScreenController {
    @FXML
    public VBox centerVBox;

    @FXML
    public Button printTeamDataButton;

  

    public void initialize() {
        
        centerVBox.prefWidthProperty().bind(Main.mainController.rootBorderPane.widthProperty().multiply(0.3));
        centerVBox.prefHeightProperty().bind(Main.mainController.rootBorderPane.heightProperty().multiply(0.7));

        printTeamDataButton.setOnAction(e -> TeamManager.printTeamData());
    }
}
