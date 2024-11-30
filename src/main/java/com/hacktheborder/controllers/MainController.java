package com.hacktheborder.controllers;


import java.io.File;

import com.hacktheborder.utilities.MyFileReader;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;

import javafx.scene.control.Label;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;



public class MainController {
    @FXML
    public BorderPane rootBorderPane;

    @FXML
    public GridPane headerGridPane;

    @FXML
    public VBox leaderboardVBox;

    @FXML
    public VBox informationVBox;

    @FXML
    public Label infoLabel;

    @FXML
    public GridPane informationGridPaneContainer;

    @FXML
    public WebView informationWebView;


    public void initialize() {

        BorderPane.setMargin(headerGridPane, new Insets(0, 0, 20, 0));
        headerGridPane.prefWidthProperty().bind(rootBorderPane.widthProperty().multiply(1));
        headerGridPane.prefHeightProperty().bind(rootBorderPane.heightProperty().multiply(0.05));

        leaderboardVBox.prefWidthProperty().bind(rootBorderPane.widthProperty().multiply(0.3));
        leaderboardVBox.prefHeightProperty().bind(rootBorderPane.heightProperty().multiply(0.7));
    
        informationVBox.prefWidthProperty().bind(rootBorderPane.widthProperty().multiply(0.3));
        informationVBox.prefHeightProperty().bind(rootBorderPane.heightProperty().multiply(0.7));

        informationGridPaneContainer.prefWidthProperty().bind(informationVBox.widthProperty().multiply(0.75));
        informationGridPaneContainer.prefHeightProperty().bind(informationVBox.heightProperty().multiply(0.9));

        informationWebView.prefWidthProperty().bind(informationGridPaneContainer.widthProperty());
        informationWebView.prefHeightProperty().bind(informationGridPaneContainer.heightProperty());

        m();
        addCenter("/com/hacktheborder/MainMenu.fxml");

    }

    public void m() {
        try {

            WebEngine webEngine = informationWebView.getEngine();

            File file = new File("secure-coding\\src\\main\\resources\\com\\hacktheborder\\information.html");

            if (file.exists()) {

                webEngine.load(file.toURI().toString());
                System.out.println("Loaded file: " + file.toURI());
            }

        } catch (Exception e) {
            System.err.println("Exception message from loadWebViewContent: " + e.getMessage());
        }
    }

    public void displayInfo() {
        if(informationWebView.isVisible()) {
            informationWebView.setVisible(false);
        } else {
            informationWebView.setVisible(true);
        }
    }




    public void addCenter(String pathName) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(pathName));
            Node newCenter = loader.load();

            // Set the new center in the BorderPane
            rootBorderPane.setCenter(newCenter);
    
            // Access the controller and bind to mainPane if applicable
            Object controller = loader.getController();
            if (controller instanceof CenterController) {
                ((CenterController) controller).bindComponentsToMainPane(rootBorderPane);
            }

            System.out.println("Added center from: " + pathName);

        } catch (Exception e) {
            System.err.println("Exception Message from addCenter(): " + e.getMessage());
        }
    }
}