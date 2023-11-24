package stickhero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

public class Hero implements Serializable {
    private Image skin;
    private ImageView skinManager;
    private int currX;
    private int currY;
    private Score score;
    private boolean isDead;

    public Hero(Image skin, int currX, int currY) {
        this.skin = skin;
        this.skinManager = new ImageView();
        this.currX = currX;
        this.currY = currY;
        this.score = new Score();
        this.isDead = false;
    }

    public int getCurrX() {
        return currX;
    }

    public void setCurrX(int currX) {
        this.currX = currX;
    }

    public int getCurrY() {
        return currY;
    }

    public void setCurrY(int currY) {
        this.currY = currY;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
