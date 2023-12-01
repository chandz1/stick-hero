package stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.Serializable;

public class Pillar extends Rectangle implements Serializable {
    // Used arbitrary value for height temporarily will change later

    public Pillar(double x, double width) {
        super(x, 1000-300, width, 300);
        super.setFill(new Color(0, 0, 0, 1));
    }
    // Didn't create getters and setters since they are inherited from Rectangle

    public void move() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000),this);
        translateTransition.setByX(-500);
        translateTransition.play();
    }
}
