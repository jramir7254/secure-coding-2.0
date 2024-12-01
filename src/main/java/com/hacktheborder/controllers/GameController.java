package com.hacktheborder.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import com.hacktheborder.ApplicationManager;
import com.hacktheborder.Main;
import com.hacktheborder.Question;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class GameController {

    @FXML
    public VBox centerVBox;

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

        VBox.setMargin(questionTextField, new Insets(20));
        questionTextField.prefWidthProperty().bind(centerVBox.widthProperty().multiply(0.7));
        questionTextField.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.05));


        VBox.setMargin(webViewVBoxContainer, new Insets(20));
        webViewVBoxContainer.prefWidthProperty().bind(centerVBox.widthProperty().multiply(0.7));
        webViewVBoxContainer.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.35));

        codeMirrorWebView.prefWidthProperty().bind(webViewVBoxContainer.widthProperty());
        codeMirrorWebView.prefHeightProperty().bind(webViewVBoxContainer.heightProperty());
               
        Platform.runLater(() -> centerVBox.getChildren().add(Main.multipleChoice));
        
        loadWebViewContent();
        
    }


    public void displayMultipleChoice() {
        centerVBox.getChildren().remove(Main.debugging);
        centerVBox.getChildren().add(Main.multipleChoice);
    }









    public String getOutput() {
        return (String)webEngine.executeScript("getEditorContent()");            
    }











    public void loadWebViewContent() {
        try {
            File file = new File("secure-coding\\src\\main\\resources\\com\\hacktheborder\\codemirror-readonly.html");

            if (file.exists()) {

                webEngine.load(file.toURI().toString());
                System.out.println("Loaded file: " + file.toURI());

                String javaCode = ApplicationManager.getSanitizedJavaCode();

                webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                    if (newState == Worker.State.SUCCEEDED) {
                        System.out.println("WebView content loaded successfully.");
                        webEngine.executeScript(
                            "setEditorContent('" + javaCode + "')"
                        );
                    }
                });
            } 
        } catch (Exception e) {
            System.err.println("Exception message from loadWebViewContent: " + e.getMessage());
        }
    }
}


