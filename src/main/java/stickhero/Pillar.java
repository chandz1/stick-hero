package stickhero;

import com.almasb.fxgl.dsl.FXGL;

public class Pillar {
    public Pillar() {
        player = FXGL.spawn("tower", FXGL.getAppWidth() / 4, FXGL.getAppHeight() - 500);
    }

}
