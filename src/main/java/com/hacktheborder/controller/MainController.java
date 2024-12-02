package com.hacktheborder.controller;



import java.net.URL;

import com.hacktheborder.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


@SuppressWarnings("exports")
public class MainController {
    @FXML 
    public BorderPane rootBorderPane;

    @FXML 
    public GridPane headerGridPane;

    @FXML 
    public Label teamHighScoreLabel;

    @FXML 
    public Label currentScoreLabel;

    @FXML 
    public Label teamLabel;

    @FXML 
    public Label questionTimeLabel;

    @FXML 
    public Label totalTimeLabel;

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


    

    public void initializeLabels(String teamName) {
        teamHighScoreLabel  .setText    ("Team High Score: " + 0);
        currentScoreLabel   .setText    ("Current Score: " + 0);
        teamLabel           .setText    ("Team: " + teamName);
        questionTimeLabel   .setText    ("Question Time: 00:00");
        totalTimeLabel      .setText    ("Total Time: 00:00");
    }




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

        loadInformationWebViewContent();
        Platform.runLater(() -> addCenter(Main.mainMenu));
        
    }


    public void addCenter(Node node) {
        rootBorderPane.setCenter(node);
    }


    public void loadInformationWebViewContent() {
        try {
            WebEngine webEngine = informationWebView.getEngine();
            URL htmlFile = getClass().getResource("/com/hacktheborder/html/information.html");
            webEngine.load(htmlFile.toExternalForm());
                
        } catch (Exception e) {
            System.err.println("Exception message from loadWebViewContent: " + e.getMessage());
        }
    }




    public void displayInfo() {
        if (informationWebView.isVisible()) {
            informationWebView.setVisible(false); 
        } else {
            informationWebView.setVisible(true);  
        }
    }
}