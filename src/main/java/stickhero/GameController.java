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
    private SequentialTransition mainSequence;
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
        // removes focus from buttons during initialization
        saveButton.setFocusTraversable(false);
        restartButton.setFocusTraversable(false);
        reviveButton.setFocusTraversable(false);
        // hides the gameOverText during initialization
        hideGameOverText();
        this.controlGame();
    }
    @FXML
    public void saveGame() throws IOException {
        // saves the game
        SaveManager.getInstance().save();
    }

    @FXML
    public void restartGame() throws IOException {
        // removes the text
        hideGameOverText();

        // loads a new fxml
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));
        // removes everything from the pane and sets all the children as the pane from the fxml
        Utils.getPane().getChildren().setAll(pane);

        // creates a new base pillar
        Pillar pillar = new Pillar(true);

        // creates a new hero
        Hero hero = new Hero();
        Utils.setHero(hero);

        // next pillar is created a brought to screen
        Pillar pillar1 = new Pillar(false);
        pillar1.bringToScreen().play();

        // the scores are reset and updated
        Utils.getScore().resetCurrentScore();
        Utils.getScore().updateScore();
    }

    @FXML
    public void reviveGame() throws IOException {
        // removes the game over text from screen
        hideGameOverText();
        // updates the cherries as the user decided to use them to revive
        Utils.getScore().updateReviveCherries();
        // saves the current base pillar
        PillarSaver savedBasePillar = new PillarSaver(Utils.getBasePillar());
        // loads a new game fxml
        Pane pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Game.fxml")));

        // removes everything from the pane and sets all the children as the pane from the fxml
        Utils.getPane().getChildren().setAll(pane);
        savedBasePillar.createPillar(true);

        // creates a new hero
        Hero hero = new Hero();
        Utils.setHero(hero);

        // updates the score
        Utils.getScore().updateScore();

        // brings a new pillar to screen
        Pillar pillar1 = new Pillar(false);
        pillar1.bringToScreen().play();
    }

    private void hideGameOverText() {
        // the game over text is hidden
        reviveButton.setDisable(true);
        reviveButton.setVisible(false);
        gameOverText.setVisible(false);
        cherryToRevive.setVisible(false);
        cherryReviveImage.setVisible(false);
        highScoreText.setVisible(false);
        highScore.setVisible(false);
    }

    public void controlGame() {

        // gets input
        getInput();
        // initializes the hero's move timer
        initHeroMoveTimer();

        // checks if space is pressed
        spacePressed.addListener(((observable, prevBool, currBool) -> {
            // gets the stick, hero and the next pillar
            Stick stick = Utils.getBasePillar().getStick();
            Hero hero = Utils.getHero();
            Pillar pillar = Utils.getNextPillar();
            // If transition is not ongoing
            if (!animationRunning) {
                // if the current state of the space bar is true
                if (currBool) {
                    // the stick is scaled while space is being pressed
                    stick.scaleStick();
                    // save button and restart button are disabled
                    (Utils.getPane().lookup("#saveButton")).setDisable(true);
                    (Utils.getPane().lookup("#restartButton")).setDisable(true);
                } else {
                    // animationRunning is set to true as the animation is running
                    animationRunning = true;
                    // the stick is made to be visible
                    stick.setVisible(true);
                    // a transition to rate the stick is created
                    RotateTransition rotateStick = stick.stopAndRotateStick();
                    // if the stick is within the bounds of the pillar
                    if (stick.isWithinBounds(pillar)) {
                        // the game continues
                        continueGame(hero, pillar, rotateStick);
                        if (stick.isWithinBounds(pillar.getBonusZone())) {
                            // Increment code by 2 if within the bonus zone
                            Utils.getScore().incrementCurrentScore(1);
                            Utils.getScore().updateScore();
                        }
                    } else {
                        // the game is over because the stick is not in bounds of the pillar
                        gameOver(hero, stick, pillar, rotateStick);
                    }
                }
            // If transition is in progress
            } else {
                // if the hero is currently flippable and space bar is pressed
                if (heroFlippable && currBool) {
                    // the hero is flipped using transition animations
                    ParallelTransition flipHero = getFlipHeroParallel(hero);
                    flipHero.play();
                }
            }
        }
        ));
    }

    @NotNull
    private static ParallelTransition getFlipHeroParallel(Hero hero) {
        // rotates the hero
        RotateTransition rotateHero = new RotateTransition(Duration.millis(1), hero.getImageView());
        // moves the hero
        TranslateTransition moveHeroVertical = new TranslateTransition(Duration.millis(1), hero.getImageView());
        // if the hero hasn't been rotated
        if (hero.getImageView().getRotate() == 0) {
            // the hero is rotated by 180
            rotateHero.setByAngle(180);
            // the hero's scale is set to -1 (as rotating would make him face the opposite of the direction that he is moving in)
            hero.getImageView().setScaleX(-1);
            // the hero is moved below the stick
            moveHeroVertical.setByY(37);
        } else {
            // the hero is rotated back by 180
            rotateHero.setByAngle(-180);
            // the hero's scale is set back to normal (facing the direction he is moving in)
            hero.getImageView().setScaleX(1);
            // the hero is moved back to the Y axis he should be in
            moveHeroVertical.setByY(-37);
        }
        // returns both the transitions for moving and rotating
        return new ParallelTransition(rotateHero, moveHeroVertical);
    }

    // gets the input
    private void getInput() {
        // updates space pressed
        spacePressEvent = event -> {
            // if the event code is space then sets the space pressed
            if (event.getCode() == KeyCode.SPACE) {
                // sets it to true
                spacePressed.set(true);
            }
        };

        // updates space released
        spaceReleaseEvent = event -> {
            // if the event code is space then sets the space released
            if (event.getCode() == KeyCode.SPACE) {
                // sets it to false
                spacePressed.set(false);
            }
        };

        // adds the appropriate event handlers from pressing and releasing space to the current scene
        Utils.getCurrentScene().addEventHandler(KeyEvent.KEY_PRESSED, spacePressEvent);
        Utils.getCurrentScene().addEventHandler(KeyEvent.KEY_RELEASED, spaceReleaseEvent);
    }

    // continues the game
    private void continueGame(Hero hero, Pillar pillar, RotateTransition rotateStick) {
        // moves the hero
        TranslateTransition moveHero = hero.move(pillar.getCurrentX() + pillar.getWidth() - 100, 1000);
        if (rotateStick == null || moveHero == null) {
            return;
        }

        // changes the base pillar
        ParallelTransition rebasePillar = pillar.reBase();
        // when the changing of the pillar is finished
        rebasePillar.setOnFinished(event -> {
            // a new pillar is brought to the screen
            ParallelTransition bringToScreen = new Pillar(false).bringToScreen();

            // when the pillar is brought to screen
            bringToScreen.setOnFinished(event1 -> {
                // animation running is set to false
                animationRunning = false;
                // save button and restart button are enabled
                (Utils.getPane().lookup("#saveButton")).setDisable(false);
                (Utils.getPane().lookup("#restartButton")).setDisable(false);
            });

            // bring to screen animation is played
            bringToScreen.play();
        });


        // rotation of stick and moving of hero are done in sequence
        mainSequence = new SequentialTransition(rotateStick, moveHero);
        // when the screen is rotated start hero move animation timer
        rotateStick.setOnFinished(event -> heroMoveTimer.start());
        // when the hero finishes moving stop hero move animation timer
        moveHero.setOnFinished(event -> heroMoveTimer.stop());

        // when the rotation of stick and moving of hero is completed
        mainSequence.setOnFinished(event -> {
            // if hero is not dead
            if (!hero.isDead()) {
                // score is incremented by one
                Utils.getScore().incrementCurrentScore(1);

                // if a cherry was picked up
                if (pillar.getCherry().isPickedUp()) {
                    // total cherries is incremented
                    Utils.getScore().incrementTotalCherries();
                }

                // the score is updated
                Utils.getScore().updateScore();
                // the pillar rebase animation starts playing
                rebasePillar.play();
            } else {
                // animation running is set to false
                animationRunning = false;
            }
        });
        // main sequence is being played
        mainSequence.play();
    }

    private void gameOver(Hero hero, Stick stick, Pillar pillar, RotateTransition rotateStick) {
        // Move hero by stick length plus an arbitrary value
        TranslateTransition moveHero = hero.move(stick.getScaleY() + 30, 700);
        // the stick is rotated and the hero is moved sequentially
        if (rotateStick == null || moveHero == null) {
            return;
        }
        mainSequence = new SequentialTransition(rotateStick, moveHero);
        // on finishing the hero moving animation the fall and rotate hero function is called
        moveHero.setOnFinished(event -> fallAndRotateHero(hero, stick, pillar));
        // the sequence is played
        mainSequence.play();
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
        // on finishing the fall
        fallRotate.setOnFinished(event -> {
            // animation running is set to false
            animationRunning = false;
            // space is unbound
            unbindSpace();
            // game over text is shown
            showGameOverText();
        });
        // Rotate stick then move hero and then make hero fall out of screen
        fallRotate.play();
    }

    // unbinds space as input
    private void unbindSpace() {
        // removes the event handlers from the current scene
        Utils.getCurrentScene().removeEventHandler(KeyEvent.KEY_PRESSED, spacePressEvent);
        Utils.getCurrentScene().removeEventHandler(KeyEvent.KEY_RELEASED, spaceReleaseEvent);
    }

    private void showGameOverText() {
        // gets all the relevant nodes
        Button reviveButton = (Button) Utils.paneLookup("#reviveButton");
        Label gameOverText = (Label) Utils.paneLookup("#gameOverText");
        Label cherryToRevive = (Label) Utils.paneLookup("#cherryToRevive");
        ImageView cherryReviveImage = (ImageView) Utils.paneLookup("#cherryReviveImage");
        Label highScoreText = (Label) Utils.paneLookup("#highScoreText");
        Label highScore = (Label) Utils.paneLookup("#highScore");
        Score score = Utils.getScore();
        Button restartButton = (Button) Utils.getPane().lookup("#restartButton");

        // if the player has enough cherries to revive then the revive button is enabled
        if (score.getTotalCherries() >= score.getReviveCherries()) {
            reviveButton.setDisable(false);
        }
        // all the game over nodes are made visible
        reviveButton.setVisible(true);
        gameOverText.setVisible(true);
        cherryToRevive.setVisible(true);
        cherryReviveImage.setVisible(true);
        highScoreText.setVisible(true);
        highScore.setVisible(true);
        restartButton.setDisable(false);
    }

    // the hero's move timer is initialized
    public void initHeroMoveTimer() {
        // while the hero animation is running
        heroMoveTimer = new AnimationTimer() {
            // handles each frame of animation
            @Override
            public void handle(long now) {
                // variable declaration with getters
                Hero hero = Utils.getHero();
                Pillar basePillar = Utils.getBasePillar();
                Pillar nextPillar = Utils.getNextPillar();
                Stick stick = nextPillar.getStick();

                // if the hero is between the pillars
                if (hero.isBetweenPillars(basePillar, nextPillar)) {
                    // the hero can be flipped
                    heroFlippable = true;
                    // hero tries to pick up the cherry
                    hero.tryPickUpCherry(nextPillar.getCherry());
                } else {
                    // the hero can't be flipped
                    heroFlippable = false;
                    // if hero is rotated
                    if (hero.getImageView().getRotate() == 180) {
                        // hero dies
                        hero.setDead(true);
                        // animation for dying
                        fallAndRotateHero(hero, stick, basePillar);
                    }
                }
            }
        };
    }
}