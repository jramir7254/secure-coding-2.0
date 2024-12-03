package com.hacktheborder.controller;

import java.net.URL;

import com.hacktheborder.Main;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


@SuppressWarnings("exports")
public class GameController {

   
    @FXML
    public VBox centerVBox;

    @FXML 
    public HBox scoreHBoxContainer;

    @FXML
    public Label questionScoreLabel;

    @FXML
    public TextField questionTextField;

    @FXML
    public HBox webViewVBoxContainer;

    @FXML
    public WebView codeMirrorWebView;


    public WebEngine webEngine;

    






    public void initialize() {
        bindComponentsToMainPane(Main.mainController.rootBorderPane);
    }

    






    public void bindComponentsToMainPane(BorderPane mainPane) {

        webEngine = codeMirrorWebView.getEngine();

        centerVBox.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.3));
        centerVBox.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.7));

        scoreHBoxContainer.prefWidthProperty().bind(centerVBox.widthProperty().multiply(0.7));
        scoreHBoxContainer.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.025));

        VBox.setMargin(questionTextField, new Insets(20));
        questionTextField.prefWidthProperty().bind(centerVBox.widthProperty().multiply(0.7));
        questionTextField.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.05));


        VBox.setMargin(webViewVBoxContainer, new Insets(20));
        webViewVBoxContainer.prefWidthProperty().bind(centerVBox.widthProperty().multiply(0.7));
        webViewVBoxContainer.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.35));

        codeMirrorWebView.prefWidthProperty().bind(webViewVBoxContainer.widthProperty());
        codeMirrorWebView.prefHeightProperty().bind(webViewVBoxContainer.heightProperty());
               
        Platform.runLater(() -> centerVBox.getChildren().add(Main.multipleChoice));
        

    }


    public void displayDebuggingPanel(Node removed, Node add) {
        int index = centerVBox.getChildren().indexOf(removed);
        centerVBox.getChildren().set(index, add);
    }




    public void displayMultipleChoice(Node removed, Node add) {
        int index = centerVBox.getChildren().indexOf(removed);
        centerVBox.getChildren().set(index, add);
    }









    public String getEditorContent() {
        return (String) webEngine.executeScript("getEditorContent()");            
    }


    public void setTextToDebug( ) {
        questionTextField.setText("Fix the code so it produces the following output.");
    }

    public void setTextToMultipleChoice( ) {
        questionTextField.setText("What type of error is this?");
    }











    public void loadWebViewContent(String htmlURLFile, String javaScriptCommand) {
        try {
       
            URL htmlFile = getClass().getResource(htmlURLFile);

            webEngine.load(htmlFile.toExternalForm());

            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    System.out.println("WebView content loaded successfully.");
                    webEngine.executeScript(javaScriptCommand);
                }
            });
            
        } catch (Exception e) {
            System.err.println("Exception message from loadWebViewContent(): @GameController" + e.getMessage());
        }
    }
}


