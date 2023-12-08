package stickhero;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.Random;

import java.io.Serializable;

public class Pillar extends Rectangle implements Serializable, Movable {
    // Used arbitrary value for height temporarily will change later
    private Pane parentPane;
    private Stick stick;
    private Stick prevStick;

    public Pillar(double x, double width) {
        super(x, 1000-300, width, 300);
        super.setFill(new Color(0, 0, 0, 1));
        this.parentPane = Utils.getPane();
        this.parentPane.getChildren().add(this);
    }
    // Didn't create getters and setters since they are inherited from Rectangle
    public Pillar(boolean initialPillar) {
        this(pillarGenX(initialPillar), pillarGenWidth(initialPillar));
        if (initialPillar) {
            Utils.setBasePillar(this);
        } else {
            Utils.setNextPillar(this);
        }
        this.stick = new Stick();
    }

    public Stick getPrevStick() {
        return prevStick;
    }

    public static int pillarGenX(boolean initialPillar) {
        if (initialPillar) {
            return 0;
        } else {
            return 600;
        }
    }

    public static int pillarGenWidth(boolean initialPillar) {
        if (initialPillar) {
            return 100;
        } else {
            Random rand = new Random();
            return rand.nextInt(280) + 20;
        }
    }

    public Stick getStick() {
        return stick;
    }

    public double getCurrentX() {
        return this.getX() + this.getTranslateX();
    }

    @Override
    public TranslateTransition move(double x) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300),this);
        translateTransition.setByX(x);
        return translateTransition;
    }

    public TranslateTransition bringToScreen(Pillar base) {
        Random rand = new Random();

        double randomize = parentPane.getWidth() - base.getWidth() - this.getWidth() - 60;
        double translate = rand.nextDouble(randomize) + this.getWidth() + 30;
        return this.move(-translate);
    }

    public double screenTranslateValue() {
        return -(this.getCurrentX() + this.getWidth() - 100);
    }

    public TranslateTransition removeFromScreen(Pillar newBase) {
        return this.move(newBase.screenTranslateValue());
    }

    public TranslateTransition moveToBase() {
        return this.move(this.screenTranslateValue());
    }

    public TranslateTransition moveStick(Pillar newBase) {
        return stick.move(newBase.screenTranslateValue());
    }

    public ParallelTransition reBase() {
        Pillar prevBase = Utils.getBasePillar();
        Utils.setBasePillar(this);
        prevStick = prevBase.getStick();
        if (prevBase.getPrevStick() == null) {
            return new ParallelTransition(prevBase.removeFromScreen(this), this.moveToBase(), prevBase.moveStick(this));
        } else {
            return new ParallelTransition(prevBase.removeFromScreen(this), this.moveToBase(), prevBase.moveStick(this), prevBase.getPrevStick().move(this.screenTranslateValue()));
        }
    }
}
