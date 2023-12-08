package stickhero;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.Random;

import java.io.Serializable;

public class Pillar extends Rectangle implements Serializable {
    // Used arbitrary value for height temporarily will change later
    private Pane parentPane;

    public Pillar(double x, double width) {
        super(x, 1000-300, width, 300);
        super.setFill(new Color(0, 0, 0, 1));
        parentPane = Utils.getPane();
        parentPane.getChildren().add(this);
    }
    // Didn't create getters and setters since they are inherited from Rectangle
    public Pillar(boolean initialPillar) {
        this(pillarGenX(initialPillar), pillarGenWidth(initialPillar));
    }

    public static int pillarGenX(boolean initialPillar) {
        if (initialPillar) {
            return 0;
        } else {
            return 600;
        }
    }

    public static int pillarGenWidth(boolean initialPillar) {
        if (initialPillar) {
            return 100;
        } else {
            Random rand = new Random();
            return rand.nextInt(280) + 20;
        }
    }

    public double getCurrentX() {
        return 600.0 + this.getTranslateX();
    }

    public TranslateTransition move(double x) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300),this);
        translateTransition.setByX(x);
        return translateTransition;
    }

    public TranslateTransition bringToScreen(Pillar base) {
        Random rand = new Random();

        double randomize = parentPane.getWidth() - base.getWidth() - this.getWidth() - 60;
        double translate = rand.nextDouble(randomize) + this.getWidth() + 30;
        return this.move(-translate);
    }

    public TranslateTransition removeFromScreen() {
        return this.move(-(this.getWidth() + this.getX()));
    }

    public TranslateTransition moveToBase() {
        return this.move(-this.getCurrentX() - this.getWidth() + 100);
    }
}
