package stickhero;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.io.Serializable;

public class Stick extends Rectangle implements Movable {
    private final RotateTransition rotateAnimator;
    private final ScaleTransition scaleAnimator;
    private final TranslateTransition translateAnimator;
    private final ParallelTransition scaleTranslateAnimator;
    private boolean scaled;


    // constructor for stick
    public Stick() {
        // creates a stick with set height and width and color as black
        super(5, 1, new Color(0, 0, 0, 1));
        // sets the position of the stick to the same location everytime
        super.setX(97.5);
        super.setY(Utils.getPane().getHeight() - Utils.getBasePillar().getHeight());
        // puts the stick on screen
        Utils.getPane().getChildren().add(this);
        // makes the stick not visible
        this.setVisible(false);
        // sets scaled to false
        this.scaled = false;
        // sets animators for the stick
        this.rotateAnimator = new RotateTransition(Duration.millis(500), this);
        this.scaleAnimator = new ScaleTransition(Duration.millis(1000), this);
        this.translateAnimator = new TranslateTransition(Duration.millis(1000), this);
        this.scaleTranslateAnimator = new ParallelTransition(scaleAnimator,translateAnimator);
    }

    public void scaleStick() {
        // if the stick has been scaled then it exits
        if (scaled) {
            return;
        }
        // if the game is not in invisible mode
        if (!Utils.isInvisibleMode()) {
            // the stick is made visible
            this.setVisible(true);
        }
        // scale by screen size - height of pillar
        double scaleBy = Utils.getPane().getHeight() - Utils.getBasePillar().getHeight();
        // scaling animation is created and played
        scaleAnimator.setByY(scaleBy);
        translateAnimator.setByY(-scaleBy/2);
        scaleTranslateAnimator.play();
    }

    public RotateTransition stopAndRotateStick() {
        // if stick has already been scaled then it returns nothing
        if (scaled) {
            return null;
        }
        // the scale animator is stopped
        scaleTranslateAnimator.stop();
        // scaled is set to true
        scaled = true;
        // returns the rotate stick transition
        return rotateStick();
    }

    // return the rotate stick transition
    private RotateTransition rotateStick() {
        // sets the angle of the stick
        rotateAnimator.setByAngle(90);
        rotateAnimator.setInterpolator(Interpolator.EASE_IN);
        // Sets y to -0.5*current pivot (which is center) so it effectively sets it to the bottom
        this.getTransforms().add(new Translate(0,-0.5));
        this.setTranslateX(0);
        this.setTranslateY(0);
        // returns the rotation animation
        return rotateAnimator;
    }

    // moves the stick by x
    @Override
    public TranslateTransition move(double x) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300),this);
        // translates by x
        translateTransition.setByX(x);
        return translateTransition;
    }

    // checks whether two values are where the end of the stick is at
    public boolean isWithinBounds(double bound1, double bound2) {
        // finds the distance of the stick at which it is at
        double stickDistance = 97.5 + this.getScaleY();
        // returns if bound1 < stickDistance < bound2
        return bound1 < stickDistance && stickDistance < bound2;
    }

    // checks whether a boundable object will be in bounds of the stick
    public <T extends Boundable> boolean isWithinBounds(T t) {
        // finds the starting bound of the boundable object
        double boundsX1 = t.getCurrentX();
        // finds the ending bound of the boundable object
        double boundsX2 = t.getWidth() + t.getCurrentX();
        // calls the overloaded method to resolve the return
        return isWithinBounds(boundsX1, boundsX2);
    }
}