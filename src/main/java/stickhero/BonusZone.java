package stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.Serializable;


// bonusZone is a Movable and Boundable class. The red bonus zone that gives you extra points
public class BonusZone extends Rectangle implements Movable, Boundable {

    // bonusZone Constructor
    public BonusZone(double x) {
        super(x, 1000-300, 12, 8);
        // sets the color to red
        super.setFill(new Color(1, 0, 0, 1));
        // puts the bonus zone on screen
        Utils.getPane().getChildren().add(this);
    }

    // returns TranslateTransition to move the bonus zone by x
    @Override
    public TranslateTransition move(double x) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300),this);
        // sets x translation
        translateTransition.setByX(x);
        return translateTransition;
    }

    // getter for the current X position
    public double getCurrentX() {
        return this.getX() + this.getTranslateX();
    }
}
