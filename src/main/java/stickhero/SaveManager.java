package stickhero;

import javafx.scene.control.Button;

import java.io.*;

// SaveManager is a Singleton class which is Serializable
public final class SaveManager implements Serializable {
    private static SaveManager saveManager = null;
    private PillarSaver basePillar;
    private PillarSaver nextPillar;
    private Score score;

    // constructor for save manager is private
    private SaveManager() {
        this.basePillar = null;
        this.nextPillar = null;
        this.score = null;
    }

    // sets the current state of the game
    private void setCurrentState() {
        // score assigned
        score = Utils.getScore();
        // if hero is not dead
        if (!Utils.getHero().isDead()) {
            // pillar savers are created for the base pillar and next pillar
            basePillar = new PillarSaver(Utils.getBasePillar());
            nextPillar = new PillarSaver(Utils.getNextPillar());
        } else {
            // pillars are set to null
            this.basePillar = null;
            this.nextPillar = null;
            score.resetCurrentScore();
        }
    }


    // gets the singleton instance of save manager
    public static SaveManager getInstance() {
        // if save manager doesn't exist
        if (saveManager == null) {
            // save manager is created
            saveManager = new SaveManager();
        }
        // singleton object is returned
        return saveManager;
    }

    // saves the current state of the game
    public void save() throws IOException {
        // tries to write to save.dat
        try (ObjectOutputStream saveFile = new ObjectOutputStream(new FileOutputStream("save.dat"))) {
            // sets the current state of the game to save manager
            saveManager.setCurrentState();
            // writes all the save manager to save.dat
            saveFile.writeObject(saveManager);
        }
    }

    // loads the current state of the game
    public void load() throws IOException, ClassNotFoundException {
        // tries to read save.dat
        try (ObjectInputStream loadFile = new ObjectInputStream(new FileInputStream("save.dat"))) {
            // reads the save manager in the save.dat file
            saveManager = (SaveManager) loadFile.readObject();
        }
        // base pillar is not null
        if (saveManager.basePillar != null) {
            // creates the base pillar
            saveManager.basePillar.createPillar(true);
        } else {
            new Pillar(true);
        }

        // creates a hero
        Hero hero = new Hero();
        Utils.setHero(hero);

        if (saveManager.nextPillar != null) {
            // creates the next pillar
            saveManager.nextPillar.createPillar(false);
        } else {
            new Pillar(false).bringToScreen().play();
        }
        // sets the score
        saveManager.score.setScore();
    }
}
