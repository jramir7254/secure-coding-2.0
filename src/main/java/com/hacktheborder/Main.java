package com.hacktheborder;


import java.io.IOException;

import com.hacktheborder.controller.DebuggingController;
import com.hacktheborder.controller.EndGameScreenController;
import com.hacktheborder.controller.GameController;
import com.hacktheborder.controller.MainController;
import com.hacktheborder.controller.MainMenuController;
import com.hacktheborder.controller.MultipleChoiceController;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



@SuppressWarnings("exports")
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
            launch(args);
        }
    
    
    
        @Override
        public void start(Stage stage) throws Exception {
            
            loadFXMLResources(); 
            ApplicationManager.initialize();
    
            main.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());

            stage.setTitle("Secure Coding");
            stage.setScene(new Scene(main, 1000, 800));
            stage.show();
   
        }
    
    
    
    
    
        private void loadFXMLResources() {
            try {
                System.out.println("Loading FXML files...");
    
                // Use a single FXMLLoader for each FXML
                FXMLLoader mainLoader = new FXMLLoader(Main.class.getResource("fxml/Main.fxml"));
                main = mainLoader.load();
                mainController = mainLoader.getController();
                

                FXMLLoader menuLoader = new FXMLLoader(Main.class.getResource("fxml/MainMenu.fxml"));
                mainMenu = menuLoader.load();
                mainMenuController = menuLoader.getController();

                FXMLLoader gameLoader = new FXMLLoader(Main.class.getResource("fxml/GameContainer.fxml"));
                game = gameLoader.load();
                gameController = gameLoader.getController();

                FXMLLoader multipleChoiceLoader = new FXMLLoader(Main.class.getResource("fxml/MultipleChoiceContainer.fxml"));
                multipleChoice = multipleChoiceLoader.load();
                multipleChoiceController = multipleChoiceLoader.getController();

                FXMLLoader debuggingLoader = new FXMLLoader(Main.class.getResource("fxml/DebuggingContainer.fxml"));
                debugging = debuggingLoader.load();
                debuggingController = debuggingLoader.getController();

                FXMLLoader endGameScreenLoader = new FXMLLoader(Main.class.getResource("fxml/EndGameScreen.fxml"));
                endGame = endGameScreenLoader.load();
                endGameScreenController = endGameScreenLoader.getController();

            System.out.println("FXML files loaded successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
