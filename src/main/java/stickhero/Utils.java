package stickhero;

import javafx.scene.layout.Pane;

public final class Utils {
    private static Pane currentPane;
    public static void setPane(Pane pane) {
        currentPane = pane;
    }
    public static Pane getPane() {
        return currentPane;
    }
}
