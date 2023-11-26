package stickhero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;

public class Game extends Application implements Serializable {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("Background.fxml"));
        StackPane stackPane = new StackPane();
        Stick stick = new Stick(20, 1, 100, 100);
        stackPane.getChildren().add(stick);
        Scene scene = new Scene(stackPane, 1920, 1080);
        // Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Stick Hero");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        KeyCombination kc = new KeyCodeCombination(KeyCode.SPACE, KeyCombination.CONTROL_DOWN);
        Runnable rn = stick::scaleStick;
        scene.getAccelerators().put(kc, rn);
    }
}
