package com.hacktheborder.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Documented;
import java.net.URL;
import java.util.Properties;

import com.hacktheborder.ApplicationManager;
import com.hacktheborder.Main;
import com.hacktheborder.model.Settings;
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
    private VBox settingsVBoxContainer;

        //HBox

            @FXML
            private PasswordField passwordField;

            @FXML
            private Button submitPasswordButton;

        //HBox

            @FXML
            private ChoiceBox<String> gameNetworkTypeChoiceBox;

            @FXML
            private Button connectButton;

        //HBox

            @FXML
            private ChoiceBox<String> enableTimeLimitChoiceBox;

            @FXML
            private Spinner<Integer> gameTimeLimitAmountSpinner;

        @FXML
        private GridPane scoringSettingsGridPane;

            @FXML
            private Spinner<Integer> pointsPerQuestionSpinner;
            
            @FXML
            private Spinner<Integer> penaltyAmountSpinner;

        @FXML
        private ChoiceBox<String> allowReplayChoiceBox;
            
        @FXML
        private Button saveSettingsButton;



        private Settings settings;

    


    public void initialize() {

        bindComponentsToMainPane(Main.mainController.rootBorderPane);

        passwordField.setPromptText("Enter Password");
        passwordField.setText("H@ck3R");

        submitPasswordButton.setOnAction(e -> onSubmitPasswordButtonPressed());


        gameNetworkTypeChoiceBox.getItems().addAll(new String[] {"Local", "Online"});
        gameNetworkTypeChoiceBox.setOnAction(e -> enableOnlineGameSetting());

        connectButton.setOnAction(e -> onTryConnectionButtonPressed());


        enableTimeLimitChoiceBox.getItems().addAll(new String[] {"Enabled", "Disabled"});
        enableTimeLimitChoiceBox.setOnAction(e -> enableGameTimeLimitSetting());

        gameTimeLimitAmountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 120, 30));


        pointsPerQuestionSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 100));
        penaltyAmountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000, 5));

        
        allowReplayChoiceBox.getItems().addAll(new String[] {"Enabled", "Disabled"});
     

        saveSettingsButton.setOnAction(e -> onSaveSettingsButtonPressed());

        settings = new Settings();

    }


    public void onSaveSettingsButtonPressed() {
        settings.setReplaySetting(getReplaySettingChoice());
        settings.setScoringSetting(getPointsPerQuestionSpinnerAmount(), getPenaltySpinnerAmount());
        settings.setTimeLimitSetting(isTimeLimitEnabled(), getTimeLimitSpinnerAmount());
        ApplicationManager.updateSettings(settings);
    }


    public boolean getReplaySettingChoice() {
        return allowReplayChoiceBox.getValue().equals("Enabled");
    }


    public void onTryConnectionButtonPressed() {
        boolean connected = new SQLConnector().testConnection();
        System.out.println(connected);
        if (!connected) {
            gameNetworkTypeChoiceBox.setValue("Local");
        } else {
            gameNetworkTypeChoiceBox.setValue("Online");
            settings.setOnlineSetting(true);
        }
    }


    public void onSubmitPasswordButtonPressed() {
        Properties properties = new Properties();

        try (InputStream input = getClass().getResourceAsStream("/com/hacktheborder/properties/config.properties")) {

            properties.load(input);
            String password = properties.getProperty("settings_password");

            if (validatePassword(password)) {
                enableSettings();
            }

        } catch (Exception e) {
            System.err.println("Exception message from p() @: " + e.getMessage());
        }
    }


    private boolean validatePassword(String enteredPassword) {
        return passwordField.getText().equals(enteredPassword);
    }

    
    private void enableSettings() {
        gameNetworkTypeChoiceBox.setDisable(false);
        enableTimeLimitChoiceBox.setDisable(false);
        scoringSettingsGridPane.setDisable(false);
        saveSettingsButton.setDisable(false);
        allowReplayChoiceBox.setDisable(false);
    }


    public void enableOnlineGameSetting() {
        connectButton.setDisable(!gameNetworkTypeChoiceBox.getValue().equals("Online"));
    }


    public void enableGameTimeLimitSetting() {
        gameTimeLimitAmountSpinner.setDisable(!enableTimeLimitChoiceBox.getValue().equals("Enabled"));
    }

    public boolean isTimeLimitEnabled() {
        return enableTimeLimitChoiceBox.getValue().equals("Enabled");
    }


    public int getTimeLimitSpinnerAmount() {
        if(!gameTimeLimitAmountSpinner.isDisable()) {
            return gameTimeLimitAmountSpinner.getValue();
        } else {
            return 0;
        }
    }


    public int getPointsPerQuestionSpinnerAmount() {
        if(!pointsPerQuestionSpinner.isDisable()) {
            return pointsPerQuestionSpinner.getValue();
        } else {
            return 0;
        }
    }

    public int getPenaltySpinnerAmount() {
        if(!penaltyAmountSpinner.isDisable()) {
            return penaltyAmountSpinner.getValue();
        } else {
            return 0;
        }
    }



    public void bindComponentsToMainPane(BorderPane mainPane) {

        settingsVBoxContainer.prefWidthProperty().bind(mainPane.widthProperty().multiply(0.3));
        settingsVBoxContainer.prefHeightProperty().bind(mainPane.heightProperty().multiply(0.7));

    }
}
