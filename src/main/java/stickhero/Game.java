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

        Image image = new Image((Game.class.getResourceAsStream("cherry.png")));
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        Pane pane = (Pane) scene.lookup("#root");
        Utils.setPane(pane);
        Pillar pillar = new Pillar(true);
        imageView.setX(30);
        imageView.setY(pillar.getY() - image.getHeight());
        Stick stick = new Stick(5, 1, pillar.getWidth()-2.5, pane.getHeight() - pillar.getHeight());

        pane.getChildren().add(stick);
        pane.getChildren().add(imageView);

        Pillar pillar1 = new Pillar(false);
        pillar1.bringToScreen(pillar).play();

        Timeline stickTransform = new Timeline();
        Timeline rotate = new Timeline();
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000),imageView);

        KeyCombination kc = new KeyCodeCombination(KeyCode.SPACE);
        scene.setOnKeyPressed(event -> {

            if (kc.match(event)) {
                KeyValue scaleY = new KeyValue(stick.scaleYProperty(), 1000);
                KeyValue translateY = new KeyValue(stick.translateYProperty(), -1000/2);
                KeyFrame keyFrame = new KeyFrame(Duration.millis(1500), scaleY, translateY);
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
                KeyFrame keyFrame = new KeyFrame(Duration.millis(500), rotateStick);
                rotate.getKeyFrames().add(keyFrame);
                SequentialTransition sequence = new SequentialTransition(rotate, pillar.removeFromScreen(), pillar1.moveToBase());
                sequence.play();
            }
        });

    }
}
