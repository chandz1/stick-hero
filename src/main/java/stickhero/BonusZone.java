package stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class BonusZone extends Rectangle implements Movable {
    private Pane parentPane;
    public BonusZone(int x) {
        super(x, 1000-300, 12, 8);
        super.setFill(new Color(1, 0, 0, 1));

        this.parentPane = Utils.getPane();
        this.parentPane.getChildren().add(this);
    }

    @Override
    public TranslateTransition move(double x) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300),this);
        translateTransition.setByX(x);
        return translateTransition;
    }
}
