package stickhero;

import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private final BooleanProperty spacePressed = new SimpleBooleanProperty();
    private EventHandler<KeyEvent> spacePressEvent;
    private EventHandler<KeyEvent> spaceReleaseEvent;
    private AnimationTimer heroMoveTimer;
    private boolean heroFlippable = false;
    private boolean animationRunning = false;

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
        hideGameOverText();
    }
    @FXML
    public void saveGame() throws IOException {
        SaveManager.getInstance().save();
    }

    @FXML
    public void restartGame() throws IOException {
        hideGameOverText();

        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        Utils.getPane().getChildren().setAll(pane);
        Pillar pillar = new Pillar(true);

        Hero hero = new Hero();
        Utils.setHero(hero);

        Pillar pillar1 = new Pillar(false);
        pillar1.bringToScreen().play();

        Utils.getScore().resetCurrentScore();
        Utils.getScore().updateScore();

        GameController gameController = new GameController();
        gameController.controlGame();
    }

    @FXML
    public void reviveGame() throws IOException {
        hideGameOverText();
        Utils.getScore().updateReviveCherries();
        PillarSaver savedBasePillar = new PillarSaver(Utils.getBasePillar());
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        Utils.getPane().getChildren().setAll(pane);
        savedBasePillar.createPillar(true);

        Hero hero = new Hero();
        Utils.setHero(hero);

        Utils.getScore().updateScore();

        Pillar pillar1 = new Pillar(false);
        pillar1.bringToScreen().play();

        GameController gameController = new GameController();
        gameController.controlGame();
    }

    private void hideGameOverText() {
        reviveButton.setDisable(true);
        reviveButton.setVisible(false);
        gameOverText.setVisible(false);
        cherryToRevive.setVisible(false);
        cherryReviveImage.setVisible(false);
        highScoreText.setVisible(false);
        highScore.setVisible(false);
    }

    public void controlGame() {

        getInput();
        initHeroMoveTimer();

        spacePressed.addListener(((observable, prevBool, currBool) -> {
            Stick stick = Utils.getBasePillar().getStick();
            Hero hero = Utils.getHero();
            Pillar pillar = Utils.getNextPillar();
            // If transition is not ongoing
            if (!animationRunning) {
                if (currBool) {
                    stick.scaleStick();
                    (Utils.getPane().lookup("#saveButton")).setDisable(true);
                    (Utils.getPane().lookup("#restartButton")).setDisable(true);
                } else {
                    animationRunning = true;
                    stick.setVisible(true);
                    RotateTransition rotateStick = stick.stopAndRotateStick();
                    if (stick.isWithinBounds(pillar)) {
                        continueGame(hero, pillar, rotateStick);
                        if (stick.isWithinBounds(pillar.getBonusZone())) {
                            // Increment code by 2 if within the bonus zone
                            Utils.getScore().incrementCurrentScore(1);
                            Utils.getScore().updateScore();
                        }
                    } else {
                        gameOver(hero, stick, pillar, rotateStick);
                    }
                }
            // If transition is in progress
            } else {
                if (heroFlippable && currBool) {
                    ParallelTransition flipHero = getFlipHeroParallel(hero);
                    flipHero.play();
                }
            }
        }
        ));
    }

    @NotNull
    private static ParallelTransition getFlipHeroParallel(Hero hero) {
        RotateTransition rotateHero = new RotateTransition(Duration.millis(1), hero.getImageView());
        TranslateTransition moveHeroVertical = new TranslateTransition(Duration.millis(1), hero.getImageView());
        if (hero.getImageView().getRotate() == 0) {
            rotateHero.setByAngle(180);
            hero.getImageView().setScaleX(-1);
            moveHeroVertical.setByY(37);
        } else {
            rotateHero.setByAngle(-180);
            hero.getImageView().setScaleX(1);
            moveHeroVertical.setByY(-37);
        }
        return new ParallelTransition(rotateHero, moveHeroVertical);
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

        TranslateTransition moveHero = hero.move(pillar.getCurrentX() + pillar.getWidth() - 100, 1000);
        ParallelTransition rebasePillar = pillar.reBase();
        rebasePillar.setOnFinished(event -> {
            ParallelTransition bringToScreen = new Pillar(false).bringToScreen();
            bringToScreen.setOnFinished(event1 -> {
                animationRunning = false;
                (Utils.getPane().lookup("#saveButton")).setDisable(false);
                (Utils.getPane().lookup("#restartButton")).setDisable(false);
                Utils.getScore().incrementTotalCherries();
                Utils.getScore().updateScore();
            });
            bringToScreen.play();
        });
        SequentialTransition mainSequence = new SequentialTransition(rotateStick, moveHero);
        rotateStick.setOnFinished(event -> {
            System.out.println("Stick Rotated");
            heroMoveTimer.start();
        });
        moveHero.setOnFinished(event -> {
            System.out.println("Hero Moved");
            heroMoveTimer.stop();
        });
        mainSequence.setOnFinished(event -> {
            System.out.println("Transition Complete");
            if (!hero.isDead()) {
                Utils.getScore().incrementCurrentScore(1);
                Utils.getScore().updateScore();
                rebasePillar.play();
            } else {
                animationRunning = false;
            }
        });
        mainSequence.play();
    }

    private void gameOver(Hero hero, Stick stick, Pillar pillar, RotateTransition rotateStick) {
        // Move hero by stick length plus an arbitrary value
        TranslateTransition moveHero = hero.move(stick.getScaleY() + 30, 700);
        SequentialTransition sequence = new SequentialTransition(rotateStick, moveHero);
        moveHero.setOnFinished(event -> fallAndRotateHero(hero, stick, pillar));
        sequence.play();
    }

    private void fallAndRotateHero(Hero hero, Stick stick, Pillar pillar) {
        // Rotate stick by 90 degrees if stick not within bounds of pillar.
        RotateTransition rotate = new RotateTransition(Duration.millis(200), stick);
        // Set rotation angle to 90 degrees
        rotate.setByAngle(90);
        // Transition to make hero fall out of screen
        TranslateTransition fall = new TranslateTransition(Duration.millis(200), hero.getImageView());
        // Make hero fall be pillar height plus extra 100 pixels
        fall.setByY(pillar.getHeight() + 100);
        // Rotate and make hero fall in parallel
        ParallelTransition fallRotate = new ParallelTransition(rotate, fall);
        fallRotate.setOnFinished(event -> {
            animationRunning = false;
            unbindSpace();
            showGameOverText();
        });
        // Rotate stick then move hero and then make hero fall out of screen
        fallRotate.play();
    }

    private void unbindSpace() {
        Utils.getCurrentScene().removeEventHandler(KeyEvent.KEY_PRESSED, spacePressEvent);
        Utils.getCurrentScene().removeEventHandler(KeyEvent.KEY_RELEASED, spaceReleaseEvent);
    }

    private void showGameOverText() {
        Button reviveButton = (Button) Utils.paneLookup("#reviveButton");
        Label gameOverText = (Label) Utils.paneLookup("#gameOverText");
        Label cherryToRevive = (Label) Utils.paneLookup("#cherryToRevive");
        ImageView cherryReviveImage = (ImageView) Utils.paneLookup("#cherryReviveImage");
        Label highScoreText = (Label) Utils.paneLookup("#highScoreText");
        Label highScore = (Label) Utils.paneLookup("#highScore");
        Score score = Utils.getScore();
        Button restartButton = (Button) Utils.getPane().lookup("#restartButton");

        if (score.getTotalCherries() >= score.getReviveCherries()) {
            reviveButton.setDisable(false);
        }
        reviveButton.setVisible(true);
        gameOverText.setVisible(true);
        cherryToRevive.setVisible(true);
        cherryReviveImage.setVisible(true);
        highScoreText.setVisible(true);
        highScore.setVisible(true);
        restartButton.setDisable(false);
    }

    public void initHeroMoveTimer() {
        heroMoveTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Hero hero = Utils.getHero();
                Pillar basePillar = Utils.getBasePillar();
                Pillar nextPillar = Utils.getNextPillar();
                Stick stick = nextPillar.getStick();
                if (hero.isBetweenPillars(basePillar, nextPillar)) {
                    heroFlippable = true;
                    hero.tryPickUpCherry(nextPillar.getCherry());
                } else {
                    heroFlippable = false;
                    if (hero.getImageView().getRotate() == 180) {
                        hero.setDead(true);
                        fallAndRotateHero(hero, stick, basePillar);
                    }
                }
            }
        };
    }
}