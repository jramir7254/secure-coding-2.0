package com.hacktheborder;



import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Main extends Application {

    
    public static void main(String[] args) {
    
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        
        Parent root = new FXMLLoader().load(getClass().getResourceAsStream("Main.fxml"));
     
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("Application.css").toExternalForm());
        stage.setScene(scene);
   
        stage.setTitle("Editable Code Area");

       // questionArea.setText("this is some text");
        stage.show();
        
   
    }

       
}
