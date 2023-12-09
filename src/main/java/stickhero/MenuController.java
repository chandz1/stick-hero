package stickhero;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static javafx.application.Platform.exit;

public class MenuController implements Initializable {
    @FXML
    private Button quitButton;

    @FXML
    private Button continueButton;

    @FXML
    private Pane root;

    @FXML
    private CheckBox invisibleCheck;

    // the function is called when the new game button is pressed
    @FXML
    public void newGame() throws IOException {
        // invisible mode is set based on its check box
        Utils.setInvisibleMode(invisibleCheck.isSelected());

        // loads a new fxml
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        // removes everything from the pane and sets all the children as the pane from the fxml
        root.getChildren().setAll(pane);
        // sets the root as current pane
        Utils.setPane(root);

        // creates a new base pillar
        Pillar pillar = new Pillar(true);

        // creates a new hero
        Hero hero = new Hero();
        Utils.setHero(hero);

        // next pillar is created a brought to screen
        Pillar pillar1 = new Pillar(false);
        pillar1.bringToScreen().play();

        // a new score is created
        Score score = new Score();
        Utils.setScore(score);

        // the score should all be set to 0 by default. this is redundant code
        Utils.getScore().updateScore();
    }

    // the function is called when the continue button is pressed
    @FXML
    public void continueGame() throws IOException, ClassNotFoundException {
        // loads a new fxml
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));

        // removes everything from the pane and sets all the children as the pane from the fxml
        root.getChildren().setAll(pane);
        // sets the root as current pane
        Utils.setPane(root);

        // loads the save
        SaveManager.getInstance().load();

        // the score is updated
        Utils.getScore().updateScore();
    }

    // the function is called when the quit button is pressed
    @FXML
    public void quit() {
        // exits the javafx
        exit();
    }

    // the function is called when the controller is initialized
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // continue button and quit button will no longer have focus
        quitButton.setFocusTraversable(false);
        continueButton.setFocusTraversable(false);
        // checks if save data exists
        if (!new File("save.dat").exists()) {
            // disable continuing if the save data doesn't exist
            continueButton.setDisable(true);
        }
    }
}
