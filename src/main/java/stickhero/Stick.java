package stickhero;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.Serializable;

public class Stick extends Rectangle implements Serializable {
    private final RotateTransition rotateAnimator;
    private final ScaleTransition scaleAnimator;
    private final TranslateTransition translateAnimator;
    private final ParallelTransition scaleStick;
    private boolean scaled;

    public Stick() {
        super(5, 1, new Color(0, 0, 0, 1));
        super.setX(97.5);
        super.setY(Utils.getPane().getHeight() - Utils.getBasePillar().getHeight());
        Utils.getPane().getChildren().add(this);
        this.setOpacity(0);
        this.scaled = false;
        this.rotateAnimator = new RotateTransition(Duration.millis(500), this);
        this.scaleAnimator = new ScaleTransition(Duration.millis(1500), this);
        this.translateAnimator = new TranslateTransition(Duration.millis(1500), this);
        this.scaleStick = new ParallelTransition(scaleAnimator,translateAnimator);
    }

    public void scaleStick() {
        if (scaled) {
            return;
        }
        // scale by screen size - height of pillar
        float temp = 1000 - 300;
        scaleAnimator.setByY(temp);
        translateAnimator.setByY(-temp/2);
        scaleStick.play();
    }

    public void stopStick() {
        if (scaled) {
            return;
        }
        scaleStick.stop();
        scaled = true;
        rotateStick();
    }

    private void rotateStick() {
        rotateAnimator.setByAngle(90);
        rotateAnimator.setInterpolator(Interpolator.EASE_IN);
        // Sets y to -0.5*current pivot (which is center) so it effectively sets it to the bottom
        this.getTransforms().add(new Translate(0,-0.5));
        this.setTranslateX(0);
        this.setTranslateY(0);
        rotateAnimator.play();
    }
}