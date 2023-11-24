package stickhero;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;

public class Main extends GameApplication {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1920);
        settings.setHeight(1080);
        settings.setTitle("Stick Hero");
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new Factory());
        FXGL.spawn("tower", FXGL.getAppWidth() / 4, FXGL.getAppHeight() - 500);
    }
}
