package stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.Serializable;

public class BonusZone extends Rectangle implements Movable, Boundable, Serializable {
    public BonusZone(int x) {
        super(x, 1000-300, 12, 8);
        super.setFill(new Color(1, 0, 0, 1));

        Utils.getPane().getChildren().add(this);
    }

    public void setBonusZone() {
        Utils.getPane().getChildren().add(this);
    }

    @Override
    public TranslateTransition move(double x) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300),this);
        translateTransition.setByX(x);
        return translateTransition;
    }

    public double getCurrentX() {
        return this.getX() + this.getTranslateX();
    }
}
