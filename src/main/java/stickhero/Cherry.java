package stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.Objects;

public class Cherry implements Movable {
    private final Image image;
    private final ImageView imageView;
    private boolean pickedUp;

    public Cherry() {
        this.image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("cherry.png")));
        this.imageView = new ImageView();
        this.imageView.setImage(this.image);
        this.imageView.setX(600);
        this.imageView.setY(Utils.getBasePillar().getY());
        Utils.getPane().getChildren().add(this.imageView);
    }

    public Cherry(double x) {
        this.image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("cherry.png")));
        this.imageView = new ImageView();
        this.imageView.setImage(this.image);
        this.imageView.setX(x);
        this.imageView.setY(Utils.getBasePillar().getY());
        Utils.getPane().getChildren().add(this.imageView);
    }

    @Override
    public TranslateTransition move(double x) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), this.imageView);
        translateTransition.setByX(x);
        return translateTransition;
    }

    public double getImageWidth() {
        return image.getWidth();
    }

    public double getCurrentX() {
        return this.imageView.getX() + this.imageView.getTranslateX();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public double getLeftX() {
        return this.getCurrentX();
    }

    public double getRightX() {
        return this.getCurrentX() + this.image.getWidth();
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void pickedUpTrue() {
        this.pickedUp = true;
        this.imageView.setOpacity(0);
        Utils.getScore().incrementTotalCherries();
    }
}