package stickhero;

import javafx.scene.layout.Pane;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

    public void save() throws IOException {
        ObjectOutputStream baseStream = null;
        ObjectOutputStream nextStream = null;
        ObjectOutputStream heroStream = null;

        try {
            baseStream = new ObjectOutputStream(new FileOutputStream("basePillar.txt"));
            nextStream = new ObjectOutputStream(new FileOutputStream("nextPillar.txt"));
            heroStream = new ObjectOutputStream(new FileOutputStream("heroPillar.txt"));
            baseStream.writeObject(basePillar);
            nextStream.writeObject(nextPillar);
            heroStream.writeObject(heroStream);
        } finally {
            if (baseStream != null) {
                baseStream.close();
            }
            if (nextStream != null) {
                nextStream.close();
            }
            if (heroStream != null) {
                heroStream.close();
            }
        }
    }
    public void load() throws IOException {
    }
}
