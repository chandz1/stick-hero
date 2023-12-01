package stickhero;

import javafx.scene.layout.Pane;

import java.io.Serializable;

public final class GameState implements Serializable {

    private Pane basePane;
    private Pillar basePillar;
    public GameState(Pane pane, Pillar pillar) {
        this.basePane = pane;
        this.basePillar = pillar;
    }
    public static void save() {
    }
    public static void load() {
    }
}
