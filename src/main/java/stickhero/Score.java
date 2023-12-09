package stickhero;

import javafx.scene.control.Label;

import java.io.Serializable;

public class Score implements Serializable {
    private int highScore;
    private int currentScore;
    private int totalCherries;
    private int reviveCherries;

    public Score() {
        this.highScore = 0;
        this.currentScore = 0;
        this.totalCherries = 0;
        this.reviveCherries = 1;
    }

    public void setScore() {
        Utils.setScore(this);
        this.updateScore();
    }

    public void updateScore() {
        Label score = (Label) Utils.getPane().lookup("#score");
        score.setText(String.valueOf(this.getCurrentScore()));
        Label highScore = (Label) Utils.getPane().lookup("#highScore");
        highScore.setText(String.valueOf(this.getHighScore()));
        Label cherry = (Label) Utils.getPane().lookup("#cherryCount");
        cherry.setText(String.valueOf(this.getTotalCherries()));
        Label cherryToRevive = (Label) Utils.getPane().lookup("#cherryToRevive");
        cherryToRevive.setText(String.valueOf(this.getReviveCherries()));
    }

    public void resetCurrentScore() {
        this.currentScore = 0;
    }



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

    public int getReviveCherries() {
        return reviveCherries;
    }

    public void incrementTotalCherries() {
        this.totalCherries++;
    }

    public void setReviveCherries() {
        this.totalCherries -= reviveCherries;
        this.reviveCherries += 1;
    }
}
