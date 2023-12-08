package stickhero;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController {
    private BooleanProperty spacePressed = new SimpleBooleanProperty();

    public void controlStick() {

        getInput();

        spacePressed.addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                getStick().scaleStick();
            } else {
                RotateTransition rotate = getStick().stopStick();
                SequentialTransition sequence = new SequentialTransition(rotate, Utils.getNextPillar().reBase(), new Pillar(false).bringToScreen(Utils.getBasePillar()));
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