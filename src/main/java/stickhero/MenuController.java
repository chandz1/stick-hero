package stickhero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Platform.exit;

public class MenuController {
    @FXML
    private Button quitButton;

    @FXML
    private Button newGameButton;

    @FXML
    private Button loadGameButton;

    public Scene getGameScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("Background.html"));
        return new Scene(fxmlLoader.load(), 600, 400);
    }

    public void newGame(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(getGameScene());
    }

    public void loadGame() {
        System.out.println("ha lame");
    }

    public void quit() {
        exit();
    }
}
