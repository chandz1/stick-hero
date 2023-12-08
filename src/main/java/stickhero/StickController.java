package stickhero;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.input.KeyCode;

public class StickController {
    private BooleanProperty spacePressed = new SimpleBooleanProperty();

    public void controlStick() {
        Stick stick = Utils.getBasePillar().getStick();

        getInput();

        spacePressed.addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                stick.scaleStick();
            } else {

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
}
