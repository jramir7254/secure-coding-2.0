package com.hacktheborder.controllers;

import com.hacktheborder.Main;

import java.io.File;
import java.net.URL;

import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Comp {
    

    @FXML
    public BorderPane mainPane;

    @FXML
    public HBox header;

    @FXML
    public BorderPane lead;

    @FXML
    public BorderPane info;

    @FXML
    public Label infoLabel;


    public void initialize() {

        header.prefWidthProperty().bind(mainPane.widthProperty().multiply(1));
        header.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.05));
  
            mainPane.setMargin(header, new Insets(0, 0, 20, 0));

            lead.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.3));
            lead.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.7));
    
            info.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.3));
            info.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.7));
   
  
    }

    public void addCenter() {
        System.out.println("cliekd add center");
        try {

            URL fxmlLocation = getClass().getResource("/com/hacktheborder/MainMenu.fxml");
            System.out.println(fxmlLocation);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/hacktheborder/Center.fxml"));
            Node newCenter = loader.load();
    
            // Set the new center in the BorderPane
            mainPane.setCenter(newCenter);
    
            // Access the new center's controller
            Center controller = loader.getController();
    
            // Pass the mainPane to the controller for dynamic bindings
            controller.bindComponentsToMainPane(mainPane);

                System.out.println("added center");
        } catch (Exception e) {
         System.out.println("exc mssg: " + e.getMessage());
        }
    }
}