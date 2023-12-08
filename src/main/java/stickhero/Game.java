package stickhero;

import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public class Game extends Application implements Serializable {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("Background.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600, 1000);


        // Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Stick Hero");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        Pane pane = (Pane) scene.lookup("#root");
        Utils.setPane(pane);
        Pillar pillar = new Pillar(true);

        Stick stick = new Stick();

        Hero hero = new Hero();

        Pillar pillar1 = new Pillar(false);
        pillar1.bringToScreen(pillar).play();

        Timeline stickTransform = new Timeline();
        Timeline rotate = new Timeline();

        KeyCombination kc = new KeyCodeCombination(KeyCode.SPACE);
        scene.setOnKeyPressed(event -> {

            if (kc.match(event)) {
                stick.setOpacity(1);
                KeyValue scaleY = new KeyValue(stick.scaleYProperty(), 700);
                KeyValue translateY = new KeyValue(stick.translateYProperty(), -700/2);
                KeyFrame keyFrame = new KeyFrame(Duration.millis(1000), scaleY, translateY);
                stickTransform.getKeyFrames().add(keyFrame);
                stickTransform.play();
            }
        });
        scene.setOnKeyReleased(event -> {
            if (kc.match(event)) {
                stickTransform.stop();
                stick.getTransforms().add(new Translate(0,-0.5));
                stick.setTranslateY(0);
                KeyValue rotateStick = new KeyValue(stick.rotateProperty(), 90, Interpolator.EASE_IN);
                KeyFrame frame = new KeyFrame(Duration.millis(500), rotateStick);
                rotate.getKeyFrames().add(frame);
                ParallelTransition parallel = new ParallelTransition(pillar.removeFromScreen(), pillar1.moveToBase());
                SequentialTransition sequence = new SequentialTransition(rotate, parallel);
                sequence.play();
            }
        });

    }
}
