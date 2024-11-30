package com.hacktheborder.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.hacktheborder.Question;
import com.hacktheborder.utilities.MyFileReader;

import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class GameController implements CenterController {
    public String path = "secure-coding\\src\\main\\java\\com\\hacktheborder\\codemirror.html";

    @FXML
    public VBox centerVBox;

    @FXML
    public TextField questionTextField;

    @FXML
    public VBox webViewVBoxContainer;

    @FXML
    public WebView codeMirrorWebView;

    @FXML
    public VBox buttonVBoxContainer;

    @FXML
    private ToggleGroup multipleChoiceSelection;

    @FXML
    public Button submitButton;

    
    public void clear() {
        Question q = readObject();
        ToggleButton selectedButton = (ToggleButton) multipleChoiceSelection.getSelectedToggle();
        System.out.println(multipleChoiceSelection.getSelectedToggle());

        if(multipleChoiceSelection.getSelectedToggle() == null) {
            return;
        }
        
        if(!selectedButton.getText().equals(q.getQuestionType())) {
            playShakeEffect(selectedButton, "#e3210b", false);

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
        } else {
            playShakeEffect(selectedButton, "#24e327", true);
        }

        multipleChoiceSelection.selectToggle(null);
      
    }


        public void playShakeEffect(ToggleButton button, String color, boolean correct) {
            button.setStyle("-fx-background-color: " + color +";");
        // Scale up transition
            ScaleTransition scaleUp = new ScaleTransition(Duration.millis(250), button);
            scaleUp.setToX(1.1); // Increase width by 10%
            scaleUp.setToY(1.1); // Increase height by 10%

            // Shake effect (left-right movement)
            TranslateTransition shake = new TranslateTransition(Duration.millis(50), button);
            if(!correct) {
                shake.setByX(10); // Move 10px to the right
                shake.setCycleCount(6); // Repeat 6 times
                shake.setAutoReverse(true); // Move back and forth
            }

            // Scale down transition
            ScaleTransition scaleDown = new ScaleTransition(Duration.millis(150), button);
            scaleDown.setToX(1.0); // Return to normal width
            scaleDown.setToY(1.0); // Return to normal height

            //scaleDown.setOnFinished(e -> button.setStyle(""));
            // Combine all transitions
            SequentialTransition sequentialTransition = new SequentialTransition(scaleUp, shake, scaleDown);
            sequentialTransition.play();
    }



    public void bindComponentsToMainPane(BorderPane mainPane) {

        centerVBox.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.3));
        centerVBox.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.7));

        VBox.setMargin(questionTextField, new Insets(20));
        questionTextField.prefWidthProperty().bind(centerVBox.widthProperty().multiply(0.7));
        questionTextField.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.05));


        VBox.setMargin(webViewVBoxContainer, new Insets(20));
        webViewVBoxContainer.prefWidthProperty().bind(centerVBox.widthProperty().multiply(0.7));
        webViewVBoxContainer.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.4));

        codeMirrorWebView.prefWidthProperty().bind(webViewVBoxContainer.widthProperty());
        codeMirrorWebView.prefHeightProperty().bind(webViewVBoxContainer.heightProperty().multiply(0.5));
               
        
        buttonVBoxContainer.prefWidthProperty().bind(centerVBox.widthProperty().multiply(0.7));
        buttonVBoxContainer.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.35));

        setButtonLayout();

        loadWebViewContent();
        
    }



    private void setButtonLayout() {
        for (javafx.scene.Node node : buttonVBoxContainer.getChildren()) {

            if (node instanceof ToggleButton) {
                ToggleButton button = (ToggleButton) node; 
                button.prefWidthProperty().bind(centerVBox.widthProperty().multiply(0.5));      
                button.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.015));   
                VBox.setMargin(button, new Insets(10, 0, 10, 0));
            } 

        }

        submitButton.prefWidthProperty().bind(centerVBox.widthProperty().multiply(0.5));    
        submitButton.prefHeightProperty().bind(centerVBox.heightProperty().multiply(0.015));   
        VBox.setMargin(submitButton, new Insets(20, 0, 5, 0));

    }



    private void loadWebViewContent() {
        try {

            WebEngine webEngine = codeMirrorWebView.getEngine();

            File file = new File("secure-coding\\src\\main\\resources\\com\\hacktheborder\\codemirror.html");

            if (file.exists()) {

                webEngine.load(file.toURI().toString());
                System.out.println("Loaded file: " + file.toURI());

                String javaCode = readObject().getJavaCode().replace("\\", "\\\\")  // Escape backslashes
                .replace("'", "\\'")    // Escape single quotes (if necessary)
                .replace("\n", "\\n")    // Escape newline characters
                .replace("\r", "");     

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

    public Question readObject() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("secure-coding\\src\\main\\resources\\Question.ser")))) {
            return (Question)ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }
}






// ClassLoader classLoader = getClass().getClassLoader();
// URL resource = classLoader.getResource("codemirror.html");

// System.out.println(resource == null ? "this bithc empty" : "this bith aight");