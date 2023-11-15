package stickhero;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label playButton;

    @FXML
    protected void onHelloButtonClick() {
        playButton.setText("Welcome to JavaFX Application!");
    }
}