package stickhero;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;

import java.io.Serializable;

public class Stick extends Rectangle implements Serializable {
    private final RotateTransition rotateAnimator;
    private final ScaleTransition scaleAnimator;
    private final TranslateTransition translateAnimator;
    private boolean scaled;

    public Stick() {
        super(5, 1, new Color(0, 0, 0, 1));
        super.setX(97.5);
        super.setY(Utils.getPane().getHeight() - Utils.getBasePillar().getHeight());
        Utils.getPane().getChildren().add(this);
        this.setOpacity(0);
        this.scaled = false;
        this.rotateAnimator = new RotateTransition(Duration.millis(700), this);
        this.scaleAnimator = new ScaleTransition(Duration.millis(1500), this);
        this.translateAnimator = new TranslateTransition(Duration.millis(1500), this);
    }

    public void scaleStick() {
        if (scaled) {
            return;
        }
        // scale by screen size - height of pillar
        float temp = 1000 - 300;
        scaleAnimator.setByY(temp);
        translateAnimator.setByY(-temp/2);
        scaleAnimator.play();
        translateAnimator.play();
    }

    public void stopStick() {
        if (scaled) {
            return;
        }
        scaleAnimator.stop();
        translateAnimator.stop();
        scaled = true;
        rotateStick();
    }

    private void rotateStick() {
        rotateAnimator.setByAngle(90);
        this.getTransforms().add(new Translate(0,-0.5));
        this.setTranslateX(0);
        this.setTranslateY(0);
        rotateAnimator.play();
    }
}