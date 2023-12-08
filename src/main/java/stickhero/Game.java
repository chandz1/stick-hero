package stickhero;

import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.Serializable;

public class Game extends Application implements Serializable {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("MainMenu.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 1000);


        // Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Stick Hero");
        stage.setScene(scene);
        Utils.setCurrentScene(scene);
        stage.setResizable(false);
        stage.show();

        Pane pane = (Pane) scene.lookup("#root");
        Utils.setPane(pane);
        Pillar pillar = new Pillar(true);

        Hero hero = new Hero();
        Utils.setHero(hero);

        Pillar pillar1 = new Pillar(false);
        pillar1.bringToScreen(pillar).play();

        GameController gameController = new GameController();
        gameController.controlStick();
    }
}
