package stickhero;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Pillar extends Rectangle implements Serializable {
    // Used arbitrary value for height temporarily will change later
    private final int height = 200;

    public Pillar(double x, double width) {
        super(x, 1000-200, width, 200);
        super.setFill(new Color(0, 0, 0, 1));
    }
    // Didn't create getters and setters since they are inherited from Rectangle
}
