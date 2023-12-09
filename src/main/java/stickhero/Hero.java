package stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Objects;

// Hero is movable. image of the hero that plays the game
public class Hero implements Movable {
    private final Image image;
    private final ImageView imageView;
    private boolean isDead;

    // Hero Constructor
    public Hero() {
        // loads the hero image
        this.image = new Image(Objects.requireNonNull(Game.class.getResourceAsStream("hero.png")));
        // creates a node for the hero
        this.imageView = new ImageView();
        // sets the imageView to the loaded image
        this.imageView.setImage(this.image);
        // sets the X and Y position (the position of hero is always preset accordingly)
        this.imageView.setX(90-this.image.getWidth());
        this.imageView.setY(Utils.getBasePillar().getY() - this.image.getHeight());
        // puts the imageView on screen
        Utils.getPane().getChildren().add(imageView);
        // hero is not dead on spawn
        this.isDead = false;
    }

    // returns TranslateTransition to move the hero imageView by x
    @Override
    public TranslateTransition move(double x) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), imageView);
        translateTransition.setByX(x);
        return translateTransition;
    }

    // returns TranslateTransition to move the hero imageView by x for a certain duration
    public TranslateTransition move(double x, double duration) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration), imageView);
        translateTransition.setByX(x);
        return translateTransition;
    }

    // getter for the current X position
    public double getCurrentX() {
        return this.imageView.getX() + this.imageView.getTranslateX();
    }

    // checks whether the hero is in between two pillars
    public boolean isBetweenPillars(Pillar pillar, Pillar pillar2) {
        // gets the bounds of the two pillars
        double basePillarBound = pillar.getCurrentX() + pillar.getWidth();
        double nextPillarBound = pillar2.getCurrentX();
        // returns if the hero is in between them
        return basePillarBound < this.getCurrentX() && this.getCurrentX() + this.image.getWidth() - 10 < nextPillarBound;
    }

    // tries to pick up the cherry
    public void tryPickUpCherry(Cherry cherry) {
        // if the cherry is already picked up or if the hero is not in a state to pick up the cherry it doesn't let you pick up the cherry
        if (this.imageView.getRotate() != 180 || cherry.isPickedUp()) {
            return;
        }
        // checks whether the left of the cherry collides with the hero
        boolean cherryLeftBool = getLeftX() <= cherry.getLeftX() && cherry.getLeftX() <= getRightX();
        // checks whether the right of the cherry collides with the hero
        boolean cherryRightBool = getLeftX() <= cherry.getRightX() && cherry.getRightX() <= getRightX();

        if (cherryLeftBool || cherryRightBool) {
            // if the cherry collides then the cherry is picked up
            cherry.pickedUpTrue();
        }
    }

    // gets the hero's imageView
    public ImageView getImageView() {
        return imageView;
    }

    // gets the left X pos of hero
    public double getLeftX() {
        return this.getCurrentX();
    }

    // gets the right X pos of hero
    public double getRightX() {
        return this.getCurrentX() + this.image.getWidth();
    }

    // gets whether hero isDead
    public boolean isDead() {
        return isDead;
    }

    // sets whether the hero is dead
    public void setDead(boolean dead) {
        isDead = dead;
    }
}
