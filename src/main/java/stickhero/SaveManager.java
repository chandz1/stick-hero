package stickhero;

import java.io.*;

public final class SaveManager implements Serializable {

    private static SaveManager saveManager = null;

    private Pillar basePillar = null;
    private Pillar nextPillar = null;
    private Score score = null;
    private int x;


    private SaveManager() {
    }

    private void setCurrentState() {
        basePillar = Utils.getBasePillar();
        nextPillar = Utils.getNextPillar();
        score = Utils.getScore();
    }

    public static SaveManager getInstance() {
        if (saveManager == null) {
            saveManager = new SaveManager();
        }
        saveManager.setCurrentState();
        return saveManager;
    }

    public void save() throws IOException {
        ObjectOutputStream saveFile = null;

        try {
            saveFile = new ObjectOutputStream(new FileOutputStream("save.dat"));
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
        saveManager.basePillar.setPillar(true);
        saveManager.nextPillar.setPillar(false);
        saveManager.score.setScore();
    }
}
