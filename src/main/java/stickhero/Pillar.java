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

public class Pillar extends Rectangle implements Serializable, Movable, Boundable {
    // Used arbitrary value for height temporarily will change later
    private Stick stick;
    private Stick prevStick;
    private BonusZone bonusZone;
    private Cherry cherry;

    public Pillar(double x, double width) {
        super(x, 1000-300, width, 300);
        super.setFill(new Color(0, 0, 0, 1));
        Utils.getPane().getChildren().add(this);
    }
    // Didn't create getters and setters since they are inherited from Rectangle
    public Pillar(boolean initialPillar) {
        this(pillarGenX(initialPillar), pillarGenWidth(initialPillar));
        if (initialPillar) {
            Utils.setBasePillar(this);
        } else {
            Utils.setNextPillar(this);
            this.bonusZone = new BonusZone((int) (this.getX() + this.getWidth()/2 - 6));
            this.cherry = new Cherry();
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

    public BonusZone getBonusZone() {
        return bonusZone;
    }

    @Override
    public TranslateTransition move(double x) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300),this);
        translateTransition.setByX(x);
        return translateTransition;
    }

    public ParallelTransition moveWithBonus(double x) {
        if (this.bonusZone == null) {
            return new ParallelTransition(this.move(x));
        } else {
            if (this.cherry == null) {
                return new ParallelTransition(this.move(x), this.bonusZone.move(x));
            } else {
                return new ParallelTransition(this.move(x), this.bonusZone.move(x), this.cherry.move(x));
            }
        }
    }

    public ParallelTransition moveWithBonus(double x, boolean y) {
        if (y){
            if (this.bonusZone == null) {
                return new ParallelTransition(this.move(x));
            } else {
                return new ParallelTransition(this.move(x), this.bonusZone.move(x));
            }
        } else {
            return moveWithBonus(x);
        }
    }

    public ParallelTransition bringToScreen() {
        Random rand = new Random();

        // width of the pane subtracted by width of the pillar and the amount of size of basePillar and padding of 64 between pillar and screen
        double randomize = Utils.getPane().getWidth() - this.getWidth() - 228;
        double translate = rand.nextDouble(randomize) + this.getWidth() + 64;

        if (cherry == null) {
            return this.moveWithBonus(-translate);
        } else {
            double cherryTranslate = translate + this.cherry.getImageWidth() + rand.nextDouble(500 - translate - this.cherry.getImageWidth());
            return new ParallelTransition(this.moveWithBonus(-translate, true), this.cherry.move(-cherryTranslate));
        }
    }

    public double screenTranslateValue() {
        return -(this.getCurrentX() + this.getWidth() - 100);
    }

    public ParallelTransition removeFromScreen(Pillar newBase) {
        return this.moveWithBonus(newBase.screenTranslateValue());
    }

    public ParallelTransition moveToBase() {
        return this.moveWithBonus(this.screenTranslateValue());
    }

    public TranslateTransition moveStick(Pillar newBase) {
        return stick.move(newBase.screenTranslateValue());
    }
    public TranslateTransition moveHero(Pillar newBase) {
        return Utils.getHero().move(newBase.screenTranslateValue());
    }

    public ParallelTransition reBase() {
        Pillar prevBase = Utils.getBasePillar();
        prevStick = prevBase.getStick();
        TranslateTransition rebaseHero = moveHero(this);
        rebaseHero.setOnFinished(actionEvent -> {
            Utils.setBasePillar(this);
        });
        if (prevBase.getPrevStick() == null) {
            return new ParallelTransition(prevBase.removeFromScreen(this), this.moveToBase(), prevBase.moveStick(this), rebaseHero);
        } else {
            return new ParallelTransition(prevBase.removeFromScreen(this), this.moveToBase(), prevBase.moveStick(this), prevBase.getPrevStick().move(this.screenTranslateValue()),rebaseHero);
        }
    }
}
