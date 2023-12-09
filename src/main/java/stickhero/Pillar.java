package stickhero;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import java.io.Serializable;

// pillar is movable and boundable and is essentially a rectangle. pillar are the pillars that the hero traverses between
public class Pillar extends Rectangle implements Movable, Boundable {
    // Used arbitrary value for height temporarily will change later
    private Stick stick;
    private Stick prevStick;
    private BonusZone bonusZone;
    private Cherry cherry;

    // pillar constructor with x position and width
    public Pillar(double x, double width) {
        // the rectangle is created using the rectangle constructor
        super(x, 1000-300, width, 300);
        // the fill of the rectangle is set to black
        super.setFill(new Color(0, 0, 0, 1));
        // the rectangle is added to screen
        Utils.getPane().getChildren().add(this);
    }
    // Didn't create getters and setters since they are inherited from Rectangle

    // pillar constructor with whether it is the initial pillar
    public Pillar(boolean initialPillar) {
        // the overloaded constructor is called with generation of the pillars X position and width based on whether it is the initial pillar
        this(pillarGenX(initialPillar), pillarGenWidth(initialPillar));
        // if it is the initial pillar
        if (initialPillar) {
            // it is set as the base pillar
            Utils.setBasePillar(this);
        } else {
            // it is set as the next pillar
            Utils.setNextPillar(this);
            // bonus zone is created in the middle of the pillar
            this.bonusZone = new BonusZone(this.getX() + this.getWidth()/2 - 6);
            // cherry is created
            this.cherry = new Cherry();
        }
        // stick is created
        this.stick = new Stick();
    }

    // constructor for genning a pillar
    public Pillar(boolean initialPillar, double x, double width, double bonusZoneX, double cherryX, boolean prevStickExists) {
        // the overloaded constructor is called
        this(x, width);
        // if it is the initial pillar
        if (initialPillar) {
            // it is set as base pillar
            Utils.setBasePillar(this);
        } else {
            // it is set as next pillar
            Utils.setNextPillar(this);
            // bonusZone is set at bonusZoneX
            this.bonusZone = new BonusZone(bonusZoneX);
            // cherry is set at cherryX
            this.cherry = new Cherry(cherryX);
        }
        // stick is created
        this.stick = new Stick();
        if (prevStickExists && initialPillar) {
            // previous stick is created
            this.prevStick = new Stick();
            this.prevStick.setVisible(true);
            this.prevStick.setX(-10);
            ParallelTransition parallel = stickParallelSpawn();
            System.out.println("Playing");
            parallel.play();
        }
    }

    @NotNull
    private ParallelTransition stickParallelSpawn() {
        ScaleTransition scaleStick = new ScaleTransition(Duration.millis(1), this.prevStick);
        TranslateTransition translateStick = new TranslateTransition(Duration.millis(1), this.prevStick);
        RotateTransition rotateStick = new RotateTransition(Duration.millis(1), this.prevStick);
        double scaleBy = 100;
        scaleStick.setByY(scaleBy);
        translateStick.setByY(-scaleBy/2);
        rotateStick.setByAngle(90);
        ParallelTransition parallel = new ParallelTransition(scaleStick, translateStick);
        parallel.setOnFinished(event -> {
            this.prevStick.getTransforms().add(new Translate(0,-0.5));
            this.prevStick.setTranslateX(0);
            this.prevStick.setTranslateY(0);
            rotateStick.play();
        });
        return parallel;
    }

    // getter for the previous stick
    public Stick getPrevStick() {
        return prevStick;
    }

    // generates the X position for the pillar
    public static int pillarGenX(boolean initialPillar) {
        // if it is the initial pillar
        if (initialPillar) {
            // it will be on the left edge of the screen
            return 0;
        } else {
            // it will be outside the screen
            return 600;
        }
    }

    // generates the width of the pillar
    public static int pillarGenWidth(boolean initialPillar) {
        // if it is the initial pillar
        if (initialPillar) {
            // it will be a set size
            return 100;
        } else {
            // it will be a randomly generated size
            Random rand = new Random();
            return rand.nextInt(280) + 20;
        }
    }

    // gets the stick of the pillar
    public Stick getStick() {
        return stick;
    }

    // gets the current X of the pillar
    public double getCurrentX() {
        return this.getX() + this.getTranslateX();
    }

    // gets the bonus zone of the pillar
    public BonusZone getBonusZone() {
        return bonusZone;
    }

    // gets the cherry of the pillar
    public Cherry getCherry() {
        return cherry;
    }

