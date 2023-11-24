package stickhero;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Stick extends Rectangle implements Serializable {
    private final RotateTransition rotateAnimator;
    private final ScaleTransition scaleAnimator;

    public Stick(double width, double height, double x, double y) {
        super(width, height, new Color(0, 0, 0, 1));
        super.setX(x);
        super.setY(y);
        this.rotateAnimator = new RotateTransition();
        this.scaleAnimator = new ScaleTransition();
    }

    public void rotateStick() {
    }

    public void scaleStick() {
    }

    public void getStickX() {
    }

    public void getStickY() {
    }

}
