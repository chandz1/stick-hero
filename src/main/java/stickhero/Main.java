package stickhero;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import javafx.scene.text.Text;
import static com.almasb.fxgl.dsl.FXGL.*;

public class Main extends GameApplication {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1920);
        settings.setHeight(1080);
        settings.setTitle("Basic Game App");
    }

    @Override
    protected void initUI() {
        // 1. create any JavaFX or FXGL UI object
        Text uiText = new Text("Hello World");
        TestStick testStick = new TestStick(0,0,100,100);
        entityBuilder()
                .at(getAppWidth()/2, getAppHeight()/2)
                .viewWithBBox(new TestStick(0,0,100,100))
                .buildAndAttach();

        // 2. add the UI object to game scene (easy way) at 100, 100
        FXGL.addUINode(uiText, 100, 100);
        FXGL.addUINode(testStick);
    }
}