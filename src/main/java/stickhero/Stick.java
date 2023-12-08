package stickhero;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.Serializable;

public class Stick extends Rectangle implements Serializable, Movable {
    private final RotateTransition rotateAnimator;
    private final ScaleTransition scaleAnimator;
    private final TranslateTransition translateAnimator;
    private final ParallelTransition scaleTranslateAnimator;
    private boolean scaled;

    public Stick() {
        super(5, 1, new Color(0, 0, 0, 1));
        super.setX(97.5);
        super.setY(Utils.getPane().getHeight() - Utils.getBasePillar().getHeight());
        Utils.getPane().getChildren().add(this);
        this.setOpacity(0);
        this.scaled = false;
        this.rotateAnimator = new RotateTransition(Duration.millis(500), this);
        this.scaleAnimator = new ScaleTransition(Duration.millis(1000), this);
        this.translateAnimator = new TranslateTransition(Duration.millis(1000), this);
        this.scaleTranslateAnimator = new ParallelTransition(scaleAnimator,translateAnimator);
    }

    public void scaleStick() {
        if (scaled) {
            return;
        }
        // scale by screen size - height of pillar
        this.setOpacity(1);
        double scaleBy = Utils.getPane().getHeight() - Utils.getBasePillar().getHeight() - 100;
        scaleAnimator.setByY(scaleBy);
        translateAnimator.setByY(-scaleBy/2);
        scaleTranslateAnimator.play();
    }

    public RotateTransition stopStick() {
        if (scaled) {
            return null;
        }
        scaleTranslateAnimator.stop();
        scaled = true;
        System.out.println(isWithinBounds(Utils.getNextPillar()));
        return rotateStick();
    }

    private RotateTransition rotateStick() {
        rotateAnimator.setByAngle(90);
        rotateAnimator.setInterpolator(Interpolator.EASE_IN);
        // Sets y to -0.5*current pivot (which is center) so it effectively sets it to the bottom
        this.getTransforms().add(new Translate(0,-0.5));
        this.setTranslateX(0);
        this.setTranslateY(0);
        return rotateAnimator;
    }

    @Override
    public TranslateTransition move(double x) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300),this);
        translateTransition.setByX(x);
        return translateTransition;
    }

    public boolean isWithinBounds(Pillar bounds) {
        double stickDistance = 97.5 + this.getScaleY();
        double boundsX1 = bounds.getCurrentX();
        double boundsX2 = bounds.getWidth() + bounds.getCurrentX();
        return boundsX1 < stickDistance && stickDistance < boundsX2;
    }
}