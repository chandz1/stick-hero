package stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.Objects;

// cherry is movable. cherry image which is a collectible.
public class Cherry implements Movable {
    private final Image image;
    private final ImageView imageView;
    private boolean pickedUp;

    // cherry constructor without any parameters
    public Cherry() {
        // loads the cherry image
        this.image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("cherry.png")));
        // creates a node for the cherry
        this.imageView = new ImageView();
        // sets the imageView to the loaded image
        this.imageView.setImage(this.image);
        // sets the X and Y position (currently cherry will be off screen and the y position will be parallel to cherry)
        this.imageView.setX(600);
        this.imageView.setY(Utils.getBasePillar().getY());
        // put the imageView on screen
        Utils.getPane().getChildren().add(this.imageView);
    }

    // cherry constructor with x position
    public Cherry(double x) {
        // loads the cherry image
        this.image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("cherry.png")));
        // creates a node for the cherry
        this.imageView = new ImageView();
        // sets the imageView to the loaded image
        this.imageView.setImage(this.image);
        // sets the X and Y position (x position will be decided based on the parameter and the y position will be parallel to cherry)
        this.imageView.setX(x);
        this.imageView.setY(Utils.getBasePillar().getY());
        // put the imageView on screen
        Utils.getPane().getChildren().add(this.imageView);
    }

    // returns TranslateTransition to move the cherry imageView by x
    @Override
    public TranslateTransition move(double x) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), this.imageView);
        // sets x translation
        translateTransition.setByX(x);
        return translateTransition;
    }

    // getter for image width
    public double getImageWidth() {
        return image.getWidth();
    }

    // getter for the current X position
    public double getCurrentX() {
        return this.imageView.getX() + this.imageView.getTranslateX();
    }

    // getter for the left X position
    public double getLeftX() {
        return this.getCurrentX();
    }

    // getter for right X position
    public double getRightX() {
        return this.getCurrentX() + this.image.getWidth();
    }

    // getter for whether or not cherry was picked up
    public boolean isPickedUp() {
        return pickedUp;
    }

    // setter for picking up cherry
    public void pickedUpTrue() {
        this.pickedUp = true;
        this.imageView.setVisible(false);
    }
}