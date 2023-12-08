package stickhero;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import static javafx.application.Platform.exit;

public class MenuController {
    @FXML
    private Button quitButton;

    public void quit() {
        exit();
    }
}