    // returns TranslateTransition to move the pillar by x
    @Override
    public TranslateTransition move(double x) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300),this);
        // sets x translation
        translateTransition.setByX(x);
        return translateTransition;
    }

    // moves the pillar along with the bonus zone and cherry
    public ParallelTransition moveWithBonus(double x) {
        // if bonus zone doesn't exist
        if (this.bonusZone == null) {
            // move the pillar only
            return new ParallelTransition(this.move(x));
        } else {
            // if the cherry doesn't exist
            if (this.cherry == null) {
                // move the pillar and the bonus zone
                return new ParallelTransition(this.move(x), this.bonusZone.move(x));
            } else {
                // move the pillar, bonus zone and the cherry
                return new ParallelTransition(this.move(x), this.bonusZone.move(x), this.cherry.move(x));
            }
        }
    }

    // if you want to move the bonus zone without the pillar
    public ParallelTransition moveWithBonus(double x, boolean ignoreCherry) {
        // if ignoring cherry
        if (ignoreCherry){
            // if bonus zone doesn't exist
            if (this.bonusZone == null) {
                // only move pillar
                return new ParallelTransition(this.move(x));
            } else {
                // move pillar and bonus zone
                return new ParallelTransition(this.move(x), this.bonusZone.move(x));
            }
        } else {
            // move everything normally
            return moveWithBonus(x);
        }
    }

    public ParallelTransition bringToScreen() {
        Random rand = new Random();

        // width of the pane subtracted by width of the pillar and the amount of size of basePillar and padding of 64 between pillar and screen
        double heroSize = Utils.getHero().getImageView().getImage().getWidth();
        double randomize = Utils.getPane().getWidth() - this.getWidth() - (heroSize * 6) - 100;
        // how much the pillar needs to translate
        double translate = rand.nextDouble(randomize) + this.getWidth() + heroSize;

        // if cherry doesn't exist
        if (cherry == null) {
            // move pillar by translate
            return this.moveWithBonus(-translate);
        } else {
            // the cherry needs to translate another amount (as they are being placed)
            double cherryTranslate = translate + this.cherry.getImageWidth() + rand.nextDouble(500 - translate - this.cherry.getImageWidth());
            // move pillar by translate and move cherry by cherryTranslate
            return new ParallelTransition(this.moveWithBonus(-translate, true), this.cherry.move(-cherryTranslate));
        }
    }

    // returns the screen translate value (so that everything is removed at uniform speed). it is the distance required for the current pillar to move to base
    public double screenTranslateValue() {
        return -(this.getCurrentX() + this.getWidth() - 100);
    }

    // removes the pillar from the screen
    public ParallelTransition removeFromScreen(Pillar newBase) {
        return this.moveWithBonus(newBase.screenTranslateValue());
    }

    // moves the current pillar to base
    public ParallelTransition moveToBase() {
        return this.moveWithBonus(this.screenTranslateValue());
    }

    // moves the stick of the previous stick back
    public TranslateTransition moveStick(Pillar newBase) {
        return stick.move(newBase.screenTranslateValue());
    }

    // moves the hero
    public TranslateTransition moveHero(Pillar newBase) {
        return Utils.getHero().move(newBase.screenTranslateValue());
    }

    // makes the current pillar into the new base
    public ParallelTransition reBase() {
        // gets the base that is soon going to become the previous base
        Pillar prevBase = Utils.getBasePillar();
        // gets the stick that is soon to become the previous stick and puts it to the current stick
        prevStick = prevBase.getStick();
        // moves the hero with the rebased pillar
        TranslateTransition rebaseHero = moveHero(this);
        // once the hero is rebased
        rebaseHero.setOnFinished(actionEvent -> {
            // the base pillar is set
            Utils.setBasePillar(this);
        });
        // removes the previous base
        ParallelTransition removePrevBase = prevBase.removeFromScreen(this);
        // places the current pillar
        ParallelTransition reBaseThisPillar = this.moveToBase();
        // removes previous base stick
        TranslateTransition removePrevBaseStick = prevBase.moveStick(this);
        // checks if previous base had previous stick
        if (prevBase.getPrevStick() == null) {
            // rebase transition is created in parallel
            return new ParallelTransition(removePrevBase, reBaseThisPillar, removePrevBaseStick, rebaseHero);
        } else {
            // removes previous base previous stick
            TranslateTransition removePrevBasePrevStick = prevBase.getPrevStick().move(this.screenTranslateValue());
            // rebase transition is created in parallel (and with previous stick)
            return new ParallelTransition(removePrevBase, reBaseThisPillar, removePrevBaseStick, removePrevBasePrevStick, rebaseHero);
        }
    }
}
