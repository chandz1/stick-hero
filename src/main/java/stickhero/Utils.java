package stickhero;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public final class Utils {
    private static Hero hero;
    static MediaPlayer bgMusic;
    private static Pane currentPane;
    private static Scene currentScene;
    private static Pillar basePillar;
    private static Pillar nextPillar;
    private static boolean invisibleMode;

    private static Score score;

    // gets whether invisible mode is on
    public static boolean isInvisibleMode() {
        return invisibleMode;
    }

    // sets the invisible mode
    public static void setInvisibleMode(boolean invisibleMode) {
        Utils.invisibleMode = invisibleMode;
    }

    // sets the score
    public static void setScore(Score score) {
        Utils.score = score;
    }

    // gets the score
    public static Score getScore() {
        return score;
    }

    // gets the hero
    public static Hero getHero() {
        return hero;
    }

    // sets the hero
    public static void setHero(Hero hero) {
        Utils.hero = hero;
    }

    // sets the pane
    public static void setPane(Pane pane) {
        currentPane = pane;
    }

    // gets the pane
    public static Pane getPane() {
        return currentPane;
    }

    // sets the base pillar
    public static void setBasePillar(Pillar pillar) {
        basePillar = pillar;
    }

    // gets the base pillar
    public static Pillar getBasePillar() {
        return basePillar;
    }

    // sets the current scene
    public static void setCurrentScene(Scene scene) {
        currentScene = scene;
    }

    // gets the current scene
    public static Scene getCurrentScene() {
        return currentScene;
    }

    // sets the next pillar
    public static void setNextPillar(Pillar nextPillar) {
        Utils.nextPillar = nextPillar;
    }

    // gets the next pillar
    public static Pillar getNextPillar() {
        return nextPillar;
    }

    // looks up the node in the pane and returns it
    public static Node paneLookup(String lookup) { return getPane().lookup(lookup); }

    public static void playCorrect() {
        MediaPlayer correct = new MediaPlayer(new Media(String.valueOf(Utils.class.getResource("correct.wav"))));
        correct.play();
    }
    public static void playLose() {
        MediaPlayer loss = new MediaPlayer(new Media(String.valueOf(Utils.class.getResource("lose.wav"))));
        loss.play();
    }
    public static void playBGMusic() {
        Media bgMusicMedia = new Media(Objects.requireNonNull(Utils.class.getResource("bgmusic.wav")).toString());
        bgMusic = new MediaPlayer(bgMusicMedia);
        bgMusic.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                bgMusic.dispose();
                bgMusic = new MediaPlayer(bgMusicMedia);
                bgMusic.play();
                bgMusic.setOnEndOfMedia(this);
            }
        });
        bgMusic.play();
    }
    public static void stopBGMusic() {
        Utils.bgMusic.stop();
    }
}
