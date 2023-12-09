package stickhero;

import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.security.PrivilegedAction;

public class GameController {

    private final BooleanProperty spacePressed = new SimpleBooleanProperty();
    private EventHandler<KeyEvent> spacePressEvent;
    private EventHandler<KeyEvent> spaceReleaseEvent;
    private boolean rotating = false;
    private boolean heroMoving = false;
    private boolean transitioning = false;

    @FXML
    public void saveGame() {
        System.out.println("Ha lame");
    }


    public void controlStick() {

        getInput();

        Button saveButton = (Button) Utils.getPane().lookup("#saveButton");
        saveButton.setFocusTraversable(false);

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
                            hero.getScore().incrementCurrentScore(1);
                        }
                        hero.getScore().incrementCurrentScore(1);
                        Label score = (Label) Utils.getPane().lookup("#score");
                        score.setText(String.valueOf(Utils.getHero().getScore().getCurrentScore()));
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
        });
        sequence.setOnFinished(event -> {
            transitioning = false;
            System.out.println("Transition Complete");
        });
        sequence.play();
    }

    private void checkCollisionOnFlip(TranslateTransition moveHero) {
        Node node = moveHero.getNode();
        new Thread(() -> {
            while(moveHero.getStatus() == Animation.Status.RUNNING) {
                Point2D point2D = new Point2D(node.getTranslateX(), node.getTranslateY());
                System.out.println(point2D);
            }
        });
    }

    private void gameOver(Hero hero, Stick stick, Pillar pillar, RotateTransition rotateStick) {
        // Move hero by stick length plus an arbitrary value
        TranslateTransition moveHero = hero.move(stick.getScaleY(), 700);
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
        Text gameOver = new Text(300,500,"Game Over");
        gameOver.setX(300-gameOver.getLayoutBounds().getWidth()/2);
        gameOver.setY(500-gameOver.getLayoutBounds().getHeight()/2);
        Utils.getPane().getChildren().add(gameOver);
    }
}