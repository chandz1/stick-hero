package stickhero;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;

public final class Utils {
    private static Hero hero;
    private static Pane currentPane;
    private static Scene currentScene;
    private static Pillar basePillar;
    private static Pillar nextPillar;

    public static Hero getHero() {
        return hero;
    }

    public static void setHero(Hero hero) {
        Utils.hero = hero;
    }

    public static void setPane(Pane pane) {
        currentPane = pane;
    }
    public static Pane getPane() {
        return currentPane;
    }
    public static void setBasePillar(Pillar pillar) {
        basePillar = pillar;
    }

    public static Pillar getBasePillar() {
        return basePillar;
    }

    public static void setCurrentScene(Scene scene) {
        currentScene = scene;
    }

    public static Scene getCurrentScene() {
        return currentScene;
    }

    public static void setNextPillar(Pillar nextPillar) {
        Utils.nextPillar = nextPillar;
    }

    public static Pillar getNextPillar() {
        return nextPillar;
    }
}
