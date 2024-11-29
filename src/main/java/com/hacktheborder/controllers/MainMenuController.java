package com.hacktheborder.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainMenuController {
    @FXML
    public VBox center;

    @FXML
    public ImageView image;

    @FXML
    public TextField text;

    @FXML
    public Button button;

    public void bindComponentsToMainPane(BorderPane mainPane) {
        center.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.3));
        center.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.7));
    }
}
