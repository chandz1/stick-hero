package stickhero;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GameController {
    private BooleanProperty spacePressed = new SimpleBooleanProperty();

    public void controlStick() {

        getInput();

        spacePressed.addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                getStick().scaleStick();
            } else {
                RotateTransition rotateStick = getStick().stopStick();
                if (getStick().isWithinBounds(Utils.getNextPillar())) {
                    TranslateTransition moveHero = Utils.getHero().move(0);
                    ParallelTransition rebasePillar = Utils.getNextPillar().reBase();
                    ParallelTransition newPillarToScreen  = new Pillar(false).bringToScreen();
                    SequentialTransition sequence = new SequentialTransition(rotateStick, moveHero, rebasePillar, newPillarToScreen);
                    sequence.play();
                } else {
                    double stickLength = getStick().getScaleY();
                    TranslateTransition moveHero = Utils.getHero().move(stickLength+30);
                    RotateTransition rotate = new RotateTransition(Duration.millis(200), getStick());
                    rotate.setByAngle(90);
                    TranslateTransition fall = new TranslateTransition(Duration.millis(200), Utils.getHero().getSkinView());
                    fall.setByY(400);
                    ParallelTransition fallRotate = new ParallelTransition(rotate, fall);
                    SequentialTransition sequence = new SequentialTransition(rotateStick, moveHero, fallRotate);
                    sequence.play();
                    Text gameOver = new Text(300,500,"Game Over");
                    gameOver.setX(300-gameOver.getLayoutBounds().getWidth()/2);
                    gameOver.setY(500-gameOver.getLayoutBounds().getHeight()/2);
                    Utils.getPane().getChildren().add(gameOver);
                }
            }
        }));
    }

    private void getInput() {
        Utils.getCurrentScene().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                spacePressed.set(true);
            }
        });

        Utils.getCurrentScene().setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                spacePressed.set(false);
            }
        });
    }

    private Stick getStick() {
        return Utils.getBasePillar().getStick();
    }
}