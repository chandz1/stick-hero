package stickhero;

import java.util.Random;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Factory implements EntityFactory {
    @Spawns("pillar")
    public Entity newPillar(SpawnData data) {
        return FXGL.entityBuilder(data)
                // Add random to util class
                .view(new Rectangle(new Random().nextInt(250) + 50, 500, Color.BLACK))
                .build();
    }
}
