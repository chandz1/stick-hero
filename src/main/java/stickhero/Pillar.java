package stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.Random;

import java.io.Serializable;

public class Pillar extends Rectangle implements Serializable {
    // Used arbitrary value for height temporarily will change later

    public Pillar(double x, double width) {
        super(x, 1000-300, width, 300);
        super.setFill(new Color(0, 0, 0, 1));
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
            return 150;
        } else {
            Random rand = new Random();
            return rand.nextInt(280) + 20;
        }
    }

    public void move() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(250),this);
        translateTransition.setByX(-350);
        translateTransition.play();
    }
}
