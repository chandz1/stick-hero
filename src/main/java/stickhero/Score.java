package stickhero;

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

    public void incrementCurrentScore(int currentScore) {
        this.currentScore += 1;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getTotalCherries() {
        return totalCherries;
    }

    public void setTotalCherries(int totalCherries) {
        this.totalCherries = totalCherries;
    }
}
