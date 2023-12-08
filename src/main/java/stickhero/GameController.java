package stickhero;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController {
    @FXML
    private Label playButton;

    private StickController stickController = new StickController();

    @FXML
    protected void onHelloButtonClick() {
        playButton.setText("Welcome to JavaFX Application!");
        stickController.controlStick();
    }
}