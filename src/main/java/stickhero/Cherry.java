package stickhero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public class Cherry implements Serializable {
    private final Image image;
    private final ImageView imageView;

    public Cherry(Image image) {
        this.image = image;
        this.imageView = new ImageView();
    }
}
