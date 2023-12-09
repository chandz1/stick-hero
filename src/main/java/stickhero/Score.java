package stickhero;

import javafx.scene.control.Label;

import java.io.Serializable;

// Score is serializable as the object needs to be saved. keeps track of cherries and score
public class Score implements Serializable {
    private int highScore;
    private int currentScore;
    private int totalCherries;
    private int reviveCherries;

    // Constructor
    public Score() {
        // sets the score to the default values
        this.highScore = 0;
        this.currentScore = 0;
        this.totalCherries = 0;
        this.reviveCherries = 1;
    }

    // sets the object as "Score"
    public void setScore() {
        Utils.setScore(this);
        this.updateScore();
    }

    // updates the on screen values of score
    public void updateScore() {
        // Label for score is updated to value of current score
        Label score = (Label) Utils.getPane().lookup("#score");
        score.setText(String.valueOf(this.getCurrentScore()));

        // Label for high score is updated to value of high score
        Label highScore = (Label) Utils.getPane().lookup("#highScore");
        highScore.setText(String.valueOf(this.getHighScore()));

        // Label for cherry is updated to value of total cherries
        Label cherry = (Label) Utils.getPane().lookup("#cherryCount");
        cherry.setText(String.valueOf(this.getTotalCherries()));

        // Label for cherries to revive is set to revive cherries
        Label cherryToRevive = (Label) Utils.getPane().lookup("#cherryToRevive");
        cherryToRevive.setText(String.valueOf(this.getReviveCherries()));
    }

    // resets the score
    public void resetCurrentScore() {
        // resets to default values
        this.currentScore = 0;
        this.reviveCherries = 1;
    }

    // gets the score
    public int getCurrentScore() {
        return currentScore;
    }

    // increments the score
    public void incrementCurrentScore(int increment) {
        // adds increment to current score
        this.currentScore += increment;
        // if current score surpasses high score
        if (this.currentScore > this.highScore) {
            // high score is now the current score
            this.highScore = this.currentScore;
        }
    }

    // gets high score
    public int getHighScore() {
        return highScore;
    }

    // gets total cherries
    public int getTotalCherries() {
        return totalCherries;
    }

    // gets revive cherries
    public int getReviveCherries() {
        return reviveCherries;
    }

    // increments the total cherries
    public void incrementTotalCherries() {
        this.totalCherries++;
    }

    // updates the cherries required to update
    public void updateReviveCherries() {
        // subtracts total cherries by the cherries required to revive
        this.totalCherries -= reviveCherries;
        // base 2 exponent increase the number of cherries require
        this.reviveCherries *= 2;
    }
}
