package com.hacktheborder;


import com.hacktheborder.controllers.MainController;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


public class Main extends Application {
    public static MainController mainController;

    public static void main(String[] args) {
        launch(args);
    }


    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws Exception {
  

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();

        mainController = loader.getController();
        
        Scene scene = new Scene(root, 1000, 800);

        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("Secure Coding");
        stage.setScene(scene);
   

        stage.show();
    }
}
