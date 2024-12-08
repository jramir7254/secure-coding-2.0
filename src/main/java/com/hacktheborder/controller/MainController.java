package com.hacktheborder.controller;



import java.net.URL;
import java.util.List;



import com.hacktheborder.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
            public Label leaderboardLabel;

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

        BorderPane.setMargin(headerGridPane, new Insets(0, 0, 50, 0));
        headerGridPane.prefWidthProperty().bind(rootBorderPane.widthProperty().multiply(1));
        headerGridPane.prefHeightProperty().bind(rootBorderPane.heightProperty().multiply(0.065));

        leaderboardVBox.prefWidthProperty().bind(rootBorderPane.widthProperty().multiply(0.3));
        leaderboardVBox.prefHeightProperty().bind(rootBorderPane.heightProperty().multiply(0.7));

        leaderboardLabel.prefWidthProperty().bind(leaderboardVBox.widthProperty().multiply(0.65));
        leaderboardLabel.prefHeightProperty().bind(leaderboardVBox.heightProperty().multiply(0.05));
    
        informationVBox.prefWidthProperty().bind(rootBorderPane.widthProperty().multiply(0.3));
        informationVBox.prefHeightProperty().bind(rootBorderPane.heightProperty().multiply(0.7));

        infoLabel.prefWidthProperty().bind(informationVBox.widthProperty().multiply(0.65));
        infoLabel.prefHeightProperty().bind(informationVBox.heightProperty().multiply(0.05));

        informationGridPaneContainer.prefWidthProperty().bind(informationVBox.widthProperty().multiply(0.95));
        informationGridPaneContainer.prefHeightProperty().bind(informationVBox.heightProperty().multiply(0.9));

        informationWebView.prefWidthProperty().bind(informationGridPaneContainer.widthProperty());
        informationWebView.prefHeightProperty().bind(informationGridPaneContainer.heightProperty());


        loadInformationWebViewContent();
        Platform.runLater(() -> addCenter(Main.mainMenu));
        
    }

    public void updateLeaderBoard(List<String[]> list) {

       leaderboardVBox.getChildren().clear();
    
        leaderboardVBox.getChildren().add(leaderboardLabel);
        
        for(String[] team : list) {
            leaderboardVBox.getChildren().add(new Label(team[0] + " : " + team[1]) {{
                getStyleClass().add("side-labels");
                prefWidthProperty().bind(leaderboardLabel.widthProperty());
                prefHeightProperty().bind(leaderboardLabel.heightProperty());
                setPadding(new Insets(5,0,5,0));
                setAlignment(Pos.CENTER);
            }});
        }
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
            System.err.println("Exception message from loadInformationWebViewContent(): @MainController" + e.getMessage());
        }
    }


    public void displayInfo() {
        informationWebView.setVisible(!informationWebView.isVisible());
    }
}