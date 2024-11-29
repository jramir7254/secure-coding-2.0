package com.hacktheborder.controllers;

import java.io.File;

import com.hacktheborder.utilities.MyFileReader;

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

public class Center {
    public String path = "secure-coding\\src\\main\\java\\com\\hacktheborder\\codemirror.html";


    @FXML
    public TextField questionField;

    @FXML
    public VBox webCont;

    @FXML
    public Button submit;

    @FXML
    private ToggleGroup selection;

    @FXML
    public VBox buttonHolder;

    @FXML
    public WebView webView;

    @FXML
    public VBox center;

    
    public void clear() {
        selection.selectToggle(null);
    }



    public void bindComponentsToMainPane(BorderPane mainPane) {
        center.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.3));
        center.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.7));
               
        
        buttonHolder.prefWidthProperty().bind(center.widthProperty().multiply(0.7));
        buttonHolder.prefHeightProperty().bind(center.heightProperty().multiply(0.35));


        for (javafx.scene.Node node : buttonHolder.getChildren()) {
            // Check if the child is a Button
            if (node instanceof ToggleButton) {
                ToggleButton button = (ToggleButton) node;  // Cast the node to Button
                button.prefWidthProperty().bind(center.widthProperty().multiply(0.5));      // Set preferred width for each button
                button.prefHeightProperty().bind(center.heightProperty().multiply(0.01));     // Set preferred height for each button
                buttonHolder.setMargin(button, new Insets(10, 0, 10, 0));
            } else {
                Button button = (Button) node;  // Cast the node to Button
                button.prefWidthProperty().bind(center.widthProperty().multiply(0.5));      // Set preferred width for each button
                button.prefHeightProperty().bind(center.heightProperty().multiply(0.01));     // Set preferred height for each
                buttonHolder.setMargin(button, new Insets(20, 0, 5, 0));
            }
        }


        center.setMargin(questionField, new Insets(20));
        center.setMargin(webCont, new Insets(20));

        webCont.prefWidthProperty().bind(center.widthProperty().multiply(0.7));
        webCont.prefHeightProperty().bind(center.heightProperty().multiply(0.4));

        webView.prefWidthProperty().bind(webCont.widthProperty());
        webView.prefHeightProperty().bind(webCont.heightProperty());

        questionField.prefWidthProperty().bind(center.widthProperty().multiply(0.7));
        questionField.prefHeightProperty().bind(center.heightProperty().multiply(0.05));

        

        loadWebViewContent();
    }


        private void loadWebViewContent() {
        WebEngine webEngine = webView.getEngine();

        File file = new File(path);
        if (file.exists()) {
            webEngine.load(file.toURI().toString());
            System.out.println("Loaded file: " + file.toURI());
            String str = MyFileReader.readFile();
            System.out.println(str);
            webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                if (newState == Worker.State.SUCCEEDED) {
                    System.out.println("WebView content loaded successfully.");
                    webEngine.executeScript(
                        "setEditorContent('" + str + "')"
                    );
                }
            });
        } else {
            System.err.println("File not found: " + file.getAbsolutePath());
        }
    }
}
