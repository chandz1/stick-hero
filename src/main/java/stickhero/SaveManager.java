package stickhero;

import java.io.*;

public final class SaveManager implements Serializable {

    private static SaveManager saveManager = null;

    private PillarSaver basePillar = null;
    private PillarSaver nextPillar = null;
    private Score score = null;

    private SaveManager() {
    }

    private void setCurrentState() {
        basePillar = new PillarSaver(Utils.getBasePillar());
        nextPillar = new PillarSaver(Utils.getNextPillar());
        score = Utils.getScore();
    }

    public static SaveManager getInstance() {
        if (saveManager == null) {
            saveManager = new SaveManager();
        }
        return saveManager;
    }

    public void save() throws IOException {
        ObjectOutputStream saveFile = null;

        try {
            saveFile = new ObjectOutputStream(new FileOutputStream("save.dat"));
            saveManager.setCurrentState();
            saveFile.writeObject(saveManager);
        } finally {
            if (saveFile != null) {
                saveFile.close();
            }
        }
    }
    public void load() throws IOException, ClassNotFoundException {
        ObjectInputStream loadFile = null;

        try {
            loadFile = new ObjectInputStream(new FileInputStream("save.dat"));
            saveManager = (SaveManager) loadFile.readObject();
        } finally {
            if (loadFile != null) {
                loadFile.close();
            }
        }
        saveManager.basePillar.createPillar(true);
        saveManager.nextPillar.createPillar(false);
        saveManager.score.setScore();
    }
}
