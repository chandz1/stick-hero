package stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.Objects;

public class Hero implements Movable {
    private final Image skin;
    private final ImageView skinView;
    private boolean isDead;

    public Hero() {
        this.skin = new Image(Objects.requireNonNull(Game.class.getResourceAsStream("hero.png")));
        this.skinView = new ImageView();
        this.skinView.setImage(this.skin);
        this.skinView.setX(90-this.skin.getWidth());
        this.skinView.setY(Utils.getBasePillar().getY() - this.skin.getHeight());
        Utils.getPane().getChildren().add(skinView);
        this.isDead = false;
    }

    @Override
    public TranslateTransition move(double x) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300),skinView);
        translateTransition.setByX(x);
        return translateTransition;
    }

    public TranslateTransition move(double x, double duration) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration),skinView);
        translateTransition.setByX(x);
        return translateTransition;
    }

    public double getCurrentX() {
        return this.skinView.getX() + this.skinView.getTranslateX();
    }

    public boolean isBetweenPillars(Pillar pillar, Pillar pillar2) {
        double basePillarBound = pillar.getCurrentX() + pillar.getWidth();
        double nextPillarBound = pillar2.getCurrentX();
        return basePillarBound < this.getCurrentX() && this.getCurrentX() + this.skin.getWidth() - 10 < nextPillarBound;
    }

    public boolean pickedUpCherry(Cherry cherry) {
        return false;
    }

    public ImageView getSkinView() {
        return skinView;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
