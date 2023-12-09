package stickhero;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.Objects;

public class Hero implements Serializable, Movable {
    private Image skin;
    private ImageView skinView;
    private boolean isDead;

    public Hero() {
        this.skin = new Image(Objects.requireNonNull(Game.class.getResourceAsStream("hero.png")));
        this.skinView = new ImageView();
        this.skinView.setImage(this.skin);
        this.skinView.setX(Utils.getBasePillar().getWidth()-this.skin.getWidth()-10);
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
