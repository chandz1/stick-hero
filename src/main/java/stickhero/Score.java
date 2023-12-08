package stickhero;

import javafx.beans.value.ObservableValue;

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
