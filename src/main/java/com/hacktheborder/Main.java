package com.hacktheborder;


import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;


public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }


    @SuppressWarnings("exports")
    @Override
    public void start(Stage stage) throws Exception {
  
        Parent root = new FXMLLoader().load(getClass().getResourceAsStream("Main.fxml"));
        Scene scene = new Scene(root, 1000, 800);

        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setTitle("CodeMirror in JavaFX");
        stage.setScene(scene);
   

        stage.show();
    }
}
