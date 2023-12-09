package stickhero;

import javafx.scene.control.Label;

import java.io.Serializable;

public class Score implements Serializable {
    private int highScore;
    private int currentScore;

    public Score(int highScore, int currentScore, int totalCherries) {
        this.highScore = highScore;
        this.currentScore = currentScore;
        this.totalCherries = totalCherries;
    }

    public Score() {
        this.highScore = 0;
        this.currentScore = 0;
        this.totalCherries = 0;
    }

    public void setScore() {
        Utils.setScore(this);
        this.updateScore();
    }

    public void updateScore() {
        Label score = (Label) Utils.getPane().lookup("#score");
        score.setText(String.valueOf(Utils.getScore().getCurrentScore()));
    }

    private int totalCherries;

    public int getCurrentScore() {
        return currentScore;
    }

    public void incrementCurrentScore(int increment) {
        this.currentScore += increment;
        if (this.currentScore > this.highScore) {
            this.highScore = this.currentScore;
        }
    }

    public int getHighScore() {
        return highScore;
    }

    public int getTotalCherries() {
        return totalCherries;
    }

    public void incrementTotalCherries(int increment) {
        this.totalCherries += increment;
    }

    public void decrementTotalCherries(int decrement) {
        this.totalCherries -= decrement;
    }
}
