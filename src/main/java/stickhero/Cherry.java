package stickhero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public class Cherry implements Serializable {
    private final Image image;
    private final ImageView imageView;
    private int x;
    private int y;

    public Cherry(Image image, int x, int y) {
        this.image = image;
        this.imageView = new ImageView();
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
