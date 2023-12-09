package stickhero;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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

    @FXML
    private CheckBox invisibleCheck;


    public void newGame() throws IOException {
        Utils.setInvisibleMode(invisibleCheck.isSelected());

        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        root.getChildren().setAll(pane);
        Utils.setPane(root);
        Pillar pillar = new Pillar(true);

        Hero hero = new Hero();
        Utils.setHero(hero);

        Pillar pillar1 = new Pillar(false);
        pillar1.bringToScreen().play();

        Score score = new Score();
        Utils.setScore(score);

        Utils.getScore().updateScore();

        GameController gameController = new GameController();
        gameController.controlGame();
    }

    public void loadGame() throws IOException, ClassNotFoundException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        root.getChildren().setAll(pane);
        Utils.setPane(root);
        SaveManager.getInstance().load();
        Hero hero = new Hero();
        Utils.setHero(hero);
        Utils.getScore().updateScore();

        GameController gameController = new GameController();
        gameController.controlGame();
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
