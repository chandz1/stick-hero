package stickhero;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.Serializable;

public class Stick extends Rectangle implements Serializable {
    private final RotateTransition rotateAnimator;
    private final ScaleTransition scaleAnimator;
    private final TranslateTransition translateAnimator;

    public Stick(double width, double height, double x, double y) {
        super(width, height, new Color(0, 0, 0, 1));
        super.setX(x);
        super.setY(y);
        this.rotateAnimator = new RotateTransition();
        this.scaleAnimator = new ScaleTransition(Duration.millis(1000), this);
        this.translateAnimator = new TranslateTransition(Duration.millis(1000), this);
    }

    public void rotateStick() {
    }

    public void scaleStick() {
        // scaleAnimator.setByX(1.5f);
        scaleAnimator.setByY(400f);
        translateAnimator.setByY(-200f);
        scaleAnimator.play();
        translateAnimator.play();
    }

    public void getStickX() {
    }

    public void getStickY() {
    }

}
