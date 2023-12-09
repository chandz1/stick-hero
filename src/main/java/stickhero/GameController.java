package stickhero;

import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private final BooleanProperty spacePressed = new SimpleBooleanProperty();
    private EventHandler<KeyEvent> spacePressEvent;
    private EventHandler<KeyEvent> spaceReleaseEvent;
    private AnimationTimer heroMoveTimer;
    private boolean rotating = false;
    private boolean heroMoving = false;
    private boolean transitioning = false;

    @FXML
    private Button saveButton;

    @FXML
    private Button restartButton;

    @FXML
    private Button reviveButton;

    @FXML
    private Label gameOverText;

    @FXML
    private Label cherryToRevive;

    @FXML
    private ImageView cherryReviveImage;

    @FXML
    private Label highScoreText;

    @FXML
    private Label highScore;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        saveButton.setFocusTraversable(false);
        restartButton.setFocusTraversable(false);
        reviveButton.setFocusTraversable(false);
        reviveButton.setDisable(true);
        reviveButton.setOpacity(0);
        gameOverText.setOpacity(0);
        cherryToRevive.setOpacity(0);
        cherryReviveImage.setOpacity(0);
        highScoreText.setOpacity(0);
        highScore.setOpacity(0);
    }
    @FXML
    public void saveGame() throws IOException {
        SaveManager.getInstance().save();
    }

    @FXML
    public void restartGame() throws IOException {
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        Utils.getPane().getChildren().setAll(pane);
        Pillar pillar = new Pillar(true);

        Hero hero = new Hero();
        Utils.setHero(hero);

        Pillar pillar1 = new Pillar(false);
        pillar1.bringToScreen().play();

        Score score = new Score();
        Utils.setScore(score);

        GameController gameController = new GameController();
        gameController.controlStick();
    }

    @FXML
    public void reviveGame() throws IOException {
        PillarSaver savedBasePillar = new PillarSaver(Utils.getBasePillar());
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        Utils.getPane().getChildren().setAll(pane);
        savedBasePillar.createPillar(true);

        Hero hero = new Hero();
        Utils.setHero(hero);

        Pillar pillar1 = new Pillar(false);
        pillar1.bringToScreen().play();

        GameController gameController = new GameController();
        gameController.controlStick();
    }

    public void controlStick() {

        getInput();

        spacePressed.addListener(((observable, prevBool, currBool) -> {
            Stick stick = Utils.getBasePillar().getStick();
            Hero hero = Utils.getHero();
            Pillar pillar = Utils.getNextPillar();
            // If transition is not ongoing
            if (!transitioning) {
                if (currBool) {
                    stick.scaleStick();
                } else {
                    System.out.println("entered");
                    transitioning = true;
                    RotateTransition rotateStick = stick.stopAndRotateStick();
                    if (stick.isWithinBounds(pillar)) {
                        continueGame(hero, pillar, rotateStick);
                        if (stick.isWithinBounds(pillar.getBonusZone())) {
                            // Increment code by 2 if within the bonus zone
                            Utils.getScore().incrementCurrentScore(1);
                        }
                        Utils.getScore().incrementCurrentScore(1);
                        Utils.getScore().updateScore();
                    } else {
                        gameOver(hero, stick, pillar, rotateStick);
                    }
                }
            // If transition is in progress
            } else {
                System.out.printf("%s %s\n", heroMoving, currBool);
                if (heroMoving && currBool) {
                    System.out.println("Rotated Hero");
                    RotateTransition rotateHero = new RotateTransition(Duration.millis(1), hero.getSkinView());
                    TranslateTransition moveHeroVertical = new TranslateTransition(Duration.millis(1), hero.getSkinView());
                    if (hero.getSkinView().getRotate() == 0) {
                        rotateHero.setByAngle(180);
                        moveHeroVertical.setByY(69);
                    } else {
                        rotateHero.setByAngle(-180);
                        moveHeroVertical.setByY(-69);
                    }
                    System.out.println(hero.getSkinView().getRotate());
                    ParallelTransition flipHero = new ParallelTransition(rotateHero, moveHeroVertical);
                    flipHero.play();
                }
            }
        }
        ));
    }

    private void getInput() {
        spacePressEvent = event -> {
            if (event.getCode() == KeyCode.SPACE) {
                spacePressed.set(true);
            }
        };

        spaceReleaseEvent = event -> {
            if (event.getCode() == KeyCode.SPACE) {
                spacePressed.set(false);
            }
        };

        Utils.getCurrentScene().addEventHandler(KeyEvent.KEY_PRESSED, spacePressEvent);
        Utils.getCurrentScene().addEventHandler(KeyEvent.KEY_RELEASED, spaceReleaseEvent);
    }

    private void continueGame(Hero hero, Pillar pillar, RotateTransition rotateStick) {

        TranslateTransition moveHero = hero.move(pillar.getCurrentX() + pillar.getWidth() - 100, 700);
        checkCollisionOnFlip(moveHero);
        ParallelTransition rebasePillar = pillar.reBase();
        ParallelTransition newPillarToScreen  = new Pillar(false).bringToScreen();
        SequentialTransition sequence = new SequentialTransition(rotateStick, moveHero, rebasePillar, newPillarToScreen);
        rotateStick.setOnFinished(event -> {
            rotating = false;
            heroMoving = true;
            System.out.println("Stick Rotated");
        });
        moveHero.setOnFinished(event -> {
            heroMoving = false;
            System.out.println("Hero Moved");
            heroMoveTimer.stop();
        });
        sequence.setOnFinished(event -> {
            transitioning = false;
            System.out.println("Transition Complete");
        });
        sequence.play();
    }

    private void checkCollisionOnFlip(TranslateTransition moveHero) {
        heroMoveTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                System.out.println("hi");
            }
        };
        heroMoveTimer.start();
    }

    private void gameOver(Hero hero, Stick stick, Pillar pillar, RotateTransition rotateStick) {
        // Move hero by stick length plus an arbitrary value
        TranslateTransition moveHero = hero.move(stick.getScaleY() + 30, 700);
        // Rotate stick by 90 degrees if stick not within bounds of pillar.
        RotateTransition rotate = new RotateTransition(Duration.millis(200), stick);
        // Set rotation angle to 90 degrees
        rotate.setByAngle(90);
        // Transition to make hero fall out of screen
        TranslateTransition fall = new TranslateTransition(Duration.millis(200), hero.getSkinView());
        // Make hero fall be pillar height plus hero height
        fall.setByY(pillar.getHeight() + 64);
        // Rotate and make hero fall in parallel
        ParallelTransition fallRotate = new ParallelTransition(rotate, fall);
        // Rotate stick then move hero and then make hero fall out of screen
        SequentialTransition sequence = new SequentialTransition(rotateStick, moveHero, fallRotate);
        // Play the transition
        sequence.play();
        Utils.getCurrentScene().removeEventHandler(KeyEvent.KEY_PRESSED, spacePressEvent);
        Utils.getCurrentScene().removeEventHandler(KeyEvent.KEY_RELEASED, spaceReleaseEvent);
        // Run game over function
        showGameOverText();
    }

    private void showGameOverText() {
        Button reviveButton = (Button) Utils.paneLookup("#reviveButton");
        Label gameOverText = (Label) Utils.paneLookup("#gameOverText");
        Label cherryToRevive = (Label) Utils.paneLookup("#cherryToRevive");
        ImageView cherryReviveImage = (ImageView) Utils.paneLookup("#cherryReviveImage");
        Label highScoreText = (Label) Utils.paneLookup("#highScoreText");
        Label highScore = (Label) Utils.paneLookup("#highScore");
        if (Utils.getScore().getTotalCherries() > Utils.getScore().getReviveCherries()) {
            reviveButton.setDisable(false);
        }
        reviveButton.setOpacity(1);
        gameOverText.setOpacity(1);
        cherryToRevive.setOpacity(1);
        cherryReviveImage.setOpacity(1);
        highScoreText.setOpacity(1);
        highScore.setOpacity(1);
    }
}