package stickhero;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.application.Platform.exit;

public class MenuController implements Initializable {
    @FXML
    private Button quitButton;

    @FXML
    private Button newGameButton;

    @FXML
    private Button loadGameButton;

    @FXML
    private Pane root;


    public void newGame() throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        root.getChildren().setAll(pane);
        Utils.setPane(root);
        Pillar pillar = new Pillar(true);

        Hero hero = new Hero();
        Utils.setHero(hero);

        Pillar pillar1 = new Pillar(false);
        pillar1.bringToScreen().play();

        GameController gameController = new GameController();
        gameController.controlStick();
    }

    public void loadGame() throws IOException {
        SaveManager.getInstance().load();
    }

    public void quit() {
        exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quitButton.setFocusTraversable(false);
        loadGameButton.setFocusTraversable(false);
    }
}
