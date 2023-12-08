package stickhero;

import javafx.scene.layout.Pane;

public final class Utils {
    private static Pane currentPane;
    private static Pillar basePillar;
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
}
