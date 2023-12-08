package stickhero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
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
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("Background.fxml"));
        return new Scene(fxmlLoader.load(), 600, 1000);
    }

    public void newGame(ActionEvent actionEvent) throws IOException {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene bgScene = getGameScene();
        primaryStage.setScene(bgScene);

        Pane pane = (Pane) bgScene.lookup("#root");
        Utils.setPane(pane);
        Pillar pillar = new Pillar(true);

        Hero hero = new Hero();
        Utils.setHero(hero);

        Pillar pillar1 = new Pillar(false);
        pillar1.bringToScreen(pillar).play();


    }

    public void loadGame() {
        System.out.println("ha lame");
    }

    public void quit() {
        exit();
    }
}
