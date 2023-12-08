package stickhero;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.KeyCode;

public class GameController {
    private BooleanProperty spacePressed = new SimpleBooleanProperty();

    public void controlStick() {

        getInput();

        spacePressed.addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                getStick().scaleStick();
            } else {
                RotateTransition rotateStick = getStick().stopStick();
                TranslateTransition moveHero = Utils.getHero().move(0);
                ParallelTransition rebasePillar = Utils.getNextPillar().reBase();
                TranslateTransition newPillarToScreen  = new Pillar(false).bringToScreen(Utils.getBasePillar());
                SequentialTransition sequence = new SequentialTransition(rotateStick, moveHero, rebasePillar, newPillarToScreen);
                sequence.play();
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