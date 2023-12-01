package stickhero;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.Serializable;

public class Pillar extends Rectangle implements Serializable {
    // Used arbitrary value for height temporarily will change later

    public Pillar(double width) {
        super(0, 1000-300, width, 300);
        super.setFill(new Color(0, 0, 0, 1));
    }
    // Didn't create getters and setters since they are inherited from Rectangle
}
