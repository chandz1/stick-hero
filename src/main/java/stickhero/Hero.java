package stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.Objects;

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
        this.imageView.setImage(this.image);
        this.imageView.setX(90-this.image.getWidth());
        this.imageView.setY(Utils.getBasePillar().getY() - this.image.getHeight());
        Utils.getPane().getChildren().add(imageView);
        this.isDead = false;
    }

    @Override
    public TranslateTransition move(double x) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), imageView);
        translateTransition.setByX(x);
        return translateTransition;
    }

    public TranslateTransition move(double x, double duration) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration), imageView);
        translateTransition.setByX(x);
        return translateTransition;
    }

    public double getCurrentX() {
        return this.imageView.getX() + this.imageView.getTranslateX();
    }

    public boolean isBetweenPillars(Pillar pillar, Pillar pillar2) {
        double basePillarBound = pillar.getCurrentX() + pillar.getWidth();
        double nextPillarBound = pillar2.getCurrentX();
        return basePillarBound < this.getCurrentX() && this.getCurrentX() + this.image.getWidth() - 10 < nextPillarBound;
    }

    public void tryPickUpCherry(Cherry cherry) {
        if (this.imageView.getRotate() != 180 || cherry.isPickedUp()) {
            return;
        }
        boolean cherryLeftBool = getLeftX() <= cherry.getLeftX() && cherry.getLeftX() <= getRightX();
        boolean cherryRightBool = getLeftX() <= cherry.getRightX() && cherry.getRightX() <= getRightX();
        if (cherryLeftBool || cherryRightBool) {
            cherry.pickedUpTrue();
        }
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

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
