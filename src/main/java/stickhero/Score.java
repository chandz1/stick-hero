package stickhero;

import java.io.Serializable;

public class Score implements Serializable {
    private int highScore;
    private int currentScore;
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
