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
import java.util.Objects;

public class Game extends Application implements Serializable {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        // Loads the Main Menu
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainMenu.fxml")));
        // Makes a window that is 600x1000
        Scene scene = new Scene(pane, 600, 1000);


        // sets window title
        stage.setTitle("Stick Hero");
        // sets the scene
        stage.setScene(scene);
        Utils.setCurrentScene(scene);
        // resizing will break the code so we disabled it
        stage.setResizable(false);
        stage.show();

        // sets the current pane
        Utils.setPane(pane);
    }
}
