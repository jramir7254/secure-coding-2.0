package com.hacktheborder;


import java.io.IOException;

import com.hacktheborder.controllers.DebuggingController;
import com.hacktheborder.controllers.EndGameScreenController;
import com.hacktheborder.controllers.GameController;
import com.hacktheborder.controllers.MainController;
import com.hacktheborder.controllers.MainMenuController;
import com.hacktheborder.controllers.MultipleChoiceController;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    public static Parent main;
    public static Node mainMenu;
    public static Node game;
    public static Node multipleChoice;
    public static Node debugging;
    public static Node endGame;

    public static MainController mainController;
    public static MainMenuController mainMenuController;
    public static GameController gameController;
    public static MultipleChoiceController multipleChoiceController;
    public static DebuggingController debuggingController;
    public static EndGameScreenController endGameScreenController;

    public static void main(String[] args) {
        ApplicationManager.initialize();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
        loadFXMLResources(); 

        main.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("Secure Coding");
        
        stage.setScene(new Scene(main, 1000, 800));
        stage.show();
    }





    private void loadFXMLResources() {
        try {
            System.out.println("Loading FXML files...");

            // Use a single FXMLLoader for each FXML
            FXMLLoader mainLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
            main = mainLoader.load();
            mainController = mainLoader.getController();

            FXMLLoader menuLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
            mainMenu = menuLoader.load();
            mainMenuController = menuLoader.getController();

            FXMLLoader gameLoader = new FXMLLoader(Main.class.getResource("GameContainer.fxml"));
            game = gameLoader.load();
            gameController = gameLoader.getController();

            FXMLLoader multipleChoiceLoader = new FXMLLoader(Main.class.getResource("MultipleChoiceContainer.fxml"));
            multipleChoice = multipleChoiceLoader.load();
            multipleChoiceController = multipleChoiceLoader.getController();

            FXMLLoader debuggingLoader = new FXMLLoader(Main.class.getResource("DebuggingContainer.fxml"));
            debugging = debuggingLoader.load();
            debuggingController = debuggingLoader.getController();

            FXMLLoader endGameScreenLoader = new FXMLLoader(Main.class.getResource("EndGameScreen.fxml"));
            endGame = endGameScreenLoader.load();
            endGameScreenController = endGameScreenLoader.getController();

            System.out.println("FXML files loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
