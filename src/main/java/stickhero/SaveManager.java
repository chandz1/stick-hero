package stickhero;

import javafx.scene.layout.Pane;

import java.io.Serializable;

public final class SaveManager implements Serializable {

    private static SaveManager saveManager = null;

    private Pillar basePillar = null;
    private Pillar nextPillar = null;
    private Hero hero = null;


    private SaveManager() {
    }

    private void setCurrentState() {
        basePillar = Utils.getBasePillar();
        nextPillar = Utils.getNextPillar();
        hero = Utils.getHero();
    }

    public static SaveManager getInstance() {
        if (saveManager == null) {
            saveManager = new SaveManager();
        }
        saveManager.setCurrentState();
        return saveManager;
    }

    public void save() {

    }
    public void load() {
    }
}
