package com.hacktheborder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class Controller {
    @FXML
    private TextArea questionArea;

    @FXML
    private ToggleGroup selection;


    @FXML
    private Button submitButton;

    @FXML
    private ToggleButton compileTimeErrorButton;

    @FXML
    private ToggleButton runtimeErrorButton;

    @FXML
    private ToggleButton logicErrorButton;

    @FXML
    private ToggleButton vulnerabilityButton;

    @FXML
    public void initialize() {
        // Example: Set initial text for the TextArea
        System.out.println(questionArea);
        selection.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle != null) {
                System.out.println("Selected: " + ((ToggleButton) newToggle).getText());
            }
        });
        questionArea.setText("string");
    }


    public void submit() {
        selection.selectToggle(null);
        System.out.println("submit clicked");
    }
}
