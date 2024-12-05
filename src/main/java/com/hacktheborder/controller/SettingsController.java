package com.hacktheborder.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import com.hacktheborder.Main;
import com.hacktheborder.utilities.SQLConnector;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

@SuppressWarnings("exports")

public class SettingsController {

    @FXML
    public VBox settings;

    @FXML
    public Button tryButton;

    @FXML
    public Button submitPassword;

    @FXML
    public Button saveSettingsButton;

    @FXML
    public PasswordField passwordField;

    @FXML
    public GridPane score;

    @FXML
    public Spinner<Integer> timeAmount;
    @FXML
    public Spinner<Integer> points;
    @FXML
    public Spinner<Integer> penalty;

    @FXML
    public ChoiceBox<String> choiceBox;

    @FXML
    public ChoiceBox<String> timerChoice;


    public String[] options = {"Local", "Online"};

    public String[] options2 = {"Enabled", "Disabled"};

    public void initialize() {

        bindComponentsToMainPane(Main.mainController.rootBorderPane);

        choiceBox.getItems().addAll(options);
        choiceBox.setOnAction(e -> show());

        SpinnerValueFactory<Integer> factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 120, 30);
        SpinnerValueFactory<Integer> factory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 100);
        SpinnerValueFactory<Integer> factory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 5);

        submitPassword.setOnAction(e -> p());

        passwordField.setPromptText("Enter Password");
        passwordField.setText("H@ck3R");

        tryButton.setOnAction(e -> onTryConnectionButtonPressed());

        timeAmount.setValueFactory(factory);
        points.setValueFactory(factory2);
        penalty.setValueFactory(factory3);

        timerChoice.getItems().addAll(options2);
        timerChoice.setOnAction(e -> show2());
    
    }


    public void onTryConnectionButtonPressed() {
        new SQLConnector().testConnection();
    }


    public void p() {
       
        Properties properties = new Properties();
      
        try (InputStream input = getClass().getResourceAsStream("/com/hacktheborder/properties/config.properties")) {

            if (input == null) 
                System.err.println("Unable to find config.properties");

            properties.load(input);
            
            String password = properties.getProperty("settings_password").replaceAll("\"", "");


            if (passwordField.getText().equals(password)) {
                choiceBox.setDisable(false);
                timerChoice.setDisable(false);
                score.setDisable(false);
                saveSettingsButton.setDisable(false);
            }


        } catch (Exception e) {
            System.err.println("Exception message from p() @: " + e.getMessage());
        }
    }


    public void show() {
        tryButton.setDisable(!choiceBox.getValue().equals("Online"));
    }

    public void show2() {
        System.out.println(timerChoice.getValue());
        timeAmount.setDisable(!timerChoice.getValue().equals("Enabled"));
    }

    public void bindComponentsToMainPane(BorderPane mainPane) {

        settings.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.3));
        settings.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.7));

    }

}
